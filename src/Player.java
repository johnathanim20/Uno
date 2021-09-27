import java.util.ArrayList;

/*
 * The Player class initializes the hand of cards that each player possesses, and includes getters and setters to give access to a specific player's hand. 
 */
public class Player {	
	public ArrayList<Card> hand;
	int playerIndex;
	
	
	
	public Player(int index) {
		hand = new ArrayList<Card> ();
		playerIndex = index;
		
		
	}
	
	public void initializeHand(GameState gamestate) {
		for (int i = 0; i < 7; i++) {
			hand.add(gamestate.getPlayingDeck().get(gamestate.getPlayDeckIndex()));
			gamestate.removeFromPlayDeck();
		}
	}
	
	public void cleanUp() {
		hand.clear();
	}
	public ArrayList<Card> getHand() {
		return hand;
	}
	
	public Card getCardInHand(int index) {
		return hand.get(index);
	}
	public void addToHand(Card card) {
		hand.add(card);
	}
	
	public void removeFromHand(int index) {
		hand.remove(hand.get(index));
	}
	
	
	public boolean isHandEmpty() {
		return hand.isEmpty();
	}
	
	public int handSize() {
		return hand.size();
	}
	
	/*
	public int getPlayersIndex() {
		return this.playerIndex;
	}
	
	public void setPlayersIndex(int index) {
		this.playerIndex = index;
	}
	*/
	
}
 