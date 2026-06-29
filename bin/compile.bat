@echo off

echo Choose Java version:

set "input="
echo 1. Java 1.4 (or Java 4) (output j2meDash4.jar)
echo 2. Java 1.8 (or Java 8) (output j2meDash8.jar)
echo 3. Both Java 1.4 and Java 1.8 (output both files)

set /p input="Type your input here and press Enter: "

if "%input%"=="1" (

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
	"C:\j2sdk1.4.2_19\bin\jar.exe" cfm j2meDash4.jar src\META-INF\MANIFEST.MF -C src images -C src levels -C tmp\classes .

	echo Compiled successfully

) else if "%input%"=="2" (

	echo Generating class
	cd ..
	cd tmp
	if not exist "classes\" (
		mkdir classes
	)
	cd ..

	"C:\Program Files\Java\jdk1.8.0_202\bin\javac.exe" -encoding UTF-8 -classpath "C:\Java_ME_platform_SDK_3.4\lib\*" -sourcepath src -d tmp\classes @sourcelist.txt

	echo Packaging .jar
	"C:\Program Files\Java\jdk1.8.0_202\bin\jar.exe" cfm j2meDash8.jar src\META-INF\MANIFEST.MF -C src images -C src levels -C tmp\classes .

	echo Compiled successfully

) else if "%input%"=="3" (

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
	"C:\j2sdk1.4.2_19\bin\jar.exe" cfm j2meDash4.jar src\META-INF\MANIFEST.MF -C src images -C src levels -C tmp\classes .

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
	"C:\Program Files\Java\jdk1.8.0_202\bin\jar.exe" cfm j2meDash8.jar src\META-INF\MANIFEST.MF -C src images -C src levels -C tmp\classes .

	echo Compiled successfully
	
	echo ### SUCCESSFULLY COMPILED BOTH VERSION ##
	
)