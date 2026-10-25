package com.j2meDash.pars;
import com.j2meDash.main.*;


import java.io.*;

/*

 ██████╗ ██████╗  ██████╗ ███╗   ██╗     ██╗ █████╗ ██╗   ██╗ █████╗  ██╗██████╗  ██████╗ ████████╗██╗  ██╗
██╔════╝ ██╔══██╗██╔═══██╗████╗  ██║     ██║██╔══██╗██║   ██║██╔══██╗███║██╔══██╗██╔═══██╗╚══██╔══╝██║  ██║
██║  ███╗██║  ██║██║   ██║██╔██╗ ██║     ██║███████║██║   ██║███████║╚██║██║  ██║██║   ██║   ██║   ███████║
██║   ██║██║  ██║██║   ██║██║╚██╗██║██   ██║██╔══██║╚██╗ ██╔╝██╔══██║ ██║██║  ██║██║   ██║   ██║   ╚════██║
╚██████╔╝██████╔╝╚██████╔╝██║ ╚████║╚█████╔╝██║  ██║ ╚████╔╝ ██║  ██║ ██║██████╔╝╚██████╔╝   ██║        ██║
 ╚═════╝ ╚═════╝  ╚═════╝ ╚═╝  ╚═══╝ ╚════╝ ╚═╝  ╚═╝  ╚═══╝  ╚═╝  ╚═╝ ╚═╝╚═════╝  ╚═════╝    ╚═╝        ╚═╝

*/

public class LevelParser {
	
	private static String name;
	private int id, x1, x2, x3, y1, y2, y3, par;

	public static int[] safeObjectID = {1,2,3,4,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40};
	public static int[] dangerousObjectID = {5,6,7,8,9,10,11,12};
	
	public static int objectNumber;
	
	private int startingInt = 0;
	
	public void parseByte(String name) {;
		
		InputStream input = null;
		
		try {
			input = getClass().getResourceAsStream(name);
			System.out.println("[ERROR/LevelParser.parseByte()] Loaded file: " + name);
		} catch (Exception e) {
			System.err.println("[ERROR/LevelParser.parseByte()] Cannot find the file specified: " + name);
		}
		
		try {
			
			int counter = 0;
			byte[] header = {0x47, 0x44, 0x4F, 0x6E, 0x4A, 0x61, 0x76, 0x61, 0x12, 0x6C, 0x65, 0x76, 0x44, 0x61, 0x74, 0x61};
			boolean isValid = true;

			int b;

			while ((b = input.read()) != -1) {
				MainApp.data[counter] = (byte) b;
				counter++;
			}

			input.close();

			for (int i = 0; i < 16; i++) {
				if (MainApp.data[i] != header[i]) {
					isValid = false;
					break;
				}
			}
			
			if (isValid == false) {
				System.out.println("[ERROR/LevelParser.parseByte()] Invalid File");
				
				for (int i = 0; i < 16; i++) {
					if (MainApp.data[i] != header[i]) {
						System.out.println("[ERROR/LevelParser.parseByte()] At 0x" + Integer.toHexString(i) + ", byte: 0x" + Integer.toHexString(MainApp.data[i] & 0xFF) + " is wrong.");
						System.out.println("[ERROR/LevelParser.parseByte()] The **valid** header is: 0x47 0x44 0x4F 0x6E 0x4A 0x61 0x76 0x61 0x12 0x6C 0x65 0x76 0x44 0x61 0x74 0x61");
						break;
					}
				}

				return;
			} else {
				System.out.println("[INFO/LevelParser.parseByte()] Valid File");
				for (int i = 32; i < counter; i += 8) {

					id = (MainApp.data[i] & 0xFF);
					x1 = (MainApp.data[i+1] & 0xFF);
					x2 = (MainApp.data[i+2] & 0xFF);
					x3 = (MainApp.data[i+3] & 0xFF);
					y1 = (MainApp.data[i+4] & 0xFF);
					y2 = (MainApp.data[i+5] & 0xFF);
					y3 = (MainApp.data[i+6] & 0xFF);
					par = (MainApp.data[i+7] & 0xFF);
					
					MainApp.idArray[startingInt] = id;
					MainApp.xArray[startingInt] = (x1 << 16) | (x2 << 8) | x3;
					MainApp.yArray[startingInt] = (y1 << 16) | (y2 << 8) | y3;
					MainApp.parArray[startingInt] = par;
				
					if (id != 0 || x1 != 0 || x2 != 0 || x3 != 0 || y1 != 0 || y2 != 0 || y3 != 0 || par != 0) {
						System.out.println("[INFO/LevelParser.parseByte()] obj=" + ((i/8)-4) + 
										   " id=" + Integer.toHexString(id) + 
										   " x1=" + Integer.toHexString(x1) + 
										   " x2=" + Integer.toHexString(x2) + 
										   " x3=" + Integer.toHexString(x3) + 
										   " y1=" + Integer.toHexString(y1) + 
										   " y2=" + Integer.toHexString(y2) + 
										   " y3=" + Integer.toHexString(y3) + 
										   " par=" + Integer.toHexString(par) +
										   "\n" + "xArray=" + MainApp.xArray[startingInt] + " yArray=" + MainApp.yArray[startingInt]);
						
						objectNumber = (i/8)-3;
					} else if ((id | x1 | x2 | x3 | y1 | y2 | y3 | par) == 0) {
						System.out.println("[INFO/LevelParser.parseByte()] EOF=" + name);
						break;
					}
					
					startingInt++;

				}
				
				System.out.println("total objects: " + objectNumber);
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("SOMETHING IS DEFINITELY WRONG");
		} finally {
			try {
				if (input != null) input.close();
			} catch (IOException ignored) {}
		}
	}
}