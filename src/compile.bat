@echo off

echo generate class
javac -source 1.3 -target 1.3 -bootclasspath C:\WTK2.5.2_01\lib\midpapi20.jar;C:\WTK2.5.2_01\lib\cldcapi11.jar GDOnJava.java

echo
echo verify class
"C:\Java_ME_platform_SDK_3.4\bin\preverify.exe" -classpath C:\WTK2.5.2_01\lib\midpapi20.jar;C:\WTK2.5.2_01\lib\cldcapi11.jar output

echo
echo packaging jar
jar cvfm GDOnJava.jar MANIFEST.MF *.class assets

echo compiled successfully
pause