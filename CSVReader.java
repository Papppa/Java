package Implements;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class CSVReader {
	
	public static void main(String[] args){
		
		String csvFile = "data/2016-01-01-03-59-59_Birmingham_Germany_TTI_External.csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		String x;
		int y;
		// variableCode
		String time;
		int delay, speed, usualSpeed, freeFlow, length, startPosLat, startPosLon, frc, fow, bearing;
		String eventCode, category, roadNumber, openLRBinaryV3, dsegs;
		double dfp;
		
		/*
		 * 0 Time[ISO8601],
		 * 1 Delay[s],
		 * 2 Speed[kph],
		 * 3 UsualSpeed[kph],
		 * 4 Freeflow[kph],
		 * 5 Length[m],
		 * 6 EventCode[Datex1],
		 * 7 Category[0-5],
		 * 8 StartPosition[lat],
		 * 9 StartPosition[lon],
		 * 10 DeltaFurtherPositions[lat/lon;second|...|last],
		 * 11 FRC,
		 * 12 FOW[0-4],
		 * 13 bearing[0-359],
		 * 14 roadNumber,
		 * 15 OpenLRBinaryV3[Base64],
		 * 16 Dsegs _map-dependent
		 */
		
		try{
			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {
				String[] content = line.split(cvsSplitBy);
				String[] intContent = line.split(cvsSplitBy);
				x = content[0];
				y = Integer.parseInt(intContent[1]);

				//System.out.println("");				
			}
			
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
