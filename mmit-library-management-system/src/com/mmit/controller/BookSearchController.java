package com.mmit.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class BookSearchController {

    @FXML
    private TableColumn<?, ?> col_author;

    @FXML
    private TableColumn<?, ?> col_available;

    @FXML
    private TableColumn<?, ?> col_category;

    @FXML
    private TableColumn<?, ?> col_code;

    @FXML
    private TableColumn<?, ?> col_librarian;

    @FXML
    private TableColumn<?, ?> col_publish_date;

    @FXML
    private TableColumn<?, ?> col_title;

    @FXML
    private TableView<?> tbl_book_search;

    @FXML
    private TextField txt_author;

    @FXML
    private TextField txt_category;

    @FXML
    private TextField txt_title;

    @FXML
    void btn_back_click(ActionEvent event) {

    }

    @FXML
    void btn_logout_click(ActionEvent event) {

    }

    @FXML
    void btn_search_click(ActionEvent event) {

    }

}
