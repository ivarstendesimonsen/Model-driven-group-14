package cardgame.ui;

import java.util.ArrayList;
import java.util.List;

import cardgame.core.Card;
import cardgame.core.CardGame;
import cardgame.core.Player;
import cardgame.core.Table;
import cardgame.core.TableListener;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;


public class PlayerController implements TableListener {
	CardGame game;
	Table table;
	Player player;
	private List<Card> markedCards = new ArrayList<Card>();

	@FXML private GridPane PlayerList;
	@FXML private GridPane CardHand;
	@FXML private Pane PlayedCard;
	@FXML private Text status;
	@FXML private Button playBtn;
	@FXML private Button passBtn;

	public PlayerController(CardGame game, Player player) {
		this.game=game;
		this.table=game.getTable();
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
			StackPane paneImg;
			if(goNextRow>8) {
				goNextRow=0;
				rowNumber++;
			}
			ImageView img = new ImageView(CardImages.of(card));
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
		for (Card card:game.getLastPlay()) {
			ImageView img = new ImageView(CardImages.of(card));
			img.setPreserveRatio(true);
			img.setFitHeight(160);
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
		playBtn.setDisable(!game.canPlay(player, markedCards));
		playBtn.setText(game.getActionName(player)+"  "+markedCards.size());
		passBtn.setDisable(!game.canPass(player));
	}
	@FXML
	public void playCards() {
		List<Card> cards = new ArrayList<Card>(markedCards);
		markedCards.clear();
		game.play(player, cards);
	}
	@FXML
	public void passTurn() {
		markedCards.clear();
		game.pass(player);
	}

	@FXML
	public void updatePlayerList() {
		PlayerList.getChildren().clear();
		Text tempTextPlayer = new Text("Players: ");
		tempTextPlayer.setStyle("-fx-font: 18 System;");
		PlayerList.add(tempTextPlayer, 0, 0);
		for(Player player:table.getPlayers()) {
			Text tempText = new Text(game.describe(player));
			PlayerList.add(tempText, 0, (table.getPlayers().indexOf(player)+1));
		}
	}
	@Override
	public void updateAll() {
		// Cards may have left the hand, e.g. when given away in the card exchange.
		markedCards.retainAll(player.getCardHand().getCurrentHand());
		status.setText(game.getStatus(player));
		updatePlayerList();
		updateMyCards();
		displayLastPlayedCard();
		updatePlayBtn();
	}
	void bootPlayer(Player player) {
		table.removeListeners(this);
		game.removePlayer(player);
	}

}
