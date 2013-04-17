package edu.txstate.hearts.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.txstate.hearts.controller.Hearts;
import edu.txstate.hearts.model.*;
import edu.txstate.hearts.model.Card.Suit;
import edu.txstate.hearts.utils.ReadFiles;

public class HeartsUI
{
	public enum CardAction
	{
		Passing, Playing
	}
	
	public enum Position
	{
		North, South, West, East
	}
	
	private JFrame frame;
	
	public JFrame getframe()
	{
		return frame;
	}
	
	private JPanel panel;
	private JTextField balloonTextField;
	private JButton passButton;
	private JButton northCardButton;
	private JButton southCardButton;
	private JButton eastCardButton;
	private JButton westCardButton;
	
	private Hearts heartsController;
	private List<Player> players;
	private List<JButton> jButtonNorthList;
	private List<JButton> jButtonSouthList;
	private List<JButton> jButtonEastList;
	private List<JButton> jButtonWestList;
	private boolean SHOW_OPPONENT_CARDS = false;
	private final int CARD_WIDTH = 72;
	private final int CARD_HEIGHT = 96;
	private final int FRAME_WIDTH = 740;
	private final int FRAME_HEIGHT = 730;
	private final int PANEL_WIDTH = 740;
	private final int PANEL_HEIGHT = 730;
	
	public void showDialog()
	{
		try
		{
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public HeartsUI()
	{
	}
	
	public HeartsUI(List<Player> players)
	{
		this.players = players;
		initialize();
	}
	
	public void setPlayers(List<Player> players)
	{
		this.players = players;
		
		//south
		JLabel player1Label = new JLabel();
		String playerName = players.get(0).getName();
		player1Label.setText(playerName);
		player1Label.setBounds(20, 500, 100, 25);
		player1Label.setVisible(true);
		this.panel.add(player1Label);
		
		//west
		JLabel player2Label = new JLabel();
		player2Label.setText(players.get(1).getName());
		player2Label.setBounds(20, 140, 100, 25);
		player2Label.setVisible(true);
		this.panel.add(player2Label);
		
		//north
		JLabel player3Label = new JLabel();
		player3Label.setText(players.get(2).getName());
		player3Label.setBounds(20, 10, 100, 25);
		player3Label.setVisible(true);
		this.panel.add(player3Label);
		
		//east
		JLabel player4Label = new JLabel();
		player4Label.setText(players.get(3).getName());
		player4Label.setBounds(660, 140, 100, 25);
		player4Label.setVisible(true);
		this.panel.add(player4Label);
	}
	
	public void setUI(boolean showOpponentCards)
	{
		this.SHOW_OPPONENT_CARDS = showOpponentCards;
		initialize();
	}
	
	public void addController(Hearts heartsController)
	{
		this.heartsController = heartsController;
	}
	
	private void initialize()
	{
		frame = new JFrame();
		frame.setSize(this.FRAME_WIDTH, this.FRAME_HEIGHT);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Hearts - game");
		
		Image image = ReadFiles.getImage("heart.png");
		frame.setIconImage(image);
		
		panel = new JPanel();
		panel.setLayout(null);
		panel.setBounds(0, 0, this.PANEL_WIDTH, this.PANEL_HEIGHT);
		
		// add pass button
		JButton passButton = new JButton();
		passButton.setBounds(panel.getWidth() / 2 + 60,
				panel.getHeight() - 270, 100, 30);
		passButton.setHorizontalTextPosition(JButton.CENTER);  
		passButton.setText("<< Pass >>");
		passButton.setVisible(false);
		passButton.putClientProperty("ButtonType", "PassButton");
		passButton.addActionListener(this.heartsController);
		panel.add(passButton);
		this.passButton = passButton;
		
		this.initializePlayingButtons();
		this.initializeBalloonTip();
		panel.setBackground(Color.GREEN);
		frame.add(panel);
		frame.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}
		});
		
		JMenuBar menuBar;
		JMenu menu, submenu;
		JMenuItem menuItem;

		//Create the menu bar.
		menuBar = new JMenuBar();

		//Build the first menu.
		menu = new JMenu("Game");
		menu.setMnemonic(KeyEvent.VK_G);
		menu.getAccessibleContext().setAccessibleDescription("Game menu");
		menuBar.add(menu);

		menuItem = new JMenuItem("Rules", KeyEvent.VK_R);
		menuItem.getAccessibleContext().setAccessibleDescription("Show game rules");
		menuItem.putClientProperty("MenuItemType", "Rules");
		menuItem.addActionListener(this.heartsController);
		menu.add(menuItem);

		menuItem = new JMenuItem("Achievements", KeyEvent.VK_A);
		menuItem.getAccessibleContext().setAccessibleDescription("Show user achievements");
		menuItem.putClientProperty("MenuItemType", "Achievements");
		menuItem.addActionListener(this.heartsController);
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Exit", KeyEvent.VK_X);
		menuItem.getAccessibleContext().setAccessibleDescription("Exit game");
		menuItem.putClientProperty("MenuItemType", "Exit");
		menuItem.addActionListener(this.heartsController);
		menu.add(menuItem);

		frame.setJMenuBar(menuBar);
	}
	
	public void displayCards()
	{
		// remove old buttons
		// create new buttons
		initializeButtons();
	}
	
	public void setPassButtonVisible(boolean flag)
	{
		passButton.setVisible(flag);
	}
	
	private List<JButton> getJButtonList(Position position)
	{
		List<JButton> jButtonList = null;
		switch (position)
		{
			case North:
				jButtonList = jButtonNorthList;
				break;
			case South:
				jButtonList = jButtonSouthList;
				break;
			case West:
				jButtonList = jButtonWestList;
				break;
			case East:
				jButtonList = jButtonEastList;
				break;
		}
		
		return jButtonList;
	}
	
	public void ShowBalloonTip(String message)
	{
		this.balloonTextField.setText(message);
		this.balloonTextField.setVisible(true);
		this.repaintPanel(); //660
		
		try
		{
			Thread.sleep(1000);
		}
		catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.balloonTextField.setVisible(false);
		this.repaintPanel(); //660
	}
	
	private void initializeBalloonTip()
	{
		JTextField jTextField = new JTextField();
		jTextField.setBounds(20, panel.getHeight() - 90, 300, 25);
		jTextField.setBackground(Color.YELLOW);
		panel.add(jTextField);
		this.balloonTextField = jTextField;
		this.balloonTextField.setVisible(false);
	}
	
	private void initializePlayingButtons()
	{
		int xBound = 20;
		int yBound = 20;
		
		// middle 4 cardsJButton jButton = new JButton();
		northCardButton = new JButton();
		northCardButton.setBounds(panel.getWidth() / 2 - 40, yBound + 120,
				this.CARD_WIDTH, this.CARD_HEIGHT);
		panel.add(northCardButton);
		this.northCardButton.setVisible(false);
		
		southCardButton = new JButton();
		southCardButton.setBounds(panel.getWidth() / 2 - 40,
				panel.getHeight() - 320, this.CARD_WIDTH, this.CARD_HEIGHT);
		panel.add(southCardButton);
		this.southCardButton.setVisible(false);
		
		westCardButton = new JButton();
		westCardButton.setBounds(xBound + 180, panel.getHeight() / 2 - 90,
				this.CARD_WIDTH, this.CARD_HEIGHT);
		panel.add(westCardButton);
		this.westCardButton.setVisible(false);
		
		eastCardButton = new JButton();
		eastCardButton.setBounds(panel.getWidth() - 280,
				panel.getHeight() / 2 - 90, this.CARD_WIDTH, this.CARD_HEIGHT);
		panel.add(eastCardButton);
		this.eastCardButton.setVisible(false);
		
	}
	
	public void redrawCards()
	{
		Player player = null;
		
		for (Position position : Position.values())
		{
			switch (position)
			{
				case North:
					if(players != null)
						player = players.get(2);
					break;
				case South:
					if(players != null)
						player = players.get(0);
					break;
				case West:
					if(players != null)
						player = players.get(1);
					break;
				case East:
					if(players != null)
						player = players.get(3);
					break;
			}
			
			List<JButton> jButtonList = getJButtonList(position);
			
			List<Card> suitHand = getHandGroupBySuit(player.getHand());
			
			for (int i = 0; i < 13; i++)
			{
				Card card = suitHand.get(i);
				JButton jButton = jButtonList.get(i);
				jButton.putClientProperty("ButtonType", "CardButton");
				jButton.putClientProperty("Card", card);
				jButton.putClientProperty("Position", position);
				jButton.putClientProperty("Selected", false);
				
				if(position != Position.South && !this.SHOW_OPPONENT_CARDS)
				{
					Image image = ReadFiles.getImage("cards\\back_blue_potrait.png");
					Image scaledImage = image.getScaledInstance(this.CARD_WIDTH, this.CARD_HEIGHT,
							java.awt.Image.SCALE_SMOOTH);
					jButton.setIcon(new ImageIcon(scaledImage));
				}
				else
				{
					Image image = ReadFiles.getCardImage(card);
					Image scaledImage = image.getScaledInstance(this.CARD_WIDTH, this.CARD_HEIGHT,
							java.awt.Image.SCALE_SMOOTH);
					jButton.setIcon(new ImageIcon(scaledImage));
				}
				
				this.repaintPanel();
			}
		}
	}
	
	private void initializeButtons()
	{
		int xBound = 20;
		int yBound = 20;
		
		// north player
		jButtonNorthList = new ArrayList<JButton>();
		jButtonSouthList = new ArrayList<JButton>();
		jButtonWestList = new ArrayList<JButton>();
		jButtonEastList = new ArrayList<JButton>();
		
		Player player = null;
		boolean addActionListener = false;
		for (Position position : Position.values())
		{
			switch (position)
			{
				case North:
					if(players != null)
						player = players.get(2);
					xBound = 20;
					yBound = 30;
					addActionListener = false;
					break;
				case South:
					if(players != null)
						player = players.get(0);
					xBound = 20;
					yBound = panel.getHeight() - 60 - this.CARD_HEIGHT;
					addActionListener = true;
					break;
				case West:
					if(players != null)
						player = players.get(1);
					xBound = 20;
					yBound = 20 + 140;
					addActionListener = false;
					break;
				case East:
					if(players != null)
						player = players.get(3);
					xBound = panel.getWidth() - 40 - this.CARD_WIDTH;
					yBound = 20 + 140;
					addActionListener = false;
					break;
			}
			
			List<Card> suitHand = getHandGroupBySuit(player.getHand());
			for (int i = 0; i < 13; i++)
			{
				addButton(xBound, yBound, this.CARD_WIDTH, this.CARD_HEIGHT,
						position, suitHand.get(i), addActionListener);
				
				// increase bound
				switch (position)
				{
					case North:
						xBound += 50;
						break;
					case South:
						xBound += 50;
						break;
					case West:
						yBound += 20;
						break;
					case East:
						yBound += 20;
						break;
				}
			}
		}
	}
	
	private List<Card> getHandGroupBySuit(List<Card> hand)
	{
		List<Card> suitHand = new ArrayList<Card>(hand.size());
		
		for (int i = 0; i < hand.size(); i++) 
		{
			if(hand.get(i).getSuit() == Suit.Clubs)
			{
				suitHand.add(hand.get(i));
			}
		}
		
		for (int i = 0; i < hand.size(); i++) 
		{
			if(hand.get(i).getSuit() == Suit.Diamonds)
			{
				suitHand.add(hand.get(i));
			}
		}
		
		for (int i = 0; i < hand.size(); i++) 
		{
			if(hand.get(i).getSuit() == Suit.Spades)
			{
				suitHand.add(hand.get(i));
			}
		}
		
		for (int i = 0; i < hand.size(); i++) 
		{
			if(hand.get(i).getSuit() == Suit.Hearts)
			{
				suitHand.add(hand.get(i));
			}
		}
		
		return suitHand;
	}
	
	private void addButton(int xBound, int yBound, int width, int height,
			Position position, Card card, boolean addActionListener)
	{
		List<JButton> jButtonList = getJButtonList(position);
		JButton jButton = new JButton();
		jButton.putClientProperty("ButtonType", "CardButton");
		jButton.putClientProperty("Card", card);
		jButton.putClientProperty("Position", position);
		jButton.putClientProperty("Selected", false);
		
		if(addActionListener)
			jButton.addActionListener(heartsController);
		
		jButton.setBounds(xBound, yBound, width, height);
		
		if(position != Position.South && !this.SHOW_OPPONENT_CARDS)
		{
			Image image = ReadFiles.getImage("cards\\back_blue_potrait.png");
			Image scaledImage = image.getScaledInstance(width, height,
					java.awt.Image.SCALE_SMOOTH);
			jButton.setIcon(new ImageIcon(scaledImage));
		}
		else
		{
			Image image = ReadFiles.getCardImage(card);
			Image scaledImage = image.getScaledInstance(width, height,
					java.awt.Image.SCALE_SMOOTH);
			jButton.setIcon(new ImageIcon(scaledImage));
		}
		panel.add(jButton);
		jButtonList.add(jButton);
		
		this.repaintPanel();
	}
	
	public void showPlayedCardButton(Position position, Card card)
	{
		JButton jButton = null;
		switch (position)
		{
			case North:
				jButton = this.northCardButton;
				break;
			case South:
				jButton = this.southCardButton;
				break;
			case East:
				jButton = this.eastCardButton;
				break;
			case West:
				jButton = this.westCardButton;
				break;
		}
		
		Image image = ReadFiles.getCardImage(card);
		Image scaledImage = image.getScaledInstance(jButton.getWidth(),
				jButton.getHeight(), java.awt.Image.SCALE_SMOOTH);
		jButton.setIcon(new ImageIcon(scaledImage));
		jButton.setVisible(true);
		
		this.repaintPanel();
	}
	
	public void hideAllPlayedCardButtons()
	{
		this.northCardButton.setVisible(false);
		this.southCardButton.setVisible(false);
		this.eastCardButton.setVisible(false);
		this.westCardButton.setVisible(false);
		
		this.repaintPanel();
	}
	
	
	
	public JButton findButton(Position position, Card card)
	{
		List<JButton> buttonList = null;
		switch (position)
		{
			case North:
				buttonList = this.jButtonNorthList;
				break;
			case South:
				buttonList = this.jButtonSouthList;
				break;
			case East:
				buttonList = this.jButtonEastList;
				break;
			case West:
				buttonList = this.jButtonWestList;
				break;
		}
		
		for (int i = 0; i < buttonList.size(); i++)
		{
			Card buttonCard = (Card) buttonList.get(i)
					.getClientProperty("Card");
			if(buttonCard.equals(card))
				return buttonList.get(i);
		}
		
		return null;
	}
	
	public void removeButton(JButton jButton, Position position)
	{
		List<JButton> jButtonList = this.getJButtonList(position);
		int index = jButtonList.indexOf(jButton);
		int listSize = jButtonList.size();
		
		// remove
		jButtonList.remove(jButton);
		panel.remove(jButton);
		
		// if this is not the last card, rearrange
		if(index < jButtonList.size() || jButtonList.size() == 1)
		{
			for (int i = index; i < jButtonList.size(); i++)
			{
				jButton = jButtonList.get(i);
				switch (position)
				{
					case North:
						jButton.setLocation(jButton.getX() - 50, jButton.getY());
						break;
					case South:
						jButton.setLocation(jButton.getX() - 50, jButton.getY());
						break;
					case West:
						jButton.setLocation(jButton.getX(), jButton.getY() - 20);
						break;
					case East:
						jButton.setLocation(jButton.getX(), jButton.getY() - 20);
						break;
				}
			}
		}
		
		this.repaintPanel();
	}
	
	public void setCardSelected(JButton button)
	{
		button.setLocation(button.getX(), button.getY() - 20);
		button.putClientProperty("Selected", true);
	}
	
	public void setCardUnselected(JButton button)
	{
		button.setLocation(button.getX(), button.getY() + 20);
		button.putClientProperty("Selected", false);
	}
	
	public void setPlayedCardVisible(Position position, boolean flag)
	{
		switch (position)
		{
			case North:
				this.northCardButton.setVisible(flag);
				break;
			case South:
				this.southCardButton.setVisible(flag);
				break;
			case West:
				this.westCardButton.setVisible(flag);
				break;
			case East:
				this.eastCardButton.setVisible(flag);
				break;
		}
		
		this.repaintPanel();
	}
	
	private void repaintPanel()
	{
		panel.paintImmediately(0, 0, this.PANEL_WIDTH, this.PANEL_HEIGHT);
	}
}
