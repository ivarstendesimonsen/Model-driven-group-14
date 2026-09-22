package cardgame.core;


public class Card {
	private char suit;
	private int face;
	public Card(char suit, int face) {
		if (!(suit=='S' || suit=='H' || suit=='D' || suit== 'C' ))
			throw new IllegalArgumentException("The cards' suit needs to be represented by their respective capital first letter. ('S','H','D','C')");
		else
			this.suit = suit;
		if (face<1 || face>13 )
			throw new IllegalArgumentException("The card needs a number value between 1 and 13.");
		else
			this.face = face;
	}

	public char getSuit() {
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
