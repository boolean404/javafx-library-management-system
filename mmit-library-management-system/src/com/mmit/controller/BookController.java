package com.mmit.controller;

import java.io.IOException;

import com.mmit.Start;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class BookController {

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
    void delete_book_click(MouseEvent event) throws IOException {
    	Start.changeScene("view/BookEditAndDelete.fxml");
    }

    @FXML
    void edit_book_click(MouseEvent event) throws IOException {
    	Start.changeScene("view/BookEditAndDelete.fxml");
    }

    @FXML
    void search_book_click(MouseEvent event) throws IOException {
    	Start.changeScene("view/BookSearch.fxml");
    }

}
