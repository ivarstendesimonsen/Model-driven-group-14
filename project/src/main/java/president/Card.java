package president;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Card implements Comparable<Card> {
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
	@Override
	public int compareTo(Card z) {
	//Returns negative int if second card is bigger than first. Added logic for the "special cards" "1" and "2"
		if (this.getFace()!=z.getFace()) {
			if (this.getFace()==2)
				return 1;
			if (z.getFace()==2)
				return -1;
			if (this.getFace()==1)
				return 1;
			if (z.getFace()==1) {
				return -1;
			}
			return this.getFace() - z.getFace();
			
		}
		//If the cards have the same face, i want to sort them by their suit, mostly for consistency. For this card game, suit doesnt really matter a whole lot.
		if (this.getSuit() == 'S' && (z.getSuit() == 'H' || z.getSuit() == 'D' ||z.getSuit() == 'C'))
			return 1;
		if (this.getSuit() == 'H' && (z.getSuit() == 'D' ||z.getSuit() == 'C'))
			return 1;
		if (this.getSuit() == 'D' && z.getSuit() == 'C')
			return 1;
		
		return -1;
	}
}
