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

public class NewTimerScreen extends GameCanvas implements Runnable {
	
	private MainApp mainApp;
	private MainMenu mainMenu;
	Image background1;
	
	public NewTimerScreen(MainApp mainApp) {
		super(true);
		
		this.mainApp = mainApp;
		try {
			background1 = Image.createImage("/rsc/img/background.png");
		} catch (Exception e) {
			// nothing
		}
	}
	
	private int secondCount = 0;
	private int minuteCount = 0;
	private int hourCount = 0;
	private volatile boolean isRunning;
	private Thread t;
	
	// very important
	public void showNotify() {
		isRunning = true;
		t = new Thread(this);
		t.start();
	}
	
	// as important as above
	public void hideNotify() {
		isRunning = false;
		t = null;
	}
	
	public void run() {
		
		Graphics g = getGraphics();

		while (isRunning == true) {
			g.drawImage(background1,0,0,Graphics.LEFT | Graphics.TOP);
			g.setColor(0,0,0);
			g.fillRect(0,300,100,50);
			g.setColor(255,255,255);
			g.fillRect(0,0,240,160);
			g.setColor(0,0,0);
			g.drawString("Time spent in newTimerScreen:", 0, 0, Graphics.LEFT | Graphics.TOP);
			g.drawString(secondCount + " second (s)", 0, 10, Graphics.LEFT | Graphics.TOP);
			g.drawString(minuteCount + " minute (s)", 0, 20, Graphics.LEFT | Graphics.TOP);
			g.drawString(hourCount + " hour (s)", 0, 30, Graphics.LEFT | Graphics.TOP);
			
			flushGraphics();
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				break;
			}
			if (!isRunning) {
				break;
			}
			secondCount++;
			
			if (secondCount % 60 == 0) {
				secondCount = 0;
				minuteCount++;
				if (minuteCount % 60 == 0) {
					minuteCount = 0;
					hourCount++;
				}
			}
		}
	}		
	
	protected void pointerPressed(int x, int y) {
		
		if (x >= 0 && x <= 100 && y >= 300 && y <= 350) {
			isRunning = false;
			mainMenu = new MainMenu(mainApp);
			mainApp.show(mainMenu);
		}
		
	}
		
}