package io.github.joycejoyce.fileprocessor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang3.StringUtils;

public class DateObj {

	private LocalDate dateObj;

	public DateObj(String date) {
		this.dateObj = LocalDate.parse(date, DateTimeFormatter.BASIC_ISO_DATE);
	}

	public String getYear() {
		return String.valueOf(dateObj.getYear());
	}

	public String getMonth() {
		return StringUtils.leftPad(String.valueOf(dateObj.getMonthValue()), 2, '0');
	}

	public String getDay() {
		return StringUtils.leftPad(String.valueOf(dateObj.getDayOfMonth()), 2, '0');
	}

}
