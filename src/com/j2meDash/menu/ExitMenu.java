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

public class ExitMenu extends GameCanvas implements Runnable {
	
	private MainApp mainApp;
	private MainMenu mainMenu;
	private Image background;
	private fpsController fpsController = new fpsController();
	private Image foreground;
	private Thread t;
	private volatile boolean running = false;
	
	private int w = getWidth();
	private int h = getHeight();
	public static int reductionA = 0;
	public static int reductionB = 0;
	
	public ExitMenu(MainApp mainApp) {
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
		int fontOffset = (20-g.getFont().getHeight())/2;
		Font f1 = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_LARGE);
		Font f2 = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL);

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

			g.setFont(f1);
			g.setColor(0xffffff);
			g.drawString("Do you want to exit?", w/2, h/2, Graphics.BASELINE | Graphics.HCENTER);
			g.setFont(f2);
			g.drawString("Do you want to exit? (double-check)", w/2, h/2+15, Graphics.BASELINE | Graphics.HCENTER);

			g.setColor(0x005ebb);
			g.fillRect(w/5, (h*8)/10-10, 40, 20);
			g.fillRect((w*8)/10-40, (h*8)/10-10, 40, 20);
			g.setColor(0xffffff);
			g.drawString("Exit", w/5+20, (h*8)/10+fontOffset, Graphics.BASELINE | Graphics.HCENTER);
			g.drawString("Cancel", (w*4)/5-20, (h*8)/10+fontOffset, Graphics.BASELINE | Graphics.HCENTER);
			flushGraphics();
		}
	}
		
	// BUTTONS FOR EXIT MENU
	protected void pointerPressed(int x, int y) {
			
		if (x >= w/5 && x <= w/5+40 && y >= (h*8)/10-10 && y <= (h*8)/10-10+20) mainApp.exitApp();
		else if (x >= (w*8)/10-40 && x <= (w*8)/10 && y >= (h*8)/10-10 && y <= (h*8)/10-10+20) {
			if (mainMenu == null) mainMenu = new MainMenu(mainApp);
			mainApp.show(mainMenu);
		}
		
	}
}
