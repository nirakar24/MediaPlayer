package application;

import java.awt.TextField;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class MyController {
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	@FXML
	private AnchorPane main;	
		
	public void next(ActionEvent e) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("new.fxml"));
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root,950,650);
		scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
		stage.setScene(scene);
		stage.setResizable(true);
		stage.show();
		
	}
	
	public void music(ActionEvent e) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("music.fxml"));
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
	}
	
	public void close(ActionEvent e) {
		
		Alert sure = new Alert(AlertType.CONFIRMATION);
		sure.setTitle("Close Player");
		sure.setHeaderText("Are you sure?");
		sure.setContentText("Progress will be lost");
		
		if(sure.showAndWait().get()==ButtonType.OK) {
		 Stage stage=(Stage)main.getScene().getWindow();
		 stage.close();
		 }		
	}
}
