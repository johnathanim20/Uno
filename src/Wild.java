import java.util.Scanner;

public class Wild extends Card {

	public Wild(String color, String value) {
		super(color, value);
	}
	
	public void playCard(GameState gamestate, Card card) {
		System.out.println("\n" + "Enter Desired Color");
		String color;
		Scanner myColor = new Scanner(System.in);
		if (myColor.hasNextLine()) {
			color = myColor.nextLine();
			System.out.println("Color is : " + color);
			gamestate.setCurrentColor(color);
		} else {
			myColor.close();
		}
		gamestate.addToDiscardPile(card); //move to discard pile
		gamestate.players[gamestate.currentPlayerIndex].removeFromHand(card); //remove played card from hand
		gamestate.drawStackedCards = false;
		gamestate.setNextPlayer();
	}
}