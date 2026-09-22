package cardgame.core;


public enum Suit {
	SPADES('S'),
	HEARTS('H'),
	DIAMONDS('D'),
	CLUBS('C');

	private final char symbol;

	Suit(char symbol) {
		this.symbol = symbol;
	}

	public char getSymbol() {
		return symbol;
	}

	public boolean isRed() {
		return this == HEARTS || this == DIAMONDS;
	}

	public static Suit fromSymbol(char symbol) {
		for (Suit suit : values()) {
			if (suit.symbol == symbol)
				return suit;
		}
		throw new IllegalArgumentException("Unknown suit symbol");
	}
}
