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
	
	private MainApp mainApp;
	private MainMenu mainMenu;
	
	private int w = getWidth();
	private int h = getHeight();
	
	Display display = Display.getDisplay(mainApp);
	
	public ExitMenu(MainApp mainApp) {
		super(true); // REQUIRED
		
		this.mainApp = mainApp;
	}

	// GUI FOR EXIT MENU
	public void paint(Graphics g) {
		int fontOffset = (int) (20-g.getFont().getHeight())/2;
		Font f1 = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_LARGE);
		Font f2 = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL);
		g.setFont(f1);
		g.setColor(0x0080ff);
		g.fillRect(0, 0, w, h);
		g.setColor(0xffffff);
		g.drawString("Do you want to exit?", (int)Math.round(w/2), (int)Math.round(h/2), Graphics.BASELINE | Graphics.HCENTER);
		g.setFont(f2);
		g.drawString("Do you want to exit? (double-check)", (int)Math.round(w/2), (int)Math.round(h/2)+15, Graphics.BASELINE | Graphics.HCENTER);

		g.setColor(0x005ebb);
		g.fillRect((int)Math.round(w*0.2), (int)Math.round(h*0.8)-10, 40, 20);
		g.fillRect((int)Math.round(w*0.8)-40, (int)Math.round(h*0.8)-10, 40, 20);
		g.setColor(0xffffff);
		g.drawString("Exit", (int)Math.round(w*0.2)+20, (int)Math.round(h*0.8)+fontOffset, Graphics.BASELINE | Graphics.HCENTER);
		g.drawString("Cancel", (int)Math.round(w*0.8)-20, (int)Math.round(h*0.8)+fontOffset, Graphics.BASELINE | Graphics.HCENTER);
	}
		
	// BUTTONS FOR EXIT MENU
	protected void pointerPressed(int x, int y) {
			
		if (x >= (int)Math.round(w*0.2) && x <= (int)Math.round(w*0.2)+40 && y >= (int)Math.round(h*0.8)-10 && y <= (int)Math.round(h*0.8)-10+20) {
			mainApp.exitApp();
		} else if (x >= (int)Math.round(w*0.8)-40 && x <= (int)Math.round(w*0.8) && y >= (int)Math.round(h*0.8)-10 && y <= (int)Math.round(h*0.8)-10+20) {
			mainMenu = new MainMenu(mainApp);
			mainApp.show(mainMenu);
		}
		
	}
}
