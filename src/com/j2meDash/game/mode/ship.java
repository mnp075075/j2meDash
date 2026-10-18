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
    private boolean gravity = true;
    private boolean isHolding = false;
    private boolean buoyancy = false;
    private boolean antibuoyancy = true;
    private int frameCount = 0;
    private int[] graph_value = {1,2,3,4};
    private Image shipIcon;
    private Image origIcon;

    public ship(MainApp mainApp) {
        super(true);
        this.mainApp = mainApp;
        try {
            shipIcon = Image.createImage("/rsc/temp/ship1.bmp");
            origIcon = Image.createImage("/rsc/temp/ship1.bmp");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(Graphics g, boolean normal_size, boolean normal_gravity) {
        gravity = normal_gravity;
        System.out.println("[DEBUG] frameCount=" + frameCount + " angle=" + degrees + "° buoyancy=" + buoyancy + " antibuoyancy=" + antibuoyancy + " isHolding=" + isHolding);
        int dy = graph_value[(frameCount < 10) ? 0 : (frameCount < 20) ? 1 : (frameCount < 30) ? 2 : 3];
        if (isHolding) {
            if (antibuoyancy) {
                ge.playerY += (normal_gravity) ? dy : -dy;
                degrees = (normal_gravity) ? ((frameCount < 5) ? 0.0 : (frameCount < 10) ? 15.0 : (frameCount < 20) ? 30.0 : (frameCount < 30) ? 45.0 : 60.0) : (frameCount < 5) ? 180.0 : (frameCount < 10) ? 195.0 : (frameCount < 20) ? 210.0 : (frameCount < 30) ? 225.0 : 240.0;
                if ((frameCount-=((frameCount < 60) ? 3 : 2)) <= 0) antibuoyancy = false;
            } else {
                ge.playerY += (normal_gravity) ? -dy : dy;
                degrees = (normal_gravity) ? ((frameCount < 5) ? 0.0 : (frameCount < 10) ? 345.0 : (frameCount < 20) ? 330.0 : (frameCount < 30) ? 315.0 : 300.0) : (frameCount < 5) ? 180.0 : (frameCount < 10) ? 165.0 : (frameCount < 20) ? 150.0 : (frameCount < 30) ? 135.0 : 120.0;
                frameCount++;
            }
        } else {
            if (buoyancy) {
                ge.playerY += (normal_gravity) ? -dy : dy;
                degrees = (normal_gravity) ? ((frameCount < 5) ? 0.0 : (frameCount < 10) ? 345.0 : (frameCount < 20) ? 330.0 : (frameCount < 30) ? 315.0 : 300.0) : (frameCount < 5) ? 180.0 : (frameCount < 10) ? 165.0 : (frameCount < 20) ? 150.0 : (frameCount < 30) ? 135.0 : 120.0;
                if ((frameCount-=((frameCount < 60) ? 3 : 2)) <= 0) buoyancy = false;
            } else {
                ge.playerY += (normal_gravity) ? dy : -dy;
                degrees = (normal_gravity) ? ((frameCount < 5) ? 0.0 : (frameCount < 10) ? 15.0 : (frameCount < 20) ? 30.0 : (frameCount < 30) ? 45.0 : 60.0) : (frameCount < 5) ? 180.0 : (frameCount < 10) ? 195.0 : (frameCount < 20) ? 210.0 : (frameCount < 30) ? 225.0 : 240.0;
                frameCount++;
            }
        }
        /*
        if (ge.playerY >= 360) {
            ge.playerY = 360;
            buoyancy = (normal_gravity) ? false : true;
            antibuoyancy = (normal_gravity) ? true : false;
            degrees = (normal_gravity) ? 0.0 : 180.0;
        } else if (ge.playerY <= 40) {
            ge.playerY = 40;
            buoyancy = (normal_gravity) ? true : false;
            antibuoyancy = (normal_gravity) ? false : true;
            degrees = (normal_gravity) ? 0.0 : 180.0;
        }
        */
        shipIcon = gm.rotateSprite(origIcon, degrees);
        if (!normal_gravity) shipIcon = gm.mirrorSprite(shipIcon, gameMechanics.HORIZONTAL);
        g.drawImage(shipIcon, ge.playerX, ge.playerY, Graphics.BOTTOM | Graphics.RIGHT);
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