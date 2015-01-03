package com.github.riccardove.easyjasub.bdsup2sub;

/*
 * #%L
 * easyjasub-lib
 * %%
 * Copyright (C) 2014 Riccardo Vestrini
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */


import static bdsup2sub.core.Constants.DEFAULT_PALETTE_ALPHA;
import static bdsup2sub.core.Constants.DEFAULT_PALETTE_BLUE;
import static bdsup2sub.core.Constants.DEFAULT_PALETTE_GREEN;
import static bdsup2sub.core.Constants.DEFAULT_PALETTE_RED;
import static com.mortennobel.imagescaling.ResampleFilters.getBSplineFilter;
import static com.mortennobel.imagescaling.ResampleFilters.getBellFilter;
import static com.mortennobel.imagescaling.ResampleFilters.getBiCubicFilter;
import static com.mortennobel.imagescaling.ResampleFilters.getHermiteFilter;
import static com.mortennobel.imagescaling.ResampleFilters.getLanczos3Filter;
import static com.mortennobel.imagescaling.ResampleFilters.getMitchellFilter;
import static com.mortennobel.imagescaling.ResampleFilters.getTriangleFilter;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import bdsup2sub.bitmap.Bitmap;
import bdsup2sub.bitmap.BitmapWithPalette;
import bdsup2sub.bitmap.ErasePatch;
import bdsup2sub.bitmap.Palette;
import bdsup2sub.core.Configuration;
import bdsup2sub.core.CoreException;
import bdsup2sub.core.InputMode;
import bdsup2sub.core.LibLogger;
import bdsup2sub.core.OutputMode;
import bdsup2sub.core.PaletteMode;
import bdsup2sub.supstream.SubPicture;
import bdsup2sub.supstream.SubtitleStream;
import bdsup2sub.supstream.bd.SupBDWriter;
import bdsup2sub.supstream.bdnxml.SupXml;
import bdsup2sub.supstream.dvd.SubDvdWriter;
import bdsup2sub.supstream.dvd.SubPictureDVD;
import bdsup2sub.supstream.dvd.SupDvdUtil;
import bdsup2sub.supstream.dvd.SupDvdWriter;
import bdsup2sub.tools.EnhancedPngEncoder;
import bdsup2sub.utils.FilenameUtils;
import bdsup2sub.utils.SubtitleUtils;

import com.github.riccardove.easyjasub.EasyJaSubException;
import com.github.riccardove.easyjasub.EasyJaSubObserver;
import com.mortennobel.imagescaling.ResampleFilter;

/**
 * This class contains mostly code copied from BDSup2Sub "Core" class
 */
public class BDSup2SubWrapper {

	private final BDSup2SubManagerForEasyJaSub manager;

	public BDSup2SubWrapper(EasyJaSubObserver observer) {

		LibLogger.getInstance().setObserver(
				new BDSup2SubLoggerToEasyJaSubObserver(observer));

		manager = new BDSup2SubManagerForEasyJaSub();

		// BDSup2Sub with arguments -m 100 -x 10 -p keep -T 24p -v -o output.idx
		// input"
		configuration = Configuration.getInstance();
		configuration.setKeepFps(true);
		configuration.setVerbose(true);
		configuration.setOutputMode(OutputMode.VOBSUB);
	}

	public void run(File bdnXmlFile, File idxFile) throws EasyJaSubException {
		try {
			readXml(bdnXmlFile.getAbsolutePath(), manager);
			scanSubtitles();
			writeSub(idxFile.getAbsolutePath());
		} catch (CoreException ex) {
			throw new EasyJaSubException("Error running BDSup2Sub: "
					+ ex.getMessage(), ex);
		}
	}

	/** Default alpha map */
	private static final int[] DEFAULT_ALPHA = { 0, 0xf, 0xf, 0xf };

	private final Configuration configuration;
	private InputMode inMode;
	private SupXml supXml;
	private SubtitleStream subtitleStream;
	private SubPictureDVD subVobTrg;
	/** Array of subpictures used for editing and export */
	private SubPicture[] subPictures;
	private static final int MIN_IMAGE_DIMENSION = 8;
	/** Converted target bitmap of current subpicture - just for display */
	private static Bitmap trgBitmap;
	/** Palette of target caption */
	private static Palette trgPal;

	private void readXml(String fname, BDSup2SubManagerForEasyJaSub manager)
			throws CoreException {
		supXml = new SupXml(fname, manager);
		subtitleStream = supXml;

		inMode = InputMode.XML;

		// decode first frame
		subtitleStream.decode(0);
		subVobTrg = new SubPictureDVD();

		// automatically set luminance thresholds for VobSub conversion
		int maxLum = subtitleStream.getPalette().getY()[subtitleStream
				.getPrimaryColorIndex()] & 0xff;
		int[] luminanceThreshold = new int[2];
		configuration.setLuminanceThreshold(luminanceThreshold);
		if (maxLum > 30) {
			luminanceThreshold[0] = maxLum * 2 / 3;
			luminanceThreshold[1] = maxLum / 3;
		} else {
			luminanceThreshold[0] = 210;
			luminanceThreshold[1] = 160;
		}
	}

	/**
	 * Create a copy of the loaded subpicture information frames.<br>
	 * Apply scaling and speedup/delay to the copied frames.<br>
	 * Sync frames to target fps.
	 */
	private void scanSubtitles() {
		boolean convertFPS = configuration.getConvertFPS();
		subPictures = new SubPicture[subtitleStream.getFrameCount()];
		double factTS = convertFPS ? configuration.getFPSSrc()
				/ configuration.getFpsTrg() : 1.0;

		double fx;
		double fy;
		fx = 1.0;
		fy = 1.0;

		// first run: clone source subpics, apply speedup/down,
		SubPicture picSrc;
		for (int i = 0; i < subPictures.length; i++) {
			picSrc = subtitleStream.getSubPicture(i);
			subPictures[i] = new SubPicture(picSrc);
			long ts = picSrc.getStartTime();
			long te = picSrc.getEndTime();
			// copy time stamps and apply speedup/speeddown
			int delayPTS = configuration.getDelayPTS();
			if (!convertFPS) {
				subPictures[i].setStartTime(ts + delayPTS);
				subPictures[i].setEndTime(te + delayPTS);
			} else {
				subPictures[i].setStartTime((long) (ts * factTS + 0.5)
						+ delayPTS);
				subPictures[i]
						.setEndTime((long) (te * factTS + 0.5) + delayPTS);
			}
			// synchronize to target frame rate
			subPictures[i].setStartTime(SubtitleUtils.syncTimePTS(
					subPictures[i].getStartTime(), configuration.getFpsTrg(),
					configuration.getFpsTrg()));
			subPictures[i].setEndTime(SubtitleUtils.syncTimePTS(
					subPictures[i].getEndTime(), configuration.getFpsTrg(),
					configuration.getFpsTrg()));

			// set forced flag
			SubPicture picTrg = subPictures[i];

			double scaleX;
			double scaleY;
			if (configuration.getConvertResolution()) {
				// adjust image sizes and offsets
				// determine scaling factors
				picTrg.setWidth(configuration.getOutputResolution()
						.getDimensions()[0]);
				picTrg.setHeight(configuration.getOutputResolution()
						.getDimensions()[1]);
				scaleX = (double) picTrg.getWidth() / picSrc.getWidth();
				scaleY = (double) picTrg.getHeight() / picSrc.getHeight();
			} else {
				picTrg.setWidth(picSrc.getWidth());
				picTrg.setHeight(picSrc.getHeight());
				scaleX = 1.0;
				scaleY = 1.0;
			}
			int w = (int) (picSrc.getImageWidth() * scaleX * fx + 0.5);
			if (w < MIN_IMAGE_DIMENSION) {
				w = picSrc.getImageWidth();
			} else if (w > picTrg.getWidth()) {
				w = picTrg.getWidth();
			}

			int h = (int) (picSrc.getImageHeight() * scaleY * fy + 0.5);
			if (h < MIN_IMAGE_DIMENSION) {
				h = picSrc.getImageHeight();
			} else if (h > picTrg.getHeight()) {
				h = picTrg.getHeight();
			}
			picTrg.setImageWidth(w);
			picTrg.setImageHeight(h);

			int xOfs = (int) (picSrc.getXOffset() * scaleX + 0.5);
			int spaceSrc = (int) ((picSrc.getWidth() - picSrc.getImageWidth())
					* scaleX + 0.5);
			int spaceTrg = picTrg.getWidth() - w;
			xOfs += (spaceTrg - spaceSrc) / 2;
			if (xOfs < 0) {
				xOfs = 0;
			} else if (xOfs + w > picTrg.getWidth()) {
				xOfs = picTrg.getWidth() - w;
			}
			picTrg.setOfsX(xOfs);

			int yOfs = (int) (picSrc.getYOffset() * scaleY + 0.5);
			spaceSrc = (int) ((picSrc.getHeight() - picSrc.getImageHeight())
					* scaleY + 0.5);
			spaceTrg = picTrg.getHeight() - h;
			yOfs += (spaceTrg - spaceSrc) / 2;
			if (yOfs + h > picTrg.getHeight()) {
				yOfs = picTrg.getHeight() - h;
			}
			picTrg.setOfsY(yOfs);
		}

		// 2nd run: validate times
		SubPicture picPrev = null;
		SubPicture picNext;
		for (int i = 0; i < subPictures.length; i++) {
			if (i < subPictures.length - 1) {
				picNext = subPictures[i + 1];
			} else {
				picNext = null;
			}
			picSrc = subPictures[i];
			validateTimes(i, subPictures[i], picNext, picPrev);
			picPrev = picSrc;
		}
	}

	/**
	 * Check start and end time, fix overlaps etc.
	 * 
	 * @param idx
	 *            Index of subpicture (just for display)
	 * @param subPic
	 *            Subpicture to check/fix
	 * @param subPicNext
	 *            Next subpicture
	 * @param subPicPrev
	 *            Previous subpicture
	 */
	private void validateTimes(int idx, SubPicture subPic,
			SubPicture subPicNext, SubPicture subPicPrev) {
		long startTime = subPic.getStartTime();
		long endTime = subPic.getEndTime();
		final long delay = 5000 * 90; // default delay for missing end time (5
										// seconds)

		idx += 1; // only used for display

		// get end time of last frame
		long lastEndTime = subPicPrev != null ? subPicPrev.getEndTime() : -1;

		if (startTime < lastEndTime) {
			startTime = lastEndTime;
		}

		// get start time of next frame
		long nextStartTime = subPicNext != null ? subPicNext.getStartTime() : 0;

		if (nextStartTime == 0) {
			if (endTime > startTime) {
				nextStartTime = endTime;
			} else {
				// completely messed up:
				// end time and next start time are invalid
				nextStartTime = startTime + delay;
			}
		}

		if (endTime <= startTime) {
			endTime = startTime + delay;
			if (endTime > nextStartTime) {
				endTime = nextStartTime;
			}
		} else if (endTime > nextStartTime) {
			endTime = nextStartTime;
		}

		int minTimePTS = configuration.getMinTimePTS();
		if (endTime - startTime < minTimePTS) {
			if (configuration.getFixShortFrames()) {
				endTime = startTime + minTimePTS;
				if (endTime > nextStartTime) {
					endTime = nextStartTime;
				}
			}
		}

		if (subPic.getStartTime() != startTime) {
			subPic.setStartTime(SubtitleUtils.syncTimePTS(startTime,
					configuration.getFpsTrg(), configuration.getFpsTrg()));
		}
		if (subPic.getEndTime() != endTime) {
			subPic.setEndTime(SubtitleUtils.syncTimePTS(endTime,
					configuration.getFpsTrg(), configuration.getFpsTrg()));
		}
	}

	/**
	 * Create BD-SUP or VobSub or Xml.
	 * 
	 * @param fname
	 *            File name of SUP/SUB/XML to create
	 * @throws CoreException
	 */
	private void writeSub(String fname) throws CoreException {
		BufferedOutputStream out = null;
		List<Integer> offsets = null;
		List<Integer> timestamps = null;
		SortedMap<Integer, SubPicture> exportedSubPictures = new TreeMap<Integer, SubPicture>();
		int frameNum = 0;
		String fn = "";

		List<Integer> subPicturesToBeExported = getSubPicturesToBeExported();

		if (subPicturesToBeExported.isEmpty()) {
			return;
		}

		OutputMode outputMode = configuration.getOutputMode();
		try {
			// handle file name extensions depending on mode
			if (outputMode == OutputMode.VOBSUB) {
				fname = FilenameUtils.removeExtension(fname) + ".sub";
				out = new BufferedOutputStream(new FileOutputStream(fname));
				offsets = new ArrayList<Integer>();
				timestamps = new ArrayList<Integer>();
			} else if (outputMode == OutputMode.SUPIFO) {
				fname = FilenameUtils.removeExtension(fname) + ".sup";
				out = new BufferedOutputStream(new FileOutputStream(fname));
			} else if (outputMode == OutputMode.BDSUP) {
				fname = FilenameUtils.removeExtension(fname) + ".sup";
				out = new BufferedOutputStream(new FileOutputStream(fname));
			} else {
				fn = FilenameUtils.removeExtension(fname);
				fname = fn + ".xml";
			}

			// main loop
			int offset = 0;
			for (int i : subPicturesToBeExported) {
				SubPicture subPicture = subPictures[i];
				if (outputMode == OutputMode.VOBSUB) {
					offsets.add(offset);
					convertSup(i, frameNum / 2 + 1,
							subPicturesToBeExported.size());
					subVobTrg.copyInfo(subPicture);
					byte buf[] = SubDvdWriter.createSubFrame(subVobTrg,
							trgBitmap);
					out.write(buf);
					offset += buf.length;
					timestamps.add((int) subPicture.getStartTime());
				} else if (outputMode == OutputMode.SUPIFO) {
					convertSup(i, frameNum / 2 + 1,
							subPicturesToBeExported.size());
					subVobTrg.copyInfo(subPicture);
					byte buf[] = SupDvdWriter.createSupFrame(subVobTrg,
							trgBitmap);
					out.write(buf);
				} else if (outputMode == OutputMode.BDSUP) {
					subPicture.setCompositionNumber(frameNum);
					convertSup(i, frameNum / 2 + 1,
							subPicturesToBeExported.size());
					byte buf[] = SupBDWriter.createSupFrame(subPicture,
							trgBitmap, trgPal);
					out.write(buf);
				} else {
					// Xml
					convertSup(i, frameNum / 2 + 1,
							subPicturesToBeExported.size());
					String fnp = SupXml.getPNGname(fn, i + 1);
					// File file = new File(fnp);
					// ImageIO.write(trgBitmap.getImage(trgPal), "png", file);
					out = new BufferedOutputStream(new FileOutputStream(fnp));
					EnhancedPngEncoder pngEncoder = new EnhancedPngEncoder(
							trgBitmap.getImage(trgPal.getColorModel()));
					byte buf[] = pngEncoder.pngEncode();
					out.write(buf);
					out.close();
					exportedSubPictures.put(i, subPicture);
				}
				frameNum += 2;
			}
		} catch (IOException ex) {
			throw new CoreException(ex.getMessage());
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException ex) {
			}
		}

		Palette trgPallete = null;
		if (outputMode == OutputMode.VOBSUB) {
			// VobSub - write IDX
			/* return offsets as array of ints */
			int[] ofs = new int[offsets.size()];
			for (int i = 0; i < ofs.length; i++) {
				ofs[i] = offsets.get(i);
			}
			int[] ts = new int[timestamps.size()];
			for (int i = 0; i < ts.length; i++) {
				ts[i] = timestamps.get(i);
			}
			fname = FilenameUtils.removeExtension(fname) + ".idx";
			trgPallete = currentDVDPalette;
			SubDvdWriter.writeIdx(fname, subPictures[0], ofs, ts, trgPallete);
		} else if (outputMode == OutputMode.XML) {
			// XML - write XML
			SupXml.writeXml(fname, exportedSubPictures);
		}
	}

	/**
	 * Convert source subpicture image to target subpicture image.
	 * 
	 * @param index
	 *            Index of subtitle to convert
	 * @param displayNum
	 *            Subtitle number to display (needed for forced subs)
	 * @param displayMax
	 *            Maximum subtitle number to display (needed for forced subs)
	 * @throws CoreException
	 */
	private void convertSup(int index, int displayNum, int displayMax)
			throws CoreException {
		convertSup(index, displayNum, displayMax, false);
	}

	/**
	 * Update width, height and offsets of target SubPicture.<br>
	 * This is needed if cropping captions during decode (i.e. the source image
	 * size changes).
	 * 
	 * @param index
	 *            Index of caption
	 * @return true: image size has changed, false: image size didn't change.
	 */
	private boolean updateTrgPic(int index) {
		SubPicture picSrc = subtitleStream.getSubPicture(index);
		SubPicture picTrg = subPictures[index];
		double scaleX = (double) picTrg.getWidth() / picSrc.getWidth();
		double scaleY = (double) picTrg.getHeight() / picSrc.getHeight();
		double fx;
		double fy;
		if (configuration.getApplyFreeScale()) {
			fx = configuration.getFreeScaleFactorX();
			fy = configuration.getFreeScaleFactorY();
		} else {
			fx = 1.0;
			fy = 1.0;
		}

		int wOld = picTrg.getImageWidth();
		int hOld = picTrg.getImageHeight();
		int wNew = (int) (picSrc.getImageWidth() * scaleX * fx + 0.5);
		if (wNew < MIN_IMAGE_DIMENSION) {
			wNew = picSrc.getImageWidth();
		} else if (wNew > picTrg.getWidth()) {
			wNew = picTrg.getWidth();
		}
		int hNew = (int) (picSrc.getImageHeight() * scaleY * fy + 0.5);
		if (hNew < MIN_IMAGE_DIMENSION) {
			hNew = picSrc.getImageHeight();
		} else if (hNew > picTrg.getHeight()) {
			hNew = picTrg.getHeight();
		}
		picTrg.setImageWidth(wNew);
		picTrg.setImageHeight(hNew);
		if (wNew != wOld) {
			int xOfs = (int) (picSrc.getXOffset() * scaleX + 0.5);
			int spaceSrc = (int) ((picSrc.getWidth() - picSrc.getImageWidth())
					* scaleX + 0.5);
			int spaceTrg = picTrg.getWidth() - wNew;
			xOfs += (spaceTrg - spaceSrc) / 2;
			if (xOfs < 0) {
				xOfs = 0;
			} else if (xOfs + wNew > picTrg.getWidth()) {
				xOfs = picTrg.getWidth() - wNew;
			}
			picTrg.setOfsX(xOfs);
		}
		if (hNew != hOld) {
			int yOfs = (int) (picSrc.getYOffset() * scaleY + 0.5);
			int spaceSrc = (int) ((picSrc.getHeight() - picSrc.getImageHeight())
					* scaleY + 0.5);
			int spaceTrg = picTrg.getHeight() - hNew;
			yOfs += (spaceTrg - spaceSrc) / 2;
			if (yOfs + hNew > picTrg.getHeight()) {
				yOfs = picTrg.getHeight() - hNew;
			}
			picTrg.setOfsY(yOfs);
		}
		// was image cropped?
		return (wNew != wOld) || (hNew != hOld);
	}

	/**
	 * Convert source subpicture image to target subpicture image.
	 * 
	 * @param index
	 *            Index of subtitle to convert
	 * @param displayNum
	 *            Subtitle number to display (needed for forced subs)
	 * @param displayMax
	 *            Maximum subtitle number to display (needed for forced subs)
	 * @param skipScaling
	 *            true: skip bitmap scaling and palette transformation (used for
	 *            moving captions)
	 * @throws CoreException
	 */
	private void convertSup(int index, int displayNum, int displayMax,
			boolean skipScaling) throws CoreException {
		int w, h;
		SubPicture subPic = subtitleStream.getSubPicture(index);

		// synchronized (semaphore)
		{
			subtitleStream.decode(index);
			w = subPic.getImageWidth();
			h = subPic.getImageHeight();
			OutputMode outputMode = configuration.getOutputMode();
			if (outputMode == OutputMode.VOBSUB
					|| outputMode == OutputMode.SUPIFO) {
				determineFramePal(index);
			}
			updateTrgPic(index);
		}
		SubPicture picTrg = subPictures[index];
		picTrg.setWasDecoded(true);

		int trgWidth = picTrg.getImageWidth();
		int trgHeight = picTrg.getImageHeight();
		if (trgWidth < MIN_IMAGE_DIMENSION || trgHeight < MIN_IMAGE_DIMENSION
				|| w < MIN_IMAGE_DIMENSION || h < MIN_IMAGE_DIMENSION) {
			// don't scale to avoid division by zero in scaling routines
			trgWidth = w;
			trgHeight = h;
		}

		if (!skipScaling) {
			ResampleFilter f;
			switch (configuration.getScalingFilter()) {
			case BELL:
				f = getBellFilter();
				break;
			case BICUBIC:
				f = getBiCubicFilter();
				break;
			case BICUBIC_SPLINE:
				f = getBSplineFilter();
				break;
			case HERMITE:
				f = getHermiteFilter();
				break;
			case LANCZOS3:
				f = getLanczos3Filter();
				break;
			case TRIANGLE:
				f = getTriangleFilter();
				break;
			case MITCHELL:
				f = getMitchellFilter();
				break;
			default:
				f = null;
			}

			Bitmap tBm;
			Palette tPal = trgPal;
			// create scaled bitmap
			OutputMode outputMode = configuration.getOutputMode();
			PaletteMode paletteMode = configuration.getPaletteMode();
			if (outputMode == OutputMode.VOBSUB
					|| outputMode == OutputMode.SUPIFO) {
				// export 4 color palette
				if (w == trgWidth && h == trgHeight) {
					// don't scale at all
					if ((inMode == InputMode.VOBSUB || inMode == InputMode.SUPIFO)
							&& paletteMode == PaletteMode.KEEP_EXISTING) {
						tBm = subtitleStream.getBitmap(); // no conversion
					} else {
						tBm = subtitleStream.getBitmap()
								.getBitmapWithNormalizedPalette(
										subtitleStream.getPalette().getAlpha(),
										configuration.getAlphaThreshold(),
										subtitleStream.getPalette().getY(),
										configuration.getLuminanceThreshold()); // reduce
																				// palette
					}
				} else {
					// scale up/down
					if ((inMode == InputMode.VOBSUB || inMode == InputMode.SUPIFO)
							&& paletteMode == PaletteMode.KEEP_EXISTING) {
						// keep palette
						if (f != null) {
							tBm = subtitleStream.getBitmap().scaleFilter(
									trgWidth, trgHeight,
									subtitleStream.getPalette(), f);
						} else {
							tBm = subtitleStream.getBitmap().scaleBilinear(
									trgWidth, trgHeight,
									subtitleStream.getPalette());
						}
					} else {
						// reduce palette
						if (f != null) {
							tBm = subtitleStream.getBitmap().scaleFilterLm(
									trgWidth, trgHeight,
									subtitleStream.getPalette(),
									configuration.getAlphaThreshold(),
									configuration.getLuminanceThreshold(), f);
						} else {
							tBm = subtitleStream.getBitmap().scaleBilinearLm(
									trgWidth, trgHeight,
									subtitleStream.getPalette(),
									configuration.getAlphaThreshold(),
									configuration.getLuminanceThreshold());
						}
					}
				}
			} else {
				// export (up to) 256 color palette
				tPal = subtitleStream.getPalette();
				if (w == trgWidth && h == trgHeight) {
					tBm = subtitleStream.getBitmap(); // no scaling, no
														// conversion
				} else {
					// scale up/down
					if (paletteMode == PaletteMode.KEEP_EXISTING) {
						// keep palette
						if (f != null) {
							tBm = subtitleStream.getBitmap().scaleFilter(
									trgWidth, trgHeight,
									subtitleStream.getPalette(), f);
						} else {
							tBm = subtitleStream.getBitmap().scaleBilinear(
									trgWidth, trgHeight,
									subtitleStream.getPalette());
						}
					} else {
						// create new palette
						boolean dither = paletteMode == PaletteMode.CREATE_DITHERED;
						BitmapWithPalette pb;
						if (f != null) {
							pb = subtitleStream.getBitmap().scaleFilter(
									trgWidth, trgHeight,
									subtitleStream.getPalette(), f, dither);
						} else {
							pb = subtitleStream.getBitmap().scaleBilinear(
									trgWidth, trgHeight,
									subtitleStream.getPalette(), dither);
						}
						tBm = pb.bitmap;
						tPal = pb.palette;
					}
				}
			}
			if (!picTrg.getErasePatch().isEmpty()) {
				// trgBitmapUnpatched = new Bitmap(tBm);
				int col = tPal.getIndexOfMostTransparentPaletteEntry();
				for (ErasePatch ep : picTrg.getErasePatch()) {
					tBm.fillRectangularWithColorIndex(ep.x, ep.y, ep.width,
							ep.height, (byte) col);
				}
			} else {
				// trgBitmapUnpatched = tBm;
			}
			trgBitmap = tBm;
			trgPal = tPal;

		}
	}

	/**
	 * Return indexes of subpictures to be exported.
	 * 
	 * @return indexes of subpictures to be exported
	 */
	private List<Integer> getSubPicturesToBeExported() {
		List<Integer> subPicturesToBeExported = new ArrayList<Integer>();
		for (int i = 0; i < subPictures.length; i++) {
			SubPicture subPicture = subPictures[i];
			if (!subPicture.isExcluded()
					&& (!configuration.isExportForced() || subPicture
							.isForced())) {
				subPicturesToBeExported.add(i);
			}
		}
		return subPicturesToBeExported;
	}

	private final Palette currentDVDPalette = new Palette(DEFAULT_PALETTE_RED,
			DEFAULT_PALETTE_GREEN, DEFAULT_PALETTE_BLUE, DEFAULT_PALETTE_ALPHA,
			true);

	/**
	 * Create the frame individual 4-color palette for VobSub mode.
	 * 
	 * @index Index of caption
	 */
	private void determineFramePal(int index) {
		if ((inMode != InputMode.VOBSUB && inMode != InputMode.SUPIFO)
				|| configuration.getPaletteMode() != PaletteMode.KEEP_EXISTING) {
			// get the primary color from the source palette
			int rgbSrc[] = subtitleStream.getPalette().getRGB(
					subtitleStream.getPrimaryColorIndex());

			// match with primary color from 16 color target palette
			// note: skip index 0 , primary colors at even positions
			// special treatment for index 1: white
			Palette trgPallete = currentDVDPalette;
			int minDistance = 0xffffff; // init > 0xff*0xff*3 = 0x02fa03
			int colIdx = 0;
			for (int idx = 1; idx < trgPallete.getSize(); idx += 2) {
				int rgb[] = trgPallete.getRGB(idx);
				// distance vector (skip sqrt)
				int rd = rgbSrc[0] - rgb[0];
				int gd = rgbSrc[1] - rgb[1];
				int bd = rgbSrc[2] - rgb[2];
				int distance = rd * rd + gd * gd + bd * bd;
				// new minimum distance ?
				if (distance < minDistance) {
					colIdx = idx;
					minDistance = distance;
					if (minDistance == 0) {
						break;
					}
				}
				// special treatment for index 1 (white)
				if (idx == 1) {
					idx--; // -> continue with index = 2
				}
			}

			// set new frame palette
			int palFrame[] = new int[4];
			palFrame[0] = 0; // black - transparent color
			palFrame[1] = colIdx; // primary color
			if (colIdx == 1) {
				palFrame[2] = colIdx + 2; // special handling: white + dark grey
			} else {
				palFrame[2] = colIdx + 1; // darker version of primary color
			}
			palFrame[3] = 0; // black - opaque

			subVobTrg.setAlpha(DEFAULT_ALPHA);
			subVobTrg.setPal(palFrame);

			trgPal = SupDvdUtil.decodePalette(subVobTrg, trgPallete);
		}
	}

}
