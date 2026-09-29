package com.j2meDash.game;

import com.j2meDash.game.mode.cube;
import com.j2meDash.main.MainApp;
import com.j2meDash.game.tools.inputHandler;
import com.j2meDash.pars.LevelBinaryParser;
import java.io.*;

import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;

// TODO: add resources and images
public class NewGameEngine extends GameCanvas implements Runnable {
    private MainApp mainApp;
    private LevelBinaryParser levelBinaryParser;
    private Thread t;
    private inputHandler inputHandler;
    
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
								
	private int[][] gamemodeWidthAndHeight = {{21,21,10,10},{21,21,10,10},{16,16,8,8},{21,21,10,10},{21,21,10,10},{21,21,10,10}}; // add the mini gamemode w and h here

    private int[][] placeholder1;
    private int[][] placeholder2;
    private int[] srcID = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40};
	private int[] srcX_objects = {0,93}; // non-gameplay elements
	private int[] srcY_objects = {0,207}; // non-gameplay elements

	private Image[] sheets; // for objects (non-gameplay elements) and gamemodes
    private Image background; // for bg
    private Image foreground; // for fg

    // gamemodes (width-height + width-height for hitbox + srcX_objects-srcY_objects from spreadsheet)
    private int[][] cube_mode;
    private int[][] ship_mode;
    private int[][] ball_mode;
    private int[][] ufo_mode;
    private int[][] wave_mode;
    private int[][] robot_mode;
    private int[][] spider_mode;
    private int[][] swing_mode;

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

    // other variables
    private boolean drawn = false;
    private int reductionA = 0;
    private int reductionB = 0;

    // gameplay variables
    public int playerX = 100; // 0 <= x <= 240 (w)
    public int playerY = 80; // 0 <= y <= 400 (h)
    public boolean playerNormalSize = true; // normal = true; mini = false
    public boolean playerNormalGravity = true; // normal = true; flipped = false;
    public byte gamemode = 0x01;
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
    
    // gamemode variables
    // cube

    // ship

    // ball

    // ufo

    // wave
    public boolean isHolding_wave = false;

    // robot

    // spider

    // swing

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

    // FPS CONFIGURATION
    private int frameCount = 0;
    private int displayFPS = 0;
    private long start_limit = System.currentTimeMillis();
    private long start_count = System.currentTimeMillis();

    public void limitFPS(int fpsLimit) {
        if (fpsLimit <= 0) return;
        long target = 1000 / fpsLimit;
        long now = System.currentTimeMillis();
        long duration = now - start_limit;
        long sleep = target - duration;
        if (sleep > 0) {
            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {}
        }
        start_limit = System.currentTimeMillis(); 
    }

    public void printFPS() {
        Graphics g = getGraphics();
        long now = System.currentTimeMillis();
        if (now - start_count >= 1000) {
            displayFPS = frameCount;
            frameCount = 0;
            start_count = now;
        }
        g.setColor(0x000000);
        g.fillRect(0,0,100,100);
        g.setColor(0xffffff);
        g.drawString("FPS: " + displayFPS, 0, 0, Graphics.LEFT | Graphics.TOP);
        flushGraphics();
        // System.out.println("FPS = " + displayFPS);
        frameCount++;
    }

    public void drawingCharString(Graphics g, Image fontstrip, String s, int x, int y, int[][] metrics) {
        // note: metrics[][] contains width and height of each character and its srcX_objects and srcY_objects on the font strip
    }

    // PRINTING FUNCTION
    public void printObject_fixed() {
		Graphics g = getGraphics();
		Image[] objects = new Image[8];
		int additionalPrintingTime = levelBinaryParser.objectNumber % 8;
		int groupedPrintingTime = levelBinaryParser.objectNumber - additionalPrintingTime;
		
		for (int i = 0; i < groupedPrintingTime; i += 8) {
			
			if (mainApp.data[32+(8*i)] >= srcX_objects.length && mainApp.data[32+8*i] >= srcY_objects.length) {
				System.out.println("stop checking because of developer failcheck");
				break;
			}

			int id = mainApp.data[32+(8*i)];
			int firstCurrentID = levelBinaryParser.idArray[i];
			int secondCurrentID = levelBinaryParser.idArray[i+1];
			int thirdCurrentID = levelBinaryParser.idArray[i+2];
			int fourthCurrentID = levelBinaryParser.idArray[i+3];
			int fifthCurrentID = levelBinaryParser.idArray[i+4];
			int sixthCurrentID = levelBinaryParser.idArray[i+5];
			int seventhCurrentID = levelBinaryParser.idArray[i+6];
			int eighthCurrentID = levelBinaryParser.idArray[i+7];

            for (int offset = 0; offset < 8; offset++) {
                switch (levelBinaryParser.parArray[i+offset]) {
                    case 0x01:
                        objects[offset] = sheets[0];
                        break;
                    case 0x02:
                        objects[offset] = orbs[levelBinaryParser.idArray[i+offset]-50];
                        break;
                    case 0x03:
                        objects[offset] = pads[levelBinaryParser.idArray[i+offset]-50];
                        break;
                    case 0x04:
                        objects[offset] = portals[levelBinaryParser.idArray[i+offset]-50];
                        break;
                }
            }
			
			if (id > srcID[0] && id <= srcID[srcID.length-1] && levelBinaryParser.parArray[i] == 0x01) {
				g.drawRegion(objects[0], srcX_objects[firstCurrentID], srcY_objects[firstCurrentID], widthAndHeight[firstCurrentID][0], widthAndHeight[firstCurrentID][1], Sprite.TRANS_NONE, levelBinaryParser.xArray[i], levelBinaryParser.yArray[i], Graphics.RIGHT | Graphics.BOTTOM);
				g.drawRegion(objects[1], srcX_objects[secondCurrentID], srcY_objects[secondCurrentID], widthAndHeight[secondCurrentID][0], widthAndHeight[secondCurrentID][1], Sprite.TRANS_NONE, levelBinaryParser.xArray[i+1], levelBinaryParser.yArray[i+1], Graphics.RIGHT | Graphics.BOTTOM);
				g.drawRegion(objects[2], srcX_objects[thirdCurrentID], srcY_objects[thirdCurrentID], widthAndHeight[thirdCurrentID][0], widthAndHeight[thirdCurrentID][1], Sprite.TRANS_NONE, levelBinaryParser.xArray[i+2], levelBinaryParser.yArray[i+2], Graphics.RIGHT | Graphics.BOTTOM);
				g.drawRegion(objects[3], srcX_objects[fourthCurrentID], srcY_objects[fourthCurrentID], widthAndHeight[fourthCurrentID][0], widthAndHeight[fourthCurrentID][1], Sprite.TRANS_NONE, levelBinaryParser.xArray[i+3], levelBinaryParser.yArray[i+3], Graphics.RIGHT | Graphics.BOTTOM);
				g.drawRegion(objects[4], srcX_objects[fifthCurrentID], srcY_objects[fifthCurrentID], widthAndHeight[fifthCurrentID][0], widthAndHeight[fifthCurrentID][1], Sprite.TRANS_NONE, levelBinaryParser.xArray[i+4], levelBinaryParser.yArray[i+4], Graphics.RIGHT | Graphics.BOTTOM);
				g.drawRegion(objects[5], srcX_objects[sixthCurrentID], srcY_objects[sixthCurrentID], widthAndHeight[sixthCurrentID][0], widthAndHeight[sixthCurrentID][1], Sprite.TRANS_NONE, levelBinaryParser.xArray[i+5], levelBinaryParser.yArray[i+5], Graphics.RIGHT | Graphics.BOTTOM);
				g.drawRegion(objects[6], srcX_objects[seventhCurrentID], srcY_objects[seventhCurrentID], widthAndHeight[seventhCurrentID][0], widthAndHeight[seventhCurrentID][1], Sprite.TRANS_NONE, levelBinaryParser.xArray[i+6], levelBinaryParser.yArray[i+6], Graphics.RIGHT | Graphics.BOTTOM);
				g.drawRegion(objects[7], srcX_objects[eighthCurrentID], srcY_objects[eighthCurrentID], widthAndHeight[eighthCurrentID][0], widthAndHeight[eighthCurrentID][1], Sprite.TRANS_NONE, levelBinaryParser.xArray[i+7], levelBinaryParser.yArray[i+7], Graphics.RIGHT | Graphics.BOTTOM);
			} else if (id > srcID[0] && id <= srcID[srcID.length-1] && levelBinaryParser.parArray[i] != 0x01) {
                g.drawImage(objects[0], levelBinaryParser.xArray[i], levelBinaryParser.yArray[i], Graphics.RIGHT | Graphics.BOTTOM);
                g.drawImage(objects[1], levelBinaryParser.xArray[i+1], levelBinaryParser.yArray[i+1], Graphics.RIGHT | Graphics.BOTTOM);
                g.drawImage(objects[2], levelBinaryParser.xArray[i+2], levelBinaryParser.yArray[i+2], Graphics.RIGHT | Graphics.BOTTOM);
                g.drawImage(objects[3], levelBinaryParser.xArray[i+3], levelBinaryParser.yArray[i+3], Graphics.RIGHT | Graphics.BOTTOM);
                g.drawImage(objects[4], levelBinaryParser.xArray[i+4], levelBinaryParser.yArray[i+4], Graphics.RIGHT | Graphics.BOTTOM);
                g.drawImage(objects[5], levelBinaryParser.xArray[i+5], levelBinaryParser.yArray[i+5], Graphics.RIGHT | Graphics.BOTTOM);
                g.drawImage(objects[6], levelBinaryParser.xArray[i+6], levelBinaryParser.yArray[i+6], Graphics.RIGHT | Graphics.BOTTOM);
                g.drawImage(objects[7], levelBinaryParser.xArray[i+7], levelBinaryParser.yArray[i+7], Graphics.RIGHT | Graphics.BOTTOM);
            }
			
		}
			
		switch (additionalPrintingTime % 4) {
			case 0:
				for (int i = groupedPrintingTime; i < levelBinaryParser.objectNumber; i += 4) {
					
					int id = mainApp.data[32+(8*i)];
					int firstCurrentID = levelBinaryParser.idArray[i];
					int secondCurrentID = levelBinaryParser.idArray[i+1];
					int thirdCurrentID = levelBinaryParser.idArray[i+2];
					int fourthCurrentID = levelBinaryParser.idArray[i+3];

                    for (int offset = 0; offset < 4; offset++) {
                        switch (levelBinaryParser.parArray[i+offset]) {
                            case 0x01:
                                objects[offset] = sheets[0];
                                break;
                            case 0x02:
                                objects[offset] = orbs[levelBinaryParser.idArray[i+offset]-50];
                                break;
                            case 0x03:
                                objects[offset] = pads[levelBinaryParser.idArray[i+offset]-50];
                                break;
                            case 0x04:
                                objects[offset] = portals[levelBinaryParser.idArray[i+offset]-50];
                                break;
                        }
                    }
					
					if (id > srcID[0] && id <= srcID[srcID.length-1] && levelBinaryParser.parArray[i] == 0x01) {
                        g.drawRegion(objects[0], srcX_objects[firstCurrentID], srcY_objects[firstCurrentID], widthAndHeight[firstCurrentID][0], widthAndHeight[firstCurrentID][1], Sprite.TRANS_NONE, levelBinaryParser.xArray[i], levelBinaryParser.yArray[i], Graphics.RIGHT | Graphics.BOTTOM);
                        g.drawRegion(objects[1], srcX_objects[secondCurrentID], srcY_objects[secondCurrentID], widthAndHeight[secondCurrentID][0], widthAndHeight[secondCurrentID][1], Sprite.TRANS_NONE, levelBinaryParser.xArray[i+1], levelBinaryParser.yArray[i+1], Graphics.RIGHT | Graphics.BOTTOM);
                        g.drawRegion(objects[2], srcX_objects[thirdCurrentID], srcY_objects[thirdCurrentID], widthAndHeight[thirdCurrentID][0], widthAndHeight[thirdCurrentID][1], Sprite.TRANS_NONE, levelBinaryParser.xArray[i+2], levelBinaryParser.yArray[i+2], Graphics.RIGHT | Graphics.BOTTOM);
                        g.drawRegion(objects[3], srcX_objects[fourthCurrentID], srcY_objects[fourthCurrentID], widthAndHeight[fourthCurrentID][0], widthAndHeight[fourthCurrentID][1], Sprite.TRANS_NONE, levelBinaryParser.xArray[i+3], levelBinaryParser.yArray[i+3], Graphics.RIGHT | Graphics.BOTTOM);
                    } else if (id > srcID[0] && id <= srcID[srcID.length-1] && levelBinaryParser.parArray[i] != 0x01) {
                        g.drawImage(objects[0], levelBinaryParser.xArray[i], levelBinaryParser.yArray[i], Graphics.RIGHT | Graphics.BOTTOM);
                        g.drawImage(objects[1], levelBinaryParser.xArray[i+1], levelBinaryParser.yArray[i+1], Graphics.RIGHT | Graphics.BOTTOM);
                        g.drawImage(objects[2], levelBinaryParser.xArray[i+2], levelBinaryParser.yArray[i+2], Graphics.RIGHT | Graphics.BOTTOM);
                        g.drawImage(objects[3], levelBinaryParser.xArray[i+3], levelBinaryParser.yArray[i+3], Graphics.RIGHT | Graphics.BOTTOM);
                    }
				}
				break;
			case 2:
				for (int i = groupedPrintingTime; i < levelBinaryParser.objectNumber; i += 2) {
					
					int id = mainApp.data[32+(8*i)];
					int firstCurrentID = levelBinaryParser.idArray[i];
					int secondCurrentID = levelBinaryParser.idArray[i+1];

                    for (int offset = 0; offset < 2; offset++) {
                        switch (levelBinaryParser.parArray[i+offset]) {
                            case 0x01:
                                objects[offset] = sheets[0];
                                break;
                            case 0x02:
                                objects[offset] = orbs[levelBinaryParser.idArray[i+offset]-50];
                                break;
                            case 0x03:
                                objects[offset] = pads[levelBinaryParser.idArray[i+offset]-50];
                                break;
                            case 0x04:
                                objects[offset] = portals[levelBinaryParser.idArray[i+offset]-50];
                                break;
                        }
                    }
					
					if (id > srcID[0] && id <= srcID[srcID.length-1] && levelBinaryParser.parArray[i] == 0x01) {
                        g.drawRegion(objects[0], srcX_objects[firstCurrentID], srcY_objects[firstCurrentID], widthAndHeight[firstCurrentID][0], widthAndHeight[firstCurrentID][1], Sprite.TRANS_NONE, levelBinaryParser.xArray[i], levelBinaryParser.yArray[i], Graphics.RIGHT | Graphics.BOTTOM);
                        g.drawRegion(objects[1], srcX_objects[secondCurrentID], srcY_objects[secondCurrentID], widthAndHeight[secondCurrentID][0], widthAndHeight[secondCurrentID][1], Sprite.TRANS_NONE, levelBinaryParser.xArray[i+1], levelBinaryParser.yArray[i+1], Graphics.RIGHT | Graphics.BOTTOM);
                    } else if (id > srcID[0] && id <= srcID[srcID.length-1] && levelBinaryParser.parArray[i] != 0x01) {
                        g.drawImage(objects[0], levelBinaryParser.xArray[i], levelBinaryParser.yArray[i], Graphics.RIGHT | Graphics.BOTTOM);
                        g.drawImage(objects[1], levelBinaryParser.xArray[i+1], levelBinaryParser.yArray[i+1], Graphics.RIGHT | Graphics.BOTTOM);
                    }
				}
				break;
			case 1:
			case 3:
				for (int i = groupedPrintingTime; i < levelBinaryParser.objectNumber; i += 1) {
					
					int id = mainApp.data[32+(8*i)];
					int firstCurrentID = levelBinaryParser.idArray[i];
					
                    for (int offset = 0; offset < additionalPrintingTime; offset++) {
                        switch (levelBinaryParser.parArray[i+offset]) {
                            case 0x01:
                                objects[offset] = sheets[0];
                                break;
                            case 0x02:
                                objects[offset] = orbs[levelBinaryParser.idArray[i+offset]-50];
                                break;
                            case 0x03:
                                objects[offset] = pads[levelBinaryParser.idArray[i+offset]-50];
                                break;
                            case 0x04:
                                objects[offset] = portals[levelBinaryParser.idArray[i+offset]-50];
                                break;
                        }
                    }

					if (id > srcID[0] && id <= srcID[srcID.length-1] && levelBinaryParser.parArray[i] == 0x01) {
                        g.drawRegion(objects[0], srcX_objects[firstCurrentID], srcY_objects[firstCurrentID], widthAndHeight[firstCurrentID][0], widthAndHeight[firstCurrentID][1], Sprite.TRANS_NONE, levelBinaryParser.xArray[i], levelBinaryParser.yArray[i], Graphics.RIGHT | Graphics.BOTTOM);
                    } else if (id > srcID[0] && id <= srcID[srcID.length-1] && levelBinaryParser.parArray[i] != 0x01) {
                        g.drawImage(objects[0], levelBinaryParser.xArray[i], levelBinaryParser.yArray[i], Graphics.RIGHT | Graphics.BOTTOM);
                    }
				}
				break;
			default:
				break;
		}
		
	}
	
	public void printObject_moving(int offset) {
		Graphics g = getGraphics();
		Image[] objects = new Image[8];
		int additionalPrintingTime = levelBinaryParser.objectNumber % 8;
		int groupedPrintingTime = levelBinaryParser.objectNumber - additionalPrintingTime;
		
		for (int i = 0; i < groupedPrintingTime; i += 8) {
			
			if (mainApp.data[32+(8*i)] >= srcX_objects.length && mainApp.data[32+8*i] >= srcY_objects.length) {
				System.out.println("stop checking because of developer failcheck");
				break;
			}

			int id = mainApp.data[32+(8*i)];
			int firstCurrentID = levelBinaryParser.idArray[i];
			int secondCurrentID = levelBinaryParser.idArray[i+1];
			int thirdCurrentID = levelBinaryParser.idArray[i+2];
			int fourthCurrentID = levelBinaryParser.idArray[i+3];
			int fifthCurrentID = levelBinaryParser.idArray[i+4];
			int sixthCurrentID = levelBinaryParser.idArray[i+5];
			int seventhCurrentID = levelBinaryParser.idArray[i+6];
			int eighthCurrentID = levelBinaryParser.idArray[i+7];

            for (int n = 0; n < 8; n++) {
                switch (levelBinaryParser.parArray[i+n]) {
                    case 0x01:
                        objects[n] = sheets[0];
                        break;
                    case 0x02:
                        objects[n] = orbs[levelBinaryParser.idArray[i+n]-50];
                        break;
                    case 0x03:
                        objects[n] = pads[levelBinaryParser.idArray[i+n]-50];
                        break;
                    case 0x04:
                        objects[n] = portals[levelBinaryParser.idArray[i+n]-50];
                        break;
                }
            }
			
			if (id > srcID[0] && id <= srcID[srcID.length-1] && levelBinaryParser.parArray[i] == 0x01) {
				g.drawRegion(objects[0], srcX_objects[firstCurrentID], srcY_objects[firstCurrentID], widthAndHeight[firstCurrentID][0], widthAndHeight[firstCurrentID][1], Sprite.TRANS_NONE, levelBinaryParser.xArray[i] - offset, levelBinaryParser.yArray[i], Graphics.RIGHT | Graphics.BOTTOM);
				g.drawRegion(objects[1], srcX_objects[secondCurrentID], srcY_objects[secondCurrentID], widthAndHeight[secondCurrentID][0], widthAndHeight[secondCurrentID][1], Sprite.TRANS_NONE, levelBinaryParser.xArray[i+1] - offset, levelBinaryParser.yArray[i+1], Graphics.RIGHT | Graphics.BOTTOM);
				g.drawRegion(objects[2], srcX_objects[thirdCurrentID], srcY_objects[thirdCurrentID], widthAndHeight[thirdCurrentID][0], widthAndHeight[thirdCurrentID][1], Sprite.TRANS_NONE, levelBinaryParser.xArray[i+2] - offset, levelBinaryParser.yArray[i+2], Graphics.RIGHT | Graphics.BOTTOM);
				g.drawRegion(objects[3], srcX_objects[fourthCurrentID], srcY_objects[fourthCurrentID], widthAndHeight[fourthCurrentID][0], widthAndHeight[fourthCurrentID][1], Sprite.TRANS_NONE, levelBinaryParser.xArray[i+3] - offset, levelBinaryParser.yArray[i+3], Graphics.RIGHT | Graphics.BOTTOM);
				g.drawRegion(objects[4], srcX_objects[fifthCurrentID], srcY_objects[fifthCurrentID], widthAndHeight[fifthCurrentID][0], widthAndHeight[fifthCurrentID][1], Sprite.TRANS_NONE, levelBinaryParser.xArray[i+4] - offset, levelBinaryParser.yArray[i+4], Graphics.RIGHT | Graphics.BOTTOM);
				g.drawRegion(objects[5], srcX_objects[sixthCurrentID], srcY_objects[sixthCurrentID], widthAndHeight[sixthCurrentID][0], widthAndHeight[sixthCurrentID][1], Sprite.TRANS_NONE, levelBinaryParser.xArray[i+5] - offset, levelBinaryParser.yArray[i+5], Graphics.RIGHT | Graphics.BOTTOM);
				g.drawRegion(objects[6], srcX_objects[seventhCurrentID], srcY_objects[seventhCurrentID], widthAndHeight[seventhCurrentID][0], widthAndHeight[seventhCurrentID][1], Sprite.TRANS_NONE, levelBinaryParser.xArray[i+6] - offset, levelBinaryParser.yArray[i+6], Graphics.RIGHT | Graphics.BOTTOM);
				g.drawRegion(objects[7], srcX_objects[eighthCurrentID], srcY_objects[eighthCurrentID], widthAndHeight[eighthCurrentID][0], widthAndHeight[eighthCurrentID][1], Sprite.TRANS_NONE, levelBinaryParser.xArray[i+7] - offset, levelBinaryParser.yArray[i+7], Graphics.RIGHT | Graphics.BOTTOM);
			} else if (id > srcID[0] && id <= srcID[srcID.length-1] && levelBinaryParser.parArray[i] != 0x01) {
                g.drawImage(objects[0], levelBinaryParser.xArray[i] - offset, levelBinaryParser.yArray[i], Graphics.RIGHT | Graphics.BOTTOM);
                g.drawImage(objects[1], levelBinaryParser.xArray[i+1] - offset, levelBinaryParser.yArray[i+1], Graphics.RIGHT | Graphics.BOTTOM);
                g.drawImage(objects[2], levelBinaryParser.xArray[i+2] - offset, levelBinaryParser.yArray[i+2], Graphics.RIGHT | Graphics.BOTTOM);
                g.drawImage(objects[3], levelBinaryParser.xArray[i+3] - offset, levelBinaryParser.yArray[i+3], Graphics.RIGHT | Graphics.BOTTOM);
                g.drawImage(objects[4], levelBinaryParser.xArray[i+4] - offset, levelBinaryParser.yArray[i+4], Graphics.RIGHT | Graphics.BOTTOM);
                g.drawImage(objects[5], levelBinaryParser.xArray[i+5] - offset, levelBinaryParser.yArray[i+5], Graphics.RIGHT | Graphics.BOTTOM);
                g.drawImage(objects[6], levelBinaryParser.xArray[i+6] - offset, levelBinaryParser.yArray[i+6], Graphics.RIGHT | Graphics.BOTTOM);
                g.drawImage(objects[7], levelBinaryParser.xArray[i+7] - offset, levelBinaryParser.yArray[i+7], Graphics.RIGHT | Graphics.BOTTOM);
            }
			
		}
			
		switch (additionalPrintingTime % 4) {
			case 0:
				for (int i = groupedPrintingTime; i < levelBinaryParser.objectNumber; i += 4) {
					
					int id = mainApp.data[32+(8*i)];
					int firstCurrentID = levelBinaryParser.idArray[i];
					int secondCurrentID = levelBinaryParser.idArray[i+1];
					int thirdCurrentID = levelBinaryParser.idArray[i+2];
					int fourthCurrentID = levelBinaryParser.idArray[i+3];

                    for (int n = 0; n < 4; n++) {
                        switch (levelBinaryParser.parArray[i+n]) {
                            case 0x01:
                                objects[n] = sheets[0];
                                break;
                            case 0x02:
                                objects[n] = orbs[levelBinaryParser.idArray[i+n]-50];
                                break;
                            case 0x03:
                                objects[n] = pads[levelBinaryParser.idArray[i+n]-50];
                                break;
                            case 0x04:
                                objects[n] = portals[levelBinaryParser.idArray[i+n]-50];
                                break;
                        }
                    }
					
					if (id > srcID[0] && id <= srcID[srcID.length-1] && levelBinaryParser.parArray[i] == 0x01) {
                        g.drawRegion(objects[0], srcX_objects[firstCurrentID], srcY_objects[firstCurrentID], widthAndHeight[firstCurrentID][0], widthAndHeight[firstCurrentID][1], Sprite.TRANS_NONE, levelBinaryParser.xArray[i] - offset, levelBinaryParser.yArray[i], Graphics.RIGHT | Graphics.BOTTOM);
                        g.drawRegion(objects[1], srcX_objects[secondCurrentID], srcY_objects[secondCurrentID], widthAndHeight[secondCurrentID][0], widthAndHeight[secondCurrentID][1], Sprite.TRANS_NONE, levelBinaryParser.xArray[i+1] - offset, levelBinaryParser.yArray[i+1], Graphics.RIGHT | Graphics.BOTTOM);
                        g.drawRegion(objects[2], srcX_objects[thirdCurrentID], srcY_objects[thirdCurrentID], widthAndHeight[thirdCurrentID][0], widthAndHeight[thirdCurrentID][1], Sprite.TRANS_NONE, levelBinaryParser.xArray[i+2] - offset, levelBinaryParser.yArray[i+2], Graphics.RIGHT | Graphics.BOTTOM);
                        g.drawRegion(objects[3], srcX_objects[fourthCurrentID], srcY_objects[fourthCurrentID], widthAndHeight[fourthCurrentID][0], widthAndHeight[fourthCurrentID][1], Sprite.TRANS_NONE, levelBinaryParser.xArray[i+3] - offset, levelBinaryParser.yArray[i+3], Graphics.RIGHT | Graphics.BOTTOM);
                    } else if (id > srcID[0] && id <= srcID[srcID.length-1] && levelBinaryParser.parArray[i] != 0x01) {
                        g.drawImage(objects[0], levelBinaryParser.xArray[i] - offset, levelBinaryParser.yArray[i], Graphics.RIGHT | Graphics.BOTTOM);
                        g.drawImage(objects[1], levelBinaryParser.xArray[i+1] - offset, levelBinaryParser.yArray[i+1], Graphics.RIGHT | Graphics.BOTTOM);
                        g.drawImage(objects[2], levelBinaryParser.xArray[i+2] - offset, levelBinaryParser.yArray[i+2], Graphics.RIGHT | Graphics.BOTTOM);
                        g.drawImage(objects[3], levelBinaryParser.xArray[i+3] - offset, levelBinaryParser.yArray[i+3], Graphics.RIGHT | Graphics.BOTTOM);
                    }
				}
				break;
			case 2:
				for (int i = groupedPrintingTime; i < levelBinaryParser.objectNumber; i += 2) {
					
					int id = mainApp.data[32+(8*i)];
					int firstCurrentID = levelBinaryParser.idArray[i];
					int secondCurrentID = levelBinaryParser.idArray[i+1];

                    for (int n = 0; n < 2; n++) {
                        switch (levelBinaryParser.parArray[i+n]) {
                            case 0x01:
                                objects[n] = sheets[0];
                                break;
                            case 0x02:
                                objects[n] = orbs[levelBinaryParser.idArray[i+n]-50];
                                break;
                            case 0x03:
                                objects[n] = pads[levelBinaryParser.idArray[i+n]-50];
                                break;
                            case 0x04:
                                objects[n] = portals[levelBinaryParser.idArray[i+n]-50];
                                break;
                        }
                    }
					
					if (id > srcID[0] && id <= srcID[srcID.length-1] && levelBinaryParser.parArray[i] == 0x01) {
                        g.drawRegion(objects[0], srcX_objects[firstCurrentID], srcY_objects[firstCurrentID], widthAndHeight[firstCurrentID][0], widthAndHeight[firstCurrentID][1], Sprite.TRANS_NONE, levelBinaryParser.xArray[i] - offset, levelBinaryParser.yArray[i], Graphics.RIGHT | Graphics.BOTTOM);
                        g.drawRegion(objects[1], srcX_objects[secondCurrentID], srcY_objects[secondCurrentID], widthAndHeight[secondCurrentID][0], widthAndHeight[secondCurrentID][1], Sprite.TRANS_NONE, levelBinaryParser.xArray[i+1] - offset, levelBinaryParser.yArray[i+1], Graphics.RIGHT | Graphics.BOTTOM);
                    } else if (id > srcID[0] && id <= srcID[srcID.length-1] && levelBinaryParser.parArray[i] != 0x01) {
                        g.drawImage(objects[0], levelBinaryParser.xArray[i] - offset, levelBinaryParser.yArray[i], Graphics.RIGHT | Graphics.BOTTOM);
                        g.drawImage(objects[1], levelBinaryParser.xArray[i+1] - offset, levelBinaryParser.yArray[i+1], Graphics.RIGHT | Graphics.BOTTOM);
                    }
				}
				break;
			case 1:
			case 3:
				for (int i = groupedPrintingTime; i < levelBinaryParser.objectNumber; i += 1) {
					
					int id = mainApp.data[32+(8*i)];
					int firstCurrentID = levelBinaryParser.idArray[i];
					
                    for (int n = 0; n < additionalPrintingTime; n++) {
                        switch (levelBinaryParser.parArray[i+n]) {
                            case 0x01:
                                objects[n] = sheets[0];
                                break;
                            case 0x02:
                                objects[n] = orbs[levelBinaryParser.idArray[i+n]-50];
                                break;
                            case 0x03:
                                objects[n] = pads[levelBinaryParser.idArray[i+n]-50];
                                break;
                            case 0x04:
                                objects[n] = portals[levelBinaryParser.idArray[i+n]-50];
                                break;
                        }
                    }

					if (id > srcID[0] && id <= srcID[srcID.length-1] && levelBinaryParser.parArray[i] == 0x01) {
                        g.drawRegion(objects[0], srcX_objects[firstCurrentID], srcY_objects[firstCurrentID], widthAndHeight[firstCurrentID][0], widthAndHeight[firstCurrentID][1], Sprite.TRANS_NONE, levelBinaryParser.xArray[i] - offset, levelBinaryParser.yArray[i], Graphics.RIGHT | Graphics.BOTTOM);
                    } else if (id > srcID[0] && id <= srcID[srcID.length-1] && levelBinaryParser.parArray[i] != 0x01) {
                        g.drawImage(objects[0], levelBinaryParser.xArray[i] - offset, levelBinaryParser.yArray[i], Graphics.RIGHT | Graphics.BOTTOM);
                    }
				}
				break;
			default:
				break;
		}

	}

    public void initializeEverything() {
        try {
            // spreadsheets
            sheets = new Image[] {
                Image.createImage("/rsc/img/sheets/objects.png"),
                Image.createImage("/rsc/img/sheets/cube.png"),
                Image.createImage("/rsc/img/sheets/ship.png"),
                Image.createImage("/rsc/img/sheets/ball.png"),
                Image.createImage("/rsc/img/sheets/ufo.png"),
                Image.createImage("/rsc/img/sheets/wave.png"),
                Image.createImage("/rsc/img/sheets/robot.png"),
                Image.createImage("/rsc/img/sheets/spider.png"),
                Image.createImage("/rsc/img/sheets/swing.png")
            };

            // bg elements
            background = Image.createImage("/rsc/img/bg/bg.png");
            foreground = Image.createImage("/rsc/img/bg/fg.png");

            // orbs
            orbs = new Image[] {
                Image.createImage("/rsc/img/orbs/yellow_orb.png"),
                Image.createImage("/rsc/img/orbs/pink_orb.png"),
                Image.createImage("/rsc/img/orbs/red_orb.png"),
                Image.createImage("/rsc/img/orbs/blue_orb.png"),
                Image.createImage("/rsc/img/orbs/green_orb.png"),
                Image.createImage("/rsc/img/orbs/black_orb.png"),
                Image.createImage("/rsc/img/orbs/spider_orb.png")
            };

            // pads
            pads = new Image[] {
                Image.createImage("/rsc/img/pads/yellow_pad.png"),
                Image.createImage("/rsc/img/pads/red_pad.png"),
                Image.createImage("/rsc/img/pads/pink_pad.png"),
                Image.createImage("/rsc/img/pads/blue_pad.png"),
                Image.createImage("/rsc/img/pads/spider_pad.png")
            };

            // portals
            portals = new Image[] {
                Image.createImage("/rsc/img/orbs/cube_portal.png"),
                Image.createImage("/rsc/img/orbs/ship_portal.png"),
                Image.createImage("/rsc/img/orbs/ball_portal.png"),
                Image.createImage("/rsc/img/orbs/ufo_portal.png"),
                Image.createImage("/rsc/img/orbs/wave_portal.png"),
                Image.createImage("/rsc/img/orbs/robot_portal.png"),
                Image.createImage("/rsc/img/orbs/spider_portal.png"),
                Image.createImage("/rsc/img/orbs/swing_portal.png"),
                Image.createImage("/rsc/img/orbs/normalSize_portal.png"),
                Image.createImage("/rsc/img/orbs/miniSize_portal.png"),
                Image.createImage("/rsc/img/orbs/blue_portal.png"),
                Image.createImage("/rsc/img/orbs/yellow_portal.png"),
                Image.createImage("/rsc/img/orbs/green_portal.png"),
                Image.createImage("/rsc/img/orbs/halfSpeed_portal.png"),
                Image.createImage("/rsc/img/orbs/_1xSpeed_portal.png"),
                Image.createImage("/rsc/img/orbs/_2xSpeed_portal.png"),
                Image.createImage("/rsc/img/orbs/_3xSpeed_portal.png"),
                Image.createImage("/rsc/img/orbs/_4xSpeed_portal.png")
            };

            // fonts
            fonts = new Image[] {
                Image.createImage("/rsc/font/font1.png"),
                Image.createImage("/rsc/font/font2.png"),
                Image.createImage("/rsc/font/font3.png"),
                Image.createImage("/rsc/font/font4.png")
            };
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("Finish initialization");
        }
    }

    public void startPos(int x, int y, byte gm) {
        playerX = x;
        playerY = y;
        if (gamemode <= 0x08 && gamemode != 0x00) gamemode = gm;
        else System.out.println("Invalid gamemode");
    }

    public void drawBackground() {
        Graphics g = getGraphics();
        int w = getWidth();
        int h = getHeight();
        g.drawImage(background, 0, 0, Graphics.TOP | Graphics.LEFT);
        g.drawImage(foreground, 0, (int)(h*0.9), Graphics.TOP | Graphics.LEFT);
        drawn = true;
    }

    public void drawBackgroundMoving() {
        Graphics g = getGraphics();
        int w = getWidth();
        int h = getHeight();
        if (drawn) {
            g.drawImage(background, 0, 0, Graphics.TOP | Graphics.LEFT);
            g.drawImage(background, w-reductionA, 0, Graphics.TOP | Graphics.LEFT);
            g.drawImage(foreground, 0, (int)(h*0.9), Graphics.TOP | Graphics.LEFT);
            g.drawImage(foreground, w-reductionB, (int)(h*0.9), Graphics.TOP | Graphics.LEFT);
            if (reductionA > 0) {
                reductionA += 5;
            } else if (reductionB > 0) {
                reductionB += 10;
            } else if (w-reductionA == 0) {
                reductionA = 0;
            } else if (w-reductionB == 0) {
                reductionB = 0;
            }
        } else {
            System.out.println("Error");
        }
    }

    // GAMEMODES (are put into while loop)
    public void cubeMode(boolean normal_gravity, boolean normal_size, int x, int y) {
        if (normal_size) {
            if (normal_gravity) {
                /*
                boolean isJumping = true;
                int frameCount = 0;
                int[] graph_value = {6, 5, 5, 5, 4, 4, 3, 3, 3, 2, 2, 1, 1, 1, 0};
                if (isJumping) {
                    int dy = graph_value[frameCount % 15];
                    y -= (frameCount < 15) ? -dy : dy;
                    if (++frameCount >= 30) {
                        frameCount = 0;
                        isJumping = false;
                    }
                }
                */
            } else {
                /*
                boolean isJumping = true;
                int frameCount = 0;
                int[] graph_value = {6, 5, 5, 5, 4, 4, 3, 3, 3, 2, 2, 1, 1, 1, 0};
                if (isJumping) {
                    int dy = graph_value[frameCount % 15];
                    y += (frameCount < 15) ? dy : -dy;
                    if (++frameCount >= 30) {
                        frameCount = 0;
                        isJumping = false;
                    }
                } else {
                    frameCount = 0;
                }
                */
            }
        } else {
            if (normal_gravity) {
                
            } else {

            }
        }
    }

    public void shipMode(boolean normal_gravity, boolean normal_size, int x, int y) {
        if (normal_size) {
            if (normal_gravity) {
                /*
                boolean isHolding_ship = true;
                int[] graph_value = {1, 2, 3, 4};
                if (isHolding_ship) {
                    if (y != 40 && buoyancy == true) {
                        frameCount = 40;
                        buoyancy = false;
                    }
                    int dy = (frameCount < 10) ? graph_value[0] : (frameCount < 20) ? graph_value[1] : (frameCount < 30) ? graph_value[2] : graph_value[3];
                    if (--frameCount > 0) y -= dy;
                    else {
                        frameCount = 0;
                        y += dy;
                    }
                } else {
                    if (y != 360 && buoyancy == true) {
                        frameCount = 40;
                        buoyancy = false;
                    }
                    int dy = (frameCount < 10) ? graph_value[0] : (frameCount < 20) ? graph_value[1] : (frameCount < 30) ? graph_value[2] : graph_value[3];
                    if (--frameCount > 0) y += dy;
                    else {
                        frameCount = 0;
                        y -= dy;
                    }
                }
                */
            } else {
                /*
                boolean isHolding_ship = true;
                int[] graph_value = {1, 2, 3, 4};
                if (isHolding_ship) {
                    if (y != 40 && buoyancy == true) {
                        frameCount = 40;
                        buoyancy = false;
                    }
                    int dy = (frameCount < 10) ? graph_value[0] : (frameCount < 20) ? graph_value[1] : (frameCount < 30) ? graph_value[2] : graph_value[3];
                    if (--frameCount > 0) y += dy;
                    else {
                        frameCount = 0;
                        y -= dy;
                    }
                } else {
                    if (y != 360 && buoyancy == true) {
                        frameCount = 40;
                        buoyancy = false;
                    }
                    int dy = (frameCount < 10) ? graph_value[0] : (frameCount < 20) ? graph_value[1] : (frameCount < 30) ? graph_value[2] : graph_value[3];
                    if (--frameCount > 0) y -= dy;
                    else {
                        frameCount = 0;
                        y += dy;
                    }
                }
                */
            }
        } else {
            if (normal_gravity) {

            } else {
                
            }
        }
    }

    public void ballMode(boolean normal_gravity, boolean normal_size, int x, int y) {
        if (normal_size) {
            if (normal_gravity) {
                /*
                boolean isBalling = true;
                int graph_value = {1, 2, 3, 4};
                if (isBalling) {
                    int dy = (frameCount < 10) ? graph_value[0] : (frameCount < 20) ? graph_value[1] : (frameCount < 30) ? graph_value[2] : graph_value[3];
                    y -= dy;
                    if (y == 360) {
                        frameCount = 0;
                        isBalling = false;
                    }
                }
                */
            } else {
                /*
                boolean isBalling = true;
                int graph_value = {1, 2, 3, 4};
                if (isBalling) {
                    int dy = (frameCount < 10) ? graph_value[0] : (frameCount < 20) ? graph_value[1] : (frameCount < 30) ? graph_value[2] : graph_value[3];
                    y += dy;
                    if (y == 40) {
                        frameCount = 0;
                        isBalling = false;
                    }
                }
                */
            }
        } else {
            if (normal_gravity) {

            } else {

            }
        }
    }

    public void ufoMode(boolean normal_gravity, boolean normal_size, int x, int y) {
        if (normal_size) {
            if (normal_gravity) {

            } else {

            }
        } else {
            if (normal_gravity) {

            } else {

            }
        }
    }

    public void waveMode(boolean normal_gravity, boolean normal_size, int x, int y) {
        if (normal_size) {
            if (normal_gravity) {
                if (isHolding_wave) y--;
                else y++;
            } else {
                /*
                boolean isHolding_wave = false;
                if (isHolding_wave) y++;
                else y--;
                */
            }
        } else {
            if (normal_gravity) {
                /*
                boolean isHolding_wave = false;
                if (isHolding_wave) y -= 2;
                else y += 2;
                */
            } else {
                /*
                boolean isHolding_wave = false;
                if (isHolding_wave) y += 2;
                else y -= 2;
                */
            }
        }
    }

    public void robotMode(boolean normal_gravity, boolean normal_size, int x, int y) {
        if (normal_size) {
            if (normal_gravity) {

            } else {

            }
        } else {
            if (normal_gravity) {

            } else {
                
            }
        }
    }

    public void spiderMode(boolean normal_gravity, boolean normal_size, int x, int y) {
        if (normal_size) {
            if (normal_gravity) {

            } else {

            }
        } else {
            if (normal_gravity) {

            } else {
                
            }
        }
    }

    public void swingMode(boolean normal_gravity, boolean normal_size, int x, int y) {
        if (normal_size) {
            if (normal_gravity) {

            } else {

            }
        } else {
            if (normal_gravity) {

            } else {
                
            }
        }
    }

    // ORBS
    public void yellowOrb(byte gamemode, boolean normal_gravity, boolean normal_size, int x, int y) {

    }

    public void pinkOrb(byte gamemode, boolean normal_gravity, boolean normal_size, int x, int y) {
        
    }

    public void redOrb(byte gamemode, boolean normal_gravity, boolean normal_size, int x, int y) {
        
    }

    public void blueOrb(byte gamemode, boolean normal_gravity, boolean normal_size, int x, int y) {
        
    }

    public void greenOrb(byte gamemode, boolean normal_gravity, boolean normal_size, int x, int y) {
        
    }

    public void blackOrb(byte gamemode, boolean normal_gravity, boolean normal_size, int x, int y) {
        
    }

    public void spiderOrb(byte gamemode, boolean normal_gravity, boolean normal_size, int x, int y) {
        
    }

    // PADS
    public void yellowPad(byte gamemode, boolean normal_gravity, boolean normal_size, int x, int y) {

    }

    public void redPad(byte gamemode, boolean normal_gravity, boolean normal_size, int x, int y) {

    }

    public void pinkPad(byte gamemode, boolean normal_gravity, boolean normal_size, int x, int y) {

    }

    public void bluePad(byte gamemode, boolean normal_gravity, boolean normal_size, int x, int y) {

    }

    public void spiderPad(byte gamemode, boolean normal_gravity, boolean normal_size, int x, int y) {

    }

    // PORTALS
    public void cubePortal(boolean normal_gravity, boolean normal_size, int x, int y) {

    }

    public void shipPortal(boolean normal_gravity, boolean normal_size, int x, int y) {
        
    }

    public void ballPortal(boolean normal_gravity, boolean normal_size, int x, int y) {
        
    }

    public void ufoPortal(boolean normal_gravity, boolean normal_size, int x, int y) {
        
    }

    public void wavePortal(boolean normal_gravity, boolean normal_size, int x, int y) {
        
    }

    public void robotPortal(boolean normal_gravity, boolean normal_size, int x, int y) {
        
    }

    public void spiderPortal(boolean normal_gravity, boolean normal_size, int x, int y) {
        
    }

    public void swingPortal(boolean normal_gravity, boolean normal_size, int x, int y) {
        
    }

    public void normalSizePortal() {

    }

    public void miniSizePortal() {

    }

    public void bluePortal() {

    }

    public void yellowPortal() {
        
    }

    public void greenPortal() {
        
    }

    public void halfSpeedPortal() {

    }

    public void _1xSpeedPortal() {
        
    }

    public void _2xSpeedPortal() {
        
    }

    public void _3xSpeedPortal() {
        
    }

    public void _4xSpeedPortal() {
        
    }

    // TEST CHAMBER
    // THIS IS ONLY FOR TESTING, OTHER THAN THAT THE COMPONENTS INSIDE THIS JAVA FILE WILL BE USED INSIDE PLAY SCREEN
    public void run() {
        cube cubeGM = new cube(mainApp);
        inputHandler = cubeGM;
        Graphics g = getGraphics();
        while (isRunning) {
            limitFPS(60);
            // printFPS();
            // drawBackground();
            cubeGM.update(g, true, true);
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