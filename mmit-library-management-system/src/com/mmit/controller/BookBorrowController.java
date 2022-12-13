package com.mmit.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.mmit.Start;
import com.mmit.model.Author;
import com.mmit.model.Book;
import com.mmit.model.Category;
import com.mmit.model.Librarian;
import com.mmit.model.Member;
import com.mmit.model.Transaction;
import com.mmit.util.DatabaseHandler;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;

public class BookBorrowController implements Initializable {
	List<Book> book_list;
	List<Member> member_list;
	List<Transaction> transaction_list;
	Book selected_book = new Book();

	@FXML
	private TableColumn<Book, String> col_author;

	@FXML
	private TableColumn<Book, String> col_available;

	@FXML
	private TableColumn<Book, String> col_category;

	@FXML
	private TableColumn<Book, Integer> col_code;

	@FXML
	private TableColumn<Book, String> col_librarian;

	@FXML
	private TableColumn<Book, LocalDate> col_publish_date;

	@FXML
	private TableColumn<Book, String> col_title;

	@FXML
	private TableView<Book> tbl_book;

	@FXML
	private TextField txt_card_id;

	@FXML
	private TextField txt_code;

	@FXML
	void btn_back_click(ActionEvent event) throws IOException {
		Start.changeScene("view/Book.fxml");
	}

	@FXML
	void btn_borrow_click(ActionEvent event) {
		try {
			String card_id = txt_card_id.getText();
			String code = txt_code.getText();

			if (card_id.isEmpty())
				Start.showAlert(AlertType.ERROR, "Insert member card number!");
			else if (code.isEmpty())
				Start.showAlert(AlertType.ERROR, "Insert book code or select from table!");
			else {
				List<Member> exist_member = member_list.stream()
						.filter(member -> member.getCard_id() == Integer.parseInt(card_id))
						.toList();
				
				List<Book> exist_book = book_list.stream()
						.filter(book -> book.getCode() == Integer.parseInt(code))
						.toList();
				
				// list of existed member borrowed list
				List<Transaction> exist_transaction = transaction_list.stream()
						.filter(t -> t.getMember().getCard_id() == exist_member.get(0).getCard_id())
						.toList();

				// if new member borrow book 'register to card' alert
				if (exist_member.size() > 0) {
					int i = LocalDate.now().compareTo(exist_member.get(0).getExpired_date());
					if (i <= 0) {

						// if search book is having in book list and available is "Yes"
						if (exist_book.size() > 0) {
							if (exist_book.get(0).getAvailable().equals("Yes")) {

								// if exist member search transaction and alert their due date
								if (exist_transaction.size() > 0) {
									if (exist_transaction.get(0).getMember().getCard_id() == exist_member.get(0)
											.getCard_id()) {

										// alert their borrowed book information
										int borrowed_book = exist_transaction.size();
										int j = LocalDate.now().compareTo(exist_transaction.get(0).getDue_date());

										// only alert to return book when early book borrowed to remember due date
										if (j <= 0) {
											Start.showAlert(AlertType.INFORMATION,
													"In your transaction, you borrowed '" + borrowed_book
															+ "' book. \nRemember to return the first book until '"
															+ exist_transaction.get(0).getDue_date() + "'.");

										} else { // alert to return book when borrowed book's due date is over
											Start.showAlert(AlertType.ERROR, "In '"
													+ exist_transaction.get(0).getBorrow_date()
													+ "', you borrowed book code with '"
													+ exist_transaction.get(0).getBookCode()
													+ "', that book's due date is over. \nYou can't borrow any book if you not return that book. \nPress 'OK' to return book!");
											try {
												Start.changeScene("view/BookReturn.fxml");
											} catch (IOException e) {
												Start.showAlert(AlertType.ERROR, e.getMessage());

											}
										}
									}
								}

								try {

									Book book = new Book();
									book.setCode(Integer.parseInt(code));
									book.setAvailable("No");

									Member member = new Member();
									member.setCard_id(Integer.parseInt(card_id));

									Transaction transaction = new Transaction();
									transaction.setBook(book);
									transaction.setMember(member);
//									transaction.setBorrow_date(LocalDate.now());
									transaction.setDue_date(LocalDate.now().plusDays(5));
									transaction.setFees(0);
									transaction.setLibrarian(Start.librarian_login);

									DatabaseHandler.editBookAvailable(book); // edit book available to 'No'
									DatabaseHandler.borrowBook(transaction);

									Optional<ButtonType> result = Start.showAlert(AlertType.CONFIRMATION,
											"Successful borrowed '" + exist_book.get(0).getTitle()
													+ "' book \nPress 'OK' to see in transaction.");
									if (result.get() == ButtonType.OK) {
										Start.changeScene("view/Transaction.fxml");
									} else
										loadBook();
								} catch (Exception e) {
									Start.showAlert(AlertType.ERROR, e.getMessage());
									}
							} else
								Start.showAlert(AlertType.INFORMATION, "Book is not available now!");
						} else
							Start.showAlert(AlertType.ERROR, "No book found with book code : " + code);
					} else
						Start.showAlert(AlertType.ERROR, "Your Card is expired! \nCan't borrow book anymore!");
				} else {
					Optional<ButtonType> result = Start.showAlert(AlertType.CONFIRMATION,
							"Not found user with this registered ID! \nPress 'OK' to go register page and register a new member");
					if (result.get() == ButtonType.OK) {
						Start.changeScene("view/Member.fxml");
					}
				}
			}
		} catch (Exception e) {
			Start.showAlert(AlertType.ERROR, e.getMessage());
		}
	}

	private void loadBook() throws Exception {
		book_list = DatabaseHandler.showAllBook();
		member_list = DatabaseHandler.showAllMember();
		transaction_list = DatabaseHandler.showAllTransaction();

		tbl_book.setItems(FXCollections.observableArrayList(book_list));
	}

	@FXML
	void btn_logout_click(ActionEvent event) {
		Start.logoutButton();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			col_code.setCellValueFactory(new PropertyValueFactory<>("code"));
			col_title.setCellValueFactory(new PropertyValueFactory<>("title"));
			col_publish_date.setCellValueFactory(new PropertyValueFactory<>("publish_date"));
			col_author.setCellValueFactory(new PropertyValueFactory<>("authorName"));
			col_category.setCellValueFactory(new PropertyValueFactory<>("categoryName"));
			col_librarian.setCellValueFactory(new PropertyValueFactory<>("librarianEmail"));
			col_available.setCellValueFactory(new PropertyValueFactory<>("available"));

			loadBook();

			tbl_book.getSelectionModel().selectedItemProperty().addListener((obs, old_select, new_select) -> {
				if (new_select != null) {
					selected_book = tbl_book.getSelectionModel().getSelectedItem();
					txt_code.setText(selected_book.getCode() + "");
				}
			});
		} catch (Exception e) {
			Start.showAlert(AlertType.ERROR, e.getMessage());
		}
	}
}
