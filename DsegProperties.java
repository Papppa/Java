package Implements;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DsegProperties {
	
	private static final int START_COLUMN = 11;
	private static final int END_COLUMN = 12;
	private static final int COLUMN_DELAY = 1;
	private static final int COLUMN_LENGTH = 5;
	private static final int COLUMN_SPEED = 5;
	private static final int COLUMN_DSEGID = 16;
	

	Instant time;
	private float totalDelay;
	private float totalLength;
	private float avgSpeed;
	private int count;
	private String polyline;

	private int dsegCount;
	
	public DsegProperties(Instant time) {
		this.time = time;
	}
	
	public DsegProperties (String[] content,Map<Long, MapProperties> mapProperties, Long segmentID) {
		count = 1;
		if (isNullOrBlank((content[COLUMN_DELAY])))
			totalDelay += 0;
		else
			totalDelay += Float.parseFloat(content[COLUMN_DELAY]);
		if (isNullOrBlank(content[COLUMN_LENGTH]))
			totalLength += 0;
		else
			totalLength += Float.parseFloat(content[COLUMN_LENGTH]);
		// compute average speed
		if (isNullOrBlank((content[COLUMN_SPEED])))
			avgSpeed += 0;
		else
			avgSpeed += Float.parseFloat(content[COLUMN_SPEED]);
		// translate dsegs in Coordinates
		double startLat, startLon, endLat, endLon;
		if (mapProperties.containsKey(segmentID)==false){
			startLat =51.476852;
			startLon = 0.000000;
			endLat = 51.476852;
			endLon = 0.0000000;
			polyline = "[" + startLat + "," + startLon+"]" + "," + "["+endLat + "," + endLon+ "]";
		}
		else if(mapProperties.containsKey(segmentID)){
		startLat = mapProperties.get(segmentID).getStart().getLat();
		startLon = mapProperties.get(segmentID).getStart().getLon();
		endLat = mapProperties.get(segmentID).getEnd().getLat();
		endLon = mapProperties.get(segmentID).getEnd().getLon();
		polyline = "[" + startLat + "-" + startLon+"]" + "-" + "["+endLat + "-" + endLon+ "]";
		}
	}
	
	public void updateDsegID (String[] content,Map<Long, MapProperties> mapProperties, Long segmentID) {
		count++;
		if (isNullOrBlank((content[COLUMN_DELAY])))
			totalDelay += 0;
		else
			totalDelay += Float.parseFloat(content[COLUMN_DELAY]);
		if (isNullOrBlank(content[COLUMN_LENGTH]))
			totalLength += 0;
		else
			totalLength += Float.parseFloat(content[COLUMN_LENGTH]);
		// compute average speed
		if (isNullOrBlank((content[COLUMN_SPEED])))
			avgSpeed += 0;
		else
			avgSpeed += Float.parseFloat(content[COLUMN_SPEED]);
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

	public float getTotalDelay() {
		return totalDelay;
	}

	public float getTotalLength() {
		return totalLength;
	}

	public Instant getTime() {
		return time;
	}
	public int getCount() {
		return count;
	}

	public String getPolyline() {
		return polyline;
	}
	
	public static boolean isNullOrBlank(String param) {
		return param == null || param.trim().length() == 0 || param.trim().isEmpty();
	}
	
}
