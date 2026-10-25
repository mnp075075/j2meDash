package com.j2meDash.game;

import com.j2meDash.main.MainApp;
import com.j2meDash.game.*;
import com.j2meDash.game.mode.*;
import com.j2meDash.game.tools.*;
import com.j2meDash.pars.LevelParser;

import java.util.logging.Level;

import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;

public class NewGameEngine extends GameCanvas implements Runnable {
    private MainApp mainApp;
    private fpsController fps = new fpsController();
    private printer printer;
    private LevelParser lbp = new LevelParser();
    private gameMechanics gm;
    private Thread t;
    public static inputHandler inputHandler;
    
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

    public static cube cubeGM;
    public static ship shipGM;
    public static ball ballGM;
    public static ufo ufoGM;
    public static wave waveGM;
    public static robot robotGM;
    public static spider spiderGM;
    public static swing swingGM;

    // gameplay variables
    public static byte playerGamemode = 0x02;
    public static int playerX = 80; // 0 <= x <= 240 (w)
    public static int playerY = 200; // 0 <= y <= 400 (h)
    public static int playerPrintingX = 80;
    public static int playerPrintingY = 200;
    public static int playerWHazard = 20; 
    public static int playerHHazard = 20;
    public static int playerWBody = 6;
    public static int playerHBody = 6;
    public static boolean playerNormalSize = true; // normal = true; mini = false
    public static boolean playerNormalGravity = true; // normal = true; flipped = false;
    public static boolean playerInbound = false; // default value when start
    public static boolean playerGrounded = false;
    public static boolean playerCeiled = false;
    public static boolean playerFloored = false;
    public static boolean playerRoofed = false;
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
        gm = new gameMechanics(mainApp);
        int placement = 0;
        int totalObjects = 0;
        Graphics g = getGraphics();

        printer = new printer(mainApp);

        lbp.parseByte("/rsc/lvl/example.bin");
        totalObjects = LevelParser.objectNumber;
        printer.drawBackground(g);
        printer.printObject_fixed(g);

        gm.changeGamemode((byte)0x02);
        while (isRunning) {
            fps.limitFPS(60);

            printer.drawBackgroundMoving(g);
            printer.printObject_moving(g, 3);

            shipGM.update(g, playerNormalSize, playerNormalGravity);

            playerX += 3;

            while (placement < totalObjects && playerX >= MainApp.xArray[placement]) {
                placement++;
            }

            int checkIndex = (placement >= totalObjects) ? totalObjects - 1 : placement;
            while (checkIndex >= 0 && MainApp.xArray[checkIndex] >= playerX - NewGameEngine.playerWHazard) {
                int id = MainApp.idArray[checkIndex];
                gm.checkCollision(MainApp.xArray[checkIndex], MainApp.yArray[checkIndex], objWidthAndHeight[id][0], objWidthAndHeight[id][1], id);
                checkIndex--;
            }

            checkIndex = placement;
            while (checkIndex < totalObjects && MainApp.xArray[checkIndex] <= playerX + NewGameEngine.playerWHazard) {
                int id = MainApp.idArray[checkIndex];
                gm.checkCollision(MainApp.xArray[checkIndex], MainApp.yArray[checkIndex], objWidthAndHeight[id][0], objWidthAndHeight[id][1], id);
                checkIndex++;
            }

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