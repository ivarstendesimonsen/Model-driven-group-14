package president;
import president.Game;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class PresidentController {
	Game game;
	Stage primaryStage;
	List<Scene> scenes;
	List<PlayerController> controllerList;
	List<Stage> stages;
	private boolean sliderChanged;
	@FXML
	private Slider sliderPlayers;
	@FXML
	int sliderPlayerValue;
	@FXML
	void initialize() {
		scenes= new ArrayList<Scene>();
		controllerList = new ArrayList<PlayerController>();
		stages = new ArrayList<Stage>();
		
	}
	@FXML
	void loadLastGame() {
		List<Player> players = new ArrayList<Player>();
		List<Player> rankList = new ArrayList<Player>();
		CardDeck carddeck = new CardDeck();
		List<Card> playedCards = new ArrayList<Card>();
		int roundType= 0;
		List<Player> lastPlayerList = new ArrayList<Player>();
		try {
			
			roundType=Game.getSaveStateFromFile("src/main/java/president/saveState/saveState.txt",players, rankList, playedCards, carddeck, roundType, lastPlayerList);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		game=new Game(players, rankList, playedCards, carddeck, roundType, ((lastPlayerList.size()!=0)? lastPlayerList.get(0):null));
		
		for (Player player:game.getPlayers()) {
			
			try {
				player.setGame(game);
				PlayerController playercontroller = new PlayerController(this.game,player);
				FXMLLoader playerfxml = new FXMLLoader(getClass().getResource("/president/Player.fxml"));
				playerfxml.setController(playercontroller);
				Parent root =playerfxml.load();
				

				Scene scene = new Scene(root);
				Stage stage= new Stage();

				stage.setScene(scene);
				stage.show();
				stage.setOnCloseRequest(event -> playercontroller.bootPlayer(player));
				stage.setTitle("Player"+(game.getPlayers().indexOf(player)+1));
				controllerList.add(playercontroller);
				game.addListeners(playercontroller);
				stages.add(stage);
				
			} catch (IOException e) {
				e.printStackTrace();
		}
	}
}
	@FXML
	void onSliderChanged() {
		sliderPlayerValue =  (int) Math.round(sliderPlayers.getValue());
		sliderChanged=true;
	}
	@FXML
	void startNewGame() {
		if (sliderChanged==true)
			game=new Game(sliderPlayerValue);
		else
			game=new Game((int) Math.round(sliderPlayers.getValue()));
		for (Player player:game.getPlayers()) {
			try {
				PlayerController playercontroller = new PlayerController(this.game,player);
				FXMLLoader playerfxml = new FXMLLoader(getClass().getResource("/president/Player.fxml"));
				playerfxml.setController(playercontroller);
				Parent root =playerfxml.load();
				

				Scene scene = new Scene(root);
				Stage stage= new Stage();

				stage.setScene(scene);
				stage.show();
				stage.setOnCloseRequest(event -> playercontroller.bootPlayer(player));
				stage.setTitle("Player"+(game.getPlayers().indexOf(player)+1));
				controllerList.add(playercontroller);
				game.addListeners(playercontroller);
				stages.add(stage);
				
			} catch (IOException e) {
				e.printStackTrace();
		}
	}
		
	}



}
