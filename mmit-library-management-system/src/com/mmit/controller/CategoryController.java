package com.mmit.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.mmit.Start;
import com.mmit.model.Category;
import com.mmit.util.DatabaseHandler;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;

public class CategoryController implements Initializable {
	Category selected_category = new Category();

	@FXML
	private TableColumn<Category, Integer> col_id;

	@FXML
	private TableColumn<Category, String> col_name;

	@FXML
	private TableView<Category> tbl_category;

	@FXML
	private TextField txt_name;

	@FXML
	void btn_add_click(ActionEvent event) {
		try {
			String name = txt_name.getText();

			if (name.isEmpty())
				Start.showAlert(AlertType.ERROR, "Category name must not be Empty!");
			else {
				Category category = new Category();
				category.setName(name);

				DatabaseHandler.addCategory(category);
				Start.showAlert(AlertType.INFORMATION, "Successful added a new category!");

				clearInputData();
				loadCategory();
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
					"Are you sure want to delete this category?");
			if (result.get() == ButtonType.OK) {
				DatabaseHandler.deleteCategory(selected_category.getId());
				clearInputData();
				loadCategory();
			}
		} catch (Exception e) {
			Start.showAlert(AlertType.ERROR, e.getMessage());
		}
	}

	@FXML
	void btn_edit_click(ActionEvent event) {
		try {
			selected_category.setName(txt_name.getText());

			DatabaseHandler.editCategory(selected_category);
			Start.showAlert(AlertType.INFORMATION, "Successful edited category!");
			clearInputData();
			loadCategory();
		} catch (Exception e) {
			Start.showAlert(AlertType.ERROR, e.getMessage());
		}
	}

	@FXML
	void btn_logout_click(ActionEvent event) {
		Start.logoutButton();
	}

	private void loadCategory() throws Exception {
		List<Category> list = DatabaseHandler.showAllCategory();
		tbl_category.setItems(FXCollections.observableArrayList(list));

	}

	private void clearInputData() {
		txt_name.setText(null);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
			col_name.setCellValueFactory(new PropertyValueFactory<>("name"));

			loadCategory();

			tbl_category.getSelectionModel().selectedItemProperty().addListener((obs, old_select, new_select) -> {
				if (new_select != null) {
					selected_category = tbl_category.getSelectionModel().getSelectedItem();
					txt_name.setText(selected_category.getName());
				}

			});

		} catch (Exception e) {
			Start.showAlert(AlertType.ERROR, e.getMessage());
		}

	}

}
