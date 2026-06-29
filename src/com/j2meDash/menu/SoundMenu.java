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

public class SoundMenu extends GameCanvas {

	/*
	 * soundMenu is pretty much dead for now
	 * because I'm currently not using audio for my game
	 * audio controlling in j2me is especially hard
	 * and i don't know why and i'm not stepping in now
	 */
	private MainApp mainApp;
	// Display display = Display.getDisplay(mainApp);
	
	// private MainMenu mainMenu = new MainMenu(mainApp);
	public SoundMenu(MainApp mainApp) {
		super(true); // REQUIRED
		
		this.mainApp = mainApp;
		
		try {
			if (mainApp.SoundEnabled == true) {
				mainApp.bgMusic.start();
			} else {
				mainApp.bgMusic.stop();
			}
		} catch (Exception e) {
			// System.err.println("qasaas");
		}
		
	}

	// GUI FOR SOUND MENU
	public void paint(Graphics g) {
		
		System.out.println("isShown: " + isShown());
		
		g.setColor(0,173,119);
		g.fillRect(0, 0, 240, 400);
		g.setColor(255,255,255);
		g.drawString("Do you want to have sound?", 120, 30, Graphics.VCENTER | Graphics.HCENTER);
		g.drawString("FUN FACT:", 120, 60, Graphics.VCENTER | Graphics.HCENTER);
		g.drawString("Robert Topala is the creator of", 10, 75, Graphics.LEFT | Graphics.TOP);
		g.drawString("Geometry Dash which is made using", 10, 90, Graphics.LEFT | Graphics.TOP);
		g.drawString("Cocos-2dx, a good game engine tbh", 10, 105, Graphics.LEFT | Graphics.TOP);
		g.drawString("Also go support RobTop Games", 10, 120, Graphics.LEFT | Graphics.TOP);
		g.drawString("on YouTube, Discord and Twitter", 10, 135, Graphics.LEFT | Graphics.TOP);
		g.drawString("He makes a good game and a good", 10, 150, Graphics.LEFT | Graphics.TOP);
		g.drawString("community and he is a single dev", 10, 165, Graphics.LEFT | Graphics.TOP);
		g.drawString("so great job on RobTop on making GD!", 10, 180, Graphics.LEFT | Graphics.TOP);
		g.drawString("Copyright Information:", 10, 210, Graphics.LEFT | Graphics.TOP);
		g.drawString("YouTube is a property of Google Inc.", 10, 225, Graphics.LEFT | Graphics.TOP);
		g.drawString("Discord is a property of Discord Inc.", 10, 240, Graphics.LEFT | Graphics.TOP);
		g.drawString("Twitter is a property of X Corp.", 10, 255, Graphics.LEFT | Graphics.TOP);
		g.drawString("So do you want sound?", 120, 285, Graphics.VCENTER | Graphics.HCENTER);
		g.setColor(0,206,119);
		g.fillRect(40, 310, 40, 20);
		g.fillRect(160, 310, 40, 20);
		g.setColor(255,255,255);
		g.drawString("Yes", 60, 320, Graphics.VCENTER | Graphics.HCENTER);
		g.drawString("No", 180, 320, Graphics.VCENTER | Graphics.HCENTER);
		
	}

	// BUTTONS FOR SOUND MENU
	protected void pointerPressed (int x, int y) {
		
		// timerScreen = new TimerScreen();
		
		if (x >= 40 && x <= 80 && y >= 310 && y <= 330) {
			
			// SoundEnabled = true;
			// display.setCurrent(timerScreen);
			// timerScreen.repaint();
			mainApp.showMainMenu();
			System.err.println("IT WORKS");
			
			// NORMAL LOOKING LOOP
			/* for (int i = 0; i < 5; i++) {
				System.err.println("loop: " + i);
			} */
			// I have to turn this loop off to optimize performance

		} else if (x >= 160 && x <= 200 && y >= 310 && y <= 330) {
			
			// SoundEnabled = false;
			// display.setCurrent(timerScreen);
			// timerScreen.repaint();
			mainApp.showMainMenu();
			System.err.println("IT ALSO WORKS");
		}
			
	}
}