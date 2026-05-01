# GDOnJava #
GDOnJava is Geometry Dash ported in Java ME (or J2ME) for Java-supported phones

## WARNING
- This project is still experimental and is in its alpha phase.
- I have only built support for Nokia Asha 311 phone model (resolution 240x400). As for other models, I will add them later in the `main` branch.

## Prerequisites
You need **Java 2 SDK Standard Edition**, **Java ME SDK**, and **Sun Java Wireless Toolkit** (I'm using Java 2 SDK SE v1.4.2_19, Java ME SDK v3.4 and Sun Java WTK v2.5.2_01), both of which can be obtained on Oracle's website

**Link:**
  - Java 2 SDK SE: https://www.oracle.com/java/technologies/java-archive-javase5-downloads.html$0
  - Java ME SDK: https://www.oracle.com/java/technologies/javame-sdk-downloads.html$0
  - Sun Java WTK: https://www.oracle.com/java/technologies/sun-java-wireless-toolkit.html$0
* You need to log in your Oracle account to download

***NOTE:*** *All three SDK are only available on Windows, so if you're using macOS or other operating systems, you should consider using a Virtual Machine with Windows on it*

From there, click the `.exe` file to install all three of the SDK

## Compilation
- Step 1. Clone the repository (if you haven't already)
- Step 2. Navigate to the project's directory in your terminal (Command Prompt), in this case it's `GDonJava/src/`
- Step 3. Compile `GDOnJava.java`
  - 3.1: You can compile using the pre-existing file named `compile.bat`, run this in the Command Prompt and the compiled `GDOnJava.jar` will appear in the current directory (`src/`) (Ensure that you are using Java 2 SDK SE v1.4.2_19, Java ME SDK v3.4 and WTK 2.5.2_01)
  - 3.2: Alternatively you can also type each command individually:
    - 3.2.1: Compile `*.class` file
    > `"C\[your Java 2 SDK directory]\bin\javac.exe" -source 1.3 -target 1.3 -bootclasspath C:\[your WTK directory]\lib\midpapi20.jar;C:\[your WTK directory]\lib\cldcapi11.jar GDOnJava.java`
    - 3.2.2: Preverify `*.class` file
    > `"C:\[your Java ME SDK directory]\bin\preverify.exe" -classpath C:\[your WTK directory]\lib\midpapi20.jar;C:\[your WTK directory]\lib\cldcapi11.jar -d . .`
    - 3.2.3: Package all into a `*.jar` file`
    >  `"C:\[your Java 2 SDK directory]\bin\jar.exe" cvfm GDOnJava.jar MANIFEST.MF *.class assets`
    
## Running
After you successfully compiled the code, you can run it using an emulator like FreeJ2ME or FreeJ2ME-plus, etc

***NOTE:*** *You should limit the emulator's FPS to around 30-60 to play smoothly*

## Controls:
**AS OF NOW, SINCE THERE'S ONLY BUILDS FOR NOKIA ASHA 311, there isn't any controls yet, you can just touch the screen to jump and navigate**

`1`: to change speed (in the main menu) (applied to build 14)

## Cleaning up:
You can delete the project's directory or executable by deleting them in File Explorer or through Command Prompt using `del` and `rmdir`
