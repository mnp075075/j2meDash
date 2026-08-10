# j2meDash #
j2meDash is a demake version of Geometry Dash ported to Java ME (or J2ME) for Java-supported phones

## WARNING
- This project is still experimental and is in its: **BETA** phase.
- I have only built support for Nokia Asha 311 phone model (resolution 240x400). As for other models, I will add them later in the `test-resolution-compatibility` branch.

## Prerequisites
- **Java 1.4 SDK** and/or **Java 8 SDK** (recommend JDK 1.4.2_19 and/or JDK 8u202)
- **Java ME SDK** (recommend JDK ME 3.4)
- **Sun Java Wireless Toolkit** (recommend WTK 2.5.2_01)

*All can be obtained on Oracle's official website or by using the links listed below*

**Links:**
  - [Java 1.4 SDK SE](https://www.oracle.com/java/technologies/java-archive-javase-v14-downloads.html)
  - [Java 8 SDK](https://www.oracle.com/asean/java/technologies/javase/javase8-archive-downloads.html)
  - [Java ME SDK](https://www.oracle.com/java/technologies/javame-sdk-downloads.html)
  - [Sun Java WTK](https://www.oracle.com/java/technologies/sun-java-wireless-toolkit.html)

*You need to log in your Oracle account to download*

***NOTE:***

- if you're using Windows, download the `.exe` files
- if you're using Linux, idk
- if you're using Mac, use a Virtual Machine

From there, click the `.exe` file to install all four of the SDK

## Compilation (why)

### WINDOWS USERS:
- Step 1: Clone the repository (if you haven't already) using `git clone https://github.com/mnp075075/j2meDash`
- Step 2: Navigate to the project's directory in your terminal (Command Prompt), in this case it's `j2meDash/src/` using `cd`
- Step 3: Compile `j2meDash.java`
  - 3.1: You can compile the source code automatically by running `compile.bat`:
    - You'll be prompted to input a number corresponding to the version of Java you wanted to compile with
    - `1` for Java 1.4, generating `j2meDash4.jar`
    - `2` for Java 8, generating `j2meDash8.jar`
    - `3` for both versions of Java above
    - ~~`4` for Java 1.4 with addition of Nokia APIs, this requires you to have the `Nokia_API\` folder (which can be downloaded from this repo) in `E:` volume (by creating a new partition) so that the dir would be `E:\Nokia_API\`, generating `j2meDash4+NOKIA.jar`~~ (DO NOT CHOOSE THIS OPTION, READ THE <u>[**WARNING SECTION**](#warning)</u> ABOVE)
  - 3.2: Alternatively you can also type each command individually:
    - 3.2.0: Create a new folder named `classes\` inside the  `src\`  directory by doing it manually or type `mkdir classes` in the Command Prompt (with its dir set to the `src\>`)
    - **FOR JAVA 1.4 or JAVA 4**
      - 3.2a.1: Compile `*.class` file
      > `"C:\[your Java 1.4 SDK directory]\bin\javac.exe" -encoding UTF-8 -source 1.3 -target 1.3 -bootclasspath C:\[your WTK directory]\lib\midpapi20.jar;C:\[your WTK directory]\lib\cldcapi11.jar -d classes *.java`
      - 3.2a.2: Preverify `*.class` file to ensure compatibility with older phones
      > `"C:\[your Jave ME SDK directory]\bin\preverify.exe" -classpath classes;C:\[your WTK directory]\lib\midpapi20.jar;C:\[your WTK directory]\lib\cldcapi11.jar -d classes classes\.`
      - 3.2a.3: Package all into a `*.jar` file
      > `"C:\[your Java 1.4 SDK directory]\bin\jar.exe" cfm j2meDashForJava4.jar META-INF\MANIFEST.MF assets levels -C classes .`
    - **FOR JAVA 8**
      - 3.2b.1: Compile `*.class` file
      > `"C:\path\to\JDK_8\bin\javac.exe" -encoding UTF-8 -classpath "C:\[your Java ME SDK directory]\lib\*" -d classes\ *.java`
      - 3.2b.2: Package all into a `*.jar` file
      >  `"C:\path\to\JDK_8\bin\jar.exe" cfm j2meDashForJava8.jar META-INF\MANIFEST.MF assets levels -C classes/ .`

### LINUX AND ONLINE USERS: **(OUTDATED)**
- i'm being lazy

### MAC USERS:
- download a virtual machine and run Windows on there

## Running
After you successfully compiled the code, there'll be a new file named `j2meDashForJava4.jar` or `j2meDashForJava8.jar`. You can run it using an emulator like FreeJ2ME, FreeJ2ME-plus, etc based on your operating system

<sub>Most emulators aren't reliable at running this `.jar` file so just put it on a J2ME-supported phone</sub>

***NOTE:*** *You should limit the emulator's FPS to around 30-60 to play smoothly*

## Controls:
- will be updated later

## Cleaning up:
- do it yourself
