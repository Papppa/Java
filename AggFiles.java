package Implements;

public class AggFiles {
	public String timeAgg,roadNumberAgg,delayAgg,lengthAgg;
	//public int delay, speed, usualSpeed, freeFlow, length, startPosLat, startPosLon, frc, fow, bearing;
	//String eventCode, category, openLRBinaryV3, dsegs, speed, usualSpeed, freeFlow, startPosLat, startPosLon, frc, fow, bearing,dfp;
	//public double dfp;
	
	public AggFiles(String timeAgg, String roadNumberAgg, String delayAgg, String lengthAgg){
		super();
		this.timeAgg = timeAgg;
		this.roadNumberAgg = roadNumberAgg;
		this.delayAgg = delayAgg;
		this.lengthAgg = lengthAgg;
	}
	
	public String getTimeAgg() {
		return timeAgg;
	}
	public void setTimeAgg(String timeAgg) {
		this.timeAgg = timeAgg;
	}
	public String getRoadNumberAgg() {
		return roadNumberAgg;
	}
	public void setRoadNumberAgg(String roadNumberAgg) {
		this.roadNumberAgg = roadNumberAgg;
	}
	public String getDelayAgg() {
		return delayAgg;
	}
	public void setDelayAgg(String delayAgg){
		this.delayAgg = delayAgg;
	}
	public int delayAggToInt() {
		return Integer.parseInt(delayAgg);
	}
	public String getLengthAgg() {
		return lengthAgg;
	}
	public void setLengthAgg(String lengthAgg){
		this.lengthAgg = lengthAgg;
	}
	public int lengthAggToInt() {
		return Integer.parseInt(lengthAgg);
	}
	
	//@ overide
	public String toString() {
		return timeAgg + "," + roadNumberAgg + "," + delayAgg + "," + lengthAgg;
	}


}
