package Implements;

import java.time.Instant;

public class TrafficJamDataPoint {
	
	private static final int COLUMN_DELAY = 1;

	private static final int COLUMN_LENGTH = 5;
	


	private final Instant time;
	private float totalDelay;  // document unit
	private float totalLength;
	private int count;
	
	public TrafficJamDataPoint(Instant time) {
		this.time = time;
	}
	
	public void addDataLine(String[] content){
		count++;
		totalDelay += Float.parseFloat(content[COLUMN_DELAY]);
		totalLength += Float.parseFloat(content[COLUMN_LENGTH]);
	}
	public Instant getTime() {
		return time;
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
	

}
