package com.j2meDash.game;

import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;

import com.j2meDash.main.MainApp;

public class Utilities extends GameCanvas implements CommandListener {
    private MainApp mainApp;
    
    public Utilities(MainApp mainApp) {
        super(true);
        
        this.mainApp = mainApp;
    }

    public String loadLevelDirectory() {

		/*
        dirLoader = new TextBox("Load the level data from your directory. You may use \"file:///\" to type your file name.", null, 256, TextField.ANY);
		
		String path = dirLoader.getString();
		
		successfully = new Alert("Level data found","Successfully found the level data at: " + path,null,AlertType.INFO);
		failed = new Alert("Level data missing","Cannot find the level data at: " + path,null,AlertType.INFO);

		dirLoader.addCommand(OK);
		dirLoader.addCommand(EXIT);

		dirLoader.setCommandListener(this);
		Display.getDisplay(mainApp).setCurrent(dirLoader);
        */
		return null;
	}
	
	public void flagChooser() {
		
        /*
		FlagChooser = new Form("Flag Value Chooser");
		FlagChooser.append("This is for changing the configuration for your gameplay");
		
		FlagChooser.addCommand(GravityAndSize);
		FlagChooser.addCommand(Mode);
		FlagChooser.addCommand(Speed);
		FlagChooser.addCommand(Exit);
		
		gravityAndSize = new TextBox("Value:", null, 2, TextField.NUMERIC);
		mode = new TextBox("Value:", null, 1, TextField.NUMERIC);
		speed = new TextBox("Value:", null, 1, TextField.NUMERIC);
		gravityAndSize.addCommand(Back);
		gravityAndSize.addCommand(Save);
		
		mode.addCommand(Back);
		mode.addCommand(Save);
		
		speed.addCommand(Back);
		speed.addCommand(Save);
		
		String savedMessage = "The value has been saved";
		String errorMessageGas = "The value you have typed is invalid\n" +
								 "For Gravity&Size, valid values are:\n" +
								 "0x00: flippedGravity+miniMode(int 0)\n" +
								 "0x01: flippedGravity+normalMode(int 1)\n" +
								 "0x10: normalGravity+miniMode(int 16)\n" +
								 "0x11: normalGravity+normalMode(int 17)\n";
								 
		String errorMessageM = "The value you have typed is invalid\n" +
							   "For Mode, valid values are:\n" +
							   "0x00: cube (int 0)\n 0x01: ship (int 1)\n" +
							   "0x02: ball (int 2)\n 0x03: ufo (int 3)\n" +
							   "0x04: wave (int 4)\n 0x05: robot (int 5)\n" +
							   "0x06: spider (int 6)\n 0x07: swing (unused)\n";
		
		String errorMessageS = "The value you have typed is invalid\n" +
							   "For Speed, valid values are:\n" +
							   "0x00: 1x speed (int 0)\n0x01: 0.5x speed (int 1)\n" +
							   "0x02: 2x speed (int 2)\n0x03: 3x speed (int 3)\n" +
							   "0x04: 4x speed (int 4)";
		
		saved = new Alert("Saved successfully", savedMessage, null, AlertType.INFO);
		errorGas = new Alert("Failed to save", errorMessageGas, null, AlertType.INFO);
		errorM = new Alert("Failed to save", errorMessageM, null, AlertType.INFO);
		errorS = new Alert("Failed to save", errorMessageS, null, AlertType.INFO);
		
		tForNoReason = new Ticker("YOUR COPY OF GDONJAVA HAS BEEN CONFISCATED, PLEASE RETURN THIS TO THE SELLER IMMEDIATELY");
		FlagChooser.setTicker(tForNoReason);
		
		Display.getDisplay(mainApp).setCurrent(FlagChooser);
		
		FlagChooser.setCommandListener(this);
		gravityAndSize.setCommandListener(this);
		mode.setCommandListener(this);
		speed.setCommandListener(this);
		
		*/
	}

    public void paint(Graphics g) {
        g.setColor(0x000000);
        g.drawString("Load the level data from your directory", 120, 130, Graphics.HCENTER | Graphics.VCENTER);
        g.drawString("You may use \"file:///\" to type your file name.", 120, 150, Graphics.HCENTER | Graphics.VCENTER);
    }

    public void commandAction(Command c, Displayable d) {
        // blank
    }
}
		
