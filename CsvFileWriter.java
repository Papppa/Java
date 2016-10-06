package Implements;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import Implements.Files;

public class CsvFileWriter {
	//Delimiter
	private static final String COMMA_DELIMITER = ",";
	private static final String NEW_LINE_SEPARATOR = "\n";
	//Csv Header
	private static final String FILE_HEADER = "id,firstName,lastName,gender,age";
	
	public static void writeCsvFile(String fileName) {
		//New Object
		Files file1 = new Files("03:00:00", "E51", 54, 2);
		Files file2 = new Files("03:30:00", "E51", 54, 4);
		//New List
		List list = new ArrayList();
		list.add(file1);
		list.add(file2);
		
		FileWriter fileWriter = null;
		
		try{
			fileWriter = new FileWriter(fileName);
			
			//Header
			fileWriter.append(FILE_HEADER.toString());
			//New line seperator after Header
			fileWriter.append(NEW_LINE_SEPARATOR);
			//Object list
			for (Files file : files) {
				fileWriter.append(((Files) file).getTime());
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(((Files) file).getRoadNumber());
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(String.valueOf(((Files) file).getDelay()));
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(String.valueOf(((Files) file).getLength()));
				fileWriter.append(NEW_LINE_SEPARATOR);
			}
			
			System.out.println("Done!");
		} catch (Exception e) {
			System.out.println("Error in CsvFileWriter !!!");
			e.printStackTrace();
		} finally {
			
			try {
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e) {
				System.out.println("Error while flushing/closing fileWriter !!!");
				e.printStackTrace();
			}
		}
	}
}
