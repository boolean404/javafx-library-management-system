package com.mmit.model;

public class Librarian {
	private int id;
	private String email, password, nrc_no, phone;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNrc_no() {
		return nrc_no;
	}

	public void setNrc_no(String nrc_no) {
		this.nrc_no = nrc_no;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}
