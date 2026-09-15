package president;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {
	public static void main(String[] args) {

		launch(args);
	}

	@Override
	public void start(final Stage primaryStage) throws Exception {
		try{
		Parent root =FXMLLoader.load(getClass().getResource("/president/startMenu.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}  
}
//Scanner numberPlayers = new Scanner(System.in);
//System.out.println("Enter number of players from 3 to 7");
//Game game = new Game(numberPlayers.nextInt());
//numberPlayers.close();
//for (Player player:game.getPlayers()) {
//	System.out.println(player.getName());
//	System.out.println(player.getCardHand());
//}
//
//System.out.println("Carddeck length");
//System.out.println(game.getCardDeck().getCardCount());
//List<Player> players= game.getPlayers();
//Card card1 = players.get(0).getCardHand().getCard(3);
//Card card2 = players.get(0).getCardHand().getCard(14);
//Card card3 = players.get(1).getCardHand().getCard(3);
//Card lastCard = new Card('C',1);
//
//	
//
////players.get(0).markCard(card1);
////players.get(0).play(players.get(0).getMarkedCards());
//////System.out.println(players.get(0).getCardHand());
//////card = players.get(1).getCardHand().getCard(0);
////System.out.println("Player1 myturn="+players.get(0).getMyTurn());
////System.out.println("Player2 myturn="+players.get(1).getMyTurn());
////System.out.println("Player3 myturn="+players.get(2).getMyTurn());
////players.get(1).markCard(card3);
////players.get(1).play(players.get(1).getMarkedCards());
////System.out.println("Player1 myturn="+players.get(0).getMyTurn());
////System.out.println("Player2 myturn="+players.get(1).getMyTurn());
////System.out.println("Player3 myturn="+players.get(2).getMyTurn());
////card3=players.get(1).getCardHand().getCard(3);
////players.get(1).markCard(card3);
////players.get(1).play(players.get(1).getMarkedCards());
////System.out.println("Player1 myturn="+players.get(0).getMyTurn());
////System.out.println("Player2 myturn="+players.get(1).getMyTurn());
////System.out.println("Player3 myturn="+players.get(2).getMyTurn());
////players.get(2).play(players.get(2).getMarkedCards());
////card1 = players.get(0).getCardHand().getCard(0);
////players.get(0).markCard(card1);
////players.get(0).markCard(card2);
////players.get(0).play(players.get(0).getMarkedCards());
////System.out.println("Player1 myturn="+players.get(0).getMyTurn());
////System.out.println("Player2 myturn="+players.get(1).getMyTurn());
////System.out.println("Player3 myturn="+players.get(2).getMyTurn());
//for (Player player:game.getPlayers()) {
//	CardHand cardhand = new CardHand(); 
//	cardhand.addCard(lastCard);
//	player.setCardHand(cardhand);
//}
//players.get(0).markCard(players.get(0).getCardHand().getCard(0));
//players.get(1).markCard(players.get(1).getCardHand().getCard(0));
//players.get(2).markCard(players.get(2).getCardHand().getCard(0));
//players.get(0).play(players.get(0).getMarkedCards());
//players.get(1).play(players.get(1).getMarkedCards());
//players.get(2).play(players.get(2).getMarkedCards());
