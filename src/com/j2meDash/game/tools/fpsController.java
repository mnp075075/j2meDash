package com.j2meDash.game.tools;

import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;

public class fpsController extends GameCanvas {

    public fpsController() {
        super(true);
    }

    private int frameCount = 0;
    private int displayFPS = 0;
    private long start_limit = System.currentTimeMillis();
    private long start_count = System.currentTimeMillis();

    public void limitFPS(int fpsLimit) {
        if (fpsLimit <= 0) return;
        long target = 1000 / fpsLimit;
        long now = System.currentTimeMillis();
        long duration = now - start_limit;
        long sleep = target - duration;
        if (sleep > 0) {
            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        start_limit = System.currentTimeMillis(); 
    }

    public void printFPS() {
        Graphics g = getGraphics();
        long now = System.currentTimeMillis();
        if (now - start_count >= 1000) {
            displayFPS = frameCount;
            frameCount = 0;
            start_count = now;
        }
        g.setColor(0x000000);
        g.fillRect(0,0,100,100);
        g.setColor(0xffffff);
        g.drawString("FPS: " + displayFPS, 0, 0, Graphics.LEFT | Graphics.TOP);
        flushGraphics();
        frameCount++;
    }
}