package cardgame.core;


public class Card {
	private Suit suit;
	private int face;
	public Card(Suit suit, int face) {
		if (suit == null)
			throw new IllegalArgumentException("The card needs a suit.");
		else
			this.suit = suit;
		if (face<1 || face>13 )
			throw new IllegalArgumentException("The card needs a number value between 1 and 13.");
		else
			this.face = face;
	}

	public Suit getSuit() {
		return this.suit;
	}
	public int getFace() {
		return this.face;
	}
	@Override
	public String toString() {
		return ""+getSuit()+getFace()+"";
	}
}
