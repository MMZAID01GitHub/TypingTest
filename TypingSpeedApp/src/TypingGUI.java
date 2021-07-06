import java.util.concurrent.TimeUnit;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TypingGUI extends Application {
	private Text myWord;
	private Text timeDisplayed;
	private int timeNumber;
	private Font font;
	private TextField field;
	private SQLiteWordGenerator generate;
	private double total;
	private double countCorrect;
	private double charsTyped;

	
	public void start(Stage primaryStage) throws Exception {
		font = new Font("Lucidia Sans", 25);
		
		
		
		//----------------------Timer----------------------
		timeDisplayed = new Text ("1:00");
		timeDisplayed.setFont(font);
		timeNumber = 60;

		Thread thread = new Thread (new Runnable() {
			@Override
			public void run() {
				Runnable updater = new Runnable() {
					@Override
					public void run() {
						if(timeNumber>0) {
							timeNumber--;
							timeDisplayed.setText("0:" + String.format("%02d", timeNumber));
						}
						if(timeNumber == 0) {
							System.out.println("WPM: "+ charsTyped/5);
							System.out.println("Accuracy: " + countCorrect/total + "%");
							myWord.setText("WPM: " + charsTyped/5 + " Accuracy: " + String.format("%.0f%%", 100*countCorrect/total));

						}
						
					}
				};
				while (timeNumber>0) {
					try {
						Thread.sleep(1000);
					}catch (InterruptedException ex) {
						System.out.println("Interrupted Exception");
					}
					Platform.runLater(updater);
				}
			}

		});
		thread.setDaemon(true);
		thread.start();
		
		//-------------------end timer-----------------------------

		//SQLite Query for a random word
		generate = new SQLiteWordGenerator();
		System.out.println(generate.getWord());

		//Creating Text which shows the random word
		myWord = new Text(generate.getWord().toLowerCase());
		myWord.setFont(font);
		
		
		//TextField
		field = new TextField();
		field.setAlignment(Pos.BOTTOM_CENTER);
		field.setOnKeyReleased(this::spacePressed);

		//Pane, scene, and stage setup
		FlowPane pane = new FlowPane(Orientation.VERTICAL);
		pane.setAlignment(Pos.TOP_CENTER);
		pane.getChildren().addAll(myWord, timeDisplayed,field);
		pane.setStyle("-fx-background-color: LightCyan");
		Scene scene = new Scene (pane, 400,100 );
		primaryStage.setScene(scene);
		primaryStage.setTitle("Typing Test");
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);

	}
	public void spacePressed(KeyEvent event) {
		if(event.getCode()==KeyCode.SPACE && timeNumber > 0) {
			charsTyped += myWord.getText().length();
			if(field.getText().equals(myWord.getText()+" ")) {
				System.out.println("CORRECT");
				countCorrect++;
			}else {
				System.out.println("INCORRECT");
			}
			myWord.setText(generate.getWord().toLowerCase());
			field.clear();
			total++;
		}
	}


}
