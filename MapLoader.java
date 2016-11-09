package Implements;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
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
import org.apache.commons.csv.*;

import com.sun.jna.StringArray;

public class MapLoader {
	
	private static final String fileSource = "mapdata/"; 

	public static void main(String[] args) {
		// for testing
		// Guava library: time measurement with StopWatch
		MapLoader loaderTest = new MapLoader();
		Map<Long, MapProperties> mapProperties = loaderTest.loadAll();
	}
	
	private Map<Long, MapProperties> loadAll() {
		Map<Long, MapProperties> mapping = new HashMap<>();
		for (File mapfile : getMapFiles()) {
			load(mapfile, mapping);
		}
		return mapping;
	}
	

	private List<File> getMapFiles() {
		File folder = new File(fileSource);
		File[] listOfFiles = folder.listFiles();
		List<File> filePaths = new ArrayList<File>();
		for (int i=0;i<listOfFiles.length;i++){
			filePaths.add(new File(listOfFiles[i].toString()));
		}
		return filePaths;
	}
	
	private void load(File file,Map<Long, MapProperties> mapping) {
		String lineReader = "";
		Long segmentId;
		for (String line : getLines()) {
			if((lineReader = line).startsWith("ticket") == false){
				String [] content = line.split(";");
				segmentId = Long.parseLong(content[0]);
				MapProperties mapProperties = new MapProperties(content);
				mapping.put(segmentId, mapProperties);
			}
		}
	}
	

	private List<String> getLines() {
		List<String> rawDataList = new ArrayList<String>(1600000);
		String line ="";
		BufferedReader br = null;
		List<File> fileList = new ArrayList<File>();
		fileList = getMapFiles();
		for (int j=0; j<fileList.size();j++){
			try{
				FileInputStream fin = new FileInputStream(fileList.get(j));
				GzipCompressorInputStream gzIn = new GzipCompressorInputStream(fin);
				Reader rd = new InputStreamReader(gzIn, "US-ASCII");
				br = new BufferedReader(rd);
				while ((line = br.readLine()) != null) 
					rawDataList.add(line);
			}
			catch(IOException ex){
		          ex.printStackTrace();
		       }
		}
		return rawDataList; 
	}
}
