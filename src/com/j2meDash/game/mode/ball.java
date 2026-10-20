package com.j2meDash.game.mode;

import com.j2meDash.game.NewGameEngine;
import com.j2meDash.main.MainApp;
import com.j2meDash.game.tools.inputHandler;

import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;

public class ball extends GameCanvas implements inputHandler {
    private MainApp mainApp;
    private NewGameEngine ge = new NewGameEngine(mainApp);
    private boolean isTouching = false;
    private boolean isBalling = false;
    private long frameCount = 0;
    private int[] graph_value = {1,2,3,4};
    private Image debug;

    public ball(MainApp mainApp) {
        super(true);
        this.mainApp = mainApp;
        try {
            debug = Image.createImage("/rsc/temp/debug.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(Graphics g, boolean normal_size, boolean normal_gravity) {
        int dy = graph_value[(frameCount < 10) ? 0 : (frameCount < 20) ? 1 : (frameCount < 30) ? 2 : 3];
        if (isBalling) {
            ge.playerY += (normal_gravity) ? -dy : dy;
            frameCount++;
            // TODO: add collision checking here
        } else {
            // TODO: ditto
        }
    }

    public void pointerPressed(int x, int y) {
        isTouching = true;
        isBalling = true;
    }

    public void pointerReleased(int x, int y) {
        isTouching = false;
    }
}