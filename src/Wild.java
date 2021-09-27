

public class Wild extends Card {

	public Wild(String color, String value) {
		super(color, value);
	}
	
	/*
	 * Reads user input and sets current color - updates gamestate instance variables
	 */
	public void playCard(GameState gamestate) {
		
		int index = 0;
		for (int i = 0; i < gamestate.getPlayer().handSize(); i++) {
			if (gamestate.getPlayer().hand.get(i).toString().equals(this.toString())) {
				index = i;
			}				
		}
		gamestate.addToDiscardPile(this); //move to discard pile
		gamestate.players[gamestate.currentPlayerIndex].removeFromHand(index); //remove played card from hand
	    gamestate.drawStackedCards = false;
		//gamestate.numStackedCards = 0;
		gamestate.setNextPlayer();
	}
}