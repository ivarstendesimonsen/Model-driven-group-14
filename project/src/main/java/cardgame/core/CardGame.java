package cardgame.core;

import java.util.List;

/**
 * The rules of a specific card game, played on a {@link Table}.
 * The UI only talks to this interface, so different games can be plugged in.
 */
public interface CardGame {
	Table getTable();

	boolean canPlay(Player player, List<Card> cards);
	void play(Player player, List<Card> cards);

	boolean canPass(Player player);
	void pass(Player player);

	void removePlayer(Player player);

	/** The cards on top of the pile, i.e. the most recent play. */
	List<Card> getLastPlay();

	/** Label for the play button, e.g. "Play" or "Give". */
	String getActionName(Player player);

	/** Message shown to the given player about what is going on. */
	String getStatus(Player player);

	/** Short line describing the given player in the player list. */
	String describe(Player player);
}
