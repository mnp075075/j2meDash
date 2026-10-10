// --------------- NOTES ----------------
//
// removed these notes
// because I'm publishing this to github
// and i need to keep it normal
//
// ---------------- END -----------------

// the beginning of the source file
// props to freej2me and freej2me-plus for being the emulator testing this app

// this is specifically compiled for java 26 instead of java 1.4
// i have no reason why you would want this
// just in case

// THIS WILL NOT WORK, NO EMULATORS CAN RUN JRE 26 COMPILED APP
// THIS IS MADE AS A JOKE ONLY

/*

 ██████╗ ██████╗  ██████╗ ███╗   ██╗     ██╗ █████╗ ██╗   ██╗ █████╗ ██████╗  ██████╗ 
██╔════╝ ██╔══██╗██╔═══██╗████╗  ██║     ██║██╔══██╗██║   ██║██╔══██╗╚════██╗██╔════╝ 
██║  ███╗██║  ██║██║   ██║██╔██╗ ██║     ██║███████║██║   ██║███████║ █████╔╝███████╗ 
██║   ██║██║  ██║██║   ██║██║╚██╗██║██   ██║██╔══██║╚██╗ ██╔╝██╔══██║██╔═══╝ ██╔═══██╗
╚██████╔╝██████╔╝╚██████╔╝██║ ╚████║╚█████╔╝██║  ██║ ╚████╔╝ ██║  ██║███████╗╚██████╔╝
 ╚═════╝ ╚═════╝  ╚═════╝ ╚═╝  ╚═══╝ ╚════╝ ╚═╝  ╚═╝  ╚═══╝  ╚═╝  ╚═╝╚══════╝ ╚═════╝ 

*/

// IMPORT ALL THOSE THINGS
import javax.microedition.midlet.*; // the midlet, required to compile a j2me application
import javax.microedition.lcdui.*; // basically the ui, things like Forms, Alert, List, and also Canvas
import javax.microedition.lcdui.game.*; // a gaming version of Canvas that is GameCanvas
import javax.microedition.media.*; // the way to play sound
import javax.microedition.media.control.*; // the way to control sound effectively
import java.util.Timer; // a timer, literally
import java.util.TimerTask; // an addon to the timer package, mainly controlling it
import java.util.Random; // random package, to generate random values of such
// import javax.microediton.io.*;
// import javax.microedition.lcdui.TextBox;
// import javax.microedition.lcdui.TextField;
import java.io.*; // basically input and output, self-explanatory

// THE LLMs, IN A J2ME APP, VERY CURSED
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;

// THE BRAIN OF THE PROJECT
public class GDOnJavaForJava26 extends MIDlet implements CommandListener { 

	// DEFINING EVERYTHING
	public static boolean SoundEnabled; // deprecated, used to control sound
	public static boolean haventOpened; // for the splash screen class, no idea for its name
	public static int fadeforward; // also for the splash screen class
	public static int seconds = 5; // once again also for the splash screen class
	public static int time = 0; // used for the new timer screen class to count time
	public static int speedCount = 0; // the speed count, mandatory for controlling speed

	// binary level parser value (useless on its own, they're used for the loops)
	public static int id;
	public static int x1;
	public static int x2;
	public static int x3;
	public static int y1;
	public static int y2;
	public static int y3;
	public static int par;

	// below are variables for the parser
	// do not touch
	// these are very important for loading and parsing

	// csv parser arrays
	public String[] details = new String[40]; // details of an object
	public String[] xCoord = new String[40]; // its x coordinate in sheet.png
	public String[] yCoord = new String[40]; // its y coordinate in sheet.png
	public String[] width = new String[40]; // the object's width
	public String[] height = new String[40]; // the object's height
	public String[] hitboxX = new String[40]; // the object's hitbox's x coordinate in sheet.png && the object itself (separated by a period ".")
	public String[] hitboxY = new String[40]; // the object's hitbox's y coordinate in sheet.png && the object itself (separated by a period ".")
	public String[] hitboxW = new String[40]; // the object's hitbox's width
	public String[] hitboxH = new String[40]; // the object's hitbox's height

	// binary level parser arrays
	// you can change the value of the amount of bytes if you want
	// probably between: 8192 - 2097152 (2^13 >= x >= 2^21) bytes
	// i choose 1048576 (1mb or 1024kb - technically kibibytes and mebibytes) because you can store up to >130000 objects
	
	public byte[] data = new byte[1048576];
	
	/* SPEED COUNT:
	 * 0.5x speed: 1
	 * 1x speed: 0
	 * 2x speed: 2
	 * 3x speed: 3
	 * 4x speed: 4
	 */

	// that board above is for speed count to manage speed and not overflow them
			 
	// public static volatile boolean paused;
	public static volatile boolean running = true; // required
	private Display display; // to control the display

	// the menus and screens
	private MainMenu mainMenu;
	private AboutMenu aboutMenu;
	private ExitMenu exitMenu;
	private DebugMenu DONOTOPENMenu;
	private SoundMenu soundMenu;
	private SplashScreen splashScreen;
	private WarningScreen warningScreen;

	// SIDENOTE:
	private TimerScreen timerScreen;
	// remember that TimerScreen isn't being used by any code so it's useless
	private NewTimerScreen newTimerScreen;
	// NewTimerScreen will be used instead
	private PlayScreen playScreen;
	private NewPlayScreen newPlayScreen;

	// the parsers
	private DataRegistry dataRegistry;
	private LevelBinaryParser levelBinaryParser;

	// the Forms (and once Alerts, and other high-level UI elements from Java ME)
	private Form soundForm;
	private Form exitForm;
	private Form speedForm; // this one is still in use

	// the commands
	// most are them are deprecated except the ones inside the speedForm menu
	private Command exitCommand = new Command("rage quit", Command.EXIT, 1);
	private Command yesCommand = new Command("no yes", Command.OK, 1);
	private Command noCommand = new Command("yes no", Command.CANCEL, 1);
	private Command mainBS = new Command("main bullsh*t", Command.OK, 1);
	
	// speedForm commands
	private Command half_times_speed = new Command("0.5x speed", Command.OK, 1);
	private Command one_time_speed = new Command("1x speed", Command.OK, 1);
	private Command two_times_speed = new Command("2x speed", Command.OK, 1);
	private Command three_times_speed = new Command("3x speed", Command.OK, 1);
	private Command four_times_speed = new Command("4x speed", Command.OK, 1);
	private Command exit = new Command("Exit back to mainMenu", Command.EXIT, 1);
	// speed for real, yup
	
	// the music players (deprecated due to being hard to control)
	private Player bgMusic;
	private Player normalMusic;

	// THE MAIN MENU
	class MainMenu extends GameCanvas {
		Image FIREINTHEHOLE;
		Image background;
		Image gdlogo;
		
		// IMAGE AND AUDIO FOR MAIN MENU
		// ALSO ISN'T A CLASS
		public MainMenu() { // the constructor
			super(true); // REQUIRED
		
			// IMAGE
			try {
				FIREINTHEHOLE = Image.createImage("assets/cubeRotate0.png");
				background = Image.createImage("assets/background.png");
				gdlogo = Image.createImage("assets/gdlogo.png");
				SpeedForm speedForm = new SpeedForm();
			} catch (Exception e) {
				e.printStackTrace();
			}

			
		}

		// GUI FOR MAIN MENU
		public void paint(Graphics g) { // the gui, basically painting stuff to the screen
			
			Font font1 = Font.getFont(Font.FACE_MONOSPACE, Font.STYLE_ITALIC, Font.SIZE_LARGE);
			Font font2 = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_MEDIUM);
			if (background != null) {
				g.drawImage(background, 0, 0, Graphics.LEFT | Graphics.TOP);
			}
			
			g.setFont(font1);
			g.setColor(0,0,0);
			g.drawString("top left", 0, 0, Graphics.LEFT | Graphics.TOP);
			g.drawString("top right", 239, 0, Graphics.RIGHT | Graphics.TOP);
			g.drawString("bottom left", 0, 399, Graphics.LEFT | Graphics.BOTTOM);
			g.drawString("bottom right", 239, 399, Graphics.RIGHT | Graphics.BOTTOM);
			g.drawString("center", 120, 200, Graphics.HCENTER | Graphics.VCENTER);
			
			g.drawLine(0, 200, 239, 200);
			g.drawLine(0, 202, 239, 202);
			g.drawLine(0, 204, 239, 204);
			
			g.drawRect(0, 100, 10, 10);
			g.drawRect(50, 100, 10, 10);
			g.fillRect(0, 50, 10, 10);
			
			g.setColor(73, 201, 142);
			g.drawArc(0, 20, 10, 10, 0, 360);
			
			g.setColor(255, 255, 255);
			
			
			if (FIREINTHEHOLE != null) {
				g.drawImage(FIREINTHEHOLE, 100, 100, Graphics.HCENTER | Graphics.VCENTER);
			} 
			
			if (gdlogo != null) {
				g.drawImage(gdlogo, 120, 100, Graphics.HCENTER | Graphics.VCENTER);
			}
			
			g.setColor(0, 0, 0);
			g.setFont(font2);
			g.fillRect(100, 300, 40, 30);
			g.setColor(255, 255, 255);
			g.drawString("Exit", 120, 315, Graphics.HCENTER | Graphics.VCENTER);

			g.setColor(0,0,0);
			g.fillRect(100, 340, 40, 30);
			g.setColor(255,255,255);
			g.drawString("Sound", 120, 355, Graphics.HCENTER | Graphics.VCENTER);
			
			g.setColor(0,0,0);
			g.fillRect(100, 50, 40, 30);
			g.setColor(255,255,255);
			g.drawString("Timer", 120, 75, Graphics.HCENTER | Graphics.VCENTER);
			
			g.setColor(0,0,0);
			g.fillRect(100, 260, 40, 30);
			g.setColor(255,255,255);
			g.drawString("Play", 120, 275, Graphics.HCENTER | Graphics.VCENTER);
			
			g.setColor(0,0,0);
			g.fillRect(100, 220, 40, 30);
			g.setColor(255,255,255);
			g.drawString("About", 120, 235, Graphics.HCENTER | Graphics.VCENTER);
			
			// g.fillRect(0,385,200,400);

		}

		// BUTTONS FOR MAIN MENU
		protected void pointerPressed(int x, int y) { 
		// a way to detect input for touchscreens, as for classic phones that use other command
		
			if (x >= 100 && x <= 140 && y >= 300 && y <= 330) {
				display.setCurrent(exitMenu);
				exitMenu.repaint();
							
			} else if (x >= 100 && x <= 140 && y >= 340 && y <= 370) {
			
				display.setCurrent(soundMenu);
				soundMenu.repaint();
				
			} else if (x >= 100 && x <= 140 && y >= 50 && y <= 80) {
				
				display.setCurrent(newTimerScreen);
				newTimerScreen.repaint();
			
			} else if (x >= 100 && x <= 140 && y >= 260 && y <= 290) {
				
				display.setCurrent(playScreen);
				
			} else if (x >= 100 && x <= 140 && y >= 220 && y <= 250) {
				
				display.setCurrent(aboutMenu);
				
			}
		
		}
		
		protected void keyPressed(int keyCode) { 
		// this is also to detect input from key presses, not suitable for touchscreen ones
			if (keyCode == KEY_NUM1) {
				display.setCurrent(speedForm);
				repaint();
			}
		}
		
		// THREAD THREATENING PEOPLE 
		// too bad I deleted it
		// there used to be a thread here but it's quite useless so I removed it

	}
	
	// ABOUT MENU
	class AboutMenu extends GameCanvas {

	/* 
	 * the aboutMenu
	 * aboutMenu itself just tells the information about the game and its original creator
	 * shoutout to RobTop Games, better known as Robert Topala for originally making:
	 * GEOMETRY DASH (for PC and Mobile)
	 * also J2ME helped me make this project possible
	 */
		
		public AboutMenu() {
			super(true); // REQUIRED
		}
	
		// GUI FOR ABOUT MENU
		public void paint(Graphics g) {
			
			g.setColor(0,173,119);
			g.fillRect(0, 0, 240, 400);
			g.setColor(255,255,255);
			g.drawString("About Us", 10, 30, Graphics.LEFT | Graphics.TOP);
			g.drawString("This game is inspired from:", 10, 60, Graphics.LEFT | Graphics.TOP);
			g.drawString("Geometry Dash", 10, 75, Graphics.LEFT | Graphics.TOP );
			g.drawString("Which is originally made by:", 10, 90, Graphics.LEFT | Graphics.TOP);
			g.drawString("RobTop Games AB", 10, 105, Graphics.LEFT | Graphics.TOP);
			g.drawString("This game is made possible using J2ME", 10, 135, Graphics.LEFT | Graphics.TOP);
			g.drawString("Java and Java Micro Edition are", 10, 150, Graphics.LEFT | Graphics.TOP);
			g.drawString("property of: Oracle Corporation", 10, 175, Graphics.LEFT | Graphics.TOP);
			g.drawString("and Sun Microsystems", 10, 190, Graphics.LEFT | Graphics.TOP);

			g.setColor(0,85,85);
			g.drawRect(99, 249, 41, 31);
			g.setColor(0, 206, 119);
			g.fillRect(100, 250, 40, 30);
			g.setColor(255,255,255);
			g.drawString("OK", 120, 265, Graphics.HCENTER | Graphics.VCENTER);
		
		}
		
		// BUTTONS FOR ABOUT MENU
		protected void pointerPressed(int x, int y) {
			
			if (x >= 100 && x <= 140 && y >= 250 && y <= 280) {
				
				display.setCurrent(mainMenu);
				mainMenu.repaint();
				
			}
			
		}
		
	}
	
	// EXIT MENU
	class ExitMenu extends GameCanvas {

	/*
	 * the exitMenu just do what it said
	 * confirms the user if they want to exit the game
	 */
		
		Image EXIT; // NORMAL IMAGE
		
		public ExitMenu() {
			super(true); // REQUIRED
			
			// IMAGE AND AUDIO FOR EXIT MENU
			try {
				EXIT = Image.createImage("assets/cubeRotate0.png");
			} catch (Exception e) {
				e.printStackTrace();
				System.err.println("NO IMAGE NAMED cubeRotate0.png (perhaps you forgot .png isn't .PNG)");
			}
		}
	
	// THAT EXIT MENU LITERALLY DO NOTHING
	// not anymore

		// GUI FOR EXIT MENU
		public void paint(Graphics g) {

			g.setColor(0,173,119);
			g.fillRect(0, 0, 240, 400);
			g.setColor(255,255,255);
			g.drawString("Do you want to exit?", 120, 30, Graphics.VCENTER | Graphics.HCENTER);
			g.drawString("SIDE NOTE:", 120, 60, Graphics.VCENTER | Graphics.HCENTER);
			g.drawString("Rage quiting is not my problem", 10, 75, Graphics.LEFT | Graphics.TOP);
			g.drawString("The issue here is that:", 10, 90, Graphics.LEFT | Graphics.TOP);
			g.drawString("### You're not skilled enough ###", 10, 105, Graphics.LEFT | Graphics.TOP);
			g.drawString("You can try the original Geometry", 10, 120, Graphics.LEFT | Graphics.TOP);
			g.drawString("Dash made by RobTop AB. There are", 10, 135, Graphics.LEFT | Graphics.TOP);
			g.drawString("main levels for you to try. Maybe", 10, 150, Graphics.LEFT | Graphics.TOP);
			g.drawString("try Stereo Madness and onwards,", 10, 165, Graphics.LEFT | Graphics.TOP);
			g.drawString("and you'll eventually leveled up!", 10, 180, Graphics.LEFT | Graphics.TOP);
			g.drawString("Nonetheless, hope you the best!", 10, 210, Graphics.LEFT | Graphics.TOP);
			g.drawString("Sincerely, idk", 10, 225, Graphics.LEFT | Graphics.TOP);
			g.setColor(0,206,119);
			g.fillRect(40, 255, 40, 20);
			g.fillRect(160, 255, 40, 20);
			g.setColor(255,255,255);
			g.drawString("Exit", 60, 265, Graphics.HCENTER | Graphics.VCENTER);
			g.drawString("Cancel", 180, 265, Graphics.HCENTER | Graphics.VCENTER);
			g.setColor(0,173,119);
			
			if (EXIT != null) {
				g.drawImage(EXIT, 120, 310, Graphics.HCENTER | Graphics.VCENTER);
			} else {
				g.drawString("???", 120, 310, Graphics.HCENTER | Graphics.VCENTER);
			}

		}
			
		// BUTTONS FOR EXIT MENU
		protected void pointerPressed(int x, int y) {
				
			if (x >= 40 && x <= 80 && y >= 255 && y <= 275) {
				destroyApp(true);
				notifyDestroyed();
			} else if (x >=  160 && x <= 200 && y >= 255 && y <= 275) {
				display.setCurrent(mainMenu);
				mainMenu.repaint();
			}
			
		}
	}
	
	// DEBUG MENU (HIDDEN)
	class DebugMenu extends GameCanvas {

		// this is originally made as a joke and also you can't leave once inside
	
		public DebugMenu() {
			super(true); // REQUIRED
		}
		
		// GUI FOR DEBUG MENU
		// YOU GET STUCK IN HERE
		public void paint(Graphics g) {
			g.drawString("Debug menu don't exist", 20, 20, Graphics.LEFT | Graphics.TOP);
			g.drawString("also you're stucked", 20, 50, Graphics.LEFT | Graphics.TOP);
		}
	// NO ESCAPE IRL

	}
	
	// SOUND MENU
	class SoundMenu extends GameCanvas {

		/*
		 * soundMenu is pretty much dead for now
		 * because I'm currently not using audio for my game
		 * audio controlling in j2me is especially hard
		 * and i don't know why and i'm not stepping in now
		 */
		
		public SoundMenu() {
			super(true); // REQUIRED
			
			try {
				if (SoundEnabled == true) {
					bgMusic.start();
				} else {
					bgMusic.stop();
				}
			} catch (Exception e) {
				// System.err.println("qasaas");
			}
			
		}
	
		// GUI FOR SOUND MENU
		public void paint(Graphics g) {
			
			g.setColor(0,173,119);
			g.fillRect(0, 0, 240, 400);
			g.setColor(255,255,255);
			g.drawString("Do you want to have sound?", 120, 30, Graphics.VCENTER | Graphics.HCENTER);
			g.drawString("FUN FACT:", 120, 60, Graphics.VCENTER | Graphics.HCENTER);
			g.drawString("Robert Topala is the creator of", 10, 75, Graphics.LEFT | Graphics.TOP);
			g.drawString("Geometry Dash which is made using", 10, 90, Graphics.LEFT | Graphics.TOP);
			g.drawString("Cocos-2dx, a good game engine tbh", 10, 105, Graphics.LEFT | Graphics.TOP);
			g.drawString("Also go support RobTop Games", 10, 120, Graphics.LEFT | Graphics.TOP);
			g.drawString("on YouTube, Discord and Twitter", 10, 135, Graphics.LEFT | Graphics.TOP);
			g.drawString("He makes a good game and a good", 10, 150, Graphics.LEFT | Graphics.TOP);
			g.drawString("community and he is a single dev", 10, 165, Graphics.LEFT | Graphics.TOP);
			g.drawString("so great job on RobTop on making GD!", 10, 180, Graphics.LEFT | Graphics.TOP);
			g.drawString("Copyright Information:", 10, 210, Graphics.LEFT | Graphics.TOP);
			g.drawString("YouTube is a property of Google Inc.", 10, 225, Graphics.LEFT | Graphics.TOP);
			g.drawString("Discord is a property of Discord Inc.", 10, 240, Graphics.LEFT | Graphics.TOP);
			g.drawString("Twitter is a property of X Corp.", 10, 255, Graphics.LEFT | Graphics.TOP);
			g.drawString("So do you want sound?", 120, 285, Graphics.VCENTER | Graphics.HCENTER);
			g.setColor(0,206,119);
			g.fillRect(40, 310, 40, 20);
			g.fillRect(160, 310, 40, 20);
			g.setColor(255,255,255);
			g.drawString("Yes", 60, 320, Graphics.VCENTER | Graphics.HCENTER);
			g.drawString("No", 180, 320, Graphics.VCENTER | Graphics.HCENTER);
			
		}

		// BUTTONS FOR SOUND MENU
		protected void pointerPressed (int x, int y) {
			
			// timerScreen = new TimerScreen();
			
			if (x >= 40 && x <= 80 && y >= 310 && y <= 330) {
				
				SoundEnabled = true;
				// display.setCurrent(timerScreen);
				// timerScreen.repaint();
				display.setCurrent(mainMenu);
				mainMenu.repaint();
				System.err.println("IT WORKS");
				
				// NORMAL LOOKING LOOP
				/* for (int i = 0; i < 5; i++) {
					System.err.println("loop: " + i);
				} */
				// I have to turn this loop off to optimize performance

			} else if (x >= 160 && x <= 200 && y >= 310 && y <= 330) {
				
				SoundEnabled = false;
				//display.setCurrent(timerScreen);
				//timerScreen.repaint();
				display.setCurrent(mainMenu);
				mainMenu.repaint();
				System.err.println("IT ALSO WORKS");
			}
				
		}
	}
	
	// SPLASH SCREEN - MAR 22 2026
	// equivalent to jumpscaring people
	// WARNING:
	// the following code contains abrupt splash and may cause epilesy
	// if you or your love ones have epilesy, please quit immediately
	// and also this contains a 3:5 strectched image of Robert Topala
	// YOU HAVE BEEN WARNED
	
	// splash screen 
	class SplashScreen extends GameCanvas {
	
		Image robert_topala; // jumpscare element
		
		// constructor
		public SplashScreen() {
			super(true);
			new Exception("TRACE").printStackTrace();
			System.err.print("GEOMETRY DASH IS THE BEST GAME ON EARTH");
			
			try {
				robert_topala = Image.createImage("assets/roberttopala.png");
			} catch (Exception e) {
				System.err.println();
			}
			
		}	
		
		// thread
		public void THREAD() {
			new Thread(new Runnable() {
				public void run() {
					
					Graphics g = getGraphics();
					
					for (int fadeforward = 0; fadeforward < 255; fadeforward++) {
		
						g.setColor(fadeforward,fadeforward,fadeforward);
						g.fillRect(0,0,240,400);
						flushGraphics();
						g.drawImage(robert_topala, 0, 0, Graphics.LEFT | Graphics.TOP);
						flushGraphics();
				
						try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
							System.err.println("nah");
						}
					}
					
					display.setCurrent(soundMenu);
				}
			}).start();
		}
			
				

		// graphics are not useful here
		// this part is useless since thread already handled everything
		// that includes Graphics g and thread.sleep
		
		// graphics again
		public void paint(Graphics g) {
			// nothing is in here
			// no use case
		}
			
	}
	
	
	// WARNING SCREEN
	// the screen that just warns you
	
	/*
	i want to say that i have no idea what to do with this code
	you can guess its use case
	i make the warningScreen because of the things they are going to expect
	that's very bad gameplay and a rapidly splashing image of Robert Topala stretched
	and everybody knows that's scary
	*/
	
	class WarningScreen extends GameCanvas {
		
		Image warning;
		Graphics g = getGraphics();
		
		public WarningScreen() {
			super(true);
			try {
				warning = Image.createImage("assets/warning.png");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		public void paint(Graphics g) {
			
			g.setColor(0,0,0);
			g.fillRect(0,0,240,400);
			
			if (warning != null) {
				g.drawImage(warning, 120, 100, Graphics.HCENTER | Graphics.VCENTER);
			}
			
			g.setColor(255,255,255);
			g.drawString("Warning: This game is", 120, 200, Graphics.HCENTER | Graphics.VCENTER);
			g.drawString("bad on purpose but who cares", 120, 215, Graphics.HCENTER | Graphics.VCENTER);
			g.drawString("you have been warned", 120, 230, Graphics.HCENTER | Graphics.VCENTER);
			
			
			while (seconds > 0) {
				g.setColor(0,0,0);
				g.fillRect(0,235,240,250);
				g.setColor(255,255,255);
				g.drawString("this warning will close in: " + seconds, 120, 245, Graphics.HCENTER | Graphics.VCENTER);
				flushGraphics();
				haventOpened = true;
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					System.out.println("nah");
				}
				seconds--;
			}
			
			if (seconds == 0) {
				if (haventOpened == true) {
					splashScreen = new SplashScreen();
					display.setCurrent(splashScreen);
					splashScreen.THREAD();
					haventOpened = false;
				} else {
					// nothing
				}
			}
			
			
			
		}
	
	}
	// SOUND FORM (DEPRECATED)
	class SoundForm extends Form {
	
		public SoundForm() {
		
			super("# SOUNDWAVE #");
			
			append("do you want sound?");
			
			addCommand(yesCommand);
			addCommand(noCommand);
			addCommand(mainBS);
			
			setCommandListener(GDOnJavaForJava26.this);
			
		}
		
	}
	
	// EXIT FORM (DEPRECATED)
	class ExitForm extends Form {
	
		public ExitForm() {
		
			super("DUDE RAGEQUIT");
			
			append("need tutorial?");
			
			addCommand(exitCommand);
			addCommand(mainBS);
			
			setCommandListener(GDOnJavaForJava26.this);
			
		}
		
	}
	
	// new timer screen - april 23rd
	
	/* 
	 * this timer screen is quite noteworthy despite its usage rendered useless
	 * the timer itself just count how much time you spent inside the screen
	 * which is quite useless and doesn't serve any help to the users
	 * nonetheless I still made this
	 * and unless you want to optimize code, you may leave this here
	 */

	class NewTimerScreen extends GameCanvas implements Runnable {
	
		Image background1;
		
		public NewTimerScreen() {
			super(true);
			try {
				background1 = Image.createImage("assets/background.png");
			} catch (Exception e) {
				// nothing
			}
		}
		
		private int secondCount = 0;
		private int minuteCount = 0;
		private int hourCount = 0;
		private int dayCount = 0;
		private int weekCount = 0;
		private int fortnightCount = 0;
		private int doubleFortnightCount = 0;
		private int quadrupleFortnightCount = 0;
		private int octupleFortnightCount = 0;
		private int sexdecupleFortnightCount = 0;
		private int duotrigintupleFortnightCount = 0;
		private int quattorsexagintupleFortnightCount = 0;
		private int octoviginticentupleFortnightCount = 0;
		private int sexquinquagintaducentupleFortnightCount = 0;
		/* private int duodecimquingentupleFortnightCount = 0;
		 * private int quattuorvigintimillupleFortnightCount = 0;
		 * private int octoquadragintamiliadoubleFortnightCount = 0;
		 * private int sexnonagintamiliaquadrupleFortnightCount = 0;
		 * private int duononagintacentummiliaoctupleFortnightCount = 0;
		 * private int quattuoroctogintatrecentimiliasedecupleFortnightCount = 0;
		 * private int octosexagintaseptingentimiliaduotrigintupleFortnightCount = 0;
		 * private int sextrigintatrecentimiliaquinquesexagintupleFortnightCount = 0;
		 * private int sexnonagintasexcentimiliaduodequadragintatrecentamiliacentenamiliesquadrupleFortnightCount = 0;
		 */ 
		private volatile boolean isRunning;
		private Thread t;
		
		// very important
		public void showNotify() {
			isRunning = true;
			t = new Thread(this);
			t.start();
		}
		
		// as important as above
		public void hideNotify() {
			isRunning = false;
			t = null;
		}
		
		public void run() {
			
			Graphics g = getGraphics();

			while (isRunning == true) {
			
				Font font3 = Font.getFont(Font.FACE_MONOSPACE, Font.STYLE_PLAIN, Font.SIZE_SMALL);
				
				// this is so weird
				// who would have thought to code a timer inside a game :skull_emoji:
				
				g.setFont(font3);
				g.drawImage(background1,0,0,Graphics.LEFT | Graphics.TOP);
				g.setColor(0,0,0);
				g.fillRect(0,300,100,50);
				g.setColor(255,255,255);
				g.fillRect(0,0,240,160);
				g.setColor(0,0,0);
				g.drawString("Time spent in newTimerScreen:", 0, 0, Graphics.LEFT | Graphics.TOP);
				g.drawString(secondCount + " second (s)", 0, 10, Graphics.LEFT | Graphics.TOP);
				g.drawString(minuteCount + " minute (s)", 0, 20, Graphics.LEFT | Graphics.TOP);
				g.drawString(hourCount + " hour (s)", 0, 30, Graphics.LEFT | Graphics.TOP);
				g.drawString(dayCount + " day (s)", 0, 40, Graphics.LEFT | Graphics.TOP);
				g.drawString(weekCount + " week (s)", 0, 50, Graphics.LEFT | Graphics.TOP);
				g.drawString(fortnightCount + " fortnight (s)", 0, 60, Graphics.LEFT | Graphics.TOP);	
				g.drawString(doubleFortnightCount + " 2 fortnight (s)", 0 ,70, Graphics.LEFT | Graphics.TOP);
				g.drawString(quadrupleFortnightCount + " 4 fortnight (s)", 0, 80, Graphics.LEFT | Graphics.TOP);
				g.drawString(octupleFortnightCount + " 8 fortnight (s)", 0, 90, Graphics.LEFT | Graphics.TOP);
				g.drawString(sexdecupleFortnightCount + " 16 fortnight (s)", 0, 100, Graphics.LEFT | Graphics.TOP);
				g.drawString(duotrigintupleFortnightCount + " 32 fortnight (s)", 0, 110, Graphics.LEFT | Graphics.TOP);
				g.drawString(quattorsexagintupleFortnightCount + " 64 fortnight (s)", 0, 120, Graphics.LEFT | Graphics.TOP);
				g.drawString(octoviginticentupleFortnightCount + " 128 fortnight (s)", 0, 130, Graphics.LEFT | Graphics.TOP);
				g.drawString(sexquinquagintaducentupleFortnightCount + " 256 fortnight (s)", 0, 140, Graphics.LEFT | Graphics.TOP);
				
				/* you can add the other one if you want to
				
				* too tired for this
				
				*/
				
				flushGraphics();
				
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					break;
				}
				
				// VERY IMPORTANT
				if (!isRunning) {
					break;
				}
				
				secondCount++;
				
				if (secondCount % 60 == 0) {
					secondCount = 0;
					minuteCount++;
				
					if (minuteCount % 60 == 0) {
						minuteCount = 0;
						hourCount++;

						if (hourCount % 24 == 0) {
							hourCount = 0;
							dayCount++;
				
							if (dayCount % 7 == 0) {
								dayCount = 0;
								weekCount++;
				
								if (weekCount % 2 == 0) {
									weekCount = 0;
									fortnightCount++;
								
									if (fortnightCount % 2 == 0) {
										fortnightCount = 0;
										doubleFortnightCount++;
										
										if (doubleFortnightCount % 2 == 0) {
											doubleFortnightCount = 0;
											quadrupleFortnightCount++;
											
											if (quadrupleFortnightCount % 2 == 0) {
												quadrupleFortnightCount = 0;
												octupleFortnightCount++;
												
												if (octupleFortnightCount % 2 == 0) {
													octupleFortnightCount = 0;
													sexdecupleFortnightCount++;
													
													if (sexdecupleFortnightCount % 2 == 0) {
														sexdecupleFortnightCount = 0;
														duotrigintupleFortnightCount++;
														
														if (duotrigintupleFortnightCount % 2 == 0) {
															duotrigintupleFortnightCount = 0;
															quattorsexagintupleFortnightCount++;
															
															if (quattorsexagintupleFortnightCount % 2 == 0) {
																quattorsexagintupleFortnightCount = 0;
																octoviginticentupleFortnightCount++;
																
																if (octoviginticentupleFortnightCount % 2 == 0) {
																	octoviginticentupleFortnightCount = 0;
																	sexquinquagintaducentupleFortnightCount++;
																	
																	if (sexquinquagintaducentupleFortnightCount % 2 == 0) {
																		g.setColor(0,0,0);
																		g.drawString("CONGRATS USER, YOU HAVE WASTED TIME", 120, 200, Graphics.HCENTER | Graphics.VCENTER);
																		
																		flushGraphics();
																		
																		try {
																			Thread.sleep(5000);
																		} catch (InterruptedException e) {
																			// nothing
																		}
																		
																		destroyApp(true);
																		notifyDestroyed();
																		System.out.println("go outside dude");
																	}
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}	
			}
		}
		
		protected void pointerPressed(int x, int y) {
			
			if (x >= 0 && x <= 100 && y >= 300 && y <= 350) {
				isRunning = false;
				display.setCurrent(mainMenu);
				mainMenu.repaint();
			}
			
		}
			
			// timer.schedule(task, 0, 1000);
			
	}
	
	// There used to be a Player command here but now it's gone
	// NOW THE CONSTRUCTOR HANDLED AUDIO
	
	// supposedly this would be a timer for debug but i failed
	// this is unused and will not be triggered
	// so yeah too bad i guess
	
	// old timer screen - april 21st
	class TimerScreen extends GameCanvas {
		public TimerScreen() {
			super(true);
		}
		
		public void paint(Graphics g) {
			long lastTime = System.currentTimeMillis();
			
			while (running == true) {
				
				g.setColor(255,255,255);
				g.fillRect(0,0,240,400);
			
				g.setColor(0,0,0);
				g.drawString("TIMER", 0,0, Graphics.LEFT | Graphics.TOP);
				
				long now = System.currentTimeMillis();
				long workTime = now - lastTime;
				long sleepTime = now - workTime;
				
				g.setColor(255,255,255);
				g.fillRect(0,385,200,400);
				flushGraphics();
				
				g.setColor(0,0,0);
				g.drawString("Time spent in MainMenu: ", 0, 400, Graphics.LEFT | Graphics.BOTTOM);
				g.drawString("" + time, 160, 400, Graphics.LEFT | Graphics.BOTTOM);
				flushGraphics();
				
				if (sleepTime > 0) {
					try {
						Thread.sleep(sleepTime);
					} catch (InterruptedException e) {
						break;
					}
				} else {
					// nothing
				}
				
				time++;
				g.setColor(255,255,255);
				g.fillRect(0,385,200,400);
				repaint();
				serviceRepaints();
			}
		}
	}
	// THAT CODE ABOVE IS DEPRECATED
	
	class SpeedForm extends Form {
		
		public SpeedForm() {
		
			super("what speed");
			
			append("select speed?");
			
			addCommand(half_times_speed);
			addCommand(one_time_speed);
			addCommand(two_times_speed);
			addCommand(three_times_speed);
			addCommand(four_times_speed);
			addCommand(exit);
			
			setCommandListener(GDOnJavaForJava26.this);
			
		}
		
	}
	
	// PLAY SCREEN - APRIL 25TH
	
	class PlayScreen extends GameCanvas implements Runnable {

	/* this is the main playScreen where you can play levels
	 * it's still quite barebones so you may consider improving this
	 * i'm trying to make it better
	 * but for now it's just a loop of a cube and spikes
	 */
		
		private int jumpFrame = 0;
		private int jumpHeight = 0;
		
		Image background;
		Image foreground;

		Image[] objectID = new Image[37];
		Image spike1_0;

		Image[] cubeRotatePositive = new Image[10];
		Image[] cubeRotateNegative = new Image[10];
		Image cubeRotate0;
		Image sheet;

		TextBox dirLoader;

		// Sprite cube = new Sprite(cubeRotate[0]);
		int iForCube = 21;
		int jForForeground = 240;
		int kForSpikeX = 240;
		int kForSpikeY = 287;
		int factor = 10;
		int parabola[] = {4,3,2,1}; // {-1,-4,-9,-16};
		
		int cubeW = 21;
		int cubeH = 21;
		
		int spikeW = 3;
		int spikeH = 7;
		int jumpTick = 0;

		boolean varNormalGravity;
		boolean varNormalMode;

		byte flags = 0x11;

		// boolean mirrorMode = false;

		int[][] widthAndHeight = { 	{0,0,0,0}, {21,21,21,21}, {21,21,21,21}, {21,21,21,21},
									{21,21,21,21}, {21,21,3,7}, {21,21,7,3}, {21,21,3,7},
									{21,21,7,3}, {21,21,3,2}, {21,21,2,3}, {21,21,3,2},
									{21,21,2,3}, {34,60,34,60}, {40,50,40,50}, {54,60,54,60},
									{69,60,69,60}, {73,60,73,60}, {32,60,32,60}, {32,60,32,60},
									{32,60,32,60}, {32,60,32,60}, {32,60,32,60}, {32,60,32,60},
									{32,60,32,60}, {30,60,30,60}, {30,60,30,60}, {32,60,32,60},
									{32,60,32,60}, {21,21,21,21}, {21,21,21,21}, {21,21,21,21},
									{21,21,21,21}, {21,21,21,21}, {21,21,21,21}, {21,4,21,4},
									{21,3,21,3}, {21,5,21,5}, {21,5,21,5}, {21,21,21,21},
									{21,21,21,21}	};
		
		private DataRegistry dataRegistry;

		private Thread t1;
		private volatile boolean isRunning = true;
		private volatile boolean isJumpingOnX;
		private volatile boolean isJumpingOnY;
		// private long lastTime = System.currentTimeMillis();
		private int tickCount = 0;
		
		public PlayScreen() {
			
			super(true);
				
			dataRegistry = new DataRegistry();
			levelBinaryParser = new LevelBinaryParser();
			
			try {
				
				
				background = Image.createImage("assets/background.png");
				foreground = Image.createImage("assets/foreground.png");

				/* for (int i = 0; i < 40; i++) {
					String currentPath = dataRegistry.directories[i];

					if (currentPath == null || currentPath.equals("null") || currentPath.length() == 0) {
						System.out.println("skip ID: " + i + " blank");
						continue;
					} 
					
					try {
						objectID[i] = Image.createImage(currentPath);
						System.out.println("image ID: " + i + " loaded");
					} catch (Exception e) {
						System.out.println("image ID: " + i + " not loaded");
					}
				} */

				spike1_0 = Image.createImage("assets/spike1_0.png");
				sheet = Image.createImage("assets/sheet.png");
				cubeRotate0 = Image.createImage("assets/cubeRotate0.png");
				
				for (int i = 1; i <= 8; i++) {
					cubeRotatePositive[i] = Image.createImage("assets/cubeRotate" + i + ".png"); // rotate clockwise
				}

				for (int i = 1; i <= 8; i++) {
					cubeRotateNegative[i] = Image.createImage("assets/cubeRotate" + i + ".png"); // rotate counterclockwise
				}
				
			} catch (Exception e) {

				e.printStackTrace();
				
			}
			
		}

		
		
		public void showNotify() {
			isRunning = true;
			t1 = new Thread(this);
			t1.start();
		}
		
		public void hideNotify() {
			isRunning = false;
			t1 = null;
		}
		
		public void run() {
			
			// Graphics graphics = getGraphics();
			
			System.out.println("PlayScreen is activated");
			// System.out.println("Running in: " + Thread.currentThread().getName());

			// this is important
			/* SPEED COUNT:
			 * 0.5x speed: 1
			 * 1x speed: 0
			 * 2x speed: 2
			 * 3x speed: 3
			 * 4x speed: 4
			 */
			 
			int tickCount = 0;
			
			// i hate this (this is like the 10th time i have said the same thing)
			// is jumping really this hard dude?
			
			while (isRunning) {
				
				int spikeX = kForSpikeX - 21;
				int spikeY = kForSpikeY - 21;
				
				int cubeX = iForCube - 21;
				int cubeY = 287 - jumpHeight - 21;

				
				// this is just messy
				// long currentTime = System.currentTimeMillis();
				
				tickCount++;
				
				System.out.println("tickCount: " + tickCount + " jumpTick: " + jumpTick + " jumpFrame: " + jumpFrame);
				// System.out.println(" jumpTick: " + jumpTick);
				// System.out.printlb(" jumpFrame: " + jumpFrame);
					
				Graphics g = getGraphics();
				
				g.drawImage(background, 0, 0, Graphics.LEFT | Graphics.TOP);
				
				
				
				if (tickCount == Integer.MAX_VALUE - 1) {
					tickCount = 0;
				}
				
				if (jumpTick == Integer.MAX_VALUE - 1) {
					jumpTick = 0;
				}
				
				// the ground moves
				if (iForCube < 81) {				
					
					iForCube += 5;
					
				} else if (jForForeground > 0 && kForSpikeX > 0) {
						
					jForForeground -= 5;
					kForSpikeX -= 5;
					
				} else if (jForForeground == 0 && kForSpikeX == 0) {
					
					jForForeground = 240;
					kForSpikeX = 240;
					
				}
				
				g.drawImage(foreground, jForForeground, 425, Graphics.RIGHT | Graphics.BOTTOM);
				g.drawImage(foreground, jForForeground + 240, 425, Graphics.RIGHT | Graphics.BOTTOM);
				
				if (isJumpingOnX == true && isJumpingOnY == true) {
					
					// NOTE
					// have no idea
					// there used to be something here
					// ------------------------
					// for the first time, order matters
					// ------------------------
					// i hate animating on this
					// ------------------------
					
					// int drawIndex = (jumpFrame > 8) ? 0 : jumpFrame;
					
					/* if (cubeRotate[drawIndex] != null) {
						g.drawImage(cubeRotate[drawIndex], iForCube, 287 - jumpHeight, Graphics.RIGHT | Graphics.BOTTOM);
					} else {
						System.out.println("frame" + jumpFrame + "is missing");
					} */
					
					//  I have removed all of this part in replace for the custom method

					
					// there used to be code here but I removed it
					
					// there's a reason i put this here
					// don't ask why
					// i hate this
					
					// jumpTick++;

					// check for flags, important for configuration
					switch(flags) {
						case 0x00:
						
						varNormalGravity = false;
						varNormalMode = false;
						break;

						case 0x01:

						varNormalGravity = false;
						varNormalMode = true;
						break;

						case 0x10:
		
						varNormalGravity = true;
						varNormalMode = false;
						break;

						case 0x11:

						varNormalGravity = true;
						varNormalMode = true;
						break;

						default:
				
						System.out.println("invalid flag");
						System.out.println("please change the flag to the valid one");

						System.out.println("system will now exit the app");
					
						destroyApp(true);
						notifyDestroyed();

						// break;
	
					}

					// check for the conditions
					if (varNormalGravity == true) {
						
						normalGravity(true);
	
					} else {
							
						normalGravity(false);
						
					}

					if (varNormalMode == true) {

						normalMode(true);

					} else {
			
						normalMode(false);

					}

					/* if (varNormalGravity == null || varNormalMode == null) {
						System.out.println("exiting game due to invalid boolean variable");
						destroyApp(true);
						notifyDestroyed();
					} */

					// there also used to be code here but I removed it
					
					/* if (tickCount >= 3) {
						jumpFrame++;
						jumpTick = 0;
					} */
					
					// remember switch is better than if-else for the above case
					// has got to be the most complex variable ever
					
					if (jumpFrame > 8 && isJumpingOnX == true && isJumpingOnY == true) {
						jumpHeight = 0;
						jumpFrame = 0;
						jumpTick = 0;
						// jumpTick = 0;
						// jumpTick = 0;
						isJumpingOnX = false;
						isJumpingOnY = false;
					}
					
					/* try {
						Thread.sleep(50);
					} catch (InterruptedException whatwhat) {
						// nothing
					} */
					
				} else if (isJumpingOnX == false && isJumpingOnY == false) {
					g.drawImage(cubeRotate0, iForCube, 287, Graphics.RIGHT | Graphics.BOTTOM);
					// g.setColor(255,0,0);
					// g.fillRect(kForSpikeX - 12, kForSpikeY - 11, 3, 7);
					// System.out.println("HELLO?");
					// g.fillRect(spikeX, spikeY, spikeW, spikeH);
					
					// that above is testing material
					// please do not touch
					// end
				}
				
				g.drawImage(spike1_0, kForSpikeX, kForSpikeY, Graphics.RIGHT | Graphics.BOTTOM);
				g.drawImage(spike1_0, kForSpikeX + 240, kForSpikeY, Graphics.RIGHT | Graphics.BOTTOM);
				
				/* for (int i = 0; i < 40; i++) {
					g.drawImage(objectID[i], 5*i, 300, Graphics.RIGHT | Graphics.BOTTOM);
					// break;
				} */

				flushGraphics();
				
				switch(speedCount) {
					case 1:
					try {
						Thread.sleep(10);
					} catch (InterruptedException error1) { }
					/* case 1:
					try {
						Thread.sleep(5);
					} catch (InterruptedException error2) { } */
					break;
					case 2:
					try {
						Thread.sleep(3);
					} catch (InterruptedException error3) { }
					break;
					case 3:
					try {
						Thread.sleep(2);
					} catch (InterruptedException error4) { }
					break;
					case 4:
					try {
						Thread.sleep(1);
					} catch (InterruptedException error5) { }
					break;
					default: // case 0
					try {
						Thread.sleep(5);
					} catch (InterruptedException error) {
						// nothing
					}
					break;
				}
				
				
				if (cubeX < (kForSpikeX - 12) + spikeW &&
				cubeX + cubeW > (kForSpikeX - 12) &&
				cubeY < (kForSpikeY - 11) + spikeH &&
				cubeY + cubeH > (kForSpikeY - 11)) { // the old hitbox check
					
					g.drawImage(background, 0, 0, Graphics.LEFT | Graphics.TOP);
					// g.setColor(0,0,0);
					// g.fillRect(0,0,240,400);
					g.setColor(255,255,255);
					g.drawString("GAME OVER", 120, 200, Graphics.HCENTER | Graphics.VCENTER);
					g.drawString("Returning to mainMenu in 5 seconds", 120, 215, Graphics.HCENTER | Graphics.VCENTER);
					flushGraphics();
					
					try {
						Thread.sleep(5000);
					} catch (InterruptedException nothing) {
						// nothing
					}
					
					display.setCurrent(mainMenu);
					isRunning = false;
					
					iForCube = 21;
					jForForeground = 240;
					kForSpikeX = 240;
					kForSpikeY = 287;
					
					jumpFrame = 0;
					jumpHeight = 0;
					jumpTick = 0;
				}
				
				
				
				if (isRunning != true) {
					break;
				} else {
					// continue
					/* NOTES:
					 * don't you dare add anything here
					 * i hate coding like this very much already
					 * just remember that
					 */
				}
				
			}
			
		}
		
		public void paint(Graphics g) {
			// nothing
		}

		public void hitboxCollision() {
			// this would be used to handle hitbox collision
		}

		public void levelDirectoryLoader() {

			// Form mainForm;
			TextBox dirLoader;
			Command OK;
			// Display display = display.getDisplay(this);

			/* Graphics g = getGraphics();

			g.setColor(0x000000);
			g.fillRect(0,0,240,400);

			g.setColor(0xFFFFFF);
			g.drawString("Load dir from:", 120, 200, Graphics.HCENTER | Graphics.VCENTER); */
			
			// mainForm = new Form("something for example");
			dirLoader = new TextBox("load from dir:", "", 50, TextField.ANY);
			OK = new Command("ok", Command.OK, 1);

			dirLoader.addCommand(OK);

			// mainForm.append(dirLoader);
			display.setCurrent(dirLoader);
			
		}

		public void normalGravity(boolean isNormalGravity) {
			// this will handle the gravity

			Graphics g = getGraphics();
			
			if (g == null) {
				// destroyApp(true);
				// notifyDestroyed();
				
				return;
			}

			if (isNormalGravity == true) {

				if (isJumpingOnX == true && isJumpingOnY == true) {
					// something

					int drawIndexPositive = (jumpFrame > 8) ? 0 : jumpFrame;

					System.out.println(drawIndexPositive);

					if (cubeRotatePositive[drawIndexPositive] != null) {
						g.drawImage(cubeRotatePositive[drawIndexPositive], iForCube, 287 - jumpHeight, Graphics.RIGHT | Graphics.BOTTOM);
					} else {
						System.out.println("frame" + jumpFrame + "is missing");
					}

					switch(jumpFrame) {
						
						// case 0: jumpHeight = 0; break; // i know there's a default one
					
						case 1:
					
						jumpHeight += parabola[0]; /* tickCount += -1 ;*/ 
						switch(speedCount) {
							case 1: jumpTick++; break;
							case 2: jumpTick += 4; break;
							case 3: jumpTick += 8; break;
							case 4: jumpTick += 16; break;
							default: jumpTick += 2; break;
						}
						System.out.print("case 1 ");  
						break;
					
						case 2: 
					
						jumpHeight += parabola[1]; 
						switch(speedCount) {
							case 1: jumpTick++; break;
							case 2: jumpTick += 4; break;
							case 3: jumpTick += 8; break;
							case 4: jumpTick += 16; break;
							default: jumpTick += 2; break;
						}
						System.out.print("case 2 ");  
						break;
					
						case 3: 
						
						jumpHeight += parabola[2]; 
						switch(speedCount) {
							case 1: jumpTick++; break;
							case 2: jumpTick += 4; break;
							case 3: jumpTick += 8; break;
							case 4: jumpTick += 16; break;
							default: jumpTick += 2; break;
						}
						System.out.print("case 3 ");  
						break;
						
						case 4: 
						
						jumpHeight += parabola[3]; 
						switch(speedCount) {
							case 1: jumpTick++; break;
							case 2: jumpTick += 4; break;
							case 3: jumpTick += 8; break;
							case 4: jumpTick += 16; break;
							default: jumpTick += 2; break;
						}
						System.out.print("case 4 ");  
						break;
						
						case 5: 
						
						jumpHeight -= parabola[3]; 
						switch(speedCount) {
							case 1: jumpTick++; break;
							case 2: jumpTick += 4; break;
							case 3: jumpTick += 8; break;
							case 4: jumpTick += 16; break;
							default: jumpTick += 2; break;
						}
						System.out.print("case 5 "); 
						break;
						
						case 6: 
						
						jumpHeight -= parabola[2]; 
						switch(speedCount) {
							case 1: jumpTick++; break;
							case 2: jumpTick += 4; break;
							case 3: jumpTick += 8; break;
							case 4: jumpTick += 16; break;
							default: jumpTick += 2; break;
						}
						System.out.print("case 6 "); 
						break;
						
						case 7: 
						
						jumpHeight -= parabola[1]; 
						switch(speedCount) {
							case 1: jumpTick++; break;
							case 2: jumpTick += 4; break;
							case 3: jumpTick += 8; break;
							case 4: jumpTick += 16; break;
							default: jumpTick += 2; break;
						}
						System.out.print("case 7 ");  
						break;
						
						case 8: 
						
						jumpHeight -= parabola[0]; 
						switch(speedCount) {
							case 1: jumpTick++; break;
							case 2: jumpTick += 4; break;
							case 3: jumpTick += 8; break;
							case 4: jumpTick += 16; break;
							default: jumpTick += 2; break;
						}
						System.out.print("case 8 ");  
						break;
						
						default: 
						
						jumpHeight = 0; 
						jumpTick = 0; 

						System.out.print("case 0 ");
						break; // just in case
							
					}

					
				} /* else if (isJumpingOnX == false && isJumpingOnY == false) {
					g.drawImage(cubeRotate[0], iForCube, 287, Graphics.RIGHT | Graphics.BOTTOM);
					
				} */

				switch(speedCount) {
					case 1: 
					if (jumpTick >= 4) {
						jumpFrame++;
						jumpTick = 0;
					} // else if (tickCount % 3 != 0) {
					// return;
					// }
					
					break;
					
					case 2:
					if (jumpTick >= 16) {
						jumpFrame++;
						jumpTick = 0;
					}
					
					break;
					
					case 3:
					if (jumpTick >= 32) {
						jumpFrame++;
						jumpTick = 0;
					}
					
					break;
					
					case 4:
					if (jumpTick >= 64) {
						jumpFrame++;
						jumpTick = 0;
					}
					
					break;
					
					default: // case 0
					if (jumpTick >= 8) {
						jumpFrame++;
						jumpTick = 0;
					}
					
					break;
					
				}

			} else if (isNormalGravity == false) {

				if (isJumpingOnX == true && isJumpingOnY == true) {
					// something

					int drawIndexNegative = (jumpFrame > 8) ? 0 : jumpFrame;

					if (cubeRotateNegative[drawIndexNegative] != null) {
						g.drawImage(cubeRotateNegative[drawIndexNegative], iForCube, 287 - jumpHeight, Graphics.RIGHT | Graphics.BOTTOM);
					} else {
						System.out.println("frame" + jumpFrame + "is missing");
					}

					switch(jumpFrame) {
					
						// case 0: jumpHeight = 0; break; // i know there's a default one
					
						case 1:
					
						jumpHeight -= parabola[0]; /* tickCount += -1 ;*/ 
						switch(speedCount) {
							case 1: jumpTick++; break;
							case 2: jumpTick += 4; break;
							case 3: jumpTick += 8; break;
							case 4: jumpTick += 16; break;
							default: jumpTick += 2; break;
						}
						System.out.print("case -1 ");  
						break;
					
						case 2: 
					
						jumpHeight -= parabola[1]; 
						switch(speedCount) {
							case 1: jumpTick++; break;
							case 2: jumpTick += 4; break;
							case 3: jumpTick += 8; break;
							case 4: jumpTick += 16; break;
							default: jumpTick += 2; break;
						}
						System.out.print("case -2 ");  
						break;
					
						case 3: 
						
						jumpHeight -= parabola[2]; 
						switch(speedCount) {
							case 1: jumpTick++; break;
							case 2: jumpTick += 4; break;
							case 3: jumpTick += 8; break;
							case 4: jumpTick += 16; break;
							default: jumpTick += 2; break;
						}
						System.out.print("case -3 ");  
						break;
						
						case 4: 
						
						jumpHeight -= parabola[3]; 
						switch(speedCount) {
							case 1: jumpTick++; break;
							case 2: jumpTick += 4; break;
							case 3: jumpTick += 8; break;
							case 4: jumpTick += 16; break;
							default: jumpTick += 2; break;
						}
						System.out.print("case -4 ");  
						break;
						
						case 5: 
						
						jumpHeight += parabola[3]; 
						switch(speedCount) {
							case 1: jumpTick++; break;
							case 2: jumpTick += 4; break;
							case 3: jumpTick += 8; break;
							case 4: jumpTick += 16; break;
							default: jumpTick += 2; break;
						}
						System.out.print("case -5 "); 
						break;
						
						case 6: 
						
						jumpHeight += parabola[2]; 
						switch(speedCount) {
							case 1: jumpTick++; break;
							case 2: jumpTick += 4; break;
							case 3: jumpTick += 8; break;
							case 4: jumpTick += 16; break;
							default: jumpTick += 2; break;
						}
						System.out.print("case -6 "); 
						break;
						
						case 7: 
						
						jumpHeight += parabola[1]; 
						switch(speedCount) {
							case 1: jumpTick++; break;
							case 2: jumpTick += 4; break;
							case 3: jumpTick += 8; break;
							case 4: jumpTick += 16; break;
							default: jumpTick += 2; break;
						}
						System.out.print("case -7 ");  
						break;
						
						case 8: 
						
						jumpHeight += parabola[0]; 
						switch(speedCount) {
							case 1: jumpTick++; break;
							case 2: jumpTick += 4; break;
							case 3: jumpTick += 8; break;
							case 4: jumpTick += 16; break;
							default: jumpTick += 2; break;
						}
						System.out.print("case -8 ");  
						break;
						
						default: 
						
						jumpHeight = 0; 
						jumpTick = 0; 
						System.out.print("case 0 ");
						break; // just in case
						
					}

				} /* else if (isJumpingOnX == false && isJumpingOnY == false) {
					g.drawImage(cubeRotate[0], iForCube, 287, Graphics.RIGHT | Graphics.BOTTOM);
					
				} */

				switch(speedCount) {
					case 1: 
					if (jumpTick >= 4) {
						jumpFrame++;
						jumpTick = 0;
					} // else if (tickCount % 3 != 0) {
					// return;
					// }
					
					break;
					
					case 2:
					if (jumpTick >= 16) {
						jumpFrame++;
						jumpTick = 0;
					}
					
					break;
					
					case 3:
					if (jumpTick >= 32) {
						jumpFrame++;
						jumpTick = 0;
					}
					
					break;
					
					case 4:
					if (jumpTick >= 64) {
						jumpFrame++;
						jumpTick = 0;
					}
					
					break;
					
					default: // case 0
					if (jumpTick >= 8) {
						jumpFrame++;
						jumpTick = 0;
					}
					
					break;
							
				}

			} /* else if (isNormalGravity == null) {
				
				System.out.println("invalid");
				return;
		
			} */
		}

		public void normalMode(boolean isNormalMode) {
			// this will handle the gamemode's size

			if (isNormalMode == true) {
				// something
			} else {
				// something
			}
		}
		
		protected void pointerPressed(int x, int y) {
			if (isJumpingOnX == false && x >= 0 && x <= 240 && y >= 0 && y <= 400) {
				isJumpingOnX = true;
				isJumpingOnY = true;

				if (varNormalGravity == true) {
					jumpFrame = 1;
				} else {
					jumpFrame = 1;
				}
			}
		}
		
	}
			
			
	class NewPlayScreen extends GameCanvas /* implements Runnable */ {

		public NewPlayScreen() {
			super(true);
		}
		
	/* 
	 * this class will be left for the future
	 * it will replace the old system
	 * but for now it's unused
	 * April 26th 2026
	 */
	
	}

	class DataRegistry {

		// the dataRegistry is specifically made to handle parsing the csv file
		// it will be used for the playScreen

		// String line;
		
		public DataRegistry() {
			
			InputStream is = getClass().getResourceAsStream("assets/objectIDSpreadsheet.csv");

			if (is == null) {
				System.out.println("objectIDSpreadsheet.csv doesn't exist");
			} else {
				System.out.println("objectIDSpreadsheet.csv exists");
			}

			StringBuffer sb = new StringBuffer();
			int ch;
			
			try {

			while ((ch = is.read()) != -1) {
				if (ch == '\n' || ch == '\r') {
					if (sb.length() > 0) {
						parseLine(sb.toString());
						sb.setLength(0);
					}
				} else {
					sb.append((char) ch);
				}
			}
			
			if (sb.length() > 0) {
				parseLine(sb.toString());
			}

			is.close();

			} catch (Exception e) {
				// System.out.println("MISSING, THE FILE IS MISSING");
			}
		}

		private void parseLine(String line) {
			if (line.startsWith("objectID")) {
				return;
			}
			
			int firstSemi = line.indexOf(';');
			int secondSemi = line.indexOf(';', firstSemi + 1);
			int thirdSemi = line.indexOf(';', secondSemi + 1);
			int fourthSemi = line.indexOf(';', thirdSemi + 1);
			int fifthSemi = line.indexOf(';', fourthSemi + 1);
			int sixthSemi = line.indexOf(';', fifthSemi + 1);
			int seventhSemi = line.indexOf(';', sixthSemi + 1);
			int eighthSemi = line.indexOf(';', seventhSemi + 1);
			int ninthSemi = line.indexOf(';', eighthSemi + 1);
			
			if (firstSemi != -1 && secondSemi != -1 && thirdSemi != -1 && fourthSemi != 1 && fifthSemi != -1 && sixthSemi != -1 && seventhSemi != -1 && eighthSemi != -1 && ninthSemi != -1) {
				int id = Integer.parseInt(line.substring(0, firstSemi).trim());

				String detail = line.substring(firstSemi + 1, secondSemi);
				String xCoords = line.substring(secondSemi + 1, thirdSemi);
				String yCoords = line.substring(thirdSemi + 1, fourthSemi);
				String widths = line.substring(fourthSemi + 1, fifthSemi);
				String heights = line.substring(fifthSemi + 1, sixthSemi);
				String hitboxXs = line.substring(sixthSemi + 1, seventhSemi);
				String hitboxYs = line.substring(seventhSemi + 1, eighthSemi);
				String hitboxWs = line.substring(eighthSemi + 1, ninthSemi);
				String hitboxHs = line.substring(ninthSemi + 1);

				details[id] = detail;
				xCoord[id] = xCoords;
				yCoord[id] = yCoords;
				width[id] = widths;
				height[id] = heights;
				hitboxX[id] = hitboxXs;
				hitboxY[id] = hitboxYs;
				hitboxW[id] = hitboxWs;
				hitboxH[id] = hitboxHs;

				// directories[id] = directory;

				System.out.println("ID: " + id + " Details: " + detail + " xCoord: " + xCoords + " yCoord: " + yCoords + " width: " + widths + " height: " + heights + " hitboxX: " + hitboxXs + " hitboxY: " + hitboxYs + " hitboxW: " + hitboxWs + " hitboxH: " + hitboxHs); // + " Paths: " + directory);
			}
		}
	}
	
	
	class LevelBinaryParser {
		
		// this is another parser for levels and its binary format
		// this one is important since playScreen will load level from it (at least not now)
		// i don't know if it's optimized yet or not but whatever
		// you can help improve this one if you want

		private volatile boolean isRunning = true;
		String name = "example.bin";

		
		
		public LevelBinaryParser() {
			
			InputStream is = getClass().getResourceAsStream("levels/" + name);
			
			if (is == null) {
				System.out.println("name is null");
			} else {
				// System.out.println("not null too");
			}
			
			ByteArrayOutputStream Baos = new ByteArrayOutputStream();
			// DataOutputStream Dos = new DataOutputStream(Baos);
			// int bytes;
			
			try {
				
				int total = is.read(data);
				byte[] header1 = {0x47, 0x44, 0x4F, 0x6E, 0x4A, 0x61, 0x76, 0x61, 0x12, 0x6C, 0x65, 0x76, 0x44, 0x61, 0x74, 0x61};
				// byte[] header2 = {
				
				is.close();
				boolean isValid = true;
				
				// is.close();
				
				while ((total = is.read()) != -1) {
					Baos.write(data);
				}
 
				for (int i = 0; i < 16; i++) {
					if (data[i] != header1[i]) {
						isValid = false;
						break;
					}
				}
				
				if (isValid == false) {
					System.out.println("Invalid File");
					System.out.println("Reason: Incorrect file header");
					
					for (int i = 0; i < 16; i++) {
						if (data[i] != header1[i] /* || data[i] != header2[i] */) {
							System.out.println("At 0x" + Integer.toHexString(i) + ", byte: 0x" + Integer.toHexString(data[i] & 0xFF) + " is wrong.");
							System.out.println("The **valid** header is: 0x47 0x44 0x4F 0x6E 0x4A 0x61 0x76 0x61 0x12 0x6C 0x65 0x76 0x44 0x61 0x74 0x61");
							System.out.println("Please check, modify your header and try again");
							break;
						}
					}

					return;
				} else {
					System.out.println("Valid File");
					// System.out.println("byte read: " + total);
					for (int i = 32; i > total; i += 4) {

						// String hexBytes = Integer.toHexString(bytes);

						id = data[i] & 0xFF;
						x1 = data[i+1] & 0xFF;
						x2 = data[i+2] & 0xFF;
						x3 = data[i+3] & 0xFF;
						y1 = data[i+4] & 0xFF;
						y2 = data[i+5] & 0xFF;
						y3 = data[i+6] & 0xFF;
						par = data[i+7] & 0xFF;
					
						// System.out.println(hexBytes + " ");
					
						if (id != 0 && x1 != 0 && x2 != 0 && x3 != 00 && y1 != 0 && y2 != 0 && y3 != 0 && par != 0) {
							System.out.println("obj: " + ((i/4)-8) + " id: " + Integer.toHexString(id) + " x1: " + x1 + " x2: " + x2 + " x3: " + x3 + " y1: " + y1 + " y2: " + y2 + " y3: " + y3 + " par: " + par);
							// System.out.println("data" + data);
						} else if (id == 0 && x1 == 0 && x2 == 0 && x3 == 0 && y1 == 0 && y2 == 0 && y3 == 0 && par == 0) {
							System.out.println("End of: " + name);
							return;
						}
						
						

					}
				}

			} catch (Exception e) {
				System.out.println("SOMETHING IS DEFINITELY WRONG");
				// note
			}
		}
	}
	

	
	// CONSTRUCTOR
	public GDOnJavaForJava26() {
		
		try {
			
    		bgMusic = Manager.createPlayer(getClass().getResourceAsStream(null), "audio/x-wav");
    		bgMusic.realize();
    		bgMusic.prefetch();
			
			normalMusic = Manager.createPlayer(getClass().getResourceAsStream(null), "audio/x-wav");
			normalMusic.realize();
			normalMusic.prefetch();
			
			VolumeControl vc1 = (VolumeControl) bgMusic.getControl("VolumeControl");
			VolumeControl vc2 = (VolumeControl) normalMusic.getControl("VolumeControl");
			
			if (vc1 != null) {
				vc1.setLevel(100);
			} else {
				System.err.println("yeah audio sucks");
			}
			
			if (vc2 != null) {
				vc2.setLevel(100);
			} else {
				System.err.println("yeah this one sucks too");
			}
			
		} catch (Exception e) {
		
    		// e.printStackTrace();
			// System.out.println("nah");

		}
		
	}

	
	// START APP
	public void startApp() {
		
		// this piece of code does nothing
		/* while (running) {
			time++;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// nothing
			}
		} */
		// used to be useful

		// this is where the actual start begins
		// the code starts from here (technically the constructor goes first but startApp() is as important)
		
		display = Display.getDisplay(this);
		warningScreen = new WarningScreen();
		mainMenu = new MainMenu();
		aboutMenu = new AboutMenu();
		exitMenu = new ExitMenu();
		soundMenu = new SoundMenu();
		soundForm = new SoundForm();
		exitForm = new ExitForm();
		newTimerScreen = new NewTimerScreen();
		playScreen = new PlayScreen();
		speedForm = new SpeedForm();

		// dataRegistry = new DataRegistry();
		// levelBinaryParser = new LevelBinaryParser();
		
		// System.out.println(dataRegistry instanceof Object);
		// System.out.println(levelBinaryParser instanceof Object);

		// System.out.println(dataRegistry.directories[39]);
		// System.out.println(dataRegistry.directories.length);

		/* for (int i = 0; i < dataRegistry.directories.length; i++) {
			String currentPath = dataRegistry.directories[i];
			System.out.println(currentPath);
		} */
		
		// this is just a joke and will be removed later
		/* Random rand = new Random();

		// AS A JOKE
		for (int i = 0; i < 9; i++) {
			int r = rand.nextInt(41);
	
			if (r == 0) {
				r += 1;
			}

			System.out.println(details[r]);
		} */

		// this piece of code is very important since it's the way to display the screen (from those classes) to the user
		// by default it will always be the warningScreen() first

		// playScreen.levelDirectoryLoader();
		// display.setCurrent(playScreen);

		display.setCurrent(warningScreen);
		// playScreen.levelDirectoryLoader();
		// System.out.println("yes");
		
		// this piece of code is also useless on its own
		/* try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// nothing
		} */
		// guess the usage of that
		
	}

	// PAUSE APP
	public void pauseApp() {

	/* pauseApp()
	 * pauseApp itself serves not much useful cases
	 * you can't test it on emulators since there isn't any system-related force acting on the program itself
	 * and so i don't consider adding any much code for this situation (or case)
	 */
	
		display = Display.getDisplay(this);
		exitMenu = new ExitMenu();
		display.setCurrent(exitMenu);
		
	}

	// DESTROY APP
	public void destroyApp(boolean unconditional) {

	/* i left this blank because like pauseApp() it does pretty much nothing
	 * i will probably not adding anything to this class
	 */

	}

	// COMMANDS
	// this part is mostly DEPRECATED because:
	// 1. it used to be mandatory for high-level ui
	// 2. i don't use form or alert anymore
	// 3. gamecanvas is better
	// that's it
	public void commandAction(Command c, Displayable d) {
	
		if (c == exitCommand) {
		
			destroyApp(true);
			notifyDestroyed();
			
		}
		
		else if (c == yesCommand) {
		
			display.setCurrent(aboutMenu);
			
		}
		
		else if (c == noCommand) {
		
			display.setCurrent(exitForm);
			
		}
		
		else if (c == mainBS) {
		
			if (d == exitForm) {
				display.setCurrent(mainMenu);
				mainMenu.repaint();
				
			} else {
				display.setCurrent(mainMenu);
				
			}
			
		} else if (c == half_times_speed) {
			speedCount = 1;
		} else if (c == one_time_speed) {
			speedCount = 0;
		} else if (c == two_times_speed) {
			speedCount = 2;
		} else if (c == three_times_speed) {
			speedCount = 3;
		} else if (c == four_times_speed) {
			speedCount = 4;
		} else if (c == exit) {
			display.setCurrent(mainMenu);
		}
		if (d != mainMenu) {
			running = false;
		} else {
			// nothing
		}
		
	}

}

// the end of the source file
// this code is not optimized btw