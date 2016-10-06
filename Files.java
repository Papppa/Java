package Implements;

public class Files {
	public String time;
	public int delay, speed, usualSpeed, freeFlow, length, startPosLat, startPosLon, frc, fow, bearing;
	String eventCode, category, roadNumber, openLRBinaryV3, dsegs;
	public double dfp;
	
	public Files(String time, String roadNumber, int delay, int length){
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
	public int getDelay() {
		return delay;
	}
	public void setDelay(int delay){
		this.delay = delay;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length){
		this.length = length;
	}
	
	//@ overide
	public String toString() {
		return "Date-Time = " + time + "Road Number = " + roadNumber + "Delay = " + delay + "Length = " + length;
	}
}
