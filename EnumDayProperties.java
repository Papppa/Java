package Implements;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.time.ZoneId;


public class EnumDayProperties {
	
	public static String getWeekday (Instant day){
		String dayProperty = null;
		LocalDateTime ldt = LocalDateTime.ofInstant(day, ZoneId.of("Zulu"));
		LocalDate ld = LocalDate.parse(ldt.toLocalDate().toString());
		int ordinalNumberDay = getOrdinalFromWeekday(ld.getDayOfWeek().toString());
		if(ordinalNumberDay == 5 || ordinalNumberDay == 6)
			dayProperty = "WE";
		else
		dayProperty = "WD";
		return dayProperty;
	}
	
	public static String getPeak (Instant time){
		String peakProperty = "EP";
		LocalDateTime ldt = LocalDateTime.ofInstant(time, ZoneId.of("Zulu"));
		LocalTime lt = LocalTime.parse(ldt.toLocalTime().toString());
		if (TimeSliceIsMorning(lt.toString()) == true){
			peakProperty = "MP";
		}	
		return peakProperty;
	}
	
	public enum Weekday{
		MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
	}
	
	public enum Peak{
		MP, EP, NP
	}
	
	public static String getOrdinalFromTime (LocalTime time){
		String peak = null;
		if(time.isAfter(LocalTime.parse("06:00:00")) && time.isBefore(LocalTime.parse("12:00:00")))
			peak = "MP";
		else if(time.isAfter(LocalTime.parse("12:00:00")) && time.isBefore(LocalTime.parse("18:00:00")))
			peak = "EP";
		peak = "NP";	
		return peak;
	}
	
	public static boolean TimeSliceIsMorning (String string){
		LocalTime morningEnd = LocalTime.parse("12:00:00");
		LocalTime morningStart = LocalTime.parse("06:00:00");
		LocalTime time = LocalTime.parse(string);
		return time.isBefore(morningEnd) && time.isAfter(morningStart);
	}
	
	private static int getOrdinalFromWeekday( String name )
	{
	  try {
	    return Weekday.valueOf( name ).ordinal();
	  }
	  catch ( IllegalArgumentException e ) {
	    return 9999;
	  }
	}
}
