package cardgame.ui;

import java.util.ArrayList;
import java.util.List;

import cardgame.core.Card;
import cardgame.core.Player;
import cardgame.core.Table;
import cardgame.core.TableListener;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;


public class PlayerController implements TableListener {
	Table table;
	Player player;
	private List<Card> markedCards = new ArrayList<Card>();

	@FXML private GridPane PlayerList;
	@FXML private GridPane CardHand;
	@FXML private Pane PlayedCard;
	@FXML private Button playBtn;

	public PlayerController(Table table, Player player) {
		this.table=table;
		this.player=player;

	}
	@FXML
	void initialize() {
		updateAll();
	}
	private Image loadImage(Card card) {
		return new Image(getClass().getResourceAsStream("/cards/"+card.toString()+".png"));
	}
	@FXML
	public void updateMyCards() {
		CardHand.getChildren().clear();
		int goNextRow=0;
		int rowNumber=0;
		for (Card card:player.getCardHand().getCurrentHand()) {
			StackPane paneImg;
			if(goNextRow>8) {
				goNextRow=0;
				rowNumber++;
			}
			ImageView img = new ImageView(loadImage(card));
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
			if (markedCards.contains(card)) {
				paneImg.setStyle("-fx-border-color:#424242; -fx-stroke-width:2px; -fx-background-color:rgba(0, 255, 255, 0.87);");
			}
			else {
				paneImg.setStyle("-fx-stroke-width:0px; -fx-background-color:none;");
			}
			CardHand.add( paneImg,goNextRow,0+rowNumber);
			goNextRow++;
		}
	}
	@FXML
	public void displayLastPlayedCard() {
		PlayedCard.getChildren().clear();
		if (!table.getPlayedCards().isEmpty()) {
			ImageView img = new ImageView(loadImage(table.getPlayedCards().get(table.getPlayedCards().size()-1)));
			img.setPreserveRatio(true);
			img.setFitHeight(225);
			PlayedCard.getChildren().add(img);
		}
	}

	public void toggleMarkedCards(Card card, StackPane paneImg) {

		if (markedCards.contains(card)) {
			markedCards.remove(card);
			paneImg.setStyle("-fx-stroke-width:0px; -fx-background-color:none;");
		}
		else {
			markedCards.add(card);
			paneImg.setStyle("-fx-border-color:#424242; -fx-stroke-width:2px; -fx-background-color:rgba(0, 255, 255, 0.87);");

		}
	}

	public void updatePlayBtn() {
		playBtn.setDisable(markedCards.isEmpty());
		playBtn.setText("Play  "+markedCards.size());
	}
	@FXML
	public void playCards() {
		List<Card> cards = new ArrayList<Card>(markedCards);
		markedCards.clear();
		table.play(player, cards);
	}

	@FXML
	public void updatePlayerList() {
		PlayerList.getChildren().clear();
		Text tempTextPlayer = new Text("Players: ");
		tempTextPlayer.setStyle("-fx-font: 18 System;");
		PlayerList.add(tempTextPlayer, 0, 0);
		for(Player player:table.getPlayers()) {
			Text tempText = new Text(player.getName()+" ("+player.getCardHand().getCardCount()+" cards)");
			PlayerList.add(tempText, 0, (table.getPlayers().indexOf(player)+1));
		}
	}
	@Override
	public void updateAll() {
		updatePlayerList();
		updateMyCards();
		displayLastPlayedCard();
		updatePlayBtn();
	}
	void bootPlayer(Player player) {
		table.removeListeners(this);
		table.removePlayer(player);
	}

}
