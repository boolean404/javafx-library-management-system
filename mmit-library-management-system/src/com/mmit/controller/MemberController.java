package com.mmit.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.mmit.Start;
import com.mmit.model.Member;
import com.mmit.util.DatabaseHandler;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;

public class MemberController implements Initializable {
	Member selected_member = new Member();
	public List<String> year_list = new ArrayList<>();

	@FXML
	private ComboBox<String> cbo_year;

	@FXML
	private ComboBox<String> cbo_year_search;

	@FXML
	private TableColumn<Member, String> col_academic_year;

	@FXML
	private TableColumn<Member, Integer> col_card_id;

	@FXML
	private TableColumn<Member, LocalDate> col_created_date;

	@FXML
	private TableColumn<Member, LocalDate> col_expired_date;

	@FXML
	private TableColumn<Member, String> col_name;

	@FXML
	private TableColumn<Member, Integer> col_roll_no;

	@FXML
	private TableColumn<Member, String> col_year;

	@FXML
	private TableView<Member> tbl_member;

	@FXML
	private TextField txt_academic_year;

	@FXML
	private TextField txt_academic_year_search;

	@FXML
	private TextField txt_name;

	@FXML
	private TextField txt_name_search;

	@FXML
	private TextField txt_roll_no;

	@FXML
	void btn_add_click(ActionEvent event) {
		try {
			String name = txt_name.getText();
			String roll_no = txt_roll_no.getText();
			String year = cbo_year.getValue();
			String academic_year = txt_academic_year.getText();

			if (roll_no.isEmpty())
				Start.showAlert(AlertType.ERROR, "Roll No. must not be Empty!");
			else if (name.isEmpty())
				Start.showAlert(AlertType.ERROR, "Name must not be Empty!");
			else if (year == null)
				Start.showAlert(AlertType.ERROR, "Year must not be Empty!");
			else if (academic_year == null)
				Start.showAlert(AlertType.ERROR, "Academic must not be Empty!");
			else {
				int year_selected_index = cbo_year.getSelectionModel().getSelectedIndex();
				String selected_year = year_list.get(year_selected_index);

				Member new_member = new Member();
				new_member.setRoll_no(Integer.parseInt(roll_no));
				new_member.setName(name);
				new_member.setAcademic_year(academic_year);
				new_member.setYear(selected_year);

				DatabaseHandler.memberRegister(new_member);
				Start.showAlert(AlertType.INFORMATION, "Successfully added a new member!");

				clearInputData();
				loadMember();
			}
		} catch (Exception e) {
			Start.showAlert(AlertType.ERROR, e.getMessage());
		}
	}

	@FXML
	void btn_delete_click(ActionEvent event) {
		try {
			String name = txt_name.getText();
			if (name.isEmpty()) {
				Start.showAlert(AlertType.INFORMATION, "Select member from table first to delete!");
				return;
			}
			Optional<ButtonType> result = Start.showAlert(AlertType.CONFIRMATION,
					"Are you sure want to delete this member?");
			if (result.get() == ButtonType.OK) {
				DatabaseHandler.deleteMember(selected_member.getCard_id());
				Start.showAlert(AlertType.INFORMATION, "Successful deleted member");
				clearInputData();
				loadMember();
			}
		} catch (Exception e) {
			Start.showAlert(AlertType.ERROR, e.getMessage());
		}
	}

	@FXML
	void btn_edit_click(ActionEvent event) {
		try {
			String name = txt_name.getText();
			if (name.isEmpty()) {
				Start.showAlert(AlertType.INFORMATION, "Select member from table first to edit!");
				return;
			}
			selected_member.setRoll_no(Integer.parseInt(txt_roll_no.getText()));
			selected_member.setName(txt_name.getText());
			selected_member.setYear(cbo_year.getValue());
			selected_member.setAcademic_year(txt_academic_year.getText());

			DatabaseHandler.editMember(selected_member);
			Start.showAlert(AlertType.INFORMATION, "Successful edited member");
			clearInputData();
			loadMember();
		} catch (Exception e) {
			Start.showAlert(AlertType.ERROR, e.getMessage());
		}
	}

	@FXML
	void btn_search_click(ActionEvent event) {
		try {
			String name = txt_name_search.getText();
			String academic_year = txt_academic_year_search.getText();
			String year = cbo_year_search.getValue();

			List<Member> member_list = DatabaseHandler.searchMember(name, academic_year, year);
			tbl_member.setItems(FXCollections.observableArrayList(member_list));
			cbo_year_search.setValue(null);

		} catch (Exception e) {
			Start.showAlert(AlertType.ERROR, e.getMessage());
		}
	}

	@FXML
	void btn_back_click(ActionEvent event) throws IOException {
		Start.changeScene("view/Main.fxml");
	}

	@FXML
	void btn_logout_click(ActionEvent event) {
		Start.logoutButton();
	}

	private void loadMember() throws Exception {
		List<Member> list = DatabaseHandler.showAllMember();
		tbl_member.setItems(FXCollections.observableArrayList(list));
	}

	private void clearInputData() {
		txt_roll_no.setText(null);
		txt_name.setText(null);
		txt_academic_year.setText(null);
		cbo_year.setValue(null);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			// adding to year cbo and showing
			year_list.add("First Year");
			year_list.add("Second Year");
			year_list.add("Third Year");
			year_list.add("Final Year");
			year_list.add("Master Year");
			cbo_year_search.setItems(FXCollections.observableArrayList(year_list));
			cbo_year.setItems(FXCollections.observableArrayList(year_list));

			col_card_id.setCellValueFactory(new PropertyValueFactory<>("card_id"));
			col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
			col_roll_no.setCellValueFactory(new PropertyValueFactory<>("roll_no"));
			col_academic_year.setCellValueFactory(new PropertyValueFactory<>("academic_year"));
			col_year.setCellValueFactory(new PropertyValueFactory<>("year"));
			col_created_date.setCellValueFactory(new PropertyValueFactory<>("created_date"));
			col_expired_date.setCellValueFactory(new PropertyValueFactory<>("expired_date"));

			loadMember();

			tbl_member.getSelectionModel().selectedItemProperty().addListener((obs, old_select, new_select) -> {
				if (new_select != null) {
					selected_member = tbl_member.getSelectionModel().getSelectedItem();

					txt_roll_no.setText(selected_member.getRoll_no() + "");
					txt_name.setText(selected_member.getName());
					txt_academic_year.setText(selected_member.getAcademic_year().substring(0, 4));
					cbo_year.setValue(selected_member.getYear());
				}
			});
		} catch (Exception e) {
			Start.showAlert(AlertType.ERROR, e.getMessage());
			e.printStackTrace();
		}
	}
}
