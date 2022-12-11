package com.mmit.model;

public class Librarian {
	private int id;
	private String email, password, nrcno, phone;

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

	public String getNrcno() {
		return nrcno;
	}

	public void setNrcno(String nrcno) {
		this.nrcno = nrcno;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}
