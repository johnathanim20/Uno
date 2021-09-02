

public class Player {
	int order;
	boolean turn = false;
	//ArrayList<Card> playersHand = new ArrayList<Card> ();
	public Player(int order, boolean turn) {
		this.order = order;
		this.turn = turn;
	}
	public boolean getTurn() {
		return this.turn;
	}
	public void setTurn(boolean turn) {
		this.turn = turn;
	}

	public int getOrder() {
		return this.order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	/**public void setHand(ArrayList<Card> playersHand) {
		this.playersHand = playersHand;
	}
	public ArrayList<Card> getHand() {
		return this.playersHand;
	}
	*/
}
 