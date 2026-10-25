package com.j2meDash.game.mode;

import com.j2meDash.game.NewGameEngine;
import com.j2meDash.main.MainApp;
import com.j2meDash.game.tools.gameMechanics;
import com.j2meDash.game.tools.inputHandler;

import java.io.*;

import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;

public class ship extends GameCanvas implements inputHandler {
    private MainApp mainApp;
    private NewGameEngine ge = new NewGameEngine(mainApp);
    private gameMechanics gm = new gameMechanics(mainApp);
    private double degrees = 0.0;
    private boolean isHolding = false;
    private boolean buoyancy = false;
    private boolean antibuoyancy = true;
    private int frameCount = 0;
    private int[] graph_value = {1,2,3,4};
    private Image shipIcon;
    private Image origIcon;
    private int w = getWidth();
    private int h = getHeight();

    private int top = (h-320)/2;
    private int bottom = (h+320)/2;

    public ship(MainApp mainApp) {
        super(true);
        this.mainApp = mainApp;
        try {
            shipIcon = Image.createImage("/rsc/temp/cube1.bmp");
            origIcon = Image.createImage("/rsc/temp/cube1.bmp");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(Graphics g, boolean normal_size, boolean normal_gravity) {
        // NewGameEngine.playerNormalGravity = normal_gravity;
        
        NewGameEngine.playerFloored = (NewGameEngine.playerY >= bottom);
        NewGameEngine.playerRoofed = (NewGameEngine.playerY <= top + 20);

        System.out.println("[DEBUG]/[SHIP] frameCount=" + frameCount + " angle=" + degrees + "° buoyancy=" + buoyancy + " antibuoyancy=" + antibuoyancy + " isHolding=" + isHolding + " playerInbound=" + NewGameEngine.playerInbound + " playerGrounded=" + NewGameEngine.playerGrounded + " playerCeiled=" + NewGameEngine.playerCeiled + " playerFloored=" + NewGameEngine.playerFloored + " playerRoofed=" + NewGameEngine.playerRoofed + " playerY=" + NewGameEngine.playerY);

        int safeFrame = (frameCount > 30) ? 30 : (frameCount < 0) ? 0 : frameCount;
        int dy = graph_value[(safeFrame < 10) ? 0 : (safeFrame < 20) ? 1 : (safeFrame < 30) ? 2 : 3];

        if (NewGameEngine.playerGrounded || NewGameEngine.playerCeiled) {
            frameCount = 0;
            degrees = (normal_gravity) ? 0.0 : 180.0;
            buoyancy = false;
            antibuoyancy = false;
        } else if (isHolding) {
            if (antibuoyancy) {
                if (NewGameEngine.playerY <= top + 20) {
                    antibuoyancy = false;
                } else {
                    NewGameEngine.playerPrintingY += (normal_gravity) ? dy : -dy;
                    NewGameEngine.playerY += (normal_gravity) ? dy : -dy;
                    degrees = (normal_gravity) ? ((frameCount < 5) ? 0.0 : (frameCount < 10) ? 15.0 : (frameCount < 20) ? 30.0 : (frameCount < 30) ? 45.0 : 60.0) : (frameCount < 5) ? 180.0 : (frameCount < 10) ? 195.0 : (frameCount < 20) ? 210.0 : (frameCount < 30) ? 225.0 : 240.0;
                    frameCount -= ((frameCount < 60) ? 3 : 2);
                    if (frameCount <= 0) {
                        frameCount = 0;
                        antibuoyancy = false;
                    }
                }
            } else {
                buoyancy = true;
                if (NewGameEngine.playerY <= top + 20) {
                    frameCount = 0;
                    degrees = (normal_gravity) ? 0.0 : 180.0;
                } else {
                    NewGameEngine.playerPrintingY += (normal_gravity) ? -dy : dy;
                    NewGameEngine.playerY += (normal_gravity) ? -dy : dy;
                    degrees = (normal_gravity) ? ((frameCount < 5) ? 0.0 : (frameCount < 10) ? 345.0 : (frameCount < 20) ? 330.0 : (frameCount < 30) ? 315.0 : 300.0) : (frameCount < 5) ? 180.0 : (frameCount < 10) ? 165.0 : (frameCount < 20) ? 150.0 : (frameCount < 30) ? 135.0 : 120.0;
                    if (frameCount < 30) frameCount++;
                }
            }
        } else {
            if (buoyancy) {
                antibuoyancy = true;
                if (NewGameEngine.playerY >= bottom) {
                    buoyancy = false;
                    antibuoyancy = false;
                } else {
                    NewGameEngine.playerPrintingY += (normal_gravity) ? -dy : dy;
                    NewGameEngine.playerY += (normal_gravity) ? -dy : dy;
                    degrees = (normal_gravity) ? ((frameCount < 5) ? 0.0 : (frameCount < 10) ? 345.0 : (frameCount < 20) ? 330.0 : (frameCount < 30) ? 315.0 : 300.0) : (frameCount < 5) ? 180.0 : (frameCount < 10) ? 165.0 : (frameCount < 20) ? 150.0 : (frameCount < 30) ? 135.0 : 120.0;
                    frameCount -= ((frameCount < 60) ? 3 : 2);
                    if (frameCount <= 0) {
                        frameCount = 0;
                        buoyancy = false;
                    }
                }
            } else {
                if (NewGameEngine.playerY >= bottom) {
                    frameCount = 0;
                    degrees = (normal_gravity) ? 0.0 : 180.0;
                } else {
                    NewGameEngine.playerPrintingY += (normal_gravity) ? dy : -dy;
                    NewGameEngine.playerY += (normal_gravity) ? dy : -dy;
                    degrees = (normal_gravity) ? ((frameCount < 5) ? 0.0 : (frameCount < 10) ? 15.0 : (frameCount < 20) ? 30.0 : (frameCount < 30) ? 45.0 : 60.0) : (frameCount < 5) ? 180.0 : (frameCount < 10) ? 195.0 : (frameCount < 20) ? 210.0 : (frameCount < 30) ? 225.0 : 240.0;
                    if (frameCount < 30) frameCount++;
                }
            }
        }

        shipIcon = gm.rotateSprite(origIcon, degrees);
        if (!normal_gravity) shipIcon = gm.mirrorSprite(shipIcon, gameMechanics.HORIZONTAL);
        g.drawImage(shipIcon, NewGameEngine.playerPrintingX, NewGameEngine.playerPrintingY, Graphics.BOTTOM | Graphics.RIGHT);
    }

    public void pointerPressed(int x, int y) {
        isHolding = true;
        buoyancy = true;
    }

    public void pointerReleased(int x, int y) {
        isHolding = false;
        antibuoyancy = true;
    }
}