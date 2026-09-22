package cardgame.core;



import java.util.List;
import java.util.ArrayList;
import java.util.Collections;




public class CardDeck {
	private List<Card> currentDeck = new ArrayList<Card>();
	public CardDeck(int n) {
		if (n>13)
			throw new IllegalArgumentException("Kan ikke ha mer enn 13 kort i hver farge.");
		final char[] suits = {'S','H','D','C'};
		for (char x : suits) {
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
		if (n<0 || n>getCardCount())
			throw new IllegalArgumentException("Velg et kortnummer mellom 0 og "+getCardCount());
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
