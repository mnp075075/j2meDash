package com.j2meDash.menu;
import com.j2meDash.game.PlayScreen;
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
	Image background;
	Image gdlogo;

	private MainApp mainApp;
	private ExitMenu exitMenu;
	private SoundMenu soundMenu;
	private NewTimerScreen newTimerScreen;
	private AboutMenu aboutMenu;
	private SpeedForm speedForm;
	private PlayScreen playScreen;
	
	private int w = getWidth();
	private int h = getHeight();

	private volatile boolean isRunning = true;
	
	public MainMenu(MainApp mainApp) { // the constructor
		super(true); // REQUIRED
		
		this.mainApp = mainApp;
	
		// IMAGE
		try {
			background = Image.createImage("/rsc/img/background.png");
			gdlogo = Image.createImage("/rsc/img/gdlogo.png");
		} catch (Exception e) {
			e.printStackTrace();
		}

		
	}
	
	// GUI FOR MAIN MENU
	public void paint(Graphics g) { // the gui, basically painting stuff to the screen
		int fontOffset = (int) (30-g.getFont().getHeight())/2;
		int gap = (int) Math.round(((0.9-0.5)*h)/(5-1));
		if (background != null && gdlogo != null) {
			g.drawImage(background, 0, 0, Graphics.LEFT | Graphics.TOP);
			g.drawImage(gdlogo, w/2, h/4, Graphics.HCENTER | Graphics.VCENTER);
		}
		
		// Timer button
		g.setColor(0,0,0);
		g.fillRect(w/2-20, h/2+gap*0-15, 40, 30);
		g.setColor(255,255,255);
		g.drawString("Timer", w/2, h/2+gap*0+fontOffset, Graphics.HCENTER | Graphics.BASELINE);
	
		// About button
		g.setColor(0,0,0);
		g.fillRect(w/2-20, h/2+gap*1-15, 40, 30);
		g.setColor(255,255,255);
		g.drawString("About", w/2, h/2+gap*1+fontOffset, Graphics.HCENTER | Graphics.BASELINE);
		
		// Play button
		g.setColor(0,0,0);
		g.fillRect(w/2-20, h/2+gap*2-15, 40, 30);
		g.setColor(255,255,255);
		g.drawString("Play", w/2, h/2+gap*2+fontOffset, Graphics.HCENTER | Graphics.BASELINE);
	
		// Exit button
		g.setColor(0, 0, 0);
		g.fillRect(w/2-20, h/2+gap*3-15, 40, 30);
		g.setColor(255, 255, 255);
		g.drawString("Exit", w/2, h/2+gap*3+fontOffset, Graphics.HCENTER | Graphics.BASELINE);

		// Sound button
		g.setColor(0,0,0);
		g.fillRect(w/2-20, h/2+gap*4-15, 40, 30);
		g.setColor(255,255,255);
		g.drawString("Sound", w/2, h/2+gap*4+fontOffset, Graphics.HCENTER | Graphics.BASELINE);
		
	}

	// BUTTONS FOR MAIN MENU
	protected void pointerPressed(int x, int y) { 
	// a way to detect input for touchscreens, as for classic phones that use other command
	
		if (x >= 100 && x <= 140 && y >= 300 && y <= 330) {
			exitMenu = new ExitMenu(mainApp);
			mainApp.show(exitMenu);
		} else if (x >= 100 && x <= 140 && y >= 340 && y <= 370) {
			soundMenu = new SoundMenu(mainApp);
			mainApp.show(soundMenu);
		} else if (x >= 100 && x <= 140 && y >= 50 && y <= 80) {
			newTimerScreen = new NewTimerScreen(mainApp);
			mainApp.show(newTimerScreen);
		} else if (x >= 100 && x <= 140 && y >= 260 && y <= 290) {
			playScreen = new PlayScreen(mainApp);
			mainApp.show(playScreen);
		} else if (x >= 100 && x <= 140 && y >= 220 && y <= 250) {
			aboutMenu = new AboutMenu(mainApp);
			mainApp.show(aboutMenu);
		}
	
	}
	
	protected void keyPressed(int keyCode) { 
	// this is also to detect input from key presses, not suitable for touchscreen ones
		if (keyCode == KEY_NUM1) {
			speedForm = new SpeedForm(null);
			mainApp.show(speedForm);
		}
	}

}