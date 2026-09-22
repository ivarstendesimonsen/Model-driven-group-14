package president;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;





public class Game {
//	private Map<String, CardHand> PlayerCards = new HashMap<String, CardHand>();
	private List<Player> players = new ArrayList<Player>();
	private List<Player> rankList = new ArrayList<Player>();
	private CardDeck carddeck;
	private List<Card> playedCards = new ArrayList<Card>();
	private int roundType;
	private Player lastPlayer;
	private Collection<PlayerController> listeners = new ArrayList<PlayerController>();
	public Game(List<Player> players,List<Player> rankList, List<Card> playedCards, CardDeck carddeck, int roundType, Player lastPlayer) {
		this.players = players;
		this.rankList = rankList;
		this.carddeck = carddeck;
		this.playedCards = playedCards;
		this.setRoundType(roundType);
		this.setLastPlayer(lastPlayer);
	}

	public Game(int players) {
		this.carddeck = new CardDeck(13);
		carddeck.shufflePerfectly();
		for(int i = 1; i <= players; ++i) {
			Player player = new Player(i,this);
			CardHand cardhand = new CardHand();
			player.setCardHand(cardhand);
			this.players.add(player);
			if (i==1)
				this.players.get(0).setTurn(true);
//			PlayerCards.put(player.getName(), cardhand);
		}
		//Deals the whole carddeck to the players.
		while (carddeck.getCardCount()>0) {
			for (Player player:this.players) {
				if(carddeck.getCardCount()>0) {
					this.carddeck.deal(player.getCardHand());
				}
				else
					break;
			}
		}
		for (Player player:getPlayers()) {
			Collections.sort(player.getCardHand().getCurrentHand());
		}
		
		
	}

	public void setLastPlayer(Player lastPlayer) {
		this.lastPlayer=lastPlayer;
	}
	public Player getLastPlayer() {
		return this.lastPlayer;
	}
	public List<Player> getPlayers() {
	    return this.players;
	}
	public void setPlayers(List<Player> players) {
		this.players=players;
	}
	public List<Player> getRankList(){
		return this.rankList;
	}
	public CardDeck getCardDeck() {
		return this.carddeck;
	}
	public List<Card> getPlayedCards(){
		return this.playedCards;
	}
	public void setRoundType(int roundType) {
		this.roundType=roundType;
	}
	public int getRoundType() {
		return this.roundType;
	}
	public void newRound() {
		List<Card> tempList = new ArrayList<Card>();
		tempList.addAll(this.getPlayedCards());
		for (Card card:tempList) {
			this.getCardDeck().getCurrentDeck().add(card);
			this.getPlayedCards().remove(card);
		}
		this.roundType=0;
		tempList.clear();
		carddeck.shufflePerfectly();
		//Deals the whole carddeck to the players.
		while (carddeck.getCardCount()>0) {
			for (Player player:this.players) {
				if(carddeck.getCardCount()>0) {
					this.carddeck.deal(player.getCardHand());
				}
				else
					break;
			}
		}
		for (Player player:getPlayers()) {
			Collections.sort(player.getCardHand().getCurrentHand());
		}
		
		//pretty hardcoded... Was tired when writing, and autopiloted this code section. 
		switch (rankList.size()) {
		
			case 3:
				//Last place gets the 2 worst cards from the first place.
					rankList.get(2).getCardHand().addCard(rankList.get(0).getCardHand().getCard(0));
					rankList.get(0).getCardHand().getCurrentHand().remove(0);
					rankList.get(2).getCardHand().addCard(rankList.get(0).getCardHand().getCard(0));
					rankList.get(0).getCardHand().getCurrentHand().remove(0);
				//First place gets the last places' 2 best cards.
					rankList.get(0).getCardHand().addCard(rankList.get(2).getCardHand().getCard(rankList.get(2).getCardHand().getCurrentHand().size()-1));
					rankList.get(2).getCardHand().getCurrentHand().remove(rankList.get(2).getCardHand().getCurrentHand().size()-1);
					rankList.get(0).getCardHand().addCard(rankList.get(2).getCardHand().getCard(rankList.get(2).getCardHand().getCurrentHand().size()-1));
					rankList.get(2).getCardHand().getCurrentHand().remove(rankList.get(2).getCardHand().getCurrentHand().size()-1);
				break;
			case 4:
			//Last place gets the 2 worst cards from the first place.
				rankList.get(3).getCardHand().addCard(rankList.get(0).getCardHand().getCard(0));
				rankList.get(0).getCardHand().getCurrentHand().remove(0);
				rankList.get(3).getCardHand().addCard(rankList.get(0).getCardHand().getCard(0));
				rankList.get(0).getCardHand().getCurrentHand().remove(0);
			//First place gets the last places' 2 best cards.
				rankList.get(0).getCardHand().addCard(rankList.get(3).getCardHand().getCard(rankList.get(3).getCardHand().getCurrentHand().size()-1));
				rankList.get(3).getCardHand().getCurrentHand().remove(rankList.get(3).getCardHand().getCurrentHand().size()-1);
				rankList.get(0).getCardHand().addCard(rankList.get(3).getCardHand().getCard(rankList.get(3).getCardHand().getCurrentHand().size()-1));
				rankList.get(3).getCardHand().getCurrentHand().remove(rankList.get(3).getCardHand().getCurrentHand().size()-1);
			//second loser gets the worst card of the second place
				rankList.get(2).getCardHand().addCard(rankList.get(1).getCardHand().getCard(0));
				rankList.get(1).getCardHand().getCurrentHand().remove(0);
			
			//Second place gets the best card of the second loser.
				rankList.get(1).getCardHand().addCard(rankList.get(2).getCardHand().getCard(rankList.get(2).getCardHand().getCurrentHand().size()-1));
				rankList.get(2).getCardHand().getCurrentHand().remove(rankList.get(2).getCardHand().getCurrentHand().size()-1);
				break;
			case 5:
			//Last place gets the 2 worst cards from the first place.
				rankList.get(4).getCardHand().addCard(rankList.get(0).getCardHand().getCard(0));
				rankList.get(0).getCardHand().getCurrentHand().remove(0);
				rankList.get(4).getCardHand().addCard(rankList.get(0).getCardHand().getCard(0));
				rankList.get(0).getCardHand().getCurrentHand().remove(0);
			//First place gets the last places' 2 best cards.
				rankList.get(0).getCardHand().addCard(rankList.get(4).getCardHand().getCard(rankList.get(4).getCardHand().getCurrentHand().size()-1));
				rankList.get(4).getCardHand().getCurrentHand().remove(rankList.get(4).getCardHand().getCurrentHand().size()-1);
				rankList.get(0).getCardHand().addCard(rankList.get(4).getCardHand().getCard(rankList.get(4).getCardHand().getCurrentHand().size()-1));
				rankList.get(4).getCardHand().getCurrentHand().remove(rankList.get(4).getCardHand().getCurrentHand().size()-1);
			//second loser gets the worst card of the second place	
				rankList.get(3).getCardHand().addCard(rankList.get(1).getCardHand().getCard(0));
				rankList.get(1).getCardHand().getCurrentHand().remove(0);
			//Second place gets the best card of the second loser.
				rankList.get(1).getCardHand().addCard(rankList.get(3).getCardHand().getCard(rankList.get(3).getCardHand().getCurrentHand().size()-1));
				rankList.get(3).getCardHand().getCurrentHand().remove(rankList.get(3).getCardHand().getCurrentHand().size()-1);
				break;
			case 6:
			//Last place gets the 2 worst cards from the first place.
				rankList.get(5).getCardHand().addCard(rankList.get(0).getCardHand().getCard(0));
				rankList.get(0).getCardHand().getCurrentHand().remove(0);
				rankList.get(5).getCardHand().addCard(rankList.get(0).getCardHand().getCard(0));
				rankList.get(0).getCardHand().getCurrentHand().remove(0);
			//First place gets the last places' 2 best cards.
				rankList.get(0).getCardHand().addCard(rankList.get(5).getCardHand().getCard(rankList.get(5).getCardHand().getCurrentHand().size()-1));
				rankList.get(5).getCardHand().getCurrentHand().remove(rankList.get(5).getCardHand().getCurrentHand().size()-1);
				rankList.get(0).getCardHand().addCard(rankList.get(5).getCardHand().getCard(rankList.get(5).getCardHand().getCurrentHand().size()-1));
				rankList.get(5).getCardHand().getCurrentHand().remove(rankList.get(5).getCardHand().getCurrentHand().size()-1);
			//second loser gets the worst card of the second place	
				rankList.get(4).getCardHand().addCard(rankList.get(1).getCardHand().getCard(0));
				rankList.get(1).getCardHand().getCurrentHand().remove(0);
			//Second place gets the best card of the second loser.
				rankList.get(1).getCardHand().addCard(rankList.get(4).getCardHand().getCard(rankList.get(4).getCardHand().getCurrentHand().size()-1));
				rankList.get(4).getCardHand().getCurrentHand().remove(rankList.get(4).getCardHand().getCurrentHand().size()-1);
				break;
			case 7:
			//Last place gets the 2 worst cards from the first place.
				rankList.get(6).getCardHand().addCard(rankList.get(0).getCardHand().getCard(0));
				rankList.get(0).getCardHand().getCurrentHand().remove(0);
				rankList.get(6).getCardHand().addCard(rankList.get(0).getCardHand().getCard(0));
				rankList.get(0).getCardHand().getCurrentHand().remove(0);
			//First place gets the last places' 2 best cards.
				rankList.get(0).getCardHand().addCard(rankList.get(6).getCardHand().getCard(rankList.get(6).getCardHand().getCurrentHand().size()-1));
				rankList.get(6).getCardHand().getCurrentHand().remove(rankList.get(6).getCardHand().getCurrentHand().size()-1);
				rankList.get(0).getCardHand().addCard(rankList.get(6).getCardHand().getCard(rankList.get(6).getCardHand().getCurrentHand().size()-1));
				rankList.get(6).getCardHand().getCurrentHand().remove(rankList.get(6).getCardHand().getCurrentHand().size()-1);
			//second loser gets the worst card of the second place	
				rankList.get(5).getCardHand().addCard(rankList.get(1).getCardHand().getCard(0));
				rankList.get(1).getCardHand().getCurrentHand().remove(0);
			//Second place gets the best card of the second loser.
				rankList.get(1).getCardHand().addCard(rankList.get(5).getCardHand().getCard(rankList.get(5).getCardHand().getCurrentHand().size()-1));
				rankList.get(5).getCardHand().getCurrentHand().remove(rankList.get(5).getCardHand().getCurrentHand().size()-1);
				break;
				
		}
		for (Player player:rankList) {
			player.setPass(false);
			if(rankList.indexOf(player)==rankList.size()-1) {
				player.setMyTurn(true);
			}
		}
		rankList.clear();
		fireStateChanged();
	}
	public Boolean checkEquality(List<Card> markedCards) {
		//checks if all cards that are not 6's are equal.
		//first it removes all cards of face 6.
		List<Card> tempList= new ArrayList<Card>();
		for (Card card:markedCards) {
			if (card==null)
				throw new IllegalArgumentException("Card can't be null");
			if(card.getFace()==3 && card.getSuit()=='C')
				return false;
			tempList.add(card);
		}
		for (Card card:markedCards) {
			if (card.getFace()==6) {
				tempList.remove(card);
			}
		}
		//need to handle the exception when all marked cards are of value 6.
		if (tempList.size()==0) {
			int lastValue;
			if(playedCards.size()!=0) {
				if (playedCards.get(playedCards.size() - 1).getFace()==1) 
					lastValue=14;
				else if (playedCards.get(playedCards.size() - 1).getFace()==2) 
					lastValue=15;
				else
					lastValue=playedCards.get(playedCards.size() - 1).getFace();
			}
			else
				lastValue=0;
			//If cards of value 6 are played as standalone cards, they count as regular cards of value 6.
			//We know at this point that all the marked cards were equal, because they were all 6s'.
			//so we just check if the last played card is lower than 6 or not.
			if(lastValue>6 ) {
				return false;
			}
			else {
				return true;
			}
			
		}
		int cardFace = tempList.get(0).getFace();
		if(this.playedCards==null) {
			for (Card card:tempList) {
				if(card.getFace()!= cardFace)
					return false;
			}
		}
		//If roundType is 0, it doesnt need to check the last played cards, because you are starting the round.
		if (getRoundType()==0) {
			for (Card card:tempList) {
				if(card.getFace()!= cardFace)
					return false;
			}
			return true;
			}
		//Makes rule that ace cards have the face value 14, and cards with the face 2 are worth 15.
		
		int lastValue;
		if (playedCards.size()!=0) {
				
			if (playedCards.get(playedCards.size() - 1).getFace()==1) 
				lastValue=14;
			else if (playedCards.get(playedCards.size() - 1).getFace()==2) 
				lastValue=15;
			else
				lastValue=playedCards.get(playedCards.size() - 1).getFace();
		}
		else
			lastValue=0;
		
		//repeats same rule for the recently played card as well.
		int tempFace;
		if(cardFace==1)
			tempFace=14;
		else if (cardFace==2)
			tempFace=15;
		else
			tempFace=cardFace;
		
		//then checks whether the value of your marked cards are higher than the previously played cards.
		if(lastValue>tempFace ) {
			return false;
		}
		else {
			for (Card card:tempList) {
				if(card.getFace()!= cardFace)
					return false;
			}
			return true;
		}
	}

	//unfinished
	public Boolean validateMarkedCards(List<Card> markedCards, Player player) {
		if (this.roundType==0) {
			//when roundType is 0, the person playing is deciding whether he wants to start a round with singles, doubles, triples or quadruples.
			if (markedCards.size()>=1 && markedCards.size()<=4) {
					if (checkEquality(markedCards)) {
						
						return true;
					}
					else {
						System.err.println("All cards played together must share face.");
						return false;
					}
			}
			
			System.err.println("You may start the round with singles, doubles, triples or quadruples.");
			return false;
		}
		else {
			if(markedCards.size()==1 && markedCards.get(0).getFace()==3 && markedCards.get(0).getSuit()=='C' && player.getCardHand().getCardCount()!=1) 
				return true;
	
			if (markedCards.size()!=this.roundType) {
				return false;
			}
			else {
				return checkEquality(markedCards);
			}

		}
		
	}
	public void endTurn(Player player) {
		

		player.getMarkedCards().clear();
		//Checks if all players are out of cards. and if the one who ended turn is also out of cards, or stuck with the card c3.
		if ((player.getCardHand().getCurrentHand().isEmpty()&& this.getPlayers().size()==rankList.size()) || (player.getCardHand().getCardCount()==1 && player.getCardHand().getCard(0).getFace()==3 && player.getCardHand().getCard(0).getSuit()=='C') && this.getPlayers().size()-1==rankList.size()) {
			
			newRound();
		}
		else {
			//Checks whether this player is the last playerobject in the player list.
			// If it is not, it passes the turn over to the next player. If this is last playerobject, it passes turn to first playerobject.
			if (this.getPlayers().indexOf(player)!=this.getPlayers().size()-1) {
				if(this.getPlayers().size()!=1) {	
					if(this.getPlayers().size()-1==rankList.size() && player.getCardHand().getCardCount()!=0) {
						player.setMyTurn(true);
					}
					else {
						
						this.getPlayers().get(this.getPlayers().indexOf(player)+1).setMyTurn(true);
						player.setMyTurn(false);
					}
				}
				
			}
			else if (player.getMyTurn()==true) {
				if(!(this.getPlayers().size()==1 ||this.getPlayers().size()-1==rankList.size()) ) {
					this.getPlayers().get(0).setMyTurn(true);
					player.setMyTurn(false);
				}
			}
			
		}
		fireStateChanged();
	}
	public void endRally(Player activePlayer) {
		if ((activePlayer.getCardHand().getCurrentHand().isEmpty()&& this.getPlayers().size()==rankList.size()) || (activePlayer.getCardHand().getCardCount()==1 && activePlayer.getCardHand().getCard(0).getFace()==3 && activePlayer.getCardHand().getCard(0).getSuit()=='C') && this.getPlayers().size()-1==rankList.size()) {
			newRound();
		}
		else {
		//Throws out the "played card bunch", and gives the last player that played a card the opportunity to start the round.
		activePlayer.getMarkedCards().clear();
		for (Player player:this.getPlayers()) {
			player.setPass(false);
		}
		this.setRoundType(0);
		//Creates temporary list with playedCards to avoid modifying the initial list while it is being iterated over.
		List<Card> tempList = new ArrayList<Card>();
		tempList.addAll(this.getPlayedCards());
		for (Card card:tempList) {
			this.getCardDeck().getCurrentDeck().add(card);
			this.getPlayedCards().remove(card);
		}
		tempList.clear();
		//
		if(this.getLastPlayer().getCardHand().getCardCount()==0)
			activePlayer.setMyTurn(true);
		else {
			this.getLastPlayer().setMyTurn(true);;
		}
		
		}
		fireStateChanged();
	}
	public void addListeners(PlayerController playercontroller) {
		if (!listeners.contains(playercontroller))
			listeners.add(playercontroller);
	}
	public void removeListeners(PlayerController playercontroller) {
		listeners.remove(playercontroller);
	}
	public void fireStateChanged() {
		this.listeners.forEach(l -> l.updateAll());
	};
	public void writeSaveStateToFile(String filename) throws FileNotFoundException{
		try {
			PrintWriter writer = new PrintWriter(filename);
			for (Player player:getPlayers()) {
				
				for (Card card:player.getCardHand().getCurrentHand()) {
					writer.print(card+" ");
				}
				writer.println();
			}
			writer.println("next");
			for (Player player:getPlayers()) {
				writer.print(player.getMyTurn()+" ");
				writer.print(player.getPass()+" ");
				writer.print(player.equals(this.lastPlayer)+" ");
				writer.println(this.rankList.contains(player));
			}
			writer.println("next");
			for (Card card:this.getCardDeck().getCurrentDeck()) {
				writer.print(card+" ");
			}
			writer.println();
			for (Card card:this.getPlayedCards()) {
				writer.print(card+" ");
			}
			writer.println();
			writer.println(getRoundType());
	
		writer.println("next");
			writer.flush();
			writer.close();
		}catch(FileNotFoundException e){
			System.out.println("Wrong filename");
		}
	}


public static int getSaveStateFromFile(String filename,List<Player> players,List<Player> rankList, List<Card> playedCards, CardDeck carddeck, int roundType,  List<Player> lastPlayerList) throws FileNotFoundException {
		try {
			
		Scanner scanner = new Scanner(new File(filename));
		int goNext=0;
		int lineNumber= 0;
		while (scanner.hasNextLine()){
			String line = scanner.nextLine();
			
			
			
			if (line.equals("next")) {
				goNext++;
				lineNumber=0;
			
			}
			else {
				
				if(goNext==0) {
					String[] card;
					Player player= new Player(lineNumber+1);
					card = line.split(" ");	
					CardHand cardhand = new CardHand();
					for (String card1:card) {
						int i;
						if (card.length!=0) {
							if (!card1.equals("")) {
								if(card1.length()<=2) {
									i=Integer.parseInt(card1.substring(1,2));
								}
								else {
									i=Integer.parseInt(card1.substring(1,3));
								}
								char c=card1.charAt(0);
								
								Card tempCard=new Card(c,i);
								cardhand.addCard(tempCard);
							}
						}
					}
					player.setCardHand(cardhand);
					players.add(player);
					
				}
				if(goNext==1) {
					String[] playerStates;
					playerStates=line.split(" ");
					players.get(lineNumber).setTurn(Boolean.parseBoolean(playerStates[0]));
					players.get(lineNumber).setterPass(Boolean.parseBoolean(playerStates[1]));
					if(Boolean.parseBoolean(playerStates[2])) {
						lastPlayerList.add(players.get(lineNumber));
					}
					if(Boolean.parseBoolean(playerStates[3])) {
						rankList.add(players.get(lineNumber));
					}
					
				}
				if(goNext==2) {
					String[] card;
					card = line.split(" ");	
					if (lineNumber==0){
						if (card.length!=0) {
							for (String card1:card) {
								int i;
								if (!card1.equals("")) {
									if(card1.length()<=2) {
										i=Integer.parseInt(card1.substring(1,2));
									}
									else
										i=Integer.parseInt(card1.substring(1,3));
									char c=card1.charAt(0);
									Card tempCard=new Card(c,i);
									carddeck.getCurrentDeck().add(tempCard);
								}
							}
						}
					}
					if(lineNumber==1) {
						if (card.length!=0) {
							for (String card1:card) {
								int i;
								if (!card1.equals("")) {
									if(card1.length()<=2) {
										i=Integer.parseInt(card1.substring(1,2));
									}
									else
										i=Integer.parseInt(card1.substring(1,3));
									char c=card1.charAt(0);
									Card tempCard=new Card(c,i);
									playedCards.add(tempCard);
								}
							}
						}
					}
					if(lineNumber==2) {
						roundType=Integer.parseInt(line);
					}
				}
				lineNumber++;
			}
		}
		scanner.close();
	
	}catch(FileNotFoundException e) {
		throw new FileNotFoundException("File not Found");
	}
	return roundType;
}
	
public static void main(String[] args) {
//	Game game2= new Game(List<Player> players,List<Player> rankList, List<Card> playedCards, CardDeck carddeck, int roundType, Player lastPlayer);
//	Game game=new Game(3);
//	try {
//	game.writeSaveStateToFile("src/main/java/president/saveState/saveState.txt");
//	}catch(FileNotFoundException e) {
//		System.out.println("Wrong filename");
//	}

	
//	try {
////		game.getPlayerHandFromFile("src/main/java/president/saveState/playerHands.txt");
//	} catch (FileNotFoundException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
//	
	
//	System.out.println(game.getPlayersFromFile("saveState.txt"));
}


}
