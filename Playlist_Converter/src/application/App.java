package application;

import java.io.IOException;
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
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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
	Scene serviceSelect;
	Scene informationInput;
	Scene endScreen;
	Scene spotifyInfoInput;
	Scene appleInfoInput;
	
	String playlistLink;

	int width = 500;
	int height = 500;
	int standardSpacing = 10;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {

		createHomeScene(primaryStage);
//		createOriginSelect(primaryStage);
//		createDestinationSelect(primaryStage);
		createInformationInput(primaryStage);
		createServiceSelect(primaryStage);
		createEndScreen(primaryStage);

		primaryStage.setScene(homeScene);
		primaryStage.setTitle("Playlist Converter");
		primaryStage.show();
	}

	public void createHomeScene(Stage stage) {
		Label welcome = new Label("Welcome to the Playlist Converter!");
		Label instructions = new Label("Click start to continue.");

		Button button = new Button("Start");
		button.setAlignment(Pos.CENTER);
		button.setOnAction(e -> stage.setScene(serviceSelect));
		welcome.setAlignment(Pos.TOP_CENTER);
		instructions.setAlignment(Pos.TOP_CENTER);

		VBox layout = new VBox(0);
		layout.getChildren().addAll(welcome, instructions, button);
		layout.setAlignment(Pos.CENTER);
		homeScene = new Scene(layout, width, height);
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

		originSelect = new Scene(root, width, height);
	}

//	public void createDestinationSelect(Stage stage) {
//		Button btn = new Button();
//		btn.setText("Say 'Hello World'");
//
//		Button spotifyButton = new Button();
//		spotifyButton.setText("Spotify");
//		spotifyButton.setOnAction(new EventHandler<ActionEvent>() {
//
//			@Override
//			public void handle(ActionEvent event) {
//				Converter.setDestinationService(Service.SPOTIFY);
//				stage.setScene(informationInput);
//			}
//		});
//
//		Button appleMusicButton = new Button();
//		appleMusicButton.setText("Apple Music");
//		appleMusicButton.setOnAction(new EventHandler<ActionEvent>() {
//
//			@Override
//			public void handle(ActionEvent event) {
//				Converter.setDestinationService(Service.APPLE);
//				stage.setScene(informationInput);
//			}
//		});
//
//		Button googlePlayButton = new Button();
//		googlePlayButton.setText("Google Play Music");
//		googlePlayButton.setOnAction(new EventHandler<ActionEvent>() {
//
//			@Override
//			public void handle(ActionEvent event) {
//				Converter.setDestinationService(Service.GOOGLE);
//				stage.setScene(informationInput);
//			}
//		});
//
//		TextFlow textFlow = new TextFlow();
//		textFlow.getChildren().add(new Text("Select the destination service."));
//		textFlow.setTextAlignment(TextAlignment.CENTER);
//
//		GridPane buttons = new GridPane();
//		buttons.setAlignment(Pos.CENTER);
//		buttons.add(spotifyButton, 0, 0, 1, 1);
//		buttons.add(appleMusicButton, 1, 0, 1, 1);
//		buttons.add(googlePlayButton, 2, 0, 1, 1);
//
//		Button testButton = new Button();
//		testButton.setText("TEST");
//		testButton.setOnAction(new EventHandler<ActionEvent>() {
//
//			@Override
//			public void handle(ActionEvent event) {
//				System.out.println(Converter.getDestinationService());
//			}
//		});
//
//		Button backButton = new Button();
//		backButton.setText("Back");
//		backButton.setOnAction(e -> stage.setScene(originSelect));
//
//		VBox vbox1 = new VBox(textFlow, buttons, backButton, testButton);
//		vbox1.setAlignment(Pos.CENTER);
//
//		StackPane root = new StackPane();
//		root.getChildren().add(vbox1);
//
//		destinationSelect = new Scene(root, width, height);
//
//	}

	public void createServiceSelect(Stage stage) {
		VBox root = new VBox();
		root.setAlignment(Pos.CENTER);

		HBox serviceSelection = new HBox();
		serviceSelection.setAlignment(Pos.CENTER);

		ToggleGroup originGroup = new ToggleGroup();
		ToggleButton spotifyButtonLeft = new ToggleButton("Spotify");
		ToggleButton appleMusicButtonLeft = new ToggleButton("Apple Music");
		ToggleButton googlePlayButtonLeft = new ToggleButton("Google Play Music");

		spotifyButtonLeft.setToggleGroup(originGroup);
		appleMusicButtonLeft.setToggleGroup(originGroup);
		googlePlayButtonLeft.setToggleGroup(originGroup);

		spotifyButtonLeft.setMinWidth(120);
		appleMusicButtonLeft.setMinWidth(120);
		googlePlayButtonLeft.setMinWidth(120);

		ToggleGroup destinationGroup = new ToggleGroup();
		ToggleButton spotifyButtonRight = new ToggleButton("Spotify");
		ToggleButton appleMusicButtonRight = new ToggleButton("Apple Music");
		ToggleButton googlePlayButtonRight = new ToggleButton("Google Play Music");

		spotifyButtonRight.setToggleGroup(destinationGroup);
		appleMusicButtonRight.setToggleGroup(destinationGroup);
		googlePlayButtonRight.setToggleGroup(destinationGroup);

		spotifyButtonRight.setMinWidth(120);
		appleMusicButtonRight.setMinWidth(120);
		googlePlayButtonRight.setMinWidth(120);

		VBox originButtons = new VBox(spotifyButtonLeft, appleMusicButtonLeft, googlePlayButtonLeft);
		VBox destinationButtons = new VBox(spotifyButtonRight, appleMusicButtonRight, googlePlayButtonRight);
		originButtons.setAlignment(Pos.CENTER);
		originButtons.setSpacing(standardSpacing);
		destinationButtons.setAlignment(Pos.CENTER);
		destinationButtons.setSpacing(standardSpacing);
		serviceSelection.getChildren().addAll(originButtons, destinationButtons);
		serviceSelection.setSpacing(50);

		Button backButton = new Button();
		backButton.setText("Back");
		backButton.setOnAction(e -> stage.setScene(homeScene));

		Button nextButton = new Button();
		nextButton.setText("Next");
		nextButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (spotifyButtonLeft.isSelected()) {
					Converter.setSourceService(Service.SPOTIFY);
				} else if (appleMusicButtonLeft.isSelected()) {
					Converter.setSourceService(Service.APPLE);
				} else if (googlePlayButtonLeft.isSelected()) {
					Converter.setSourceService(Service.GOOGLE);
				}

				if (spotifyButtonRight.isSelected()) {
					Converter.setDestinationService(Service.SPOTIFY);
				} else if (appleMusicButtonRight.isSelected()) {
					Converter.setDestinationService(Service.APPLE);
				} else if (googlePlayButtonRight.isSelected()) {
					Converter.setDestinationService(Service.GOOGLE);
				}

				System.out.println(Converter.getSourceService());
				System.out.println(Converter.getDestinationService());
				stage.setScene(informationInput);
			}
		});

		HBox navigationButtons = new HBox(backButton, nextButton);
		navigationButtons.setAlignment(Pos.BASELINE_CENTER);
		navigationButtons.setSpacing(standardSpacing);

		root.getChildren().addAll(serviceSelection, navigationButtons);
		root.setSpacing(standardSpacing);
		// create the scene
		serviceSelect = new Scene(root, width, height);
	}

	public void createInformationInput(Stage stage) {
		TextField linkInput = new TextField("Enter the playlist link");

		TextField nameInput = new TextField("Enter name of the new playlist");

		Button enterButton = new Button();
		enterButton.setText("Enter");
		enterButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					System.out.println(convert(linkInput.getText(), nameInput.getText()));
					stage.setScene(endScreen);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		Button backButton = new Button();
		backButton.setText("Back");
		backButton.setOnAction(e -> stage.setScene(serviceSelect));

		Button nextButton = new Button();
		nextButton.setText("Convert!");
		nextButton.setOnAction(e -> stage.setScene(informationInput));

		HBox navigationButtons = new HBox(backButton, nextButton);
		navigationButtons.setAlignment(Pos.BASELINE_CENTER);
		navigationButtons.setSpacing(standardSpacing);

		VBox vbox1 = new VBox(linkInput, nameInput, enterButton, navigationButtons);
		vbox1.setAlignment(Pos.CENTER);
		vbox1.setSpacing(standardSpacing);

		StackPane root = new StackPane();
		root.getChildren().add(vbox1);
		informationInput = new Scene(root, width, height);
	}
	
	public void createSpotifyInput(Stage stage) {
		Label message = new Label("Link: " + playlistLink);

		Button exitButton = new Button();
		exitButton.setText("Convert!");
		exitButton.setOnAction(e -> stage.setScene(informationInput));


		VBox vbox1 = new VBox(message, exitButton);
		vbox1.setAlignment(Pos.CENTER);
		vbox1.setSpacing(standardSpacing);

		StackPane root = new StackPane();
		root.getChildren().add(vbox1);		
		spotifyInfoInput = new Scene(root, width, height);
	}

	public void createAppleInput(Stage stage) {
		Label message = new Label("Link: " + playlistLink);

		Button exitButton = new Button();
		exitButton.setText("Convert!");
		exitButton.setOnAction(e -> stage.setScene(informationInput));


		VBox vbox1 = new VBox(message, exitButton);
		vbox1.setAlignment(Pos.CENTER);
		vbox1.setSpacing(standardSpacing);

		StackPane root = new StackPane();
		root.getChildren().add(vbox1);		
		spotifyInfoInput = new Scene(root, width, height);
	}
	
	public void createEndScreen(Stage stage) {
		Label message = new Label("Link: " + playlistLink);

		Button exitButton = new Button();
		exitButton.setText("Convert!");
		exitButton.setOnAction(e -> stage.setScene(informationInput));


		VBox vbox1 = new VBox(message, exitButton);
		vbox1.setAlignment(Pos.CENTER);
		vbox1.setSpacing(standardSpacing);

		StackPane root = new StackPane();
		root.getChildren().add(vbox1);
		endScreen = new Scene(root, width, height);
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

	public static String convert(String link, String name) throws IOException {
		return Converter.convert(link, name);
	}
}
