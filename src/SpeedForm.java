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

public class SpeedForm extends Form {
	
	private j2meDash mainApp;
	
	public Command half_times_speed = new Command("0.5x speed", Command.OK, 1);
	public Command one_time_speed = new Command("1x speed", Command.OK, 1);
	public Command two_times_speed = new Command("2x speed", Command.OK, 1);
	public Command three_times_speed = new Command("3x speed", Command.OK, 1);
	public Command four_times_speed = new Command("4x speed", Command.OK, 1);
	public Command exit = new Command("Exit back to mainMenu", Command.EXIT, 1);
		
	public SpeedForm(CommandListener cl) {
	
		super("what speed");
		
		append("select speed?");
		
		addCommand(half_times_speed);
		addCommand(one_time_speed);
		addCommand(two_times_speed);
		addCommand(three_times_speed);
		addCommand(four_times_speed);
		addCommand(exit);
		
		setCommandListener(cl);
		
	}
	
}