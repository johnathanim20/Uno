import java.util.ArrayList;


/* 
 * The GameState Class is used to generate an Instance of the Card Game 'UNO' by utilizing the Player, Card, and Deck Classes.
 */
public class GameState {
	boolean gameEnd;
	boolean drawStackedCards;
	public int numPlayers = 0;
	public int numAI = 0;
	int numStackedCards = 0;
	int currentPlayerIndex;
	Player[] players; 
	boolean clockwise;
	Player currentPlayer;
	String currentColor;
	private ArrayList<Card> initialDeck = new ArrayList<Card> ();
	private ArrayList<Card> discardPile = new ArrayList<Card> ();
	private ArrayList<Card> playingDeck = new ArrayList<Card> ();
	private int playDeckIndex = 107;
	
	public GameState() {
		currentPlayer = null;
		currentPlayerIndex = 0;
		clockwise = true;
		drawStackedCards = false;
		gameEnd = false;
	}
	
	public Card toCard(String card) {
		Card newCard = null;
		String color = "";
		if (card.contains("Red")) {
			color = "Red";
		}
		if (card.contains("Blue")) {
			color = "Blue";
		}
		if (card.contains("Yellow")) {
			color = "Yellow";
		}
		if (card.contains("Green")) {
			color = "Green";
		}
		if (card.contains("Wild")) {
			color = "Wild";
		}
		for (int j = 0; j < 10; j++) {
			if (card.contains(Integer.toString(j))) {
				newCard = new IntegerCard(color, Integer.toString(j)); 
			}
		}
		if (card.contains("WildDrawFour")) {
			newCard = new WildDrawFour("Wild", "WildDrawFour");
		} else if (card.contains("Wild")) {
			newCard = new Wild("Wild", "Wild");
		} else if (card.contains("Reverse")) {
			newCard = new Reverse(color, "Reverse");
		} else if (card.contains("DrawTwo")) {
			newCard = new DrawTwo(color, "DrawTwo");
		} else if (card.contains("Skip")) {
			newCard = new Skip(color, "Skip");
		}
		return newCard;
	}
	

	/*
	 * Function used for initializing game environment - for testing
	 */
	public void setGameUp() {
		initializeDeck();
		shuffleDeck();
		discardPile.add(playingDeck.get(playDeckIndex));
		removeFromPlayDeck();
		currentColor = getTopCardDiscardPile().getColor();
		numPlayers = 4;
		buildPlayerArr();
		currentPlayer = players[0];
	}
	
	public void setUpForGui() {
		initializeDeck();
		shuffleDeck();
		discardPile.add(playingDeck.get(playDeckIndex));
		removeFromPlayDeck();
		currentColor = getTopCardDiscardPile().getColor();
	}
	
	public int hasWon() {
		for (int i = 0; i < numPlayers; i++) {
			if (players[i].hand.size() == 0) {
				gameEnd = true;
				return i;
			}
		}
		return -1;
	}
	
	public void drawCard() {
		 currentPlayer.hand.add(getTopCardPlayingDeck());
	}
	 
	public void clearDecks() {
		initialDeck.clear();
		discardPile.clear();
		playingDeck.clear();
	}
	
	public int getPlayDeckIndex() {
		return playDeckIndex;
	}
	
	public  ArrayList<Card> getInitialDeck() {
		return initialDeck;
	}
	
	public Card getInitialDeckCard(int i) {
		return initialDeck.get(i);
	}
	
	public ArrayList<Card> getPlayingDeck() {
		return playingDeck;
	}
	
	public Card getTopCardDiscardPile() {
		return discardPile.get(discardPile.size() - 1);
	}
	
	public ArrayList<Card> getDiscardPile() {
		return discardPile;
	}
	public void addToDiscardPile(Card card) {
		discardPile.add(card);
	}
	
	public Card getTopCardPlayingDeck() {
		return playingDeck.get(playDeckIndex);
	}
	
	public void removeFromPlayDeck() {
		playingDeck.remove(playDeckIndex);
		playDeckIndex--;
	}
	
	public void additionRule(ArrayList<Card> hand) {
		try {
			int value = Integer.parseInt(getTopCardDiscardPile().getValue());
			for (int i = 0; i < hand.size() - 1; i++) {
				for (int j = i + 1; j < hand.size(); j++) {
					if (value == ((Integer.parseInt(hand.get(i).getValue())) + Integer.parseInt(hand.get(j).getValue())) && (hand.get(i).getColor() == hand.get(j).getColor())) {
						IntegerCard card1 = (IntegerCard) hand.get(i);
						IntegerCard card2 = (IntegerCard) hand.get(j);
						card1.playCard(this);
						card2.playCard(this);
						setNextPlayer();
						return;
					}
				}
			}
		} catch (NumberFormatException e) {
			
		}
	}
	
	public void subtractionRule(ArrayList<Card> hand) {
		try {
			int value = Integer.parseInt(getTopCardDiscardPile().getValue());
			for (int i = 0; i < hand.size() - 1; i++) {
				for (int j = i + 1; j < hand.size(); j++) {
					if (value == (Math.abs((Integer.parseInt(hand.get(i).getValue())) - Integer.parseInt(hand.get(j).getValue()))) && (hand.get(i).getColor() == hand.get(j).getColor())) {
						IntegerCard card1 = (IntegerCard) hand.get(i);
						IntegerCard card2 = (IntegerCard) hand.get(j);
						card1.playCard(this);
						card2.playCard(this);
						setNextPlayer();
						return;
					}
				}
			}
		} catch (NumberFormatException e) {
			
		}
	}
				
	
	/*
	 * Method that generates a standard deck of 108 UNO cards 
	 */
	public void initializeDeck() {
		for (int i = 0; i < Card.getColors().length - 1; i++) {
			String[] colors = Card.getColors(); 
			String[] values = Card.getValues(); 
			//add all 0 value'd cards first
			initialDeck.add(new IntegerCard(colors[i], values[0]));
			//cardCounter++;
			for (int j = 1; j < 10; j++) {
				//add all cards valued 1-9 and skip, draw2, reverse
				initialDeck.add(new IntegerCard(colors[i], values[j]));
				initialDeck.add(new IntegerCard(colors[i], values[j]));	
				//cardCounter+=2;
			}
			for (int x = 0; x < 2; x++) {
				initialDeck.add(new Skip(colors[i], values[10]));
				initialDeck.add(new Reverse(colors[i], values[11]));
				initialDeck.add(new DrawTwo(colors[i], values[12]));
			}
			//add wild cards at the end.
			initialDeck.add(new Wild(colors[4], values[13]));
			initialDeck.add(new WildDrawFour(colors[4], values[14]));	
			//cardCounter+=2;
		} 
	}
	
	/*
	 * shuffle algorithm from stack overflow : https://stackoverflow.com/questions/39557701/shuffle-a-deck-of-cards-in-java 
	 */
	public void shuffleDeck() {
		for (int i = 0; i < initialDeck.size(); i++) {
			int index = (int) (Math.random() * initialDeck.size());
			initialDeck.add(initialDeck.remove(index));
		}	
		playingDeck = initialDeck;
	}
	
	/*
	 * Call this method if the playingDeck runs out of cards to draw to reset it using the discard pile
	 */
	public void playingDeckReset() {
		if (playingDeck.isEmpty()) {
			for (int i = 0; i < discardPile.size()-1; i++) {
				playingDeck.add(discardPile.get(i));
				discardPile.remove(discardPile.get(i));
			}
			shuffleDeck();
		}
	}

	public int getNumPlayers() {
		return numPlayers;
	}
	
	public boolean getDirection() {
		return clockwise;
	}
	
	public Player getPlayer() {
		return currentPlayer;
	}
	
	public int getPlayerIndex() {
		return currentPlayerIndex;
	}
	
	public boolean getGameEnd() {
		return gameEnd;
	}
	
	public String getCurrentColor() {
		return currentColor;
	}
	
	public void setCurrentColor(String color) {
		currentColor = color;
	}

	/*
	 * Method to read user input to determine number of players in the game.
	 */
	

	/*
	 * Method to initialize a Player Array of size equal to the number of players, and initializes the hands of each player. (1 < number of players <= 10)
	 */
	public void buildPlayerArr() {
		players = new Player[numPlayers];
		for (int i = 0; i < numPlayers - numAI; i++) {
			players[i] = new Player(i);
			players[i].initializeHand(this);
		}
		for (int i = numPlayers - numAI; i < numPlayers; i++) {
			players[i] = new AI(i);
			players[i].initializeHand(this);
		}
	}
	
	/*
	 * Method to set the next Player's turn and set Current Player to be the next Player. Takes care of edge cases of being at the start or end of the Player Array.
	 */
	
	public void setNextPlayer() {
		if (clockwise) {
			currentPlayerIndex = (currentPlayerIndex + 1) % numPlayers;
			currentPlayer = players[currentPlayerIndex];
		} else {
			if (currentPlayerIndex == 0) {
				currentPlayerIndex = numPlayers - 1;
				currentPlayer = players[currentPlayerIndex];
			} else {
				currentPlayerIndex = (currentPlayerIndex - 1) % numPlayers;
				currentPlayer = players[currentPlayerIndex];
			}
		}	
	} 	
	

	/*
	 * Method that handles if there was card stacking.
	 */
	public void handlePenalty() {
		if (numStackedCards > 0 && drawStackedCards == true) {
			 for (int i = 0; i < numStackedCards; i++) {
				 currentPlayer.hand.add(getTopCardPlayingDeck());
				 removeFromPlayDeck();
			 }
			 setNextPlayer();
		}
	}
	
	
	/*
	 * Method that handles whenever a card is played and handles their effect
	 */
	public void cardHandler(Card card, ArrayList<Card> playerHand) {
	
		if (card.getValue().equals("WildDrawFour")) {
			WildDrawFour wildDrawFour = (WildDrawFour) card;
			wildDrawFour.playCard(this);
		} else if (card.getValue().equals("DrawTwo")) {
			DrawTwo drawTwo = (DrawTwo) card;
			drawTwo.playCard(this);
		} else if (card.getValue().equals("Skip")) {
			Skip skip = (Skip) card;
			skip.playCard(this);
		} else if (card.getValue().equals("Reverse")) {
			Reverse reverse = (Reverse) card;
			reverse.playCard(this);
		} else if (card.getValue().equals("Wild")) {
			Wild wild = (Wild) card;
			wild.playCard(this);
		} else if (card.checkAddRule(playerHand)) {
			additionRule(playerHand);
		} else if (card.checkSubRule(playerHand)) {
			subtractionRule(playerHand);
		} else {
			IntegerCard intCard = (IntegerCard) card;
			intCard.playCard(this);
			setNextPlayer();
		}
	}
	
	
	/*
	 * Method that handles playing the first valid Card in a GameState instance.
	 */

	
	public void baselinePlayCard() {
		if (players[currentPlayerIndex].isHandEmpty()) {
			gameEnd = true;
			return;
		}
		if (getPlayingDeck().isEmpty()) {
			playingDeckReset();
		}
		
		for (int j = 0; j < currentPlayer.hand.size(); j++) {
			if (currentPlayer.hand.get(j).validMove(currentPlayer.getHand(), this)) {
				handlePenalty();
				cardHandler(currentPlayer.hand.get(j), currentPlayer.getHand()); //handle card effect
				return;
			}
		}
		setNextPlayer();
	}
	
	/*
	 * Helper Method that finds the most frequent color in a hand.
	 */
	int blueCounter = 0;
	int yellowCounter = 0;
	int redCounter = 0;
	int greenCounter = 0;
	int max = 0;
	String colorToPlay = "";
	
	public void findFreqColor () {
		
		for (int j = 0; j < currentPlayer.hand.size(); j++) {
			if (currentPlayer.hand.get(j).getColor().equals("Blue")) {
				blueCounter++;
			}
			if (currentPlayer.hand.get(j).getColor().equals("Yellow")) {
				yellowCounter++;
			}
			if (currentPlayer.hand.get(j).getColor().equals("Red")) {
				redCounter++;
			}
			if (currentPlayer.hand.get(j).getColor().equals("Green")) {
				greenCounter++;
			}
		}
		int [] colorCounter = new int[] {blueCounter, yellowCounter, redCounter, greenCounter};
		for (int i = 0; i < colorCounter.length ; i++) {
			if (max < colorCounter[i]) {
				max = colorCounter[i];
			}
		}
		
		if (max == blueCounter) {
			colorToPlay = "Blue";
		}
		
		if (max == redCounter) {
			colorToPlay = "Red";
		}
		
		if (max == yellowCounter) {
			colorToPlay = "Yellow";

		}
		if (max == greenCounter) {
			colorToPlay = "Green";
		}
	}
	
	/*
	 *Method that handles playing the card that suits the following strategy : play the card with the most shared colors in a hand.
	 */
	public void strategicPlayCard() {
		if (players[currentPlayerIndex].isHandEmpty()) {
			gameEnd = true;
			return;
		}
		if (getPlayingDeck().isEmpty()) {
			playingDeckReset();
		}
		findFreqColor();
		for (int j = 0; j < currentPlayer.hand.size(); j++) {
			if (currentPlayer.hand.get(j).getValue().equals("Wild")) {
				currentColor = colorToPlay;
				handlePenalty();
				cardHandler(currentPlayer.hand.get(j), currentPlayer.getHand()); //handle card effect
				return;
			}
		}
		for (int j = 0; j < currentPlayer.hand.size(); j++) {
			if (colorToPlay.equals(currentPlayer.hand.get(j).getColor()) && currentPlayer.hand.get(j).validMove(currentPlayer.getHand(), this)) {
				handlePenalty();
				cardHandler(currentPlayer.hand.get(j), currentPlayer.getHand()); //handle card effect
				return;
			}
		}
		baselinePlayCard();
	}
}
