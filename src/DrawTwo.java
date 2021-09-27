public class DrawTwo extends Card {
	
	public DrawTwo(String color, String value) {
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
			gamestate.addToDiscardPile(this);
			gamestate.players[gamestate.currentPlayerIndex].removeFromHand(index); 
			gamestate.setCurrentColor(this.getColor().toString()); 
			gamestate.numStackedCards += 2;	
			gamestate.drawStackedCards = true;
			gamestate.setNextPlayer();
		}
	}
}
