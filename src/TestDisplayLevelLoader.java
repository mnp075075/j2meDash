import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;

public class TestDisplayLevelLoader extends GameCanvas {
	
	private j2meDash mainApp;
	private LevelBinaryParser levelBinaryParser;
	
	Image sheet;
	
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
	
	public int[] srcX = {};
	public int[] srcY = {};
	
	public TestDisplayLevelLoader(j2meDash mainApp) {
		
		super(true);
		this.mainApp = mainApp;
		
		try {
			sheet = Image.createImage("assets/sheet.png");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void callParseLBP(String name) {
		
		if (levelBinaryParser == null) {
			levelBinaryParser = new LevelBinaryParser(mainApp);
		}
		
		levelBinaryParser.parseByte(name);
		
		// use the example file for testing
	}
	
	public void printObject() {
		// dont use this method yet
		// i haven't finished the srcX[] and srcY[] at the moment
		Graphics g = getGraphics();
		
		for (int i = 0; i < levelBinaryParser.objectNumber; i++) {
			
			int id = levelBinaryParser.data[32+(8*i)];
			int currentID = levelBinaryParser.idArray[i]
			
			if (id > srcID[0] && id <= srcID[srcID.length-1]) {
				g.drawRegion(sheet, srcX[currentID], srcY[currentID], widthAndHeight[currentID][0], widthAndHeight[currentID][1], Sprite.TRANS_NONE, levelBinaryParser.xArray[i], levelBinaryParser.yArray[i], Graphics.RIGHT | Graphics.BOTTOM);
				System.out.println("PRINTED OBJECT " + i + " WITH ID: " + current ID);
			} else if (id <= srcID[0] || id > srcID[srcID.length]) {
				System.err.println("INVALID OBJECT ID AT: " + currentID + ".CHECK YOUR LEVEL FILES");
			}
			
		}
	}
	
	public void paint(Graphics g) {
		
		g.setColor(0xabcdef);
		g.fillRect(0,0,240,400);
		
		// test
		
		/* for (int i = 0; i < levelBinaryParser.objectNumber; i++) {
			g.drawRegion(sheet, 93, 207, 21, 21, Sprite.TRANS_NONE, levelBinaryParser.xArray[i], levelBinaryParser.yArray[i], Graphics.LEFT | Graphics.TOP);
		} */
		
	}
	
}