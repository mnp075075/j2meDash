package com.j2meDash.menu;
import com.j2meDash.main.*;

import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;

/*

 ██████╗ ██████╗  ██████╗ ███╗   ██╗     ██╗ █████╗ ██╗   ██╗ █████╗  ██╗██████╗  ██████╗ ████████╗██╗  ██╗
██╔════╝ ██╔══██╗██╔═══██╗████╗  ██║     ██║██╔══██╗██║   ██║██╔══██╗███║██╔══██╗██╔═══██╗╚══██╔══╝██║  ██║
██║  ███╗██║  ██║██║   ██║██╔██╗ ██║     ██║███████║██║   ██║███████║╚██║██║  ██║██║   ██║   ██║   ███████║
██║   ██║██║  ██║██║   ██║██║╚██╗██║██   ██║██╔══██║╚██╗ ██╔╝██╔══██║ ██║██║  ██║██║   ██║   ██║   ╚════██║
╚██████╔╝██████╔╝╚██████╔╝██║ ╚████║╚█████╔╝██║  ██║ ╚████╔╝ ██║  ██║ ██║██████╔╝╚██████╔╝   ██║        ██║
 ╚═════╝ ╚═════╝  ╚═════╝ ╚═╝  ╚═══╝ ╚════╝ ╚═╝  ╚═╝  ╚═══╝  ╚═╝  ╚═╝ ╚═╝╚═════╝  ╚═════╝    ╚═╝        ╚═╝

*/

public class MainMenu extends GameCanvas /* implements Runnable */ {
	Image FIREINTHEHOLE;
	Image background;
	Image gdlogo;
	
	// private Thread t;
	
	private MainApp mainApp;
	// private PlayScreen playScreen;
	
	private volatile boolean isRunning = true;
	
	public MainMenu(MainApp mainApp) { // the constructor
		super(true); // REQUIRED
		
		this.mainApp = mainApp;
	
		// IMAGE
		try {
			// FIREINTHEHOLE = Image.createImage("rsc/img/cubeRotate0.png");
			background = Image.createImage("rsc/img/background.png");
			gdlogo = Image.createImage("rsc/img/gdlogo.png");
			// SpeedForm speedForm = new SpeedForm();
			
			// bgMusic.start();
			// normalMusic.start();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		
	}
	
	// GUI FOR MAIN MENU
	public void paint(Graphics g) { // the gui, basically painting stuff to the screen
			
		if (background != null) {
			g.drawImage(background, 0, 0, Graphics.LEFT | Graphics.TOP);
		}
		
		if (FIREINTHEHOLE != null) {
			g.drawImage(FIREINTHEHOLE, 100, 100, Graphics.HCENTER | Graphics.VCENTER);
		} 
		
		if (gdlogo != null) {
			g.drawImage(gdlogo, 100, 100, Graphics.HCENTER | Graphics.VCENTER);
		}
		
		// Timer button
		g.setColor(0,0,0);
		g.fillRect(100, 200, 40, 30);
		g.setColor(255,255,255);
		g.drawString("Timer", 120, 75, Graphics.HCENTER | Graphics.VCENTER);
	
		// About button
		g.setColor(0,0,0);
		g.fillRect(100, 240, 40, 30);
		g.setColor(255,255,255);
		g.drawString("About", 120, 235, Graphics.HCENTER | Graphics.VCENTER);
		
		// Play button
		g.setColor(0,0,0);
		g.fillRect(100, 280, 40, 30);
		g.setColor(255,255,255);
		g.drawString("Play", 120, 275, Graphics.HCENTER | Graphics.VCENTER);
	
		// Exit button
		g.setColor(0, 0, 0);
		g.fillRect(100, 320, 40, 30);
		g.setColor(255, 255, 255);
		g.drawString("Exit", 120, 315, Graphics.HCENTER | Graphics.VCENTER);

		// Sound button
		g.setColor(0,0,0);
		g.fillRect(100, 360, 40, 30);
		g.setColor(255,255,255);
		g.drawString("Sound", 120, 355, Graphics.HCENTER | Graphics.VCENTER);
		
	}

	// BUTTONS FOR MAIN MENU
	protected void pointerPressed(int x, int y) { 
	// a way to detect input for touchscreens, as for classic phones that use other command
	
		if (x >= 100 && x <= 140 && y >= 300 && y <= 330) {
			mainApp.showExitMenu();
						
		} else if (x >= 100 && x <= 140 && y >= 340 && y <= 370) {
		
			mainApp.showSoundMenu();
			
		} else if (x >= 100 && x <= 140 && y >= 50 && y <= 80) {
			
			mainApp.showNewTimerScreen();
		
		} else if (x >= 100 && x <= 140 && y >= 260 && y <= 290) {
			
			mainApp.showPlayScreen();
			
		} else if (x >= 100 && x <= 140 && y >= 220 && y <= 250) {
			
			mainApp.showAboutMenu();
			
		}
	
	}
	
	protected void keyPressed(int keyCode) { 
	// this is also to detect input from key presses, not suitable for touchscreen ones
		if (keyCode == KEY_NUM1) {
			mainApp.showSpeedForm();
		}
	}

}