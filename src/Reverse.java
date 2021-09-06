public class Reverse extends Card {

	public Reverse(String color, String value) {
		super(color, value);
	}
	
	public void playCard(GameState gamestate, Card card) {
		if (validMove(card, gamestate)) {
			gamestate.drawStackedCards = false;
			gamestate.addToDiscardPile(card); //move to discard pile
			gamestate.players[gamestate.currentPlayerIndex].removeFromHand(card); //remove played card from hand
			gamestate.setCurrentColor(card.getColor().toString()); //sets current color
			gamestate.clockwise = !(gamestate.clockwise);
			gamestate.drawStackedCards = false;
			gamestate.setNextPlayer();
		}
	}
	
	
}
