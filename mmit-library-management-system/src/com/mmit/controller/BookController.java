package com.mmit.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.mmit.Start;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class BookController implements Initializable {

	@FXML
	private Text txt_current_libraian;

	@FXML
	void add_book_click(MouseEvent event) throws IOException {
		Start.changeScene("view/BookAdd.fxml");
	}

	@FXML
	void book_list_click(MouseEvent event) throws IOException {
		Start.changeScene("view/BookList.fxml");
	}

	@FXML
	void borrow_book_click(MouseEvent event) throws IOException {
		Start.changeScene("view/BookBorrow.fxml");
	}

	@FXML
	void btn_back_click(ActionEvent event) throws IOException {
		Start.changeScene("view/Main.fxml");
	}

	@FXML
	void btn_logout_click(ActionEvent event) {
		Start.logoutButton();
	}

	@FXML
	void return_book_click(MouseEvent event) throws IOException {
		Start.changeScene("view/BookReturn.fxml");
	}

	@FXML
	void edit_and_delete_book_click(MouseEvent event) throws IOException {
		Start.changeScene("view/BookEditAndDelete.fxml");
	}

	@FXML
	void search_book_click(MouseEvent event) throws IOException {
		Start.changeScene("view/BookSearch.fxml");
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		txt_current_libraian.setText(Start.librarian_login.getEmail()); 
	}

}
