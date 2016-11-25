package Implements;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.StopWatch;

public class AggregateJamData {
	
	private static final String finalCSVFile = "jamDsegFile.csv";
	private static final String fileSource = "data/jamdataExample";
	private static final int COLUMN_TIMESTAMP = 0;

	public static void main(String[] args) throws Exception {
		StopWatch stopwatch = StopWatch.createStarted();
		List<TrafficJamDataPoint> jamData = loadTrafficJamData();
		writeJamFile(jamData);
		System.out.println("time:" + stopwatch);
		
	}
	
	private static List<TrafficJamDataPoint> loadTrafficJamData() {
		MapLoader mapFiles = new MapLoader();
		Map<Long, MapProperties> mapProperties = mapFiles.loadAll();
		List<TrafficJamDataPoint> jamData = new ArrayList<>();
		loadData(jamData,mapProperties);
		return jamData;
	}
	
	private static void loadData(List<TrafficJamDataPoint> jamData,Map<Long, MapProperties> mapProperties) {
		List<File> fileList = new ArrayList<File>();
		fileList = getAllFiles();
		String line = "";
		BufferedReader br = null;
		for (int j = 0; j < fileList.size(); j++) {
			System.out.println("reading file:" + fileList.get(j));
			try {
				FileInputStream fin = new FileInputStream(fileList.get(j));
				GzipCompressorInputStream gzIn = new GzipCompressorInputStream(fin);
				Reader rd = new InputStreamReader(gzIn, "US-ASCII");
				br = new BufferedReader(rd);
				TrafficJamDataPoint dp = null;
				while ((line = br.readLine()) != null) {
					String[] content = line.split(",");
					if (StringUtils.isEmpty(content[COLUMN_TIMESTAMP]) == false) {
						Instant time = Instant.parse(content[COLUMN_TIMESTAMP].substring(0, 20));
						dp = new TrafficJamDataPoint(time);
						dp.getPolylines(content,mapProperties);
						jamData.add(dp);
					} else if (content[COLUMN_TIMESTAMP].isEmpty()) {
						dp = new TrafficJamDataPoint(dp.getTime());
						dp.getPolylines(content,mapProperties);
						jamData.add(dp);
					}
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	private static List<File> getAllFiles() {
		File folder = new File(fileSource);
		File[] listOfFiles = folder.listFiles();
		List<File> filePaths = new ArrayList<File>();
		for (int i = 0; i < listOfFiles.length; i++) {
			filePaths.add(new File(listOfFiles[i].toString()));
		}
		return filePaths;
	}
	
	public static void writeJamFile(List<TrafficJamDataPoint> jamData) throws Exception {
		FileWriter writer = new FileWriter(finalCSVFile);
		for (int i = 0; i < jamData.size(); i++) {
			String ts = jamData.get(i).getTime().toString();
			String polyline = jamData.get(i).getPolylineArray();
			CSVUtils.writeLine(writer, Arrays.asList(ts,polyline));
		}
		System.out.println("CSV File printed succesfully");
		writer.flush();
		writer.close();
	}
}
