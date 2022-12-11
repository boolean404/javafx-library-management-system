package com.mmit.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.mmit.Start;
import com.mmit.model.Author;
import com.mmit.util.DatabaseHandler;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class AuthorController implements Initializable {
	Author selected_author = new Author();

	@FXML
	private TableColumn<Author, LocalDate> col_birthday;

	@FXML
	private TableColumn<Author, Integer> col_id;

	@FXML
	private TableColumn<Author, String> col_name;

	@FXML
	private TableColumn<Author, String> col_native_town;

	@FXML
	private TableView<Author> tbl_author;

	@FXML
	private DatePicker txt_birthday;

	@FXML
	private TextField txt_name;

	@FXML
	private TextField txt_native_town;

	@FXML
	void btn_add_click(ActionEvent event) {
		try {
			String name = txt_name.getText();
			LocalDate birthday = txt_birthday.getValue();
			String native_town = txt_native_town.getText();

			if (name.isEmpty())
				Start.showAlert(AlertType.ERROR, "Author name must not be Empty!");
			else if (birthday == null)
				Start.showAlert(AlertType.ERROR, "Birthday must not be Empty!");
			else {
				Author author = new Author();
				author.setName(name);
				author.setBirthday(birthday);
				author.setCity(native_town);

				DatabaseHandler.addAuthor(author);
				Start.showAlert(AlertType.INFORMATION, "Successful added a new author!");

				clearInputData();
				loadAuthor();
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
		try {
			Optional<ButtonType> result = Start.showAlert(AlertType.CONFIRMATION,
					"Are you sure want to delete this author?");
			if (result.get() == ButtonType.OK) {
				DatabaseHandler.deleteAuthor(selected_author.getId());
				clearInputData();
				loadAuthor();
			}
		} catch (Exception e) {
			Start.showAlert(AlertType.ERROR, e.getMessage());
		}
	}

	@FXML
	void btn_edit_click(ActionEvent event) {
		try {
			selected_author.setName(txt_name.getText());
			selected_author.setCity(txt_native_town.getText());
			selected_author.setBirthday(txt_birthday.getValue());

			DatabaseHandler.editAuthor(selected_author);
			Start.showAlert(AlertType.INFORMATION, "Successful edited author");
			clearInputData();
			loadAuthor();
		} catch (Exception e) {
			Start.showAlert(AlertType.ERROR, e.getMessage());
		}
	}

	@FXML
	void btn_logout_click(ActionEvent event) {
		Start.logoutButton();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
			col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
			col_native_town.setCellValueFactory(new PropertyValueFactory<>("city"));
			col_birthday.setCellValueFactory(new PropertyValueFactory<>("birthday"));

			loadAuthor();

			tbl_author.getSelectionModel().selectedItemProperty().addListener((obs, old_select, new_select) -> {
				if (new_select != null) {
					selected_author = tbl_author.getSelectionModel().getSelectedItem();
					txt_name.setText(selected_author.getName());
					txt_birthday.setValue(selected_author.getBirthday());
					txt_native_town.setText(selected_author.getCity());
				}

			});

		} catch (Exception e) {
			Start.showAlert(AlertType.ERROR, e.getMessage());
		}
	}

	private void clearInputData() {
		txt_name.setText(null);
		txt_native_town.setText(null);
		txt_birthday.setValue(null);
	}

	private void loadAuthor() throws Exception {
		List<Author> list = DatabaseHandler.showAllAuthor();
		tbl_author.setItems(FXCollections.observableArrayList(list));

	}

}
