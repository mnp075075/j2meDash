import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;

/*

  888888  .d8888b.  888b     d888 8888888888 8888888b.        d8888  .d8888b.  888    888 
    "88b d88P  Y88b 8888b   d8888 888        888  "Y88b      d88888 d88P  Y88b 888    888 
     888        888 88888b.d88888 888        888    888     d88P888 Y88b.      888    888 
     888      .d88P 888Y88888P888 8888888    888    888    d88P 888  "Y888b.   8888888888 
     888  .od888P"  888 Y888P 888 888        888    888   d88P  888     "Y88b. 888    888 
     888 d88P"      888  Y8P  888 888        888    888  d88P   888       "888 888    888 
     88P 888"       888   "   888 888        888  .d88P d8888888888 Y88b  d88P 888    888 
     888 888888888  888       888 8888888888 8888888P" d88P     888  "Y8888P"  888    888 
   .d88P                                                                                  
 .d88P"                                                                                   
888P"   
                                                                                  
*/

public class TransitionScreen extends GameCanvas implements Runnable {
	
	private j2meDash mainApp;
	
	public TransitionScreen(j2meDash mainApp) {
		
		super(true);
		this.mainApp = mainApp;
		
	}

	Thread t;
	int number[] = {255, 205, 155, 105, 55, 5};
	boolean isRunning = true;
	// boolean haveRan = false;
	Display d;
	
	public void showNotify() {
		Thread t = new Thread(this);
		t.start();
	}
	
	public void hideNotify() {
		Thread t = null;
	}
	
	public void run() {
		
		Graphics g = getGraphics();
		
		System.out.println("isShown: " + isShown());
		for (int i = 0; i < 6; i++) {
			
			System.out.println("number i: " + i);
			
			g.setColor(number[i], number[i], number[i]);
			g.fillRect(0,0,240,400);
			
			flushGraphics();
			
			try {
				Thread.sleep(64);
			} catch (Exception e) {
				Thread.currentThread().interrupt();
			}
			
		}
		
		for (int j = 4; j > -1; j--) {
			
			System.out.println("number j: " + j);
			
			g.setColor(number[j], number[j], number[j]);
			g.fillRect(0,0,240,400);
			
			flushGraphics();
			
			try {
				Thread.sleep(64);
			} catch (Exception e) {
				Thread.currentThread().interrupt();
			}
		}
		
		System.out.println("switching");
		
		mainApp.changeScreen();
		// serviceRepaints();
		System.out.println("worked, name: " + Display.getDisplay(mainApp).getCurrent());
		
		while (this.isRunning == true) {
			System.out.println("screen name: " + Display.getDisplay(mainApp).getCurrent());
			
			try {
				Thread.sleep(5000);
			} catch (Exception e) {
				
			}
		}
	}
}