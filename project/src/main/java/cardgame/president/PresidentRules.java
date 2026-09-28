package cardgame.president;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import cardgame.core.Card;
import cardgame.core.Suit;

/**
 * Card values for President og Boms: 3 is lowest, then 4 ... K, A, 2,
 * and the three of clubs beats everything.
 */
public final class PresidentRules {

	public static final int CLUB_THREE_VALUE = 16;
	public static final Comparator<Card> ORDER = Comparator.comparingInt(PresidentRules::value).thenComparing(Card::getSuit);

	private PresidentRules() {}

	public static boolean isClubThree(Card card) {
		return card.getSuit() == Suit.C && card.getFace() == 3;
	}

	public static int value(Card card) {
		if (isClubThree(card))
			return CLUB_THREE_VALUE;
		return faceValue(card.getFace());
	}

	private static int faceValue(int face) {
		switch (face) {
			case 1: return 14;
			case 2: return 15;
			default: return face;
		}
	}

	/**
	 * The value of 1-4 cards played together, or -1 if they can't be played together.
	 * All cards must share a face, except that sixes may stand in for any face.
	 * Sixes played on their own count as sixes. The three of clubs can't be part of a set.
	 */
	public static int setValue(List<Card> cards) {
		if (cards.isEmpty() || cards.size() > 4)
			return -1;
		int face = 6;
		for (Card card : cards) {
			if (isClubThree(card))
				return -1;
			if (card.getFace() == 6)
				continue;
			if (face != 6 && card.getFace() != face)
				return -1;
			face = card.getFace();
		}
		return faceValue(face);
	}

	public static String describe(Card card) {
		String face;
		switch (card.getFace()) {
			case 1: face = "A"; break;
			case 11: face = "J"; break;
			case 12: face = "Q"; break;
			case 13: face = "K"; break;
			default: face = String.valueOf(card.getFace());
		}
		switch (card.getSuit()) {
			case S: return face + "♠";
			case H: return face + "♥";
			case D: return face + "♦";
			default: return face + "♣";
		}
	}

	public static String describe(List<Card> cards) {
		return cards.stream().map(PresidentRules::describe).collect(Collectors.joining(" "));
	}
}
