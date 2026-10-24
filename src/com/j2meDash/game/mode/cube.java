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
    private Image cubeIcon;
    private Image origIcon;

    public cube(MainApp mainApp) {
        super(true);
        this.mainApp = mainApp;
        try {
            cubeIcon = Image.createImage("/rsc/temp/cube1.bmp");
            origIcon = Image.createImage("/rsc/temp/cube1.bmp");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void update(Graphics g, boolean normal_size, boolean normal_gravity) {
        gameMechanics gm = new gameMechanics(mainApp);
        degrees = degrees % 360;
        if (isJumping) {
            int dy = graph_value[frameCount % 15];
            NewGameEngine.playerPrintingY += (normal_gravity) ? ((frameCount < 15) ? -dy : dy) : ((frameCount < 15) ? dy : -dy);
            NewGameEngine.playerY += (normal_gravity) ? ((frameCount < 15) ? -dy : dy) : ((frameCount < 15) ? dy : -dy);
            if (++frameCount >= 30) {
                frameCount = 0;
                isJumping = false;
            }
            degrees += 6.0;
            cubeIcon = gm.rotateSprite(origIcon, degrees);   
        }
        g.drawImage(cubeIcon, NewGameEngine.playerPrintingX, NewGameEngine.playerPrintingY, Graphics.BOTTOM | Graphics.RIGHT);
    }
    
    public void pointerPressed(int x, int y) {
        isTouching = true;
        isJumping = true;
    }

    public void pointerReleased(int x, int y) {
        isTouching = false;
    }
}