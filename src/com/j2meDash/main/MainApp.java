package com.j2meDash.main;

import com.j2meDash.game.*;
import com.j2meDash.menu.*;
import com.j2meDash.pars.*;

import javax.microedition.midlet.*; // the midlet, required to compile a j2me application
import javax.microedition.lcdui.*; // basically the ui, things like Forms, Alert, List, and also Canvas
import javax.microedition.media.*; // the way to play sound

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
	
	SpeedForm speedForm;
	ExitMenu exitMenu;
	SplashScreen splashScreen;
	DataRegistry dataRegistry;
	LevelBinaryParser levelBinaryParser;
	WarningScreen warningScreen;
	AboutMenu aboutMenu;
	SoundMenu soundMenu;
	MainMenu mainMenu;
	TimerScreen TimerScreen;
	PlayScreen playScreen;
	PauseScreen pauseScreen;
	GameOverScreen gameOverScreen;
	GameOverScreenSpecificallyForRestarting gameOverScreenSpecificallyForRestarting;
	GameEngine gameEngine;
	Utilities utilities;
	NewGameEngine newGameEngine;
	
	// DEFINING EVERYTHING
	public boolean SoundEnabled; // deprecated, used to control sound
	public int time = 0; // used for the new timer screen class to count time
	public int speedCount = 0; // the speed count, mandatory for controlling speed

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
	
	// ----------------------------------- //
	
	public static volatile boolean running = true; // required
	
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
	}
	
	// START APP
	public void startApp() {
		splashScreen = new SplashScreen(this);
		show(splashScreen);
	}
	
	// PAUSE APP
	public void pauseApp() {
		exitMenu = new ExitMenu(this);
		show(exitMenu);
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
			mainMenu = new MainMenu(this);
			show(mainMenu);
		}
		if (d != mainMenu) {
			running = false;
		}
	}

}