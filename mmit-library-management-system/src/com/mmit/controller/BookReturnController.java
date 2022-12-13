package com.mmit.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import com.mmit.Start;
import com.mmit.model.Book;
import com.mmit.model.Member;
import com.mmit.model.Transaction;
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

public class BookReturnController implements Initializable {
	List<Transaction> transaction_list;
	Transaction selected_transaction = new Transaction();

	@FXML
	private TableColumn<Transaction, Integer> col_book_id;

	@FXML
	private TableColumn<Transaction, LocalDate> col_borrow_date;

	@FXML
	private TableColumn<Transaction, Integer> col_card_id;

	@FXML
	private TableColumn<Transaction, LocalDate> col_due_date;

	@FXML
	private TableColumn<Transaction, Float> col_fees;

	@FXML
	private TableColumn<Transaction, Integer> col_id;

	@FXML
	private TableColumn<Transaction, String> col_librarian;

	@FXML
	private TableColumn<Transaction, LocalDate> col_return_date;

	@FXML
	private TableView<Transaction> tbl_transaction;

	@FXML
	private TextField txt_card_id;

	@FXML
	private TextField txt_code;

	@FXML
	void btn_return_click(ActionEvent event) {
		try {
			String card_id = txt_card_id.getText();
			String code = txt_code.getText();
			if (card_id.isEmpty())
				Start.showAlert(AlertType.ERROR, "Insert member card ID and search!");
			else if (code.isEmpty())
				Start.showAlert(AlertType.ERROR, "Insert book code or select from table!");
			else {
				List<Transaction> exist_transaction = transaction_list.stream()
						.filter(tl -> tl.getMember().getCard_id() == Integer.parseInt(card_id)
								&& tl.getBookCode() == Integer.parseInt(code))
						.toList();

				if (exist_transaction.size() > 0) {
					LocalDate due_date = exist_transaction.get(0).getDue_date();
					float fees = exist_transaction.get(0).getFees();
					int late_date = LocalDate.now().compareTo(due_date);

					// calculation of fees 100/day
					for (int i = 0; i < late_date; i++) {
						fees = fees + 100;
					}
					
					Book book = new Book();
					book.setCode(Integer.parseInt(code));
					book.setAvailable("Yes");

					Member member = new Member();
					member.setCard_id(Integer.parseInt(card_id));

					Transaction transaction = new Transaction();
					transaction.setBook(book);
					transaction.setMember(member);
					transaction.setReturn_date(LocalDate.now());
					transaction.setFees(fees);

					DatabaseHandler.returnBook(transaction); // book's return date set to localdate set fees
					DatabaseHandler.editBookAvailable(book); // book's available set to 'Yes'
					
					Start.showAlert(AlertType.INFORMATION, "Successful returned book & Updated book available!");
					loadTransaction();
				} else
					Start.showAlert(AlertType.ERROR, "No found transaction match with that card ID and book code!");
			}

		} catch (Exception e) {
			Start.showAlert(AlertType.ERROR, e.getMessage());
		}
	}

	@FXML
	void btn_search_click(ActionEvent event) {
		try {
			String card_id = txt_card_id.getText();
			if (card_id.isEmpty())
				Start.showAlert(AlertType.ERROR, "Insert member card ID and search!");
			else {
				List<Transaction> transaction_list = DatabaseHandler
						.searchTransactionByCardId(Integer.parseInt(card_id));
				tbl_transaction.setItems(FXCollections.observableArrayList(transaction_list));
				
				// alert msg when no find with that ID
				if(transaction_list.size()==0)
					Start.showAlert(AlertType.ERROR, "Card holder doesn't borrowed any book!");
			}
		} catch (Exception e) {
			Start.showAlert(AlertType.ERROR, e.getMessage());
		}
	}

	@FXML
	void btn_back_click(ActionEvent event) throws IOException {
		Start.changeScene("view/Book.fxml");
	}

	@FXML
	void btn_logout_click(ActionEvent event) {
		Start.logoutButton();
	}

	private void loadTransaction() throws Exception {
		transaction_list = DatabaseHandler.showAllTransaction();
		tbl_transaction.setItems(FXCollections.observableArrayList(transaction_list));
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
			col_card_id.setCellValueFactory(new PropertyValueFactory<>("memberCardId"));
			col_book_id.setCellValueFactory(new PropertyValueFactory<>("bookCode"));
			col_borrow_date.setCellValueFactory(new PropertyValueFactory<>("borrow_date"));
			col_due_date.setCellValueFactory(new PropertyValueFactory<>("due_date"));
			col_return_date.setCellValueFactory(new PropertyValueFactory<>("return_date"));
			col_fees.setCellValueFactory(new PropertyValueFactory<>("fees"));
			col_librarian.setCellValueFactory(new PropertyValueFactory<>("librarianEmail"));

			loadTransaction();

			tbl_transaction.getSelectionModel().selectedItemProperty().addListener((obs, old_select, new_select) -> {
				if (new_select != null) {
					selected_transaction = tbl_transaction.getSelectionModel().getSelectedItem();
					txt_code.setText(selected_transaction.getBook().getCode() + "");
				}
			});

		} catch (Exception e) {
			Start.showAlert(AlertType.ERROR, e.getMessage());
		}
	}
}
