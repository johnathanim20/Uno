import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
 * The cardButtons class handles when a player's card is pressed and updates all game values according the card's effect.
 */
public class cardButtons implements ActionListener {
	Card card;
	GUI gui;
	public cardButtons(Card c1, GUI g) {
		card = c1;
		gui = g;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		gui.handlePlay(card);
		
	}
	
}
