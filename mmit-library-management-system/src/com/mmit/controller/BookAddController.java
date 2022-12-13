package com.mmit.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
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

public class BookAddController implements Initializable {
	List<Author> author_list;
	List<Category> category_list;

	@FXML
	private TextField txt_code;

	@FXML
	private TextField txt_title;

	@FXML
	private ComboBox<String> cbo_author;

	@FXML
	private ComboBox<String> cbo_category;

	@FXML
	private DatePicker txt_publish_date;

	@FXML
	void btn_add_click(ActionEvent event) {
		try {
			// get data
			String code = txt_code.getText();
			String title = txt_title.getText();
			LocalDate publish_date = txt_publish_date.getValue();

			if (code.isEmpty())
				Start.showAlert(AlertType.ERROR, "Book code must not be Empty!");
			else if (title.isEmpty())
				Start.showAlert(AlertType.ERROR, "Book title must not be Empty!");
			else if (publish_date == null)
				Start.showAlert(AlertType.ERROR, "Publish date must not be Empty!");
			else if (cbo_author.getValue() == null)
				Start.showAlert(AlertType.ERROR, "Choose Author!");
			else if (cbo_category.getValue() == null)
				Start.showAlert(AlertType.ERROR, "Choose Category!");
			else {
				int author_selected_index = cbo_author.getSelectionModel().getSelectedIndex();
				int category_selected_index = cbo_category.getSelectionModel().getSelectedIndex();

				Author selected_author = author_list.get(author_selected_index);
				Category selected_category = category_list.get(category_selected_index);

				// create obj and assign data
				Book book = new Book();
				book.setCode(Integer.parseInt(code));
				book.setTitle(title);
				book.setPublish_date(publish_date);
				book.setAuthor(selected_author);
				book.setCategory(selected_category);
				book.setLibrarian(Start.librarian_login);
				book.setAvailable("Yes");

				// save to database
				DatabaseHandler.addBook(book);

				// show msg box
				Start.showAlert(AlertType.INFORMATION, "Successful added a new book!");

				// reset data
				clearInputData();
			}
		} catch (Exception e) {
			Start.showAlert(AlertType.ERROR, e.getMessage());
		}
	}

	@FXML
	void btn_reset_click(ActionEvent event) {
		clearInputData();
	}

	@FXML
	void btn_back_click(ActionEvent event) throws IOException {
		Start.changeScene("view/Book.fxml");
	}

	@FXML
	void btn_logout_click(ActionEvent event) {
		Start.logoutButton();
	}

	private void clearInputData() {
		txt_code.setText(null);
		txt_title.setText(null);
		txt_publish_date.setValue(null);
		cbo_author.setValue(null);
		cbo_category.setValue(null);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			// author list
			author_list = DatabaseHandler.showAllAuthor();
			List<String> author_name_list = author_list.stream().map(author -> author.getName()).toList();
			cbo_author.setItems(FXCollections.observableArrayList(author_name_list));

			// category list
			category_list = DatabaseHandler.showAllCategory();
			List<String> category_name_list = category_list.stream().map(category -> category.getName()).toList();
			cbo_category.setItems(FXCollections.observableArrayList(category_name_list));

		} catch (Exception e) {
			Start.showAlert(AlertType.ERROR, e.getMessage());
		}
	}

}
