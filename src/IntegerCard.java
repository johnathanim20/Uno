public class IntegerCard extends Card {

	public IntegerCard(String color, String value) {
		super(color, value);
	}
	
	/*
	 * Updates gamestate instance variables
	 */
	public void playCard(GameState gamestate) {
		int index = 0;
		if (validMove(null, gamestate)) {
			for (int i = 0; i < gamestate.getPlayer().handSize(); i++) {
				if (gamestate.getPlayer().hand.get(i).toString().equals(this.toString())) {
					index = i;
				}
			}
			gamestate.addToDiscardPile(this); //move to discard pile
			gamestate.players[gamestate.currentPlayerIndex].removeFromHand(index); //remove played card from hand
			gamestate.setCurrentColor(this.getColor()); //sets currentcolor
			gamestate.drawStackedCards = false;
			//gamestate.numStackedCards = 0;
			//gamestate.setNextPlayer(); 
		}
	}
}
