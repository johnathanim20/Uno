import java.util.ArrayList;

/*
  * The Card class contains the framework of how UNO cards are stored as String combinations and includes getters and setters to access attributes of a specific card
  */
public abstract class Card {
	String color;
	String value;
	
	public Card(String color, String value) {
		this.color = color;
		this.value = value;
	}
	
	
	public static String[] allColors = {"Red", "Blue", "Green", "Yellow", "Wild"};
	
	public static String[] getColors() {
		return allColors;
	}
	
	public static String[] allValues = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "Skip", "Reverse", "DrawTwo", "Wild", "WildDrawFour"};
	
	public static String[] getValues() {
		return allValues;
	}
	
	
	public String getColor() {
		return this.color;
	}
	
	public String getValue() {
		return this.value;
	}
	
	/*/
	 * Checks if the card being played is valid.
	 */
	
	public boolean newRules(ArrayList<Card> hand, Card topCard) {
		for (int i = 0; i < hand.size() - 1; i++) {
			for (int j = i + 1; j < hand.size(); j++) {
				if (hand.get(i).getValue() == "DrawTwo" || hand.get(j).getValue() == "DrawTwo" || hand.get(i).getValue() == "Wild" || hand.get(j).getValue() == "Wild" || hand.get(i).getValue() == "WildDrawFour" || hand.get(j).getValue() == "WildDrawFour" ||
						hand.get(i).getValue() == "Reverse" || hand.get(j).getValue() == "Reverse" || hand.get(i).getValue() == "Skip" || hand.get(j).getValue() == "Skip") {
					return false;
				}
				if (Integer.parseInt(topCard.getValue()) == ((Integer.parseInt(hand.get(i).getValue())) + Integer.parseInt(hand.get(j).getValue())) && (hand.get(i).getColor() == hand.get(j).getColor())) {
					return true;
				} else if (Integer.parseInt(topCard.getValue()) == (Math.abs((Integer.parseInt(hand.get(i).getValue())) - Integer.parseInt(hand.get(j).getValue()))) && (hand.get(i).getColor() == hand.get(j).getColor())) {
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean validMove(ArrayList<Card> hand, Card topCard, GameState gamestate) {
		if (this.color == topCard.color || this.value == topCard.value || this.color == "Wild" || this.color == "WildDrawFour" || this.color == gamestate.getCurrentColor()) {
			return true;
		} else {
		//addition and subtraction rules are now valid plays.
			return newRules(hand, topCard);
		}
	}
	

	
	/* 
	 * Takes a card object and converts it into a string representation.
	 */
	@Override
	public String toString() {
		return String.valueOf(this.color) + " - "+ String.valueOf(this.value);
	}

}
