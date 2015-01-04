easyjasub
=========

easyjasub (http://easyjasub.sf.net) is a tool to assist in understanding Japanese subtitles those who have a little knowledge of Japanese language, for language learning. It allows you to get subtitles with furigana and in-line translation, in several formats suitable for both video rendering and self-study.

It is designed to work as a command-line utility or as a Java library to be integrated in other applications, or maybe a custom UI.

It takes Japanese and an other language (say English) text subtitles and combines them in picture-basted (IDX/SUB) subtitles.

Timing for the Japanese subtitles is used, the secondary subtitles may be repeated into multiple lines.
The synchronization of input text subtitles is very important to properly associate them.

The program is now in early development stage, you may need to read the the inline help (-h option) or browse the code in http://github.com/riccardove/easyjasub

A JRE is required, even by the Windows .exe version.
wkhtmltoimage is required, take it from http://sf.net/p/wkhtmltopdf
BDSup2Sub may be useful (BDN/XML subtitles are created as intermediate output).

Basic usage:
easyjasub.exe -ja subtitle.jp.srt -tr subtitle.en.srt
java -jar easyjasub-cmd.jar -ja subtitle.jp.srt -tr subtitle.en.srt

You may find defects and usage may be difficult, do not hesitate to ask for help opening a new ticket (http://sf.net/p/easyjasub/tickets/)

Visit http://easyjasub.wordpress.com to get support and discuss about the project.

Developer information
---------------------

Source code is available on GitHub in https://github.com/riccardove/easyjasub

If you want to use easyjasub as part of your application, the SonaType OSSRH Maven repository (https://oss.sonatype.org) is used to deploy artifacts,
see https://oss.sonatype.org/content/groups/public/com/github/riccardove/easyjasub/

There is a continuous integration build in Travis (http://travis-ci.org), see http://travis-ci.org/riccardove/easyjasub