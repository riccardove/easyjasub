@echo off
set BASE=%~dp0
for %%a in (%BASE%target\easyjasub-*-jar-with-dependencies.jar) do set JAR=%%a
echo run easyjasub from %JAR% in %BASE% 
java -jar %JAR% %*