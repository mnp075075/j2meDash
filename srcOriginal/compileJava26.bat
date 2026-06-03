@echo off

echo generate class

if not exist "class_Java26\" (
mkdir class_Java26
)

cd ..
"C:\Program Files\Java\jdk-26.0.1\bin\javac.exe" -encoding UTF-8 -classpath "C:\Java_ME_platform_SDK_3.4\lib\*;lib\*" -d src\class_Java26\ src\GDOnJavaForJava26.java

cd src

:: echo verify class
:: "C:\Java_ME_platform_SDK_3.4\bin\preverify.exe" -classpath C:\WTK2.5.2_01\lib\midpapi20.jar;C:\WTK2.5.2_01\lib\cldcapi11.jar -d . .

echo packaging jar
"C:\Program Files\Java\jdk-26.0.1\bin\jar.exe" cvfm GDOnJavaForJava26.jar META-INF_Java26\MANIFEST.MF assets levels -C class_Java26 .

:: echo checking jar
:: "C:\Program Files\Java\jdk1.8.0_202\bin\jar.exe" tf GDOnJavaForJava8.jar

echo compiled successfully