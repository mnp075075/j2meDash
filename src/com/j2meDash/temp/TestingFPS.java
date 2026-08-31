package com.j2meDash.temp;

import com.j2meDash.main.MainApp;
import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;

/*

  888888  .d8888b.  888b     d888 8888888888 8888888b.        d8888  .d8888b.  888    888 
    "88b d88P  Y88b 8888b   d8888 888        888  "Y88b      d88888 d88P  Y88b 888    888 
     888        888 88888b.d88888 888        888    888     d88P888 Y88b.      888    888 
     888      .d88P 888Y88888P888 8888888    888    888    d88P 888  "Y888b.   8888888888 
     888  .od888P"  888 Y888P 888 888        888    888   d88P  888     "Y88b. 888    888 
     888 d88P"      888  Y8P  888 888        888    888  d88P   888       "888 888    888 
     88P 888"       888   "   888 888        888  .d88P d8888888888 Y88b  d88P 888    888 
     888 888888888  888       888 8888888888 8888888P" d88P     888  "Y8888P"  888    888 
   .d88P                                                                                  
 .d88P"                                                                                   
888P"   

*/

public class TestingFPS extends GameCanvas implements Runnable {
    private MainApp mainApp;
	
	public TestingFPS(MainApp mainApp) {
		super(true);
		
		this.mainApp = mainApp;
	}

    private int frameCount = 0;
    private long startTime = System.currentTimeMillis();
    private boolean isRunning = true;
    private int targetFPS = 60;
    private long frameInterval = 1000 / targetFPS;

    public void showNotify() {
        Thread thread = new Thread(this);
        thread.start();
    }
    
    public void run() {
        Graphics g = getGraphics();
        
        long lastFpsCheck = System.currentTimeMillis();
        int displayedFps = 0;
        
        long targetFrameTime = 1000 / 60; 

        while (isRunning) {
            long frameStart = System.currentTimeMillis();

            if (frameStart - lastFpsCheck >= 1000) {
                displayedFps = frameCount;
                frameCount = 0;
                lastFpsCheck = frameStart;
            }

            g.setColor(0x000000); // Clear background
            g.fillRect(0, 0, getWidth(), getHeight());

            g.setColor(0xFFFFFF); // Draw FPS counter
            g.drawString("FPS: " + displayedFps, getWidth() / 2, getHeight() / 2, Graphics.BASELINE | Graphics.HCENTER);

            flushGraphics();
            frameCount++;

            long frameDuration = System.currentTimeMillis() - frameStart;
            long sleepTime = targetFrameTime - frameDuration;

            if (sleepTime > 0) {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {}
            }
        }
    }
}
