public class DrawTwo extends Card {
	
	public DrawTwo(String color, String value) {
		super(color, value);
	}

	/*
	 * Updates gamestate instance variables
	 */
	public void playCard(GameState gamestate, Card card) {
		if (validMove(card, gamestate)) {
			gamestate.addToDiscardPile(card);
			gamestate.players[gamestate.currentPlayerIndex].removeFromHand(card); 
			gamestate.setCurrentColor(card.getColor().toString()); 
			gamestate.numStackedCards += 2;	
			gamestate.drawStackedCards = true;
			gamestate.setNextPlayer();
			gamestate.setNextPlayer();
		}
	}
	
}
