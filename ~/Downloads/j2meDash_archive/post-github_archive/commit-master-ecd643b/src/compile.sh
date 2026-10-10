#!/bin/bash

"/home/.../j2sdk1.4.2_19/bin/javac" -encoding UTF-8 -source 1.3 -target 1.3 -classpath $(echo ../lib/*.jar | tr ' ' ':') -d classes *.java

proguard -injars classes -outjars classes_tmp -libraryjars ../lib -microedition -dontoptimize -dontobfuscate -dontnote -keep 'public class * extends javax.microedition.midlet.MIDlet' && rm -rf classes && mv classes_tmp classes

"/home/.../j2sdk1.4.2_19/bin/jar" cfm j2meDash4.jar META-INF/MANIFEST.MF assets levels -C classes .

