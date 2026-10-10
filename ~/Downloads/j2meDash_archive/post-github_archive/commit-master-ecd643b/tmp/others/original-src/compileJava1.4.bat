@echo off

echo generate class

if not exist "class_Java1.4\" (
mkdir class_Java1.4
)

"C:\j2sdk1.4.2_19\bin\javac.exe" -encoding UTF-8 -source 1.3 -target 1.3 -bootclasspath C:\WTK2.5.2_01\lib\midpapi20.jar;C:\WTK2.5.2_01\lib\cldcapi11.jar -d class_Java1.4\ GDOnJavaForJava1dot4.java

echo verify class

"C:\Java_ME_platform_SDK_3.4\bin\preverify.exe" -classpath class_Java1.4;C:\WTK2.5.2_01\lib\midpapi20.jar;C:\WTK2.5.2_01\lib\cldcapi11.jar -d class_Java1.4 class_Java1.4\.

echo packaging jar
"C:\j2sdk1.4.2_19\bin\jar.exe" cvfm GDOnJavaForJava1dot4.jar META-INF_Java1.4\MANIFEST.MF assets levels -C class_Java1.4 .

echo compiled successfully