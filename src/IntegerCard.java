public class IntegerCard extends Card {

	public IntegerCard(String color, String value) {
		super(color, value);
	}
	
	/*
	 * Updates gamestate instance variables
	 */
	public void playCard(GameState gamestate, Card card) {
		if (validMove(null, card, gamestate)) {
			gamestate.addToDiscardPile(card); //move to discard pile
			gamestate.players[gamestate.currentPlayerIndex].removeFromHand(card); //remove played card from hand
			gamestate.setCurrentColor(card.getColor()); //sets currentcolor
			gamestate.drawStackedCards = false;
			gamestate.numStackedCards = 0;
			//gamestate.setNextPlayer(); - removed line because of addition/subtraction rule for assignment 1-1.
		}
	}
}
