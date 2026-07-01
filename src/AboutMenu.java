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

public class AboutMenu extends GameCanvas {

private j2meDash mainApp;
Display display = Display.getDisplay(mainApp);

private MainMenu mainMenu = new MainMenu(mainApp);
/* 
 * the aboutMenu
 * aboutMenu itself just tells the information about the game and its original creator
 * shoutout to RobTop Games, better known as Robert Topala for originally making:
 * GEOMETRY DASH (for PC and Mobile)
 * also J2ME helped me make this project possible
 */
	
	public AboutMenu(j2meDash mainApp) {
		super(true); // REQUIRED
		
		this.mainApp = mainApp;
	}

	// GUI FOR ABOUT MENU
	public void paint(Graphics g) {
		
		g.setColor(0,173,119);
		g.fillRect(0, 0, 240, 400);
		g.setColor(255,255,255);
		g.drawString("About Us", 10, 30, Graphics.LEFT | Graphics.TOP);
		g.drawString("This game is inspired from:", 10, 60, Graphics.LEFT | Graphics.TOP);
		g.drawString("Geometry Dash", 10, 75, Graphics.LEFT | Graphics.TOP );
		g.drawString("Which is originally made by:", 10, 90, Graphics.LEFT | Graphics.TOP);
		g.drawString("RobTop Games AB", 10, 105, Graphics.LEFT | Graphics.TOP);
		g.drawString("This game is made possible using J2ME", 10, 135, Graphics.LEFT | Graphics.TOP);
		g.drawString("Java and Java Micro Edition are", 10, 150, Graphics.LEFT | Graphics.TOP);
		g.drawString("property of: Oracle Corporation", 10, 175, Graphics.LEFT | Graphics.TOP);
		g.drawString("and Sun Microsystems", 10, 190, Graphics.LEFT | Graphics.TOP);

		g.setColor(0,85,85);
		g.drawRect(99, 249, 41, 31);
		g.setColor(0, 206, 119);
		g.fillRect(100, 250, 40, 30);
		g.setColor(255,255,255);
		g.drawString("OK", 120, 265, Graphics.HCENTER | Graphics.TOP);
	}
	
	// BUTTONS FOR ABOUT MENU
	protected void pointerPressed(int x, int y) {
		
		if (x >= 100 && x <= 140 && y >= 250 && y <= 280) {
			
			display.setCurrent(mainMenu);
			mainMenu.repaint();
			
		}
		
	}
	
}