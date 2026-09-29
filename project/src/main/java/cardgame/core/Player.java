package cardgame.core;


public class Player {
	private CardHand cardhand;
	private String name;
	public Player(int number) {
		this.name = "player"+number;
	}
	public void setCardHand(CardHand cardhand) {
		this.cardhand=cardhand;
	}
	public CardHand getCardHand() {
		return this.cardhand;
	}
	public String getName(){
		return this.name;
	}
}
