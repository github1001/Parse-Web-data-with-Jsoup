package com.model;

public class Holidays {

	Integer year;
	String holidays;
	String date;

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public String getHolidays() {
		return holidays;
	}

	public void setHolidays(String holidays) {
		this.holidays = holidays;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Holidays [year=" + year + ", holidays=" + holidays + ", date=" + date + "]";
	}

}