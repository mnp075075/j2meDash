package com.j2meDash.game.tools;

import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;
import com.j2meDash.pars.LevelParser;
import com.j2meDash.main.MainApp;

public class printer extends GameCanvas {
    private MainApp mainApp;
    private Image background;
    private Image foreground;
    private Image object_sheet;
    private Image[] fonts;
    private Image[] orbs;
    private Image[] pads;
    private Image[] portals;
    private boolean drawn = false;
    private int reductionA = 0;
    private int reductionB = 0;

    private int[][] objWidthAndHeight = { 	{0,0,0,0}, {21,21,21,21}, {21,21,21,21}, {21,21,21,21}, // 0,1,2,3
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

    // private int[][] placeholder1;
    // private int[][] placeholder2;
    private int[] srcID = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40};
	private int[] srcX_objects = {0,93}; // non-gameplay elements
	private int[] srcY_objects = {0,207}; // non-gameplay elements

    public printer(MainApp mainApp) {
        super(true);
        this.mainApp = mainApp;
        try {
            // sheet
            object_sheet = Image.createImage("/rsc/img/sheets/object.png");

            // background and foreground
            background = Image.createImage("/rsc/img/bg/bg.gif");
            foreground = Image.createImage("/rsc/img/bg/fg.gif");
            
            // fonts
            fonts = new Image[] {
                Image.createImage("/rsc/font/font1.png"),
                Image.createImage("/rsc/font/font2.png"),
                Image.createImage("/rsc/font/font3.png"),
                Image.createImage("/rsc/font/font4.png")
            };

            // orbs
            orbs = new Image[] {
                Image.createImage("/rsc/img/orbs/yellow_orb.gif"),
                Image.createImage("/rsc/img/orbs/pink_orb.gif"),
                Image.createImage("/rsc/img/orbs/red_orb.gif"),
                Image.createImage("/rsc/img/orbs/blue_orb.gif"),
                Image.createImage("/rsc/img/orbs/green_orb.gif"),
                Image.createImage("/rsc/img/orbs/black_orb.gif"),
                Image.createImage("/rsc/img/orbs/green_dash_orb.gif"),
                Image.createImage("/rsc/img/orbs/pink_dash_orb.gif"),
                Image.createImage("/rsc/img/orbs/up_spider_orb.gif"),
                Image.createImage("/rsc/img/orbs/down_spider_orb.gif")
            };

            // pads
            pads = new Image[] {
                Image.createImage("/rsc/img/pads/yellow_pad.gif"),
                Image.createImage("/rsc/img/pads/red_pad.gif"),
                Image.createImage("/rsc/img/pads/pink_pad.gif"),
                Image.createImage("/rsc/img/pads/blue_pad.gif"),
                Image.createImage("/rsc/img/pads/spider_pad.gif")
            };

            // portals
            portals = new Image[] {
                Image.createImage("/rsc/img/portals/cube_portal.gif"),
                Image.createImage("/rsc/img/portals/ship_portal.gif"),
                Image.createImage("/rsc/img/portals/ball_portal.gif"),
                Image.createImage("/rsc/img/portals/ufo_portal.gif"),
                Image.createImage("/rsc/img/portals/wave_portal.gif"),
                Image.createImage("/rsc/img/portals/robot_portal.gif"),
                Image.createImage("/rsc/img/portals/spider_portal.gif"),
                Image.createImage("/rsc/img/portals/swing_portal.gif"),
                Image.createImage("/rsc/img/portals/normalSize_portal.gif"),
                Image.createImage("/rsc/img/portals/miniSize_portal.gif"),
                Image.createImage("/rsc/img/portals/blue_portal.gif"),
                Image.createImage("/rsc/img/portals/yellow_portal.gif"),
                Image.createImage("/rsc/img/portals/green_portal.gif"),
                Image.createImage("/rsc/img/portals/speed_portal-.gif"),
                Image.createImage("/rsc/img/portals/speed_portal+.gif"),
                Image.createImage("/rsc/img/portals/speed_portal++.gif"),
                Image.createImage("/rsc/img/portals/speed_portal+++.gif"),
                Image.createImage("/rsc/img/portals/speed_portal++++.gif")
            };
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void printObject_fixed() {
		Graphics g = getGraphics();

        for (int i = 0; i < LevelParser.objectNumber; i++) {
            int id = MainApp.idArray[i];
            if (id >= 0 && id <= 40) {
                switch (MainApp.parArray[i] & 0x03) {
                    case 0x00: {
                        g.drawRegion(object_sheet, srcX_objects[id], srcY_objects[id], objWidthAndHeight[id][0], objWidthAndHeight[id][1], Sprite.TRANS_NONE, MainApp.xArray[i], MainApp.yArray[i], Graphics.BOTTOM | Graphics.RIGHT);
                        break;
                    }
                    case 0x01: {
                        g.drawImage(orbs[MainApp.idArray[i]-20], MainApp.xArray[i], MainApp.yArray[i], Graphics.BOTTOM | Graphics.RIGHT);
                        break;
                    }
                    case 0x02: {
                        g.drawImage(pads[MainApp.idArray[i]-30], MainApp.xArray[i], MainApp.yArray[i], Graphics.BOTTOM | Graphics.RIGHT);
                        break;
                    }
                    case 0x03: {
                        g.drawImage(portals[MainApp.idArray[i]-35], MainApp.xArray[i], MainApp.yArray[i], Graphics.BOTTOM | Graphics.RIGHT);
                        break;
                    }
                }
            }
        }
        flushGraphics();
	}
	
	public void printObject_moving(int offset) {
		Graphics g = getGraphics();

        for (int i = 0; i < LevelParser.objectNumber; i++) {
            int id = MainApp.idArray[i];
            int drawX = MainApp.xArray[i];
            if (id >= 0 && id <= 40) {
                switch (MainApp.parArray[i] & 0x03) {
                    case 0x00: {
                        g.drawRegion(object_sheet, srcX_objects[id], srcY_objects[id], objWidthAndHeight[id][0], objWidthAndHeight[id][1], Sprite.TRANS_NONE, drawX, MainApp.yArray[i], Graphics.BOTTOM | Graphics.RIGHT);
                        break;
                    }
                    case 0x01: {
                        g.drawImage(orbs[MainApp.idArray[i]-20], drawX, MainApp.yArray[i], Graphics.BOTTOM | Graphics.RIGHT);
                        break;
                    }
                    case 0x02: {
                        g.drawImage(pads[MainApp.idArray[i]-30], drawX, MainApp.yArray[i], Graphics.BOTTOM | Graphics.RIGHT);
                        break;
                    }
                    case 0x03: {
                        g.drawImage(portals[MainApp.idArray[i]-35], drawX, MainApp.yArray[i], Graphics.BOTTOM | Graphics.RIGHT);
                        break;
                    }
                }
                if (drawX < -1000) MainApp.xArray[i] = -100;
            }
        }
        flushGraphics();
	}

    public void drawBackground() {
        Graphics g = getGraphics();
        int h = getHeight();
        g.drawImage(background, 0, 0, Graphics.TOP | Graphics.LEFT);
        g.drawImage(foreground, 0, (h*9)/10, Graphics.TOP | Graphics.LEFT);
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

    public void drawingCharString(byte fontstrip, String s, int x, int y, int[][] metrics) {
    }
}