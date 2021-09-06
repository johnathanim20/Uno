public class IntegerCard extends Card {

	public IntegerCard(String color, String value) {
		super(color, value);
	}

	public void playCard(GameState gamestate, Card card) {
		if (validMove(card, gamestate)) {
			gamestate.addToDiscardPile(card); //move to discard pile
			gamestate.players[gamestate.currentPlayerIndex].removeFromHand(card); //remove played card from hand
			gamestate.setCurrentColor(card.getColor().toString()); //sets currentcolor
			gamestate.drawStackedCards = false;
			gamestate.setNextPlayer();
		}
	}
}
