package cardgame.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	public static void main(String[] args) {

		launch(args);
	}

	@Override
	public void start(final Stage primaryStage) throws Exception {
		try{
		Parent root =FXMLLoader.load(getClass().getResource("/cardgame/ui/startMenu.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Card Game");
		primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}

	}
}
