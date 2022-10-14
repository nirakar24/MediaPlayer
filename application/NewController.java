package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;

import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

public class NewController implements Initializable{

	private Stage stage;
	private Scene scene;
	private Parent root;		
	private String path;
	
	private boolean End = false;
	private boolean playing = true;
	private boolean muted = true;
	
	@FXML
	private MenuItem close;
	private MenuItem back;
	
	@FXML
	private AnchorPane pane;
	private AnchorPane mpane;
	
	@FXML
	private MediaView view;
	private MediaPlayer player;
	private Media video;
	
	@FXML
	private HBox HboxVolume;
	
	
	@FXML
	private Slider volume;
	
	@FXML
	private Label CurrentTime;
	
	@FXML
	private Label TotalTime;
	
	@FXML
	private Button buttonP;
	
	@FXML
	private Slider timeSlide;
	
	@FXML
	private Label fullScreen;
	
	@FXML
	private VBox controls;
	
	@FXML
	private ImageView full;
	
//	@FXML
//	private Menu mainMenu;
	
	public void prev(ActionEvent e) throws IOException {
		player.stop();
		Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
		stage.setScene(scene);
//		stage.setMaximized(true);
		stage.show();
		
	}
	public void open() {
		if(playing=true) {
			FileChooser choose = new FileChooser();
			File vid = choose.showOpenDialog(null);
			path = vid.toURI().toString();
			player.stop();
			timeSlide.setValue(0);
			System.out.println(path);
			
			if (path != null) {
				Media video = new Media(path);
				player = new MediaPlayer(video);
							
				view.setMediaPlayer(player);
				player.play();
				player.volumeProperty().bindBidirectional(volume.valueProperty());
				bindCurrentTime();	
				
				player.totalDurationProperty().addListener(new ChangeListener<Duration>() {

					@Override
					public void changed(ObservableValue<? extends Duration> observableValue, Duration old, Duration newduration){
						
						timeSlide.setMax(newduration.toSeconds());
						TotalTime.setText(getTime(newduration));
						
						
					}
					
				});
				
			}

		}			
		}
	public void close(ActionEvent e) {
		
		Alert sure = new Alert(AlertType.CONFIRMATION);
		sure.setTitle("Close Player");
		sure.setHeaderText("Are you sure?");
		sure.setContentText("Progress will be lost");
		
		
		if(sure.showAndWait().get()==ButtonType.OK) {
		 Stage stage=(Stage)pane.getScene().getWindow();
		 stage.close();
		 }
		
	}
	public void pause() {
		player.pause();
	}
	public void play() {
		player.play();
		player.setRate(1);
	}
	public void speed25(ActionEvent e) {
		player.setRate(0.25);
	}
	public void speed50(ActionEvent e) {
		player.setRate(0.5);
	}
	public void speed75(ActionEvent e) {
		player.setRate(0.75);
	}
	public void speed100(ActionEvent e) {
		player.setRate(1);
	}
	public void speed125(ActionEvent e) {
		player.setRate(1.25);
	}
	public void speed150(ActionEvent e) {
		player.setRate(1.5);
	}
	public void speed175(ActionEvent e) {
		player.setRate(1.75);
	}
	public void speed200(ActionEvent e) {
		player.setRate(2);
	}
	
	public void bindCurrentTime() {
		
		CurrentTime.textProperty().bind(Bindings.createStringBinding(new Callable<String>(){

			@Override
			public String call() throws Exception {
				
				return getTime(player.getCurrentTime())+"/";
			}

		}, player.currentTimeProperty()));
		
	}
	
	public String getTime(Duration string) {
		
		int hours = (int)string.toHours();
		int minutes = (int)string.toMinutes();
		int secs = (int)string.toSeconds();
		
		if(secs>59) secs = secs%60;
		if(minutes>59)minutes = minutes%60;
		if(hours>59)hours = hours%60;
		
		if(hours>1) return String.format("%d:%02d:%02d",hours,minutes,secs);
		else return String.format("%02d:%02d", minutes,secs);
		
		
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		final int size = 25;
		
//		Images<>
		Image fullscreen = new Image(new File("src\\application\\components\\full.png").toURI().toString());
		full = new ImageView(fullscreen);
		full.setFitWidth(size);
		full.setFitHeight(size);
//		Image</>
		
		FileChooser choose = new FileChooser();
		File vid = choose.showOpenDialog(null);
		path = vid.toURI().toString();
		System.out.println(path);
		
		if (path != null) {
			Media video = new Media(path);
			player = new MediaPlayer(video);
			fullScreen.setGraphic(full);
						
			view.setMediaPlayer(player);
			player.play();
		}
		player.volumeProperty().bindBidirectional(volume.valueProperty());
		bindCurrentTime();
		
		
		buttonP.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
//				Button play = (Button) e.getSource();
				if(End) {
					timeSlide.setValue(0);
					End=false;
					playing=false;
				} 
				if(playing) {
					pause();
					buttonP.setText("Pause");
					playing=false;
				}else {
					play();
					buttonP.setText("Play");
					playing=true;
				}
				
			}
			
		});
		
		player.totalDurationProperty().addListener(new ChangeListener<Duration>() {

			@Override
			public void changed(ObservableValue<? extends Duration> observableValue, Duration old, Duration newduration){
				
				timeSlide.setMax(newduration.toSeconds());
				TotalTime.setText(getTime(newduration));
				
				
			}
			
		});
		
		timeSlide.valueChangingProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean stopped, Boolean changing) {
				if(!changing) {
					player.seek(Duration.seconds(timeSlide.getValue()));
				}
				
			}
			
		});
		
		timeSlide.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number oldValue, Number newValue) {
				double currentTime = player.getCurrentTime().toSeconds();
				if(Math.abs(currentTime-newValue.doubleValue())>0.5) {
					player.seek(Duration.seconds(newValue.doubleValue()));
				}
				
			}
			
		});
		
		player.currentTimeProperty().addListener(new ChangeListener<Duration>() {

			@Override
			public void changed(ObservableValue<? extends Duration> arg0, Duration oldTime, Duration newTime) {
				if(!timeSlide.isValueChanging()) {
					timeSlide.setValue(newTime.toSeconds());
				}
				
			}
			
		});
		
		fullScreen.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent e) {
				Label label = (Label) e.getSource();
				Stage stage = (Stage) label.getScene().getWindow();
				
				if(stage.isMaximized()) {
					stage.setMaximized(false);
					label.setGraphic(full);
				}else {
					stage.setMaximized(true);
					label.setGraphic(full);
				}
				
			}
			
		});
		
		pane.sceneProperty().addListener(new ChangeListener<Scene>() {

			@Override
			public void changed(ObservableValue<? extends Scene> arg0, Scene oldscene, Scene newscene) {
				if(oldscene == null && newscene != null) {
					view.fitHeightProperty().bind(newscene.heightProperty().subtract(controls.heightProperty().add(185)));
				}
				
			}
			
		});
		
	}
}


