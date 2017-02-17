package Implements;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;

public class EveningDsegProperties {
	
	private static final int START_COLUMN = 11;
	private static final int END_COLUMN = 12;
	private static final int COLUMN_DELAY = 1;
	private static final int COLUMN_LENGTH = 5;
	private static final int COLUMN_SPEED = 2;
	private static final int COLUMN_DSEGID = 16;
	private static final int COLUMN_TIMESTAMP = 0;
	private static final int COLUMN_FREEFLOAT = 4;
	

	Instant time;
	private float totalDelay;
	private float totalLength;
	private float avgSpeed;
	private int count;
	private static int freeLineCount;
	private String polyline;
	private Instant timestamp;
	private String dayProperty, timeProperty;
	
	private int timeDifference;
	private float delay,speed, length, freeFloat, meanVehicles ,jamDelay, carDelay,density_cong,trafficDensity,NumberOfLanes,jamSpreadingSpeed,meanVehicles0;

	private int dsegCount,emptyCellCount;
	
	public EveningDsegProperties(Instant time) {
		this.time = time;
	}
	
	public static int CalculateFreeLinesBetweenTimestamps(boolean freeLine){
		if(freeLine == true)
			freeLineCount = +1;
		return freeLineCount;
	}
	
	public EveningDsegProperties (String[] content, Long segmentID, Instant dateTime, List<Instant> timeStampList) {
		dayProperty = Implements.EnumDayProperties.getWeekday(dateTime);
		timeProperty = Implements.EnumDayProperties.getPeak(dateTime);
		timestamp = dateTime;
		
		if (isNullOrBlank((content[COLUMN_DELAY])))
			totalDelay += 0;
		else
			totalDelay += Float.parseFloat(content[COLUMN_DELAY]);
		if (isNullOrBlank(content[COLUMN_LENGTH]))
			totalLength += 0;
		else
			totalLength += Float.parseFloat(content[COLUMN_LENGTH]);
		// data given from jamData
		if (isNullOrBlank((content[COLUMN_SPEED])))
			speed = 0;
		else
			speed = Float.parseFloat(content[COLUMN_SPEED]); // [km/h]
		if (isNullOrBlank((content[COLUMN_DELAY])))
			delay = 0;
		else
			delay = Float.parseFloat(content[COLUMN_DELAY]);
		delay = delay / 3600;  // [h]
		if (isNullOrBlank(content[COLUMN_LENGTH]))
			length = 0;
		else
			length = Float.parseFloat(content[COLUMN_LENGTH]);
		length = length / 1000; //[km]
		if (isNullOrBlank(content[COLUMN_FREEFLOAT]))
			freeFloat = 0; //[km/h]
		else
			freeFloat = Float.parseFloat(content[COLUMN_LENGTH]); //[km/h]
		// assumed Data
		trafficDensity = 2000; //[fzg/h]
		NumberOfLanes = 3; //[lanes]
		jamSpreadingSpeed = -15; //[km/h]
		
		//calculations
		density_cong = (trafficDensity * (1 - (jamSpreadingSpeed / freeFloat)) / (speed-jamSpreadingSpeed)); //[Fzg / km]
 		meanVehicles = NumberOfLanes*length*density_cong; //[kfz]
 		
		jamDelay = meanVehicles; // [kfz*h]
		meanVehicles0 = meanVehicles * (speed/freeFloat); // [kfz]
		carDelay = (meanVehicles-meanVehicles0); // [kfz*h]

		// translate dsegs in Coordinates
		//polyline = transformDsegToGeocoordninate(mapProperties,segmentID);

	}
	public void updateEveningDsegID (String[] content, Long segmentID, Instant dateTime, List<Instant> timeStampList) {
		count++;
		dayProperty = Implements.EnumDayProperties.getWeekday(dateTime);
		timeProperty = Implements.EnumDayProperties.getPeak(dateTime);
		timestamp = dateTime;
		Instant preLastTimestamp = null;
		for (int i=0;i<timeStampList.size();i++){
			int preLastEntry = timeStampList.size()-2;
			preLastTimestamp = timeStampList.get(preLastEntry);
		}
		Duration duration = Duration.between(preLastTimestamp,dateTime);
		timeDifference = toIntExact(duration.toMillis())/1000;

		if (isNullOrBlank((content[COLUMN_DELAY])))
			totalDelay += 0;
		else
			totalDelay += Float.parseFloat(content[COLUMN_DELAY]);
		if (isNullOrBlank(content[COLUMN_LENGTH]))
			totalLength += 0;
		else
			totalLength += Float.parseFloat(content[COLUMN_LENGTH]);
		// data given from jamData
		if (isNullOrBlank((content[COLUMN_SPEED])))
			speed = 0;
		else
			speed = Float.parseFloat(content[COLUMN_SPEED]);
		if (isNullOrBlank((content[COLUMN_DELAY])))
			delay = 0;
		else
			delay = Float.parseFloat(content[COLUMN_DELAY]);
		delay = delay / 3600;  // [h]
		if (isNullOrBlank(content[COLUMN_LENGTH]))
			length = 0;
		else
			length = Float.parseFloat(content[COLUMN_LENGTH]);
		length = length / 1000; //[km]
		if (isNullOrBlank(content[COLUMN_FREEFLOAT]))
			freeFloat = 0; //[km/h]
		else
			freeFloat = Float.parseFloat(content[COLUMN_LENGTH]); //[km/h]
		trafficDensity = 2000; //[fzg/h]
		NumberOfLanes = 3; //[lanes]
		jamSpreadingSpeed = -15; //[km/h]
		
		//calculations
		density_cong = (trafficDensity * (1 - (jamSpreadingSpeed / freeFloat)) / (speed-jamSpreadingSpeed)); //[Fzg / km]
 		meanVehicles = NumberOfLanes*length*density_cong; //[kfz]
 		
		jamDelay = meanVehicles; // [kfz*h]
		meanVehicles0 = meanVehicles * (speed/freeFloat); // [kfz]
		carDelay += (meanVehicles-meanVehicles0)*timeDifference/3600; // [kfz*h]
	}
	
	private static String transformDsegToGeocoordninate (Map<Long, MapProperties> mapProperties, Long segmentID){
		double startLat, startLon, endLat, endLon;
		String polyline = null;
		if (mapProperties.containsKey(segmentID)==false){
			startLat =51.476852;
			startLon = 0.000000;
			endLat = 51.476852;
			endLon = 0.0000000;
			polyline = "[" + startLat + "-" + startLon+"]" + "-" + "["+endLat + "-" + endLon+ "]";
		}
		else if(mapProperties.containsKey(segmentID)){
		startLat = mapProperties.get(segmentID).getStart().getLat();
		startLon = mapProperties.get(segmentID).getStart().getLon();
		endLat = mapProperties.get(segmentID).getEnd().getLat();
		endLon = mapProperties.get(segmentID).getEnd().getLon();
		polyline = "[" + startLat + "-" + startLon+"]" + "-" + "["+endLat + "-" + endLon+ "]";
		}
		return polyline;
	}
	
	public static int toIntExact(long value) {
	    if ((int)value != value) {
	        throw new ArithmeticException("integer overflow");
	    }
	    return (int)value;
	}
	
	public static boolean isNullOrBlank(String param) {
		return param == null || param.trim().length() == 0 || param.trim().isEmpty();
	}
	
	public String getDayProperty() {
		return dayProperty;
	}

	public float getTotalDelay() {
		return totalDelay;
	}
	
	public float getAvgSpeed() {
		return avgSpeed;
	}
	
	public float getSpeed() {
		return speed;
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
	
	public Instant getTimestamp() {
		return timestamp;
	}


	public String getTimeProperty() {
		return timeProperty;
	}
	public float getLength() {
		return length;
	}

	public float getFreeFloat() {
		return freeFloat;
	}

	public float getMeanVehicles() {
		return meanVehicles;
	}

	public float getCarDelay() {
		return carDelay;
	}

	public float getDensity_cong() {
		return density_cong;
	}
	

}
