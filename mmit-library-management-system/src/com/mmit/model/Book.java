package com.mmit.model;

import java.time.LocalDate;

public class Book {
	private int code;
	private String title, available;
	private LocalDate publish_date;
	private Author author;
	private Category category;
	private Librarian librarian;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAvailable() {
		return available;
	}

	public void setAvailable(String available) {
		this.available = available;
	}

	public LocalDate getPublish_date() {
		return publish_date;
	}

	public void setPublish_date(LocalDate publish_date) {
		this.publish_date = publish_date;
	}

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Librarian getLibrarian() {
		return librarian;
	}

	public void setLibrarian(Librarian librarian) {
		this.librarian = librarian;
	}

	public String getAuthorName() {
		return this.author.getName();
	}

	public String getCategoryName() {
		return this.category.getName();
	}

	public String getLibrarianEmail() {
		return this.librarian.getEmail();
	}

}
