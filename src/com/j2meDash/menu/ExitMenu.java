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

public class ExitMenu extends GameCanvas {
	
/*
 * the exitMenu just do what it said
 * confirms the user if they want to exit the game
 */
	
	private MainApp mainApp;
	
	// MainMenu mainMenu = new MainMenu(mainApp);
	
	Display display = Display.getDisplay(mainApp);
	Image EXIT; // NORMAL IMAGE
	
	public ExitMenu(MainApp mainApp) {
		super(true); // REQUIRED
		
		// IMAGE AND AUDIO FOR EXIT MENU
		
		this.mainApp = mainApp;
		
		try {
			EXIT = Image.createImage("/rsc/img/cubeRotate0.png");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("NO IMAGE NAMED cubeRotate0.png (perhaps you forgot .png isn't .PNG)");
		}
	}

// THAT EXIT MENU LITERALLY DO NOTHING
// not anymore

	// GUI FOR EXIT MENU
	public void paint(Graphics g) {

		g.setColor(0,173,119);
		g.fillRect(0, 0, 240, 400);
		g.drawString("Do you want to exit?", 120, 30, Graphics.BASELINE | Graphics.HCENTER);
		g.drawString("SIDE NOTE:", 120, 60, Graphics.BASELINE | Graphics.HCENTER);
		g.drawString("SIDE NOTE:", 120, 60, Graphics.BASELINE | Graphics.HCENTER);
		g.drawString("Rage quiting is not my problem", 10, 75, Graphics.LEFT | Graphics.TOP);
		g.drawString("The issue here is that:", 10, 90, Graphics.LEFT | Graphics.TOP);
		g.drawString("### You're not skilled enough ###", 10, 105, Graphics.LEFT | Graphics.TOP);
		g.drawString("You can try the original Geometry", 10, 120, Graphics.LEFT | Graphics.TOP);
		g.drawString("Dash made by RobTop AB. There are", 10, 135, Graphics.LEFT | Graphics.TOP);
		g.drawString("main levels for you to try. Maybe", 10, 150, Graphics.LEFT | Graphics.TOP);
		g.drawString("try Stereo Madness and onwards,", 10, 165, Graphics.LEFT | Graphics.TOP);
		g.drawString("and you'll eventually leveled up!", 10, 180, Graphics.LEFT | Graphics.TOP);
		g.drawString("Nonetheless, hope you the best!", 10, 210, Graphics.LEFT | Graphics.TOP);
		g.drawString("Sincerely, idk", 10, 225, Graphics.LEFT | Graphics.TOP);
		g.setColor(0,206,119);
		g.fillRect(40, 255, 40, 20);
		g.fillRect(160, 255, 40, 20);
		g.drawString("Exit", 60, 265, Graphics.HCENTER | Graphics.BASELINE);
		g.drawString("Cancel", 180, 265, Graphics.HCENTER | Graphics.BASELINE);
		g.drawString("Cancel", 180, 265, Graphics.HCENTER | Graphics.BASELINE);
		g.setColor(0,173,119);
		
		if (EXIT != null) {
			g.drawImage(EXIT, 120, 310, Graphics.HCENTER | Graphics.VCENTER);
		} else {
			g.drawString("???", 120, 310, Graphics.HCENTER | Graphics.BASELINE);
		}

	}
		
	// BUTTONS FOR EXIT MENU
	protected void pointerPressed(int x, int y) {
			
		if (x >= 40 && x <= 80 && y >= 255 && y <= 275) {
			mainApp.exitApp();
		} else if (x >=  160 && x <= 200 && y >= 255 && y <= 275) {
			mainApp.showMainMenu();
		}
		
	}
}
