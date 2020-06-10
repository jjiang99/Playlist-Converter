package application;

import java.net.URL;

import converterCode.Converter;
import converterCode.Service;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class App extends Application {
	Scene homeScene;
	Scene originSelect;
	Scene destinationSelect;
	Scene informationInput;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {

		createHomeScene(primaryStage);
		createOriginSelect(primaryStage);
		createDestinationSelect(primaryStage);
		createInformationInput(primaryStage);

		primaryStage.setScene(homeScene);
		primaryStage.setTitle("Playlist Converter");
		primaryStage.show();

//		Button btn = new Button();
//		btn.setText("Say 'Hello World'");
//		btn.setOnAction(new EventHandler<ActionEvent>() {
//
//			@Override
//			public void handle(ActionEvent event) {
//				System.out.println("Hello World!");
//			}
//		});
//
//		Button spotifyButton = new Button();
//		spotifyButton.setText("Spotify");
//		spotifyButton.setOnAction(new EventHandler<ActionEvent>() {
//
//			@Override
//			public void handle(ActionEvent event) {
//				System.out.println("Hello World!");
//			}
//		});
//
//		Button appleMusicButton = new Button();
//		appleMusicButton.setText("Apple Music");
//		appleMusicButton.setOnAction(new EventHandler<ActionEvent>() {
//
//			@Override
//			public void handle(ActionEvent event) {
//				System.out.println("Hello World!");
//			}
//		});
//
//		Button googlePlayButton = new Button();
//		googlePlayButton.setText("Google Play Music");
//		googlePlayButton.setOnAction(new EventHandler<ActionEvent>() {
//
//			@Override
//			public void handle(ActionEvent event) {
//				System.out.println("Hello World!");
//			}
//		});
//		
//		TextFlow textFlow = new TextFlow();
//		textFlow.getChildren().add(new Text("Welcome to the Playlist Converter!"));
//		textFlow.setTextAlignment(TextAlignment.CENTER);
//		
//
//		GridPane buttons = new GridPane();
//		buttons.setAlignment(Pos.CENTER);
//		buttons.add(spotifyButton, 0, 0, 1, 1);
//		buttons.add(appleMusicButton, 1, 0, 1, 1);
//		buttons.add(googlePlayButton, 2, 0, 1, 1);
//		
//		TextField input = new TextField("Enter the playlist link");
//		
//		VBox vbox1 = new VBox(textFlow, input, buttons);
//		vbox1.setAlignment(Pos.TOP_CENTER);
//
//		StackPane root = new StackPane();
//		root.getChildren().add(vbox1);
////		root.getChildren().add(input);
////		root.getChildren().add(buttons);
//		Scene scene = new Scene(root, 500, 500);
//		primaryStage.setScene(scene);
//		primaryStage.show();
	}

	public void createHomeScene(Stage stage) {
		Label welcome = new Label("Welcome to the Playlist Converter!");
		Label instructions = new Label("Click start to continue.");

		Button button = new Button("Start");
		button.setAlignment(Pos.CENTER);
		button.setOnAction(e -> stage.setScene(originSelect));
		welcome.setAlignment(Pos.TOP_CENTER);
		instructions.setAlignment(Pos.TOP_CENTER);

		VBox layout = new VBox(0);
		layout.getChildren().addAll(welcome, instructions, button);
		layout.setAlignment(Pos.CENTER);
		homeScene = new Scene(layout, 300, 300);
	}

	public void createOriginSelect(Stage stage) {
		Button btn = new Button();
		btn.setText("Say 'Hello World'");

		Button spotifyButton = new Button();
		spotifyButton.setText("Spotify");
		spotifyButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Converter.setSourceService(Service.SPOTIFY);
				stage.setScene(destinationSelect);
			}
		});

		Button appleMusicButton = new Button();
		appleMusicButton.setText("Apple Music");
		appleMusicButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Converter.setSourceService(Service.APPLE);
				stage.setScene(destinationSelect);
			}
		});

		Button googlePlayButton = new Button();
		googlePlayButton.setText("Google Play Music");
		googlePlayButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Converter.setSourceService(Service.GOOGLE);
				stage.setScene(destinationSelect);
			}
		});

		TextFlow textFlow = new TextFlow();
		textFlow.getChildren().add(new Text("Select the origin service."));
		textFlow.setTextAlignment(TextAlignment.CENTER);

		GridPane buttons = new GridPane();
		buttons.setAlignment(Pos.CENTER);
		buttons.add(spotifyButton, 0, 0, 1, 1);
		buttons.add(appleMusicButton, 1, 0, 1, 1);
		buttons.add(googlePlayButton, 2, 0, 1, 1);

		Button testButton = new Button();
		testButton.setText("TEST");
		testButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				System.out.println(Converter.getSourceService());
			}
		});

		Button backButton = new Button();
		backButton.setText("Back");
		backButton.setOnAction(e -> stage.setScene(homeScene));

		VBox vbox1 = new VBox(textFlow, buttons, backButton, testButton);
		vbox1.setAlignment(Pos.CENTER);

		StackPane root = new StackPane();
		root.getChildren().add(vbox1);

		originSelect = new Scene(root, 300, 300);
	}

	public void createDestinationSelect(Stage stage) {
		Button btn = new Button();
		btn.setText("Say 'Hello World'");

		Button spotifyButton = new Button();
		spotifyButton.setText("Spotify");
		spotifyButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Converter.setDestinationService(Service.SPOTIFY);
				stage.setScene(informationInput);
			}
		});

		Button appleMusicButton = new Button();
		appleMusicButton.setText("Apple Music");
		appleMusicButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Converter.setDestinationService(Service.APPLE);
				stage.setScene(informationInput);
			}
		});

		Button googlePlayButton = new Button();
		googlePlayButton.setText("Google Play Music");
		googlePlayButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Converter.setDestinationService(Service.GOOGLE);
				stage.setScene(informationInput);
			}
		});

		TextFlow textFlow = new TextFlow();
		textFlow.getChildren().add(new Text("Select the destination service."));
		textFlow.setTextAlignment(TextAlignment.CENTER);

		GridPane buttons = new GridPane();
		buttons.setAlignment(Pos.CENTER);
		buttons.add(spotifyButton, 0, 0, 1, 1);
		buttons.add(appleMusicButton, 1, 0, 1, 1);
		buttons.add(googlePlayButton, 2, 0, 1, 1);

		Button testButton = new Button();
		testButton.setText("TEST");
		testButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				System.out.println(Converter.getDestinationService());
			}
		});

		Button backButton = new Button();
		backButton.setText("Back");
		backButton.setOnAction(e -> stage.setScene(destinationSelect));

		VBox vbox1 = new VBox(textFlow, buttons, backButton, testButton);
		vbox1.setAlignment(Pos.CENTER);

		StackPane root = new StackPane();
		root.getChildren().add(vbox1);

		destinationSelect = new Scene(root, 300, 300);

	}

	public void createInformationInput(Stage primaryStage) {
		TextField input = new TextField("Enter the playlist link");

		Button enterButton = new Button();
		enterButton.setText("Enter");
		enterButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// System.out.println(input.getText());
				System.out.println(isValidLink(input.getText()));
			}
		});

		input.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent ke) {
				if (ke.getCode().equals(KeyCode.ENTER)) {
//					System.out.println(input.getText());
					System.out.println(isValidLink(input.getText()));
				}
			}
		});

		VBox vbox1 = new VBox(input, enterButton);
		vbox1.setAlignment(Pos.CENTER);

		StackPane root = new StackPane();
		root.getChildren().add(vbox1);
		informationInput = new Scene(root, 300, 300);
	}

	public TextField getSpotifyPanel() {
		return new TextField("HELLO");

	}

	public static boolean isValidLink(String url) {
		try {
			new URL(url).toURI();
			return true;
		}

		catch (Exception e) {
			return false;
		}
	}
}
