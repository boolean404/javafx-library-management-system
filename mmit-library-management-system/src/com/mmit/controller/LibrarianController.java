package com.mmit.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.mmit.Start;
import com.mmit.model.Librarian;
import com.mmit.util.DatabaseHandler;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class LibrarianController implements Initializable {
	Librarian selected_librarian = new Librarian();

	@FXML
	private TableColumn<Librarian, String> col_email;

	@FXML
	private TableColumn<Librarian, Integer> col_id;

	@FXML
	private TableColumn<Librarian, String> col_nrc_no;

	@FXML
	private TableColumn<Librarian, String> col_password;

	@FXML
	private TableColumn<Librarian, String> col_phone;

	@FXML
	private TableView<Librarian> tbl_librarian;

	@FXML
	private TextField txt_email;

	@FXML
	private TextField txt_id_search;

	@FXML
	private TextField txt_nrcno;

	@FXML
	private TextField txt_password;

	@FXML
	private TextField txt_phone;

	@FXML
	void btn_add_click(ActionEvent event) {
		try {
			String email = txt_email.getText();
			String password = txt_password.getText();
			String nrc_no = txt_nrcno.getText();
			String phone = txt_phone.getText();

			if (email.isEmpty())
				Start.showAlert(AlertType.ERROR, "Email must not be Empty!");
			else if (password.isEmpty())
				Start.showAlert(AlertType.ERROR, "Password must not be Empty!");
			else if (nrc_no.isEmpty())
				Start.showAlert(AlertType.ERROR, "NRC must not be Empty!");
			else if (phone.isEmpty())
				Start.showAlert(AlertType.ERROR, "Phone must not be Empty!");
			else if (email.isEmpty() && password.isEmpty() && nrc_no.isEmpty() && phone.isEmpty())
				Start.showAlert(AlertType.ERROR, "Input data must not be Empty!");
			else {
				Librarian new_librarian = new Librarian();
				new_librarian.setEmail(email);
				new_librarian.setPassword(password);
				new_librarian.setNrcno(nrc_no);
				new_librarian.setPhone(phone);

				DatabaseHandler.librarianRegister(new_librarian);
				Start.showAlert(AlertType.INFORMATION, "Successfully added a new librarian!");

				clearInputData();
				loadLibrarian();
			}
		} catch (Exception e) {
			Start.showAlert(AlertType.ERROR, e.getMessage());
		}
	}

	@FXML
	void btn_back_click(ActionEvent event) throws IOException {
		Start.changeScene("view/Main.fxml");
	}

	@FXML
	void btn_delete_click(ActionEvent event) {
		if (Start.librarian_login.getEmail().equals("admin@mmit.com")) {
			if (selected_librarian.getId() != Start.librarian_login.getId()) {
				try {
					Optional<ButtonType> result = Start.showAlert(AlertType.CONFIRMATION,
							"Are you sure want to delete this librarian?");
					if (result.get() == ButtonType.OK) {
						DatabaseHandler.deleteLibrarian(selected_librarian.getId());
						clearInputData();
						loadLibrarian();
					}
				} catch (Exception e) {
					Start.showAlert(AlertType.ERROR, e.getMessage());
				}
			} else
				Start.showAlert(AlertType.ERROR, "System need 1 admin user! \nDenied to delete admin!");

		} else
			Start.showAlert(AlertType.ERROR, "Permission Denied! \nOnly Admin can delete librarians!");

	}

	@FXML
	void btn_edit_click(ActionEvent event) {
		if (Start.librarian_login.getEmail().equals("admin@mmit.com")) {
			try {
				selected_librarian.setEmail(txt_email.getText());
				selected_librarian.setPassword(txt_password.getText());
				selected_librarian.setNrcno(txt_nrcno.getText());
				selected_librarian.setPhone(txt_phone.getText());

				DatabaseHandler.editLibrarian(selected_librarian);

				Start.showAlert(AlertType.INFORMATION, "Successful edited!");
				clearInputData();
				loadLibrarian();

			} catch (Exception e) {
				Start.showAlert(AlertType.ERROR, e.getMessage());
			}

		} else if (Start.librarian_login.getId() == selected_librarian.getId()) {
			try {
				selected_librarian.setEmail(txt_email.getText());
				selected_librarian.setPassword(txt_password.getText());
				selected_librarian.setNrcno(txt_nrcno.getText());
				selected_librarian.setPhone(txt_phone.getText());

				DatabaseHandler.editLibrarian(selected_librarian);

				Start.showAlert(AlertType.INFORMATION, "Successful edited your information!");
				clearInputData();
				loadLibrarian();

			} catch (Exception e) {
				Start.showAlert(AlertType.ERROR, e.getMessage());
			}

		} else
			Start.showAlert(AlertType.ERROR, """
					Permission Denied!
					You can't edit other librarians except from Admin
					You can edit only your information!
											""");

	}

	private void clearInputData() {
		txt_id_search.setText(null);
		txt_email.setText(null);
		txt_password.setText(null);
		txt_nrcno.setText(null);
		txt_phone.setText(null);

	}

	@FXML
	void btn_logout_click(ActionEvent event) {
		Start.logoutButton();
	}

	@FXML
	void btn_search_click(ActionEvent event) {
		try {
			var id = txt_id_search.getText();
			if (id.isEmpty()) {
				Start.showAlert(AlertType.ERROR, "Enter librarian ID");
				return;
			}
			Librarian librarian = DatabaseHandler.searchLibrarian(Integer.parseInt(id));
			if (librarian == null) {
				Start.showAlert(AlertType.WARNING, "No Librarian with inputed ID");
			} else {
				txt_email.setText(librarian.getEmail());
				txt_password.setText(librarian.getPassword());
				txt_nrcno.setText(librarian.getNrcno());
				txt_phone.setText(librarian.getPhone());
			}
		} catch (Exception e) {
			Start.showAlert(AlertType.ERROR, e.getMessage());
		}
	}

	private void loadLibrarian() throws Exception {
		List<Librarian> list = DatabaseHandler.showAllLibrarian();
		tbl_librarian.setItems(FXCollections.observableArrayList(list));

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
			col_email.setCellValueFactory(new PropertyValueFactory<>("email"));
			col_password.setCellValueFactory(new PropertyValueFactory<>("password"));
			col_nrc_no.setCellValueFactory(new PropertyValueFactory<>("nrcno"));
			col_phone.setCellValueFactory(new PropertyValueFactory<>("phone"));

			loadLibrarian();

			tbl_librarian.getSelectionModel().selectedItemProperty().addListener((obs, old_select, new_select) -> {
				if (new_select != null) {
					selected_librarian = tbl_librarian.getSelectionModel().getSelectedItem();
					txt_email.setText(selected_librarian.getEmail());
					txt_password.setText(selected_librarian.getPassword());
					txt_nrcno.setText(selected_librarian.getNrcno());
					txt_phone.setText(selected_librarian.getPhone());

				}

			});

		} catch (Exception e) {
			Start.showAlert(AlertType.ERROR, e.getMessage());
		}
	}

}
