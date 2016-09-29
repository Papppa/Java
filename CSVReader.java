package Implements;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class CSVReader {
	
	public static void main(String[] args){
		
		String csvFile = "data/books.csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ";";
		String x;
		int version;
		
		try{
			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {
				String[] content = line.split(cvsSplitBy);
				String[] intContent = line.split(cvsSplitBy);
				x = content[0];
				version = Integer.parseInt(intContent[1]);
				System.out.println(version);
				
				//System.out.println("Autor: " + content[2] + " Auflage: " + content[1] + " Titel: " + content[0]);
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
