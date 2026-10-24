package com.j2meDash.game;

import com.j2meDash.main.MainApp;
import com.j2meDash.game.*;
import com.j2meDash.game.mode.*;
import com.j2meDash.game.tools.*;
import com.j2meDash.pars.LevelParser;

import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;

public class NewGameEngine extends GameCanvas implements Runnable {
    private MainApp mainApp;
    private fpsController fps = new fpsController();
    private printer printer = new printer(mainApp);
    private LevelParser lbp = new LevelParser();
    private gameMechanics gm = new gameMechanics(mainApp);
    private Thread t;
    public inputHandler inputHandler;
    
    private volatile boolean isRunning;
    private int[][] objWidthAndHeight = { 	{0,0,0,0}, {21,21,21,21}, {21,21,21,21}, {21,21,21,21}, // 0,1,2,3
								{21,21,21,21}, {21,21,3,7}, {21,21,7,3}, {21,21,3,7},			    // 4,5,6,7
								{21,21,7,3}, {21,21,3,2}, {21,21,2,3}, {21,21,3,2},				    // 8,9,10,11
								{21,21,2,3}, {34,60,34,60}, {40,50,40,50}, {54,60,54,60},		    // 12,13,14,15
								{69,60,69,60}, {73,60,73,60}, {32,60,32,60}, {32,60,32,60},		    // 16,17,18,19
								{32,60,32,60}, {32,60,32,60}, {32,60,32,60}, {32,60,32,60},		    // 20,21,22,23
								{32,60,32,60}, {30,60,30,60}, {30,60,30,60}, {32,60,32,60},		    // 24,25,26,27
								{32,60,32,60}, {21,21,21,21}, {21,21,21,21}, {21,21,21,21},		    // 28,29,30,31
								{21,21,21,21}, {21,21,21,21}, {21,21,21,21}, {21,4,21,4},		    // 32,33,34,35
								{21,3,21,3}, {21,5,21,5}, {21,5,21,5}, {21,21,21,21},			    // 36,37,38,39
								{21,21,21,21}	};												    // 40
								
	private int[][] gmWidthAndHeight = {{},{},{},{},{},{},{},{}};

    private int[] srcID = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40};
	private int[] srcX_objects = {0,93}; // non-gameplay elements
	private int[] srcY_objects = {0,207}; // non-gameplay elements

	private Image[] sheets; // for objects (non-gameplay elements) and gamemodes
    private Image background; // for bg
    private Image foreground; // for fg

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
    public static byte playerGamemode = 0x01;
    public static int playerX = 80; // 0 <= x <= 240 (w)
    public static int playerY = 360; // 0 <= y <= 400 (h)
    public static int playerPrintingX = 100;
    public static int playerPrintingY = 80;
    public static int playerWHazard = 0; 
    public static int playerHHazard = 0;
    public static int playerWBody = 0;
    public static int playerHBody = 0;
    public static boolean playerNormalSize = true; // normal = true; mini = false
    public static boolean playerNormalGravity = true; // normal = true; flipped = false;
    public static boolean playerInbound = false; // default value when start
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
        int placement = 0;
        Graphics g = getGraphics();
        g.setColor(0xabcdef);
        g.fillRect(0,0,240,400);

        lbp.parseByte("/rsc/lvl/example.bin");
        printer.printObject_fixed();

        cubeGM = new cube(mainApp);
        shipGM = new ship(mainApp);
        inputHandler = shipGM;
        while (isRunning) {
            fps.limitFPS(60);
            g.fillRect(0,0,240,400);
            shipGM.update(g, playerNormalSize, playerNormalGravity);
            printer.printObject_moving(3);
            if (placement-1 >= 0) gm.checkCollision(MainApp.xArray[placement-1], MainApp.yArray[placement-1], objWidthAndHeight[MainApp.idArray[placement-1]][0], objWidthAndHeight[MainApp.idArray[placement-1]][1], MainApp.idArray[placement-1]);
            gm.checkCollision(MainApp.xArray[placement], MainApp.yArray[placement], objWidthAndHeight[MainApp.idArray[placement]][0], objWidthAndHeight[MainApp.idArray[placement]][1], MainApp.idArray[placement]);
            if (MainApp.idArray[placement+1] != 0) gm.checkCollision(MainApp.xArray[placement+1], MainApp.yArray[placement+1], objWidthAndHeight[MainApp.idArray[placement+1]][0], objWidthAndHeight[MainApp.idArray[placement+1]][1], MainApp.idArray[placement+1]);
            System.out.println("[DEBUG]/[PLAYER] playerX=" + playerX + " playerY=" + playerY + " placement=" + placement);
            if ((playerX += 3) >= MainApp.xArray[placement]) placement++;
            flushGraphics();
        }
    }

    // POINTERS
    public void pointerPressed(int x, int y) {
        inputHandler.pointerPressed(x, y);
    }

    public void pointerReleased(int x, int y) {
        inputHandler.pointerReleased(x, y);
    }
}