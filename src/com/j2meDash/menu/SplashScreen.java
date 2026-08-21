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

public class SplashScreen extends GameCanvas {
	
	private MainApp mainApp;
	Image java_logo; // jumpscare element
	
	// constructor
	public SplashScreen(MainApp mainApp) {
		super(true);
		
		this.mainApp = mainApp;
		
		try {
			java_logo = Image.createImage("rsc/img/javalogo.png");
		} catch (Exception e) {
			System.err.println();
		}
		
	}	
	
	// thread
	public void threading() {
		
		System.out.println("running thread");

		new Thread(new Runnable() {
			public void run() {
				Graphics g = getGraphics();
				g.setColor(0x000000); // black background
				g.fillRect(0, 0, 240, 400);
				flushGraphics();

				mainApp.sleepFor(2000);

				g.drawImage(java_logo, 120, 200, Graphics.HCENTER | Graphics.VCENTER);
				flushGraphics();

				mainApp.sleepFor(4000);

				mainApp.showWarningScreen();
			}
		}).start();
	}
		
}