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

public class PauseScreen extends GameCanvas {
	
	private j2meDash mainApp;
	private PlayScreen playScreen;
	
	Image background;
	
	public PauseScreen(j2meDash mainApp) {
		
		super(true);
		this.mainApp = mainApp;
		// this.playScreen = playScreen;
		
		try {
			background = Image.createImage("assets/background.png");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void paint(Graphics g) {
		
		g.drawImage(background, 0, 0, Graphics.LEFT | Graphics.TOP);
		
		g.setColor(100,100,100);
		g.fillRect(30, 30, 180, 340);
		
		g.setColor(255,255,255);
		g.drawString("Do you want to exit game?", 120, 60, Graphics.HCENTER | Graphics.VCENTER);
		
		g.setColor(50,50,50);
		g.fillRect(100, 100, 40, 30);
		g.fillRect(100, 200, 40, 30);
		g.fillRect(100, 300, 40, 30);
		
		g.setColor(255,255,255);
		g.drawString("Yes", 120, 115, Graphics.HCENTER | Graphics.VCENTER);
		g.drawString("No", 120, 215, Graphics.HCENTER | Graphics.VCENTER);
		g.drawString("Restart", 120, 315, Graphics.HCENTER | Graphics.VCENTER);
		
	}
	
	protected void pointerPressed(int x, int y) {
		if (x >= 100 && y >= 100 && x <= 140 && y <= 130) {
			
			mainApp.showMainMenu();
			
		} else if (x >= 100 && y >= 200 && x <= 140 && y <= 230) {
			
			mainApp.showPlayScreen();
			
		} else if (x >= 100 && y >= 300 && x <= 140 && y <= 330) {
			
			restartPlayScreen(true);
			mainApp.showPlayScreen();
			
		}
	}
	
	public void restartPlayScreen(boolean restart) {
		
		if (playScreen == null) {
			playScreen = new PlayScreen(mainApp);
		}
		
		playScreen.iForCube = 21;
		playScreen.jForForeground = 240;
		playScreen.kForSpikeX = 240;
		playScreen.kForSpikeY = 287;
		
		playScreen.jumpFrame = 0;
		playScreen.jumpHeight = 0;
		playScreen.jumpTick = 0;
				
		playScreen.isRunning = false;
		
	}
	
}