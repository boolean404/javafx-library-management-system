package com.mmit.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class BookListController {

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
    private TableView<?> tbl_book;

    @FXML
    void btn_back_click(ActionEvent event) {

    }

    @FXML
    void btn_logout_click(ActionEvent event) {

    }

}
