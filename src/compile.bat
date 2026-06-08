@echo off

echo Choose Java version:

set "input="
echo 1. Java 1.4 (or Java 4)
echo 2. Java 1.8 (or Java 8)
echo 3. Both Java 1.4 and Java 1.8
echo 4. Nokia API addition for Java 1.4 (ONLY CHOOSE THIS IF YOU HAVE NOKIA PACKAGES)

echo IF YOU CHOOSE OPTION 4:
echo Please copy the "Nokia_API" folder inside this repository to VOLUME E:\, NOT C:\. And the path must be E:\Nokia_API\

set /p input="Type your input here and press Enter: "

if "%input%"=="1" (

	echo Generating class

	if not exist "classes\" (
		mkdir classes
	)

	"C:\j2sdk1.4.2_19\bin\javac.exe" -encoding UTF-8 -source 1.3 -target 1.3 -bootclasspath C:\WTK2.5.2_01\lib\midpapi20.jar;C:\WTK2.5.2_01\lib\cldcapi11.jar;C:\Java_ME_platform_SDK_3.4\lib\jsr75_1.0.jar -d classes *.java

	echo Verifying class

	"C:\Java_ME_platform_SDK_3.4\bin\preverify.exe" -classpath classes;C:\WTK2.5.2_01\lib\midpapi20.jar;C:\WTK2.5.2_01\lib\cldcapi11.jar;C:\Java_ME_platform_SDK_3.4\lib\jsr75_1.0.jar -d classes classes\.

	echo Packaging .jar
	"C:\j2sdk1.4.2_19\bin\jar.exe" cfm j2meDash4.jar META-INF\MANIFEST.MF assets levels -C classes .

	echo Compiled successfully
	
	doskey /listsize=0
	doskey /listsize=50

) else if "%input%"=="2" (

	echo Generating class

	if not exist "classes\" (
		mkdir classes
	)

	"C:\Program Files\Java\jdk1.8.0_202\bin\javac.exe" -encoding UTF-8 -classpath "C:\Java_ME_platform_SDK_3.4\lib\*" -d classes\ *.java

	echo Packaging .jar
	"C:\Program Files\Java\jdk1.8.0_202\bin\jar.exe" cfm j2meDash8.jar META-INF\MANIFEST.MF assets levels -C classes/ .

	echo Compiled successfully
	
	doskey /listsize=0
	doskey /listsize=50

) else if "%input%"=="3" (

	echo ### JAVA 1.4 ###
	
	echo Generating class

	if not exist "classes\" (
		mkdir classes
	)

	"C:\j2sdk1.4.2_19\bin\javac.exe" -encoding UTF-8 -source 1.3 -target 1.3 -bootclasspath C:\WTK2.5.2_01\lib\midpapi20.jar;C:\WTK2.5.2_01\lib\cldcapi11.jar;C:\Java_ME_platform_SDK_3.4\lib\jsr75_1.0.jar -d classes *.java

	echo Verifying class

	"C:\Java_ME_platform_SDK_3.4\bin\preverify.exe" -classpath classes;C:\WTK2.5.2_01\lib\midpapi20.jar;C:\WTK2.5.2_01\lib\cldcapi11.jar;C:\Java_ME_platform_SDK_3.4\lib\jsr75_1.0.jar -d classes classes\.

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
	
	echo ### SUCCESSFULLY COMPILED BOTH VERSION ##
	
	doskey /listsize=0
	doskey /listsize=50
	
) else if "%input%"=="4" (

	echo Option 4 is currently disabled because I have removed the Nokia_API\ directory from this repository, please choose options 1-3 instead
	
	doskey /listsize=0
	doskey /listsize=50
	
)