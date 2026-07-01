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
	
	private j2meDash mainApp;
	// Display display = Display.getDisplay(mainApp);
	// private SplashScreen splashScreen = new SplashScreen(mainApp);
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
	
	public WarningScreen(j2meDash mainApp) {
		
		super(true);
		
		this.mainApp = mainApp;
		// System.out.println("created mainApp");
		// System.out.println("mainApp: " + mainApp);
		
		try {
			warning = Image.createImage("assets/warning.png");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		
		// System.out.println("triggered");
		g.setColor(0,0,0);
		g.fillRect(0,0,240,400);
		
		if (warning != null) {
			g.drawImage(warning, 120, 100, Graphics.HCENTER | Graphics.VCENTER);
		}
		
		g.setColor(255,255,255);
		g.drawString("Warning: This game is", 120, 200, Graphics.HCENTER | Graphics.TOP);
		g.drawString("bad on purpose but who cares", 120, 215, Graphics.HCENTER | Graphics.TOP);
		g.drawString("you have been warned", 120, 230, Graphics.HCENTER | Graphics.TOP);
		while (seconds > 0) {
			g.setColor(0,0,0);
			g.fillRect(0,235,240,250);
			g.setColor(255,255,255);
			g.drawString("this warning will close in: " + this.seconds, 120, 245, Graphics.HCENTER | Graphics.TOP);
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
				// splashScreen.splashScreen = new splashScreen.SplashScreen();
				
				// System.out.println("mainApp: " + mainApp);
				mainApp.showSplashScreen();
				this.haventOpened = false;
			} else {
				// nothing
			}
		}
		
		
		
	}

}