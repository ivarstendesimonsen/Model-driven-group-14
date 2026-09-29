package cardgame.president;

public enum Role {
	PRESIDENT("President"),
	VICE_PRESIDENT("Vice-President"),
	NEUTRAL("Neutral"),
	VICE_BOMS("Vice-Boms"),
	BOMS("Boms");

	private final String displayName;

	Role(String displayName) {
		this.displayName = displayName;
	}

	/** The role earned by finishing at the given place (0 = first out) among the given number of players. */
	public static Role of(int place, int players) {
		if (place == 0)
			return PRESIDENT;
		if (place == players - 1)
			return BOMS;
		if (players >= 4 && place == 1)
			return VICE_PRESIDENT;
		if (players >= 4 && place == players - 2)
			return VICE_BOMS;
		return NEUTRAL;
	}

	@Override
	public String toString() {
		return displayName;
	}
}
