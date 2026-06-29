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
	Image robert_topala; // jumpscare element
	
	// Display display = Display.getDisplay(mainApp);
	
	// private SoundMenu soundMenu = new SoundMenu(mainApp);
	
	// constructor
	public SplashScreen(MainApp mainApp) {
		super(true);
		// new Exception("TRACE").printStackTrace();
		// System.err.println("GEOMETRY DASH IS THE BEST GAME ON EARTH");
		
		this.mainApp = mainApp;
		
		try {
			robert_topala = Image.createImage("img/roberttopala.png");
		} catch (Exception e) {
			System.err.println();
		}
		
	}	
	
	// thread
	public void THREAD() {
		
		System.out.println("running thread");
		
		new Thread(new Runnable() {
			public void run() {
				
				Graphics g = getGraphics();
				
				for (int fadeforward = 0; fadeforward < 255; fadeforward++) {
	
					g.setColor(fadeforward,fadeforward,fadeforward);
					g.fillRect(0,0,240,400);
					flushGraphics();
					g.drawImage(robert_topala, 0, 0, Graphics.LEFT | Graphics.TOP);
					flushGraphics();
			
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						System.err.println("nah");
					}
				}
				
				mainApp.showSoundMenu();
			}
		}).start();
	}
		
			

	// graphics are not useful here
	// this part is useless since thread already handled everything
	// that includes Graphics g and thread.sleep
	
	// graphics again
	/* public void paint(Graphics g) {
		// nothing is in here
		// no use case
	} */
		
}