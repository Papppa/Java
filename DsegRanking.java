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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.StopWatch;

public class DsegRanking {
	
	private static final int COLUMN_TIMESTAMP = 0;
	private static final int COLUMN_DSEGID = 16;
	private static final String fileSource = "data/jams2015_q4";
	public static final String destinationFolder = "dataOutput/2015_DsegRanking_q4";
	public static final String fileName = fileSource.substring(5);
	
	public static void main(String[] args) throws Exception{
		StopWatch stopwatch = StopWatch.createStarted();
		Map<Long, DsegProperties> mpWD = new HashMap<>();
		Map<Long, EveningDsegProperties> epWD = new HashMap<>();
		Map<Long, DsegProperties> mpWE = new HashMap<>();
		Map<Long, EveningDsegProperties> epWE = new HashMap<>();
		Map<Long, DsegProperties> allDays = new HashMap<>();
		List<Instant> timeStampList = new ArrayList<>();
		List<File> fileList = new ArrayList<File>();
		fileList = getAllFiles();
		String line = "";
		BufferedReader dataLine = null;
		Instant timestamp = null;
		for (int j = 0; j < fileList.size(); j++) {
			System.out.println("reading file:" + fileList.get(j));
			try {
				FileInputStream fin = new FileInputStream(fileList.get(j));
				GzipCompressorInputStream gzIn = new GzipCompressorInputStream(fin);
				Reader rd = new InputStreamReader(gzIn, "US-ASCII");
				dataLine = new BufferedReader(rd);
				while ((line = dataLine.readLine()) != null) {
					String[] content = line.split(",");
					if (StringUtils.isEmpty(content[COLUMN_TIMESTAMP]) == false && line.contains(",") == true) {
						timestamp = getShortenTimestamp(content);
						timeStampList.add(timestamp);
						chooseMap(mpWD,epWD,mpWE,epWE,content,timestamp,timeStampList);
						calculateDsegProperties(content,allDays,timestamp,timeStampList);
					}
					else if (content[COLUMN_TIMESTAMP].isEmpty()) {
						chooseMap(mpWD,epWD,mpWE,epWE,content,timestamp,timeStampList);
						calculateDsegProperties(content,allDays,timestamp,timeStampList);
					}
				}
			}
			 catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		writeJamsMorningWeekdays(mpWD);
		writeJamsEveningWeekdays(epWD);
		writeJamsMorningWeekend(mpWE);
		writeJamsEveningWeekend(epWE);
		
		writeJamsallDays(allDays);
		writeTimestampList(timeStampList,stopwatch);
		System.out.println("time:" + stopwatch);
	}


	private static void chooseMap(Map<Long, DsegProperties> mpWD, Map<Long, EveningDsegProperties> epWD,
			Map<Long, DsegProperties> mpWE, Map<Long, EveningDsegProperties> epWE, String[] content, Instant timestamp, List<Instant> timeStampList) {
		String day = Implements.EnumDayProperties.getWeekday(timestamp);
		String time = Implements.EnumDayProperties.getPeak(timestamp);
		if (day.contains("WD") && time.contains("MP"))
			calculateDsegProperties(content,mpWD,timestamp,timeStampList);
		else if (day.contains("WD") && time.contains("EP"))
			calculateEveningDsegProperties(content,epWD,timestamp,timeStampList);
		else if (day.contains("WE") && time.contains("MP"))
			calculateDsegProperties(content,mpWE,timestamp,timeStampList);
		else if (day.contains("WE") && time.contains("EP"))
			calculateEveningDsegProperties(content,epWE,timestamp,timeStampList);
	}

	private static Instant getShortenTimestamp(String[] content) {
		int tsBegin = content[COLUMN_TIMESTAMP].length() - 20;
		int tsEnd = content[COLUMN_TIMESTAMP].length();
		Instant timestamp = Instant.parse(content[COLUMN_TIMESTAMP].substring(tsBegin, tsEnd));
		return timestamp;
	}

	private static void calculateDsegProperties(String[] content, Map<Long, DsegProperties> mapping, Instant timestamp,List<Instant> timeStampList) {
		List<Long> dsegColumnList = new ArrayList<>(600000);
		String dsegColumn = content[COLUMN_DSEGID];
		getDsegs(dsegColumn, dsegColumnList);
		for (Long segmentID : dsegColumnList){
			DsegProperties dsegProperties = mapping.get(segmentID);
			if (dsegProperties == null){
				dsegProperties = new DsegProperties(content,segmentID,timestamp);
				mapping.put(segmentID, dsegProperties);
			}
			else if(dsegProperties != null){
				dsegProperties.updateDsegID(content, segmentID,timestamp, timeStampList);
				mapping.put(segmentID, dsegProperties);
			}
		}
		
	}
	
	private static void calculateEveningDsegProperties(String[] content, Map<Long, EveningDsegProperties> mapping, Instant timestamp, List<Instant> timeStampList) {
		List<Long> dsegColumnList = new ArrayList<>(600000);
		String dsegColumn = content[COLUMN_DSEGID];
		getDsegs(dsegColumn, dsegColumnList);
		for (Long segmentID : dsegColumnList){
			EveningDsegProperties dsegProperties = mapping.get(segmentID);
			if (dsegProperties == null){
				dsegProperties = new EveningDsegProperties(content,segmentID,timestamp, timeStampList);
				mapping.put(segmentID, dsegProperties);
			}
			else if(dsegProperties != null){
				dsegProperties.updateEveningDsegID(content, segmentID,timestamp, timeStampList);
				mapping.put(segmentID, dsegProperties);
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
	
	///////////Writing the CSV-Files///////////
	
	private static void writeJamsMorningWeekdays(Map<Long, DsegProperties> dsegProperties) throws Exception {
		FileWriter writer = new FileWriter(destinationFolder+"/"+fileName+"_MP_WD.csv");
		// writing the head of the text file
		CSVUtils.writeLine(writer,Arrays.asList("DsegID","carDelay", "Count"));
		for(Map.Entry<Long, DsegProperties> entry : dsegProperties.entrySet()){
			Long key = entry.getKey();
			DsegProperties value = entry.getValue();
			CSVUtils.writeLine
			(writer,Arrays.asList(key.toString(),
					String.valueOf(value.getCarDelay()),
					String.valueOf(value.getCount())
					));
		}
		System.out.println("CSV File " + fileName +"_MP_WD.csv" + "printed succesfully");
		writer.flush();
		writer.close();
	}
	
	private static void writeJamsEveningWeekdays(Map<Long, EveningDsegProperties> dsegProperties) throws Exception {
		FileWriter writer = new FileWriter(destinationFolder+"/"+fileName+"_EP_WD.csv");
		// writing the head of the text file
		CSVUtils.writeLine(writer,Arrays.asList("DsegID","carDelay", "Count"));
		for(Map.Entry<Long, EveningDsegProperties> entry : dsegProperties.entrySet()){
			Long key = entry.getKey();
			EveningDsegProperties value = entry.getValue();
			CSVUtils.writeLine
			(writer,Arrays.asList(key.toString(),
					String.valueOf(value.getCarDelay()),
					String.valueOf(value.getCount())
					));
		}
		System.out.println("CSV File " + fileName +"_EP_WD.csv" + "printed succesfully");
		writer.flush();
		writer.close();
	}
	
	private static void writeJamsMorningWeekend(Map<Long, DsegProperties> dsegProperties) throws Exception {
		FileWriter writer = new FileWriter(destinationFolder+"/"+fileName+"_MP_WE.csv");
		// writing the head of the text file
		CSVUtils.writeLine(writer,Arrays.asList("DsegID","carDelay", "Count"));
		for(Map.Entry<Long, DsegProperties> entry : dsegProperties.entrySet()){
			Long key = entry.getKey();
			DsegProperties value = entry.getValue();
			CSVUtils.writeLine
			(writer,Arrays.asList(key.toString(),
					String.valueOf(value.getCarDelay()),
					String.valueOf(value.getCount())
					));
		}
		System.out.println("CSV File " + fileName +"_MP_WE.csv" + "printed succesfully");
		writer.flush();
		writer.close();
	}

	private static void writeJamsEveningWeekend(Map<Long, EveningDsegProperties> dsegProperties) throws Exception {
		FileWriter writer = new FileWriter(destinationFolder+"/"+fileName+"_EP_WE.csv");
		// writing the head of the text file
		CSVUtils.writeLine(writer,Arrays.asList("DsegID","carDelay", "Count"));
		for(Map.Entry<Long, EveningDsegProperties> entry : dsegProperties.entrySet()){
			Long key = entry.getKey();
			EveningDsegProperties value = entry.getValue();
			CSVUtils.writeLine
			(writer,Arrays.asList(key.toString(),
					String.valueOf(value.getCarDelay()),
					String.valueOf(value.getCount())
					));
		}
		System.out.println("CSV File " + fileName +"_EP_WE.csv" + "printed succesfully");
		writer.flush();
		writer.close();
	}
	private static void writeJamsallDays(Map<Long, DsegProperties> allDays) throws Exception {
		FileWriter writer = new FileWriter(destinationFolder+"/"+fileName+".csv");
		// writing the head of the text file
		CSVUtils.writeLine(writer,Arrays.asList("DsegID","carDelay", "Count"));
		for(Map.Entry<Long, DsegProperties> entry : allDays.entrySet()){
			Long key = entry.getKey();
			DsegProperties value = entry.getValue();
			CSVUtils.writeLine
			(writer,Arrays.asList(key.toString(),
					String.valueOf(value.getCarDelay()),
					String.valueOf(value.getCount())
					));
		}
		System.out.println("CSV File " + fileName +".csv" + "printed succesfully");
		writer.flush();
		writer.close();
		
	}
	private static void writeTimestampList(List<Instant> timeStampList, StopWatch stopwatch) throws IOException {
		FileWriter writer = new FileWriter(destinationFolder+"/"+fileName+"_timestampList.csv");
		// writing the head of the text file
		CSVUtils.writeLine(writer,Arrays.asList("time used for " + timeStampList.size() + "rows of timestamps: " + stopwatch));
		CSVUtils.writeLine(writer,Arrays.asList("/nTimestamp"));
		for(Instant ts : timeStampList){
			CSVUtils.writeLine(writer,Arrays.asList(ts.toString()));
		}
		writer.flush();
		writer.close();
		
	}
	
}