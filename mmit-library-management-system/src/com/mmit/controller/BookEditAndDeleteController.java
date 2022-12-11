package com.mmit.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.mmit.Start;
import com.mmit.model.Author;
import com.mmit.model.Book;
import com.mmit.model.Category;
import com.mmit.util.DatabaseHandler;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class BookEditAndDeleteController implements Initializable {
	private List<Author> author_list;
	private List<Category> category_list;
	private Book book = new Book();

	@FXML
	private ComboBox<String> cbo_author;

	@FXML
	private ComboBox<String> cbo_category;

	@FXML
	private TextField txt_code;

	@FXML
	private DatePicker txt_publish_date;

	@FXML
	private TextField txt_search_code;

	@FXML
	private TextField txt_title;

	@FXML
	void btn_delete_click(ActionEvent event) {
		try {
			String search_code = txt_search_code.getText();

			if (search_code.isEmpty()) {
				Start.showAlert(AlertType.INFORMATION, "Insert book code and search first to delete!");
				return;
			}
			String code = txt_code.getText();
			DatabaseHandler.deleteBook(Integer.parseInt(code));
			Optional<ButtonType> result = Start.showAlert(AlertType.CONFIRMATION, "Are you sure to delete this book?");
			if (result.get() == ButtonType.OK) {
				Start.showAlert(AlertType.INFORMATION, "Successful deleted book!");
				clearInputData();
			}
		} catch (Exception e) {
			Start.showAlert(AlertType.ERROR, e.getMessage());
		}
	}

	private void clearInputData() {
		txt_code.setText(null);
		txt_title.setText(null);
		txt_publish_date.setValue(null);
		cbo_author.setValue(null);
		cbo_category.setValue(null);
	}

	@FXML
	void btn_back_click(ActionEvent event) throws IOException {
		Start.changeScene("view/Book.fxml");
	}

	@FXML
	void btn_edit_click(ActionEvent event) {
		try {
			String search_code = txt_search_code.getText();
			if (search_code.isEmpty()) {
				Start.showAlert(AlertType.INFORMATION, "Insert book code and search first to edit!");
				return;
			}
			book.setTitle(txt_title.getText());
			book.setPublish_date(txt_publish_date.getValue());

			int selected_author_index = cbo_author.getSelectionModel().getSelectedIndex();
			int selected_category_index = cbo_category.getSelectionModel().getSelectedIndex();

			book.setAuthor(author_list.get(selected_author_index));
			book.setCategory(category_list.get(selected_category_index));

			DatabaseHandler.editBook(book);
			Start.showAlert(AlertType.INFORMATION, "Successful edited book!");

		} catch (Exception e) {
			Start.showAlert(AlertType.ERROR, e.getMessage());
		}
	}

	@FXML
	void btn_logout_click(ActionEvent event) {
		Start.logoutButton();
	}

	@FXML
	void btn_search_click(ActionEvent event) {
		try {
			String code = txt_search_code.getText();
			if (code.isEmpty()) {
				Start.showAlert(AlertType.ERROR, "Enter book code!");
				return;
			}
			book = DatabaseHandler.searchBookByCode(Integer.parseInt(code));
			if (book == null)
				Start.showAlert(AlertType.WARNING, "No book with this code number : " + code);
			else {
				txt_code.setText(book.getCode() + "");
				txt_title.setText(book.getTitle());
				txt_publish_date.setValue(book.getPublish_date());
				cbo_author.getSelectionModel().select(book.getAuthorName());
				cbo_category.getSelectionModel().select(book.getCategoryName());

			}
		} catch (Exception e) {
			Start.showAlert(AlertType.ERROR, e.getMessage());
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			author_list = DatabaseHandler.showAllAuthor();
			category_list = DatabaseHandler.showAllCategory();

			List<String> author_name_list = author_list.stream().map(auth -> auth.getName()).toList();
			List<String> category_name_list = category_list.stream().map(cat -> cat.getName()).toList();
			cbo_author.setItems(FXCollections.observableArrayList(author_name_list));
			cbo_category.setItems(FXCollections.observableArrayList(category_name_list));
		} catch (Exception e) {
			Start.showAlert(AlertType.ERROR, e.getMessage());

		}
	}

}
