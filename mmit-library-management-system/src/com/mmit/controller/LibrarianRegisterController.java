package com.mmit.controller;

import java.io.IOException;

import com.mmit.Start;
import com.mmit.model.Librarian;
import com.mmit.util.DatabaseHandler;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class LibrarianRegisterController {

	@FXML
	private TextField txt_email;

	@FXML
	private TextField txt_nrc_no;

	@FXML
	private PasswordField txt_password;

	@FXML
	private TextField txt_phone;

	@FXML
	void btn_register_click(ActionEvent event) throws IOException {
		String email = txt_email.getText();
		String password = txt_password.getText();
		String nrc_no = txt_nrc_no.getText();
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
			DatabaseHandler.librarianRegister(email, password, nrc_no, phone);
			Start.showAlert(AlertType.INFORMATION, "Register librarian successful! \nPress 'OK' to Login");
			Start.changeScene("view/LibrarianLogin.fxml");
		}

	}

	@FXML
	void lbl_login_click(MouseEvent event) throws IOException {
		Start.changeScene("view/LibrarianLogin.fxml");
	}

}
