package com.j2meDash.menu;
import com.j2meDash.game.tools.fpsController;
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

public class AboutMenu extends GameCanvas implements Runnable {

	private MainApp mainApp;
	private MainMenu mainMenu;
	private fpsController fpsController = new fpsController();
	private Thread t;
	private Image background;
	private Image foreground;
	private boolean running = false;

	private int w = getWidth();
	private int h = getHeight();
	
	public AboutMenu(MainApp mainApp) {
		super(true); // REQUIRED
		this.mainApp = mainApp;
		try {
			background = Image.createImage("/rsc/general/background.png");
			foreground = Image.createImage("/rsc/general/foreground.png");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void showNotify() {
		running = true;
		t = new Thread(this);
		t.start();
	}

	public void hideNotify() {
		running = false;
		t = null;
	}

	public void run() {
		Graphics g = getGraphics();

		while (running) {
			fpsController.limitFPS(60);
			if (MainMenu.reductionA <= w) MainMenu.reductionA += 1;
			else MainMenu.reductionA = 0;
			if (MainMenu.reductionB <= w) MainMenu.reductionB += 2;
			else MainMenu.reductionB = 0;

			g.drawImage(background, 0-MainMenu.reductionA, 0, Graphics.LEFT | Graphics.TOP);
			g.drawImage(background, w-MainMenu.reductionA, 0, Graphics.LEFT | Graphics.TOP);
			g.drawImage(foreground, 0-MainMenu.reductionB, (int)(0.9*h), Graphics.LEFT | Graphics.TOP);
			g.drawImage(foreground, w-MainMenu.reductionB, (int)(0.9*h), Graphics.LEFT | Graphics.TOP);

			g.setColor(0xFFFFFF);
			g.drawString("About Us", 10, 30, Graphics.LEFT | Graphics.TOP);
			g.drawString("This game is inspired from:", 10, 60, Graphics.LEFT | Graphics.TOP);
			g.drawString("Geometry Dash", 10, 75, Graphics.LEFT | Graphics.TOP );
			g.drawString("Which is originally made by:", 10, 90, Graphics.LEFT | Graphics.TOP);
			g.drawString("RobTop Games AB", 10, 105, Graphics.LEFT | Graphics.TOP);


			flushGraphics();
		}
	}
	
	// BUTTONS FOR ABOUT MENU
	protected void pointerPressed(int x, int y) {
		if (x >= 100 && x <= 140 && y >= 250 && y <= 280) {
			if (mainMenu == null) mainMenu = new MainMenu(mainApp);
			mainApp.show(mainMenu);
		}
	}
}