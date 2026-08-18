@echo off

::  ██████╗ ██████╗  ██████╗ ███╗   ██╗     ██╗ █████╗ ██╗   ██╗ █████╗ 
:: ██╔════╝ ██╔══██╗██╔═══██╗████╗  ██║     ██║██╔══██╗██║   ██║██╔══██╗
:: ██║  ███╗██║  ██║██║   ██║██╔██╗ ██║     ██║███████║██║   ██║███████║
:: ██║   ██║██║  ██║██║   ██║██║╚██╗██║██   ██║██╔══██║╚██╗ ██╔╝██╔══██║
:: ╚██████╔╝██████╔╝╚██████╔╝██║ ╚████║╚█████╔╝██║  ██║ ╚████╔╝ ██║  ██║
::  ╚═════╝ ╚═════╝  ╚═════╝ ╚═╝  ╚═══╝ ╚════╝ ╚═╝  ╚═╝  ╚═══╝  ╚═╝  ╚═╝
                                                                     
echo compiling all version of the source code
echo this includes Java 1.4 and Java 8

cd ..
cd srcJava1.4
echo.
echo compiling Java 1.4
call compile.bat

cd ..
cd srcJava8
echo.
echo Compiling Java 8
call compile.bat

cd ..
cd bin

echo.
echo compiled successfully