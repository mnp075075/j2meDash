package com.j2meDash.game.mode;

import com.j2meDash.game.NewGameEngine;
import com.j2meDash.main.MainApp;
import com.j2meDash.game.tools.inputHandler;

import java.io.*;

import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;

public class ship extends GameCanvas implements inputHandler {
    private MainApp mainApp;
    private NewGameEngine ge = new NewGameEngine(mainApp);
    private boolean isTouching = false;
    private boolean isJumping = false;
    private int frameCount = 0;
    private int[] graph_value = {6,5,5,5,4,4,3,3,3,2,2,1,1,1,0};
    private Image debug;

    public ship(MainApp mainApp) {
        super(true);
        this.mainApp = mainApp;
        try {
            debug = Image.createImage("/rsc/temp/debug.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(Graphics g, boolean normal_size, boolean normal_gravity) {

    
 
    }

    public void pointerPressed(int x, int y) {

    }

    public void pointerReleased(int x, int y) {

    }
}