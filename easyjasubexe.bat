@echo off
set BASE=%~dp0
for %%a in (%BASE%\easyjasub-cmd\target\easyjasub-*.exe) do set EXE=%%a
echo run easyjasub from %EXE% in %BASE% 
%EXE% %*