easyjasub
=========

easyjasub is a tool to add furigana and in-line translation to Japanese subtitles, for language learning. 
It takes Japanese and an other language (say English) text subtitles and combines them in picture-basted subtitles.

It is designed to work as a command-line utility or as a Java library to be integrated in other applications, or maybe a custom UI.

easyjasub is still in alpha development stage, you may find defects and its usage may be difficult, do not hesitate to ask for help opening a new ticket (http://sourceforge.net/p/easyjasub/tickets/)

Visit http://easyjasub.sourceforge.net for usage info and http://easyjasub.wordpress.com to get support and discuss about the project.

Developer information
---------------------

Source code is available on GitHub in https://github.com/riccardove/easyjasub

If you are a developer and want to use easyjasub as part of your application, the SonaType OSSRH Maven repository (https://oss.sonatype.org) is used to deploy artifacts,
see https://oss.sonatype.org/content/groups/public/com/github/riccardove/easyjasub/

The following Java libraries are embedded in easyjasub:
- subtitleConverter library https://github.com/JDaren/subtitleConverter is used to read the text subtitles
- Lucene Kuromoji Analyzer ${easyjasub.lucene-analyzers-kuromoji.version} http://www.atilika.org/ is used to parse Japanese 
- rendersnake library http://rendersnake.org is used to produce HTML files
- html2image library http://code.google.com/p/java-html2image/ is used to create the pictures from HTML files alternatively to wkhtmltoimage
- TagSoup http://home.ccil.org/~cowan/XML/tagsoup/ is used to parse the online Kanji Converter HTML
- Kurikosu library http://code.google.com/p/kurikosu/ is used for Katakana/Hiragana/Romaji conversions 
- nv-i18n library https://github.com/TakahikoKawasaki/nv-i18n is used for country codes list

Programs listed below may be used:
- BDSup2Sub https://github.com/mjuhasz/BDSup2Sub is necessary to convert the resulting BDN XML subtitle file
- wkhtmltopdf http://code.google.com/p/wkhtmltopdf/ is used to create the pictures from HTML files, you need to install it separately
- MeCab https://code.google.com/p/mecab/ may be used to parse Japanese, if you install it in your system
- ChaSen http://sourceforge.jp/projects/chasen-legacy/ is used by the online Kanji Converter
