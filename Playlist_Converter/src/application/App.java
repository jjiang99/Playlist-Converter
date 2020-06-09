package application;


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class App extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Playlist Converter");



		Button btn = new Button();
		btn.setText("Say 'Hello World'");
		btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				System.out.println("Hello World!");
			}
		});

		Button spotifyButton = new Button();
		spotifyButton.setText("Spotify");
		spotifyButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				System.out.println("Hello World!");
			}
		});

		Button appleMusicButton = new Button();
		appleMusicButton.setText("Apple Music");
		appleMusicButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				System.out.println("Hello World!");
			}
		});

		Button googlePlayButton = new Button();
		googlePlayButton.setText("Google Play Music");
		googlePlayButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				System.out.println("Hello World!");
			}
		});
		
		TextFlow textFlow = new TextFlow();
		textFlow.getChildren().add(new Text("Welcome to the Playlist Converter!"));
		textFlow.setTextAlignment(TextAlignment.CENTER);
		

		GridPane buttons = new GridPane();
		buttons.setAlignment(Pos.CENTER);
		buttons.add(spotifyButton, 0, 0, 1, 1);
		buttons.add(appleMusicButton, 1, 0, 1, 1);
		buttons.add(googlePlayButton, 2, 0, 1, 1);
		
		TextField input = new TextField("Enter the playlist link");
		
		VBox vbox1 = new VBox(textFlow, input, buttons);
		vbox1.setAlignment(Pos.TOP_CENTER);

		StackPane root = new StackPane();
		root.getChildren().add(vbox1);
//		root.getChildren().add(input);
//		root.getChildren().add(buttons);
		Scene scene = new Scene(root, 500, 500);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public TextField getSpotifyPanel() {
		return new TextField("HELLO");
		
	}
}
