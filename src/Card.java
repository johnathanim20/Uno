 /*
  * The Card class contains the framework of how UNO cards are stored as String combinations and includes getters and setters to access attributes of a specific card
  */
public abstract class Card {
	
	protected GameState gamestate;
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
	
	public static String[] allValues = {"Zero", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Skip", "Reverse", "DrawTwo", "Wild", "WildDrawFour"};
	
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
	
	public boolean validMove(Card topCard, GameState gamestate) {
		if (this.color == topCard.color || this.value == topCard.value || this.color.toString() == "Wild" || this.color.toString() == "WildDrawFour" || this.color.toString() == gamestate.getCurrentColor()) {
			return true;
		} else {
			return false;
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
