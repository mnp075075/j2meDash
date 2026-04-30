// --------------- NOTES ----------------
//
// removed these notes
// because I'm publishing this to github
// and i need to keep it normal
//
// ---------------- END -----------------

// IMPORT ALL THOSE THINGS
import javax.microedition.midlet.*;
import javax.microedition.lcdui.*; 
import javax.microedition.lcdui.game.*; 
import javax.microedition.media.*;
import javax.microedition.media.control.*;
import java.util.Timer;
import java.util.TimerTask;
import java.io.*;

// THE BRAIN OF THE PROJECT
public class GDOnJava extends MIDlet implements CommandListener { 

	// DEFINING EVERYTHING
	public static boolean SoundEnabled;
	public static boolean haventOpened;
	public static int fadeforward;
	public static int seconds = 5;
	public static int time = 0;
	public static int speedCount = 0;
	
	/* SPEED COUNT:
	 * 0.5x speed: 1
	 * 1x speed: 0
	 * 2x speed: 2
	 * 3x speed: 3
	 * 4x speed: 4
	 */
			 
	// public static volatile boolean paused;
	public static volatile boolean running = true;
	private Display display;
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
	private Form soundForm;
	private Form exitForm;
	private Form speedForm;
	private Command exitCommand = new Command("rage quit", Command.EXIT, 1);
	private Command yesCommand = new Command("no yes", Command.OK, 1);
	private Command noCommand = new Command("yes no", Command.CANCEL, 1);
	private Command mainBS = new Command("main bullsh*t", Command.OK, 1);
	
	// speed
	private Command half_times_speed = new Command("0.5x speed", Command.OK, 1);
	private Command one_time_speed = new Command("1x speed", Command.OK, 1);
	private Command two_times_speed = new Command("2x speed", Command.OK, 1);
	private Command three_times_speed = new Command("3x speed", Command.OK, 1);
	private Command four_times_speed = new Command("4x speed", Command.OK, 1);
	private Command exit = new Command("Exit back to mainMenu", Command.EXIT, 1);
	// speed for real
	
	private Player bgMusic;
	private Player normalMusic;

	// THE MAIN MENU
	class MainMenu extends GameCanvas {
		Image FIREINTHEHOLE;
		Image background;
		Image gdlogo;
		
		// IMAGE AND AUDIO FOR MAIN MENU
		// ALSO ISN'T A CLASS
		public MainMenu() {
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
		public void paint(Graphics g) {
			
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
			if (keyCode == KEY_NUM1) {
				display.setCurrent(speedForm);
				repaint();
			}
		}
		
		// THREAD THREATENING PEOPLE 
		// too bad I deleted it

	}
	
	// ABOUT MENU
	class AboutMenu extends GameCanvas {
		
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
		
		public SoundMenu() {
			super(true); // REQUIRED
			
			try {
				if (SoundEnabled == true) {
					bgMusic.start();
				} else {
					bgMusic.stop();
				}
			} catch (Exception e) {
				System.err.println("qasaas");
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
			
			setCommandListener(GDOnJava.this);
			
		}
		
	}
	
	// EXIT FORM (DEPRECATED)
	class ExitForm extends Form {
	
		public ExitForm() {
		
			super("DUDE RAGEQUIT");
			
			append("need tutorial?");
			
			addCommand(exitCommand);
			addCommand(mainBS);
			
			setCommandListener(GDOnJava.this);
			
		}
		
	}
	
	// new timer screen - april 23rd
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
			
			setCommandListener(GDOnJava.this);
			
		}
		
	}
	
	// PLAY SCREEN - APRIL 25TH
	
	class PlayScreen extends GameCanvas implements Runnable {
		
		private int jumpFrame = 0;
		private int jumpHeight = 0;
		
		Image background;
		Image foreground;
		Image spike;
		Image[] cubeRotate = new Image[10];
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
		
		private Thread t1;
		private volatile boolean isRunning = true;
		private volatile boolean isJumpingOnX;
		private volatile boolean isJumpingOnY;
		// private long lastTime = System.currentTimeMillis();
		private int tickCount = 0;
		
		public PlayScreen() {
			
			super(true);
			try {
				background = Image.createImage("assets/background.png");
				foreground = Image.createImage("assets/foreground.png");
				spike = Image.createImage("assets/spike.png");
				
				for (int i = 0; i <= 8; i++) {
					cubeRotate[i] = Image.createImage("assets/cubeRotate" + i + ".png");
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
			
			System.out.println("THE GROUND, I HATE IT");
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
			int jumpTick = 0;
			
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
				
				System.out.println("tickCount: " + tickCount);
				System.out.println("jumpTick: " + jumpTick);
					
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
					
					int drawIndex = (jumpFrame > 8) ? 0 : jumpFrame;
					
					if (cubeRotate[drawIndex] != null) {
						g.drawImage(cubeRotate[drawIndex], iForCube, 287 - jumpHeight, Graphics.RIGHT | Graphics.BOTTOM);
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
						System.out.println("case 1");  
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
						System.out.println("case 2");  
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
						System.out.println("case 3");  
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
						System.out.println("case 4");  
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
						System.out.println("case 5"); 
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
						System.out.println("case 6"); 
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
						System.out.println("case 7");  
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
						System.out.println("case 8");  
						break;
						
						default: 
						
						jumpHeight = 0; 
						jumpTick = 0; 
						break; // just in case
						
					}
					
					// there's a reason i put this here
					// don't ask why
					// i hate this
					
					// jumpTick++;
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
					g.drawImage(cubeRotate[0], iForCube, 287, Graphics.RIGHT | Graphics.BOTTOM);
					// g.setColor(255,0,0);
					// g.fillRect(kForSpikeX - 12, kForSpikeY - 11, 3, 7);
					// System.out.println("HELLO?");
					// g.fillRect(spikeX, spikeY, spikeW, spikeH);
					
					// that above is testing material
					// please do not touch
					// end
				}
				
				g.drawImage(spike, kForSpikeX, kForSpikeY, Graphics.RIGHT | Graphics.BOTTOM);
				g.drawImage(spike, kForSpikeX + 240, kForSpikeY, Graphics.RIGHT | Graphics.BOTTOM);
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
				cubeY + cubeH > (kForSpikeY - 11)) {
					
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
		
		protected void pointerPressed(int x, int y) {
			if (isJumpingOnX == false && x >= 0 && x <= 240 && y >= 0 && y <= 400) {
				isJumpingOnX = true;
				isJumpingOnY = true;
				jumpFrame = 1;
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
	
	// CONSTRUCTOR
	public GDOnJava() {
		
		try {
			
    		bgMusic = Manager.createPlayer(getClass().getResourceAsStream("assets/sample-15s.wav"), "audio/x-wav");
    		bgMusic.realize();
    		bgMusic.prefetch();
			
			normalMusic = Manager.createPlayer(getClass().getResourceAsStream("assets/sample-3s.wav"), "audio/x-wav");
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
		
    		e.printStackTrace();

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
		
		System.out.println(-1 % 59);
		display.setCurrent(warningScreen);
		
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
	
		display = Display.getDisplay(this);
		exitMenu = new ExitMenu();
		display.setCurrent(exitMenu);
		
	}

	// DESTROY APP
	public void destroyApp(boolean unconditional) {

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