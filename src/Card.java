public class Card {
	
	enum allColors {
		Red, Blue, Green, Yellow, Wild;
		private static allColors[] colors = allColors.values();
		public static allColors getColor(int value) {
			return allColors.colors[value];
		}
	}
	
	enum allValues {
		Zero, One, Two, Three, Four, Five, Six, Seven, Eight, Nine, Skip, Reverse, DrawTwo, Wild, WildDrawFour;
		private static allValues[] values = allValues.values();
		public static allValues getValue(int value) {
			return allValues.values[value];
		}
	}
	
	private allColors color;
	private allValues value;
	
	//Custom Constructor for all cards
	
	public Card(allColors color, allValues value) {
		this.color = color;
		this.value = value;
	}

	
	public static allColors getColor(Card card) {
		return card.color;
	}
	
	public static allValues getValue(Card card) {
		return card.value;
	}
	
	@Override
	/** Takes a card object and converts into a string representation.
	*/
	public String toString() {
		return String.valueOf(this.color) + " - "+ String.valueOf(this.value);
	}
	
	//Checks if the card being played is valid.
	public static boolean validMove(Card currentCard, Card topCard) {
		if (currentCard.color == topCard.color || currentCard.value == topCard.value || currentCard.color.toString() == "Wild" || currentCard.color.toString() == "WildDrawFour") {
			return true;
		} else {
			return false;
		}
	}
	
	
	
	

}
