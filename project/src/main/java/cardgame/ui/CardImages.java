package cardgame.ui;

import cardgame.core.Card;
import javafx.scene.image.Image;

public final class CardImages {

	private CardImages() {}

	public static Image of(Card card) {
		return new Image(CardImages.class.getResourceAsStream("/cards/" + card.getSuit() + card.getFace() + ".png"));
	}
}
