import java.io.*;
import java.io.InputStream.*;
import java.io.ByteArrayInputStream.*;

/*

 ██████╗ ██████╗  ██████╗ ███╗   ██╗     ██╗ █████╗ ██╗   ██╗ █████╗  ██╗██████╗  ██████╗ ████████╗██╗  ██╗
██╔════╝ ██╔══██╗██╔═══██╗████╗  ██║     ██║██╔══██╗██║   ██║██╔══██╗███║██╔══██╗██╔═══██╗╚══██╔══╝██║  ██║
██║  ███╗██║  ██║██║   ██║██╔██╗ ██║     ██║███████║██║   ██║███████║╚██║██║  ██║██║   ██║   ██║   ███████║
██║   ██║██║  ██║██║   ██║██║╚██╗██║██   ██║██╔══██║╚██╗ ██╔╝██╔══██║ ██║██║  ██║██║   ██║   ██║   ╚════██║
╚██████╔╝██████╔╝╚██████╔╝██║ ╚████║╚█████╔╝██║  ██║ ╚████╔╝ ██║  ██║ ██║██████╔╝╚██████╔╝   ██║        ██║
 ╚═════╝ ╚═════╝  ╚═════╝ ╚═╝  ╚═══╝ ╚════╝ ╚═╝  ╚═╝  ╚═══╝  ╚═╝  ╚═╝ ╚═╝╚═════╝  ╚═════╝    ╚═╝        ╚═╝

*/

public class LevelBinaryParser {
	
	// this is another parser for levels and its binary format
	// this one is important since playScreen will load level from it (at least not now)
	// i don't know if it's optimized yet or not but whatever
	// you can help improve this one if you want

	private volatile boolean isRunning = true;
	public static String name;

	private j2meDash mainApp;
	
	// binary level parser value (useless on its own, they're used for the loops)
	public static int id;
	public static int x1;
	public static int x2;
	public static int x3;
	public static int y1;
	public static int y2;
	public static int y3;
	public static int par;
	
	public LevelBinaryParser(j2meDash mainApp) {
		
		this.mainApp = mainApp;
		
	}
	
	public void parseByte(String name) {;
		
		InputStream is = null;
		
		try {
			is = getClass().getResourceAsStream(name);
			System.err.println("FILENAME DOES EXIST");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("FILENAME DOES NOT EXIST");
		}
		
		ByteArrayOutputStream Baos = new ByteArrayOutputStream();
		// DataOutputStream Dos = new DataOutputStream(Baos);
		// int bytes;
		
		try {
			
			int total = is.read(mainApp.data);
			byte[] header1 = {0x47, 0x44, 0x4F, 0x6E, 0x4A, 0x61, 0x76, 0x61, 0x12, 0x6C, 0x65, 0x76, 0x44, 0x61, 0x74, 0x61};
			// byte[] header2 = {
			
			is.close();
			boolean isValid = true;
			
			// is.close();
			
			while ((total = is.read()) != -1) {
				Baos.write(mainApp.data);
			}

			for (int i = 0; i < 16; i++) {
				if (mainApp.data[i] != header1[i]) {
					isValid = false;
					break;
				}
			}
			
			if (isValid == false) {
				System.out.println("Invalid File");
				System.out.println("Reason: Incorrect file header");
				
				for (int i = 0; i < 16; i++) {
					if (mainApp.data[i] != header1[i] /* || data[i] != header2[i] */) {
						System.out.println("At 0x" + Integer.toHexString(i) + ", byte: 0x" + Integer.toHexString(mainApp.data[i] & 0xFF) + " is wrong.");
						System.out.println("The **valid** header is: 0x47 0x44 0x4F 0x6E 0x4A 0x61 0x76 0x61 0x12 0x6C 0x65 0x76 0x44 0x61 0x74 0x61");
						System.out.println("Please check, modify your header and try again");
						break;
					}
				}

				return;
			} else {
				System.out.println("Valid File");
				// System.out.println("byte read: " + total);
				for (int i = 32; i > total; i += 4) {

					// String hexBytes = Integer.toHexString(bytes);

					id = mainApp.data[i] & 0xFF;
					x1 = mainApp.data[i+1] & 0xFF;
					x2 = mainApp.data[i+2] & 0xFF;
					x3 = mainApp.data[i+3] & 0xFF;
					y1 = mainApp.data[i+4] & 0xFF;
					y2 = mainApp.data[i+5] & 0xFF;
					y3 = mainApp.data[i+6] & 0xFF;
					par = mainApp.data[i+7] & 0xFF;
				
					// System.out.println(hexBytes + " ");
				
					if (id != 0 && x1 != 0 && x2 != 0 && x3 != 00 && y1 != 0 && y2 != 0 && y3 != 0 && par != 0) {
						System.out.println("obj: " + ((i/4)-8) + 
										   " id: " + Integer.toHexString(id) + 
										   " x1: " + x1 + 
										   " x2: " + x2 + 
										   " x3: " + x3 + 
										   " y1: " + y1 + 
										   " y2: " + y2 + 
										   " y3: " + y3 + 
										   " par: " + par);
						// System.out.println("data" + data);
					} else if (id == 0 && x1 == 0 && x2 == 0 && x3 == 0 && y1 == 0 && y2 == 0 && y3 == 0 && par == 0) {
						System.out.println("End of: " + name);
						return;
					}
					
					

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("SOMETHING IS DEFINITELY WRONG");
			// note
		}
	}
}