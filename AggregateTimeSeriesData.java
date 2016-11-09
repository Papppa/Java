package Implements;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Time;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.lang3.StringUtils;

public class AggregateTimeSeriesData {

	private static final int COLUMN_TIMESTAMP = 0;
	private static final String fileSource = "jamdataExample";
	

	public static void main(String[] args) {
		// TODO start and stop time
		Instant startTime = Instant.parse("2015-01-01T16:59:59Z"); // Iso Format 2016-01-01T03:00:00Z
		Instant stopTime= Instant.parse("2015-01-01T17:59:59Z");
		loadData(null, stopTime, stopTime, null);
		List<TrafficJamDataPoint> tsData = loadTrafficJamData(startTime, stopTime);
		File path;
		System.out.println(tsData);
		//writeToFile(path, tsData);
		
	}

	private static List<TrafficJamDataPoint> loadTrafficJamData(Instant startTime, Instant stopTime) {
		// TODO initialize cleverly by calculating the number of minutes (*2)
		// TODO fill tsData with files
		List<TrafficJamDataPoint> tsData = new ArrayList<>();
		List<File> filesToRead = new ArrayList<>();
		for (File file : filesToRead) {
			loadData(file, startTime, stopTime, tsData);
		}
		return tsData;
	}

	private static void loadData(File file, Instant startTime, Instant stopTime, List<TrafficJamDataPoint> tsData) {
		// TODO Auto-generated method stub
		// startTime.isBefore(arg)
		List<File> fileList = new ArrayList<File>();
		fileList = getAllFiles(startTime,stopTime);
		String line ="";
		BufferedReader br = null;
		for (int j=0; j<fileList.size();j++){
			try{
				FileInputStream fin = new FileInputStream(fileList.get(j));
				GzipCompressorInputStream gzIn = new GzipCompressorInputStream(fin);
				Reader rd = new InputStreamReader(gzIn, "US-ASCII");
				br = new BufferedReader(rd);
				TrafficJamDataPoint dp = new TrafficJamDataPoint(Instant);
				while ((line = br.readLine()) != null){
					String[] content = line.split(",");
					if (!StringUtils.isBlank(content[COLUMN_TIMESTAMP])) {
						Instant time = Instant.parse(content[COLUMN_TIMESTAMP]);
						dp = new TrafficJamDataPoint(time);
						dp.addDataLine(content);
						System.out.println(dp);
					} 
					else if (dp != null) {
						dp.addDataLine(content);
					}
					System.out.println(dp);
				}
			}
			catch(IOException ex){
		          ex.printStackTrace();
		       }
		}
	}

	private static List<File> getAllFiles(Instant startTime, Instant stopTime) {
		File folder = new File(fileSource);
		File[] listOfFiles = folder.listFiles();
		List<File> filePaths = new ArrayList<File>();
		for (int i=0;i<listOfFiles.length;i++){
			filePaths.add(new File(listOfFiles[i].toString()));
		}
		return filePaths;
	}

}