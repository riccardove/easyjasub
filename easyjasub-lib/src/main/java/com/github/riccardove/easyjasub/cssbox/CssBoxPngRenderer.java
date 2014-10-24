/**
 * ImageRenderer.java
 * Copyright (c) 2005-2014 Radek Burget
 *
 * CSSBox is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *  
 * CSSBox is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *  
 * You should have received a copy of the GNU Lesser General Public License
 * along with CSSBox. If not, see <http://www.gnu.org/licenses/>.
 *
 * Created on 5.2.2009, 12:00:02 by burgetr
 */
package com.github.riccardove.easyjasub.cssbox;

import java.awt.Dimension;
import java.awt.Font;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;

import javax.imageio.ImageIO;

import org.fit.cssbox.css.CSSNorm;
import org.fit.cssbox.css.DOMAnalyzer;
import org.fit.cssbox.io.DOMSource;
import org.fit.cssbox.io.DefaultDOMSource;
import org.fit.cssbox.io.DefaultDocumentSource;
import org.fit.cssbox.io.DocumentSource;
import org.fit.cssbox.layout.BrowserCanvas;
import org.fit.cssbox.layout.BrowserConfig;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import cz.vutbr.web.css.MediaSpec;

/**
 * Borrows from ImageRenderer sample of CSSBox
 */
public class CssBoxPngRenderer {

	public void fromFile(File htmlFile, int width, int height, File pngFile)
			throws IOException, SAXException {
		windowSize = new Dimension(width, height);
		boolean ok = false;
		FileOutputStream os = new FileOutputStream(pngFile);
		try {
			renderURL(htmlFile.toURI().toURL(), htmlFile.getParentFile()
					.toURI().toURL(), os);
			ok = true;
		} finally {
			os.close();
			if (!ok && pngFile.exists()) {
				pngFile.delete();
			}
		}
	}

	private final String mediaType = "screen";
	private Dimension windowSize;
	private final boolean cropWindow = true;
	private final boolean loadImages = false;
	private final boolean loadBackgroundImages = false;

	/**
	 * Renders the URL and prints the result to the specified output stream in
	 * the specified format.
	 * 
	 * @param urlstring
	 *            the source URL
	 * @param out
	 *            output stream
	 * @param type
	 *            output type
	 * @return true in case of success, false otherwise
	 * @throws SAXException
	 */
	private boolean renderURL(URL urlstring, URL baseUrl, OutputStream out)
			throws IOException, SAXException {
		// Open the network connection
		DocumentSource docSource = new DefaultDocumentSource(urlstring);


		// Parse the input document
		DOMSource parser = new DefaultDOMSource(docSource);
		Document doc = parser.parse();

		// create the media specification
		MediaSpec media = new MediaSpec(mediaType);
		media.setDimensions(windowSize.width, windowSize.height);
		media.setDeviceDimensions(windowSize.width, windowSize.height);

		// Create the CSS analyzer
		DOMAnalyzer da = new DOMAnalyzer(doc, baseUrl);
		da.setMediaSpec(media);
		da.attributesToStyles(); // convert the HTML presentation attributes to
									// inline styles
		da.addStyleSheet(baseUrl, CSSNorm.stdStyleSheet(),
				DOMAnalyzer.Origin.AGENT); // use the standard style sheet
		da.addStyleSheet(null, CSSNorm.userStyleSheet(),
				DOMAnalyzer.Origin.AGENT); // use the additional style sheet
		da.addStyleSheet(null, CSSNorm.formsStyleSheet(),
				DOMAnalyzer.Origin.AGENT); // render form fields using css
		da.getStyleSheets(); // load the author style sheets

		BrowserCanvas contentCanvas = new BrowserCanvas(da.getRoot(), da,
				baseUrl);
		// contentCanvas.setAutoMediaUpdate(false); // we have a correct media
		// // specification, do not
		// // update
		contentCanvas.getConfig().setClipViewport(cropWindow);
		contentCanvas.getConfig().setLoadImages(loadImages);
		contentCanvas.getConfig().setLoadBackgroundImages(loadBackgroundImages);
		contentCanvas.setPreferredSize(new Dimension(windowSize.width, 10));
		contentCanvas.setAutoSizeUpdate(true);

		setDefaultFonts(contentCanvas.getConfig());

		contentCanvas.createLayout(windowSize);
		contentCanvas.validate();
		ImageIO.write(contentCanvas.getImage(), "png", out);

		// Image image = contentCanvas.createImage(windowSize.width,
		// windowSize.height);

		docSource.close();

		return true;
	}

	/**
	 * Sets some common fonts as the defaults for generic font families.
	 */
	private void setDefaultFonts(BrowserConfig config) {
		config.setDefaultFont(Font.SERIF, "Times New Roman");
		config.setDefaultFont(Font.SANS_SERIF, "Arial");
		config.setDefaultFont(Font.MONOSPACED, "Courier New");
	}
}
