package com.mmit.model;

import java.time.LocalDate;

public class Transaction {
	private int id;
	private Category category;
	private Book book;
	private LocalDate borrow_date, due_date, return_date;
	private float fees;
	private Librarian librarian;
	private Member member;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public LocalDate getBorrow_date() {
		return borrow_date;
	}

	public void setBorrow_date(LocalDate borrow_date) {
		this.borrow_date = borrow_date;
	}

	public LocalDate getDue_date() {
		return due_date;
	}

	public void setDue_date(LocalDate due_date) {
		this.due_date = due_date;
	}

	public LocalDate getReturn_date() {
		return return_date;
	}

	public void setReturn_date(LocalDate return_date) {
		this.return_date = return_date;
	}

	public float getFees() {
		return fees;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public void setFees(float fees) {
		this.fees = fees;
	}

	public Librarian getLibrarian() {
		return librarian;
	}

	public void setLibrarian(Librarian librarian) {
		this.librarian = librarian;
	}

	public int getMemberCardId() {
		return this.member.getCard_id();
	}

	public int getBookCode() {
		return this.book.getCode();
	}

	public String getLibrarianEmail() {
		return this.librarian.getEmail();
	}

}
