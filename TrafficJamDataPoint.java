package Implements;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class TrafficJamDataPoint {

	private static final int COLUMN_DELAY = 1;
	private static final int COLUMN_LENGTH = 5;
	private static final int COLUMN_DSEGID = 16;

	private final Instant time;
	private float totalDelay; // document unit
	private float totalLength;
	private int count;
	private String dsegID,polylineArray;
	private int dsegCount;

	public TrafficJamDataPoint(Instant time) {
		this.time = time;
	}

	public void addDataLine(String[] content) {
		count++;
		if (isNullOrBlank((content[COLUMN_DELAY])))
			totalDelay += 0;
		else
			totalDelay += Float.parseFloat(content[COLUMN_DELAY]);
		if (isNullOrBlank(content[COLUMN_LENGTH]))
			totalLength += 0;
		else
			totalLength += Float.parseFloat(content[COLUMN_LENGTH]);
	}
	
	public void getDsegID (String[] content) {
		dsegID = content[COLUMN_DSEGID];
	}
	
	public String getDsegID() {
		return dsegID;
	}

	public void getPolylines(String[] content,Map<Long, MapProperties> mapProperties){
		List<String> polylineList = new ArrayList<>();
		String polyline,idArray;
		Long id=null;
		double startLat, startLon, endLat, endLon;
		idArray = content[COLUMN_DSEGID];
		assert idArray.contains("|") : "cannot parse" + idArray;
		String[] split = idArray.split("\\|");
		dsegCount=0;
		for (int i=0;i<idArray.length();i++){
			if(idArray.charAt(i) == '|')
			dsegCount++;
		};
		for (int i=0; i < dsegCount ;i++){
			id = Long.parseLong(split[i]);
			startLat = mapProperties.get(id).getStart().getLat();
			startLon = mapProperties.get(id).getStart().getLon();
			endLat = mapProperties.get(id).getEnd().getLat();
			endLon = mapProperties.get(id).getEnd().getLon();
			polyline = "[" + startLat + "," + startLon+"]" + "," + "["+endLat + "," + endLon+ "]";
			polylineList.add(polyline);
		}
		polylineArray = polylineList.toString();
	}

	public Instant getTime() {
		return time;
	}

	public String getPolylineArray() {
		return polylineArray;
	}

	public float getTotalDelay() {
		return totalDelay;
	}

	public float getTotalLength() {
		return totalLength;
	}

	public int getCount() {
		return count;
	}

	public static boolean isNullOrBlank(String param) {
		return param == null || param.trim().length() == 0;
	}
}
