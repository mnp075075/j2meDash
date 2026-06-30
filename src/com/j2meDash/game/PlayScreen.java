package com.j2meDash.game;
import com.j2meDash.main.*;
import com.j2meDash.menu.*;
import com.j2meDash.pars.*;
import com.j2meDash.temp.*;
import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;
import javax.microedition.io.file.*;
import javax.microedition.io.Connector;
import java.io.*;

/*

 ██████╗ ██████╗  ██████╗ ███╗   ██╗     ██╗ █████╗ ██╗   ██╗ █████╗  ██╗██████╗  ██████╗ ████████╗██╗  ██╗
██╔════╝ ██╔══██╗██╔═══██╗████╗  ██║     ██║██╔══██╗██║   ██║██╔══██╗███║██╔══██╗██╔═══██╗╚══██╔══╝██║  ██║
██║  ███╗██║  ██║██║   ██║██╔██╗ ██║     ██║███████║██║   ██║███████║╚██║██║  ██║██║   ██║   ██║   ███████║
██║   ██║██║  ██║██║   ██║██║╚██╗██║██   ██║██╔══██║╚██╗ ██╔╝██╔══██║ ██║██║  ██║██║   ██║   ██║   ╚════██║
╚██████╔╝██████╔╝╚██████╔╝██║ ╚████║╚█████╔╝██║  ██║ ╚████╔╝ ██║  ██║ ██║██████╔╝╚██████╔╝   ██║        ██║
 ╚═════╝ ╚═════╝  ╚═════╝ ╚═╝  ╚═══╝ ╚════╝ ╚═╝  ╚═╝  ╚═══╝  ╚═╝  ╚═╝ ╚═╝╚═════╝  ╚═════╝    ╚═╝        ╚═╝

*/

public class PlayScreen extends GameCanvas implements Runnable, CommandListener {

/* this is the main playScreen where you can play levels
 * it's still quite barebones so you may consider improving this
 * i'm trying to make it better
 * but for now it's just a loop of a cube and spikes
 */
	
	private MainApp mainApp;
	private GameOverScreen gameOverScreen;
	// Display display = Display.getDisplay(mainApp);
	// private MainMenu mainMenu = new MainMenu(mainApp);
	public int jumpFrame = 0;
	public int jumpHeight = 0;
	
	private DataRegistry dataRegistry = new DataRegistry(mainApp);
	private LevelBinaryParser levelBinaryParser = new LevelBinaryParser(mainApp);
	
	public byte gravityAndSizeFlags;
	public byte modeFlags;
	public byte speedFlags;
	
	public boolean haveTouched;
	
	Image background;
	Image foreground;

	Image[] objectID = new Image[37];
	Image spike1_0;

	Image[] cubeRotatePositive = new Image[10];
	Image[] cubeRotateNegative = new Image[10];
	Image cubeRotate0;
	Image sheet;

	TextBox dirLoader;

	// Sprite cube = new Sprite(cubeRotate[0]);
	public int iForCube = 21;
	public int jForForeground = 240;
	public int kForSpikeX = 240;
	public int kForSpikeY = 287;
	int xBackground = 240;
	int factor = 10;
	int parabola[] = {4,3,2,1}; // {-1,-4,-9,-16};
	
	int cubeW = 21;
	int cubeH = 21;
	
	int spikeW = 3;
	int spikeH = 7;
	public int jumpTick = 0;

	boolean varNormalGravity;
	boolean varNormalMode;

	Form FlagChooser;
	TextBox gravityAndSize;
	TextBox mode;
	TextBox speed;
	Command Back = new Command("Return", Command.BACK, 1);
	Command Save = new Command("Save", Command.OK, 1);
	Command Okay = new Command("OK", Command.OK, 1);
	Command Exit = new Command("Exit", Command.EXIT, 1);
	
	Command GravityAndSize = new Command("Gravity&Size", Command.OK, 1);
	Command Mode = new Command("Mode", Command.OK, 1);
	Command Speed = new Command("Speed", Command.OK, 1);
	
	Command OK = new Command("ok", Command.OK, 1);
	Command EXIT = new Command("exit", Command.EXIT, 1);
	
	Alert saved;
	Alert errorGas;
	Alert errorM;
	Alert errorS;
	
	Alert successfully;
	Alert failed;
	
	Ticker tForNoReason;
	// Ticker tip = new Ticker("Press 1 to access FlagChooser - Press 2 to access PauseScreen");

	// boolean mirrorMode = false;
																								// objectID
	int[][] widthAndHeight = { 	{0,0,0,0}, {21,21,21,21}, {21,21,21,21}, {21,21,21,21},			// 0,1,2,3
								{21,21,21,21}, {21,21,3,7}, {21,21,7,3}, {21,21,3,7},			// 4,5,6,7
								{21,21,7,3}, {21,21,3,2}, {21,21,2,3}, {21,21,3,2},				// 8,9,10,11
								{21,21,2,3}, {34,60,34,60}, {40,50,40,50}, {54,60,54,60},		// 12,13,14,15
								{69,60,69,60}, {73,60,73,60}, {32,60,32,60}, {32,60,32,60},		// 16,17,18,19
								{32,60,32,60}, {32,60,32,60}, {32,60,32,60}, {32,60,32,60},		// 20,21,22,23
								{32,60,32,60}, {30,60,30,60}, {30,60,30,60}, {32,60,32,60},		// 24,25,26,27
								{32,60,32,60}, {21,21,21,21}, {21,21,21,21}, {21,21,21,21},		// 28,29,30,31
								{21,21,21,21}, {21,21,21,21}, {21,21,21,21}, {21,4,21,4},		// 32,33,34,35
								{21,3,21,3}, {21,5,21,5}, {21,5,21,5}, {21,21,21,21},			// 36,37,38,39
								{21,21,21,21}	};												// 40
	
	// private DataRegistry dataRegistry;

	private Thread t1;
	protected volatile boolean isRunning = true;
	private volatile boolean isJumpingOnX;
	private volatile boolean isJumpingOnY;
	// private long lastTime = System.currentTimeMillis();
	private int tickCount = 0;
	
	public PlayScreen(MainApp mainApp) {
		
		super(true);
		
		this.mainApp = mainApp;
		
		try {
			
			
			background = Image.createImage("rsc/img/background.png");
			foreground = Image.createImage("rsc/img/foreground.png");

			/* for (int i = 0; i < 40; i++) {
				String currentPath = dataRegistry.directories[i];

				if (currentPath == null || currentPath.equals("null") || currentPath.length() == 0) {
					System.out.println("skip ID: " + i + " blank");
					continue;
				} 
				
				try {
					objectID[i] = Image.createImage(currentPath);
					System.out.println("image ID: " + i + " loaded");
				} catch (Exception e) {
					System.out.println("image ID: " + i + " not loaded");
				}
			} */

			spike1_0 = Image.createImage("rsc/img/spike1_0.png");
			sheet = Image.createImage("rsc/img/sheet.png");
			cubeRotate0 = Image.createImage("rsc/img/cubeRotate0.png");
			
			for (int i = 1; i <= 8; i++) {
				cubeRotatePositive[i] = Image.createImage("rsc/img/cubeRotate" + i + ".png"); // rotate clockwise
			}

			for (int i = 1; i <= 8; i++) {
				cubeRotateNegative[i] = Image.createImage("rsc/img/cubeRotate" + i + ".png"); // rotate counterclockwise
			}
			
		} catch (Exception e) {

			e.printStackTrace();
			
		}
		
	}

	
	
	public void showNotify() {
		isRunning = true;
		t1 = new Thread(this);
		t1.start();
	}
	
	public void hideNotify() {
		isRunning = false;
		t1 = null;
	}
	
	public void run() {
		
		// Graphics graphics = getGraphics();
		
		// setTicker(tip); - ONLY ENABLE THIS IF THE EMULATOR BEING USED TO RUN THIS MIDLET ISN'T FREEJ2ME
		System.out.println("PlayScreen is activated");
		
		// System.out.println("Running in: " + Thread.currentThread().getName());

		// this is important
		/* SPEED COUNT:
		 * 0.5x speed: 1
		 * 1x speed: 0
		 * 2x speed: 2
		 * 3x speed: 3
		 * 4x speed: 4
		 */
		 
		int tickCount = 0;
		
		while (isRunning) {
			
			if (isRunning != true) {
				break;
			} else {
				// continue
				/* NOTES:
				 * don't you dare add anything here
				 * i hate coding like this very much already
				 * just remember that
				 */
			}
			
			int spikeX = kForSpikeX - 21;
			int spikeY = kForSpikeY - 21;
			
			int cubeX = iForCube - 21;
			int cubeY = 287 - jumpHeight - 21;

			
			// this is just messy
			// long currentTime = System.currentTimeMillis();
			
			tickCount++;
			
			System.out.println("tickCount: " + tickCount + " jumpTick: " + jumpTick + " jumpFrame: " + jumpFrame + " speedCount: " + mainApp.speedCount);
			// System.out.println(" jumpTick: " + jumpTick);
			// System.out.printlb(" jumpFrame: " + jumpFrame);
			
			// System.out.println("kForSpikeX: " + kForSpikeX);
				
			Graphics g = getGraphics();
			
			if (xBackground > 0) {
				
				xBackground -= 1;
				
			} else if (xBackground == 0) {
				
				xBackground = 240;
				
			}
			
			g.drawImage(background, xBackground, 400, Graphics.RIGHT | Graphics.BOTTOM);
			g.drawImage(background, xBackground + 240, 400, Graphics.RIGHT | Graphics.BOTTOM);
			
			
			
			if (tickCount == Integer.MAX_VALUE - 1) {
				tickCount = 0;
			}
			
			if (jumpTick == Integer.MAX_VALUE - 1) {
				jumpTick = 0;
			}
			
			// the ground moves
			if (iForCube < 81) {				
				
				iForCube += 5;
				
			} else if (jForForeground > 0 && kForSpikeX > 0) {
					
				jForForeground -= 5;
				kForSpikeX -= 5;
				
			} else if (jForForeground == 0 && kForSpikeX == 0) {
				
				jForForeground = 240;
				kForSpikeX = 240;
				
			}
			
			g.drawImage(foreground, jForForeground, 425, Graphics.RIGHT | Graphics.BOTTOM);
			g.drawImage(foreground, jForForeground + 240, 425, Graphics.RIGHT | Graphics.BOTTOM);
			
			if (isJumpingOnX == true && isJumpingOnY == true) {
				
				// NOTE
				// have no idea
				// there used to be something here
				// ------------------------
				// for the first time, order matters
				// ------------------------
				// i hate animating on this
				// ------------------------
				
				// int drawIndex = (jumpFrame > 8) ? 0 : jumpFrame;
				
				/* if (cubeRotate[drawIndex] != null) {
					g.drawImage(cubeRotate[drawIndex], iForCube, 287 - jumpHeight, Graphics.RIGHT | Graphics.BOTTOM);
				} else {
					System.out.println("frame" + jumpFrame + "is missing");
				} */
				
				//  I have removed all of this part in replace for the custom method

				
				// there used to be code here but I removed it
				
				// there's a reason i put this here
				// don't ask why
				// i hate this
				
				// jumpTick++;

				// check for flags, important for configuration
				
				gravityAndSizeFlags = 0x11; // temporary
				
				switch(gravityAndSizeFlags) {
					case 0x00:
					
					varNormalGravity = false;
					varNormalMode = false;
					break;

					case 0x01:

					varNormalGravity = false;
					varNormalMode = true;
					break;

					case 0x10:
	
					varNormalGravity = true;
					varNormalMode = false;
					break;

					case 0x11:

					varNormalGravity = true;
					varNormalMode = true;
					break;

					default:
			
					System.out.println("invalid flag");
					System.out.println("please change the flag to the valid one");

					System.out.println("system will now exit the app");
				
					mainApp.exitApp();

					// break;

				}

				// check for the conditions
				if (varNormalGravity == true) {
					
					normalGravity(true);

				} else {
						
					normalGravity(false);
					
				}

				if (varNormalMode == true) {

					normalMode(true);

				} else {
		
					normalMode(false);

				}

				/* if (varNormalGravity == null || varNormalMode == null) {
					System.out.println("exiting game due to invalid boolean variable");
					destroyApp(true);
					notifyDestroyed();
				} */

				// there also used to be code here but I removed it
				
				/* if (tickCount >= 3) {
					jumpFrame++;
					jumpTick = 0;
				} */
				
				// remember switch is better than if-else for the above case
				// has got to be the most complex variable ever
				
				if (jumpFrame > 8 && isJumpingOnX == true && isJumpingOnY == true) {
					jumpHeight = 0;
					jumpFrame = 0;
					jumpTick = 0;
					// jumpTick = 0;
					// jumpTick = 0;
					isJumpingOnX = false;
					isJumpingOnY = false;
				}
				
				/* try {
					Thread.sleep(50);
				} catch (InterruptedException whatwhat) {
					// nothing
				} */
				
			} else if (isJumpingOnX == false && isJumpingOnY == false) {
				g.drawImage(cubeRotate0, iForCube, 287, Graphics.RIGHT | Graphics.BOTTOM);
				// g.setColor(255,0,0);
				// g.fillRect(kForSpikeX - 12, kForSpikeY - 11, 3, 7);
				// System.out.println("HELLO?");
				// g.fillRect(spikeX, spikeY, spikeW, spikeH);
				
				// that above is testing material
				// please do not touch
				// end
			}
			
			g.drawImage(spike1_0, kForSpikeX, kForSpikeY, Graphics.RIGHT | Graphics.BOTTOM);
			g.drawImage(spike1_0, kForSpikeX + 240, kForSpikeY, Graphics.RIGHT | Graphics.BOTTOM);
			
			/* for (int i = 0; i < 40; i++) {
				g.drawImage(objectID[i], 5*i, 300, Graphics.RIGHT | Graphics.BOTTOM);
				// break;
			} */

			flushGraphics();
			
			switch(mainApp.speedCount) {
				case 1:
				try {
					Thread.sleep(10);
				} catch (InterruptedException error1) { }
				/* case 1:
				try {
					Thread.sleep(5);
				} catch (InterruptedException error2) { } */
				break;
				case 2:
				try {
					Thread.sleep(3);
				} catch (InterruptedException error3) { }
				break;
				case 3:
				try {
					Thread.sleep(2);
				} catch (InterruptedException error4) { }
				break;
				case 4:
				try {
					Thread.sleep(1);
				} catch (InterruptedException error5) { }
				break;
				default: // case 0
				try {
					Thread.sleep(5);
				} catch (InterruptedException error) {
					// nothing
				}
				break;
			}
			
			
			if (cubeX < (kForSpikeX - 12) + spikeW &&
			cubeX + cubeW > (kForSpikeX - 12) &&
			cubeY < (kForSpikeY - 11) + spikeH &&
			cubeY + cubeH > (kForSpikeY - 11)) { // the old hitbox check
				
				haveTouched = true;
				iForCube = 21;
				jForForeground = 240;
				kForSpikeX = 240;
				kForSpikeY = 287;
				
				jumpFrame = 0;
				jumpHeight = 0;
				jumpTick = 0;
				
				isRunning = false;
				// System.out.print("\033[H\033[2J");
				// System.out.flush();
				
				mainApp.showGameOverScreen();
				
			}
			
		}
		
	}
	
	public void paint(Graphics g) {
		// nothing
	}

	public void levelDirectoryLoader() {

		dirLoader = new TextBox("Load the level data from your directory. You may use \"file:///\" to type your file name.", null, 256, TextField.ANY);
		
		String path = dirLoader.getString();
		
		successfully = new Alert("Level data found","Successfully found the level data at: " + path,null,AlertType.INFO);
		failed = new Alert("Level data missing","Cannot find the level data at: " + path,null,AlertType.INFO);

		dirLoader.addCommand(OK);
		dirLoader.addCommand(EXIT);

		dirLoader.setCommandListener(this);
		Display.getDisplay(mainApp).setCurrent(dirLoader);
		
	}
	
	public void flagChooser() {
		
		FlagChooser = new Form("Flag Value Chooser");
		FlagChooser.append("This is for changing the configuration for your gameplay");
		
		FlagChooser.addCommand(GravityAndSize);
		FlagChooser.addCommand(Mode);
		FlagChooser.addCommand(Speed);
		FlagChooser.addCommand(Exit);
		
		gravityAndSize = new TextBox("Value:", null, 2, TextField.NUMERIC);
		mode = new TextBox("Value:", null, 1, TextField.NUMERIC);
		speed = new TextBox("Value:", null, 1, TextField.NUMERIC);
		
		gravityAndSize.addCommand(Back);
		gravityAndSize.addCommand(Save);
		
		mode.addCommand(Back);
		mode.addCommand(Save);
		
		speed.addCommand(Back);
		speed.addCommand(Save);
		
		String savedMessage = "The value has been saved";
		String errorMessageGas = "The value you have typed is invalid\n" +
								 "For Gravity&Size, valid values are:\n" +
								 "0x00: flippedGravity+miniMode(int 0)\n" +
								 "0x01: flippedGravity+normalMode(int 1)\n" +
								 "0x10: normalGravity+miniMode(int 16)\n" +
								 "0x11: normalGravity+normalMode(int 17)\n";
								 
		String errorMessageM = "The value you have typed is invalid\n" +
							   "For Mode, valid values are:\n" +
							   "0x00: cube (int 0)\n 0x01: ship (int 1)\n" +
							   "0x02: ball (int 2)\n 0x03: ufo (int 3)\n" +
							   "0x04: wave (int 4)\n 0x05: robot (int 5)\n" +
							   "0x06: spider (int 6)\n 0x07: swing (unused)\n";
		
		String errorMessageS = "The value you have typed is invalid\n" +
							   "For Speed, valid values are:\n" +
							   "0x00: 1x speed (int 0)\n0x01: 0.5x speed (int 1)\n" +
							   "0x02: 2x speed (int 2)\n0x03: 3x speed (int 3)\n" +
							   "0x04: 4x speed (int 4)";
		
		saved = new Alert("Saved successfully", savedMessage, null, AlertType.INFO);
		errorGas = new Alert("Failed to save", errorMessageGas, null, AlertType.INFO);
		errorM = new Alert("Failed to save", errorMessageM, null, AlertType.INFO);
		errorS = new Alert("Failed to save", errorMessageS, null, AlertType.INFO);
		
		tForNoReason = new Ticker("YOUR COPY OF GDONJAVA HAS BEEN CONFISCATED, PLEASE RETURN THIS TO THE SELLER IMMEDIATELY");
		FlagChooser.setTicker(tForNoReason);
		
		Display.getDisplay(mainApp).setCurrent(FlagChooser);
		
		FlagChooser.setCommandListener(this);
		gravityAndSize.setCommandListener(this);
		mode.setCommandListener(this);
		speed.setCommandListener(this);
		
		
	}
	
	public void normalGravity(boolean isNormalGravity) {
		// this will handle the gravity

		Graphics g = getGraphics();
		
		if (g == null) {
			// destroyApp(true);
			// notifyDestroyed();
			
			return;
		}

		if (isNormalGravity == true) {

			if (isJumpingOnX == true && isJumpingOnY == true) {
				// something

				int drawIndexPositive = (jumpFrame > 8) ? 0 : jumpFrame;

				// System.out.println(drawIndexPositive);

				if (cubeRotatePositive[drawIndexPositive] != null) {
					g.drawImage(cubeRotatePositive[drawIndexPositive], iForCube, 287 - jumpHeight, Graphics.RIGHT | Graphics.BOTTOM);
				} else {
					System.out.println("frame" + jumpFrame + "is missing");
				}

				switch(jumpFrame) {
					
					// case 0: jumpHeight = 0; break; // i know there's a default one
				
					case 1:
				
					jumpHeight += parabola[0]; /* tickCount += -1 ;*/ 
					switch(mainApp.speedCount) {
						case 1: jumpTick++; break;
						case 2: jumpTick += 4; break;
						case 3: jumpTick += 8; break;
						case 4: jumpTick += 16; break;
						default: jumpTick += 2; break;
					}
					System.out.print("case 1 ");  
					break;
				
					case 2: 
				
					jumpHeight += parabola[1]; 
					switch(mainApp.speedCount) {
						case 1: jumpTick++; break;
						case 2: jumpTick += 4; break;
						case 3: jumpTick += 8; break;
						case 4: jumpTick += 16; break;
						default: jumpTick += 2; break;
					}
					System.out.print("case 2 ");  
					break;
				
					case 3: 
					
					jumpHeight += parabola[2]; 
					switch(mainApp.speedCount) {
						case 1: jumpTick++; break;
						case 2: jumpTick += 4; break;
						case 3: jumpTick += 8; break;
						case 4: jumpTick += 16; break;
						default: jumpTick += 2; break;
					}
					System.out.print("case 3 ");  
					break;
					
					case 4: 
					
					jumpHeight += parabola[3]; 
					switch(mainApp.speedCount) {
						case 1: jumpTick++; break;
						case 2: jumpTick += 4; break;
						case 3: jumpTick += 8; break;
						case 4: jumpTick += 16; break;
						default: jumpTick += 2; break;
					}
					System.out.print("case 4 ");  
					break;
					
					case 5: 
					
					jumpHeight -= parabola[3]; 
					switch(mainApp.speedCount) {
						case 1: jumpTick++; break;
						case 2: jumpTick += 4; break;
						case 3: jumpTick += 8; break;
						case 4: jumpTick += 16; break;
						default: jumpTick += 2; break;
					}
					System.out.print("case 5 "); 
					break;
					
					case 6: 
					
					jumpHeight -= parabola[2]; 
					switch(mainApp.speedCount) {
						case 1: jumpTick++; break;
						case 2: jumpTick += 4; break;
						case 3: jumpTick += 8; break;
						case 4: jumpTick += 16; break;
						default: jumpTick += 2; break;
					}
					System.out.print("case 6 "); 
					break;
					
					case 7: 
					
					jumpHeight -= parabola[1]; 
					switch(mainApp.speedCount) {
						case 1: jumpTick++; break;
						case 2: jumpTick += 4; break;
						case 3: jumpTick += 8; break;
						case 4: jumpTick += 16; break;
						default: jumpTick += 2; break;
					}
					System.out.print("case 7 ");  
					break;
					
					case 8: 
					
					jumpHeight -= parabola[0]; 
					switch(mainApp.speedCount) {
						case 1: jumpTick++; break;
						case 2: jumpTick += 4; break;
						case 3: jumpTick += 8; break;
						case 4: jumpTick += 16; break;
						default: jumpTick += 2; break;
					}
					System.out.print("case 8 ");  
					break;
					
					default: 
					
					jumpHeight = 0; 
					jumpTick = 0; 

					System.out.print("case 0 ");
					break; // just in case
						
				}

				
			} /* else if (isJumpingOnX == false && isJumpingOnY == false) {
				g.drawImage(cubeRotate[0], iForCube, 287, Graphics.RIGHT | Graphics.BOTTOM);
				
			} */

			switch(mainApp.speedCount) {
				case 1: 
				if (jumpTick >= 4) {
					jumpFrame++;
					jumpTick = 0;
				} // else if (tickCount % 3 != 0) {
				// return;
				// }
				
				break;
				
				case 2:
				if (jumpTick >= 16) {
					jumpFrame++;
					jumpTick = 0;
				}
				
				break;
				
				case 3:
				if (jumpTick >= 32) {
					jumpFrame++;
					jumpTick = 0;
				}
				
				break;
				
				case 4:
				if (jumpTick >= 64) {
					jumpFrame++;
					jumpTick = 0;
				}
				
				break;
				
				default: // case 0
				if (jumpTick >= 8) {
					jumpFrame++;
					jumpTick = 0;
				}
				
				break;
				
			}

		} else if (isNormalGravity == false) {

			if (isJumpingOnX == true && isJumpingOnY == true) {
				// something

				int drawIndexNegative = (jumpFrame > 8) ? 0 : jumpFrame;

				if (cubeRotateNegative[drawIndexNegative] != null) {
					g.drawImage(cubeRotateNegative[drawIndexNegative], iForCube, 287 - jumpHeight, Graphics.RIGHT | Graphics.BOTTOM);
				} else {
					System.out.println("frame" + jumpFrame + "is missing");
				}

				switch(jumpFrame) {
				
					// case 0: jumpHeight = 0; break; // i know there's a default one
				
					case 1:
				
					jumpHeight -= parabola[0]; /* tickCount += -1 ;*/ 
					switch(mainApp.speedCount) {
						case 1: jumpTick++; break;
						case 2: jumpTick += 4; break;
						case 3: jumpTick += 8; break;
						case 4: jumpTick += 16; break;
						default: jumpTick += 2; break;
					}
					System.out.print("case -1 ");  
					break;
				
					case 2: 
				
					jumpHeight -= parabola[1]; 
					switch(mainApp.speedCount) {
						case 1: jumpTick++; break;
						case 2: jumpTick += 4; break;
						case 3: jumpTick += 8; break;
						case 4: jumpTick += 16; break;
						default: jumpTick += 2; break;
					}
					System.out.print("case -2 ");  
					break;
				
					case 3: 
					
					jumpHeight -= parabola[2]; 
					switch(mainApp.speedCount) {
						case 1: jumpTick++; break;
						case 2: jumpTick += 4; break;
						case 3: jumpTick += 8; break;
						case 4: jumpTick += 16; break;
						default: jumpTick += 2; break;
					}
					System.out.print("case -3 ");  
					break;
					
					case 4: 
					
					jumpHeight -= parabola[3]; 
					switch(mainApp.speedCount) {
						case 1: jumpTick++; break;
						case 2: jumpTick += 4; break;
						case 3: jumpTick += 8; break;
						case 4: jumpTick += 16; break;
						default: jumpTick += 2; break;
					}
					System.out.print("case -4 ");  
					break;
					
					case 5: 
					
					jumpHeight += parabola[3]; 
					switch(mainApp.speedCount) {
						case 1: jumpTick++; break;
						case 2: jumpTick += 4; break;
						case 3: jumpTick += 8; break;
						case 4: jumpTick += 16; break;
						default: jumpTick += 2; break;
					}
					System.out.print("case -5 "); 
					break;
					
					case 6: 
					
					jumpHeight += parabola[2]; 
					switch(mainApp.speedCount) {
						case 1: jumpTick++; break;
						case 2: jumpTick += 4; break;
						case 3: jumpTick += 8; break;
						case 4: jumpTick += 16; break;
						default: jumpTick += 2; break;
					}
					System.out.print("case -6 "); 
					break;
					
					case 7: 
					
					jumpHeight += parabola[1]; 
					switch(mainApp.speedCount) {
						case 1: jumpTick++; break;
						case 2: jumpTick += 4; break;
						case 3: jumpTick += 8; break;
						case 4: jumpTick += 16; break;
						default: jumpTick += 2; break;
					}
					System.out.print("case -7 ");  
					break;
					
					case 8: 
					
					jumpHeight += parabola[0]; 
					switch(mainApp.speedCount) {
						case 1: jumpTick++; break;
						case 2: jumpTick += 4; break;
						case 3: jumpTick += 8; break;
						case 4: jumpTick += 16; break;
						default: jumpTick += 2; break;
					}
					System.out.print("case -8 ");  
					break;
					
					default: 
					
					jumpHeight = 0; 
					jumpTick = 0; 
					System.out.print("case 0 ");
					break; // just in case
					
				}

			} /* else if (isJumpingOnX == false && isJumpingOnY == false) {
				g.drawImage(cubeRotate[0], iForCube, 287, Graphics.RIGHT | Graphics.BOTTOM);
				
			} */

			switch(mainApp.speedCount) {
				case 1: 
				if (jumpTick >= 4) {
					jumpFrame++;
					jumpTick = 0;
				} // else if (tickCount % 3 != 0) {
				// return;
				// }
				
				break;
				
				case 2:
				if (jumpTick >= 16) {
					jumpFrame++;
					jumpTick = 0;
				}
				
				break;
				
				case 3:
				if (jumpTick >= 32) {
					jumpFrame++;
					jumpTick = 0;
				}
				
				break;
				
				case 4:
				if (jumpTick >= 64) {
					jumpFrame++;
					jumpTick = 0;
				}
				
				break;
				
				default: // case 0
				if (jumpTick >= 8) {
					jumpFrame++;
					jumpTick = 0;
				}
				
				break;
						
			}

		} /* else if (isNormalGravity == null) {
			
			System.out.println("invalid");
			return;
	
		} */
	}

	public void normalMode(boolean isNormalMode) {
		// this will handle the gamemode's size

		if (isNormalMode == true) {
			// something
		} else {
			// something
		}
	}
	
	protected void pointerPressed(int x, int y) {
		if (isJumpingOnX == false && x >= 0 && x <= 240 && y >= 0 && y <= 400) {
			isJumpingOnX = true;
			isJumpingOnY = true;

			if (varNormalGravity == true) {
				jumpFrame = 1;
			} else {
				jumpFrame = 1;
			}
		}
	}
	
	public void commandAction(Command c, Displayable d) {
		
		System.out.println(c.getLabel() + " " + d.getTitle());
		if (c == GravityAndSize) {
			
			Display.getDisplay(mainApp).setCurrent(gravityAndSize);
			
		} else if (c == Mode) {
			
			Display.getDisplay(mainApp).setCurrent(mode);
			
		} else if (c == Speed) {
			
			Display.getDisplay(mainApp).setCurrent(speed);
			
		} else if (c == Back && (d == gravityAndSize || d == mode || d == speed)) {
			
			Display.getDisplay(mainApp).setCurrent(FlagChooser);
			
		} else if (c == Exit) {
			
			mainApp.showPlayScreen("playScreen");
			showNotify(); // just to make sure
		
		} else if (c == Save) {
			
			Displayable[] arr = {gravityAndSize, mode, speed};
			byte[] gAS = {0x00, 0x01, 0x10, 0x11};
			byte[] m = {0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06};
			byte[] s = {0x00, 0x01, 0x02, 0x03, 0x04};
			
			try {
			
				if (d == gravityAndSize && (Byte.parseByte(gravityAndSize.getString()) == gAS[0] ||
											Byte.parseByte(gravityAndSize.getString()) == gAS[1] ||
											Byte.parseByte(gravityAndSize.getString()) == gAS[2] ||
											Byte.parseByte(gravityAndSize.getString()) == gAS[3])) {
					
					gravityAndSizeFlags = Byte.parseByte(gravityAndSize.getString());
					System.out.println("gravityAndSizeFlags = 0x" + Integer.toHexString(gravityAndSizeFlags));
					Display.getDisplay(mainApp).setCurrent(saved, arr[0]);
				
				} else if (d == gravityAndSize && (Byte.parseByte(gravityAndSize.getString()) != gAS[0] ||
												   Byte.parseByte(gravityAndSize.getString()) != gAS[1] ||
												   Byte.parseByte(gravityAndSize.getString()) != gAS[2] ||
												   Byte.parseByte(gravityAndSize.getString()) != gAS[3])) {
					
					Display.getDisplay(mainApp).setCurrent(errorGas, arr[0]);
					
				} else if (d == mode && (Byte.parseByte(mode.getString()) == m[0] ||
										 Byte.parseByte(mode.getString()) == m[1] ||
										 Byte.parseByte(mode.getString()) == m[2] ||
										 Byte.parseByte(mode.getString()) == m[3] ||
										 Byte.parseByte(mode.getString()) == m[4] ||
										 Byte.parseByte(mode.getString()) == m[5] ||
										 Byte.parseByte(mode.getString()) == m[6])) {
					
					modeFlags = Byte.parseByte(mode.getString());
					System.out.println("modeFlags = 0x" + Integer.toHexString(modeFlags));
					Display.getDisplay(mainApp).setCurrent(saved, arr[1]);
					
				} else if (d == mode && (Byte.parseByte(mode.getString()) != m[0] ||
										 Byte.parseByte(mode.getString()) != m[1] ||
										 Byte.parseByte(mode.getString()) != m[2] ||
										 Byte.parseByte(mode.getString()) != m[3] ||
										 Byte.parseByte(mode.getString()) != m[4] ||
										 Byte.parseByte(mode.getString()) != m[5] ||
										 Byte.parseByte(mode.getString()) != m[6])) {
					
					Display.getDisplay(mainApp).setCurrent(errorM, arr[1]);
					
				} else if (d == speed && (Byte.parseByte(speed.getString()) == s[0] ||
										  Byte.parseByte(speed.getString()) == s[1] ||
										  Byte.parseByte(speed.getString()) == s[2] ||
										  Byte.parseByte(speed.getString()) == s[3] ||
										  Byte.parseByte(speed.getString()) == s[4])) {
					
					speedFlags = Byte.parseByte(speed.getString());
					mainApp.speedCount = speedFlags;
					System.out.println("speedFlags = 0x" + Integer.toHexString(speedFlags));
					Display.getDisplay(mainApp).setCurrent(saved, arr[2]);
					
				} else if (d == speed && (Byte.parseByte(speed.getString()) != s[0] ||
										  Byte.parseByte(speed.getString()) != s[1] ||
										  Byte.parseByte(speed.getString()) != s[2] ||
										  Byte.parseByte(speed.getString()) != s[3] ||
										  Byte.parseByte(speed.getString()) != s[4])) {
					
					Display.getDisplay(mainApp).setCurrent(errorS, arr[2]);
					
				}
				
			} catch (Exception e) {
				
				if (d == arr[0]) {
				
					Display.getDisplay(mainApp).setCurrent(errorGas, arr[0]);
					e.printStackTrace();
					
				} else if (d == arr[1]) {
					
					Display.getDisplay(mainApp).setCurrent(errorM, arr[1]);
					e.printStackTrace();
					
				} else if (d == arr[2]) {
					
					Display.getDisplay(mainApp).setCurrent(errorS, arr[2]);
					e.printStackTrace();
					
				}
				
			}
			
		} else if (c == OK) {
			
			FileConnection fconn = null;
			
			System.out.println(dirLoader.getString());
			
			try {
				
				String path = dirLoader.getString();
				
				fconn = (FileConnection) Connector.open(path, Connector.READ);
				
				if (fconn.exists() == true) {
					levelBinaryParser.parseByte(dirLoader.getString());
					Display.getDisplay(mainApp).setCurrent(successfully, dirLoader);
				} else {
					Display.getDisplay(mainApp).setCurrent(failed, dirLoader);
				}
			} catch (Exception e) {
				Display.getDisplay(mainApp).setCurrent(failed, dirLoader);
			} finally {
				try { if (fconn != null) fconn.close(); } catch(Exception e) {}
			}
			
		} else if (c == EXIT) {
			
			Display.getDisplay(mainApp).setCurrent(this);
			
		}
		
	}
	
	protected void keyPressed(int keyCode) {
		
		System.out.println(keyCode);
		
		if (keyCode == KEY_NUM1) {
			System.out.println("pressed 1");
			flagChooser();
		}
		
		if (keyCode == KEY_NUM2) {
			System.out.println("pressed 2");
			mainApp.showPauseScreen();
		}
		
	}
	
}