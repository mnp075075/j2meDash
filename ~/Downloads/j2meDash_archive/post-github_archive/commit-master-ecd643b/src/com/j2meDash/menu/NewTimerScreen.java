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

public class NewTimerScreen extends GameCanvas implements Runnable {
	
	private MainApp mainApp;
	// private MainMenu mainMenu = new MainMenu(mainApp);
	// Display display = Display.getDisplay(mainApp);
	Image background1;
	
	public NewTimerScreen(MainApp mainApp) {
		super(true);
		
		this.mainApp = mainApp;
		try {
			background1 = Image.createImage("rsc/img/background.png");
		} catch (Exception e) {
			// nothing
		}
	}
	
	private int secondCount = 0;
	private int minuteCount = 0;
	private int hourCount = 0;
	private int dayCount = 0;
	private int weekCount = 0;
	private int fortnightCount = 0;
	private int doubleFortnightCount = 0;
	private int quadrupleFortnightCount = 0;
	private int octupleFortnightCount = 0;
	private int sexdecupleFortnightCount = 0;
	private int duotrigintupleFortnightCount = 0;
	private int quattorsexagintupleFortnightCount = 0;
	private int octoviginticentupleFortnightCount = 0;
	private int sexquinquagintaducentupleFortnightCount = 0;
	/* private int duodecimquingentupleFortnightCount = 0;
	 * private int quattuorvigintimillupleFortnightCount = 0;
	 * private int octoquadragintamiliadoubleFortnightCount = 0;
	 * private int sexnonagintamiliaquadrupleFortnightCount = 0;
	 * private int duononagintacentummiliaoctupleFortnightCount = 0;
	 * private int quattuoroctogintatrecentimiliasedecupleFortnightCount = 0;
	 * private int octosexagintaseptingentimiliaduotrigintupleFortnightCount = 0;
	 * private int sextrigintatrecentimiliaquinquesexagintupleFortnightCount = 0;
	 * private int sexnonagintasexcentimiliaduodequadragintatrecentamiliacentenamiliesquadrupleFortnightCount = 0;
	 */ 
	private volatile boolean isRunning;
	private Thread t;
	
	// very important
	public void showNotify() {
		isRunning = true;
		t = new Thread(this);
		t.start();
	}
	
	// as important as above
	public void hideNotify() {
		isRunning = false;
		t = null;
	}
	
	public void run() {
		
		Graphics g = getGraphics();

		while (isRunning == true) {
		
			Font font3 = Font.getFont(Font.FACE_MONOSPACE, Font.STYLE_PLAIN, Font.SIZE_SMALL);
			
			// this is so weird
			// who would have thought to code a timer inside a game :skull_emoji:
			
			g.setFont(font3);
			g.drawImage(background1,0,0,Graphics.LEFT | Graphics.TOP);
			g.setColor(0,0,0);
			g.fillRect(0,300,100,50);
			g.setColor(255,255,255);
			g.fillRect(0,0,240,160);
			g.setColor(0,0,0);
			g.drawString("Time spent in newTimerScreen:", 0, 0, Graphics.LEFT | Graphics.TOP);
			g.drawString(secondCount + " second (s)", 0, 10, Graphics.LEFT | Graphics.TOP);
			g.drawString(minuteCount + " minute (s)", 0, 20, Graphics.LEFT | Graphics.TOP);
			g.drawString(hourCount + " hour (s)", 0, 30, Graphics.LEFT | Graphics.TOP);
			g.drawString(dayCount + " day (s)", 0, 40, Graphics.LEFT | Graphics.TOP);
			g.drawString(weekCount + " week (s)", 0, 50, Graphics.LEFT | Graphics.TOP);
			g.drawString(fortnightCount + " fortnight (s)", 0, 60, Graphics.LEFT | Graphics.TOP);	
			g.drawString(doubleFortnightCount + " 2 fortnight (s)", 0 ,70, Graphics.LEFT | Graphics.TOP);
			g.drawString(quadrupleFortnightCount + " 4 fortnight (s)", 0, 80, Graphics.LEFT | Graphics.TOP);
			g.drawString(octupleFortnightCount + " 8 fortnight (s)", 0, 90, Graphics.LEFT | Graphics.TOP);
			g.drawString(sexdecupleFortnightCount + " 16 fortnight (s)", 0, 100, Graphics.LEFT | Graphics.TOP);
			g.drawString(duotrigintupleFortnightCount + " 32 fortnight (s)", 0, 110, Graphics.LEFT | Graphics.TOP);
			g.drawString(quattorsexagintupleFortnightCount + " 64 fortnight (s)", 0, 120, Graphics.LEFT | Graphics.TOP);
			g.drawString(octoviginticentupleFortnightCount + " 128 fortnight (s)", 0, 130, Graphics.LEFT | Graphics.TOP);
			g.drawString(sexquinquagintaducentupleFortnightCount + " 256 fortnight (s)", 0, 140, Graphics.LEFT | Graphics.TOP);
			
			/* you can add the other one if you want to
			
			* too tired for this
			
			*/
			
			flushGraphics();
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				break;
			}
			
			// VERY IMPORTANT
			if (!isRunning) {
				break;
			}
			
			secondCount++;
			
			if (secondCount % 60 == 0) {
				secondCount = 0;
				minuteCount++;
			
				if (minuteCount % 60 == 0) {
					minuteCount = 0;
					hourCount++;

					if (hourCount % 24 == 0) {
						hourCount = 0;
						dayCount++;
			
						if (dayCount % 7 == 0) {
							dayCount = 0;
							weekCount++;
			
							if (weekCount % 2 == 0) {
								weekCount = 0;
								fortnightCount++;
							
								if (fortnightCount % 2 == 0) {
									fortnightCount = 0;
									doubleFortnightCount++;
									
									if (doubleFortnightCount % 2 == 0) {
										doubleFortnightCount = 0;
										quadrupleFortnightCount++;
										
										if (quadrupleFortnightCount % 2 == 0) {
											quadrupleFortnightCount = 0;
											octupleFortnightCount++;
											
											if (octupleFortnightCount % 2 == 0) {
												octupleFortnightCount = 0;
												sexdecupleFortnightCount++;
												
												if (sexdecupleFortnightCount % 2 == 0) {
													sexdecupleFortnightCount = 0;
													duotrigintupleFortnightCount++;
													
													if (duotrigintupleFortnightCount % 2 == 0) {
														duotrigintupleFortnightCount = 0;
														quattorsexagintupleFortnightCount++;
														
														if (quattorsexagintupleFortnightCount % 2 == 0) {
															quattorsexagintupleFortnightCount = 0;
															octoviginticentupleFortnightCount++;
															
															if (octoviginticentupleFortnightCount % 2 == 0) {
																octoviginticentupleFortnightCount = 0;
																sexquinquagintaducentupleFortnightCount++;
																
																if (sexquinquagintaducentupleFortnightCount % 2 == 0) {
																	g.setColor(0,0,0);
																	g.drawString("CONGRATS USER, YOU HAVE WASTED TIME", 120, 200, Graphics.HCENTER | Graphics.TOP);
																	flushGraphics();
																	
																	try {
																		Thread.sleep(5000);
																	} catch (InterruptedException e) {
																		// nothing
																	}
																	
																	mainApp.exitApp();
																	System.out.println("go outside dude");
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}	
		}
	}
	
	protected void pointerPressed(int x, int y) {
		
		if (x >= 0 && x <= 100 && y >= 300 && y <= 350) {
			isRunning = false;
			mainApp.showMainMenu();
		}
		
	}
		
		// timer.schedule(task, 0, 1000);
		
}