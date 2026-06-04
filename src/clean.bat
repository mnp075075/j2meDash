@echo off

echo Cleaning compiled .jar

if exist "j2meDash4.jar" ( 
	del /f j2meDash4.jar 
)
if exist "j2meDash8.jar" (
	del /f j2meDash8.jar 
)
if exist "j2meDash4+NOKIA.jar" ( 
	del /f j2meDash4+NOKIA.jar 
)

goto continue

if not exist "j2meDash4.jar" "j2meDash8.jar" "j2meDash4+NOKIA.jar" ( goto failed )

:continue
cd classes

if exist *.class (
	del /f /s /q *.class
	cd ..
	goto finished
)
if not exist *.class ( 
	cd ..
	goto failed
)

:failed
echo Cannot find *.jar and/or *.class
goto :eof

:finished
echo Finished deleting *.jar and *.class files
goto :eof