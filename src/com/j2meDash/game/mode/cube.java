package com.j2meDash.game.mode;

import com.j2meDash.game.NewGameEngine;
import com.j2meDash.main.MainApp;

import java.io.IOException;

import javax.microedition.lcdui.game.*;

public class cube extends GameCanvas {
    private MainApp mainApp;
    private NewGameEngine ge;
    private boolean isTouching = false;
    private boolean isJumping = false;
    private int frameCount = 0;
    private int[] graph_value = {6,5,5,5,4,4,3,3,3,2,2,1,1,1,0};

    public cube(MainApp mainApp) {
        super(true);
        this.mainApp = mainApp;
        try {

        } catch (IOException io) {
            io.printStackTrace();
        }
    }
    
    public void update(boolean normal_size, boolean normal_gravity) {
        Graphics g = getGraphics();
        if (isJumping) {
            int dy = graph_value[frameCount % 15];
            y += (normal_gravity) ? ((frameCount < 15) ? -dy : dy) : ((frameCount < 15) ? dy : -dy);
            if (++frameCount >= 30) {
                frameCount = 0;
                isJumping = false;
            }
        }
    }
    
    protected void pointerPressed(int x, int y) {
        isTouching = true;
        isJumping = true;
    }

    protected void pointerReleased(int x, int y) {
        isTouching = false;
    }
}