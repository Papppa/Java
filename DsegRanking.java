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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.StopWatch;

public class DsegRanking {
	
	private static final int COLUMN_TIMESTAMP = 0;
	private static final int COLUMN_DSEGID = 16;
	private static final String finalCSVFile = "DsegRanking2015.csv";
	private static final String fileSource = "data/jamdata2015";
	
	public static void main(String[] args) throws Exception {
	StopWatch stopwatch = StopWatch.createStarted();
	DsegRanking loaderTest = new DsegRanking();
	Map<Long, DsegProperties> dsegProperties = loaderTest.loadAll();
	System.out.println("time:" + stopwatch);
	writeDsegRanking(dsegProperties);
	}

	public Map<Long, DsegProperties> loadAll() {
		Map<Long, DsegProperties> mapping = new HashMap<>();
		loadDsegRanking(mapping);
		return mapping;
	}

	private static void loadDsegRanking(Map<Long,DsegProperties> mapping) {
	MapLoader mapFiles = new MapLoader();
	Map<Long, MapProperties> mapProperties = mapFiles.loadAll();
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
			while ((line = br.readLine()) != null) {
				String[] content = line.split(",");
				// TODO add a timefilter in if-statement!
				if (StringUtils.isEmpty(content[COLUMN_TIMESTAMP]) == false && line.contains(",") == true) {
					List<Long> dsegColumnList = new ArrayList<>(100);
					String dsegColumn = content[COLUMN_DSEGID];
					getDsegs(dsegColumn, dsegColumnList);
					for (Long segmentID : dsegColumnList){
						DsegProperties dsegProperties = mapping.get(segmentID);
						if (dsegProperties == null){
						dsegProperties = new DsegProperties(content,mapProperties,segmentID);
						mapping.put(segmentID, dsegProperties);
						}
						else if(dsegProperties != null){
						dsegProperties.updateDsegID(content, mapProperties, segmentID);
						mapping.put(segmentID, dsegProperties);
						}
						}
					}
					
				else if (content[COLUMN_TIMESTAMP].isEmpty()) {
					List<Long> dsegColumnList = new ArrayList<>(100);
					String dsegColumn = content[COLUMN_DSEGID];
					getDsegs(dsegColumn, dsegColumnList);
					for (Long segmentID : dsegColumnList){
						DsegProperties dsegProperties = mapping.get(segmentID);
						if (dsegProperties == null){
						dsegProperties = new DsegProperties(content,mapProperties,segmentID);
						mapping.put(segmentID, dsegProperties);
						}
						else if(dsegProperties != null){
						dsegProperties.updateDsegID(content, mapProperties, segmentID);
						mapping.put(segmentID, dsegProperties);
						}
						}					
					}
				}
			}
		 catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
	private static void getDsegs (String dsegColumn, List<Long> dsegColumnList){
		Long id;
		int dsegCount;
		dsegCount=0;
		for (int i=0;i<dsegColumn.length();i++)
		{
			if(dsegColumn.charAt(i) == '|')
			dsegCount++;
		};
		String[] split = dsegColumn.split("\\|");
		for (int i=0;i<dsegCount;i++)
		{
			id = Long.parseLong(split[i]);
			dsegColumnList.add(id);
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

	private static void writeDsegRanking(Map<Long, DsegProperties> dsegProperties) throws Exception {
		FileWriter writer = new FileWriter(finalCSVFile);
		// writing the head of the text file
		CSVUtils.writeLine(writer,Arrays.asList("DsegID","totalDelay","totalLength","Count","Polyline"));
		for(Map.Entry<Long, DsegProperties> entry : dsegProperties.entrySet()){
			Long key = entry.getKey();
			DsegProperties value = entry.getValue();
			CSVUtils.writeLine
			(writer,Arrays.asList(key.toString(),
					String.valueOf(value.getTotalDelay()),
					String.valueOf(value.getTotalLength()),
					String.valueOf(value.getCount()),
					"[",
					String.valueOf(value.getPolyline()),
					"]"
					));
		}
		System.out.println("CSV File printed succesfully");
		writer.flush();
		writer.close();
	}

}