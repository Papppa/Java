package Implements;

public class Files {
	public String time;
	//public int delay, speed, usualSpeed, freeFlow, length, startPosLat, startPosLon, frc, fow, bearing;
	String eventCode, category, roadNumber, openLRBinaryV3, dsegs,delay, speed, usualSpeed, freeFlow, length, startPosLat, startPosLon, frc, fow, bearing,dfp;
	String totalDelay, totalLength;
	int intDelay, intLength, intTotalDelay, intTotalLength;
	//public double dfp;
	
	
	public Files(String time, String roadNumber, String delay, String length){
		super();
		this.time = time;
		this.roadNumber = roadNumber;
		this.delay = delay;
		this.length = length;
	}
	
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getRoadNumber() {
		return roadNumber;
	}
	public void setRoadNumber(String roadNumber) {
		this.roadNumber = roadNumber;
	}
	public String getDelay() {
		return delay;
	}
	public void setDelay(String delay){
		this.delay = delay;
	}
	public int delayToInt() {
		return Integer.parseInt(delay);
	}
	public String getLength() {
		return length;
	}
	public void setLength(String length){
		this.length = length;
	}
	public int lengthToInt() {
		return Integer.parseInt(length);
	}
	//@ overide
	public String toString() {
		return time + "," + roadNumber + "," + delay + "," + length;
	}
}


