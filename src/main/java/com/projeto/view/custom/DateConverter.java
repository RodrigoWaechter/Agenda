package com.projeto.view.custom;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;

public class DateConverter {

	public LocalDateTime convertToLocalDateTime(Date data, Calendar hora) {
		LocalDateTime localDateTime = LocalDateTime.ofInstant(data.toInstant(), hora.getTimeZone().toZoneId())
				.withHour(hora.get(Calendar.HOUR_OF_DAY)).withMinute(hora.get(Calendar.MINUTE));
		return localDateTime;
	}
}