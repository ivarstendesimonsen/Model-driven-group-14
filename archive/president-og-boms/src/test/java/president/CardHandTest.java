package president;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;



import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CardHandTest {
	CardHand hand;
	Card s1;
	Card c2;


	@BeforeEach
	public void setUp() {
		hand = new CardHand();
		s1 = new Card('S', 1);
		c2 = new Card('C', 2);
		
		
	}
	@Test
	@DisplayName("Testplaycard")
	public void testPlayCard() {
		hand.addCard(s1);
		Assertions.assertEquals(hand.play(s1),s1);
		Assertions.assertThrows(IllegalArgumentException.class,()->hand.play(c2));
	}
}