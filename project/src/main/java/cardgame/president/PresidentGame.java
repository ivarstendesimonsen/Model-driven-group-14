package cardgame.president;

import static cardgame.president.PresidentRules.CLUB_THREE_VALUE;
import static cardgame.president.PresidentRules.isClubThree;
import static cardgame.president.PresidentRules.setValue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import cardgame.core.Card;
import cardgame.core.CardGame;
import cardgame.core.Player;
import cardgame.core.Table;

public class PresidentGame implements CardGame {

	public enum Phase { EXCHANGE, PLAYING, GAME_OVER }

	/** Cards a winner still has to give back to the loser who gave them their best cards. */
	private record Exchange(Player receiver, int count) {}

	private final Table table;
	private Phase phase;
	private int round = 1;
	private Player turn;

	// The pile of the current trick
	private Player lastPlayer;
	private List<Card> lastPlay = new ArrayList<Card>();
	private int pileValue;
	private int pileValueCount;
	private final Set<Player> passed = new HashSet<Player>();

	private final List<Player> finished = new ArrayList<Player>();
	private List<Player> ranking = new ArrayList<Player>();
	private final Map<Player, Role> roles = new HashMap<Player, Role>();
	private final Map<Player, Exchange> exchanges = new LinkedHashMap<Player, Exchange>();
	private final Map<Player, String> exchangeNotes = new HashMap<Player, String>();

	private String log;
	private String lastRoundSummary = "";

	public PresidentGame(int players) {
		if (players < 2)
			throw new IllegalArgumentException("President needs at least two players.");
		this.table = new Table(players);
		sortHands();
		this.phase = Phase.PLAYING;
		this.turn = table.getPlayers().get(0);
		this.log = "Round 1: " + turn.getName() + " starts.";
	}

	@Override
	public Table getTable() {
		return this.table;
	}
	public Phase getPhase() {
		return this.phase;
	}
	public int getRound() {
		return this.round;
	}
	public Player getTurn() {
		return this.turn;
	}
	public Role getRole(Player player) {
		return this.roles.get(player);
	}
	@Override
	public List<Card> getLastPlay() {
		return Collections.unmodifiableList(this.lastPlay);
	}

	@Override
	public boolean canPlay(Player player, List<Card> cards) {
		if (cards.isEmpty() || new HashSet<Card>(cards).size() != cards.size()
				|| !player.getCardHand().getCurrentHand().containsAll(cards))
			return false;
		switch (phase) {
			case EXCHANGE:
				Exchange exchange = exchanges.get(player);
				return exchange != null && cards.size() == exchange.count();
			case PLAYING:
				return player == turn && beatsPile(player, cards);
			default:
				return false;
		}
	}

	private boolean beatsPile(Player player, List<Card> cards) {
		// The three of clubs clears any pile, but can't be led or be your last card.
		if (cards.size() == 1 && isClubThree(cards.get(0)))
			return !lastPlay.isEmpty() && player.getCardHand().getCardCount() > 1;
		int value = setValue(cards);
		if (value < 0)
			return false;
		return lastPlay.isEmpty() || (cards.size() == lastPlay.size() && value >= pileValue);
	}

	@Override
	public void play(Player player, List<Card> cards) {
		if (!canPlay(player, cards))
			throw new IllegalArgumentException("Those cards can't be played now.");
		List<Card> play = new ArrayList<Card>(cards);

		if (phase == Phase.EXCHANGE) {
			Exchange exchange = exchanges.remove(player);
			give(player, exchange.receiver(), play);
			exchangeNotes.merge(player, "You gave " + PresidentRules.describe(play) + " to " + exchange.receiver().getName() + ".", (a, b) -> a + " " + b);
			exchangeNotes.merge(exchange.receiver(), "You got " + PresidentRules.describe(play) + " from " + player.getName() + ".", (a, b) -> a + " " + b);
			if (exchanges.isEmpty())
				beginPlaying();
			table.fireStateChanged();
			return;
		}

		table.play(player, play);
		int value = isClubThree(play.get(0)) ? CLUB_THREE_VALUE : setValue(play);
		pileValueCount = (value == pileValue ? pileValueCount : 0) + play.size();
		pileValue = value;
		lastPlay = play;
		lastPlayer = player;
		log = player.getName() + " played " + PresidentRules.describe(play) + ".";
		if (!hasCards(player)) {
			finished.add(player);
			log += " " + player.getName() + " is out!";
		}

		if (activePlayers().size() <= 1)
			endRound();
		else if (value == CLUB_THREE_VALUE || pileValueCount >= 4)
			endTrick();
		else
			nextTurn(player);
		table.fireStateChanged();
	}

	@Override
	public boolean canPass(Player player) {
		if (phase != Phase.PLAYING || player != turn)
			return false;
		// You may only pass on an empty pile if all you have left is the three of clubs.
		return !lastPlay.isEmpty() || player.getCardHand().getCurrentHand().stream().allMatch(PresidentRules::isClubThree);
	}

	@Override
	public void pass(Player player) {
		if (!canPass(player))
			throw new IllegalStateException("You can't pass now.");
		passed.add(player);
		log = player.getName() + " passed.";
		nextTurn(player);
		table.fireStateChanged();
	}

	@Override
	public void removePlayer(Player player) {
		Player successor = turn == player ? next(player, p -> p != player && hasCards(p)) : turn;
		boolean wasOnPile = player == turn || player == lastPlayer;
		exchanges.remove(player);
		exchanges.values().removeIf(exchange -> exchange.receiver() == player);
		finished.remove(player);
		ranking.remove(player);
		passed.remove(player);
		roles.remove(player);
		table.removePlayer(player);
		log = player.getName() + " left the game.";

		if (table.getPlayers().size() < 2) {
			phase = Phase.GAME_OVER;
			turn = null;
		}
		else if (phase == Phase.EXCHANGE && exchanges.isEmpty())
			beginPlaying();
		else if (phase == Phase.PLAYING) {
			if (activePlayers().size() <= 1)
				endRound();
			else if (wasOnPile)
				clearPile(successor);
		}
		table.fireStateChanged();
	}

	@Override
	public String getActionName(Player player) {
		return phase == Phase.EXCHANGE && exchanges.containsKey(player) ? "Give" : "Play";
	}

	@Override
	public String getStatus(Player player) {
		List<String> lines = new ArrayList<String>();
		Role role = roles.get(player);
		lines.add("Round " + round + (role != null ? " - you are " + role : ""));
		lines.add(log);
		switch (phase) {
			case GAME_OVER:
				lines.add("Game over: not enough players left.");
				break;
			case EXCHANGE:
				Exchange exchange = exchanges.get(player);
				if (exchange != null)
					lines.add("Pick " + cardCount(exchange.count()) + " to give to " + exchange.receiver().getName() + ".");
				else
					lines.add("Waiting for the card exchange.");
				break;
			case PLAYING:
				if (!hasCards(player))
					lines.add("You are out as number " + (finished.indexOf(player) + 1) + ".");
				else if (player != turn)
					lines.add("Waiting for " + turn.getName() + ".");
				else if (lastPlay.isEmpty())
					lines.add(canPass(player) ? "Your lead, but the 3♣ can't be led, so you have to pass."
							: "Your lead: play a single, pair, triple or four of a kind.");
				else
					lines.add("Your turn: beat " + PresidentRules.describe(lastPlay) + " with " + cardCount(lastPlay.size()) + ", or pass.");
				break;
		}
		if (exchangeNotes.containsKey(player))
			lines.add(exchangeNotes.get(player));
		if (!lastRoundSummary.isEmpty())
			lines.add("\n" + lastRoundSummary);
		return String.join("\n", lines);
	}

	@Override
	public String describe(Player player) {
		String line = (phase == Phase.PLAYING && player == turn ? "▶ " : "") + player.getName()
				+ " (" + player.getCardHand().getCardCount() + ")";
		if (roles.containsKey(player))
			line += " " + roles.get(player);
		if (finished.contains(player))
			line += " - out #" + (finished.indexOf(player) + 1);
		else if (passed.contains(player))
			line += " - pass";
		return line;
	}

	private void nextTurn(Player from) {
		Player next = next(from, p -> p != from && hasCards(p) && !passed.contains(p));
		// Back at the one who played last (or nobody left who hasn't passed): the pile goes out.
		if (next == null || next == lastPlayer)
			endTrick();
		else
			turn = next;
	}

	/** The one who played last leads the next trick, or the next player after them if they are out. */
	private void endTrick() {
		if (lastPlayer != null && hasCards(lastPlayer))
			clearPile(lastPlayer);
		else
			clearPile(next(lastPlayer != null ? lastPlayer : turn, this::hasCards));
	}

	private void clearPile(Player leader) {
		table.clearPlayedCards();
		lastPlay = new ArrayList<Card>();
		lastPlayer = null;
		pileValue = 0;
		pileValueCount = 0;
		passed.clear();
		turn = leader;
		log += " The pile is cleared, " + leader.getName() + " leads.";
	}

	private void endRound() {
		for (Player player : table.getPlayers()) {
			if (!finished.contains(player))
				finished.add(player);
		}
		ranking = new ArrayList<Player>(finished);
		lastRoundSummary = "Result of round " + round + ":\n" + ranking.stream()
				.map(p -> (ranking.indexOf(p) + 1) + ". " + p.getName() + " - " + Role.of(ranking.indexOf(p), ranking.size()))
				.collect(Collectors.joining("\n"));
		startNextRound();
	}

	private void startNextRound() {
		round++;
		table.collectCards();
		table.shuffleAndDeal();
		lastPlay = new ArrayList<Card>();
		lastPlayer = null;
		pileValue = 0;
		pileValueCount = 0;
		passed.clear();
		finished.clear();
		roles.clear();
		exchanges.clear();
		exchangeNotes.clear();

		int n = ranking.size();
		for (int i = 0; i < n; i++)
			roles.put(ranking.get(i), Role.of(i, n));
		setUpExchange(ranking.get(n - 1), ranking.get(0), 2);
		if (n >= 4)
			setUpExchange(ranking.get(n - 2), ranking.get(1), 1);
		sortHands();
		phase = Phase.EXCHANGE;
		turn = null;
		log = "Round " + round + " is dealt. Time to exchange cards.";
	}

	/** The loser gives their best cards to the winner right away, the winner then has to give some back. */
	private void setUpExchange(Player loser, Player winner, int count) {
		List<Card> hand = loser.getCardHand().getCurrentHand();
		hand.sort(PresidentRules.ORDER);
		List<Card> best = new ArrayList<Card>(hand.subList(hand.size() - count, hand.size()));
		give(loser, winner, best);
		exchanges.put(winner, new Exchange(loser, count));
		exchangeNotes.put(loser, "Your best " + (count == 1 ? "card " : "cards ") + PresidentRules.describe(best) + " went to " + winner.getName() + ".");
		exchangeNotes.put(winner, "You got " + PresidentRules.describe(best) + " from " + loser.getName() + ".");
	}

	private void beginPlaying() {
		phase = Phase.PLAYING;
		sortHands();
		Player boms = ranking.isEmpty() ? table.getPlayers().get(0) : ranking.get(ranking.size() - 1);
		turn = boms;
		log = "The exchange is done. " + boms.getName() + " starts.";
	}

	private void give(Player from, Player to, List<Card> cards) {
		for (Card card : cards)
			to.getCardHand().addCard(from.getCardHand().play(card));
		to.getCardHand().getCurrentHand().sort(PresidentRules.ORDER);
	}

	private void sortHands() {
		for (Player player : table.getPlayers())
			player.getCardHand().getCurrentHand().sort(PresidentRules.ORDER);
	}

	private boolean hasCards(Player player) {
		return player.getCardHand().getCardCount() > 0;
	}

	private List<Player> activePlayers() {
		return table.getPlayers().stream().filter(this::hasCards).collect(Collectors.toList());
	}

	/** The first player after the given one, going round the table, that is eligible. */
	private Player next(Player from, Predicate<Player> eligible) {
		List<Player> players = table.getPlayers();
		int start = players.indexOf(from);
		for (int i = 1; i <= players.size(); i++) {
			Player candidate = players.get(Math.floorMod(start + i, players.size()));
			if (eligible.test(candidate))
				return candidate;
		}
		return null;
	}

	private static String cardCount(int count) {
		return count + (count == 1 ? " card" : " cards");
	}
}
