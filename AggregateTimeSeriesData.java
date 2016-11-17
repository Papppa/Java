package Implements;

import java.io.BufferedReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Time;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.io.FileWriter;

import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.lang3.StringUtils;

public class AggregateTimeSeriesData {

	private static final int COLUMN_TIMESTAMP = 0;
	private static final String fileSource = "jamdataExample/";
	private static final String finalCSVFile = "tsfile.csv";
	

	public static void main(String[] args) throws Exception{
		// TODO start and stop time
		Instant startTime = Instant.parse("2015-01-01T16:00:02Z"); // Iso Format 2016-01-01T03:00:00Z
		Instant stopTime= Instant.parse("2015-01-01T17:59:41Z");
		List<TrafficJamDataPoint> tsData = loadTrafficJamData(startTime, stopTime);
		File path = null;
		writeTSFile(tsData);
		
	}

	private static List<TrafficJamDataPoint> loadTrafficJamData(Instant startTime, Instant stopTime) {
		// TODO initialize cleverly by calculating the number of minutes (*2)
		List<TrafficJamDataPoint> tsData = new ArrayList<>(200);
		List<File> filesToRead = new ArrayList<>();
		for (File file : getAllFiles(startTime,stopTime)) {
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
				TrafficJamDataPoint dp=null;
				while ((line = br.readLine()) != null){
					String[] content = line.split(",");
					if (StringUtils.isEmpty(content[COLUMN_TIMESTAMP]) == false) {
						Instant time = Instant.parse(content[COLUMN_TIMESTAMP].substring(0, 20));
						dp = new TrafficJamDataPoint(time);
						dp.addDataLine(content);
						tsData.add(dp);
					} 
					else if (content[COLUMN_TIMESTAMP].isEmpty()) {
						dp.addDataLine(content);
					}
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
	
	public static void writeTSFile (List<TrafficJamDataPoint> tsData) throws Exception {
		FileWriter writer = new FileWriter(finalCSVFile);
		for(int i=0;i<tsData.size(); i++){
			String ts = tsData.get(i).getTime().toString();
			String length = String.valueOf(tsData.get(i).getTotalLength());
			String delay = String.valueOf(tsData.get(i).getTotalDelay());
			String count = String.valueOf(tsData.get(i).getCount());
			CSVUtils.writeLine(writer, Arrays.asList(ts,length,delay,count),',');
		}
		System.out.println("CSV File printed succesfully");
		writer.flush();
		writer.close();
		}
}