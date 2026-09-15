package president;

import java.io.FileNotFoundException;
import java.util.List;

public interface FileManager {
	public void writeSaveStateToFile(String filename) throws FileNotFoundException;
	public int getSaveStateFromFile(String filename,List<Player> players,List<Player> rankList, List<Card> playedCards, CardDeck carddeck, int roundType, Player lastPlayeraa) throws FileNotFoundException;
}
