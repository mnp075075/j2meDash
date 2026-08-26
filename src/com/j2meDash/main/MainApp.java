package com.j2meDash.main;

import com.j2meDash.game.*;
import com.j2meDash.menu.*;
import com.j2meDash.pars.*;
import com.j2meDash.temp.*;

import javax.microedition.midlet.*; // the midlet, required to compile a j2me application
import javax.microedition.lcdui.*; // basically the ui, things like Forms, Alert, List, and also Canvas
import javax.microedition.lcdui.game.*; // a gaming version of Canvas that is GameCanvas
import javax.microedition.media.*; // the way to play sound
import javax.microedition.media.control.*; // the way to control sound effectively
import java.io.*; // basically input and output, self-explanatory

/*

  888888  .d8888b.  888b     d888 8888888888 8888888b.        d8888  .d8888b.  888    888 
    "88b d88P  Y88b 8888b   d8888 888        888  "Y88b      d88888 d88P  Y88b 888    888 
     888        888 88888b.d88888 888        888    888     d88P888 Y88b.      888    888 
     888      .d88P 888Y88888P888 8888888    888    888    d88P 888  "Y888b.   8888888888 
     888  .od888P"  888 Y888P 888 888        888    888   d88P  888     "Y88b. 888    888 
     888 d88P"      888  Y8P  888 888        888    888  d88P   888       "888 888    888 
     88P 888"       888   "   888 888        888  .d88P d8888888888 Y88b  d88P 888    888 
     888 888888888  888       888 8888888888 8888888P" d88P     888  "Y8888P"  888    888 
   .d88P                                                                                  
 .d88P"                                                                                   
888P"   

*/

public class MainApp extends MIDlet implements CommandListener { 

	private MainApp mainApp;
	private CommandListener cl;
	
	SpeedForm speedForm;
	SoundForm soundForm;
	ExitMenu exitMenu;
	DebugMenu debugMenu;
	SplashScreen splashScreen;
	DataRegistry dataRegistry;
	LevelBinaryParser levelBinaryParser;
	WarningScreen warningScreen;
	AboutMenu aboutMenu;
	SoundMenu soundMenu;
	MainMenu mainMenu;
	ExitForm exitForm;
	NewTimerScreen newTimerScreen;
	TimerScreen timerScreen;
	PlayScreen playScreen;
	NewPlayScreen newPlayScreen;
	PauseScreen pauseScreen;
	GameOverScreen gameOverScreen;
	GameOverScreenSpecificallyForRestarting gameOverScreenSpecificallyForRestarting;
	TransitionScreen transitionScreen;
	GameEngine gameEngine;
	TestingFPS testFPS;
	Utilities utilities;
	NewGameEngine newGameEngine;
	
	// DEFINING EVERYTHING
	public static boolean SoundEnabled; // deprecated, used to control sound
	public static int time = 0; // used for the new timer screen class to count time
	public static int speedCount = 0; // the speed count, mandatory for controlling speed


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
	
	// -----------------------------------
	
	public static volatile boolean running = true; // required
	private Display display = Display.getDisplay(this); // to control the display
	
	// speedForm commands
	public Command half_times_speed = new Command("0.5x speed", Command.OK, 1);
	public Command one_time_speed = new Command("1x speed", Command.OK, 1);
	public Command two_times_speed = new Command("2x speed", Command.OK, 1);
	public Command three_times_speed = new Command("3x speed", Command.OK, 1);
	public Command four_times_speed = new Command("4x speed", Command.OK, 1);
	public Command exit = new Command("Exit back to mainMenu", Command.EXIT, 1);
	
	// the music players (deprecated due to being hard to control)
	public Player bgMusic;
	public Player normalMusic;
	
	// ids for screens
	public static final byte STATE_null = 0x0000;
	public static final byte STATE_SpeedForm = 0x0001;
	public static final byte STATE_SoundForm = 0x0002;
	public static final byte STATE_ExitMenu = 0x0003;
	public static final byte STATE_DebugMenu = 0x0004;
	public static final byte STATE_SplashScreen = 0x0005;
	public static final byte STATE_WarningScreen = 0x0006;
	public static final byte STATE_AboutMenu = 0x0007;
	public static final byte STATE_SoundMenu = 0x0008;
	public static final byte STATE_MainMenu = 0x0009;
	public static final byte STATE_ExitForm = 0x000a;
	public static final byte STATE_NewTimerScreen = 0x000b;
	public static final byte STATE_PlayScreen = 0x000c;
	public static final byte STATE_PauseScreen = 0x000d;
	public static final byte STATE_GameOverScreen = 0x000e;
	public static final byte STATE_GameOverScreenSpecificallyForRestarting = 0x000f;
	public static final byte STATE_Utilities = 0x0010;
	
	// state ids
	public static byte targetSTATE;
	
	public void changeScreen() {
		
		Displayable d = null;
		
		switch (this.targetSTATE) {
			case STATE_SpeedForm: d = speedForm; break;
			case STATE_SoundForm: d = soundForm; break;
			case STATE_ExitMenu: d = exitMenu; break;
			case STATE_DebugMenu: d = debugMenu; break;
			case STATE_SplashScreen: d = splashScreen; break;
			case STATE_WarningScreen: d = warningScreen; break;
			case STATE_AboutMenu: d = aboutMenu; break;
			case STATE_SoundMenu: d = soundMenu; break;
			case STATE_MainMenu: d = mainMenu; break;
			case STATE_ExitForm: d = exitForm; break;
			case STATE_NewTimerScreen: d = newTimerScreen; break;
			case STATE_PlayScreen: d = playScreen; break;
			case STATE_PauseScreen: d = pauseScreen; break;
			case STATE_GameOverScreen: d = gameOverScreen; break;
			case STATE_GameOverScreenSpecificallyForRestarting: d = gameOverScreenSpecificallyForRestarting; break;
			case STATE_Utilities: d = utilities; break;
			default: d = null; System.out.println("invalid state"); exitApp();
		}
		
		Display.getDisplay(this).setCurrent(d);
		
	}

	public void show(Displayable d) {
		if (d == null) {
			System.out.println("null displayable");
			return;
		}
		Display.getDisplay(this).setCurrent(d);
	}

	public void exitApp() {
		destroyApp(true);
		notifyDestroyed();
	}

	public void sleepFor(int miliseconds) {
		try {
			Thread.sleep(miliseconds);
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}
	}
	
	// CONSTRUCTOR
	public MainApp() {
		
		Object name[][] = {{"SpeedForm","SoundForm","ExitMenu","DebugMenu","SplashScreen","DataRegistry","LevelBinaryParser","WarningScreen","AboutMenu","SoundMenu","MainMenu","ExitForm","NewTimerScreen","TimerScreen","PlayScreen","NewPlayScreen"},
						   {"speedForm","soundForm","exitMenu","debugMenu","splashScreen","dataRegistry","levelBinaryParser","warningScreen","aboutMenu","soundMenu","mainMenu","exitForm","newTimerScreen","timerScreen","playScreen","newPlayScreen"}};
						   
		// commands = new Commands();
		
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

	public void showTransitionScreen() {
		if (transitionScreen == null) {
			transitionScreen = new TransitionScreen(this);
		}
		
		Display.getDisplay(this).setCurrent(transitionScreen);
	}
	
	public void showExitMenu() {
		if (exitMenu == null) {
			exitMenu = new ExitMenu(this);
		}
		
		this.targetSTATE = STATE_ExitMenu;
	}
	
	public void showMainMenu() {
		
		if (mainMenu == null) {
			mainMenu = new MainMenu(this);
		}
		
		this.targetSTATE = STATE_MainMenu;
	}
	
	public void showSpeedForm() {
		
		if (speedForm == null) {
			speedForm = new SpeedForm(this);
		}
		
		this.targetSTATE = STATE_SpeedForm;
	}
	
	public void showDebugMenu() {
		
		if (debugMenu == null) {
			debugMenu = new DebugMenu(this);
		}
		
		this.targetSTATE = STATE_DebugMenu;
	}
	
	public void showSplashScreen() {
		if (splashScreen == null) {
			splashScreen = new SplashScreen(this);
		}
		
		Display.getDisplay(this).setCurrent(splashScreen);
		splashScreen.threading();
	}
	
	public void showWarningScreen() {
		if (warningScreen == null) {
			warningScreen = new WarningScreen(this);
		}
		
		Display.getDisplay(this).setCurrent(warningScreen);
		// System.out.println("shown");
	}
	
	public void showAboutMenu() {
		
		if (aboutMenu == null) {
			aboutMenu = new AboutMenu(this);
		}
		
		this.targetSTATE = STATE_AboutMenu;
	}
	
	public void showSoundMenu() {	
		
		if (soundMenu == null) {
			soundMenu = new SoundMenu(this);
		}
		
		this.targetSTATE = STATE_SoundMenu;
	}
	
	public void showSoundForm() {
		
		if (soundForm == null) {
			soundForm = new SoundForm(cl, this);
		}
		
		this.targetSTATE = STATE_SoundForm;
	}
	
	public void showNewTimerScreen() {
		
		if (newTimerScreen == null) {
			newTimerScreen = new NewTimerScreen(this);
		}
		
		this.targetSTATE = STATE_NewTimerScreen;
	}
	
	public void showExitForm() {
		
		if (exitForm == null) {
			exitForm = new ExitForm(cl, this);
		}
		
		this.targetSTATE = STATE_ExitForm;
	}
	
	public void showPlayScreen() {
		if (playScreen == null) {
			playScreen = new PlayScreen(this);
		}
		
		this.targetSTATE = STATE_PlayScreen;
	}
	
	public void showNewPlayScreen() {
		// intentionally left blank because of unused class
	}
	
	public void showTimerScreen() {
		// intentionally left blank because of unused class
	}
	
	public void showPauseScreen() {
		
		if (pauseScreen == null) {
			pauseScreen = new PauseScreen(this);
		}
		
		this.targetSTATE = STATE_PauseScreen;
	}
	
	public void showGameOverScreen() {
		
		if (gameOverScreen == null) {
			gameOverScreen = new GameOverScreen(this);
		}
		
		this.targetSTATE = STATE_GameOverScreen;
	}
	
	public void showGameOverScreenSpecificallyForRestarting() {
		
		if (gameOverScreenSpecificallyForRestarting == null) {
			gameOverScreenSpecificallyForRestarting = new GameOverScreenSpecificallyForRestarting(this);
		}
		
		this.targetSTATE = STATE_GameOverScreenSpecificallyForRestarting;
	}
	
	public void showGameEngine() {
		// for testing only
		if (gameEngine == null) {
			gameEngine = new GameEngine(this);
		}
		
		Display.getDisplay(this).setCurrent(gameEngine);
	}

	public void showTestFPS() {
		// for testing only
		if (testFPS == null) {
			testFPS = new TestingFPS(this);
		}
		
		Display.getDisplay(this).setCurrent(testFPS);
	}

	public void showUtilities() {
		if (utilities == null) {
			utilities = new Utilities(this);
		}

		Display.getDisplay(this).setCurrent(utilities);
	}
	
	// START APP
	public void startApp() {
		splashScreen = new SplashScreen(this);
		show(splashScreen);
		splashScreen.threading();
	}

	// PAUSE APP
	public void pauseApp() {
		showExitMenu();
	}

	// DESTROY APP
	public void destroyApp(boolean unconditional) {

	}

	// commands
	public void commandAction(Command c, Displayable d) {
	
		if (c == half_times_speed) {
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
			showMainMenu();
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