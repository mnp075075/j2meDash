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

public class SplashScreen extends GameCanvas implements Runnable {
	
	private MainApp mainApp;
	private WarningScreen warningScreen;
	private Thread t;
	private boolean isRunning = false;
	private Image java_logo;
	
	// constructor
	public SplashScreen(MainApp mainApp) {
		super(true);
		
		this.mainApp = mainApp;
		
		try {
			java_logo = Image.createImage("/rsc/img/javalogo.png");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}	
	
	public void showNotify() {
        isRunning = true;
        t = new Thread(this);
        t.start();
    }

    public void hideNotify() {
        isRunning = false;
        t = null;
    }
	
	public void run() {
		int h = getHeight();
		int w = getWidth();
		Graphics g = getGraphics();
		g.drawImage(java_logo, (int)w/2, (int)h/2, g.VCENTER | g.HCENTER);
		try {
			Thread.sleep(3000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		warningScreen = new WarningScreen(mainApp);
		mainApp.show(warningScreen);
	}
}