package com.mmit.model;

import java.time.LocalDate;

public class Member {
	private int card_id, roll_no;
	private String name, year, academic_year;
	private LocalDate created_date, expired_date;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCard_id() {
		return card_id;
	}

	public void setCard_id(int card_id) {
		this.card_id = card_id;
	}

	public int getRoll_no() {
		return roll_no;
	}

	public void setRoll_no(int roll_no) {
		this.roll_no = roll_no;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getAcademic_year() {
		return academic_year;
	}

	public void setAcademic_year(String string) {
		this.academic_year = string;
	}

	public LocalDate getCreated_date() {
		return created_date;
	}

	public void setCreated_date(LocalDate created_date) {
		this.created_date = created_date;
	}

	public LocalDate getExpired_date() {
		return expired_date;
	}

	public void setExpired_date(LocalDate expired_date) {
		this.expired_date = expired_date;
	}

}
