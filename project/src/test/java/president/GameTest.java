package president;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class GameTest {
	private Game game;
	private List<Card> markedCards;
	
	@BeforeEach
	public void setup() {
		game = new Game(3);
		markedCards = new ArrayList<Card>();

	}
	@Test
	@DisplayName("Test konstruktøren")
	public void testConstructor() {
	
	}
	@Test
	@DisplayName("Sjekk at antall spillere matcher Gameobjektets parameter.")
	public void checkPlayerCount() {
		Assertions.assertEquals(game.getPlayers().size(), 3);
	}
	@Test
	@DisplayName("Sjekk valideringsmetodene for markerte kort.")
	public void checkValidateMarkedCards() {

		Assertions.assertFalse(game.validateMarkedCards(markedCards,game.getPlayers().get(0)), "Kortlisten skal være ugyldig om den er tom.");
		
		markedCards.add(null);
		Assertions.assertThrows(IllegalArgumentException.class, ()->{
			game.validateMarkedCards(markedCards,game.getPlayers().get(0));
		});
		markedCards.clear();
	}

}
