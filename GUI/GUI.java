import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GUI implements ActionListener {
	
	JFrame frame;
	JFrame gameFrame;
	JFrame frameEnd;
	JButton button1;
	JButton hide;
	JButton setColor;
	JButton skip;
	JPanel panel;
	JPanel endPanel;
	JLabel endMessage;
	JLabel label;
	JLabel hand;
	JTextField textField;
	JTextField chooseColor;
	JLabel numP;
	JLabel currentColor;
	JLabel topDiscardPile;
	JLabel enterColor;
	JLabel stack;
	JLabel currPlayer;
	JLabel deck;
	int input;
	
	GameState gamestate;
	boolean currentView = false;
	
	public GUI() {
		gamestate = new GameState();
		gamestate.setGameUp();
		frame = new JFrame();
		button1 = new JButton("Start Game");
		button1.addActionListener(this);
		panel = new JPanel();
		panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
		panel.setLayout(new GridLayout(0, 1));
		panel.add(button1);
		frame.add(panel, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("UNO");
		frame.pack();
		frame.setVisible(true);
		makeInitialLabels();
		gameEnded();
	}
	
	public void makeInitialLabels() {
		label = new JLabel("Enter Number of Players (> 1 but <= 10)");
		label.setBounds(10,20,80,25);
		panel.add(label);
		textField = new JTextField(20);
		textField.setBounds(100,20,165,25);
		panel.add(textField);
	}
	public void showNumPlayers() {
		numP = new JLabel("Number of Players : " + input);
		numP.setBounds(10,20,80,25);
		panel.add(numP);
	}
	
	public void showCurrentColor () {
		currentColor = new JLabel("Current Color : " + gamestate.getCurrentColor());
		currentColor.setBounds(80, 80, 80, 24);
		panel.add(currentColor);
	}
	
	public void showTopDiscardPile() {
		topDiscardPile = new JLabel("Current Card of Discard Pile : " + gamestate.getTopCardDiscardPile());
		topDiscardPile.setBounds(80, 80, 80, 24);
		panel.add(topDiscardPile);
	}
	
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
	
	public void skipTurn() {
		skip = new JButton("Skip Turn");
		skip.addActionListener(this);
		panel.add(skip);
	}
	
	public void showCurrentPlayer() {
		currPlayer = new JLabel("Current Player : " + gamestate.getPlayer().playerIndex);
		currPlayer.setBounds(80, 80, 80, 24);
		panel.add(currPlayer);
	}
	
	public void showCurrentHand() {
		hand = new JLabel("Current Player's Hand : " + gamestate.getPlayer().hand);
		hand.setBounds(80, 80, 80, 24);
		panel.add(hand);
	}
	
	public void hideHand() {
		hide = new JButton("Hide/Show Hand");
		hide.addActionListener(this);
		panel.add(hide);
	}
	
	public void showStackedCards() {
		stack = new JLabel("Number of Cards Stacked : " + gamestate.numStackedCards);
		stack.setBounds(80, 80, 80, 24);
		panel.add(stack);
	}
	
	public void showCurrentPlayDeck() {
		deck = new JLabel();
		deck.setText("<html>" + "Current Playing Deck : " + gamestate.getPlayingDeck() + "</html>");
		deck.setBounds(80, 80, 80, 24);
		panel.add(deck);
	}
	
	public void setGameStateValues() {
		showNumPlayers();
		showCurrentPlayer();
		showCurrentHand();
		showCurrentColor();
		showTopDiscardPile();
		showStackedCards();
		showCurrentPlayDeck();
		enterColor();
		skipTurn();
		hideHand();
		
	}

	public static void main(String[] args) {
		new GUI();
	}
	
	public void gameEnded() {
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
	
	public void onStartPress() {
		input = Integer.parseInt(textField.getText());
		if (input <= 1 || input > 10) {
			JOptionPane.showMessageDialog(gameFrame, "Invalid Number of Players. (Must be more than 1 but less than 10)");
		} else {
			frame.setVisible(false);
			gameFrame = new JFrame();
			gameFrame.add(panel, BorderLayout.CENTER);
			gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			gameFrame.setTitle("Game in Progress");
			gameFrame.pack();
			gameFrame.setVisible(true);
			button1.setVisible(false);
			label.setVisible(false);
			textField.setVisible(false);
			setGameStateValues();
		}
	}
	
	public void onHidePress() {
		if (currentView == false) {
			currentView = true;
		} else if (currentView == true) {
			currentView = false;
		}
		hand.setVisible(currentView);
	}
	
	public void onSkipPress() {
		gamestate.setNextPlayer();
		currPlayer.setText("Current Player : " + gamestate.getPlayerIndex());
		hand.setText("Current Player's hand : " + gamestate.getPlayer().hand);
	}
	
	public void onSetColorPress() {
		gamestate.setCurrentColor(chooseColor.getText());
		currentColor.setText("Current Color : " + gamestate.getCurrentColor());
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == button1) {
			onStartPress();
		} else if (e.getSource() == hide) {
			onHidePress();
		} else if (e.getSource() == skip) {
			onSkipPress();
		} else if (e.getSource() == setColor) {
			onSetColorPress();
		}
	}
}

