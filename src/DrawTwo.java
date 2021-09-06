public class DrawTwo extends Card {
	
	public DrawTwo(String color, String value) {
		super(color, value);
	}

	public void playCard(GameState gamestate, Card card) {
		if (validMove(card, gamestate)) {
			gamestate.addToDiscardPile(card); //move to discard pile
			gamestate.players[gamestate.currentPlayerIndex].removeFromHand(card); //remove played card from hand
			gamestate.setCurrentColor(card.getColor().toString()); //sets currentcolor
			gamestate.numStackedCards += 2;	
			gamestate.drawStackedCards = true;
			gamestate.setNextPlayer();
			gamestate.setNextPlayer();
		}
	}
	
}
