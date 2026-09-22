package president;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

public class PlayerTest {

    private Player player;
    Game game;
    Player p1;
    Player p2;
    Player p3;
    CardHand cardhand;
    List<Card> playCards;
    Card s1;
    Card h1;
    Card d1;
    Card c2;
    Card c3;
    Card s6;
    
    @BeforeEach
	public void setUp() {
    	playCards= new ArrayList<Card>();
        s1= new Card('S',1);
        h1 =new Card('H',1);
        d1 =new Card('D',1);
        c2 =new Card('C',2);
        c3= new Card('C',3);
        s6= new Card('S',6);
        cardhand =  new CardHand();
		game = new Game(3);

		p1= game.getPlayers().get(0);
		p2= game.getPlayers().get(1);
		p3= game.getPlayers().get(2);
	}
    @Test
    @DisplayName("Test konstruktør")
    public void testConstructor(){
    	Player constructor = new Player(4, game);
    	Assertions.assertEquals(constructor.getName(), "player4");
    	Assertions.assertEquals(constructor.getGame(), game);
    }
    @Test
    @DisplayName("Test setPass")
    public void testSetPass(){
    	p1.setPass(true);
    	Assertions.assertEquals(p1.getPass(), true);;
    }
    @Test
    @DisplayName("Test play()")
    public void testPlay() {
    	cardhand.addCard(s1);
    	cardhand.addCard(c2);
    	cardhand.addCard(c3);
    	cardhand.addCard(d1);
    	cardhand.addCard(d1);
    	
    	p1.setCardHand(cardhand);
    	p2.setCardHand(cardhand);
    	//sjekker at man ikke kan passe på første tur.
    	Assertions.assertThrows(IllegalArgumentException.class,()-> p1.play(playCards));
    	
    	//sjekker at man ikke kan spille 2 forskjellige kort. 
    	playCards.add(s1);
    	playCards.add(c2);
    	Assertions.assertThrows(IllegalArgumentException.class,()-> p1.play(playCards));
    	//checks that you get another turn after playing c3.
    	playCards.clear();
    	playCards.add(c3);
    	p1.play(playCards);
    	Assertions.assertEquals(p1.getMyTurn(), true);
    	//check that you dont get another turn after playing any card.
    	playCards.clear();
    	playCards.add(d1);
    	p1.play(playCards);
    	Assertions.assertThrows(IllegalArgumentException.class,()-> p1.play(playCards));

    	//Checks that it is rightfully player 2's turn.
    	p2.play(playCards);
    	
    }

}