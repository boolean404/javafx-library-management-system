package com.mmit.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import com.mmit.Start;
import com.mmit.model.Book;
import com.mmit.util.DatabaseHandler;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;

public class BookSearchController implements Initializable {

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
	private TextField txt_author;

	@FXML
	private TextField txt_category;

	@FXML
	private TextField txt_title;

	@FXML
	void btn_back_click(ActionEvent event) throws IOException {
		Start.changeScene("view/Book.fxml");
	}

	@FXML
	void btn_logout_click(ActionEvent event) {
		Start.logoutButton();
	}

	@FXML
	void btn_search_click(ActionEvent event) {
		try {
			String title = txt_title.getText();
			String author = txt_author.getText();
			String category = txt_category.getText();

			List<Book> book_list = DatabaseHandler.searchBook(title, author, category);
			tbl_book.setItems(FXCollections.observableArrayList(book_list));
		} catch (Exception e) {
			Start.showAlert(AlertType.ERROR, e.getMessage());
		}
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

		} catch (Exception e) {
			Start.showAlert(AlertType.ERROR, e.getMessage());
		}
	}

}
