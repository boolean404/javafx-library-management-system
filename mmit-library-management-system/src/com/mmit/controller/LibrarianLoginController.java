package com.mmit.controller;

import java.io.IOException;

import com.mmit.Start;
import com.mmit.model.Librarian;
import com.mmit.util.DatabaseHandler;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;

public class LibrarianLoginController {

	@FXML
	private TextField txt_email;

	@FXML
	private PasswordField txt_password;

	@FXML
	void btn_clear_click(ActionEvent event) {
		txt_email.setText("");
		txt_password.setText("");
	}

	@FXML
	void btn_login_click(ActionEvent event) throws IOException {
		try {
			String email = txt_email.getText();
			String password = txt_password.getText();

			Librarian librarian = DatabaseHandler.librarianLogin(email, password);

			if (email.isEmpty())
				Start.showAlert(AlertType.ERROR, "Email must not be Empty!");
			else if (password.isEmpty())
				Start.showAlert(AlertType.ERROR, "Password must not be Empty!");
			else if (email.isEmpty() && password.isEmpty())
				Start.showAlert(AlertType.ERROR, "Email must not be Empty!");
			else if (librarian == null)
				Start.showAlert(AlertType.ERROR, "Login Fail! Email or Password is incorrect, try again!");
			else {
				Start.librarian_login = librarian;
				Start.changeScene("view/Main.fxml");
			}
		} catch (Exception e) {
			Start.showAlert(AlertType.ERROR, e.getMessage());
		}
	}

	@FXML
	void lbl_register_click(MouseEvent event) throws IOException {
		Start.changeScene("view/LibrarianRegister.fxml");
	}
}
