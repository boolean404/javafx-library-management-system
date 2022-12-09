package com.mmit;

import java.io.IOException;
import java.util.Optional;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.fxml.FXMLLoader;

public class Start extends Application {
	private static Stage original_stage;

	@Override
	public void start(Stage primaryStage) {
		try {
			original_stage = primaryStage;
			Parent root = FXMLLoader.load(getClass().getResource("view/Login.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.setTitle("MMIT Book Management System");
			primaryStage.setFullScreen(false);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error from Main class");
		}
	}

	public static void changeScene(String inp_file) throws IOException {
		Parent root = FXMLLoader.load(Start.class.getResource(inp_file));
		Scene scene = new Scene(root);
		original_stage.hide();
		original_stage.setScene(scene);
		original_stage.show();
	}
	
	public static Optional<ButtonType> showAlert(AlertType type, String msg){
		Alert alert = new Alert(type);
		alert.setTitle("Message");
		alert.setHeaderText(null);
		alert.setContentText(msg);
		return alert.showAndWait();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
