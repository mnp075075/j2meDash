package com.j2meDash.game;
import com.j2meDash.main.*;
import com.j2meDash.menu.*;
import com.j2meDash.pars.*;
import com.j2meDash.temp.*;
import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;

public class GameEngine extends GameCanvas implements Runnable {
	
	private MainApp mainApp;
	private LevelBinaryParser levelBinaryParser;
	private PlayScreen playScreen;
	
	Image sheet;
	private Thread gameTest;
	private volatile boolean isRunning = true;
	
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
	private Image spreadsheet;
	
	// global components for all gamemodes
	private boolean isReleased = false;
	private boolean isPressed = false;
	private boolean oneShotPressed = false;
	private boolean canPress = true;
	private boolean normalGravity = true;
	private int gamemode = -1;
	private int x = 81;
	private int y = 287;
	private int velocityY = 0;
	
	// components for the ship gamemode
	private boolean havePassed = false;
	private Image ship;
	private Image ship_1;
	private Image ship_2;
	private Image ship_3;
	private Image ship_4;
	private Image ship_5;
	private Image ship_6;
	private Image ship_7;
	private Image ship_8;
	private Image ship_inv;
	private Image ship_1_inv;
	private Image ship_2_inv;
	private Image ship_3_inv;
	private Image ship_4_inv;
	private Image ship_5_inv;
	private Image ship_6_inv;
	private Image ship_7_inv;
	private Image ship_8_inv;
	private Image mini_ship;
	private Image mini_ship_1;
	private Image mini_ship_2;
	private Image mini_ship_3;
	private Image mini_ship_4;
	private Image mini_ship_5;
	private Image mini_ship_6;
	private Image mini_ship_7;
	private Image mini_ship_8;
	private Image mini_ship_inv;
	private Image mini_ship_1_inv;
	private Image mini_ship_2_inv;
	private Image mini_ship_3_inv;
	private Image mini_ship_4_inv;
	private Image mini_ship_5_inv;
	private Image mini_ship_6_inv;
	private Image mini_ship_7_inv;
	private Image mini_ship_8_inv;
	private int timeCounter = 0;
	private int releaseCounter = 0;
	private int tick = 0;
	private int[] yValues = new int[2];
	private boolean leftoverEnergy = false;
	
	// components for the wave gamemode
	private Image wave;
	private Image wave_up;
	private Image wave_down;
	private boolean isUp = false;
	private boolean isDown = false;
	
	// components for the ball gamemode
	private Image ball;
	private Image mini_ball;
	private int pressedCounter = 0;
	private boolean passed = false;
	
	// components for the spider gamemode
	private Image spider;
	private Image spider_inverted;
	private Image mini_spider;
	private Image mini_spider_inverted;
	
	// components for the ufo gamemode
	private Image ufo;
	private Image ufo_inverted;
	private Image mini_ufo;
	private Image mini_ufo_inverted;
	private int timer = 0;
	
	// temporary components
	// empty
	
	public int[] srcID = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40};
	
	public int[] srcX = {0,93};
	public int[] srcY = {0,207};
	
	public GameEngine(MainApp mainApp) {
		
		super(true);
		this.mainApp = mainApp;
		
		try {
			sheet = Image.createImage("rsc/img/sheet.png");
			
			// ship
			ship = Image.createImage("rsc/img/ship.png");
			ship_1 = Image.createImage("rsc/img/ship_1.png");
			ship_2 = Image.createImage("rsc/img/ship_2.png");
			ship_3 = Image.createImage("rsc/img/ship_3.png");
			ship_4 = Image.createImage("rsc/img/ship_4.png");
			
			// wave
			wave = Image.createImage("rsc/img/wave.png");
			wave_up = Image.createImage("rsc/img/wave_up.png");
			wave_down = Image.createImage("rsc/img/wave_down.png");
			
			// ball
			ball = Image.createImage("rsc/img/ball.png");
			
			// spider
			spider = Image.createImage("rsc/img/spider.png");
			spider_inverted = Image.createImage("rsc/img/spider_inverted.png");
			
			// ufo
			ufo = Image.createImage("rsc/img/ufo.png");
			ufo_inverted = Image.createImage("rsc/img/ufo_inverted.png");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void showNotify() {
		isRunning = true;
		gameTest = new Thread(this);
		gameTest.start();
	}
	
	public void hideNotify() {
		isRunning = false;
		gameTest = null;
	}
	
	public void callParseLBP(String name) {
		
		if (levelBinaryParser == null) {
			levelBinaryParser = new LevelBinaryParser(mainApp);
		}
		
		levelBinaryParser.parseByte(name);
		
		// use the example file for testing
	}
	
	public void printObjectOriginally() {
		// dont use this method yet
		// i haven't finished the srcX[] and srcY[] at the moment
		Graphics g = getGraphics();
		
		int additionalPrintingTime = levelBinaryParser.objectNumber % 8;
		int groupedPrintingTime = levelBinaryParser.objectNumber - additionalPrintingTime;
		
		for (int i = 0; i < groupedPrintingTime; i += 8) {
			
			// temporarily failcheck
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
				// nothing
				break;
		}
		
		// flushGraphics(); (manually flushGraphics)
		
	}
	
	public void printObjectMoving(int offset) {
		// dont use this method yet
		// i haven't finished the srcX[] and srcY[] at the moment
		Graphics g = getGraphics();
		
		int additionalPrintingTime = levelBinaryParser.objectNumber % 8;
		int groupedPrintingTime = levelBinaryParser.objectNumber - additionalPrintingTime;
		
		for (int i = 0; i < groupedPrintingTime; i += 8) {
			
			// temporarily failcheck
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
				g.drawRegion(sheet, srcX[firstCurrentID], srcY[firstCurrentID], widthAndHeight[firstCurrentID][0], widthAndHeight[firstCurrentID][1], Sprite.TRANS_NONE, levelBinaryParser.xArray[i] -= offset, levelBinaryParser.yArray[i], Graphics.RIGHT | Graphics.BOTTOM);
				g.drawRegion(sheet, srcX[secondCurrentID], srcY[secondCurrentID], widthAndHeight[secondCurrentID][0], widthAndHeight[secondCurrentID][1], Sprite.TRANS_NONE, levelBinaryParser.xArray[i+1] -= offset, levelBinaryParser.yArray[i+1], Graphics.RIGHT | Graphics.BOTTOM);
				g.drawRegion(sheet, srcX[thirdCurrentID], srcY[thirdCurrentID], widthAndHeight[thirdCurrentID][0], widthAndHeight[thirdCurrentID][1], Sprite.TRANS_NONE, levelBinaryParser.xArray[i+2] -= offset, levelBinaryParser.yArray[i+2], Graphics.RIGHT | Graphics.BOTTOM);
				g.drawRegion(sheet, srcX[fourthCurrentID], srcY[fourthCurrentID], widthAndHeight[fourthCurrentID][0], widthAndHeight[fourthCurrentID][1], Sprite.TRANS_NONE, levelBinaryParser.xArray[i+3] -= offset, levelBinaryParser.yArray[i+3], Graphics.RIGHT | Graphics.BOTTOM);
				g.drawRegion(sheet, srcX[fifthCurrentID], srcY[fifthCurrentID], widthAndHeight[fifthCurrentID][0], widthAndHeight[fifthCurrentID][1], Sprite.TRANS_NONE, levelBinaryParser.xArray[i+4] -= offset, levelBinaryParser.yArray[i+4], Graphics.RIGHT | Graphics.BOTTOM);
				g.drawRegion(sheet, srcX[sixthCurrentID], srcY[sixthCurrentID], widthAndHeight[sixthCurrentID][0], widthAndHeight[sixthCurrentID][1], Sprite.TRANS_NONE, levelBinaryParser.xArray[i+5] -= offset, levelBinaryParser.yArray[i+5], Graphics.RIGHT | Graphics.BOTTOM);
				g.drawRegion(sheet, srcX[seventhCurrentID], srcY[seventhCurrentID], widthAndHeight[seventhCurrentID][0], widthAndHeight[seventhCurrentID][1], Sprite.TRANS_NONE, levelBinaryParser.xArray[i+6] -= offset, levelBinaryParser.yArray[i+6], Graphics.RIGHT | Graphics.BOTTOM);
				g.drawRegion(sheet, srcX[eighthCurrentID], srcY[eighthCurrentID], widthAndHeight[eighthCurrentID][0], widthAndHeight[eighthCurrentID][1], Sprite.TRANS_NONE, levelBinaryParser.xArray[i+7] -= offset, levelBinaryParser.yArray[i+7], Graphics.RIGHT | Graphics.BOTTOM);
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
						g.drawRegion(sheet, srcX[firstCurrentID], srcY[firstCurrentID], widthAndHeight[firstCurrentID][0], widthAndHeight[firstCurrentID][1], Sprite.TRANS_NONE, levelBinaryParser.xArray[i] -= offset, levelBinaryParser.yArray[i], Graphics.RIGHT | Graphics.BOTTOM);
						g.drawRegion(sheet, srcX[secondCurrentID], srcY[secondCurrentID], widthAndHeight[secondCurrentID][0], widthAndHeight[secondCurrentID][1], Sprite.TRANS_NONE, levelBinaryParser.xArray[i+1] -= offset, levelBinaryParser.yArray[i+1], Graphics.RIGHT | Graphics.BOTTOM);
						g.drawRegion(sheet, srcX[thirdCurrentID], srcY[thirdCurrentID], widthAndHeight[thirdCurrentID][0], widthAndHeight[thirdCurrentID][1], Sprite.TRANS_NONE, levelBinaryParser.xArray[i+2] -= offset, levelBinaryParser.yArray[i+2], Graphics.RIGHT | Graphics.BOTTOM);
						g.drawRegion(sheet, srcX[fourthCurrentID], srcY[fourthCurrentID], widthAndHeight[fourthCurrentID][0], widthAndHeight[fourthCurrentID][1], Sprite.TRANS_NONE, levelBinaryParser.xArray[i+3] -= offset, levelBinaryParser.yArray[i+3], Graphics.RIGHT | Graphics.BOTTOM);
					}
				}
				break;
			case 2:
				for (int i = groupedPrintingTime; i < levelBinaryParser.objectNumber; i += 2) {
					
					int id = mainApp.data[32+(8*i)];
					int firstCurrentID = levelBinaryParser.idArray[i];
					int secondCurrentID = levelBinaryParser.idArray[i+1];
					
					if (id > srcID[0] && id <= srcID[srcID.length-1]) {
						g.drawRegion(sheet, srcX[firstCurrentID], srcY[firstCurrentID], widthAndHeight[firstCurrentID][0], widthAndHeight[firstCurrentID][1], Sprite.TRANS_NONE, levelBinaryParser.xArray[i] -= offset, levelBinaryParser.yArray[i], Graphics.RIGHT | Graphics.BOTTOM);
						g.drawRegion(sheet, srcX[secondCurrentID], srcY[secondCurrentID], widthAndHeight[secondCurrentID][0], widthAndHeight[secondCurrentID][1], Sprite.TRANS_NONE, levelBinaryParser.xArray[i+1] -= offset, levelBinaryParser.yArray[i+1], Graphics.RIGHT | Graphics.BOTTOM);
					}
				}
				break;
			case 1:
			case 3:
				for (int i = groupedPrintingTime; i < levelBinaryParser.objectNumber; i += 1) {
					
					int id = mainApp.data[32+(8*i)];
					int firstCurrentID = levelBinaryParser.idArray[i];
					
					if (id > srcID[0] && id <= srcID[srcID.length-1]) {
						g.drawRegion(sheet, srcX[firstCurrentID], srcY[firstCurrentID], widthAndHeight[firstCurrentID][0], widthAndHeight[firstCurrentID][1], Sprite.TRANS_NONE, levelBinaryParser.xArray[i] -= offset, levelBinaryParser.yArray[i], Graphics.RIGHT | Graphics.BOTTOM);
					}
				}
				break;
			default:
				// nothing
				break;
		}
		
		// flushGraphics(); (manually flushGraphics)
	}
	
	public void run() {
		
		int maxValue = 0;
		Graphics g = getGraphics();
		
		g.setColor(0xabcdef);
		g.fillRect(0,0,240,400);
		
		callParseLBP("rsc/lvl/example.bin");
		printObjectOriginally();
		
		flushGraphics();
		
		// temporarily failcheck
		if (mainApp.data[32] == 0x00) {
			System.out.println("cannot continue");
		}
		
		for (int i = 0; i < levelBinaryParser.idArray.length - 1; i++) {
			if (levelBinaryParser.idArray[i] == 0) {
				maxValue = i;
				break;
			}
		}
		
		if (maxValue == 0 && levelBinaryParser.idArray[0] != 0) {
			maxValue = levelBinaryParser.idArray.length;
		}
		
		// try { Thread.sleep(5000); } catch (Exception e) { e.printStackTrace(); }
		
		while (isRunning) {
			
			g.fillRect(0,0,240,400);
			
			// add the img for ship with gravity inverted and mini ship with gravity inverted and normal
			
			// TEST SANDBOX //
			ufoMode(true, true, false);
			
			printObjectMoving(5);
			// -------------------------- //
			
			for (int i = 0; i < maxValue; i++) {
				
				if (levelBinaryParser.xArray[i] >= -60 && levelBinaryParser.xArray[i] <= 300) {
					
					int currentID = levelBinaryParser.idArray[i];
					int safeID = -1;
					int hazardID = -1;
					
					for (int j = 0; j < levelBinaryParser.safeObjectID.length; j++) {
						if (currentID == levelBinaryParser.safeObjectID[j]) {
							safeID = currentID;
							break;
						}
					}
					
					for (int j = 0; j < levelBinaryParser.dangerousObjectID.length; j++) {
						if (currentID == levelBinaryParser.dangerousObjectID[j]) {
							hazardID = currentID;
							break;
						}
					}
					
					int playerX = x;
					int playerY = y;
					int playerBodyW = gamemodeWidthAndHeight[gamemode][2];
					int playerBodyH = gamemodeWidthAndHeight[gamemode][3];
					int playerHazardW = gamemodeWidthAndHeight[gamemode][0];
					int playerHazardH = gamemodeWidthAndHeight[gamemode][1];
					
					int objectX = levelBinaryParser.xArray[i];
					int objectY = levelBinaryParser.yArray[i];
					int objectW = widthAndHeight[levelBinaryParser.idArray[i]][2];
					int objectH = widthAndHeight[levelBinaryParser.idArray[i]][3];
					
					checkCollision(safeID, hazardID, playerX, playerY, playerBodyW, playerBodyH, playerHazardW, playerHazardH, objectX, objectY, objectW, objectH);
				}
				
			}
			
			flushGraphics();
			
			updateState();
		} 
		
	}
	
	public void checkCollision(
	int safeID, int hazardID, int Ax, int Ay, int AwBody, int AhBody,
	int AwHazard, int AhHazard, int Bx, int By, int Bw, int Bh
	) {
		
		// boolean valueBody = ((Ax < Bx + Bw) && (Ax + AwBody > Bx) && (Ay < By + Bh) && (Ay + AhBody > By));
		// boolean valueHazard = ((Ax < Bx + Bw) && (Ax + AwHazard > Bx) && (Ay < By + Bh) && (Ay + AhHazard > By));
		
		boolean valueBody = ((Ax < Bx + Bw) && (Ax + AwBody > Bx) && (Ay < By + Bh) && (Ay + AhHazard > By));
		boolean valueHazard = ((Ax < Bx + Bw) && (Ax + AwHazard > Bx) && (Ay < By + Bh) && (Ay + AhHazard > By));
		
		int[] blockID = {1,2,3,4};
		int[] speedID = {13,14,15,16,17};
		int[] gamePortalID = {18,19,20,21,22,23,24};
		
		/* System.out.println(
		"========== AwBody + AhBody ==========" +
		"\nAx < Bx + Bw = " + (Ax < Bx + Bw) +
		"\nAx + AwBody > Bx = " + (Ax + AwBody > Bx) +
		"\nAy < By + Bh = " + (Ay < By + Bh) +
		"\nAy + AhBody > By = " + (Ay + AhBody > By) +
		"\n======== AwHazard + AhHazard ========" +
		"\nAx < Bx + Bw = " + (Ax < Bx + Bw) +
		"\nAx + AwBody > Bx = " + (Ax + AwHazard > Bx) +
		"\nAy < By + Bh = " + (Ay < By + Bh) +
		"\nAy + AhBody > By = " + (Ay + AhHazard > By) +
		"\nAx = " + Ax + ", Ay = " + Ay + ", AwBody = " + AwBody + ", AhBody = " + AhBody +
		"\nAwHazard = " + AwHazard + ", AhHazard = " + AhHazard + "Bx = " + Bx + ", By = " + By + ", Bw = " + Bw + ", Bh = " + Bh +
		"\nTotal - Body = " + valueBody + ", Total - Hazard = " + valueHazard); */
		
		if (valueHazard == true && hazardID != -1) {
			System.out.println("died");
			mainApp.showGameOverScreen();
		} else if (valueBody == true && safeID != -1) {
			System.out.println("survived");
			
			if (safeID >= 1 && safeID <= 4) {
				if (gamemode == 2) {
					System.out.println("died");
					hideNotify();
					mainApp.showGameOverScreen();
				}
				
				if (gamemode == 4) {
					if (velocityY >= 0) {
						y = By - AhHazard;
					} else {
						y = By + Bh;
					}
					velocityY = 0;
					pressedCounter = 0;
					return;
				}
				
				int overlapY = (Ay + AhHazard) - By;
				int tolerance = 4; // pixels, probably
				
				if (overlapY > tolerance) {
					System.out.println("died");
					hideNotify();
					mainApp.showGameOverScreen();
					return;
				} else {
					y = By - AhHazard;
					velocityY = 0;
					pressedCounter = 0;
				}
				
			}
			
			if (safeID >= 13 && safeID <= 17) {
				mainApp.speedCount = safeID - 13;
			}
		}
	}
	
	public void shipMode(boolean isShip, boolean isNormalGravity, boolean isMiniMode) {
		
		Graphics g = getGraphics();
		gamemode = 1;
		int[] parabola = {1,2,3,4,0};
		int[] parabolaMini = {1,2,4,8,0};
		int lastSavedValue = 0;
		int index = 0;
		int n = (tick % 2 == 0) ? 0 : 1;
		int deltaY = 0;
		velocityY = deltaY;
		
		if (isShip == true && isMiniMode == false) {
			boolean onGround = (y == 287) ? true : false;
			boolean onCeiling = (y == 40) ? true : false;
			
			if (isNormalGravity == true) {
				if (isPressed == true && isReleased == false) {
					if (leftoverEnergy == true && onGround == false) {
						if (havePassed == false) {
							switch (lastSavedValue) {
								case 0:
									releaseCounter = 19;
									break;
								case 1:
									releaseCounter = 39;
									break;
								case 2:
								case 3:
									releaseCounter = 79;
									break;
								default:
									// nothing
							}
							
							havePassed = true;
						}
						
						index = (releaseCounter == 0) ? 4 : (releaseCounter < 20) ? 0 : (releaseCounter < 40) ? 1 : (releaseCounter < 80) ? 2 : 3;
						y += parabola[index];
						
						if (releaseCounter > 0) {
							releaseCounter--;
						} else {
							leftoverEnergy = false;
							timeCounter = 0;
						}
					} else {
					
						index = (timeCounter < 20) ? 0 : (timeCounter < 40) ? 1 : (timeCounter < 80) ? 2 : 3;
						lastSavedValue = index;
						y -= parabola[index];
						timeCounter++;
					
					}
					
				} else if (isReleased == true && isPressed == false) {
					if (leftoverEnergy == true && onCeiling == false) {
						if (havePassed == false) {
							switch (lastSavedValue) {
								case 0:
									timeCounter = 19;
									break;
								case 1:
									timeCounter = 39;
									break;
								case 2:
								case 3:
									timeCounter = 79;
									break;
								default:
									// nothing
							}
							
							havePassed = true;
						}
						
						index = (timeCounter == 0) ? 4 : (timeCounter < 20) ? 0 : (timeCounter < 40) ? 1 : (timeCounter < 80) ? 2 : 3;
						y -= parabola[index];
						
						if (timeCounter > 0) {
							timeCounter--;
						} else {
							leftoverEnergy = false;
							releaseCounter = 0;
						}
					} else {
					
						index = (releaseCounter < 20) ? 0 : (releaseCounter < 40) ? 1 : (releaseCounter < 80) ? 2 : 3;
						lastSavedValue = index;
						y += parabola[index];
						releaseCounter++;
					
					}
				} 
			} else {
				if (isReleased == true && isPressed == false) {
					if (leftoverEnergy == true && onGround == false) {
						if (havePassed == false) {
							switch (lastSavedValue) {
								case 0:
									releaseCounter = 19;
									break;
								case 1:
									releaseCounter = 39;
									break;
								case 2:
								case 3:
									releaseCounter = 79;
									break;
								default:
									// nothing
							}
							
							havePassed = true;
						}
						
						index = (releaseCounter == 0) ? 4 : (releaseCounter < 20) ? 0 : (releaseCounter < 40) ? 1 : (releaseCounter < 80) ? 2 : 3;
						y += parabola[index];
						
						if (releaseCounter > 0) {
							releaseCounter--;
						} else {
							leftoverEnergy = false;
							timeCounter = 0;
						}
					} else {
					
						index = (timeCounter < 20) ? 0 : (timeCounter < 40) ? 1 : (timeCounter < 80) ? 2 : 3;
						lastSavedValue = index;
						y -= parabola[index];
						timeCounter++;
					
					}
				} else if (isPressed == true && isReleased == false) {
					if (leftoverEnergy == true && onCeiling == false) {
						if (havePassed == false) {
							switch (lastSavedValue) {
								case 0:
									timeCounter = 19;
									break;
								case 1:
									timeCounter = 39;
									break;
								case 2:
								case 3:
									timeCounter = 79;
									break;
								default:
									// nothing
							}
							
							havePassed = true;
						}
						
						index = (timeCounter == 0) ? 4 : (timeCounter < 20) ? 0 : (timeCounter < 40) ? 1 : (timeCounter < 80) ? 2 : 3;
						y -= parabola[index];
						
						if (timeCounter > 0) {
							timeCounter--;
						} else {
							leftoverEnergy = false;
							releaseCounter = 0;
						}
					} else {
					
						index = (releaseCounter < 20) ? 0 : (releaseCounter < 40) ? 1 : (releaseCounter < 80) ? 2 : 3;
						lastSavedValue = index;
						y += parabola[index];
						releaseCounter++;
					
					}
				}
			}
			
			yValues[n] = y;
			deltaY = yValues[0] - yValues[1];
			
			System.out.println("tick = " + tick + ", yValues[" + n + "] = " + yValues[n] + ", yValues[0] = " + yValues[0] + ", yValues[1] = " + yValues[1] + ", deltaY = " + deltaY);
				
			if (y >= 287) {
				y = 287;
				leftoverEnergy = false;
				havePassed = false;
				g.drawImage(ship, x, y, Graphics.RIGHT | Graphics.BOTTOM);
			} else if (y <= 40) {
				y = 40;
				leftoverEnergy = false;
				havePassed = false;
				g.drawImage(ship, x, y, Graphics.RIGHT | Graphics.BOTTOM);
			} else {
				if (isNormalGravity == true) {
					if (deltaY < 0) {
						switch (Math.abs(deltaY)) {
							case 1:
								g.drawImage(ship_1, x, y, Graphics.RIGHT | Graphics.BOTTOM);
								break;
							case 2:
								g.drawImage(ship_2, x, y, Graphics.RIGHT | Graphics.BOTTOM);
								break;
							case 3:
								g.drawImage(ship_3, x, y, Graphics.RIGHT | Graphics.BOTTOM);
								break;
							case 4:
								g.drawImage(ship_4, x, y, Graphics.RIGHT | Graphics.BOTTOM);
								break;
						}
					} else if (deltaY > 0) {
						switch (deltaY) {
							case 1:
								g.drawImage(ship_1, x, y, Graphics.RIGHT | Graphics.BOTTOM);
								break;
							case 2:
								g.drawImage(ship_2, x, y, Graphics.RIGHT | Graphics.BOTTOM);
								break;
							case 3:
								g.drawImage(ship_3, x, y, Graphics.RIGHT | Graphics.BOTTOM);
								break;
							case 4:
								g.drawImage(ship_4, x, y, Graphics.RIGHT | Graphics.BOTTOM);
								break;
						}
					} else if (deltaY == 0) {
						g.drawImage(ship, x, y, Graphics.RIGHT | Graphics.BOTTOM);
					}
				} else { // change this one
					if (deltaY > 0) {
						switch (deltaY) {
							case 1:
								g.drawImage(ship_1, x, y, Graphics.RIGHT | Graphics.BOTTOM);
								break;
							case 2:
								g.drawImage(ship_2, x, y, Graphics.RIGHT | Graphics.BOTTOM);
								break;
							case 3:
								g.drawImage(ship_3, x, y, Graphics.RIGHT | Graphics.BOTTOM);
								break;
							case 4:
								g.drawImage(ship_4, x, y, Graphics.RIGHT | Graphics.BOTTOM);
								break;
						}
					} else if (deltaY < 0) {
						switch (Math.abs(deltaY)) {
							case 1:
								g.drawImage(ship_1, x, y, Graphics.RIGHT | Graphics.BOTTOM);
								break;
							case 2:
								g.drawImage(ship_2, x, y, Graphics.RIGHT | Graphics.BOTTOM);
								break;
							case 3:
								g.drawImage(ship_3, x, y, Graphics.RIGHT | Graphics.BOTTOM);
								break;
							case 4:
								g.drawImage(ship_4, x, y, Graphics.RIGHT | Graphics.BOTTOM);
								break;
						}
					} else if (deltaY == 0) {
						g.drawImage(ship, x, y, Graphics.RIGHT | Graphics.BOTTOM);
					}
				}
			}
			
			// flushGraphics(); do it manually
			tick++;
			
			updateState();
			
		} else if (isShip == true && isMiniMode == true) {
			boolean onGround = (y == 287) ? true : false;
			boolean onCeiling = (y == 40) ? true : false;
			
			if (isNormalGravity == true) {
				if (isPressed == true && isReleased == false) {
					if (leftoverEnergy == true && onGround == false) {
						if (havePassed == false) {
							switch (lastSavedValue) {
								case 0:
									releaseCounter = 19;
									break;
								case 1:
									releaseCounter = 39;
									break;
								case 2:
								case 3:
									releaseCounter = 79;
									break;
								default:
									// nothing
							}
							
							havePassed = true;
						}
						
						index = (releaseCounter == 0) ? 4 : (releaseCounter < 20) ? 0 : (releaseCounter < 40) ? 1 : (releaseCounter < 80) ? 2 : 3;
						y += parabolaMini[index];
						
						if (releaseCounter > 0) {
							releaseCounter--;
						} else {
							leftoverEnergy = false;
							timeCounter = 0;
						}
					} else {
					
						index = (timeCounter < 20) ? 0 : (timeCounter < 40) ? 1 : (timeCounter < 80) ? 2 : 3;
						lastSavedValue = index;
						y -= parabolaMini[index];
						timeCounter++;
					
					}
					
				} else if (isReleased == true && isPressed == false) {
					if (leftoverEnergy == true && onCeiling == false) {
						if (havePassed == false) {
							switch (lastSavedValue) {
								case 0:
									timeCounter = 19;
									break;
								case 1:
									timeCounter = 39;
									break;
								case 2:
								case 3:
									timeCounter = 79;
									break;
								default:
									// nothing
							}
							
							havePassed = true;
						}
						
						index = (timeCounter == 0) ? 4 : (timeCounter < 20) ? 0 : (timeCounter < 40) ? 1 : (timeCounter < 80) ? 2 : 3;
						y -= parabolaMini[index];
						
						if (timeCounter > 0) {
							timeCounter--;
						} else {
							leftoverEnergy = false;
							releaseCounter = 0;
						}
					} else {
					
						index = (releaseCounter < 20) ? 0 : (releaseCounter < 40) ? 1 : (releaseCounter < 80) ? 2 : 3;
						lastSavedValue = index;
						y += parabolaMini[index];
						releaseCounter++;
					
					}
				} 
			} else {
				if (isReleased == true && isPressed == false) {
					if (leftoverEnergy == true && onGround == false) {
						if (havePassed == false) {
							switch (lastSavedValue) {
								case 0:
									releaseCounter = 19;
									break;
								case 1:
									releaseCounter = 39;
									break;
								case 2:
								case 3:
									releaseCounter = 79;
									break;
								default:
									// nothing
							}
							
							havePassed = true;
						}
						
						index = (releaseCounter == 0) ? 4 : (releaseCounter < 20) ? 0 : (releaseCounter < 40) ? 1 : (releaseCounter < 80) ? 2 : 3;
						y += parabolaMini[index];
						
						if (releaseCounter > 0) {
							releaseCounter--;
						} else {
							leftoverEnergy = false;
							timeCounter = 0;
						}
					} else {
					
						index = (timeCounter < 20) ? 0 : (timeCounter < 40) ? 1 : (timeCounter < 80) ? 2 : 3;
						lastSavedValue = index;
						y -= parabolaMini[index];
						timeCounter++;
					
					}
				} else if (isPressed == true && isReleased == false) {
					if (leftoverEnergy == true && onCeiling == false) {
						if (havePassed == false) {
							switch (lastSavedValue) {
								case 0:
									timeCounter = 19;
									break;
								case 1:
									timeCounter = 39;
									break;
								case 2:
								case 3:
									timeCounter = 79;
									break;
								default:
									// nothing
							}
							
							havePassed = true;
						}
						
						index = (timeCounter == 0) ? 4 : (timeCounter < 20) ? 0 : (timeCounter < 40) ? 1 : (timeCounter < 80) ? 2 : 3;
						y -= parabolaMini[index];
						
						if (timeCounter > 0) {
							timeCounter--;
						} else {
							leftoverEnergy = false;
							releaseCounter = 0;
						}
					} else {
					
						index = (releaseCounter < 20) ? 0 : (releaseCounter < 40) ? 1 : (releaseCounter < 80) ? 2 : 3;
						lastSavedValue = index;
						y += parabolaMini[index];
						releaseCounter++;
					
					}
				}
			}
			
			if (y >= 287) {
				y = 287;
				leftoverEnergy = false;
				havePassed = false;
				g.drawImage(ship, x, y, Graphics.RIGHT | Graphics.BOTTOM);
			} else if (y <= 40) {
				y = 40;
				leftoverEnergy = false;
				havePassed = false;
				g.drawImage(ship, x, y, Graphics.RIGHT | Graphics.BOTTOM);
			} else {
				if (isNormalGravity == true) {
					if (deltaY < 0) {
						switch (Math.abs(deltaY)) {
							case 1:
								g.drawImage(ship_1, x, y, Graphics.RIGHT | Graphics.BOTTOM);
								break;
							case 2:
								g.drawImage(ship_2, x, y, Graphics.RIGHT | Graphics.BOTTOM);
								break;
							case 4:
								g.drawImage(ship_3, x, y, Graphics.RIGHT | Graphics.BOTTOM);
								break;
							case 8:
								g.drawImage(ship_4, x, y, Graphics.RIGHT | Graphics.BOTTOM);
								break;
						}
					} else if (deltaY > 0) {
						switch (deltaY) {
							case 1:
								g.drawImage(ship_1, x, y, Graphics.RIGHT | Graphics.BOTTOM);
								break;
							case 2:
								g.drawImage(ship_2, x, y, Graphics.RIGHT | Graphics.BOTTOM);
								break;
							case 4:
								g.drawImage(ship_3, x, y, Graphics.RIGHT | Graphics.BOTTOM);
								break;
							case 8:
								g.drawImage(ship_4, x, y, Graphics.RIGHT | Graphics.BOTTOM);
								break;
						}
					} else if (deltaY == 0) {
						g.drawImage(ship, x, y, Graphics.RIGHT | Graphics.BOTTOM);
					}
				} else { // change this one
					if (deltaY > 0) {
						switch (deltaY) {
							case 1:
								g.drawImage(ship_1, x, y, Graphics.RIGHT | Graphics.BOTTOM);
								break;
							case 2:
								g.drawImage(ship_2, x, y, Graphics.RIGHT | Graphics.BOTTOM);
								break;
							case 4:
								g.drawImage(ship_3, x, y, Graphics.RIGHT | Graphics.BOTTOM);
								break;
							case 8:
								g.drawImage(ship_4, x, y, Graphics.RIGHT | Graphics.BOTTOM);
								break;
						}
					} else if (deltaY < 0) {
						switch (Math.abs(deltaY)) {
							case 1:
								g.drawImage(ship_1, x, y, Graphics.RIGHT | Graphics.BOTTOM);
								break;
							case 2:
								g.drawImage(ship_2, x, y, Graphics.RIGHT | Graphics.BOTTOM);
								break;
							case 4:
								g.drawImage(ship_3, x, y, Graphics.RIGHT | Graphics.BOTTOM);
								break;
							case 8:
								g.drawImage(ship_4, x, y, Graphics.RIGHT | Graphics.BOTTOM);
								break;
						}
					} else if (deltaY == 0) {
						g.drawImage(ship, x, y, Graphics.RIGHT | Graphics.BOTTOM);
					}
				}
			}
			
			// flushGraphics(); do it manually
			
			tick++;
			
			updateState();
		} else {
			System.out.println("why did you even choose false");
		}
		
		// flushGraphics(); (manually flushGraphics)
		
	}
	
	public void waveMode(boolean isWave, boolean isNormalGravity, boolean isMiniMode) {
		
		Graphics g = getGraphics();
		gamemode = 2;
		
		if (isWave == true && isMiniMode == false) {
			
			int shift = 5;
			switch (mainApp.speedCount) {
				case 1: shift = 10; break;
				case 2: shift = 3; break;
				case 3: shift = 2; break;
				case 4: shift = 1; break;
			}
			
			if (isNormalGravity == true) {
				if (isPressed == true && isReleased == false) {
					y -= shift;
					isUp = true;
					isDown = false;
				} else if (isPressed == false && isReleased == true) {
					y += shift;
					isUp = false;
					isDown = true;
				}
			} else {
				if (isPressed == false && isReleased == true) {
					y -= shift;
					isUp = false;
					isDown = true;
				} else if (isPressed == true && isReleased == false) {
					y += shift;
					isUp = true;
					isDown = false;
				}
			}
			
			if (y >= 287) y = 287;
			if (y <= 40) y = 40;
			
			if (y >= 287 || y <= 40) {
				g.drawImage(wave, x, y, Graphics.RIGHT | Graphics.BOTTOM);
			} else {
				if (isUp == true && isDown == false) {
					g.drawImage(wave_up, x, y, Graphics.RIGHT | Graphics.BOTTOM);
				} else if (isDown = true && isUp == false) {
					g.drawImage(wave_down, x, y, Graphics.RIGHT | Graphics.BOTTOM);
				}
			}
			
			// flushGraphics(); manually add
			
			updateState();
			
		} else if (isWave == true && isMiniMode == true) {
			int shift = 10;
			
			switch (mainApp.speedCount) {
				case 1: shift = 20; break;
				case 2: shift = 6; break;
				case 3: shift = 4; break;
				case 4: shift = 2; break;
			}
			
			int verticalDelta = (shift * 2236) / 1000;
			
			if (isNormalGravity == true) {
				if (isPressed == true && isReleased == false) {
					y -= verticalDelta;
					isUp = true;
					isDown = false;
				} else if (isPressed == false && isReleased == true) {
					y += verticalDelta;
					isUp = false;
					isDown = true;
				}
			} else {
				if (isPressed == false && isReleased == true) {
					y -= verticalDelta;
					isUp = true;
					isDown = false;
				} else if (isPressed == true && isReleased == false) {
					y += verticalDelta;
					isUp = false;
					isDown = true;
				}
			}
			
			if (y >= 287) y = 287;
			if (y <= 40) y = 40;
			
			if (y >= 287 || y <= 40) {
				g.drawImage(wave, x, y, Graphics.RIGHT | Graphics.BOTTOM);
			} else {
				if (isUp == true && isDown == false) {
					g.drawImage(wave_up, x, y, Graphics.RIGHT | Graphics.BOTTOM);
				} else if (isDown = true && isUp == false) {
					g.drawImage(wave_down, x, y, Graphics.RIGHT | Graphics.BOTTOM);
				}
			}
			
			// flushGraphics(); manually add
			
			updateState();
		} else if (isWave == false) {
			System.out.println("why did you even choose false");
		}
	}
	
	public void ballMode(boolean isBall, boolean isNormalGravity, boolean isMiniMode) {
		
		int[] parabola = {1,2,4,8};
		Graphics g = getGraphics();
		gamemode = 3;
		
		// System.out.println("normalGravity = " + normalGravity);
		if (passed == false) {
			if (isNormalGravity == true) {y = 287; normalGravity = true;}
			else {y = 100; normalGravity = false;}
			passed = true;
		}
		
		if (isBall == true && isMiniMode == false) {
			if (oneShotPressed == true && canPress == true) {
				if (y >= 287 || y <= 100) {
					normalGravity = !normalGravity;
					canPress = false;
					pressedCounter = 0;
				}
			}
					
			int index = (pressedCounter < 5) ? 0 : (pressedCounter < 10) ? 1 : (pressedCounter < 20) ? 2 : 3;
			
			if (isNormalGravity == true) {
				if (normalGravity == false) {velocityY = y += parabola[index];}
				else {velocityY = y -= parabola[index];}
			} else {
				if (normalGravity == false) {velocityY = y -= parabola[index];}
				else {velocityY = y += parabola[index];}
			}
			
			if (y >= 287) {
				y = 287;
				g.drawImage(ball, x, y, Graphics.RIGHT | Graphics.BOTTOM);
			} else if (y <= 100) {
				y = 100;
				g.drawImage(ball, x, y, Graphics.RIGHT | Graphics.BOTTOM);
			} else {
				g.drawImage(ball, x, y, Graphics.RIGHT | Graphics.BOTTOM);
			}
			
			// flushGraphics();
			
			pressedCounter++;
			
			updateState();
				
		} else if (isBall == true && isMiniMode == true) {
			if (oneShotPressed == true && canPress == true) {
				if (y >= 287 || y <= 100) {
					normalGravity = !normalGravity;
					canPress = false;
					pressedCounter = 0;
				}
			}
					
			int index = (pressedCounter < 3) ? 0 : (pressedCounter < 6) ? 1 : (pressedCounter < 12) ? 2 : 3;
			
			if (isNormalGravity == true) {
				if (normalGravity == false) {velocityY = y += parabola[index];}
				else {velocityY = y -= parabola[index];}
			} else {
				if (normalGravity == false) {velocityY = y -= parabola[index];}
				else {velocityY = y += parabola[index];}
			}
			
			if (y >= 287) {
				y = 287;
				g.drawImage(ball, x, y, Graphics.RIGHT | Graphics.BOTTOM);
			} else if (y <= 100) {
				y = 100;
				g.drawImage(ball, x, y, Graphics.RIGHT | Graphics.BOTTOM);
			} else {
				g.drawImage(ball, x, y, Graphics.RIGHT | Graphics.BOTTOM);
			}
			
			// flushGraphics();
			
			pressedCounter++;
			
			updateState();
		} else {
			// nothing
		}
	}
	
	public void spiderMode(boolean isSpider, boolean isNormalGravity, boolean isMiniMode) {
		Graphics g = getGraphics();
		gamemode = 4;
		int oldY = y;
		
		if (isSpider == true && isMiniMode == false) {
			
			if (oneShotPressed == true && canPress == true) {
				if (y >= 287 || y <= 40) {
					normalGravity = !normalGravity;
					canPress = false;
				}
			}
			
			if (isNormalGravity == true) {
				if (normalGravity == false) y = 40;
				else y = 287;
			} else {
				if (normalGravity == false) y = 287;
				else y = 40;
			}
			
			velocityY = y - oldY;
			
			if (y == 40) {
				g.drawImage(spider_inverted, x, y, Graphics.RIGHT | Graphics.BOTTOM);
			} else if (y == 287) {
				g.drawImage(spider, x, y, Graphics.RIGHT | Graphics.BOTTOM);
			}
			
			updateState();
		} else if (isSpider == true && isMiniMode == true) {
			
			if (oneShotPressed == true && canPress == true) {
				if (y >= 287 || y <= 40) {
					normalGravity = !normalGravity;
					canPress = false;
				}
			}
			
			if (isNormalGravity == true) {
				if (normalGravity == false) y = 40;
				else y = 287;
			} else {
				if (normalGravity == false) y = 287;
				else y = 40;
			}
			
			velocityY = y - oldY;
			
			if (y == 40) {
				g.drawImage(spider_inverted, x, y, Graphics.RIGHT | Graphics.BOTTOM);
			} else if (y == 287) {
				g.drawImage(spider, x, y, Graphics.RIGHT | Graphics.BOTTOM);
			}
			
			updateState();
		} else {
			
		}
	}
	
	public void ufoMode(boolean isUFO, boolean isNormalGravity, boolean isMiniMode) {
		Graphics g = getGraphics();
		gamemode = 5;
		int parabola[] = {1,2,3,4};
		int parabola2[] = {1,2};
		
		if (isUFO == true && isMiniMode == false) {
			System.out.println("timeCounter = " + timeCounter + ", y = " + y + ", releaseCounter = " + releaseCounter + ", timer = " + timer);
			if (oneShotPressed == true && canPress == true) {
				canPress = false;
				timer = 12;
				timeCounter = 0;
			}
			
			if (timer > 0) {
				int index = (timeCounter < 3) ? 3 : (timeCounter < 6) ? 2 : (timeCounter < 12) ? 1 : 0;
				velocityY = parabola[index];
				y -= velocityY;
				timer--;
			} else if (timer <= 0) {
				if (y < 287) {
					int index = (releaseCounter < 3) ? 0 : (releaseCounter < 6) ? 1 : (releaseCounter < 12) ? 2 : 3;
					velocityY = parabola[index];
					y += velocityY;
					releaseCounter++;
				}
			}
				
			
			if (y >= 287) {
				y = 287;
				timer = -1;
				g.drawImage(ufo, x, y, Graphics.RIGHT | Graphics.BOTTOM);
			} else if (y <= 40) {
				y = 40;
				timer = -1;
				g.drawImage(ufo, x, y, Graphics.RIGHT | Graphics.BOTTOM);
			} else {
				g.drawImage(ufo, x, y, Graphics.RIGHT | Graphics.BOTTOM);
			}
		} else if (isUFO == true && isMiniMode == true) {
			System.out.println("timeCounter = " + timeCounter + ", y = " + y + ", releaseCounter = " + releaseCounter + ", timer = " + timer);
			if (oneShotPressed == true && canPress == true) {
				canPress = false;
				timer = 12;
				timeCounter = 0;
			}
			
			if (timer > 0) {
				int index = (timeCounter < 3) ? 1 : 0;
				velocityY = parabola[index];
				y -= velocityY;
				timer--;
			} else if (timer <= 0) {
				if (y < 287) {
					int index = (releaseCounter < 3) ? 0 : 1;
					velocityY = parabola[index];
					y += velocityY;
					releaseCounter++;
				}
			}
				
			
			if (y >= 287) {
				y = 287;
				timer = -1;
				g.drawImage(ufo, x, y, Graphics.RIGHT | Graphics.BOTTOM);
			} else if (y <= 40) {
				y = 40;
				timer = -1;
				g.drawImage(ufo, x, y, Graphics.RIGHT | Graphics.BOTTOM);
			} else {
				g.drawImage(ufo, x, y, Graphics.RIGHT | Graphics.BOTTOM);
			}
		} else {
			// nothing
		}
		
		timeCounter++;
		
		// updateState(); REMEMBER TO ADD UPDATE STATE FOR THIS PARTICULAR GAMEMODE
	}
	
	public void cubeMode(boolean isCube, boolean isNormalGravity, boolean isMiniMode) {
		
		
		
		
	}
	
	public void updateState() {
		switch(mainApp.speedCount) {
			case 1:
			try {
				Thread.sleep(10);
			} catch (InterruptedException error1) { }
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
			} catch (InterruptedException error) { }
			break;
		}
	}
	
	protected void pointerPressed(int x, int y) {
		
		// continuous + one-shot components
		isPressed = true;
		isReleased = false;
		releaseCounter = 0;
		leftoverEnergy = true;
		havePassed = false;
		
		// specifically one-shot components
		oneShotPressed = true;
	}
	
	protected void pointerReleased(int x, int y) {
		
		// continuous + one-shot components
		isPressed = false;
		isReleased = true;
		leftoverEnergy = true;
		havePassed = false;
		
		// specifically one-shot components
		oneShotPressed = false;
		canPress = true;
	}
}