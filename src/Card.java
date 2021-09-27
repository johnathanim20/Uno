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
	
	/*
	 * New method that checks for addition/subtraction rules required in assignment 1.1
	 */

	
	public boolean checkAddRule(ArrayList<Card> hand) {
		for (int i = 0; i < hand.size() - 1; i++) {
			for (int j = i + 1; j < hand.size(); j++) {
				try {
					if (Integer.parseInt(this.getValue()) == ((Integer.parseInt(hand.get(i).getValue())) + Integer.parseInt(hand.get(j).getValue())) && (hand.get(i).getColor().equals(hand.get(j).getColor()))) {
						return true;
					}
				}
				catch (NumberFormatException e) {
						return false;
				}
			}
		}
		return false;
	}
	
	
	public boolean checkSubRule(ArrayList<Card> hand) {
		for (int i = 0; i < hand.size() - 1; i++) {
			for (int j = i + 1; j < hand.size(); j++) {
				try {
					if (Integer.parseInt(this.getValue()) == ((Integer.parseInt(hand.get(i).getValue())) + Integer.parseInt(hand.get(j).getValue())) && (hand.get(i).getColor().equals(hand.get(j).getColor()))) {
						return true;
					}
				}
				catch (NumberFormatException e) {
					return false;
				}
			}
		}
		return false;
	}
	
	/*
	 * Checks if the card being played is valid.
	 */
	public boolean validMove(ArrayList<Card> hand, GameState gamestate) {
		if (this.color.equals(gamestate.getTopCardDiscardPile().getColor()) || this.value.equals(gamestate.getTopCardDiscardPile().getValue()) || this.color.equals("Wild") || this.color.equals("WildDrawFour") || this.color.equals(gamestate.getCurrentColor())) {
			return true;
		} else {
		//addition and subtraction rules are now valid plays.
			return checkAddRule(hand) || checkSubRule(hand);
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
