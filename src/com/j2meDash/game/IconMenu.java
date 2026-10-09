package com.j2meDash.game;

import com.j2meDash.main.MainApp;
import javax.microedition.lcdui.game.*;

public class IconMenu extends GameCanvas{
    private MainApp mainApp;
    public IconMenu(MainApp mainApp) {
        super(true);
        this.mainApp = mainApp;
    }
}
