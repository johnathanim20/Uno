import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/*
 * Test Class to check if helper methods and class generation are working properly.
 */

public class JUnitTests {
	GameState gamestate;
	
	@BeforeEach
	void setUp() {
		gamestate = new GameState();
	}
	
	@Test
	void test1() {
		System.out.println(gamestate.toCard("Wild - WildDrawFour"));
	}
	
	/*
	 * Test the initial player's hand size. Each player should have 7 cards.
	 */
	@Test
	void testInitializeHand() {
		gamestate.setGameUp();
		assertEquals(gamestate.getInitialDeck().size(), 79);
		for (int i = 0; i < gamestate.numPlayers; i++) {
			assertEquals(gamestate.players[i].hand.size(), 7);
		}
	}
	
	@Test
	void testNumberFillDeck() {
		gamestate.setGameUp();
		assertEquals(108, gamestate.getInitialDeck().size() + 7 * gamestate.numPlayers + 1);
	}
	
	
	/*
	Test if all 108 UNO cards are generated.
	 */
	@Test
	void testContentFillDeck() {
		gamestate.initializeDeck();
		for (int i = 0; i < 108; i++) {
			Card card = gamestate.getInitialDeckCard(i);
			assertEquals(card.toString(), card.getColor() + " - " + card.getValue());
			//System.out.println(card.toString());
		}
	}
	
	/*
	 * Test if setNextPlayer correctly updates the index of the currentPlayer.
	 */
	@Test
	void testSetNextPlayer() {
		gamestate.setGameUp();
		gamestate.setNextPlayer();
		assertEquals(1, gamestate.getPlayerIndex());
		gamestate.setNextPlayer();
		assertEquals(2, gamestate.getPlayerIndex());
		gamestate.setNextPlayer();
		assertEquals(3, gamestate.getPlayerIndex());
		gamestate.setNextPlayer();
		assertEquals(0, gamestate.getPlayerIndex());
	}
	
	@Test
	void removetest() {
		gamestate.setGameUp();
		IntegerCard n = new IntegerCard("Blue", "1");
		IntegerCard n2 = new IntegerCard("Blue", "1");
		gamestate.players[0].hand.clear();
		gamestate.getDiscardPile().add(n);
		gamestate.players[0].hand.add(n);
		gamestate.players[0].removeFromHand(0);
		System.out.println(gamestate.players[0].hand);
	}

	@Test
	void testNormal() {
		gamestate.setGameUp();
		IntegerCard n = new IntegerCard("Blue", "1");
		gamestate.players[0].hand.clear();
		gamestate.getDiscardPile().add(n);
		gamestate.players[0].hand.add(n);
		gamestate.cardHandler(n, gamestate.getPlayer().getHand());
		assertEquals(true, gamestate.getDirection());
		System.out.println(gamestate.players[0].handSize());
	}
	
	/*
	 * Test when a reverse card is played and if the direction of the gamestate is updated.
	 */
	@Test
	void testReverseCard() {
		gamestate.setGameUp();
		Reverse reverse = new Reverse("Blue", "Reverse");
		gamestate.cardHandler(reverse, gamestate.getPlayer().getHand());
		assertEquals(false, gamestate.getDirection());
		//System.out.println("\n" + "this is the current player index after reversing the order " + gamestate.getPlayerIndex());
		gamestate.cardHandler(reverse, gamestate.getPlayer().getHand());
		assertEquals(true, gamestate.getDirection());
		//System.out.println("\n" + "this is the current player index after reversing the order again " + gamestate.getPlayerIndex());
	}
	
	/*
	 * Test when a draw2 card is played and if playerindex and stackedcards are updated.
	 */
	@Test
	void testDrawTwoCard() {
		gamestate.setGameUp();
		DrawTwo d2 = new DrawTwo("Blue", "DrawTwo");
		gamestate.cardHandler(d2, gamestate.getPlayer().getHand());
		assertEquals(true, gamestate.getDirection());
		//System.out.println("\n" + "this is the current player index after the turn " + gamestate.getPlayerIndex());
		assertEquals(2, gamestate.getPlayerIndex());
		assertEquals(2, gamestate.numStackedCards);
	}
	
	/*
	 * Test when a wild card is played and if current color is set to user input
	 */
	@Test 
	void testWildCard() {
		gamestate.setGameUp();
		Wild w = new Wild("Wild", "Wild");
		gamestate.cardHandler(w, gamestate.getPlayer().getHand());
		assertEquals(true, gamestate.getDirection());
		assertEquals(1, gamestate.getPlayerIndex());
		assertEquals(0, gamestate.numStackedCards);
		System.out.println(gamestate.getCurrentColor());
		//assertEquals(gamestate.getCurrentColor(), "Blue");
		
	}
	/*
	 * Test when a wild4 card is played and if current color is set to user input, and if playerindex and stacked cards are updated properly.
	 */
	@Test 
	void testWildDrawFourCard() {
		gamestate.setGameUp();
		WildDrawFour w1 = new WildDrawFour("Wild", "WildDrawFour");
		gamestate.cardHandler(w1, gamestate.getPlayer().getHand());
		assertEquals(true, gamestate.getDirection());
		assertEquals(2, gamestate.getPlayerIndex());
		assertEquals(4, gamestate.numStackedCards);
		System.out.println(gamestate.getCurrentColor());
		//assertEquals(gamestate.getCurrentColor(), "Blue");
	}
	
	@Test
	/*
	 * Test to see if when 2 consecutive draw 2 cards are played, the next next player pays the penalty if he does not have anything to stack.
	 */
	void testStackedCards() {
		gamestate.setGameUp();
		DrawTwo d2 = new DrawTwo("Blue", "DrawTwo");
		gamestate.addToDiscardPile(d2);
		gamestate.players[0].hand.clear();
		gamestate.players[0].hand.add(d2);
		gamestate.players[1].hand.clear();
		gamestate.players[1].hand.add(d2);
		gamestate.players[2].hand.clear();
		gamestate.cardHandler(d2, gamestate.getPlayer().getHand());
		gamestate.cardHandler(d2, gamestate.getPlayer().getHand());
		gamestate.setNextPlayer();
		gamestate.setNextPlayer();
		gamestate.handlePenalty();
		assertEquals(gamestate.players[2].hand.size(), 4);
	}
	
	/*
	 * Test to see if when 2 consecutive draw4 cards are played, the next next player pays the penalty if he does not have anything to stack.
	 */
	@Test
	void testStackedCards2() {
		gamestate.setGameUp();
		WildDrawFour d2 = new WildDrawFour("Wild", "WildDrawFour");
		gamestate.addToDiscardPile(d2);
		gamestate.players[0].hand.clear();
		gamestate.players[0].hand.add(d2);
		gamestate.players[1].hand.clear();
		gamestate.players[1].hand.add(d2);
		gamestate.players[2].hand.clear();
		gamestate.cardHandler(d2, gamestate.getPlayer().getHand());
		gamestate.cardHandler(d2, gamestate.getPlayer().getHand());
		gamestate.setNextPlayer();
		gamestate.setNextPlayer();
		gamestate.handlePenalty();
		assertEquals(gamestate.players[2].hand.size(), 8);
	}
	
	@Test
	void testAddRule() {
		gamestate.setGameUp();
		IntegerCard c1 = new IntegerCard("Blue", "5");
		IntegerCard c2 = new IntegerCard("Yellow", "3");
		IntegerCard c3 = new IntegerCard("Yellow", "2");
		
		gamestate.addToDiscardPile(c1);
		gamestate.players[0].hand.clear();
		gamestate.players[0].hand.add(c2);
		gamestate.players[0].hand.add(c3);
		
		System.out.println(gamestate.players[0].hand);
		System.out.println(gamestate.getDiscardPile());
		gamestate.cardHandler(c1, gamestate.players[0].hand);
		
		System.out.println(gamestate.getDiscardPile());
		System.out.println(gamestate.players[0].hand);
	
	}
	
	@Test
	void testAddRule2() {
		gamestate.setGameUp();
		IntegerCard c1 = new IntegerCard("Blue", "5");
		IntegerCard c2 = new IntegerCard("Yellow", "1");
		IntegerCard c3 = new IntegerCard("Yellow", "2");
		IntegerCard c4 = new IntegerCard("Yellow", "3");
		gamestate.addToDiscardPile(c1);
		gamestate.players[0].hand.clear();
		gamestate.players[0].hand.add(c2);
		gamestate.players[0].hand.add(c3);
		gamestate.players[0].hand.add(c4);
		System.out.println(gamestate.players[0].hand.size());
		gamestate.baselinePlayCard();
		System.out.println(gamestate.getDiscardPile());
		System.out.println(gamestate.players[0].hand);
		System.out.println(gamestate.getPlayerIndex());
		assertEquals(gamestate.players[0].hand.size(), 1);
	}
	
	@Test
	void testAddRule3() {
		gamestate.setGameUp();
		IntegerCard c1 = new IntegerCard("Blue", "5");
		IntegerCard c2 = new IntegerCard("Yellow", "1");
		IntegerCard c3 = new IntegerCard("Yellow", "2");
		IntegerCard c4 = new IntegerCard("Yellow", "7");
		gamestate.addToDiscardPile(c1);
		gamestate.players[0].hand.clear();
		gamestate.players[0].hand.add(c2);
		gamestate.players[0].hand.add(c3);
		gamestate.players[0].hand.add(c4);
		System.out.println(gamestate.players[0].hand.size());
		System.out.println(gamestate.getTopCardPlayingDeck());
		gamestate.baselinePlayCard();
		System.out.println(gamestate.getDiscardPile());
		System.out.println(gamestate.players[0].hand);
		System.out.println(gamestate.getPlayerIndex());
		assertEquals(gamestate.players[0].hand.size(), 1);
	}
	
	@Test
	void testSubRule() {
		gamestate.setGameUp();
		IntegerCard c1 = new IntegerCard("Blue", "5");
		IntegerCard c2 = new IntegerCard("Yellow", "9");
		IntegerCard c3 = new IntegerCard("Yellow", "3");
		IntegerCard c4 = new IntegerCard("Yellow", "4");
		gamestate.addToDiscardPile(c1);
		gamestate.players[0].hand.clear();
		gamestate.players[0].hand.add(c2);
		gamestate.players[0].hand.add(c3);
		gamestate.players[0].hand.add(c4);
		gamestate.cardHandler(c4, gamestate.players[0].hand);
		System.out.println(gamestate.getDiscardPile());
		System.out.println(gamestate.players[0].hand);
		assertEquals(gamestate.players[0].hand.size(), 1);
	}
	 
	@Test
	void testCardHandler() {
		gamestate.setGameUp();
		gamestate.players[0].hand.clear();
		gamestate.players[1].hand.clear();
		gamestate.players[2].hand.clear();
		gamestate.players[3].hand.clear();
		IntegerCard c1 = new IntegerCard("Blue", "1");
		IntegerCard c2 = new IntegerCard("Blue", "7");
		IntegerCard c3 = new IntegerCard("Blue", "3");
		IntegerCard c4 = new IntegerCard("Red", "1");
		IntegerCard c5 = new IntegerCard("Red", "2");
		IntegerCard c6 = new IntegerCard("Red", "3");
		IntegerCard c7 = new IntegerCard("Red", "7");
		IntegerCard c8 = new IntegerCard("Yellow", "3");
		gamestate.addToDiscardPile(c8);
		System.out.println(gamestate.getDiscardPile());
		gamestate.players[0].hand.add(c1);
		gamestate.players[0].hand.add(c2);
		gamestate.players[0].hand.add(c3);
		gamestate.players[0].hand.add(c4);
		gamestate.players[0].hand.add(c5);
		gamestate.players[0].hand.add(c6);
		gamestate.players[0].hand.add(c7);
		System.out.println(gamestate.players[0].hand);
		gamestate.cardHandler(c7, gamestate.players[0].hand);
		System.out.println(gamestate.getDiscardPile());
		System.out.println(gamestate.players[0].hand);
		assertEquals(gamestate.players[0].hand.size(), 5);
	}
	
	@Test
	void testBaselinePlayCard() {
		gamestate.setGameUp();
		gamestate.players[0].hand.clear();
		gamestate.players[1].hand.clear();
		gamestate.players[2].hand.clear();
		gamestate.players[3].hand.clear();
		IntegerCard c1 = new IntegerCard("Blue", "1");
		IntegerCard c2 = new IntegerCard("Blue", "7");
		IntegerCard c3 = new IntegerCard("Blue", "3");
		IntegerCard c4 = new IntegerCard("Red", "1");
		IntegerCard c5 = new IntegerCard("Red", "2");
		IntegerCard c6 = new IntegerCard("Red", "3");
		IntegerCard c8 = new IntegerCard("Yellow", "3");
		gamestate.addToDiscardPile(c8);
		System.out.println(gamestate.getDiscardPile());
		gamestate.players[0].hand.add(c1);
		gamestate.players[0].hand.add(c2);
		gamestate.players[0].hand.add(c3);
		gamestate.players[0].hand.add(c4);
		gamestate.players[0].hand.add(c5);
		gamestate.players[0].hand.add(c6);
	
		System.out.println(gamestate.players[0].hand);
		gamestate.baselinePlayCard();
		System.out.println(gamestate.getDiscardPile());
		System.out.println(gamestate.players[0].hand);
		assertEquals(gamestate.players[0].hand.size(), 4);
	}
	
	@Test
	void testStrategicPlayCard() {
		gamestate.setGameUp();
		gamestate.players[0].hand.clear();
		gamestate.players[1].hand.clear();
		gamestate.players[2].hand.clear();
		gamestate.players[3].hand.clear();
		IntegerCard c1 = new IntegerCard("Blue", "1");
		IntegerCard c2 = new IntegerCard("Blue", "7");
		IntegerCard c3 = new IntegerCard("Blue", "3");
		IntegerCard c4 = new IntegerCard("Red", "1");
		IntegerCard c5 = new IntegerCard("Red", "2");
		IntegerCard c6 = new IntegerCard("Red", "3");
		IntegerCard c0 = new IntegerCard("Blue", "2");
		IntegerCard c8 = new IntegerCard("Yellow", "3");
		gamestate.addToDiscardPile(c8);
		System.out.println(gamestate.getDiscardPile());
		gamestate.players[0].hand.add(c1);
		gamestate.players[0].hand.add(c2);
		gamestate.players[0].hand.add(c3);
		gamestate.players[0].hand.add(c4);
		gamestate.players[0].hand.add(c5);
		gamestate.players[0].hand.add(c6);
		gamestate.players[0].hand.add(c0);
		System.out.println(gamestate.players[0].hand);
		System.out.println(gamestate.getTopCardPlayingDeck());
		gamestate.findFreqColor();
		//System.out.println(gamestate.blueCounter);
		//System.out.println(gamestate.max);
		System.out.println(gamestate.colorToPlay == gamestate.currentPlayer.hand.get(0).getColor());
		gamestate.strategicPlayCard();
		System.out.println(gamestate.getDiscardPile());
		System.out.println(gamestate.players[0].hand);
		assertEquals(gamestate.players[0].hand.size(), 5);

	}

}
