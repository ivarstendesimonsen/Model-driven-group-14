package cardgame.core;



import java.util.ArrayList;
import java.util.List;

public class CardHand {
	private List<Card> currentHand = new ArrayList<Card>();
	public CardHand() {

	}
	public void addCard(Card card) {
		this.currentHand.add(card);
	}
	public List<Card> getCurrentHand(){
		return this.currentHand;
	}
	public Card play(Card n) {
		if (!currentHand.contains(n))
			throw new IllegalArgumentException("Valgt kort ikke i cardhand.");
		return currentHand.remove(currentHand.indexOf(n));
	}
	public int getCardCount(){
		return this.currentHand.size();
	}
}
