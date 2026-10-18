package com.j2meDash.game.tools;

import com.j2meDash.main.MainApp;

import javax.microedition.lcdui.Image;

import com.j2meDash.game.NewGameEngine;
import com.j2meDash.game.mode.*;

public class gameMechanics {
    private MainApp mainApp;
    private NewGameEngine ge;

    public static final int VERTICAL = 0x01;
    public static final int HORIZONTAL = 0x02;

    public gameMechanics(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public void changeGamemode(byte gamemode) {
        switch (gamemode) {
            case 0x01:
                if (ge.cubeGM == null) ge.cubeGM = new cube(mainApp);
                ge.inputHandler = ge.cubeGM;
                System.out.println("[INFO/changeGamemode()] Changed gamemode to [CUBE] with byte number: " + gamemode);
                break;
            case 0x02:
                if (ge.shipGM == null) ge.shipGM = new ship(mainApp);
                ge.inputHandler = ge.shipGM;
                System.out.println("[INFO/changeGamemode()] Changed gamemode to [SHIP] with byte number: " + gamemode);
                break;
            case 0x03:
                if (ge.ballGM == null) ge.ballGM = new ball(mainApp);
                ge.inputHandler = ge.ballGM;
                System.out.println("[INFO/changeGamemode()] Changed gamemode to [BALL] with byte number: " + gamemode);
                break;
            case 0x04:
                if (ge.ufoGM == null) ge.ufoGM = new ufo(mainApp);
                ge.inputHandler = ge.ufoGM;
                System.out.println("[INFO/changeGamemode()] Changed gamemode to [UFO] with byte number: " + gamemode);
                break;
            case 0x05:
                if (ge.waveGM == null) ge.waveGM = new wave(mainApp);
                ge.inputHandler = ge.waveGM;
                System.out.println("[INFO/changeGamemode()] Changed gamemode to [WAVE] with byte number: " + gamemode);
                break;
            case 0x06:
                if (ge.robotGM == null) ge.robotGM = new robot(mainApp);
                ge.inputHandler = ge.robotGM;
                System.out.println("[INFO/changeGamemode()] Changed gamemode to [ROBOT] with byte number: " + gamemode);
                break;
            case 0x07:
                if (ge.spiderGM == null) ge.spiderGM = new spider(mainApp);
                ge.inputHandler = ge.spiderGM;
                System.out.println("[INFO/changeGamemode()] Changed gamemode to [SPIDER] with byte number: " + gamemode);
                break;
            case 0x08:
                if (ge.swingGM == null) ge.swingGM = new swing(mainApp);
                ge.inputHandler = ge.swingGM;
                System.out.println("[INFO/changeGamemode()] Changed gamemode to [SWING] with byte number: " + gamemode);
                break;
            default: 
                System.out.println("[ERROR/changeGamemode()] Invalid gamemode: " + gamemode);
                break;
        }
    }

    public void startPosition(int x, int y) {
        if (x < 0) System.out.println("[ERROR/startPosition()] Argument x=" + x + " is smaller than 0");
        else {
            ge.playerX = x;
            ge.playerY = y;
            System.out.println("[INFO/startPosition()] Start position placed at x=" + x + " y=" + y);
        }
    }

    public Image rotateSprite(Image srcImg, double degrees) {
        int srcWidth = srcImg.getWidth();
        int srcHeight = srcImg.getHeight();

        int[] srcPixels = new int[srcWidth * srcHeight];
        srcImg.getRGB(srcPixels, 0, srcWidth, 0, 0, srcWidth, srcHeight);

        double radians = Math.toRadians(degrees);
        double cos = Math.cos(radians);
        double sin = Math.sin(radians);

        if (degrees == 0 || degrees == 360) {
            cos = 1.0;
            sin = 0.0;
        } else if (degrees == 90) {
            cos = 0.0;
            sin = 1.0;
        } else if (degrees == 180) {
            cos = -1.0;
            sin = 0.0;
        } else if (degrees == 270) {
            cos = 0.0;
            sin = -1.0;
        }

        int destWidth = (int) Math.ceil(Math.abs(srcWidth * cos) + Math.abs(srcHeight * sin));
        int destHeight = (int) Math.ceil(Math.abs(srcWidth * sin) + Math.abs(srcHeight * cos));

        int[] destPixels = new int[destWidth * destHeight];

        double srcCenterX = (srcWidth - 1) / 2.0;
        double srcCenterY = (srcHeight - 1) / 2.0;
        double destCenterX = (destWidth - 1) / 2.0;
        double destCenterY = (destHeight - 1) / 2.0;

        for (int yDest = 0; yDest < destHeight; yDest++) {
            double dy = yDest - destCenterY;
            int destRowOffset = yDest * destWidth;

            for (int xDest = 0; xDest < destWidth; xDest++) {
                double dx = xDest - destCenterX;

                int xSrc = (int) Math.round(dx * cos + dy * sin + srcCenterX);
                int ySrc = (int) Math.round(-dx * sin + dy * cos + srcCenterY);

                int destIndex = destRowOffset + xDest;

                if (xSrc >= 0 && xSrc < srcWidth && ySrc >= 0 && ySrc < srcHeight) {
                    int srcIndex = (ySrc * srcWidth) + xSrc;
                    destPixels[destIndex] = srcPixels[srcIndex];
                } else {
                    destPixels[destIndex] = 0x00000000; 
                }
            }
        }
        // System.out.println("[DEBUG]/[gameMechanics.rotateSprite()] destWidth=" + destWidth + " destHeight=" + destHeight);
        return Image.createRGBImage(destPixels, destWidth, destHeight, true);
    }

    public Image mirrorSprite(Image srcImg, int orientation) {
        if (orientation == HORIZONTAL) {
            int counter = 0;
            int[] srcPixels = new int[srcImg.getWidth() * srcImg.getHeight()];
            srcImg.getRGB(srcPixels, 0, srcImg.getWidth(), 0, 0, srcImg.getWidth(), srcImg.getHeight());
            int[][] _2dSrcPixels = new int[srcImg.getHeight()][srcImg.getWidth()];
            for (int i = 0; i < srcImg.getHeight(); i++) {
                for (int j = 0; j < srcImg.getWidth(); j++) {
                    _2dSrcPixels[i][j] = srcPixels[counter];
                    counter++;
                }
            }
            for (int i = 0; i < srcImg.getHeight(); i++) {
                for (int j = 0; j < srcImg.getWidth()/2; j++) {
                    int temp = _2dSrcPixels[i][j];
                    _2dSrcPixels[i][j] = _2dSrcPixels[i][_2dSrcPixels[i].length-j-1];
                    _2dSrcPixels[i][_2dSrcPixels[i].length-j-1] = temp;
                }
            }
            counter = 0;
            int[] distPixels = new int[srcImg.getWidth() * srcImg.getHeight()];
            for (int i = 0; i < srcImg.getHeight(); i++) {
                for (int j = 0; j < srcImg.getWidth(); j++) {
                    distPixels[counter] = _2dSrcPixels[i][j];
                    counter++;
                }
            }
            return Image.createRGBImage(distPixels, srcImg.getWidth(), srcImg.getHeight(), true);
        } else if (orientation == VERTICAL) {
            return null;
        } else {
            System.out.println("[ERROR/mirrorSprite()] Argument orientation=" + orientation + " is invalid");
            return null;
        }
    }
}
