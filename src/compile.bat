@echo off

echo Choose Java version:

set "input="
echo 1. Java 1.4 (or Java 4)
echo 2. Java 1.8 (or Java 8)
echo 3. Both Java 1.4 and Java 1.8

set /p input="Type your input here and press Enter: "

if "%input%"=="1" (

	echo Generating class

	if not exist "classes\" (
		mkdir classes
	)

	"C:\j2sdk1.4.2_19\bin\javac.exe" -encoding UTF-8 -source 1.3 -target 1.3 -bootclasspath C:\WTK2.5.2_01\lib\midpapi20.jar;C:\WTK2.5.2_01\lib\cldcapi11.jar -d classes *.java

	echo Verifying class

	"C:\Java_ME_platform_SDK_3.4\bin\preverify.exe" -classpath classes;C:\WTK2.5.2_01\lib\midpapi20.jar;C:\WTK2.5.2_01\lib\cldcapi11.jar -d classes classes\.

	echo Packaging .jar
	"C:\j2sdk1.4.2_19\bin\jar.exe" cfm j2meDash4.jar META-INF\MANIFEST.MF assets levels -C classes .

	echo Compiled successfully

) else if "%input%"=="2" (

	echo Generating class

	if not exist "classes\" (
		mkdir classes
	)

	"C:\Program Files\Java\jdk1.8.0_202\bin\javac.exe" -encoding UTF-8 -classpath "C:\Java_ME_platform_SDK_3.4\lib\*" -d classes\ *.java

	echo Packaging .jar
	"C:\Program Files\Java\jdk1.8.0_202\bin\jar.exe" cfm j2meDash8.jar META-INF\MANIFEST.MF assets levels -C classes/ .

	echo Compiled successfully

) else if "%input%"=="3" (

	echo ### JAVA 1.4 ###
	
	echo Generating class

	if not exist "classes\" (
		mkdir classes
	)

	"C:\j2sdk1.4.2_19\bin\javac.exe" -encoding UTF-8 -source 1.3 -target 1.3 -bootclasspath C:\WTK2.5.2_01\lib\midpapi20.jar;C:\WTK2.5.2_01\lib\cldcapi11.jar -d classes *.java

	echo Verifying class

	"C:\Java_ME_platform_SDK_3.4\bin\preverify.exe" -classpath classes;C:\WTK2.5.2_01\lib\midpapi20.jar;C:\WTK2.5.2_01\lib\cldcapi11.jar -d classes classes\.

	echo Packaging .jar
	"C:\j2sdk1.4.2_19\bin\jar.exe" cfm j2meDash4.jar META-INF\MANIFEST.MF assets levels -C classes .

	echo Compiled successfully
	
	echo ### JAVA 1.8 ###
	
	echo Generating class

	if not exist "classes\" (
		mkdir classes
	)

	"C:\Program Files\Java\jdk1.8.0_202\bin\javac.exe" -encoding UTF-8 -classpath "C:\Java_ME_platform_SDK_3.4\lib\*" -d classes\ *.java

	echo Packaging .jar
	"C:\Program Files\Java\jdk1.8.0_202\bin\jar.exe" cfm j2meDash8.jar META-INF\MANIFEST.MF assets levels -C classes/ .

	echo Compiled successfully
	
	echo ### SUCCESSFULLY COMPILED BOTH VERSION ###
	
)