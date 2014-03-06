EasyJaSub
=========

EasyJaSub is a tool to add furigana and in-line translation to japanese subtitles, for language learning. 
It takes Japanese and an other language (say English) text subtitles and combines them in picture-basted subtitles. 

With the help of Kanji Converter (http://nihongo.j-talk.com/) it adds furigana, split words, put romaji and a raw word translation where possible. 
Actually you need to manually use the Kanji Converter website and save the resulting HTML locally; 
of course the result of the automatic converter is not always accurate.

Here is a sample of the resulting subtitle caption: https://github.com/riccardove/easyjasub/blob/master/easyjasub_sample.png

Timing for the Japanese subtitles is used, the secondary subtitles may be repeated into multiple lines. 
The synchronization of input text subtitles is very important to properly associate them.

Supported formats for input text subtitles are: EBU's STL, .SCC, .ASS/.SSA, .SRT, TTML; 
https://github.com/JDaren/subtitleConverter is used to read the text subtitles

Supported output formats are: Blu-Ray SUP, Sony BDN XML, VobSub (SUB/IDX), DVD-SUP (SUP/IFO); 
https://github.com/mjuhasz/BDSup2Sub is used to write the resulting subtitle file

It is designed to work as a command-line utility or as a Java library to be integrated in other applications, or maybe a custom UI.

This is in programming exercise status, it is not suitable for usage unless you have some software development skill and knowledge of subtitle file formats

If you would like to try this out, please contact me and send me your subtitle files, preferably in a textual format; I will email you back the resulting subtitles

If you know any similar tool please warn me so that I can contribute to it instead of writing a new one...

If you think that this little thing is useful and would like to contribute, you are more than welcome!

http://chiphead.jp/font.htm Cinecaption and http://www.l.u-tokyo.ac.jp/GT/downloads.html gt2000-01 fonts are used

http://rendersnake.org is used to produce HTML files

http://code.google.com/p/wkhtmltopdf/ is used to create the pictures from HTML files

http://sourceforge.jp/projects/chasen-legacy/ ChaSen is used by Kanji Converter

Developer information
---------------------
The SonaType OSSRH Maven repository (https://oss.sonatype.org) is used to deploy artifacts
Releases: https://oss.sonatype.org/content/groups/public/com/github/riccardove/easyjasub/
Snapshots: https://oss.sonatype.org/content/repositories/snapshots/com/github/riccardove/easyjasub/
