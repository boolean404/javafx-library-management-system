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

public class MainController implements Initializable {

    @FXML
    private Text txt_current_libraian;

    @FXML
    void author_click(MouseEvent event) throws IOException {
    	Start.changeScene("view/Author.fxml");
    }

    @FXML
    void book_click(MouseEvent event) throws IOException {
    	Start.changeScene("view/Book.fxml");
    }

    @FXML
    void btn_logout_click(ActionEvent event) {
    	Start.logoutButton();
    }

    @FXML
    void category_click(MouseEvent event) throws IOException {
    	Start.changeScene("view/Category.fxml");
    }

    @FXML
    void librarian_click(MouseEvent event) throws IOException {
    	Start.changeScene("view/Librarian.fxml");
    }

    @FXML
    void member_click(MouseEvent event) throws IOException {
    	Start.changeScene("view/Member.fxml");
    }

    @FXML
    void transaction_click(MouseEvent event) throws IOException {
    	Start.changeScene("view/Transaction.fxml");

    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		txt_current_libraian.setText(Start.librarian_login.getEmail());
	}

}
