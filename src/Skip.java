public class Skip extends Card {

	public Skip(String color, String value) {
		super(color, value);
	}

	/*
	 * Updates gamestate instance variables
	 */
	public void playCard(GameState gamestate) {
		if (validMove(null, gamestate)) {
			int index = 0;
			
			for (int i = 0; i < gamestate.getPlayer().handSize(); i++) {
				if (gamestate.getPlayer().hand.get(i).toString().equals(this.toString())) {
					index = i;
				}				
			}
			gamestate.drawStackedCards = false;
			gamestate.addToDiscardPile(this); //move to discard pile
			gamestate.players[gamestate.currentPlayerIndex].removeFromHand(index); //remove played card from hand
			gamestate.setCurrentColor(this.getColor().toString()); //sets currentcolor
			//gamestate.numStackedCards = 0;
			gamestate.setNextPlayer();
			gamestate.setNextPlayer();
		}
	}
}
