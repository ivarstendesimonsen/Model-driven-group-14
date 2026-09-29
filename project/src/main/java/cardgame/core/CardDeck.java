package cardgame.core;



import java.util.List;
import java.util.ArrayList;
import java.util.Collections;




public class CardDeck {
	private List<Card> currentDeck = new ArrayList<Card>();
	public CardDeck(int n) {
		if (n>13)
			throw new IllegalArgumentException("Cannot have more than 13 cards in each suit.");
		for (Suit x : Suit.values()) {
			for (int i = 1; i <= n; ++i) {
				Card card = new Card(x,i);
				this.currentDeck.add(card);
			};
		};
	};
	public List<Card> getCurrentDeck(){
		return this.currentDeck;
	};
	public int getCardCount(){
		return this.currentDeck.size();
	};
	public Card getCard(int n) {
		if (n<0 || n>=getCardCount())
			throw new IllegalArgumentException("Choose a card number between 0 and "+(getCardCount()-1));
		return this.currentDeck.get(n);
	};

	public void shufflePerfectly() {
		Collections.shuffle(currentDeck);
	};
	public void deal(CardHand CardHand) {

			CardHand.addCard(getCard(getCardCount()-1));
			this.currentDeck.remove(getCardCount()-1);
	};
};
