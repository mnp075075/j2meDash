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
	Image warning;
	Graphics g = getGraphics();
	
	public static boolean haventOpened; // for the splash screen class, no idea for its name
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
		// System.out.println("created mainApp");
		// System.out.println("mainApp: " + mainApp);
		
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
		g.drawString("bad on purpose but who cares", (int)w/2, (int)h/2+20, Graphics.HCENTER | Graphics.BASELINE);
		g.drawString("you have been warned", (int)w/2, (int)h/2+40, Graphics.HCENTER | Graphics.BASELINE);
		
		
		while (seconds > 0) {
			g.setColor(0,0,0);
			g.fillRect(0,235,240,250);
			g.setColor(255,255,255);
			g.drawString("this warning will close in: " + this.seconds, (int)w/2, (int)h/2+60, Graphics.HCENTER | Graphics.BASELINE);
			flushGraphics();
			this.haventOpened = true;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				System.out.println("nah");
			}
			this.seconds--;
		}
		
		if (this.seconds == 0) {
			if (this.haventOpened == true) {
				soundMenu = new SoundMenu(mainApp);
				mainApp.show(soundMenu);
				this.haventOpened = false;
			} else {
				// nothing
			}
		}
		
	}

}