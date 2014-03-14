easyjasub
=========

easyjasub is a tool to add furigana and in-line translation to Japanese subtitles, for language learning. 
It takes Japanese and an other language (say English) text subtitles and combines them in picture-basted subtitles.

It is designed to work as a command-line utility or as a Java library to be integrated in other applications, or maybe a custom UI.

easyjasub is still in alpha development stage, you may find defects and its usage may be difficult, do not hesitate to ask for help opening a new ticket (http://sourceforge.net/p/easyjasub/tickets/)

Visit http://easyjasub.sourceforge.net/ for usage info

Developer information
---------------------

Source code is available on GitHub in https://github.com/riccardove/easyjasub

If you are a developer and want to use easyjasub as part of your application, the SonaType OSSRH Maven repository (https://oss.sonatype.org) is used to deploy artifacts,
see https://oss.sonatype.org/content/groups/public/com/github/riccardove/easyjasub/

- MeCab https://code.google.com/p/mecab/downloads/list is used to parse Japanese, you need to install it separately
- https://github.com/JDaren/subtitleConverter is used to read the text subtitles
- https://github.com/mjuhasz/BDSup2Sub is necessary to convert the resulting BDN XML subtitle file
- http://rendersnake.org is used to produce HTML files
- http://code.google.com/p/wkhtmltopdf/ is used to create the pictures from HTML files
- http://sourceforge.jp/projects/chasen-legacy/ ChaSen is used by Kanji Converter
