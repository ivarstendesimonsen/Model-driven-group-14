package president;



import java.util.List;
import java.util.ArrayList;
import java.util.Collections;




public class CardDeck {
	private List<Card> currentDeck = new ArrayList<Card>();
	public CardDeck() {
		
	}
	//vorfor kan man velge hvor mange kort i hver farge, og parameteren bør hete noe annet enn n.
	public CardDeck(int n) { 
		if (n>13)
			throw new IllegalArgumentException("Kan ikke ha mer enn 13 kort i hver farge.");
		final char[] suits = {'S','H','D','C'};
		for (char x : suits) {
			for (int i = 1; i <= n; ++i) {
				Card card = new Card(x,i);
				this.currentDeck.add(card);
			};
		};
	};
	public List<Card> getCurrentDeck(){
		return this.currentDeck;
	};
	public int getCardCount(){
		return this.currentDeck.size();
	};
	public Card getCard(int n) {
		if (n<0 || n>getCardCount()) 
			throw new IllegalArgumentException("Velg et kortnummer mellom 0 og "+getCardCount());
		return this.currentDeck.get(n);
	};
	public int getCardPos(Card card) {
		return this.currentDeck.indexOf(card);
	};
	
	//Riffle shuffles a random amount of times.
	public void shufflePerfectly() {
		Collections.shuffle(currentDeck);
		//Pr�vde � lage en shufflefunksjon, men inns� det var like greit � bruke collections.shuffle.
//		for (int r= 0;r<=(random.nextInt(9)+random.nextInt(random.nextInt(10)+1));++r) {
//			int halfDeck = getCardCount()/2;
//			for (int x = 0; x<halfDeck; ++x) {
//			
//				this.currentDeck.add(getCard(x));
//				this.currentDeck.add(getCard(halfDeck+x));
//			}
//			for (int x = 0;x<halfDeck*2;++x)
//				this.currentDeck.remove(0);
//		}
	};
	public void deal(CardHand CardHand) {
		
			CardHand.addCard(getCard(getCardCount()-1));
			this.currentDeck.remove(getCardCount()-1);
	};
//	public void deal(ArrayList<CardHand> cardhands) {
//		while (getCardCount()!=0) {
//			for (CardHand cardhand:cardhands) {
//				cardhand.addCard(getCard(getCardCount()-1));
//				this.currentDeck.remove(getCardCount()-1);
//			}
//		}
//	}
	public static void main(String[] args) {
		CardDeck cardDeck = new CardDeck(13);
		cardDeck.shufflePerfectly();
		Collections.sort(cardDeck.currentDeck); // ???????
		for (int x = 0; x<52; ++x){
			System.out.println(cardDeck.getCard(x));
		};
		
	};
};