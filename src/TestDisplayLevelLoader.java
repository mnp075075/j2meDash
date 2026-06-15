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
	
	public int[] srcID = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40};
	
	public int[] srcX = {0,93};
	public int[] srcY = {0,207};
	
	public TestDisplayLevelLoader(j2meDash mainApp) {
		
		super(true);
		this.mainApp = mainApp;
		
		try {
			sheet = Image.createImage("assets/sheet.png");
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
		
		for (int i = 0; i < levelBinaryParser.objectNumber; i++) {
			
			// temporarily failcheck
			if (mainApp.data[32+(8*i)] >= srcX.length && mainApp.data[32+8*i] >= srcY.length) {
				System.out.println("stop checking because of developer failcheck");
				break;
			}
			
			int id = mainApp.data[32+(8*i)];
			int currentID = levelBinaryParser.idArray[i];
			
			if (id > srcID[0] && id <= srcID[srcID.length-1]) {
				g.drawRegion(sheet, srcX[currentID], srcY[currentID], widthAndHeight[currentID][0], widthAndHeight[currentID][1], Sprite.TRANS_NONE, levelBinaryParser.xArray[i], levelBinaryParser.yArray[i], Graphics.RIGHT | Graphics.BOTTOM);
				flushGraphics();
				System.out.println("PRINTED OBJECT " + i + " WITH ID: " + currentID + " in Original position" + "\nWith the following details: srcX: " + srcX[currentID] + " srcY: " + srcY[currentID] + " w: " + widthAndHeight[currentID][0] + " h: " + widthAndHeight[currentID][1] + " destX: " + levelBinaryParser.xArray[i] + " destY: " + levelBinaryParser.yArray[i]);
			} else if (id <= srcID[0] || id > srcID[srcID.length]) {
				System.err.println("INVALID OBJECT ID AT: " + currentID + ". CHECK YOUR LEVEL FILES - original function");
			}
			
		}
	}
	
	public void printObjectMoving(int offset) {
		// dont use this method yet
		// i haven't finished the srcX[] and srcY[] at the moment
		Graphics g = getGraphics();
		
		for (int i = 0; i < levelBinaryParser.objectNumber; i++) {
			
			// temporarily failcheck
			if (mainApp.data[32+(8*i)] >= srcX.length && mainApp.data[32+8*i] >= srcY.length) {
				System.out.println("stop checking because of developer failcheck");
				break;
			}
			
			int id = mainApp.data[32+(8*i)];
			int currentID = levelBinaryParser.idArray[i];
			
			if (id > srcID[0] && id <= srcID[srcID.length-1]) {
				g.drawRegion(sheet, srcX[currentID], srcY[currentID], widthAndHeight[currentID][0], widthAndHeight[currentID][1], Sprite.TRANS_NONE, levelBinaryParser.xArray[i] -= offset, levelBinaryParser.yArray[i], Graphics.RIGHT | Graphics.BOTTOM);
				flushGraphics();
				System.out.println("PRINTED OBJECT " + i + " WITH ID: " + currentID + " in Moving position at offset: " + offset + "\nWith the following details: srcX: " + srcX[currentID] + " srcY: " + srcY[currentID] + " w: " + widthAndHeight[currentID][0] + " h: " + widthAndHeight[currentID][1] + " destX: " + (levelBinaryParser.xArray[i] -= offset) + " destY: " + levelBinaryParser.yArray[i]);
			} else if (id <= srcID[0] || id > srcID[srcID.length]) {
				System.err.println("INVALID OBJECT ID AT: " + currentID + ". CHECK YOUR LEVEL FILES - moving function");
			}
			
		}
	}
	
	public void run() {
		
		Graphics g = getGraphics();
		
		g.setColor(0xabcdef);
		g.fillRect(0,0,240,400);
		flushGraphics();
		
		callParseLBP("levels/example.bin");
		
		// temporarily failcheck
		if (mainApp.data[32] == 0x00) {
			System.out.println("cannot continue");
		}
		
		printObjectOriginally();
		
		if (g == null) System.out.println("how");
		
		try { Thread.sleep(2000); } catch (Exception e) { }
		
		while (isRunning) {
			g.fillRect(0,0,240,400);
			printObjectMoving(5);
			
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
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
	
}