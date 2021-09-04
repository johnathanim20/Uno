import java.util.ArrayList;

public class Deck {
	private static ArrayList<Card> initialDeck = new ArrayList<Card> ();
	private static ArrayList<Card> discardPile = new ArrayList<Card> ();
	private static ArrayList<Card> playingDeck = new ArrayList<Card> ();
	private static int playDeckIndex = 107;
	

	public Deck() {
			
	}
	
	public static int getPlayDeckIndex() {
		return playDeckIndex;
	}
	public static ArrayList<Card> getInitialDeck() {
		return initialDeck;
	}
	
	public static Card getInitialDeckCard(int i) {
		return initialDeck.get(i);
	}
	
	public static ArrayList<Card> getPlayingDeck() {
		return playingDeck;
	}
	public static Card getTopCardDiscardPile() {
		return discardPile.get(discardPile.size() - 1);
	}
	
	public static void addToDiscardPile(Card card) {
		discardPile.add(card);
	}
	
	public static Card getTopCardPlayingDeck() {
		return playingDeck.get(playDeckIndex);
	}
	
	public static void removeFromPlayDeck(int index) {
		playingDeck.remove(index);
		playDeckIndex--;
	}

	public static void initializeDeck() {
		Card.allColors[] colors = Card.allColors.values();
		Card.allValues[] values = Card.allValues.values();
		for (int i = 0; i < colors.length - 1; i++) {
			Card.allColors color = colors[i]; 
			//add all 0 value'd cards first
			initialDeck.add(new Card(color, Card.allValues.getValue(0)));
			//cardCounter++;
			for (int j = 1; j < 13; j++) {
				//add all cards valued 1-9 and skip, draw2, reverse
				initialDeck.add(new Card(color, Card.allValues.getValue(j)));
				initialDeck.add(new Card(color, Card.allValues.getValue(j)));	
				//cardCounter+=2;
			}
			//add wild cards at the end.
			initialDeck.add(new Card(colors[4], values[13]));
			initialDeck.add(new Card(colors[4], values[14]));	
			//cardCounter+=2;
		} 
	}
	
	//shuffle algorithm from geeksforgeeks
	public static void shuffleDeck(ArrayList<Card> deckToShuffle) {
		for (int i = 0; i < deckToShuffle.size(); i++) {
			int index = (int) (Math.random() * deckToShuffle.size());
			deckToShuffle.add(deckToShuffle.remove(index));
		}	
		playingDeck = deckToShuffle;
	}
	
	//if the playingDeck runs out of cards to draw, reset it with this method
	public static void playingDeckReset() {
		if (playingDeck.isEmpty()) {
			for (int i = 0; i < discardPile.size()-1; i++) {
				playingDeck.add(discardPile.get(i));
				discardPile.remove(discardPile.get(i));
			}
			shuffleDeck(playingDeck);
		}
	}
	
}
