package cardgame.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class Table {
	private List<Player> players = new ArrayList<Player>();
	private CardDeck carddeck;
	private List<Card> playedCards = new ArrayList<Card>();
	private Collection<TableListener> listeners = new ArrayList<TableListener>();

	public Table(int players) {
		this.carddeck = new CardDeck(13);
		carddeck.shufflePerfectly();
		for(int i = 1; i <= players; ++i) {
			Player player = new Player(i);
			player.setCardHand(new CardHand());
			this.players.add(player);
		}
		while (carddeck.getCardCount()>0) {
			for (Player player:this.players) {
				if(carddeck.getCardCount()>0) {
					this.carddeck.deal(player.getCardHand());
				}
				else
					break;
			}
		}
	}

	public List<Player> getPlayers() {
		return this.players;
	}
	public CardDeck getCardDeck() {
		return this.carddeck;
	}
	public List<Card> getPlayedCards(){
		return this.playedCards;
	}
	public void play(Player player, List<Card> cards) {
		for (Card card:cards) {
			this.playedCards.add(player.getCardHand().play(card));
		}
		fireStateChanged();
	}
	public void removePlayer(Player player) {
		this.carddeck.getCurrentDeck().addAll(player.getCardHand().getCurrentHand());
		player.getCardHand().getCurrentHand().clear();
		this.players.remove(player);
		fireStateChanged();
	}
	public void addListeners(TableListener listener) {
		if (!listeners.contains(listener))
			listeners.add(listener);
	}
	public void removeListeners(TableListener listener) {
		listeners.remove(listener);
	}
	public void fireStateChanged() {
		this.listeners.forEach(l -> l.updateAll());
	}
}
