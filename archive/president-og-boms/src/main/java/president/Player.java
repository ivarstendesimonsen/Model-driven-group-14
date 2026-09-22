package president;

import java.util.ArrayList;
import java.util.List;

public class Player {
	private CardHand cardhand;
	private List<Card> markedCards = new ArrayList<Card>();
	private String name;
	private char rolle;
	private boolean myturn;
	private Game game;
	private boolean pass;
	public Player(int number) {
		this.name = "player"+number;
	}
	public Player ( int number, Game game){
		this.name = "player"+number;
		this.game = game;
	}
	public Game getGame() {
		return this.game;
	}
	public void setGame(Game game) {
		this.game=game;
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
	public char getRole() {
		return this.rolle;
	}
	public void setterPass(boolean pass) {
		this.pass=pass;
	}
	public void setPass(boolean pass) {
		if (pass==false) {
			this.pass=pass;
		}
		else {
			this.pass=pass;
			int tempPassCount=0;
			for (Player player:this.game.getPlayers()) {
				if(player!=game.getLastPlayer()&&(player.getPass() || player.getCardHand().getCardCount()==0)) {
					tempPassCount++;
				}
			}
			System.out.println("tempPasscount= "+tempPassCount);
			if (this.game.getPlayers().size()-1-game.getRankList().size()<=tempPassCount) {
				tempPassCount=0;
				game.endRally(this);
			}
		}
	}
	public boolean getPass() {
		return this.pass;
	}
	public void setTurn(boolean myturn) {
		this.myturn=myturn;
	}
	public void setMyTurn(boolean myturn) {
		if (myturn==false)
			this.myturn = myturn;
		else if (getCardHand().getCardCount()==0 || pass) {
			if (game.getPlayers().size()!=1)
				game.endTurn(this);
			else
				this.myturn=myturn;
		}
		else
			this.myturn = myturn;
		
	}
	public boolean getMyTurn() {
		return this.myturn;
	}
	public void markCard(Card card){
		if (getCardHand().getCurrentHand().contains(card)) {
			if(card.getFace()!=6)
				markedCards.add(card);
			else
				markedCards.add(0,card);
		}
		else
			throw new IllegalArgumentException("Cant mark cards not in your hand");
		
	}
	public void unmarkCards(Card card) {
		if(markedCards.contains(card))
			markedCards.remove(card);
		else
			throw new IllegalArgumentException("Cant unmark unmarked card");
	}
	public List<Card> getMarkedCards(){
		return this.markedCards;
	}

	public void play(List<Card> markedCards) {
		for(Card card:markedCards) {
			if (!this.getCardHand().getCurrentHand().contains(card)) {
				throw new IllegalArgumentException("Can't play card not in hand!"); 
			}
		}
		if (this.myturn) {
			if(game.getRoundType()==0)	
				game.setRoundType(markedCards.size());
			if (markedCards.size()==0) {
				if(game.getRoundType()==0)
					if(this.getCardHand().getCardCount()==1 && (this.getCardHand().getCard(0).getFace()==3 && this.getCardHand().getCard(0).getSuit()=='C')) {
						this.setPass(true);
						game.endTurn(this);
					}
					else
						throw new IllegalArgumentException("Can't pass on the first turn");
				this.setPass(true);
				game.endTurn(this);
				
			}
			else if(markedCards.size()==1 && markedCards.get(0).getFace()==3 && markedCards.get(0).getSuit()=='C' && this.cardhand.getCardCount()!=1) {

				this.game.setLastPlayer(this);
				this.game.getPlayedCards().add(this.cardhand.play(markedCards.get(0)));
				game.endRally(this);				
			}
			else if (this.game.validateMarkedCards(markedCards,this)) {
				this.game.setLastPlayer(this);
				for (Card card:markedCards) {
					this.game.getPlayedCards().add(this.cardhand.play(card));
				}
				if(getCardHand().getCardCount()==0) {
					System.out.println("Du er tom for kort");
					this.game.getRankList().add(this);
					this.game.endTurn(this);
				}
				if(markedCards.size()==4) {
					//Only calls endrally if the 4 equals are organic. "No 6's". Calls endRally if 4 6's are played at the same time.
					boolean hasSix =false;
					int allSix = 0;
					for (Card card:markedCards) {
						if (card.getFace()==6) {
							hasSix=true;
							allSix++;
						}
					}
					if (!hasSix || allSix==4)
						game.endRally(this);
					else 
						game.endTurn(this);
				}
				else if(game.getPlayedCards().size()>3) {
					//Checks if the last 4 cards have the same face. If that is the case, the playedCards are wiped out.
					int sameCard = 0;
					int sameCardNumber=0;
					List<Card> tempCards =new ArrayList<Card>();
					for(Card card:game.getPlayedCards()) {
						if (card.getFace()!=6)
							tempCards.add(card);
					}
					for (int i=1; i<=4; i++) {
						if (!(tempCards.size()>3))
							game.endTurn(this);
						else {
							if (i==1) {
								sameCard=tempCards.get(tempCards.size()-i).getFace();
							}
							if(tempCards.get(tempCards.size()-i).getFace()==sameCard ) {
								sameCardNumber++;
							}
						}
					}
					if (sameCardNumber>=4)
						game.endRally(this);
					else
						game.endTurn(this);
				}
				else
					game.endTurn(this);
				}
			
				else
					throw new IllegalArgumentException("Unplayable card/card-combination");
			}
			else
				throw new IllegalArgumentException("Wait your turn");
			
	}
}
