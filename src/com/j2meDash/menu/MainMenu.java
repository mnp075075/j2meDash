package com.j2meDash.menu;
import com.j2meDash.game.*;
import com.j2meDash.game.tools.*;
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

public class MainMenu extends GameCanvas implements Runnable {
	private Image background;
	private Image foreground;
	private Image logo;
	private Image logo2;
	private Image close;
	private Image options;
	private Image play;
	private Thread t;
	private volatile boolean running = false;

	private MainApp mainApp;
	private ExitMenu exitMenu;
	private SoundMenu soundMenu;
	private TimerScreen timerScreen;
	private AboutMenu aboutMenu;
	private SpeedForm speedForm;
	private PlayScreen playScreen;
	private fpsController fpsController = new fpsController();
	
	private int w = getWidth();
	private int h = getHeight();
	public static int reductionA = 0;
	public static int reductionB = 0;
	
	public MainMenu(MainApp mainApp) { // the constructor
		super(true); // REQUIRED
		
		this.mainApp = mainApp;
	
		// IMAGE
		try {
			background = Image.createImage("/rsc/general/background.png");
			foreground = Image.createImage("/rsc/general/foreground.png");
			logo = Image.createImage("/rsc/general/logo.png");
			logo2 = Image.createImage("/rsc/general/logo2.png");

			close = Image.createImage("/rsc/general/close.png");
			options = Image.createImage("/rsc/general/options.png");
			play = Image.createImage("/rsc/general/play.png");
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
			if (reductionA <= w) reductionA += 1;
			else reductionA = 0;
			if (reductionB <= w) reductionB += 2;
			else reductionB = 0;

			g.drawImage(background, 0-reductionA, 0, Graphics.LEFT | Graphics.TOP);
			g.drawImage(background, w-reductionA, 0, Graphics.LEFT | Graphics.TOP);
			g.drawImage(foreground, 0-reductionB, (int)(0.9*h), Graphics.LEFT | Graphics.TOP);
			g.drawImage(foreground, w-reductionB, (int)(0.9*h), Graphics.LEFT | Graphics.TOP);
			g.drawImage(logo, w/2, h/4, Graphics.HCENTER | Graphics.VCENTER);
			g.drawImage(logo2, w/2, (int)(0.9*h), Graphics.HCENTER | Graphics.VCENTER);

			g.drawImage(play, w/2, (int)(h/1.75), Graphics.HCENTER | Graphics.VCENTER);
			g.drawImage(close, 0, 0, Graphics.LEFT | Graphics.TOP);
			g.drawImage(options, w, 0, Graphics.RIGHT | Graphics.TOP);

			flushGraphics();
		}
	}

	// BUTTONS FOR MAIN MENU
	protected void pointerPressed(int x, int y) {
		if (x >= 0 && x <= 40 && y >= 0 && y <= 40) {
			if (exitMenu == null) exitMenu = new ExitMenu(mainApp);
			mainApp.show(exitMenu);
		} else if (x >= (w-169)/2 && x <= (w+169)/2 && y >= (h*9)/10-10 && y <= (h*9)/10+10) {
			if (aboutMenu == null) aboutMenu = new AboutMenu(mainApp);
			mainApp.show(aboutMenu);
		}
	}
	
	protected void keyPressed(int keyCode) { 
		if (keyCode == KEY_NUM1) {
			speedForm = new SpeedForm(null);
			mainApp.show(speedForm);
		}
	}
}