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

public class WarningScreen extends GameCanvas implements Runnable {
	
	private MainApp mainApp;
	private SoundMenu soundMenu;
	private Image warning;
	private Graphics g = getGraphics();

	public static int fadeforward; // also for the splash screen class
	public static int seconds = 5; // once again also for the splash screen class
	private Thread t;
	
	public void showNotify() {
		t = new Thread(this);
		t.start();
	}
	
	public void hideNotify() {
		t = null;
	}
	
	public WarningScreen(MainApp mainApp) {
		
		super(true);
		
		this.mainApp = mainApp;
		try {
			warning = Image.createImage("/rsc/img/warning.png");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		int w = getWidth();
		int h = getHeight();
		g.setColor(0,0,0);
		g.fillRect(0,0,w,h);
		
		if (warning != null) {
			g.drawImage(warning, w/2, h/4, Graphics.HCENTER | Graphics.VCENTER);
		}
		
		g.setColor(255,255,255);
		g.drawString("Warning: This game is", (int)w/2, (int)h/2, Graphics.HCENTER | Graphics.BASELINE);
		g.drawString("not made by RobTop Games", (int)w/2, (int)h/2+20, Graphics.HCENTER | Graphics.BASELINE);
		
		while (seconds > 0) {
			g.setColor(0,0,0);
			g.fillRect(0,h/2+50,w,h/2+70);
			g.setColor(255,255,255);
			g.drawString("This screen will close in: " + seconds, (int)w/2, (int)h/2+60, Graphics.HCENTER | Graphics.BASELINE);
			flushGraphics();
			seconds--;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		if (seconds == 0) {
			soundMenu = new SoundMenu(mainApp);
			mainApp.show(soundMenu);
		}
		
	}

}