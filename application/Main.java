package application;
	
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;


public class Main extends Application {
	
	
	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());	
		
		stage.setTitle("JavaFX");
		stage.setScene(scene);
//		stage.setMaximized(arg0);
		stage.show();
		
		stage.setOnCloseRequest(event -> {
			 event.consume();
			close(stage);
			});
		
	}
	
	public void close(Stage stage) {
		
		Alert sure = new Alert(AlertType.CONFIRMATION);
		sure.setTitle("Close Player");
		sure.setHeaderText("Are you sure?");
		sure.setContentText("Progress will be lost");
		
		
		if(sure.showAndWait().get()==ButtonType.OK) {
		 stage.close();
		 }
	}
	
     	
}
