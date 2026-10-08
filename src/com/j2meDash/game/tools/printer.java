package com.j2meDash.game.tools;

import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;
import com.j2meDash.pars.LevelBinaryParser;
import com.j2meDash.main.MainApp;

public class printer extends GameCanvas {
    private MainApp mainApp;
    private Image background;
    private Image foreground;
    private Image object_sheet;
    // private Image[] fonts;
    private Image[] orbs;
    private Image[] pads;
    private Image[] portals;
    private boolean drawn = false;
    private int reductionA = 0;
    private int reductionB = 0;

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
								
	// private int[][] gamemodeWidthAndHeight = {{21,21,10,10},{21,21,10,10},{16,16,8,8},{21,21,10,10},{21,21,10,10},{21,21,10,10}}; // add the mini gamemode w and h here

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
            background = Image.createImage("/rsc/img/bg/bg.png");
            foreground = Image.createImage("/rsc/img/bg/fg.png");
            
            // fonts
            /*
            fonts = new Image[] {
                Image.createImage("/rsc/font/font1.png"),
                Image.createImage("/rsc/font/font2.png"),
                Image.createImage("/rsc/font/font3.png"),
                Image.createImage("/rsc/font/font4.png")
            };
            */

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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

     public void printObject_fixed() {
		Graphics g = getGraphics();
		Image[] objects = new Image[8];
		int additionalPrintingTime = LevelBinaryParser.objectNumber % 8;
		int groupedPrintingTime = LevelBinaryParser.objectNumber - additionalPrintingTime;
		
		for (int i = 0; i < groupedPrintingTime; i += 8) {
			
			if (mainApp.data[32+(8*i)] >= srcX_objects.length && mainApp.data[32+8*i] >= srcY_objects.length) {
				System.out.println("stop checking because of developer failcheck");
				break;
			}

			int id = mainApp.data[32+(8*i)];
			int firstCurrentID = LevelBinaryParser.idArray[i];
			int secondCurrentID = LevelBinaryParser.idArray[i+1];
			int thirdCurrentID = LevelBinaryParser.idArray[i+2];
			int fourthCurrentID = LevelBinaryParser.idArray[i+3];
			int fifthCurrentID = LevelBinaryParser.idArray[i+4];
			int sixthCurrentID = LevelBinaryParser.idArray[i+5];
			int seventhCurrentID = LevelBinaryParser.idArray[i+6];
			int eighthCurrentID = LevelBinaryParser.idArray[i+7];

            for (int offset = 0; offset < 8; offset++) {
                switch (LevelBinaryParser.parArray[i+offset]) {
                    case 0x01:
                        objects[offset] = object_sheet;
                        break;
                    case 0x02:
                        objects[offset] = orbs[LevelBinaryParser.idArray[i+offset]-50];
                        break;
                    case 0x03:
                        objects[offset] = pads[LevelBinaryParser.idArray[i+offset]-50];
                        break;
                    case 0x04:
                        objects[offset] = portals[LevelBinaryParser.idArray[i+offset]-50];
                        break;
                }
            }
			
			if (id > srcID[0] && id <= srcID[srcID.length-1] && LevelBinaryParser.parArray[i] == 0x01) {
				g.drawRegion(objects[0], srcX_objects[firstCurrentID], srcY_objects[firstCurrentID], widthAndHeight[firstCurrentID][0], widthAndHeight[firstCurrentID][1], Sprite.TRANS_NONE, LevelBinaryParser.xArray[i], LevelBinaryParser.yArray[i], Graphics.RIGHT | Graphics.BOTTOM);
				g.drawRegion(objects[1], srcX_objects[secondCurrentID], srcY_objects[secondCurrentID], widthAndHeight[secondCurrentID][0], widthAndHeight[secondCurrentID][1], Sprite.TRANS_NONE, LevelBinaryParser.xArray[i+1], LevelBinaryParser.yArray[i+1], Graphics.RIGHT | Graphics.BOTTOM);
				g.drawRegion(objects[2], srcX_objects[thirdCurrentID], srcY_objects[thirdCurrentID], widthAndHeight[thirdCurrentID][0], widthAndHeight[thirdCurrentID][1], Sprite.TRANS_NONE, LevelBinaryParser.xArray[i+2], LevelBinaryParser.yArray[i+2], Graphics.RIGHT | Graphics.BOTTOM);
				g.drawRegion(objects[3], srcX_objects[fourthCurrentID], srcY_objects[fourthCurrentID], widthAndHeight[fourthCurrentID][0], widthAndHeight[fourthCurrentID][1], Sprite.TRANS_NONE, LevelBinaryParser.xArray[i+3], LevelBinaryParser.yArray[i+3], Graphics.RIGHT | Graphics.BOTTOM);
				g.drawRegion(objects[4], srcX_objects[fifthCurrentID], srcY_objects[fifthCurrentID], widthAndHeight[fifthCurrentID][0], widthAndHeight[fifthCurrentID][1], Sprite.TRANS_NONE, LevelBinaryParser.xArray[i+4], LevelBinaryParser.yArray[i+4], Graphics.RIGHT | Graphics.BOTTOM);
				g.drawRegion(objects[5], srcX_objects[sixthCurrentID], srcY_objects[sixthCurrentID], widthAndHeight[sixthCurrentID][0], widthAndHeight[sixthCurrentID][1], Sprite.TRANS_NONE, LevelBinaryParser.xArray[i+5], LevelBinaryParser.yArray[i+5], Graphics.RIGHT | Graphics.BOTTOM);
				g.drawRegion(objects[6], srcX_objects[seventhCurrentID], srcY_objects[seventhCurrentID], widthAndHeight[seventhCurrentID][0], widthAndHeight[seventhCurrentID][1], Sprite.TRANS_NONE, LevelBinaryParser.xArray[i+6], LevelBinaryParser.yArray[i+6], Graphics.RIGHT | Graphics.BOTTOM);
				g.drawRegion(objects[7], srcX_objects[eighthCurrentID], srcY_objects[eighthCurrentID], widthAndHeight[eighthCurrentID][0], widthAndHeight[eighthCurrentID][1], Sprite.TRANS_NONE, LevelBinaryParser.xArray[i+7], LevelBinaryParser.yArray[i+7], Graphics.RIGHT | Graphics.BOTTOM);
			} else if (id > srcID[0] && id <= srcID[srcID.length-1] && LevelBinaryParser.parArray[i] != 0x01) {
                g.drawImage(objects[0], LevelBinaryParser.xArray[i], LevelBinaryParser.yArray[i], Graphics.RIGHT | Graphics.BOTTOM);
                g.drawImage(objects[1], LevelBinaryParser.xArray[i+1], LevelBinaryParser.yArray[i+1], Graphics.RIGHT | Graphics.BOTTOM);
                g.drawImage(objects[2], LevelBinaryParser.xArray[i+2], LevelBinaryParser.yArray[i+2], Graphics.RIGHT | Graphics.BOTTOM);
                g.drawImage(objects[3], LevelBinaryParser.xArray[i+3], LevelBinaryParser.yArray[i+3], Graphics.RIGHT | Graphics.BOTTOM);
                g.drawImage(objects[4], LevelBinaryParser.xArray[i+4], LevelBinaryParser.yArray[i+4], Graphics.RIGHT | Graphics.BOTTOM);
                g.drawImage(objects[5], LevelBinaryParser.xArray[i+5], LevelBinaryParser.yArray[i+5], Graphics.RIGHT | Graphics.BOTTOM);
                g.drawImage(objects[6], LevelBinaryParser.xArray[i+6], LevelBinaryParser.yArray[i+6], Graphics.RIGHT | Graphics.BOTTOM);
                g.drawImage(objects[7], LevelBinaryParser.xArray[i+7], LevelBinaryParser.yArray[i+7], Graphics.RIGHT | Graphics.BOTTOM);
            }
			
		}
			
		switch (additionalPrintingTime % 4) {
			case 0:
				for (int i = groupedPrintingTime; i < LevelBinaryParser.objectNumber; i += 4) {
					
					int id = mainApp.data[32+(8*i)];
					int firstCurrentID = LevelBinaryParser.idArray[i];
					int secondCurrentID = LevelBinaryParser.idArray[i+1];
					int thirdCurrentID = LevelBinaryParser.idArray[i+2];
					int fourthCurrentID = LevelBinaryParser.idArray[i+3];

                    for (int offset = 0; offset < 4; offset++) {
                        switch (LevelBinaryParser.parArray[i+offset]) {
                            case 0x01:
                                objects[offset] = object_sheet;
                                break;
                            case 0x02:
                                objects[offset] = orbs[LevelBinaryParser.idArray[i+offset]-50];
                                break;
                            case 0x03:
                                objects[offset] = pads[LevelBinaryParser.idArray[i+offset]-50];
                                break;
                            case 0x04:
                                objects[offset] = portals[LevelBinaryParser.idArray[i+offset]-50];
                                break;
                        }
                    }
					
					if (id > srcID[0] && id <= srcID[srcID.length-1] && LevelBinaryParser.parArray[i] == 0x01) {
                        g.drawRegion(objects[0], srcX_objects[firstCurrentID], srcY_objects[firstCurrentID], widthAndHeight[firstCurrentID][0], widthAndHeight[firstCurrentID][1], Sprite.TRANS_NONE, LevelBinaryParser.xArray[i], LevelBinaryParser.yArray[i], Graphics.RIGHT | Graphics.BOTTOM);
                        g.drawRegion(objects[1], srcX_objects[secondCurrentID], srcY_objects[secondCurrentID], widthAndHeight[secondCurrentID][0], widthAndHeight[secondCurrentID][1], Sprite.TRANS_NONE, LevelBinaryParser.xArray[i+1], LevelBinaryParser.yArray[i+1], Graphics.RIGHT | Graphics.BOTTOM);
                        g.drawRegion(objects[2], srcX_objects[thirdCurrentID], srcY_objects[thirdCurrentID], widthAndHeight[thirdCurrentID][0], widthAndHeight[thirdCurrentID][1], Sprite.TRANS_NONE, LevelBinaryParser.xArray[i+2], LevelBinaryParser.yArray[i+2], Graphics.RIGHT | Graphics.BOTTOM);
                        g.drawRegion(objects[3], srcX_objects[fourthCurrentID], srcY_objects[fourthCurrentID], widthAndHeight[fourthCurrentID][0], widthAndHeight[fourthCurrentID][1], Sprite.TRANS_NONE, LevelBinaryParser.xArray[i+3], LevelBinaryParser.yArray[i+3], Graphics.RIGHT | Graphics.BOTTOM);
                    } else if (id > srcID[0] && id <= srcID[srcID.length-1] && LevelBinaryParser.parArray[i] != 0x01) {
                        g.drawImage(objects[0], LevelBinaryParser.xArray[i], LevelBinaryParser.yArray[i], Graphics.RIGHT | Graphics.BOTTOM);
                        g.drawImage(objects[1], LevelBinaryParser.xArray[i+1], LevelBinaryParser.yArray[i+1], Graphics.RIGHT | Graphics.BOTTOM);
                        g.drawImage(objects[2], LevelBinaryParser.xArray[i+2], LevelBinaryParser.yArray[i+2], Graphics.RIGHT | Graphics.BOTTOM);
                        g.drawImage(objects[3], LevelBinaryParser.xArray[i+3], LevelBinaryParser.yArray[i+3], Graphics.RIGHT | Graphics.BOTTOM);
                    }
				}
				break;
			case 2:
				for (int i = groupedPrintingTime; i < LevelBinaryParser.objectNumber; i += 2) {
					
					int id = mainApp.data[32+(8*i)];
					int firstCurrentID = LevelBinaryParser.idArray[i];
					int secondCurrentID = LevelBinaryParser.idArray[i+1];

                    for (int offset = 0; offset < 2; offset++) {
                        switch (LevelBinaryParser.parArray[i+offset]) {
                            case 0x01:
                                objects[offset] = object_sheet;
                                break;
                            case 0x02:
                                objects[offset] = orbs[LevelBinaryParser.idArray[i+offset]-50];
                                break;
                            case 0x03:
                                objects[offset] = pads[LevelBinaryParser.idArray[i+offset]-50];
                                break;
                            case 0x04:
                                objects[offset] = portals[LevelBinaryParser.idArray[i+offset]-50];
                                break;
                        }
                    }
					
					if (id > srcID[0] && id <= srcID[srcID.length-1] && LevelBinaryParser.parArray[i] == 0x01) {
                        g.drawRegion(objects[0], srcX_objects[firstCurrentID], srcY_objects[firstCurrentID], widthAndHeight[firstCurrentID][0], widthAndHeight[firstCurrentID][1], Sprite.TRANS_NONE, LevelBinaryParser.xArray[i], LevelBinaryParser.yArray[i], Graphics.RIGHT | Graphics.BOTTOM);
                        g.drawRegion(objects[1], srcX_objects[secondCurrentID], srcY_objects[secondCurrentID], widthAndHeight[secondCurrentID][0], widthAndHeight[secondCurrentID][1], Sprite.TRANS_NONE, LevelBinaryParser.xArray[i+1], LevelBinaryParser.yArray[i+1], Graphics.RIGHT | Graphics.BOTTOM);
                    } else if (id > srcID[0] && id <= srcID[srcID.length-1] && LevelBinaryParser.parArray[i] != 0x01) {
                        g.drawImage(objects[0], LevelBinaryParser.xArray[i], LevelBinaryParser.yArray[i], Graphics.RIGHT | Graphics.BOTTOM);
                        g.drawImage(objects[1], LevelBinaryParser.xArray[i+1], LevelBinaryParser.yArray[i+1], Graphics.RIGHT | Graphics.BOTTOM);
                    }
				}
				break;
			case 1:
			case 3:
				for (int i = groupedPrintingTime; i < LevelBinaryParser.objectNumber; i += 1) {
					
					int id = mainApp.data[32+(8*i)];
					int firstCurrentID = LevelBinaryParser.idArray[i];
					
                    for (int offset = 0; offset < additionalPrintingTime; offset++) {
                        switch (LevelBinaryParser.parArray[i+offset]) {
                            case 0x01:
                                objects[offset] = object_sheet;
                                break;
                            case 0x02:
                                objects[offset] = orbs[LevelBinaryParser.idArray[i+offset]-50];
                                break;
                            case 0x03:
                                objects[offset] = pads[LevelBinaryParser.idArray[i+offset]-50];
                                break;
                            case 0x04:
                                objects[offset] = portals[LevelBinaryParser.idArray[i+offset]-50];
                                break;
                        }
                    }

					if (id > srcID[0] && id <= srcID[srcID.length-1] && LevelBinaryParser.parArray[i] == 0x01) {
                        g.drawRegion(objects[0], srcX_objects[firstCurrentID], srcY_objects[firstCurrentID], widthAndHeight[firstCurrentID][0], widthAndHeight[firstCurrentID][1], Sprite.TRANS_NONE, LevelBinaryParser.xArray[i], LevelBinaryParser.yArray[i], Graphics.RIGHT | Graphics.BOTTOM);
                    } else if (id > srcID[0] && id <= srcID[srcID.length-1] && LevelBinaryParser.parArray[i] != 0x01) {
                        g.drawImage(objects[0], LevelBinaryParser.xArray[i], LevelBinaryParser.yArray[i], Graphics.RIGHT | Graphics.BOTTOM);
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
		int additionalPrintingTime = LevelBinaryParser.objectNumber % 8;
		int groupedPrintingTime = LevelBinaryParser.objectNumber - additionalPrintingTime;
		
		for (int i = 0; i < groupedPrintingTime; i += 8) {
			
			if (mainApp.data[32+(8*i)] >= srcX_objects.length && mainApp.data[32+8*i] >= srcY_objects.length) {
				System.out.println("stop checking because of developer failcheck");
				break;
			}

			int id = mainApp.data[32+(8*i)];
			int firstCurrentID = LevelBinaryParser.idArray[i];
			int secondCurrentID = LevelBinaryParser.idArray[i+1];
			int thirdCurrentID = LevelBinaryParser.idArray[i+2];
			int fourthCurrentID = LevelBinaryParser.idArray[i+3];
			int fifthCurrentID = LevelBinaryParser.idArray[i+4];
			int sixthCurrentID = LevelBinaryParser.idArray[i+5];
			int seventhCurrentID = LevelBinaryParser.idArray[i+6];
			int eighthCurrentID = LevelBinaryParser.idArray[i+7];

            for (int n = 0; n < 8; n++) {
                switch (LevelBinaryParser.parArray[i+n]) {
                    case 0x01:
                        objects[n] = object_sheet;
                        break;
                    case 0x02:
                        objects[n] = orbs[LevelBinaryParser.idArray[i+n]-50];
                        break;
                    case 0x03:
                        objects[n] = pads[LevelBinaryParser.idArray[i+n]-50];
                        break;
                    case 0x04:
                        objects[n] = portals[LevelBinaryParser.idArray[i+n]-50];
                        break;
                }
            }
			
			if (id > srcID[0] && id <= srcID[srcID.length-1] && LevelBinaryParser.parArray[i] == 0x01) {
				g.drawRegion(objects[0], srcX_objects[firstCurrentID], srcY_objects[firstCurrentID], widthAndHeight[firstCurrentID][0], widthAndHeight[firstCurrentID][1], Sprite.TRANS_NONE, LevelBinaryParser.xArray[i] - offset, LevelBinaryParser.yArray[i], Graphics.RIGHT | Graphics.BOTTOM);
				g.drawRegion(objects[1], srcX_objects[secondCurrentID], srcY_objects[secondCurrentID], widthAndHeight[secondCurrentID][0], widthAndHeight[secondCurrentID][1], Sprite.TRANS_NONE, LevelBinaryParser.xArray[i+1] - offset, LevelBinaryParser.yArray[i+1], Graphics.RIGHT | Graphics.BOTTOM);
				g.drawRegion(objects[2], srcX_objects[thirdCurrentID], srcY_objects[thirdCurrentID], widthAndHeight[thirdCurrentID][0], widthAndHeight[thirdCurrentID][1], Sprite.TRANS_NONE, LevelBinaryParser.xArray[i+2] - offset, LevelBinaryParser.yArray[i+2], Graphics.RIGHT | Graphics.BOTTOM);
				g.drawRegion(objects[3], srcX_objects[fourthCurrentID], srcY_objects[fourthCurrentID], widthAndHeight[fourthCurrentID][0], widthAndHeight[fourthCurrentID][1], Sprite.TRANS_NONE, LevelBinaryParser.xArray[i+3] - offset, LevelBinaryParser.yArray[i+3], Graphics.RIGHT | Graphics.BOTTOM);
				g.drawRegion(objects[4], srcX_objects[fifthCurrentID], srcY_objects[fifthCurrentID], widthAndHeight[fifthCurrentID][0], widthAndHeight[fifthCurrentID][1], Sprite.TRANS_NONE, LevelBinaryParser.xArray[i+4] - offset, LevelBinaryParser.yArray[i+4], Graphics.RIGHT | Graphics.BOTTOM);
				g.drawRegion(objects[5], srcX_objects[sixthCurrentID], srcY_objects[sixthCurrentID], widthAndHeight[sixthCurrentID][0], widthAndHeight[sixthCurrentID][1], Sprite.TRANS_NONE, LevelBinaryParser.xArray[i+5] - offset, LevelBinaryParser.yArray[i+5], Graphics.RIGHT | Graphics.BOTTOM);
				g.drawRegion(objects[6], srcX_objects[seventhCurrentID], srcY_objects[seventhCurrentID], widthAndHeight[seventhCurrentID][0], widthAndHeight[seventhCurrentID][1], Sprite.TRANS_NONE, LevelBinaryParser.xArray[i+6] - offset, LevelBinaryParser.yArray[i+6], Graphics.RIGHT | Graphics.BOTTOM);
				g.drawRegion(objects[7], srcX_objects[eighthCurrentID], srcY_objects[eighthCurrentID], widthAndHeight[eighthCurrentID][0], widthAndHeight[eighthCurrentID][1], Sprite.TRANS_NONE, LevelBinaryParser.xArray[i+7] - offset, LevelBinaryParser.yArray[i+7], Graphics.RIGHT | Graphics.BOTTOM);
			} else if (id > srcID[0] && id <= srcID[srcID.length-1] && LevelBinaryParser.parArray[i] != 0x01) {
                g.drawImage(objects[0], LevelBinaryParser.xArray[i] - offset, LevelBinaryParser.yArray[i], Graphics.RIGHT | Graphics.BOTTOM);
                g.drawImage(objects[1], LevelBinaryParser.xArray[i+1] - offset, LevelBinaryParser.yArray[i+1], Graphics.RIGHT | Graphics.BOTTOM);
                g.drawImage(objects[2], LevelBinaryParser.xArray[i+2] - offset, LevelBinaryParser.yArray[i+2], Graphics.RIGHT | Graphics.BOTTOM);
                g.drawImage(objects[3], LevelBinaryParser.xArray[i+3] - offset, LevelBinaryParser.yArray[i+3], Graphics.RIGHT | Graphics.BOTTOM);
                g.drawImage(objects[4], LevelBinaryParser.xArray[i+4] - offset, LevelBinaryParser.yArray[i+4], Graphics.RIGHT | Graphics.BOTTOM);
                g.drawImage(objects[5], LevelBinaryParser.xArray[i+5] - offset, LevelBinaryParser.yArray[i+5], Graphics.RIGHT | Graphics.BOTTOM);
                g.drawImage(objects[6], LevelBinaryParser.xArray[i+6] - offset, LevelBinaryParser.yArray[i+6], Graphics.RIGHT | Graphics.BOTTOM);
                g.drawImage(objects[7], LevelBinaryParser.xArray[i+7] - offset, LevelBinaryParser.yArray[i+7], Graphics.RIGHT | Graphics.BOTTOM);
            }
			
		}
			
		switch (additionalPrintingTime % 4) {
			case 0:
				for (int i = groupedPrintingTime; i < LevelBinaryParser.objectNumber; i += 4) {
					
					int id = mainApp.data[32+(8*i)];
					int firstCurrentID = LevelBinaryParser.idArray[i];
					int secondCurrentID = LevelBinaryParser.idArray[i+1];
					int thirdCurrentID = LevelBinaryParser.idArray[i+2];
					int fourthCurrentID = LevelBinaryParser.idArray[i+3];

                    for (int n = 0; n < 4; n++) {
                        switch (LevelBinaryParser.parArray[i+n]) {
                            case 0x01:
                                objects[n] = object_sheet;
                                break;
                            case 0x02:
                                objects[n] = orbs[LevelBinaryParser.idArray[i+n]-50];
                                break;
                            case 0x03:
                                objects[n] = pads[LevelBinaryParser.idArray[i+n]-50];
                                break;
                            case 0x04:
                                objects[n] = portals[LevelBinaryParser.idArray[i+n]-50];
                                break;
                        }
                    }
					
					if (id > srcID[0] && id <= srcID[srcID.length-1] && LevelBinaryParser.parArray[i] == 0x01) {
                        g.drawRegion(objects[0], srcX_objects[firstCurrentID], srcY_objects[firstCurrentID], widthAndHeight[firstCurrentID][0], widthAndHeight[firstCurrentID][1], Sprite.TRANS_NONE, LevelBinaryParser.xArray[i] - offset, LevelBinaryParser.yArray[i], Graphics.RIGHT | Graphics.BOTTOM);
                        g.drawRegion(objects[1], srcX_objects[secondCurrentID], srcY_objects[secondCurrentID], widthAndHeight[secondCurrentID][0], widthAndHeight[secondCurrentID][1], Sprite.TRANS_NONE, LevelBinaryParser.xArray[i+1] - offset, LevelBinaryParser.yArray[i+1], Graphics.RIGHT | Graphics.BOTTOM);
                        g.drawRegion(objects[2], srcX_objects[thirdCurrentID], srcY_objects[thirdCurrentID], widthAndHeight[thirdCurrentID][0], widthAndHeight[thirdCurrentID][1], Sprite.TRANS_NONE, LevelBinaryParser.xArray[i+2] - offset, LevelBinaryParser.yArray[i+2], Graphics.RIGHT | Graphics.BOTTOM);
                        g.drawRegion(objects[3], srcX_objects[fourthCurrentID], srcY_objects[fourthCurrentID], widthAndHeight[fourthCurrentID][0], widthAndHeight[fourthCurrentID][1], Sprite.TRANS_NONE, LevelBinaryParser.xArray[i+3] - offset, LevelBinaryParser.yArray[i+3], Graphics.RIGHT | Graphics.BOTTOM);
                    } else if (id > srcID[0] && id <= srcID[srcID.length-1] && LevelBinaryParser.parArray[i] != 0x01) {
                        g.drawImage(objects[0], LevelBinaryParser.xArray[i] - offset, LevelBinaryParser.yArray[i], Graphics.RIGHT | Graphics.BOTTOM);
                        g.drawImage(objects[1], LevelBinaryParser.xArray[i+1] - offset, LevelBinaryParser.yArray[i+1], Graphics.RIGHT | Graphics.BOTTOM);
                        g.drawImage(objects[2], LevelBinaryParser.xArray[i+2] - offset, LevelBinaryParser.yArray[i+2], Graphics.RIGHT | Graphics.BOTTOM);
                        g.drawImage(objects[3], LevelBinaryParser.xArray[i+3] - offset, LevelBinaryParser.yArray[i+3], Graphics.RIGHT | Graphics.BOTTOM);
                    }
				}
				break;
			case 2:
				for (int i = groupedPrintingTime; i < LevelBinaryParser.objectNumber; i += 2) {
					
					int id = mainApp.data[32+(8*i)];
					int firstCurrentID = LevelBinaryParser.idArray[i];
					int secondCurrentID = LevelBinaryParser.idArray[i+1];

                    for (int n = 0; n < 2; n++) {
                        switch (LevelBinaryParser.parArray[i+n]) {
                            case 0x01:
                                objects[n] = object_sheet;
                                break;
                            case 0x02:
                                objects[n] = orbs[LevelBinaryParser.idArray[i+n]-50];
                                break;
                            case 0x03:
                                objects[n] = pads[LevelBinaryParser.idArray[i+n]-50];
                                break;
                            case 0x04:
                                objects[n] = portals[LevelBinaryParser.idArray[i+n]-50];
                                break;
                        }
                    }
					
					if (id > srcID[0] && id <= srcID[srcID.length-1] && LevelBinaryParser.parArray[i] == 0x01) {
                        g.drawRegion(objects[0], srcX_objects[firstCurrentID], srcY_objects[firstCurrentID], widthAndHeight[firstCurrentID][0], widthAndHeight[firstCurrentID][1], Sprite.TRANS_NONE, LevelBinaryParser.xArray[i] - offset, LevelBinaryParser.yArray[i], Graphics.RIGHT | Graphics.BOTTOM);
                        g.drawRegion(objects[1], srcX_objects[secondCurrentID], srcY_objects[secondCurrentID], widthAndHeight[secondCurrentID][0], widthAndHeight[secondCurrentID][1], Sprite.TRANS_NONE, LevelBinaryParser.xArray[i+1] - offset, LevelBinaryParser.yArray[i+1], Graphics.RIGHT | Graphics.BOTTOM);
                    } else if (id > srcID[0] && id <= srcID[srcID.length-1] && LevelBinaryParser.parArray[i] != 0x01) {
                        g.drawImage(objects[0], LevelBinaryParser.xArray[i] - offset, LevelBinaryParser.yArray[i], Graphics.RIGHT | Graphics.BOTTOM);
                        g.drawImage(objects[1], LevelBinaryParser.xArray[i+1] - offset, LevelBinaryParser.yArray[i+1], Graphics.RIGHT | Graphics.BOTTOM);
                    }
				}
				break;
			case 1:
			case 3:
				for (int i = groupedPrintingTime; i < LevelBinaryParser.objectNumber; i += 1) {
					
					int id = mainApp.data[32+(8*i)];
					int firstCurrentID = LevelBinaryParser.idArray[i];
					
                    for (int n = 0; n < additionalPrintingTime; n++) {
                        switch (LevelBinaryParser.parArray[i+n]) {
                            case 0x01:
                                objects[n] = object_sheet;
                                break;
                            case 0x02:
                                objects[n] = orbs[LevelBinaryParser.idArray[i+n]-50];
                                break;
                            case 0x03:
                                objects[n] = pads[LevelBinaryParser.idArray[i+n]-50];
                                break;
                            case 0x04:
                                objects[n] = portals[LevelBinaryParser.idArray[i+n]-50];
                                break;
                        }
                    }

					if (id > srcID[0] && id <= srcID[srcID.length-1] && LevelBinaryParser.parArray[i] == 0x01) {
                        g.drawRegion(objects[0], srcX_objects[firstCurrentID], srcY_objects[firstCurrentID], widthAndHeight[firstCurrentID][0], widthAndHeight[firstCurrentID][1], Sprite.TRANS_NONE, LevelBinaryParser.xArray[i] - offset, LevelBinaryParser.yArray[i], Graphics.RIGHT | Graphics.BOTTOM);
                    } else if (id > srcID[0] && id <= srcID[srcID.length-1] && LevelBinaryParser.parArray[i] != 0x01) {
                        g.drawImage(objects[0], LevelBinaryParser.xArray[i] - offset, LevelBinaryParser.yArray[i], Graphics.RIGHT | Graphics.BOTTOM);
                    }
				}
				break;
			default:
				break;
		}

	}

    public void drawBackground() {
        Graphics g = getGraphics();
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

    public void drawingCharString(byte fontstrip, String s, int x, int y, int[][] metrics) {
    }
}