package Implements;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.*;
import java.util.*;
import java.util.stream.Stream;

import org.apache.commons.compress.compressors.gzip.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.apache.commons.csv.*;

public class MapLoader {

	private static final String fileSource = "data/mapdata/";
	private static final String finalCSVFile = "mapfile.csv";

	public static void main(String[] args) throws Exception {
		StopWatch stopwatch = StopWatch.createStarted();
		MapLoader loaderTest = new MapLoader();
		Map<Long, MapProperties> mapProperties = loaderTest.loadAll();
		System.out.println("time:" + stopwatch);
		// get information about a dsegid (random dsegID: 1171544345426846566)
		//keyboardInput(mapProperties);
		// write all mapfiles in finalCSVFile
		//writeMapFile(mapProperties);

	}

	public Map<Long, MapProperties> loadAll() {
		Map<Long, MapProperties> mapping = new HashMap<>();
		load(mapping);
		return mapping;
	}

	private List<File> getMapFiles() {
		File folder = new File(fileSource);
		File[] listOfFiles = folder.listFiles();
		List<File> filePaths = new ArrayList<File>();
		for (int i = 0; i < listOfFiles.length; i++) {
			filePaths.add(new File(listOfFiles[i].toString()));
		}
		return filePaths;
	}

	private void load(Map<Long, MapProperties> mapping) {
		String lineReader = "";
		Long segmentId;
		for (String line : getLines()) {
			if ((lineReader = line).startsWith("ticket") == false) {
				String[] content = line.split(";");
				segmentId = Long.parseLong(content[0]);
				MapProperties mapProperties = new MapProperties(content);
				mapping.put(segmentId, mapProperties);
			}
		}
	}

	private List<String> getLines() {
		List<String> rawDataList = new ArrayList<String>(1600000);
		String line = "";
		BufferedReader br = null;
		List<File> fileList = new ArrayList<File>();
		fileList = getMapFiles();
		for (int j = 0; j < fileList.size(); j++) {
			try {
				FileInputStream fin = new FileInputStream(fileList.get(j));
				GzipCompressorInputStream gzIn = new GzipCompressorInputStream(fin);
				Reader rd = new InputStreamReader(gzIn, "US-ASCII");
				br = new BufferedReader(rd);
				while ((line = br.readLine()) != null)
					rawDataList.add(line);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return rawDataList;
	}

	private static void writeMapFile(Map<Long, MapProperties> mapProperties) throws Exception {
		FileWriter writer = new FileWriter(finalCSVFile);
		for(Map.Entry<Long, MapProperties> entry : mapProperties.entrySet()){
			Long key = entry.getKey();
			MapProperties value = entry.getValue();
			CSVUtils.writeLine(writer, Arrays.asList(key.toString(),String.valueOf(value.getStart().getLat()),String.valueOf(value.getStart().getLon()),String.valueOf(value.getEnd().getLat()),String.valueOf(value.getEnd().getLon())));
		}
		System.out.println("CSV File printed succesfully");
		writer.flush();
		writer.close();
	}

	private static void keyboardInput(Map<Long, MapProperties> mapProperties) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Type in the dsegID: ");
		String dsegID = scanner.next();
		if (dsegID.length() == 19) {
			Long id = Long.parseLong(dsegID);
			double startLat, startLon, endLat, endLon;
			startLat = mapProperties.get(id).getStart().getLat();
			startLon = mapProperties.get(id).getStart().getLon();
			endLat = mapProperties.get(id).getEnd().getLat();
			endLon = mapProperties.get(id).getEnd().getLon();
			String road = mapProperties.get(id).getRoadname();
			String city = mapProperties.get(id).getNearCity();
			System.out.println("The dsegID: " + dsegID + " is located on " + road + " near the city " + city);
			System.out.println("the starting point is (Lat,Lon): " + startLat + " , " + startLon);
			System.out.println("the end point is (Lat, Lon): " + endLat + " , " + endLon);
		}

	}
}
