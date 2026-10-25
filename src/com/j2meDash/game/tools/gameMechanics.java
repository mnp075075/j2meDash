package com.j2meDash.game.tools;

import com.j2meDash.main.MainApp;
import com.j2meDash.menu.GameOverScreen;
import com.j2meDash.pars.LevelParser;
import com.oracle.util.logging.Level;

import javax.microedition.lcdui.Image;

import com.j2meDash.game.NewGameEngine;
import com.j2meDash.game.mode.*;

public class gameMechanics {
    private MainApp mainApp;
    private NewGameEngine ge;
    private LevelParser lbp = new LevelParser();
    private GameOverScreen gameOver = new GameOverScreen(mainApp);

    public static final int VERTICAL = 0x01;
    public static final int HORIZONTAL = 0x02;

    public gameMechanics(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public void changeGamemode(byte gamemode) {
        switch (gamemode) {
            case 0x01:
                if (NewGameEngine.cubeGM == null) NewGameEngine.cubeGM = new cube(mainApp);
                NewGameEngine.inputHandler = NewGameEngine.cubeGM;
                System.out.println("[INFO/changeGamemode()] Changed gamemode to [CUBE] with byte number: " + gamemode);
                break;
            case 0x02:
                if (NewGameEngine.shipGM == null) NewGameEngine.shipGM = new ship(mainApp);
                NewGameEngine.inputHandler = NewGameEngine.shipGM;
                System.out.println("[INFO/changeGamemode()] Changed gamemode to [SHIP] with byte number: " + gamemode);
                break;
            case 0x03:
                if (NewGameEngine.ballGM == null) NewGameEngine.ballGM = new ball(mainApp);
                NewGameEngine.inputHandler = NewGameEngine.ballGM;
                System.out.println("[INFO/changeGamemode()] Changed gamemode to [BALL] with byte number: " + gamemode);
                break;
            case 0x04:
                if (NewGameEngine.ufoGM == null) NewGameEngine.ufoGM = new ufo(mainApp);
                NewGameEngine.inputHandler = NewGameEngine.ufoGM;
                System.out.println("[INFO/changeGamemode()] Changed gamemode to [UFO] with byte number: " + gamemode);
                break;
            case 0x05:
                if (NewGameEngine.waveGM == null) NewGameEngine.waveGM = new wave(mainApp);
                NewGameEngine.inputHandler = NewGameEngine.waveGM;
                System.out.println("[INFO/changeGamemode()] Changed gamemode to [WAVE] with byte number: " + gamemode);
                break;
            case 0x06:
                if (NewGameEngine.robotGM == null) NewGameEngine.robotGM = new robot(mainApp);
                NewGameEngine.inputHandler = NewGameEngine.robotGM;
                System.out.println("[INFO/changeGamemode()] Changed gamemode to [ROBOT] with byte number: " + gamemode);
                break;
            case 0x07:
                if (NewGameEngine.spiderGM == null) NewGameEngine.spiderGM = new spider(mainApp);
                NewGameEngine.inputHandler = NewGameEngine.spiderGM;
                System.out.println("[INFO/changeGamemode()] Changed gamemode to [SPIDER] with byte number: " + gamemode);
                break;
            case 0x08:
                if (NewGameEngine.swingGM == null) NewGameEngine.swingGM = new swing(mainApp);
                NewGameEngine.inputHandler = NewGameEngine.swingGM;
                System.out.println("[INFO/changeGamemode()] Changed gamemode to [SWING] with byte number: " + gamemode);
                break;
            default: 
                System.out.println("[ERROR/changeGamemode()] Invalid gamemode: " + gamemode);
                break;
        }
    }

    public void updateGamemode(byte gamemode) {
        switch (gamemode) {
            case 0x01:
                
                break;
            case 0x02:
                
                break;
            case 0x03:
                
                break;
            case 0x04:
                
                break;
            case 0x05:
                
                break;
            case 0x06:
                
                break;
            case 0x07:
                
                break;
            case 0x08:
                
                break;
            default: 
                System.out.println("[ERROR/updateGamemode()] Invalid gamemode: " + gamemode);
                break;
        }
    }

    public void startPosition(int x, int y) {
        if (x < 0) System.out.println("[ERROR/startPosition()] Argument x=" + x + " is smaller than 0");
        else {
            NewGameEngine.playerX = x;
            NewGameEngine.playerY = y;
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

    public void checkCollision(int objectX, int objectY, int objectW, int objectH, int objectID) {
        int pHazardLeft = NewGameEngine.playerX-NewGameEngine.playerWHazard;
        int pHazardRight = NewGameEngine.playerX;
        int pHazardTop = NewGameEngine.playerY-NewGameEngine.playerHHazard;
        int pHazardBottom = NewGameEngine.playerY;

        int pBodyLeft = NewGameEngine.playerX-(NewGameEngine.playerWHazard+NewGameEngine.playerWBody)/2;
        int pBodyRight = NewGameEngine.playerX-(NewGameEngine.playerWHazard-NewGameEngine.playerWBody)/2;
        int pBodyTop = NewGameEngine.playerY-(NewGameEngine.playerHHazard+NewGameEngine.playerHBody)/2;
        int pBodyBottom = NewGameEngine.playerY-(NewGameEngine.playerHHazard-NewGameEngine.playerHBody)/2;

        int objectLeft = objectX-objectW;
        int objectRight = objectX;
        int objectTop = objectY-objectH;
        int objectBottom = objectY;

        boolean valueBody = pBodyLeft <= objectRight && pBodyRight >= objectLeft && pBodyTop <= objectBottom && pBodyBottom >= objectTop;
        boolean valueHazard = pHazardLeft <= objectRight && pHazardRight >= objectLeft && pHazardTop <= objectBottom && pHazardBottom >= objectTop;

        if (objectID == 0x02) {
            if (valueHazard) {
                System.out.println("[INFO/player] Player has died (Hazard hitbox)");
                mainApp.show(gameOver);
            }
        } else if (objectID == 0x01) {
            if (valueBody) {
                System.out.println("[INFO/player] Player has died (Body hitbox)");
                if (gameOver == null) gameOver = new GameOverScreen(mainApp);
                mainApp.show(gameOver);
            } else if (NewGameEngine.playerGamemode == 0x02 && pBodyTop > objectBottom) {
                if (objectBottom > pHazardTop) {
                    NewGameEngine.playerY += (NewGameEngine.playerInbound) ? (objectBottom - pHazardTop) : 0;
                    NewGameEngine.playerPrintingY += (NewGameEngine.playerInbound) ? (objectBottom - pHazardTop) : 0;
                    NewGameEngine.playerCeiled = NewGameEngine.playerInbound;
                } else {
                    if (!NewGameEngine.playerInbound) NewGameEngine.playerInbound = true;
                    if (pHazardLeft > objectRight) NewGameEngine.playerInbound = false;
                    NewGameEngine.playerGrounded = NewGameEngine.playerInbound && objectBottom == pHazardTop && pHazardLeft <= objectRight && pHazardRight >= objectLeft;
                }
            } else if (NewGameEngine.playerGamemode == 0x02 && pBodyBottom < objectTop) {
                if (objectTop < pHazardBottom) {
                    NewGameEngine.playerY -= (NewGameEngine.playerInbound) ? (pHazardBottom - objectTop) : 0;
                    NewGameEngine.playerPrintingY -= (NewGameEngine.playerInbound) ? (pHazardBottom - objectTop) : 0;
                    NewGameEngine.playerGrounded = NewGameEngine.playerInbound;
                } else {
                    if (!NewGameEngine.playerInbound) NewGameEngine.playerInbound = true;
                    if (pHazardLeft > objectRight) NewGameEngine.playerInbound = false;
                    NewGameEngine.playerGrounded = NewGameEngine.playerInbound && objectTop == pHazardBottom && pHazardLeft <= objectRight && pHazardRight >= objectLeft;
                }
            }
        }
    }
}
