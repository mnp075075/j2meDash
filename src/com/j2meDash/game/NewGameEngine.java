package com.j2meDash.game;

import com.j2meDash.main.MainApp;
import com.j2meDash.pars.LevelBinaryParser;

import java.io.IOException;

import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;

// TODO: add resources and images
public class NewGameEngine extends GameCanvas implements Runnable {
    private MainApp mainApp;
    private LevelBinaryParser levelBinaryParser;
    private Thread t;
    
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
    private int[] srcID = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40};
	private int[] srcX = {0,93};
	private int[] srcY = {0,207};

	private Image sheet; // for objects (not gameplay elements)
    private Image background; // for bg
    private Image foreground; // for fg

    // gamemodes (w&h, w&h hitbox, srcX & srcY from spreadsheet)
    private int[][] cube_mode;
    private int[][] ship_mode;
    private int[][] ball_mode;
    private int[][] ufo_mode;
    private int[][] wave_mode;
    private int[][] robot_mode;
    private int[][] spider_mode;
    private int[][] swing_mode;

    // gameplay elements
    private Image[] orbs;
    private Image[] pads;
    private Image[] portals;

    // fonts (w&h and srcX&srcY from spreadsheet)
    public Image[] fonts;
    public int[][] font1;
    public int[][] font2;
    public int[][] font3;
    public int[][] font4;
    
    public NewGameEngine(MainApp mainApp) {
        super(true);
        this.mainApp = mainApp;

        try {
            // spreadsheets
            sheet = Image.createImage("rsc/img/sheets/obj_port_sh.png");

            // bg elements
            background = Image.createImage("rsc/img/bg/bg.png");
            foreground = Image.createImage("rsc/img/bg/fg.png");

            // orbs
            orbs = new Image[] {
                Image.createImage("rsc/img/orbs/yellow_orb.png"),
                Image.createImage("rsc/img/orbs/pink_orb.png"),
                Image.createImage("rsc/img/orbs/red_orb.png"),
                Image.createImage("rsc/img/orbs/blue_orb.png"),
                Image.createImage("rsc/img/orbs/green_orb.png"),
                Image.createImage("rsc/img/orbs/black_orb.png"),
                Image.createImage("rsc/img/orbs/spider_orb.png")
            };

            // pads
            pads = new Image[] {
                Image.createImage("rsc/img/pads/yellow_pad.png"),
                Image.createImage("rsc/img/pads/red_pad.png"),
                Image.createImage("rsc/img/pads/pink_pad.png"),
                Image.createImage("rsc/img/pads/blue_pad.png"),
                Image.createImage("rsc/img/pads/spider_pad.png")
            };

            // portals
            portals = new Image[] {
                Image.createImage("rsc/img/orbs/cube_portal.png"),
                Image.createImage("rsc/img/orbs/ship_portal.png"),
                Image.createImage("rsc/img/orbs/ball_portal.png"),
                Image.createImage("rsc/img/orbs/ufo_portal.png"),
                Image.createImage("rsc/img/orbs/wave_portal.png"),
                Image.createImage("rsc/img/orbs/robot_portal.png"),
                Image.createImage("rsc/img/orbs/spider_portal.png"),
                Image.createImage("rsc/img/orbs/swing_portal.png"),
                Image.createImage("rsc/img/orbs/normalSize_portal.png"),
                Image.createImage("rsc/img/orbs/miniSize_portal.png"),
                Image.createImage("rsc/img/orbs/blue_portal.png"),
                Image.createImage("rsc/img/orbs/yellow_portal.png"),
                Image.createImage("rsc/img/orbs/green_portal.png"),
                Image.createImage("rsc/img/orbs/halfSpeed_portal.png"),
                Image.createImage("rsc/img/orbs/_1xSpeed_portal.png"),
                Image.createImage("rsc/img/orbs/_2xSpeed_portal.png"),
                Image.createImage("rsc/img/orbs/_3xSpeed_portal.png"),
                Image.createImage("rsc/img/orbs/_4xSpeed_portal.png")
            };

            // fonts
            fonts = new Image[] {
                Image.createImage("rsc/font/font1.png"),
                Image.createImage("rsc/font/font2.png"),
                Image.createImage("rsc/font/font3.png"),
                Image.createImage("rsc/font/font4.png")
            };
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        g.fillRect(0,0,40,15);
        g.setColor(0xffffff);
        g.drawString("FPS: " + displayFPS, 0, 0, Graphics.LEFT | Graphics.TOP);
        flushGraphics();
        frameCount++;
    }

    public void drawingCharString(Graphics g, Image fontstrip, String s, int x, int y, int[][] metrics) {
        // note: metrics[][] contains width and height of each character and its srcX and srcY on the font strip
    }

    // PRINTING FUNCTION
    public void printObject_fixed() {
		Graphics g = getGraphics();
		
		int additionalPrintingTime = levelBinaryParser.objectNumber % 8;
		int groupedPrintingTime = levelBinaryParser.objectNumber - additionalPrintingTime;
		
		for (int i = 0; i < groupedPrintingTime; i += 8) {
			
			if (mainApp.data[32+(8*i)] >= srcX.length && mainApp.data[32+8*i] >= srcY.length) {
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
			
			if (id > srcID[0] && id <= srcID[srcID.length-1]) {
				g.drawRegion(sheet, srcX[firstCurrentID], srcY[firstCurrentID], widthAndHeight[firstCurrentID][0], widthAndHeight[firstCurrentID][1], Sprite.TRANS_NONE, levelBinaryParser.xArray[i], levelBinaryParser.yArray[i], Graphics.RIGHT | Graphics.BOTTOM);
				g.drawRegion(sheet, srcX[secondCurrentID], srcY[secondCurrentID], widthAndHeight[secondCurrentID][0], widthAndHeight[secondCurrentID][1], Sprite.TRANS_NONE, levelBinaryParser.xArray[i+1], levelBinaryParser.yArray[i+1], Graphics.RIGHT | Graphics.BOTTOM);
				g.drawRegion(sheet, srcX[thirdCurrentID], srcY[thirdCurrentID], widthAndHeight[thirdCurrentID][0], widthAndHeight[thirdCurrentID][1], Sprite.TRANS_NONE, levelBinaryParser.xArray[i+2], levelBinaryParser.yArray[i+2], Graphics.RIGHT | Graphics.BOTTOM);
				g.drawRegion(sheet, srcX[fourthCurrentID], srcY[fourthCurrentID], widthAndHeight[fourthCurrentID][0], widthAndHeight[fourthCurrentID][1], Sprite.TRANS_NONE, levelBinaryParser.xArray[i+3], levelBinaryParser.yArray[i+3], Graphics.RIGHT | Graphics.BOTTOM);
				g.drawRegion(sheet, srcX[fifthCurrentID], srcY[fifthCurrentID], widthAndHeight[fifthCurrentID][0], widthAndHeight[fifthCurrentID][1], Sprite.TRANS_NONE, levelBinaryParser.xArray[i+4], levelBinaryParser.yArray[i+4], Graphics.RIGHT | Graphics.BOTTOM);
				g.drawRegion(sheet, srcX[sixthCurrentID], srcY[sixthCurrentID], widthAndHeight[sixthCurrentID][0], widthAndHeight[sixthCurrentID][1], Sprite.TRANS_NONE, levelBinaryParser.xArray[i+5], levelBinaryParser.yArray[i+5], Graphics.RIGHT | Graphics.BOTTOM);
				g.drawRegion(sheet, srcX[seventhCurrentID], srcY[seventhCurrentID], widthAndHeight[seventhCurrentID][0], widthAndHeight[seventhCurrentID][1], Sprite.TRANS_NONE, levelBinaryParser.xArray[i+6], levelBinaryParser.yArray[i+6], Graphics.RIGHT | Graphics.BOTTOM);
				g.drawRegion(sheet, srcX[eighthCurrentID], srcY[eighthCurrentID], widthAndHeight[eighthCurrentID][0], widthAndHeight[eighthCurrentID][1], Sprite.TRANS_NONE, levelBinaryParser.xArray[i+7], levelBinaryParser.yArray[i+7], Graphics.RIGHT | Graphics.BOTTOM);
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
					
					if (id > srcID[0] && id <= srcID[srcID.length-1]) {
						g.drawRegion(sheet, srcX[firstCurrentID], srcY[firstCurrentID], widthAndHeight[firstCurrentID][0], widthAndHeight[firstCurrentID][1], Sprite.TRANS_NONE, levelBinaryParser.xArray[i], levelBinaryParser.yArray[i], Graphics.RIGHT | Graphics.BOTTOM);
						g.drawRegion(sheet, srcX[secondCurrentID], srcY[secondCurrentID], widthAndHeight[secondCurrentID][0], widthAndHeight[secondCurrentID][1], Sprite.TRANS_NONE, levelBinaryParser.xArray[i+1], levelBinaryParser.yArray[i+1], Graphics.RIGHT | Graphics.BOTTOM);
						g.drawRegion(sheet, srcX[thirdCurrentID], srcY[thirdCurrentID], widthAndHeight[thirdCurrentID][0], widthAndHeight[thirdCurrentID][1], Sprite.TRANS_NONE, levelBinaryParser.xArray[i+2], levelBinaryParser.yArray[i+2], Graphics.RIGHT | Graphics.BOTTOM);
						g.drawRegion(sheet, srcX[fourthCurrentID], srcY[fourthCurrentID], widthAndHeight[fourthCurrentID][0], widthAndHeight[fourthCurrentID][1], Sprite.TRANS_NONE, levelBinaryParser.xArray[i+3], levelBinaryParser.yArray[i+3], Graphics.RIGHT | Graphics.BOTTOM);
					}
				}
				break;
			case 2:
				for (int i = groupedPrintingTime; i < levelBinaryParser.objectNumber; i += 2) {
					
					int id = mainApp.data[32+(8*i)];
					int firstCurrentID = levelBinaryParser.idArray[i];
					int secondCurrentID = levelBinaryParser.idArray[i+1];
					
					if (id > srcID[0] && id <= srcID[srcID.length-1]) {
						g.drawRegion(sheet, srcX[firstCurrentID], srcY[firstCurrentID], widthAndHeight[firstCurrentID][0], widthAndHeight[firstCurrentID][1], Sprite.TRANS_NONE, levelBinaryParser.xArray[i], levelBinaryParser.yArray[i], Graphics.RIGHT | Graphics.BOTTOM);
						g.drawRegion(sheet, srcX[secondCurrentID], srcY[secondCurrentID], widthAndHeight[secondCurrentID][0], widthAndHeight[secondCurrentID][1], Sprite.TRANS_NONE, levelBinaryParser.xArray[i+1], levelBinaryParser.yArray[i+1], Graphics.RIGHT | Graphics.BOTTOM);
					}
				}
				break;
			case 1:
			case 3:
				for (int i = groupedPrintingTime; i < levelBinaryParser.objectNumber; i += 1) {
					
					int id = mainApp.data[32+(8*i)];
					int firstCurrentID = levelBinaryParser.idArray[i];
					
					if (id > srcID[0] && id <= srcID[srcID.length-1]) {
						g.drawRegion(sheet, srcX[firstCurrentID], srcY[firstCurrentID], widthAndHeight[firstCurrentID][0], widthAndHeight[firstCurrentID][1], Sprite.TRANS_NONE, levelBinaryParser.xArray[i], levelBinaryParser.yArray[i], Graphics.RIGHT | Graphics.BOTTOM);
					}
				}
				break;
			default:
				break;
		}
		
	}
	
	public void printObject_moving(int offset) {
		Graphics g = getGraphics();
		
		int additionalPrintingTime = levelBinaryParser.objectNumber % 8;
		int groupedPrintingTime = levelBinaryParser.objectNumber - additionalPrintingTime;
		
		for (int i = 0; i < groupedPrintingTime; i += 8) {
			
			if (mainApp.data[32+(8*i)] >= srcX.length && mainApp.data[32+8*i] >= srcY.length) {
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
			
			if (id > srcID[0] && id <= srcID[srcID.length-1]) {
				g.drawRegion(sheet, srcX[firstCurrentID], srcY[firstCurrentID], widthAndHeight[firstCurrentID][0], widthAndHeight[firstCurrentID][1], Sprite.TRANS_NONE, levelBinaryParser.xArray[i] - offset, levelBinaryParser.yArray[i], Graphics.RIGHT | Graphics.BOTTOM);
				g.drawRegion(sheet, srcX[secondCurrentID], srcY[secondCurrentID], widthAndHeight[secondCurrentID][0], widthAndHeight[secondCurrentID][1], Sprite.TRANS_NONE, levelBinaryParser.xArray[i+1] - offset, levelBinaryParser.yArray[i+1], Graphics.RIGHT | Graphics.BOTTOM);
				g.drawRegion(sheet, srcX[thirdCurrentID], srcY[thirdCurrentID], widthAndHeight[thirdCurrentID][0], widthAndHeight[thirdCurrentID][1], Sprite.TRANS_NONE, levelBinaryParser.xArray[i+2] - offset, levelBinaryParser.yArray[i+2], Graphics.RIGHT | Graphics.BOTTOM);
				g.drawRegion(sheet, srcX[fourthCurrentID], srcY[fourthCurrentID], widthAndHeight[fourthCurrentID][0], widthAndHeight[fourthCurrentID][1], Sprite.TRANS_NONE, levelBinaryParser.xArray[i+3] - offset, levelBinaryParser.yArray[i+3], Graphics.RIGHT | Graphics.BOTTOM);
				g.drawRegion(sheet, srcX[fifthCurrentID], srcY[fifthCurrentID], widthAndHeight[fifthCurrentID][0], widthAndHeight[fifthCurrentID][1], Sprite.TRANS_NONE, levelBinaryParser.xArray[i+4] - offset, levelBinaryParser.yArray[i+4], Graphics.RIGHT | Graphics.BOTTOM);
				g.drawRegion(sheet, srcX[sixthCurrentID], srcY[sixthCurrentID], widthAndHeight[sixthCurrentID][0], widthAndHeight[sixthCurrentID][1], Sprite.TRANS_NONE, levelBinaryParser.xArray[i+5] - offset, levelBinaryParser.yArray[i+5], Graphics.RIGHT | Graphics.BOTTOM);
				g.drawRegion(sheet, srcX[seventhCurrentID], srcY[seventhCurrentID], widthAndHeight[seventhCurrentID][0], widthAndHeight[seventhCurrentID][1], Sprite.TRANS_NONE, levelBinaryParser.xArray[i+6] - offset, levelBinaryParser.yArray[i+6], Graphics.RIGHT | Graphics.BOTTOM);
				g.drawRegion(sheet, srcX[eighthCurrentID], srcY[eighthCurrentID], widthAndHeight[eighthCurrentID][0], widthAndHeight[eighthCurrentID][1], Sprite.TRANS_NONE, levelBinaryParser.xArray[i+7] - offset, levelBinaryParser.yArray[i+7], Graphics.RIGHT | Graphics.BOTTOM);
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
					
					if (id > srcID[0] && id <= srcID[srcID.length-1]) {
						g.drawRegion(sheet, srcX[firstCurrentID], srcY[firstCurrentID], widthAndHeight[firstCurrentID][0], widthAndHeight[firstCurrentID][1], Sprite.TRANS_NONE, levelBinaryParser.xArray[i] - offset, levelBinaryParser.yArray[i], Graphics.RIGHT | Graphics.BOTTOM);
						g.drawRegion(sheet, srcX[secondCurrentID], srcY[secondCurrentID], widthAndHeight[secondCurrentID][0], widthAndHeight[secondCurrentID][1], Sprite.TRANS_NONE, levelBinaryParser.xArray[i+1] - offset, levelBinaryParser.yArray[i+1], Graphics.RIGHT | Graphics.BOTTOM);
						g.drawRegion(sheet, srcX[thirdCurrentID], srcY[thirdCurrentID], widthAndHeight[thirdCurrentID][0], widthAndHeight[thirdCurrentID][1], Sprite.TRANS_NONE, levelBinaryParser.xArray[i+2] - offset, levelBinaryParser.yArray[i+2], Graphics.RIGHT | Graphics.BOTTOM);
						g.drawRegion(sheet, srcX[fourthCurrentID], srcY[fourthCurrentID], widthAndHeight[fourthCurrentID][0], widthAndHeight[fourthCurrentID][1], Sprite.TRANS_NONE, levelBinaryParser.xArray[i+3] - offset, levelBinaryParser.yArray[i+3], Graphics.RIGHT | Graphics.BOTTOM);
					}
				}
				break;
			case 2:
				for (int i = groupedPrintingTime; i < levelBinaryParser.objectNumber; i += 2) {
					
					int id = mainApp.data[32+(8*i)];
					int firstCurrentID = levelBinaryParser.idArray[i];
					int secondCurrentID = levelBinaryParser.idArray[i+1];
					
					if (id > srcID[0] && id <= srcID[srcID.length-1]) {
						g.drawRegion(sheet, srcX[firstCurrentID], srcY[firstCurrentID], widthAndHeight[firstCurrentID][0], widthAndHeight[firstCurrentID][1], Sprite.TRANS_NONE, levelBinaryParser.xArray[i] - offset, levelBinaryParser.yArray[i], Graphics.RIGHT | Graphics.BOTTOM);
						g.drawRegion(sheet, srcX[secondCurrentID], srcY[secondCurrentID], widthAndHeight[secondCurrentID][0], widthAndHeight[secondCurrentID][1], Sprite.TRANS_NONE, levelBinaryParser.xArray[i+1] - offset, levelBinaryParser.yArray[i+1], Graphics.RIGHT | Graphics.BOTTOM);
					}
				}
				break;
			case 1:
			case 3:
				for (int i = groupedPrintingTime; i < levelBinaryParser.objectNumber; i += 1) {
					
					int id = mainApp.data[32+(8*i)];
					int firstCurrentID = levelBinaryParser.idArray[i];
					
					if (id > srcID[0] && id <= srcID[srcID.length-1]) {
						g.drawRegion(sheet, srcX[firstCurrentID], srcY[firstCurrentID], widthAndHeight[firstCurrentID][0], widthAndHeight[firstCurrentID][1], Sprite.TRANS_NONE, levelBinaryParser.xArray[i] - offset, levelBinaryParser.yArray[i], Graphics.RIGHT | Graphics.BOTTOM);
					}
				}
				break;
			default:
				break;
		}
		
	}

    // GAMEMODES
    public void cubeMode(boolean normal_gravity, boolean normal_size) {
        
    }

    public void shipMode(boolean normal_gravity, boolean normal_size) {
        
    }

    public void ballMode(boolean normal_gravity, boolean normal_size) {
        
    }

    public void ufoMode(boolean normal_gravity, boolean normal_size) {
        
    }

    public void waveMode(boolean normal_gravity, boolean normal_size) {
        
    }

    public void robotMode(boolean normal_gravity, boolean normal_size) {
        
    }

    public void spiderMode(boolean normal_gravity, boolean normal_size) {
        
    }

    public void swingMode(boolean normal_gravity, boolean normal_size) {
        
    }

    // ORBS
    public void yellowOrb(boolean normal_gravity, boolean normal_size) {

    }

    public void pinkOrb(boolean normal_gravity, boolean normal_size) {
        
    }

    public void redOrb(boolean normal_gravity, boolean normal_size) {
        
    }

    public void blueOrb(boolean normal_gravity, boolean normal_size) {
        
    }

    public void greenOrb(boolean normal_gravity, boolean normal_size) {
        
    }

    public void blackOrb(boolean normal_gravity, boolean normal_size) {
        
    }

    public void spiderOrb(boolean normal_gravity, boolean normal_size) {
        
    }

    // PADS
    public void yellowPad(boolean normal_gravity, boolean normal_size) {

    }

    public void redPad(boolean normal_gravity, boolean normal_size) {

    }

    public void pinkPad(boolean normal_gravity, boolean normal_size) {

    }

    public void bluePad(boolean normal_gravity, boolean normal_size) {

    }

    public void spiderPad(boolean normal_gravity, boolean normal_size) {

    }

    // PORTALS
    public void cubePortal(boolean normal_gravity, boolean normal_size) {

    }

    public void shipPortal(boolean normal_gravity, boolean normal_size) {
        
    }

    public void ballPortal(boolean normal_gravity, boolean normal_size) {
        
    }

    public void ufoPortal(boolean normal_gravity, boolean normal_size) {
        
    }

    public void wavePortal(boolean normal_gravity, boolean normal_size) {
        
    }

    public void robotPortal(boolean normal_gravity, boolean normal_size) {
        
    }

    public void spiderPortal(boolean normal_gravity, boolean normal_size) {
        
    }

    public void swingPortal(boolean normal_gravity, boolean normal_size) {
        
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

    public void run() {
        while (isRunning) {
            limitFPS(60);
            printFPS();
        }
    }
}
