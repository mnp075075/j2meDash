#!/bin/bash

echo "Choose the JDK version to use for compilation:"
echo "1) JDK 4 (create j2meDash4.jar)"
echo "2) JDK 8 (create j2meDash8.jar)"
echo "3) Both JDK 4 and JDK 8"
read -p "Enter your choice: " choice

if [[ "$choice" == "1" ]]; then
    PACKAGE="proguard"
    if ! command -v "$PACKAGE" &> /dev/null
    then
        echo "Error: $PACKAGE is not installed. Installing it now..."
        # Check for package manager (e.g. apt, yum, dnf,...) and install ProGuard
        if command -v apt &> /dev/null; then
            sudo apt update && sudo apt install -y proguard
        elif command -v yum &> /dev/null; then
            sudo yum install -y proguard
        elif command -v dnf &> /dev/null; then
            sudo dnf install -y proguard
        else
            echo "Error: No supported package manager found. Please install ProGuard manually."
            exit 1
        fi
        echo "$PACKAGE installed successfully."
    fi
    set -e
    find ~ -name "j2sdk1.4*" -type d | head -n 1
    if [ $? -eq 0 ]; then
        jdk_path=$(find ~ -name "j2sdk1.4*" -type d | head -n 1)
        echo "Found JDK 1.4 installation at: $jdk_path"
    else
        echo "JDK 1.4 installation not found in the home directory."
        echo "Enter the path to the JDK 1.4 installation (e.g. /home/user/j2sdk1.4.2_19):"
        read jdk_path
        if [ ! -d "$jdk_path" ]; then
            echo "Error: The specified path does not exist or is not a directory."
            exit 1
        fi
    fi
    echo "Compiling source (Java 1.4)"
    echo "Generating class"

    cd ..
    cd tmp

    if [ ! -d "classes" ];
    then
        mkdir classes
    fi

    cd ..
    find "$(pwd)/src" -name "*.java" | sort > sourcelist.txt

    "$jdk_path/bin/javac" \
        -encoding UTF-8 \
        -source 1.3 \
        -target 1.3 \
        -classpath "$(paste -sd: <(ls -1 lib/*.jar 2>/dev/null))" \
        -sourcepath src \
        -d tmp/classes \
        @sourcelist.txt

    echo "Preverifying class"
    proguard -injars tmp/classes \
            -outjars tmp/classes_tmp \
            -libraryjars lib \
            -microedition \
            -dontoptimize \
            -dontobfuscate \
            -dontnote \
            -keep 'public class * extends javax.microedition.midlet.MIDlet' \
    && rm -rf tmp/classes \
    && mv tmp/classes_tmp tmp/classes

    echo "Packaging jar"
    "$jdk_path/bin/jar" cvfm j2meDash4.jar src/META-INF/MANIFEST.MF icon.png rsc -C tmp/classes .

    if [ -f "j2meDash4.jar" ];
    then
        mv j2meDash4.jar bin
    fi

    echo "Done"
elif [[ "$choice" == "2" ]]; then
    set -e
    find ~ -name "jdk1.8*" -type d | head -n 1
    if [ $? -eq 0 ]; then
        jdk_path=$(find ~ -name "jdk1.8*" -type d | head -n 1)
        echo "Found JDK 1.8 installation at: $jdk_path"
    else
        echo "JDK 1.8 installation not found in the home directory."
        echo "Enter the path to the JDK 1.8 installation (e.g. /home/user/jdk1.8.0_281):"
        read jdk_path
        if [ ! -d "$jdk_path" ]; then
            echo "Error: The specified path does not exist or is not a directory."
            exit 1
        fi
    fi
    echo "Compiling source (Java 8)"
    echo "Generating class"

    cd ..
    cd tmp

    if [ ! -d "classes" ];
    then
        mkdir classes
    fi

    cd ..
    find "$(pwd)/src" -name "*.java" | sort > sourcelist.txt

    "$jdk_path/bin/javac" \
        -encoding UTF-8 \
        -source 1.8 \
        -target 1.8 \
        -classpath "$(paste -sd: <(ls -1 lib/*.jar 2>/dev/null))" \
        -sourcepath src \
        -d tmp/classes \
        @sourcelist.txt

    echo "Packaging jar"
    "$jdk_path/bin/jar" cvfm j2meDash8.jar src/META-INF/MANIFEST.MF icon.png rsc -C tmp/classes .

    if [ -f "j2meDash8.jar" ];
    then
        mv j2meDash8.jar bin
    fi

    echo "Done"
elif [[ "$choice" == "3" ]]; then
    # Compile for JDK 4
    echo 1 | ./compile.sh

    # Compile for JDK 8
    echo 2 | ./compile.sh
else
    echo "Invalid choice"
fi
