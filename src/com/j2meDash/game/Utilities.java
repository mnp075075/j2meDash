package com.j2meDash.game;

import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;
import java.io.*;

import com.j2meDash.main.MainApp;
import com.j2meDash.pars.LevelBinaryParser;

public class Utilities extends GameCanvas implements CommandListener {
    private MainApp mainApp;
    private LevelBinaryParser levelBinaryParser;

    private TextBox dirLoader = new TextBox("Load the level data", "file:///", 1024, TextField.URL | TextField.NON_PREDICTIVE);
    private Command ok = new Command("OK", Command.OK, 1);
    private Command exit = new Command("Exit", Command.EXIT, 1);

    private Form flagChooser = new Form("Flag Changer");
    private TextBox gravityAndSize = new TextBox("Value:", null, 1, TextField.NUMERIC);
	private TextBox mode = new TextBox("Value:", null, 1, TextField.NUMERIC);
	private TextBox speed = new TextBox("Value:", null, 1, TextField.NUMERIC);
    private Command GravityAndSize = new Command("Gravity&Size", Command.ITEM, 1);
    private Command Mode = new Command("Mode", Command.ITEM, 1);
    private Command Speed = new Command("Speed", Command.ITEM, 1);
    private Command Exit = new Command("Exit", Command.EXIT, 1);
    private Command Back = new Command("Return", Command.BACK, 1);
	private Command Save = new Command("Save", Command.OK, 1);
    private String savedMessage = "The value has been saved";
	private String errorMessageGas = "The value you have typed is invalid\n" +
								 "For Gravity&Size, valid values are:\n" +
								 "0x00: flippedGravity+miniMode(int 0)\n" +
								 "0x01: flippedGravity+normalMode(int 1)\n" +
								 "0x10: normalGravity+miniMode(int 16)\n" +
								 "0x11: normalGravity+normalMode(int 17)\n";
								 
	private String errorMessageM = "The value you have typed is invalid\n" +
							   "For Mode, valid values are:\n" +
							   "0x00: cube (int 0)\n 0x01: ship (int 1)\n" +
							   "0x02: ball (int 2)\n 0x03: ufo (int 3)\n" +
							   "0x04: wave (int 4)\n 0x05: robot (int 5)\n" +
							   "0x06: spider (int 6)\n 0x07: swing (unused)\n";
		
	private String errorMessageS = "The value you have typed is invalid\n" +
							   "For Speed, valid values are:\n" +
							   "0x00: 1x speed (int 0)\n0x01: 0.5x speed (int 1)\n" +
							   "0x02: 2x speed (int 2)\n0x03: 3x speed (int 3)\n" +
							   "0x04: 4x speed (int 4)";
    private Alert saved = new Alert("Saved successfully", savedMessage, null, AlertType.INFO);
	private Alert errorGas = new Alert("Failed to save", errorMessageGas, null, AlertType.ERROR);
	private Alert errorM = new Alert("Failed to save", errorMessageM, null, AlertType.ERROR);
	private Alert errorS = new Alert("Failed to save", errorMessageS, null, AlertType.ERROR);
    private byte gravityAndSizeFlags;
	private byte modeFlags;
	private byte speedFlags;

    public Utilities(MainApp mainApp) {
        super(true);
        
        this.mainApp = mainApp;
    }

    public void showDir() {
        dirLoader.addCommand(ok);
        dirLoader.addCommand(exit);
        dirLoader.setCommandListener(this);
        levelBinaryParser = new LevelBinaryParser(mainApp);
        Display.getDisplay(mainApp).setCurrent(dirLoader);
    }
	
	public void showFlag() {
        flagChooser.append("This is for changing the configuration for your gameplay");
		flagChooser.addCommand(GravityAndSize);
		flagChooser.addCommand(Mode);
		flagChooser.addCommand(Speed);
		flagChooser.addCommand(Exit);

		gravityAndSize.addCommand(Back);
		gravityAndSize.addCommand(Save);
		mode.addCommand(Back);
		mode.addCommand(Save);
		speed.addCommand(Back);
		speed.addCommand(Save);
		
		Display.getDisplay(mainApp).setCurrent(flagChooser);
		
		flagChooser.setCommandListener(this);
		gravityAndSize.setCommandListener(this);
		mode.setCommandListener(this);
		speed.setCommandListener(this);
	}

    public void paint(Graphics g) {
        g.setColor(0xffffff);
        g.fillRect(0,0,240,400);

        g.setColor(0x000000);
        g.fillRect(70, 100, 100, 20);
        g.setColor(0xffffff);
        g.drawString("Load level", 120, 110, Graphics.HCENTER | Graphics.VCENTER);

        g.setColor(0x000000);
        g.fillRect(70, 140, 100, 20);
        g.setColor(0xffffff);
        g.drawString("Change flags", 120, 130, Graphics.HCENTER | Graphics.VCENTER);
    }

    public void commandAction(Command c, Displayable d) {
        if (c == ok) {
            System.out.println("pressed ok");
            try {
                System.out.println(dirLoader.getString());
                levelBinaryParser.parseByte(dirLoader.getString());
            } catch (Exception e) {
                System.out.println("invalid path or parser error");
            }
        } else if (c == exit) {
            System.out.println("pressed exit");
            Display.getDisplay(mainApp).setCurrent(this);
        } else if (c == Save) {
            Displayable[] arr = {gravityAndSize, mode, speed};

            try {
                if (d == gravityAndSize) {
                    byte val = Byte.parseByte(gravityAndSize.getString());
                    if (val == 0x00 || val == 0x01 || val == 0x10 || val == 0x11) {
                        gravityAndSizeFlags = val;
                        System.out.println("gravityAndSizeFlags = 0x" + Integer.toHexString(gravityAndSizeFlags));
                        Display.getDisplay(mainApp).setCurrent(saved, arr[0]);
                    } else {
                        Display.getDisplay(mainApp).setCurrent(errorGas, arr[0]);
                    }
                } else if (d == mode) {
                    byte val = Byte.parseByte(mode.getString());
                    // Valid values: 0x00 through 0x06
                    if (val >= 0x00 && val <= 0x06) {
                        modeFlags = val;
                        System.out.println("modeFlags = 0x" + Integer.toHexString(modeFlags));
                        Display.getDisplay(mainApp).setCurrent(saved, arr[1]);
                    } else {
                        Display.getDisplay(mainApp).setCurrent(errorM, arr[1]);
                    }
                } else if (d == speed) {
                    byte val = Byte.parseByte(speed.getString());
                    // Valid values: 0x00 through 0x04
                    if (val >= 0x00 && val <= 0x04) {
                        speedFlags = val;
                        mainApp.speedCount = speedFlags;
                        System.out.println("speedFlags = 0x" + Integer.toHexString(speedFlags));
                        Display.getDisplay(mainApp).setCurrent(saved, arr[2]);
                    } else {
                        Display.getDisplay(mainApp).setCurrent(errorS, arr[2]);
                    }
                }
            } catch (Exception e) {
                // Handles NumberFormatException when parsing invalid string inputs
                if (d == arr[0]) {
                    Display.getDisplay(mainApp).setCurrent(errorGas, arr[0]);
                } else if (d == arr[1]) {
                    Display.getDisplay(mainApp).setCurrent(errorM, arr[1]);
                } else if (d == arr[2]) {
                    Display.getDisplay(mainApp).setCurrent(errorS, arr[2]);
                }
                e.printStackTrace();
            }
        } else if (c == Back) {
            Display.getDisplay(mainApp).setCurrent(this);
        } else if (c == GravityAndSize) {
            Display.getDisplay(mainApp).setCurrent(gravityAndSize);
        } else if (c == Mode) {
            Display.getDisplay(mainApp).setCurrent(mode);
        } else if (c == Speed) {
            Display.getDisplay(mainApp).setCurrent(speed);
        }
    }

    protected void pointerPressed(int x, int y) {
        if (x < 170 && x > 70 && y < 120 && y > 100) {
            showDir();
        } else if (x < 170 && x > 70 && y < 160 && y > 140) {
            showFlag();
        }
    }
}
		
