import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;

public class GameEngine extends GameCanvas implements Runnable {
	
	private j2meDash mainApp;
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
								
	private int[][] gamemodeWidthAndHeight = {{21,21,10,10},{21,30,10,15},{},{},{},{}};
	private Image spreadsheet;
	
	// global components for all gamemodes
	private boolean isReleased = false;
	private boolean isPressed = false;
	private boolean oneShotPressed = false;
	private boolean canPress = true;
	private boolean normalGravity = true;
	private int y = 287;
	
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
	
	public int[] srcID = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40};
	
	public int[] srcX = {0,93};
	public int[] srcY = {0,207};
	
	public GameEngine(j2meDash mainApp) {
		
		super(true);
		this.mainApp = mainApp;
		
		try {
			sheet = Image.createImage("assets/sheet.png");
			
			// ship
			ship = Image.createImage("assets/ship.png");
			ship_1 = Image.createImage("assets/ship_1.png");
			ship_2 = Image.createImage("assets/ship_2.png");
			ship_3 = Image.createImage("assets/ship_3.png");
			ship_4 = Image.createImage("assets/ship_4.png");
			
			// wave
			wave = Image.createImage("assets/wave.png");
			wave_up = Image.createImage("assets/wave_up.png");
			wave_down = Image.createImage("assets/wave_down.png");
			
			// ball
			ball = Image.createImage("assets/ball.png");
			
			// spider
			spider = Image.createImage("assets/spider.png");
			spider_inverted = Image.createImage("assets/spider_inverted.png");
			
			// ufo
			ufo = Image.createImage("assets/ufo.png");
			ufo_inverted = Image.createImage("assets/ufo_inverted.png");
			
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
		
		callParseLBP("levels/example.bin");
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
		
		try { Thread.sleep(5000); } catch (Exception e) { e.printStackTrace(); }
		
		while (isRunning) {
			
			g.fillRect(0,0,240,400);
			
			// add the assets for ship with gravity inverted and mini ship with gravity inverted and normal
			
			// TEST SANDBOX //
			shipMode(true, true, false);
			
			printObjectMoving(5);
			// -------------------------- //
			
			flushGraphics();
			
			for (int i = 0; i < maxValue; i++) {
				
				if (levelBinaryParser.xArray[i] < -32 || levelBinaryParser.xArray[] > 128) {
					checkCollision(levelBinaryParser.idArray[i], 81, y, gamemodeWidthAndHeight[1][2], gamemodeWidthAndHeight[1][3], levelBinaryParser.xArray[i], levelBinaryParser.yArray[i], widthAndHeight[levelBinaryParser.idArray[i]][2], widthAndHeight[levelBinaryParser.idArray[i]][3]);
					System.out.println("xArray[] = " + levelBinaryParser.xArray[i] + ", yArray[] = " + levelBinaryParser.yArray[i]);
				}
				
			}
			
			updateState();
		} 
		
	}
	
	public void checkCollision(
	int id, int Ax, int Ay, int Aw, int Ah,
	int Bx, int By, int Bw, int Bh
	) {
		
		boolean value = ((Ax < Bx + Bw) && (Ax + Aw > Bx) && (Ay < By + Bh) && (Ay + Ah > By));
		
		System.out.println(
		"Ax < Bx + Bw = " + (Ax < Bx + Bw) +
		"\nAx + Aw > Bx = " + (Ax + Aw > Bx) +
		"\nAy < By + Bh = " + (Ay < By + Bh) +
		"\nAy + Ah > By = " + (Ay + Ah > By) +
		"\nAx = " + Ax + ", Ay = " + Ay + ", Aw = " + Aw + ", Ah = " + Ah +
		"\nBx = " + Bx + ", By = " + By + ", Bw = " + Bw + ", Bh = " + Bh +
		"\nTotal = " + value);
		
		if (value == true) {
			System.out.println("dead");
			return;
		}
	}
	
	public void shipMode(boolean isShip, boolean isNormalGravity, boolean isMiniMode) {
		
		Graphics g = getGraphics();
		int[] parabola = {1,2,3,4,0};
		int[] parabolaMini = {1,2,4,8,0};
		int lastSavedValue = 0;
		int index = 0;
		int n = (tick % 2 == 0) ? 0 : 1;
		int deltaY = 0;
		
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
				g.drawImage(ship, 81, y, Graphics.RIGHT | Graphics.BOTTOM);
			} else if (y <= 40) {
				y = 40;
				leftoverEnergy = false;
				havePassed = false;
				g.drawImage(ship, 81, y, Graphics.RIGHT | Graphics.BOTTOM);
			} else {
				if (isNormalGravity == true) {
					if (deltaY < 0) {
						switch (Math.abs(deltaY)) {
							case 1:
								g.drawImage(ship_1, 81, y, Graphics.RIGHT | Graphics.BOTTOM);
								break;
							case 2:
								g.drawImage(ship_2, 81, y, Graphics.RIGHT | Graphics.BOTTOM);
								break;
							case 3:
								g.drawImage(ship_3, 81, y, Graphics.RIGHT | Graphics.BOTTOM);
								break;
							case 4:
								g.drawImage(ship_4, 81, y, Graphics.RIGHT | Graphics.BOTTOM);
								break;
						}
					} else if (deltaY > 0) {
						switch (deltaY) {
							case 1:
								g.drawImage(ship_1, 81, y, Graphics.RIGHT | Graphics.BOTTOM);
								break;
							case 2:
								g.drawImage(ship_2, 81, y, Graphics.RIGHT | Graphics.BOTTOM);
								break;
							case 3:
								g.drawImage(ship_3, 81, y, Graphics.RIGHT | Graphics.BOTTOM);
								break;
							case 4:
								g.drawImage(ship_4, 81, y, Graphics.RIGHT | Graphics.BOTTOM);
								break;
						}
					} else if (deltaY == 0) {
						g.drawImage(ship, 81, y, Graphics.RIGHT | Graphics.BOTTOM);
					}
				} else { // change this one
					if (deltaY > 0) {
						switch (deltaY) {
							case 1:
								g.drawImage(ship_1, 81, y, Graphics.RIGHT | Graphics.BOTTOM);
								break;
							case 2:
								g.drawImage(ship_2, 81, y, Graphics.RIGHT | Graphics.BOTTOM);
								break;
							case 3:
								g.drawImage(ship_3, 81, y, Graphics.RIGHT | Graphics.BOTTOM);
								break;
							case 4:
								g.drawImage(ship_4, 81, y, Graphics.RIGHT | Graphics.BOTTOM);
								break;
						}
					} else if (deltaY < 0) {
						switch (Math.abs(deltaY)) {
							case 1:
								g.drawImage(ship_1, 81, y, Graphics.RIGHT | Graphics.BOTTOM);
								break;
							case 2:
								g.drawImage(ship_2, 81, y, Graphics.RIGHT | Graphics.BOTTOM);
								break;
							case 3:
								g.drawImage(ship_3, 81, y, Graphics.RIGHT | Graphics.BOTTOM);
								break;
							case 4:
								g.drawImage(ship_4, 81, y, Graphics.RIGHT | Graphics.BOTTOM);
								break;
						}
					} else if (deltaY == 0) {
						g.drawImage(ship, 81, y, Graphics.RIGHT | Graphics.BOTTOM);
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
				g.drawImage(ship, 81, y, Graphics.RIGHT | Graphics.BOTTOM);
			} else if (y <= 40) {
				y = 40;
				leftoverEnergy = false;
				havePassed = false;
				g.drawImage(ship, 81, y, Graphics.RIGHT | Graphics.BOTTOM);
			} else {
				if (isNormalGravity == true) {
					if (deltaY < 0) {
						switch (Math.abs(deltaY)) {
							case 1:
								g.drawImage(ship_1, 81, y, Graphics.RIGHT | Graphics.BOTTOM);
								break;
							case 2:
								g.drawImage(ship_2, 81, y, Graphics.RIGHT | Graphics.BOTTOM);
								break;
							case 4:
								g.drawImage(ship_3, 81, y, Graphics.RIGHT | Graphics.BOTTOM);
								break;
							case 8:
								g.drawImage(ship_4, 81, y, Graphics.RIGHT | Graphics.BOTTOM);
								break;
						}
					} else if (deltaY > 0) {
						switch (deltaY) {
							case 1:
								g.drawImage(ship_1, 81, y, Graphics.RIGHT | Graphics.BOTTOM);
								break;
							case 2:
								g.drawImage(ship_2, 81, y, Graphics.RIGHT | Graphics.BOTTOM);
								break;
							case 4:
								g.drawImage(ship_3, 81, y, Graphics.RIGHT | Graphics.BOTTOM);
								break;
							case 8:
								g.drawImage(ship_4, 81, y, Graphics.RIGHT | Graphics.BOTTOM);
								break;
						}
					} else if (deltaY == 0) {
						g.drawImage(ship, 81, y, Graphics.RIGHT | Graphics.BOTTOM);
					}
				} else { // change this one
					if (deltaY > 0) {
						switch (deltaY) {
							case 1:
								g.drawImage(ship_1, 81, y, Graphics.RIGHT | Graphics.BOTTOM);
								break;
							case 2:
								g.drawImage(ship_2, 81, y, Graphics.RIGHT | Graphics.BOTTOM);
								break;
							case 4:
								g.drawImage(ship_3, 81, y, Graphics.RIGHT | Graphics.BOTTOM);
								break;
							case 8:
								g.drawImage(ship_4, 81, y, Graphics.RIGHT | Graphics.BOTTOM);
								break;
						}
					} else if (deltaY < 0) {
						switch (Math.abs(deltaY)) {
							case 1:
								g.drawImage(ship_1, 81, y, Graphics.RIGHT | Graphics.BOTTOM);
								break;
							case 2:
								g.drawImage(ship_2, 81, y, Graphics.RIGHT | Graphics.BOTTOM);
								break;
							case 4:
								g.drawImage(ship_3, 81, y, Graphics.RIGHT | Graphics.BOTTOM);
								break;
							case 8:
								g.drawImage(ship_4, 81, y, Graphics.RIGHT | Graphics.BOTTOM);
								break;
						}
					} else if (deltaY == 0) {
						g.drawImage(ship, 81, y, Graphics.RIGHT | Graphics.BOTTOM);
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
				g.drawImage(wave, 81, y, Graphics.RIGHT | Graphics.BOTTOM);
			} else {
				if (isUp == true && isDown == false) {
					g.drawImage(wave_up, 81, y, Graphics.RIGHT | Graphics.BOTTOM);
				} else if (isDown = true && isUp == false) {
					g.drawImage(wave_down, 81, y, Graphics.RIGHT | Graphics.BOTTOM);
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
			
			int verticalDelta = (shift * 1732) / 1000;
			
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
				g.drawImage(wave, 81, y, Graphics.RIGHT | Graphics.BOTTOM);
			} else {
				if (isUp == true && isDown == false) {
					g.drawImage(wave_up, 81, y, Graphics.RIGHT | Graphics.BOTTOM);
				} else if (isDown = true && isUp == false) {
					g.drawImage(wave_down, 81, y, Graphics.RIGHT | Graphics.BOTTOM);
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
				if (normalGravity == false) {y += parabola[index];}
				else {y -= parabola[index];}
			} else {
				if (normalGravity == false) {y -= parabola[index];}
				else {y += parabola[index];}
			}
			
			if (y >= 287) {
				y = 287;
				g.drawImage(ball, 81, y, Graphics.RIGHT | Graphics.BOTTOM);
			} else if (y <= 100) {
				y = 100;
				g.drawImage(ball, 81, y, Graphics.RIGHT | Graphics.BOTTOM);
			} else {
				g.drawImage(ball, 81, y, Graphics.RIGHT | Graphics.BOTTOM);
			}
			
			// flushGraphics();
			
			pressedCounter++;
			
			updateState();
				
		} else if (isBall == true && isMiniMode == true) {
			if (oneShotPressed == true) {
				if (canPress == true) {
					if (y >= 287 || y <= 100) {
						if (normalGravity == true) {
							normalGravity = false;
						} else {
							normalGravity = true;
						}
						
						canPress = false;
						pressedCounter = 0;
					}
				}
			}
					
			int index = (pressedCounter < 3) ? 0 : (pressedCounter < 6) ? 1 : (pressedCounter < 12) ? 2 : 3;
			
			if (normalGravity == false) {
				y -= parabola[index];
			} else if (normalGravity == true) {
				y += parabola[index];
			}
			
			if (y >= 287) {
				y = 287;
				g.drawImage(ball, 81, y, Graphics.RIGHT | Graphics.BOTTOM);
			} else if (y <= 100) {
				y = 100;
				g.drawImage(ball, 81, y, Graphics.RIGHT | Graphics.BOTTOM);
			} else {
				g.drawImage(ball, 81, y, Graphics.RIGHT | Graphics.BOTTOM);
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
			
			if (y == 40) {
				g.drawImage(spider_inverted, 81, y, Graphics.RIGHT | Graphics.BOTTOM);
			} else if (y == 287) {
				g.drawImage(spider, 81, y, Graphics.RIGHT | Graphics.BOTTOM);
			}
			
			updateState();
		} else if (isSpider == true && isMiniMode == false) {
			
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
			
			if (y == 40) {
				g.drawImage(spider_inverted, 81, y, Graphics.RIGHT | Graphics.BOTTOM);
			} else if (y == 287) {
				g.drawImage(spider, 81, y, Graphics.RIGHT | Graphics.BOTTOM);
			}
			
			updateState();
		} else {
			
		}
	}
	
	public void ufoMode(boolean isUFO, boolean isNormalGravity, boolean isMiniMode) {
		Graphics g = getGraphics();
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
				y -= parabola[index];
				timer--;
			} else if (timer <= 0) {
				if (y < 287) {
					int index = (releaseCounter < 3) ? 0 : (releaseCounter < 6) ? 1 : (releaseCounter < 12) ? 2 : 3;
					y += parabola[index];
					releaseCounter++;
				}
			}
				
			
			if (y >= 287) {
				y = 287;
				timer = -1;
				g.drawImage(ufo, 81, y, Graphics.RIGHT | Graphics.BOTTOM);
			} else if (y <= 40) {
				y = 40;
				timer = -1;
				g.drawImage(ufo, 81, y, Graphics.RIGHT | Graphics.BOTTOM);
			} else {
				g.drawImage(ufo, 81, y, Graphics.RIGHT | Graphics.BOTTOM);
			}
		} else if (isUFO == true && isMiniMode == true) {
			if (oneShotPressed == true && canPress == true) {
				canPress = false;
				timer = 12;
				timeCounter = 0;
			}
			
			if (timer > 0) {
				int index = (timeCounter < 3) ? 0 : 1;
				y -= parabola2[index];
				timer--;
			} else if (timer == 0) {
				int index = (releaseCounter < 3) ? 1 : 0;
				y += parabola2[index];
				releaseCounter++;
			}
				
			
			if (y >= 287) {
				y = 287;
				timer = -1;
				g.drawImage(ufo, 81, y, Graphics.RIGHT | Graphics.BOTTOM);
			} else if (y <= 40) {
				y = 40;
				timer = -1;
				g.drawImage(ufo, 81, y, Graphics.RIGHT | Graphics.BOTTOM);
			} else {
				g.drawImage(ufo, 81, y, Graphics.RIGHT | Graphics.BOTTOM);
			}
		} else {
			// nothing
		}
		
		timeCounter++;
		
		// updateState(); REMEMBER TO ADD UPDATE STATE FOR THIS PARTICULAR GAMEMODE
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