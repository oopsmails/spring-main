package com.ziyang.testjava;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;

public class DataTimeDemo {

	public static void main(String[] args) {
		LocalDate date = LocalDate.now();
		System.out.println("date = " + date);

		LocalDate dateOf = LocalDate.of(1900, Month.FEBRUARY, 28);
		System.out.println("dateOf = " + dateOf);

		LocalTime time = LocalTime.now();
		System.out.println("time = " + time);

		LocalTime timeWithZone = LocalTime.now(ZoneId.of("Africa/Cairo"));
		System.out.println("timeWithZone = " + timeWithZone);

		LocalTime timeOf = LocalTime.of(12, 35, 1, 998);
		System.out.println("timeOf = " + timeOf);
		
		Instant instant = Instant.now();
		System.out.println("instant = " + instant);
		
		LocalDateTime dateTime = LocalDateTime.now(ZoneId.of("GMT"));
		System.out.println("dateTime = " + dateTime);

//		ZoneId.getAvailableZoneIds().stream().forEach(System.out::println);
//
//		Set<String> timezones = new TreeSet<String>(ZoneId.getAvailableZoneIds());
//		timezones.forEach(o -> {
//			System.out.println("" + o);
//		});
	}

}
