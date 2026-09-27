package com.j2meDash.game.mode;

import com.j2meDash.game.NewGameEngine;
import com.j2meDash.main.MainApp;

import java.io.IOException;

import javax.microedition.lcdui.game.*;

public class wave extends GameCanvas {
    private MainApp mainApp;
    private NewGameEngine ge;
    private boolean isHolding = false;

    public wave(MainApp mainApp) {
        super(true);
        this.mainApp = mainApp;
        try {

        } catch (IOException io) {
            io.printStackTrace();
        }
    }
    
    public void update(boolean normal_size, boolean normal_gravity) {
        Graphics g = getGraphics();
        if (isHolding) {
            int size = (normal_size) ? 1 : 2;
            ge.playerY += (normal_gravity) ? -size : size;
            // g.drawImage(image, ge.playerX, ge.playerY, g.RIGHT | g.BOTTOM);
        }
    }
    
    protected void pointerPressed(int x, int y) {
        isHolding = true;
    }

    protected void pointerReleased(int x, int y) {
        isHolding = false;
    }
}