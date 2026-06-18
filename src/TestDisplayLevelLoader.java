import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;

public class TestDisplayLevelLoader extends GameCanvas implements Runnable {
	
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
								
	private Image spreadsheet;
	private Image ship;
	
	/* private volatile boolean isPressing = false;
	private volatile boolean isHolding = false;
	private volatile boolean tapped = false;
	private volatile boolean falling = false; */
	private int timeCounter = 0;
	private int releaseCounter = 0;
	private boolean isReleased = false;
	private boolean isPressed = false;
	private volatile boolean runningMode = false;
	
	private long startPressingTime = 0;
	private long startReleasingTime = 0;
	
	int y = 287;
	
	// private long startTime = 0;
	
	public int[] srcID = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40};
	
	public int[] srcX = {0,93};
	public int[] srcY = {0,207};
	
	public TestDisplayLevelLoader(j2meDash mainApp) {
		
		super(true);
		this.mainApp = mainApp;
		
		try {
			sheet = Image.createImage("assets/sheet.png");
			ship = Image.createImage("assets/ship.png");
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
		
		runningMode = true;
		int a = 287;
		int b = 0;
		
		Graphics g = getGraphics();
		
		g.setColor(0xabcdef);
		g.fillRect(0,0,240,400);
		// shipMode();
		
		callParseLBP("levels/example.bin");
		
		// temporarily failcheck
		if (mainApp.data[32] == 0x00) {
			System.out.println("cannot continue");
		}
		
		printObjectOriginally();
		
		flushGraphics();
		
		if (g == null) System.out.println("how");
		
		try { Thread.sleep(2000); } catch (Exception e) { }
		
		while (isRunning) {
			System.out.println("ship Y coord: " + y);
			g.fillRect(0,0,240,400);
			printObjectMoving(5); // ALWAYS 5 NO MATTER WHAT
			
			if (isPressed == true && isReleased == false) {
				if (timeCounter < 50) {
					g.drawImage(ship, 81, y -= 5, Graphics.RIGHT | Graphics.BOTTOM); 
				} else if (timeCounter >= 50) {
					g.drawImage(ship, 81, y -= 8, Graphics.RIGHT | Graphics.BOTTOM); 
				}
			} else if (isReleased == true && isPressed == false) {
				if (releaseCounter < 50) {
					g.drawImage(ship, 81, y += 5, Graphics.RIGHT | Graphics.BOTTOM); 
				} else if (releaseCounter >= 50) {
					g.drawImage(ship, 81, y += 8, Graphics.RIGHT | Graphics.BOTTOM); 
				}
			} else if (y >= 287) {
				isReleased = false;
				isPressed = false;
				y = 287;
				shipMode(true); // assuming the ship always touch the ground (which is definitely false)
			}
			
			flushGraphics();
			
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
				} catch (InterruptedException error) {
					// nothing
				}
				break;
			}
		}
		
	}
	
	public void paint(Graphics g) {
		
		// test
		
		/* for (int i = 0; i < levelBinaryParser.objectNumber; i++) {
			g.drawRegion(sheet, 93, 207, 21, 21, Sprite.TRANS_NONE, levelBinaryParser.xArray[i], levelBinaryParser.yArray[i], Graphics.LEFT | Graphics.TOP);
		} */
		
	}
	
	public void hitboxCollision() {
		
		if (playScreen == null) {
			playScreen = new PlayScreen(mainApp);
		}
		
		Graphics g = getGraphics();
		
		g.setColor(0xff0000);
		
		if (playScreen.haveTouched == true) {
			for (int i = 0; i < (levelBinaryParser.idArray.length - 1); i++) {
				if (levelBinaryParser.idArray[i] == levelBinaryParser.safeObjectID[i]) {
					
					
					
				} else if (levelBinaryParser.idArray[i] == levelBinaryParser.dangerousObjectID[i]) {
					
					
					
				}
			}
		}
	}
	
	public void shipMode(boolean isShip) {
		
		Graphics g = getGraphics();
		
		if (isShip == true) {
			g.drawImage(ship, 81, y, Graphics.RIGHT | Graphics.BOTTOM);
		} else {
			System.out.println("why did you even choose false");
		}
		
		// flushGraphics(); (manually flushGraphics)
		
	}
	
	/* public void timeCounting(boolean state) {
		// please put this in a loop
		if (state == true) {
			switch (mainApp.speedCount) {
				case 1:
					timeCounter += 10;
					break;
				case 2:
					timeCounter += 3;
					break;
				case 3:
					timeCounter += 2;
					break;
				case 4:
					timeCounter += 1;
					break;
				default:
					timeCounter += 5;
					break;
			}
		} else {
			System.out.println("clicked for: " + timeCounter);
		}
			
	}
	
	public void releaseCounting(boolean state) {
		// please put this in a loop
		
		// startTime = System.currentTimeMillis();
		
		if (state == true) {
			switch (mainApp.speedCount) {
				case 1:
					releaseCounter += 10;
					break;
				case 2:
					releaseCounter += 3;
					break;
				case 3:
					releaseCounter += 2;
					break;
				case 4:
					releaseCounter += 1;
					break;
				default:
					releaseCounter += 5;
					break;
			}
		} else {
			System.out.println("released for: " + releaseCounter);
		}
			
	} */
	
	protected void pointerPressed(int x, int y) {
		
		double releasingDuration = Math.floor((System.currentTimeMillis() - startReleasingTime) / 10) * 10;
		
		if (releasingDuration < Integer.MAX_VALUE) {
			releaseCounter = (int) releasingDuration;
		}
		
		startPressingTime = System.currentTimeMillis();
		
		isPressed = true;
		isReleased = false;
	}
	
	protected void pointerReleased(int x, int y) {
		
		double pressingDuration = Math.floor((System.currentTimeMillis() - startPressingTime) / 10) * 10;
		
		if (pressingDuration < Integer.MAX_VALUE) {
			timeCounter = (int) pressingDuration;
		}
		
		startReleasingTime = System.currentTimeMillis();
		
		isPressed = false;
		isReleased = true;
	}
}