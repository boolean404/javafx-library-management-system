package com.mmit.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class TransactionController {

	@FXML
	private TableColumn<?, ?> col_book_id;

	@FXML
	private TableColumn<?, ?> col_borrow_date;

	@FXML
	private TableColumn<?, ?> col_card_id;

	@FXML
	private TableColumn<?, ?> col_due_date;

	@FXML
	private TableColumn<?, ?> col_fees;

	@FXML
	private TableColumn<?, ?> col_id;

	@FXML
	private TableColumn<?, ?> col_librarian;

	@FXML
	private TableColumn<?, ?> col_return_date;

	@FXML
	private TableView<?> tbl_transaction;

	@FXML
	void btn_back_click(ActionEvent event) {

	}

	@FXML
	void btn_clear_all_click(ActionEvent event) {

	}

	@FXML
	void btn_logout_click(ActionEvent event) {

	}

}
