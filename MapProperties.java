package Implements;

public class MapProperties {

	private static final int START_COLUMN = 11;
	private static final int END_COLUMN = 12;

	private final GeoCoordinate start;

	private final GeoCoordinate end;

	public MapProperties(String[] line) {
		this.start = new GeoCoordinate(line[START_COLUMN]);
		this.end = new GeoCoordinate(line[END_COLUMN]);
	}

	public GeoCoordinate getStart() {
		return start;
	}

	public GeoCoordinate getEnd() {
		return end;
	}

}
