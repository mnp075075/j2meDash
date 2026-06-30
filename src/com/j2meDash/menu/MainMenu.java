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
			FIREINTHEHOLE = Image.createImage("rsc/img/cubeRotate0.png");
			background = Image.createImage("rsc/img/background.png");
			gdlogo = Image.createImage("rsc/img/gdlogo.png");
			// SpeedForm speedForm = new SpeedForm();
			
			// bgMusic.start();
			// normalMusic.start();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		
	}

	/* public void showNotify() {
		isRunning = true;
		t = new Thread(this);
		t.start();
	}
	
	public void hideNotify() {
		isRunning = false;
		t = null;
	} */
	
	/* public void run() {
		// blank
	} */
	
	// GUI FOR MAIN MENU
	public void paint(Graphics g) { // the gui, basically painting stuff to the screen
		
		Font font1 = Font.getFont(Font.FACE_MONOSPACE, Font.STYLE_ITALIC, Font.SIZE_LARGE);
		Font font2 = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_MEDIUM);
			
		if (background != null) {
			g.drawImage(background, 0, 0, Graphics.LEFT | Graphics.TOP);
		}
		
		g.setFont(font1);
		g.setColor(0,0,0);
		g.drawString("top left", 0, 0, Graphics.LEFT | Graphics.TOP);
		g.drawString("top right", 239, 0, Graphics.RIGHT | Graphics.TOP);
		g.drawString("bottom left", 0, 399, Graphics.LEFT | Graphics.BOTTOM);
		g.drawString("bottom right", 239, 399, Graphics.RIGHT | Graphics.BOTTOM);
		g.drawString("center", 120, 200, Graphics.HCENTER | Graphics.VCENTER);
		
		g.drawLine(0, 200, 239, 200);
		g.drawLine(0, 202, 239, 202);
		g.drawLine(0, 204, 239, 204);
		
		g.drawRect(0, 100, 10, 10);
		g.drawRect(50, 100, 10, 10);
		g.fillRect(0, 50, 10, 10);
		
		g.setColor(73, 201, 142);
		g.drawArc(0, 20, 10, 10, 0, 360);
		
		g.setColor(255, 255, 255);
		
		
		if (FIREINTHEHOLE != null) {
			g.drawImage(FIREINTHEHOLE, 100, 100, Graphics.HCENTER | Graphics.VCENTER);
		} 
		
		if (gdlogo != null) {
			g.drawImage(gdlogo, 120, 100, Graphics.HCENTER | Graphics.VCENTER);
		}
		
		g.setColor(0, 0, 0);
		g.setFont(font2);
		g.fillRect(100, 300, 40, 30);
		g.setColor(255, 255, 255);
		g.drawString("Exit", 120, 315, Graphics.HCENTER | Graphics.VCENTER);

		g.setColor(0,0,0);
		g.fillRect(100, 340, 40, 30);
		g.setColor(255,255,255);
		g.drawString("Sound", 120, 355, Graphics.HCENTER | Graphics.VCENTER);
		
		g.setColor(0,0,0);
		g.fillRect(100, 50, 40, 30);
		g.setColor(255,255,255);
		g.drawString("Timer", 120, 75, Graphics.HCENTER | Graphics.VCENTER);
	
		g.setColor(0,0,0);
		g.fillRect(100, 220, 40, 30);
		g.setColor(255,255,255);
		g.drawString("About", 120, 235, Graphics.HCENTER | Graphics.VCENTER);
		
		g.setColor(0,0,0);
		g.fillRect(100, 260, 40, 30);
		g.setColor(255,255,255);
	
		g.drawString("Play", 120, 275, Graphics.HCENTER | Graphics.VCENTER);
			
		// g.fillRect(0,385,200,400);
		
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
			
			mainApp.showPlayScreen(null);
			
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