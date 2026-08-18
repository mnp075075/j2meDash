@echo off

echo Cleaning compiled .jar

if exist "j2meDash4.jar" ( 
	set found=1
)
if exist "j2meDash8.jar" (
	set found=1
)
if exist "j2meDash4+NOKIA.jar" ( 
	set found=1
)

if "%found%"==0 ( goto failed1 )

if exist "j2meDash4.jar" ( 
	del /f j2meDash4.jar 
)
if exist "j2meDash8.jar" (
	del /f j2meDash8.jar 
)

goto continue

:continue
cd ..
cd tmp
cd classes

if exist "com/" (
	rmdir /s /q "com/"
	cd ..
	goto finished
)
if not exist *.class ( 
	cd ..
	goto failed2
)

:failed1
echo Cannot find *.jar
goto :eof

:failed2
echo Cannot find *.class
cd ..
cd bin
goto :eof

:finished
echo Finished deleting *.jar and *.class files
cd ..
cd bin
goto :eof