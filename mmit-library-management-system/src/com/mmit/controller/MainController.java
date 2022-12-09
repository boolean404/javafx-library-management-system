package com.mmit.controller;

import com.mmit.Start;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class MainController {

    @FXML
    private Text txt_current_libraian;

    @FXML
    void author_click(MouseEvent event) {

    }

    @FXML
    void book_click(MouseEvent event) {

    }

    @FXML
    void btn_logout_click(ActionEvent event) {
    	Start.logoutButton();
    }

    @FXML
    void category_click(MouseEvent event) {

    }

    @FXML
    void librarina_click(MouseEvent event) {

    }

    @FXML
    void member_click(MouseEvent event) {

    }

    @FXML
    void transaction_click(MouseEvent event) {

    }

}
