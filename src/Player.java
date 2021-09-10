import java.util.ArrayList;

/*
 * The Player class initializes the hand of cards that each player possesses, and includes getters and setters to give access to a specific player's hand. 
 */
public class Player {	
	public ArrayList<Card> hand;
	
	
	public Player() {
		hand = new ArrayList<Card> ();
		//this.playerIndex = playerIndex;
	}
	
	public void initializeHand(GameState gamestate) {
		for (int i = 0; i < 7; i++) {
			hand.add(gamestate.getPlayingDeck().get(gamestate.getPlayDeckIndex()));
			gamestate.removeFromPlayDeck((gamestate.getPlayDeckIndex()));
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
	
	public void removeFromHand(Card card) {
		hand.remove(card);
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
 