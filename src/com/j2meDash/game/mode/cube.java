package com.j2meDash.game.mode;

import com.j2meDash.game.NewGameEngine;
import com.j2meDash.main.MainApp;
import com.j2meDash.game.tools.inputHandler;
import com.j2meDash.game.tools.gameMechanics;
import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;

public class cube extends GameCanvas implements inputHandler {
    private MainApp mainApp;
    private NewGameEngine ge = new NewGameEngine(mainApp);
    private boolean isTouching = false;
    private double degrees = 0.0;
    private boolean isJumping = false;
    private int frameCount = 0;
    private int[] graph_value = {6,5,5,5,4,4,3,3,3,2,2,1,1,1,0};
    private Image debug;
    private Image origImage;

    public cube(MainApp mainApp) {
        super(true);
        this.mainApp = mainApp;
        try {
            debug = Image.createImage("/rsc/temp/debug.png");
            origImage = Image.createImage("/rsc/temp/debug.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void update(Graphics g, boolean normal_size, boolean normal_gravity) {
        gameMechanics gm = new gameMechanics(mainApp);
        degrees = degrees % 360;
        if (isJumping) {
            int dy = graph_value[frameCount % 15];
            ge.playerY += (normal_gravity) ? ((frameCount < 15) ? -dy : dy) : ((frameCount < 15) ? dy : -dy);
            if (++frameCount >= 30) {
                frameCount = 0;
                isJumping = false;
            }
            degrees += 6.0;
            debug = gm.rotateSprite(origImage, degrees);   
        }
        g.drawImage(debug, ge.playerX, ge.playerY, Graphics.BOTTOM | Graphics.RIGHT);
        // System.out.println("[DEBUG]/[PLAYER] degrees=" + degrees);
        // System.out.println("[DEBUG]/[CUBE] isJumping=" + isJumping + " isTouching=" + isTouching);
    }
    
    public void pointerPressed(int x, int y) {
        isTouching = true;
        isJumping = true;
    }

    public void pointerReleased(int x, int y) {
        isTouching = false;
    }
}