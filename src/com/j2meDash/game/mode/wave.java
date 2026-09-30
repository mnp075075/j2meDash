package com.j2meDash.game.mode;

import com.j2meDash.game.NewGameEngine;
import com.j2meDash.game.tools.inputHandler;
import com.j2meDash.main.MainApp;

import java.io.*;

import javax.microedition.lcdui.game.*;
import javax.microedition.lcdui.*;

public class wave extends GameCanvas implements inputHandler {
    private MainApp mainApp;
    private NewGameEngine ge = new NewGameEngine(mainApp);
    private boolean isHolding = false;
    private Image debug;

    public wave(MainApp mainApp) {
        super(true);
        this.mainApp = mainApp;
        try {
            debug = Image.createImage("/rsc/temp/debug.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void update(Graphics g, boolean normal_size, boolean normal_gravity) {
        int ge_wave_size = (normal_size) ? 1 : 2;
        if (isHolding) {
            ge.playerY += (normal_gravity) ? -1*ge_wave_size : ge_wave_size;
            g.drawImage(debug, ge.playerX, ge.playerY, g.BOTTOM | g.RIGHT);
        } else {
            ge.playerY += (normal_gravity) ? ge_wave_size : -1*ge_wave_size;
            g.drawImage(debug, ge.playerX, ge.playerY, g.BOTTOM | g.RIGHT);
        }
        System.out.println("[WAVE] isHolding = " + isHolding);
    }
    
    public void pointerPressed(int x, int y) {
        isHolding = true;
    }

    public void pointerReleased(int x, int y) {
        isHolding = false;
    }
}