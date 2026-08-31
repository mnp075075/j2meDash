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

public class GameOverScreen extends GameCanvas {
	
	private MainApp mainApp;
	// private PlayScreen playScreen;
	
	Image background;
	
	public GameOverScreen(MainApp mainApp) {
		
		super(true);
		
		this.mainApp = mainApp;
		
		try {
			background = Image.createImage("/rsc/img/background.png");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void paint(Graphics g) {
		g.drawImage(background, 0, 0, Graphics.LEFT | Graphics.TOP);
		// g.setColor(0,0,0);
		// g.fillRect(0,0,240,400);
		g.drawString("GAME OVER", 120, 200, Graphics.HCENTER | Graphics.BASELINE);
		g.drawString("Returning to mainMenu in 5 seconds", 120, 215, Graphics.HCENTER | Graphics.BASELINE);
		g.drawString("Returning to mainMenu in 5 seconds", 120, 215, Graphics.HCENTER | Graphics.BASELINE);
		
		serviceRepaints();
		
		try {
			Thread.sleep(5000);
		} catch (Exception e) {
			// nothing
		}
		
		mainApp.showMainMenu();
		
	}
	
}