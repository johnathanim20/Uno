public class Reverse extends Card {

	public Reverse(String color, String value) {
		super(color, value);
	}
	
	/*
	 * Updates gamestate instance variables
	 */
	public void playCard(GameState gamestate, Card card) {
		if (validMove(null, card, gamestate)) {
			gamestate.drawStackedCards = false;
			gamestate.addToDiscardPile(card); //move to discard pile
			gamestate.players[gamestate.currentPlayerIndex].removeFromHand(card); //remove played card from hand
			gamestate.setCurrentColor(card.getColor().toString()); //sets current color
			gamestate.clockwise = !(gamestate.clockwise);
			gamestate.drawStackedCards = false;
			gamestate.numStackedCards = 0;
			gamestate.setNextPlayer();
		}
	}
}
