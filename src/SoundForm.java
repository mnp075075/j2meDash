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

public class SoundForm extends Form implements CommandListener {
	
	private j2meDash mainApp;
	
	public Command exitCommand = new Command("rage quit", Command.EXIT, 1);
	public Command yesCommand = new Command("no yes", Command.OK, 1);
	public Command noCommand = new Command("yes no", Command.CANCEL, 1);
	public Command mainBS = new Command("main bullsh*t", Command.OK, 1);
	
	public SoundForm(CommandListener cl, j2meDash mainApp) {
		
		super("# SOUNDWAVE #");
		
		this.mainApp = mainApp;
		append("do you want sound?");
		
		addCommand(yesCommand);
		addCommand(noCommand);
		addCommand(mainBS);
		
		setCommandListener(this);
		
	}
	
	public void commandAction(Command c, Displayable d) {
		if (c == mainBS) {
			
			mainApp.showMainMenu();
			
		} else if (c == yesCommand) {
			
			mainApp.showMainMenu();
			
		} else if (c == noCommand) {
			
			mainApp.showMainMenu();
			
		}
	}
	
}