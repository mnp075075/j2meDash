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

public class DataRegistry {
	
	private MainApp mainApp;

	// the dataRegistry is specifically made to handle parsing the csv file
	// it will be used for the playScreen

	// String line;
	
	public DataRegistry(MainApp mainApp) {
		
		this.mainApp = mainApp;
		InputStream is = getClass().getResourceAsStream("img/objectIDSpreadsheet.csv");

		if (is == null) {
			System.out.println("objectIDSpreadsheet.csv doesn't exist");
		} else {
			System.out.println("objectIDSpreadsheet.csv exists");
		}

		StringBuffer sb = new StringBuffer();
		int ch;
		
		try {

		while ((ch = is.read()) != -1) {
			if (ch == '\n' || ch == '\r') {
				if (sb.length() > 0) {
					parseLine(sb.toString());
					sb.setLength(0);
				}
			} else {
				sb.append((char) ch);
			}
		}
		
		if (sb.length() > 0) {
			parseLine(sb.toString());
		}

		is.close();

		} catch (Exception e) {
			// System.out.println("MISSING, THE FILE IS MISSING");
		}
	}

	private void parseLine(String line) {
		if (line.startsWith("objectID")) {
			return;
		}
		
		int firstSemi = line.indexOf(';');
		int secondSemi = line.indexOf(';', firstSemi + 1);
		int thirdSemi = line.indexOf(';', secondSemi + 1);
		int fourthSemi = line.indexOf(';', thirdSemi + 1);
		int fifthSemi = line.indexOf(';', fourthSemi + 1);
		int sixthSemi = line.indexOf(';', fifthSemi + 1);
		int seventhSemi = line.indexOf(';', sixthSemi + 1);
		int eighthSemi = line.indexOf(';', seventhSemi + 1);
		int ninthSemi = line.indexOf(';', eighthSemi + 1);
		
		if (firstSemi != -1 && secondSemi != -1 && thirdSemi != -1 && fourthSemi != 1 && fifthSemi != -1 && sixthSemi != -1 && seventhSemi != -1 && eighthSemi != -1 && ninthSemi != -1) {
			int id = Integer.parseInt(line.substring(0, firstSemi).trim());

			String detail = line.substring(firstSemi + 1, secondSemi);
			String xCoords = line.substring(secondSemi + 1, thirdSemi);
			String yCoords = line.substring(thirdSemi + 1, fourthSemi);
			String widths = line.substring(fourthSemi + 1, fifthSemi);
			String heights = line.substring(fifthSemi + 1, sixthSemi);
			String hitboxXs = line.substring(sixthSemi + 1, seventhSemi);
			String hitboxYs = line.substring(seventhSemi + 1, eighthSemi);
			String hitboxWs = line.substring(eighthSemi + 1, ninthSemi);
			String hitboxHs = line.substring(ninthSemi + 1);

			mainApp.details[id] = detail;
			mainApp.xCoord[id] = xCoords;
			mainApp.yCoord[id] = yCoords;
			mainApp.width[id] = widths;
			mainApp.height[id] = heights;
			mainApp.hitboxX[id] = hitboxXs;
			mainApp.hitboxY[id] = hitboxYs;
			mainApp.hitboxW[id] = hitboxWs;
			mainApp.hitboxH[id] = hitboxHs;

			// directories[id] = directory;

			System.out.println("ID: " + id + " Details: " + detail + " xCoord: " + xCoords + " yCoord: " + yCoords + " width: " + widths + " height: " + heights + " hitboxX: " + hitboxXs + " hitboxY: " + hitboxYs + " hitboxW: " + hitboxWs + " hitboxH: " + hitboxHs); // + " Paths: " + directory);
		}
	}
}