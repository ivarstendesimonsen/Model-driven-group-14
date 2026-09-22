package president;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class PlayerController {
	Game game;
	Player player;
	
//	Image image;
	@FXML private GridPane PlayerList;
	@FXML private GridPane CardHand;
	@FXML private Pane PlayedCard;
	@FXML private Text RoundType;
	@FXML private Button playBtn;
	
	public PlayerController(Game game, Player player) {
		this.game=game;
		this.player=player;
		
	}
	@FXML
	void initialize() {
		updateAll();
	}
	@FXML
	public void updateMyCards() {
		CardHand.getChildren().clear();
		int goNextRow=0;
		int rowNumber=0;
		for (Card card:player.getCardHand().getCurrentHand()) {
			Image image;
			StackPane paneImg;
			try {
				if(goNextRow>8) {
					goNextRow=0;
					rowNumber++;
				}
				image= new Image(new FileInputStream(new File("src/main/java/president/cardImg/"+"/"+card.toString()+".png").getAbsolutePath()));
				ImageView img = new ImageView(image);
				img.setPreserveRatio(true);
				img.setFitHeight(125);
				paneImg = new StackPane();
				paneImg.getChildren().add(img);
				paneImg.setOnMouseClicked(event ->  {
					toggleMarkedCards(card, paneImg);
					updatePlayBtn();
					}
				);
				paneImg.setId("card"+player.getCardHand().getCurrentHand().indexOf(card));
				if (player.getMarkedCards().contains(card)) {
					paneImg.setStyle("-fx-border-color:#424242; -fx-stroke-width:2px; -fx-background-color:rgba(0, 255, 255, 0.87);");
				}
				else {
					paneImg.setStyle("-fx-stroke-width:0px; -fx-background-color:none;");
				}
				CardHand.add( paneImg,goNextRow,0+rowNumber);
				goNextRow++;
				
			}catch(FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	@FXML
	public void displayLastPlayedCard() {
		try {
		if (!game.getPlayedCards().isEmpty()) {
			Image lastCard;
			lastCard = new Image(new FileInputStream(new File("src/main/java/president/cardImg/"+"/"+game.getPlayedCards().get(game.getPlayedCards().size()-1)+".png").getAbsolutePath()));
			ImageView img = new ImageView(lastCard);
			img.setPreserveRatio(true);
			img.setFitHeight(225);
			PlayedCard.getChildren().add(img);
		}
		else
			if (PlayedCard.getChildren().size()>0)
				PlayedCard.getChildren().clear();
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void toggleMarkedCards(Card card, StackPane paneImg) {
		
		if (player.getMarkedCards().contains(card)) {
			unMarkCard(card);
			paneImg.setStyle("-fx-stroke-width:0px; -fx-background-color:none;");
		}
		else {
			markCard(card);
			paneImg.setStyle("-fx-border-color:#424242; -fx-stroke-width:2px; -fx-background-color:rgba(0, 255, 255, 0.87);");
		
		}
	}

	public void markCard(Card card) {
		
		player.markCard(card);
	}

	public void unMarkCard(Card card) {
		player.unmarkCards(card);
	}
	
	public void updatePlayBtn() {
		if(!player.getMyTurn()) {
			playBtn.setText("Wait...");
			playBtn.setDisable(true);
		}
		else {
			playBtn.setDisable(false);
			if(player.getMarkedCards().isEmpty()) {
				playBtn.setText("Pass");
			}
			else {
				playBtn.setText("Play  "+player.getMarkedCards().size());
				if (!game.validateMarkedCards(player.getMarkedCards(),player)) {
					playBtn.setDisable(true);
					playBtn.setText("invalid play");
				}
			}
		}
		
	}
	@FXML
	public void playCards() {
		player.play(player.getMarkedCards());
	}

	@FXML
	public void updatePlayerList() {
		PlayerList.getChildren().clear();
		Text tempTextPlayer = new Text("Players: ");
		tempTextPlayer.setStyle("-fx-font: 18 System;");
		PlayerList.add(tempTextPlayer, 0, 0);
		for(Player player:game.getPlayers()) {
			Text tempText = new Text();
			String pass;
			String myTurn;
			if(player.getPass()) {
				pass="pass";
			}
			else {
				pass="";
			}
			if(player.getMyTurn()) {
				myTurn="active";
			}
			else {
				myTurn="";
			}
			tempText.setText(player.getName()+" "+myTurn+pass+ (game.getRankList().contains(player) ? "Finished":""));
			PlayerList.add(tempText, 0, (game.getPlayers().indexOf(player)+1));
			
		}
	}
	@FXML
	public void updateRoundType() {
		RoundType.setText("Roundtype: "+game.getRoundType());
		//temp

	}
	public void saveGameState() throws FileNotFoundException {
		try {
		game.writeSaveStateToFile("src/main/java/president/saveState/saveState.txt");
		}catch(FileNotFoundException e) {
			throw new FileNotFoundException("Finner ikke fil å skrive til. Spill ikke lagret.");
		}
	}
	public void updateAll() {
		updatePlayerList();
		updateMyCards();
		displayLastPlayedCard();
		updatePlayBtn();
		updateRoundType();
	}
	void bootPlayer(Player player) {
		if (player.getMyTurn()) {
			game.endTurn(player);
		}
		if (player.getCardHand().getCurrentHand().isEmpty()) {
			game.getRankList().remove(player);
		}
		for (Card card:player.getCardHand().getCurrentHand()) {
			game.getCardDeck().getCurrentDeck().add(card);
			}
		game.removeListeners(this);
		game.getPlayers().remove(player);
		updateAll();
	}	
	
}
