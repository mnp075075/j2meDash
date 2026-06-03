@echo off

echo generate class

if not exist "class_Java8\" (
mkdir class_Java8
)

cd ..
"C:\Program Files\Java\jdk1.8.0_202\bin\javac.exe" -encoding UTF-8 -classpath "C:\Java_ME_platform_SDK_3.4\lib\*" -d src\class_Java8\ src\GDOnJavaForJava8.java

cd src

:: echo verify class
:: "C:\Java_ME_platform_SDK_3.4\bin\preverify.exe" -classpath C:\WTK2.5.2_01\lib\midpapi20.jar;C:\WTK2.5.2_01\lib\cldcapi11.jar -d . .

echo packaging jar
"C:\Program Files\Java\jdk1.8.0_202\bin\jar.exe" cvfm GDOnJavaForJava8.jar META-INF_Java8\MANIFEST.MF assets levels -C class_Java8 .

:: echo checking jar
:: "C:\Program Files\Java\jdk1.8.0_202\bin\jar.exe" tf GDOnJavaForJava8.jar

echo compiled successfully