package Implements;

public class GeoCoordinate {
	
	private final float lat;
	
	private final float lon;
	
	public GeoCoordinate(float lat, float lon) {
		this.lat = lat;
		this.lon = lon;
	}
	
	public GeoCoordinate(String geo) {
		assert geo.contains(",") : "cannot parse "+geo;
		String[] split = geo.split(",");
		this.lat = Float.parseFloat(split[0]);
		this.lon = Float.parseFloat(split[1]);
	}

	public float getLat() {
		return lat;
	}

	public float getLon() {
		return lon;
	}

}
