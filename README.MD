![j2meDash](https://raw.githubusercontent.com/mnp075075/j2meDash/refs/heads/main/j2meDash_banner.png?token=GHSAT0AAAAAAD34BO56NGM7VXPVKL2TD7DE2Q5U37A)

# j2meDash #
j2meDash is Geometry Dash ported in Java ME (or J2ME) for Java-supported phones

## WARNING
- This project is still experimental and is in its pre-alpha phase.
- I have only built support for Nokia Asha 311 phone model (resolution 240x400). As for other models, I will add them later in the `main` branch.

## Prerequisites
- **Java 1.4 SDK** or **Java 8 SDK** (recommend JDK 1.4.2_19 or JDK 8u202)
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
- if you're using Linux, choose your appropriate format inside the download table from the links above
- if you're using Mac, use a Virtual Machine

From there, click the `.exe` file to install all three of the SDK

## Compilation (NIGHTLY)

### WINDOWS USERS:
- Step 1: Clone the repository (if you haven't already) using `git clone https://github.com/mnp075075/j2meDash`
- Step 2: Navigate to the project's directory in your terminal (Command Prompt), in this case it's `j2meDash/src/` using `cd`
- Step 3: Compile `j2meDash.java`
  - 3.1: You can compile the source code automatically by running `compileJava4.bat` or `compileJava8.bat`, which will generate a file named `j2meDashJava4.jar` or `j2meDashJava8.jar` depending which version you wanted to compile with (Java 1.4/Java 4 or Java 8)
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

### LINUX USERS:
**¯\\\_(ツ)\_/¯ I have no idea**

### MAC USERS:
***Use [VMWare Fusion](https://www.vmware.com/products/desktop-hypervisor/workstation-and-fusion) or [Parallels Desktop](https://www.parallels.com/products/desktop/) to run Windows 11 or Linux in your VM***

## Running
After you successfully compiled the code, there'll be a new file named `j2meDashForJava4.jar` or `j2meDashForJava8.jar`. You can run it using an emulator like FreeJ2ME, FreeJ2ME-plus, etc based on your operating system

<sub>Most emulators aren't reliable at running this `.jar` file so just put it on a J2ME-supported phone</sub>

***NOTE:*** *You should limit the emulator's FPS to around 30-60 to play smoothly*

## Controls:
**AS OF NOW, SINCE THERE'S ONLY BUILDS FOR NOKIA ASHA 311, there isn't any controls yet, you can just touch the screen to jump and navigate**

`1`: to change speed (in the main menu)

`1`: to change flags (in the play screen) (won't work on emulators like freej2me due to compatibility issues)

`2`: to trigger pause screen (in the play screen)

## Cleaning up:
You can delete the project's directory or executable by deleting them in File Explorer or through Command Prompt using `del` and `rmdir`