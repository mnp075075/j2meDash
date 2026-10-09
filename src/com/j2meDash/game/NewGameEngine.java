package com.j2meDash.game;

import com.j2meDash.game.*;
import com.j2meDash.game.mode.*;
import com.j2meDash.main.MainApp;
import com.j2meDash.game.tools.inputHandler;
import com.j2meDash.pars.LevelBinaryParser;
import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;

public class NewGameEngine extends GameCanvas implements Runnable {
    private MainApp mainApp;
    private Thread t;
    public inputHandler inputHandler;
    
    private volatile boolean isRunning;
    private int[][] widthAndHeight = { 	{0,0,0,0}, {21,21,21,21}, {21,21,21,21}, {21,21,21,21}, // 0,1,2,3
								{21,21,21,21}, {21,21,3,7}, {21,21,7,3}, {21,21,3,7},			// 4,5,6,7
								{21,21,7,3}, {21,21,3,2}, {21,21,2,3}, {21,21,3,2},				// 8,9,10,11
								{21,21,2,3}, {34,60,34,60}, {40,50,40,50}, {54,60,54,60},		// 12,13,14,15
								{69,60,69,60}, {73,60,73,60}, {32,60,32,60}, {32,60,32,60},		// 16,17,18,19
								{32,60,32,60}, {32,60,32,60}, {32,60,32,60}, {32,60,32,60},		// 20,21,22,23
								{32,60,32,60}, {30,60,30,60}, {30,60,30,60}, {32,60,32,60},		// 24,25,26,27
								{32,60,32,60}, {21,21,21,21}, {21,21,21,21}, {21,21,21,21},		// 28,29,30,31
								{21,21,21,21}, {21,21,21,21}, {21,21,21,21}, {21,4,21,4},		// 32,33,34,35
								{21,3,21,3}, {21,5,21,5}, {21,5,21,5}, {21,21,21,21},			// 36,37,38,39
								{21,21,21,21}	};												// 40
								
	// private int[][] gamemodeWidthAndHeight = {{21,21,10,10},{21,21,10,10},{16,16,8,8},{21,21,10,10},{21,21,10,10},{21,21,10,10}}; // add the mini gamemode w and h here

    // private int[][] placeholder1;
    // private int[][] placeholder2;
    private int[] srcID = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40};
	private int[] srcX_objects = {0,93}; // non-gameplay elements
	private int[] srcY_objects = {0,207}; // non-gameplay elements

	private Image[] sheets; // for objects (non-gameplay elements) and gamemodes
    private Image background; // for bg
    private Image foreground; // for fg

    // gamemodes (width-height + width-height for hitbox + srcX_objects-srcY_objects from spreadsheet)
    /*
    private int[][] cube_mode;
    private int[][] ship_mode;
    private int[][] ball_mode;
    private int[][] ufo_mode;
    private int[][] wave_mode;
    private int[][] robot_mode;
    private int[][] spider_mode;
    private int[][] swing_mode;
    */

    // gameplay elements (individual images)
    private Image[] orbs;
    private Image[] pads;
    private Image[] portals;

    // fonts (width-height + srcX_objects-srcY_objects from spreadsheet)
    public Image[] fonts;
    public int[][] font1;
    public int[][] font2;
    public int[][] font3;
    public int[][] font4;

    public cube cubeGM;
    public ship shipGM;
    public ball ballGM;
    public ufo ufoGM;
    public wave waveGM;
    public robot robotGM;
    public spider spiderGM;
    public swing swingGM;

    // gameplay variables
    public int playerX = 100; // 0 <= x <= 240 (w)
    public int playerY = 80; // 0 <= y <= 400 (h)
    public boolean playerNormalSize = true; // normal = true; mini = false
    public boolean playerNormalGravity = true; // normal = true; flipped = false;
    public byte playerGamemode = 0x01;
    /*
    cube = 0x01
    ship = 0x02
    ball = 0x03
    ufo = 0x04
    wave = 0x05
    robot = 0x06
    spider = 0x07
    swing = 0x08
    */
    public NewGameEngine(MainApp mainApp) {
        super(true);
        this.mainApp = mainApp;
    }

    public void showNotify() {
        isRunning = true;
        t = new Thread(this);
        t.start();
    }

    public void hideNotify() {
        isRunning = false;
        t = null;
    }

    // TEST CHAMBER
    // THIS IS ONLY FOR TESTING, OTHER THAN THAT THE COMPONENTS INSIDE THIS JAVA FILE WILL BE USED INSIDE PLAY SCREEN
    public void run() {
        // put some code here
    }

    // POINTERS
    public void pointerPressed(int x, int y) {
        inputHandler.pointerPressed(x, y);
    }

    public void pointerReleased(int x, int y) {
        inputHandler.pointerReleased(x, y);
    }
}