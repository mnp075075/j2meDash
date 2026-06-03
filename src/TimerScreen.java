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

public class TimerScreen extends GameCanvas {
	
	private j2meDash mainApp;
	
	public TimerScreen(j2meDash mainApp) {
		super(true);
		
		this.mainApp = mainApp;
	}
	
	public void paint(Graphics g) {
		long lastTime = System.currentTimeMillis();
		
		while (mainApp.running == true) {
			
			g.setColor(255,255,255);
			g.fillRect(0,0,240,400);
		
			g.setColor(0,0,0);
			g.drawString("TIMER", 0,0, Graphics.LEFT | Graphics.TOP);
			
			long now = System.currentTimeMillis();
			long workTime = now - lastTime;
			long sleepTime = now - workTime;
			
			g.setColor(255,255,255);
			g.fillRect(0,385,200,400);
			flushGraphics();
			
			g.setColor(0,0,0);
			g.drawString("Time spent in MainMenu: ", 0, 400, Graphics.LEFT | Graphics.BOTTOM);
			g.drawString("" + mainApp.time, 160, 400, Graphics.LEFT | Graphics.BOTTOM);
			flushGraphics();
			
			if (sleepTime > 0) {
				try {
					Thread.sleep(sleepTime);
				} catch (InterruptedException e) {
					break;
				}
			} else {
				// nothing
			}
			
			mainApp.time++;
			g.setColor(255,255,255);
			g.fillRect(0,385,200,400);
			repaint();
			serviceRepaints();
		}
	}
}