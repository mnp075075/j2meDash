@echo off

if "%1"=="1" goto OP1
if "%1"=="2" goto OP2
if "%1"=="3" goto OP3

if not "%1"=="" goto INVALID_ARG

:MENU
	set "input="
	echo Choose Java version:
	echo 1. Java 1.4 (or Java 4) (output j2meDash4.jar)
	echo 2. Java 1.8 (or Java 8) (output j2meDash8.jar)
	echo 3. Both Java 1.4 and Java 1.8 (output both files)

	set /p input="Type your input here and press Enter: "

	if "%input%"=="1" goto OP1
	if "%input%"=="2" goto OP2
	if "%input%"=="3" goto OP3

	echo Invalid
	echo .
	goto MENU

:INVALID_ARG
	echo Invalid argument provided.
	pause
	exit /b

:OP1
	echo Generating class
	cd ..
	cd tmp
	if not exist "classes\" (
		mkdir classes
	)
	cd ..

	"C:\j2sdk1.4.2_19\bin\javac.exe" -encoding UTF-8 -source 1.3 -target 1.3 -bootclasspath C:\WTK2.5.2_01\lib\midpapi20.jar;C:\WTK2.5.2_01\lib\cldcapi11.jar;C:\Java_ME_platform_SDK_3.4\lib\jsr75_1.0.jar -sourcepath src -d tmp\classes @sourcelist.txt

	echo Verifying class

	"C:\Java_ME_platform_SDK_3.4\bin\preverify.exe" -classpath tmp\classes;C:\WTK2.5.2_01\lib\midpapi20.jar;C:\WTK2.5.2_01\lib\cldcapi11.jar;C:\Java_ME_platform_SDK_3.4\lib\jsr75_1.0.jar -d tmp\classes tmp\classes

	echo Packaging .jar
	"C:\j2sdk1.4.2_19\bin\jar.exe" cfm j2meDash4.jar src\META-INF\MANIFEST.MF rsc -C tmp\classes .
	
	if exist "j2meDash4.jar" (
		move "j2meDash4.jar" "bin\"
	)
	
	cd bin

	echo Compiled successfully
	goto :eof

:OP2
	echo Generating class
	cd ..
	cd tmp
	if not exist "classes\" (
		mkdir classes
	)
	cd ..

	"C:\Program Files\Java\jdk1.8.0_202\bin\javac.exe" -encoding UTF-8 -classpath "C:\Java_ME_platform_SDK_3.4\lib\*" -sourcepath src -d tmp\classes @sourcelist.txt

	echo Packaging .jar
	"C:\Program Files\Java\jdk1.8.0_202\bin\jar.exe" cfm j2meDash8.jar src\META-INF\MANIFEST.MF rsc -C tmp\classes .
	
	if exist "j2meDash8.jar" (
		move "j2meDash8.jar" "bin\"
	)
	
	cd bin

	echo Compiled successfully
	goto :eof

:OP3
	echo ### JAVA 1.4 ###
	
	echo Generating class
	cd ..
	cd tmp
	if not exist "classes\" (
		mkdir classes
	)
	cd ..

	"C:\j2sdk1.4.2_19\bin\javac.exe" -encoding UTF-8 -source 1.3 -target 1.3 -bootclasspath C:\WTK2.5.2_01\lib\midpapi20.jar;C:\WTK2.5.2_01\lib\cldcapi11.jar;C:\Java_ME_platform_SDK_3.4\lib\jsr75_1.0.jar -sourcepath src -d tmp\classes @sourcelist.txt

	echo Verifying class

	"C:\Java_ME_platform_SDK_3.4\bin\preverify.exe" -classpath tmp\classes;C:\WTK2.5.2_01\lib\midpapi20.jar;C:\WTK2.5.2_01\lib\cldcapi11.jar;C:\Java_ME_platform_SDK_3.4\lib\jsr75_1.0.jar -d tmp\classes tmp\classes

	echo Packaging .jar
	"C:\j2sdk1.4.2_19\bin\jar.exe" cfm j2meDash4.jar src\META-INF\MANIFEST.MF rsc -C tmp\classes .
	
	if exist "j2meDash4.jar" (
		move "j2meDash4.jar" "bin\"
	)
	
	cd bin

	echo Compiled successfully
	
	echo ### JAVA 1.8 ###
	
	echo Generating class
	cd ..
	cd tmp
	if not exist "classes\" (
		mkdir classes
	)
	cd ..

	"C:\Program Files\Java\jdk1.8.0_202\bin\javac.exe" -encoding UTF-8 -classpath "C:\Java_ME_platform_SDK_3.4\lib\*" -sourcepath src -d tmp\classes @sourcelist.txt

	echo Packaging .jar
	"C:\Program Files\Java\jdk1.8.0_202\bin\jar.exe" cfm j2meDash8.jar src\META-INF\MANIFEST.MF rsc -C tmp\classes .
	
	if exist "j2meDash8.jar" (
		move "j2meDash8.jar" "bin\"
	)
	
	cd bin

	echo Compiled successfully
	
	echo ### SUCCESSFULLY COMPILED BOTH VERSION ##
	goto :eof