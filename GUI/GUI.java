import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GUI implements ActionListener {
	
	JFrame startFrame;
	JFrame gameFrame;
	JFrame frameEnd;
	JButton startGame;
	JButton hide;
	JButton skip;
	JPanel panel;
	JPanel gamePanel;
	JPanel endPanel;
	JLabel endMessage;
	JLabel label;
	JLabel label2;
	JLabel hand;
	JTextField textField;
	JTextField chooseColor;
	JTextField enterNumAi;
	JLabel numP;
	JLabel currentColor;
	JLabel topDiscardPile;
	JLabel enterColor = new JLabel();
	JButton setColor = new JButton();
	JLabel stack;
	JLabel currPlayer;
	JLabel deck;
	int input;
	int inputAi;
	JButton b;
	JButton[] saveValues;
	cardButtons[] cButtons;
	JButton drawCard;
	GameState gamestate;
	boolean currentView = false;
	//boolean penalty = false;
	JButton payPenalty;
	
	public GUI() {
		gamestate = new GameState();
		gamestate.setUpForGui();
		makeInitialLabels();

	}
	/*
	 * Function to initialize the start frame labels and buttons.
	 */
	public void makeInitialLabels() {
		startFrame = new JFrame();
		startGame = new JButton("Start Game");
		startGame.addActionListener(this);
		gamePanel = new JPanel();
		gamePanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
		gamePanel.setLayout(new GridLayout(0, 1));
		gamePanel.add(startGame);
		startFrame.add(gamePanel, BorderLayout.CENTER);
		startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		startFrame.setTitle("UNO");
		startFrame.pack();
		startFrame.setVisible(true);
		label = new JLabel("Enter Number of Total Players (> 1 but <= 10)");
		label.setBounds(10,20,80,25);
		label2 = new JLabel("Enter Number of AI Players");
		label2.setBounds(10,20,80,25);
		gamePanel.add(label);
		textField = new JTextField();
		textField.setBounds(0,0,0,0);
		gamePanel.add(textField);
		enterNumAi = new JTextField();
		enterNumAi = new JTextField();
		gamePanel.add(label2);
		gamePanel.add(enterNumAi);
	}
	
	/*
	 * Function to display the number of total players.
	 */
	
	public void showNumPlayers() {
		numP = new JLabel("Number of Players : " + input);
		numP.setBounds(10,20,80,25);
		panel.add(numP);
	}
	
	/*
	 * Function to display the current color of the game.
	 */
	
	public void showCurrentColor () {
		currentColor = new JLabel("Current Color : " + gamestate.getCurrentColor());
		currentColor.setBounds(80, 80, 80, 24);
		panel.add(currentColor);
	}
	
	/*
	 * Function to display the current color of the game.
	 */
	public void showTopDiscardPile() {
		topDiscardPile = new JLabel("Current Card of Discard Pile : " + gamestate.getTopCardDiscardPile());
		topDiscardPile.setBounds(80, 80, 80, 24);
		panel.add(topDiscardPile);
	}
	
	/*
	 * Function to initialize the skip turn button.
	 */
	
	public void skipTurn() {
		skip = new JButton("Skip Turn");
		skip.addActionListener(this);
		panel.add(skip);
	}
	
	/*
	 * Function to show the index of the current player
	 */
	
	public void showCurrentPlayer() {
		currPlayer = new JLabel("Current Player : " + gamestate.getPlayer().playerIndex);
		currPlayer.setBounds(80, 80, 80, 24);
		panel.add(currPlayer);
	}
	
	/*
	 * Function to show the hand of the current player.
	 */
	public void showCurrentHand() {
		hand = new JLabel("Current Player's Hand : " + gamestate.getPlayer().hand);
		hand.setBounds(80, 80, 80, 24);
		panel.add(hand);
	}
	/*
	 * Function to add a card to the current player's hand.
	 */
	
	public void showDrawCard() {
		drawCard = new JButton("Draw a Card");
		drawCard.addActionListener(this);
		panel.add(drawCard);
	}
	
	/*
	 * Function to convert player cards into buttons.
	 */
	public void buttonPlayersHand() {
		saveValues = new JButton[gamestate.currentPlayer.handSize()];
		cButtons = new cardButtons[gamestate.currentPlayer.handSize()];
		for (int i = 0; i < gamestate.currentPlayer.handSize(); i++) {
			b = new JButton(gamestate.getPlayer().hand.get(i).toString());
			cardButtons cB = new cardButtons(gamestate.getPlayer().hand.get(i), this);
			cButtons[i] = cB;
			b.addActionListener(cB);
			panel.add(b);
			saveValues[i] = b;
		}
	}
	/*
	 * Function to reset player card buttons.
	 */
	
	public void clearSaveValues() {
		Arrays.fill(saveValues, null);
		Arrays.fill(cButtons, null);
	}
	
	/*
	 * Function to initialize the penalty button
	 */
	
	public void payPenalty() {
		
		payPenalty = new JButton("Pay Penalty");
		payPenalty.addActionListener(this);
		panel.add(payPenalty);
		
	}
	
	/*
	 * Function to make the current player's hand not-visible
	 */
	
	
	public void hideHand() {
		hide = new JButton("Hide/Show Hand");
		hide.addActionListener(this);
		panel.add(hide);
	}
	
	/*
	 * Function to show the number of stacked cards in the current gamestate.
	 */
	
	public void showStackedCards() {
		stack = new JLabel("Number of Cards Stacked : " + gamestate.numStackedCards);
		stack.setBounds(80, 80, 80, 24);
		panel.add(stack);
	}
	
	/*
	 * Function to display all necessary labels and buttons to play a turn of UNO
	 */
	
	public void setGameStateValues() {
		showNumPlayers();
		showCurrentPlayer();
		showCurrentHand();
		showCurrentColor();
		showTopDiscardPile();
		showStackedCards();
		skipTurn();
		hideHand();
		showDrawCard();
		payPenalty();
		buttonPlayersHand();
	}

	/*
	 * Function that triggers if any player's hand is empty and sets the end Frame.
	 */
	public void gameEnded() {
		gameFrame.dispose();
		gameFrame.setVisible(false);
		panel.removeAll();
		frameEnd = new JFrame();
		endPanel = new JPanel();
		endPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
		endPanel.setLayout(new GridLayout(0, 1));
		frameEnd.add(endPanel, BorderLayout.CENTER);
		frameEnd.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameEnd.setTitle("Game Ended");
		frameEnd.pack();
		frameEnd.setVisible(true);
		endMessage = new JLabel("Congratulations player " + gamestate.hasWon() + ", You Won!");
		endPanel.add(endMessage);
	}
	
	/*
	 * Function to handle when the start button is pressed, along with reading the input of AI and human players.
	 */
	public void onStartPress() {
		input = Integer.parseInt(textField.getText());
		inputAi = Integer.parseInt(enterNumAi.getText());
		gamestate.numPlayers = input;
		gamestate.numAI = inputAi;
		if (!(inputAi <= input)) {
			JOptionPane.showMessageDialog(gameFrame, "Invalid Number of AI Players. (Must be <= Total Number of Players");
		}
		gamestate.buildPlayerArr();
		gamestate.currentPlayer = gamestate.players[0];
		if (input <= 1 || input > 10) {
			JOptionPane.showMessageDialog(gameFrame, "Invalid Number of Players. (Must be more than 1 but less than 10)");
		}
		
		else {
			startFrame.setVisible(false);
			startFrame.dispose();
			panel = new JPanel();
			panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
			panel.setLayout(new GridLayout(0, 1));
			gameFrame = new JFrame();
			gameFrame.add(panel, BorderLayout.CENTER);
			gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			gameFrame.setTitle("Game in Progress");
			gameFrame.pack();
			gameFrame.setVisible(true);
			setGameStateValues();
			if (gamestate.getTopCardDiscardPile().getColor().equals("Wild")) {
				enterColor();
			}
		}
	}
	
	/*
	 * Function to handle when the hide/show hand button is pressed - hide or show the current player's hand.
	 */
	public void onHidePress() {
		if (currentView == false) {
			currentView = true;
			for (int i = 0; i < saveValues.length; i++) {
				panel.add(saveValues[i]);
			}
		} else if (currentView == true) {
			currentView = false;
			for (int i = 0; i < saveValues.length; i++) {
				panel.remove(saveValues[i]);
			}
		}
		hand.setVisible(currentView);
		
	}
	/*
	 * Function to handle when the skip button is pressed - set the current gamestate to show the next player's values.
	 */
	public void onSkipPress() {
		for (int i = 0; i < saveValues.length; i++) {
			panel.remove(saveValues[i]);
		}
		clearSaveValues();
		gamestate.setNextPlayer();
		currPlayer.setText("Current Player : " + gamestate.getPlayerIndex());
		hand.setText("Current Player's hand : " + gamestate.getPlayer().hand);
		buttonPlayersHand();
		if (gamestate.currentPlayer instanceof AI) {
			handleAiTurn();
		}
		for (int i = 0; i < gamestate.players.length; i++) {
			if (gamestate.players[i].hand.size() == 0) {
				gameEnded();
			}
		}
		enterColor.setVisible(false);
	}
	
	/*
	 * Function to handle when the set color button is pressed - updates the current color to be the user input.
	 */
	
	public void onSetColorPress() {
		if (chooseColor.getText().equals("Red") || chooseColor.getText().equals("Blue") || chooseColor.getText().equals("Yellow") || chooseColor.getText().equals("Green")) {
			gamestate.setCurrentColor(chooseColor.getText());
			currentColor.setText("Current Color : " + gamestate.getCurrentColor());
			panel.remove(enterColor);
			panel.remove(setColor);
			panel.remove(chooseColor);
			enterColor.setVisible(false);
			setColor.setVisible(false);
			chooseColor.setVisible(false);
		} else {
			JOptionPane.showMessageDialog(gameFrame, "Invalid Color. Color Options are Red, Blue, Yellow, Green");
		}
	}
	/*
	 * Function that initializes the text field to read user input.
	 */
	public void enterColor() {
		enterColor = new JLabel("Enter Desired Color");
		enterColor.setBounds(10,20,80,25);
		panel.add(enterColor);
		chooseColor = new JTextField(20);
		chooseColor.setBounds(100,20,165,25);
		panel.add(chooseColor);
		setColor = new JButton("Set Color");
		setColor.addActionListener(this);
		panel.add(setColor);
	}
	
	/*
	 * Function that updates the current game state values.
	 */
	
	public void updateGameValues () {
		currentColor.setText("Current Color : " + gamestate.getCurrentColor());
		topDiscardPile.setText("Current Card of Discard Pile : " + gamestate.getTopCardDiscardPile());
		currPlayer.setText("Current Player : " + gamestate.getPlayer().playerIndex);
		hand.setText("Current Player's Hand : " + gamestate.getPlayer().hand);
		stack.setText("Number of Cards Stacked : " + gamestate.numStackedCards);
	}
	
	/*
	 * Function that handles when the pay penalty button is pressed - force the player to draw cards and move to the next player
	 */

	
	public void onPayPenaltyPress() {
		if (gamestate.numStackedCards != 0 && gamestate.getPlayer() instanceof Player) {
			for (int i = 0; i < saveValues.length; i++) {
				panel.remove(saveValues[i]);
			}
			clearSaveValues();
			gamestate.handlePenalty();
			gamestate.numStackedCards = 0;
			gamestate.setNextPlayer();
			buttonPlayersHand();
			updateGameValues();
		} else if (gamestate.currentPlayer instanceof AI) {
			handleAiTurn();
		}
		gamestate.numStackedCards = 0;
	}
	/*
	 * Function that handles an AI player's turn.
	 */
	public void handleAiTurn() {
		gamestate.strategicPlayCard();
		updateGameValues();
		for (int i = 0; i < gamestate.players.length; i++) {
			if (gamestate.players[i].hand.size() == 0) {
				gameEnded();
			}
		}
		
	}
	
	/*
	 * Function that handles a human player's turn.
	 */

	public void handlePlay(Card card) {
		if (card.validMove(gamestate.currentPlayer.hand, gamestate)) {
			if (card.getColor().equals("Wild")) {
				enterColor();
			}
			gamestate.handlePenalty();
			gamestate.cardHandler(card, gamestate.currentPlayer.hand);
			for (int i = 0; i < saveValues.length; i++) {
				panel.remove(saveValues[i]);
			}
			for (int i = 0; i < gamestate.players.length; i++) {
				if (gamestate.players[i].hand.size() == 0) {
					gameEnded();
				}
			}
			clearSaveValues();
			buttonPlayersHand();
			updateGameValues();
		} else if (gamestate.currentPlayer instanceof AI) {
			handleAiTurn();
		}
	}
	
	/*
	 * Function that handles when the draw card button is pressed.
	 */
	public void onDrawCardPress() {
		gamestate.drawCard();
		for (int i = 0; i < saveValues.length; i++) {
			panel.remove(saveValues[i]);
		}
		clearSaveValues();
		hand.setText("Current Player's Hand : " + gamestate.getPlayer().hand);
		buttonPlayersHand();

	}

	/*
	 * Function that handles all the possible button actions.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == startGame) {
			onStartPress();
		} else if (e.getSource() == hide) {
			onHidePress();
		} else if (e.getSource() == skip) {
			onSkipPress();
		} else if (e.getSource() == setColor) {
			onSetColorPress();
		} else if (e.getSource() == drawCard) {
			onDrawCardPress();
		} else if (e.getSource() == payPenalty) {
			onPayPenaltyPress();
		}
		
	}
	
	
	
	public static void main(String[] args) {
		GUI g = new GUI();
		
	}
		
	
}

