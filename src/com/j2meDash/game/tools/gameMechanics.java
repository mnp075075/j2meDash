package com.j2meDash.game.tools;

import com.j2meDash.main.MainApp;
import com.j2meDash.game.NewGameEngine;
import com.j2meDash.game.mode.*;

public class gameMechanics {
    private MainApp mainApp;
    private NewGameEngine ge;

    public gameMechanics(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public void changeGamemode(byte gamemode) {
        switch (gamemode) {
            case 0x01:
                if (ge.cubeGM == null) ge.cubeGM = new cube(mainApp);
                ge.inputHandler = ge.cubeGM;
                System.out.println("[INFO] Changed gamemode to [CUBE] with byte number: " + gamemode);
                break;
            case 0x02:
                if (ge.shipGM == null) ge.shipGM = new ship(mainApp);
                ge.inputHandler = ge.shipGM;
                System.out.println("[INFO] Changed gamemode to [SHIP] with byte number: " + gamemode);
                break;
            case 0x03:
                if (ge.ballGM == null) ge.ballGM = new ball(mainApp);
                ge.inputHandler = ge.ballGM;
                System.out.println("[INFO] Changed gamemode to [BALL] with byte number: " + gamemode);
                break;
            case 0x04:
                if (ge.ufoGM == null) ge.ufoGM = new ufo(mainApp);
                ge.inputHandler = ge.ufoGM;
                System.out.println("[INFO] Changed gamemode to [UFO] with byte number: " + gamemode);
                break;
            case 0x05:
                if (ge.waveGM == null) ge.waveGM = new wave(mainApp);
                ge.inputHandler = ge.waveGM;
                System.out.println("[INFO] Changed gamemode to [WAVE] with byte number: " + gamemode);
                break;
            case 0x06:
                if (ge.robotGM == null) ge.robotGM = new robot(mainApp);
                ge.inputHandler = ge.robotGM;
                System.out.println("[INFO] Changed gamemode to [ROBOT] with byte number: " + gamemode);
                break;
            case 0x07:
                if (ge.spiderGM == null) ge.spiderGM = new spider(mainApp);
                ge.inputHandler = ge.spiderGM;
                System.out.println("[INFO] Changed gamemode to [SPIDER] with byte number: " + gamemode);
                break;
            case 0x08:
                if (ge.swingGM == null) ge.swingGM = new swing(mainApp);
                ge.inputHandler = ge.swingGM;
                System.out.println("[INFO] Changed gamemode to [SWING] with byte number: " + gamemode);
                break;
            default: 
                System.out.println("[ERROR] Invalid gamemode, no gamemode with byte number: " + gamemode);
                break;
        }
    }

    public void startPosition(int x, int y) {
        if (x < 0) System.out.println("[ERROR] Invalid arguments for method: startPosition(int x, int y), expected (x > 0)");
        else {
            ge.playerX = x;
            ge.playerY = y;
            System.out.println("[INFO] Start position at x = " + x + " - y = " + y);
        }
    }
}
