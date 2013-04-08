package edu.txstate.hearts.gui;

import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.txstate.hearts.controller.Hearts;
import edu.txstate.hearts.model.*;

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
	private String imagesFolder = "images\\cards\\";
	
	public void showDialog()
	{
		try
		{
			frame.setVisible(true);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public HeartsUI()
	{
		// javax.swing.SwingUtilities.invokeLater(new Runnable() {
		// public void run() {
		// panel.repaint();
		// }
		// });
	}
	
	public HeartsUI(List<Player> players)
	{
		this.players = players;
		initialize();
	}
	
	public void setPlayers(List<Player> players)
	{
		this.players = players;
	}
	
	public void setUI()
	{
		initialize();
	}
	
	public void addController(Hearts heartsController)
	{
		this.heartsController = heartsController;
	}
	
	private void initialize()
	{
		// dialog = new JDialog();
		// dialog.setSize(720, 600);
		// dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		frame = new JFrame();
		frame.setSize(740, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel = new JPanel();
		panel.setLayout(null);
		panel.setBounds(0, 0, 720, 600);
		
		// add pass button
		JButton passButton = new JButton();
		passButton.setBounds(panel.getWidth() / 2 + 60,
				panel.getHeight() - 120, 100, 30);
		passButton.setText("PASS");
		passButton.setVisible(false);
		passButton.putClientProperty("ButtonType", "PassButton");
		passButton.addActionListener(this.heartsController);
		panel.add(passButton);
		this.passButton = passButton;
		
		this.initializePlayingButtons();
		this.initializeBalloonTip();
		// initializeButtons();
		
		// dialog.add(panel);
		//
		// dialog.addWindowListener(new WindowAdapter()
		// {
		// public void windowClosing(WindowEvent e)
		// {
		// System.exit(0);
		// }
		// });
		
		frame.add(panel);
		
		frame.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}
		});
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
		panel.paintImmediately(0, 0, 720, 660);
		
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
		panel.paintImmediately(0, 0, 720, 660);
	}
	
	private void initializeBalloonTip()
	{
		JTextField jTextField = new JTextField();
		jTextField.setBounds(20, panel.getHeight(), 300, 30);
		panel.add(jTextField);
		this.balloonTextField = jTextField;
		this.balloonTextField.setVisible(false);
	}
	
	private void initializePlayingButtons()
	{
		int xBound = 20;
		int yBound = 20;
		int jButtonWidth = 60;
		int jButtonHeight = 80;
		
		// middle 4 cardsJButton jButton = new JButton();
		northCardButton = new JButton();
		northCardButton.setBounds(panel.getWidth() / 2 - 40, yBound + 120,
				jButtonWidth, jButtonHeight);
		panel.add(northCardButton);
		this.northCardButton.setVisible(false);
		
		southCardButton = new JButton();
		southCardButton.setBounds(panel.getWidth() / 2 - 40,
				panel.getHeight() - 240, jButtonWidth, jButtonHeight);
		panel.add(southCardButton);
		this.southCardButton.setVisible(false);
		
		westCardButton = new JButton();
		westCardButton.setBounds(xBound + 180, panel.getHeight() / 2 - 60,
				jButtonWidth, jButtonHeight);
		panel.add(westCardButton);
		this.westCardButton.setVisible(false);
		
		eastCardButton = new JButton();
		eastCardButton.setBounds(panel.getWidth() - 280,
				panel.getHeight() / 2 - 60, jButtonWidth, jButtonHeight);
		panel.add(eastCardButton);
		this.eastCardButton.setVisible(false);
		
	}
	
	public void redrawCards()
	{
		int width = 60;
		int height = 80;
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
			for (int i = 0; i < 13; i++)
			{
				Card card = player.getHand().get(i);
				JButton jButton = jButtonList.get(i);
				jButton.putClientProperty("ButtonType", "CardButton");
				jButton.putClientProperty("Card", card);
				jButton.putClientProperty("Position", position);
				jButton.putClientProperty("Selected", false);
				
				Image image = GetImage(card);
				Image scaledImage = image.getScaledInstance(width, height,
						java.awt.Image.SCALE_SMOOTH);
				jButton.setIcon(new ImageIcon(scaledImage));
				
				// new
				panel.paintImmediately(0, 0, 720, 620);
				// panel.revalidate();
				// panel.repaint();
			}
		}
	}
	
	private void initializeButtons()
	{
		int xBound = 20;
		int yBound = 20;
		int jButtonWidth = 60;
		int jButtonHeight = 80;
		
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
					yBound = 40;
					addActionListener = false;
					break;
				case South:
					if(players != null)
						player = players.get(0);
					xBound = 20;
					yBound = panel.getHeight() - 60 - jButtonHeight;
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
					xBound = panel.getWidth() - 40 - jButtonWidth;
					yBound = 20 + 140;
					addActionListener = false;
					break;
			}
			
			for (int i = 0; i < 13; i++)
			{
				addButton(xBound, yBound, jButtonWidth, jButtonHeight,
						position, player.getHand().get(i), addActionListener);
				
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
		Image image = GetImage(card);
		Image scaledImage = image.getScaledInstance(width, height,
				java.awt.Image.SCALE_SMOOTH);
		jButton.setIcon(new ImageIcon(scaledImage));
		panel.add(jButton);
		jButtonList.add(jButton);
		
		// new
		panel.paintImmediately(0, 0, 720, 620);
		
		// panel.revalidate();
		// panel.repaint();
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
		
		Image image = GetImage(card);
		Image scaledImage = image.getScaledInstance(jButton.getWidth(),
				jButton.getHeight(), java.awt.Image.SCALE_SMOOTH);
		jButton.setIcon(new ImageIcon(scaledImage));
		jButton.setVisible(true);
		
		// panel.revalidate();
		// panel.repaint();
		panel.paintImmediately(0, 0, 720, 620);
	}
	
	public void hideAllPlayedCardButtons()
	{
		this.northCardButton.setVisible(false);
		this.southCardButton.setVisible(false);
		this.eastCardButton.setVisible(false);
		this.westCardButton.setVisible(false);
		
		panel.paintImmediately(0, 0, 720, 620);
		// panel.revalidate();
		// panel.repaint();
		// panel.paintComponents(panel.getGraphics());
	}
	
	private Image GetImage(Card card)
	{
		String suitName = "";
		String faceName = "";
		
		switch (card.getSuit())
		{
			case Clubs:
				suitName = "c";
				break;
			case Diamonds:
				suitName = "d";
				break;
			case Hearts:
				suitName = "h";
				break;
			case Spades:
				suitName = "s";
				break;
		}
		
		switch (card.getFace())
		{
			case Deuce:
				faceName = "2";
				break;
			case Three:
				faceName = "3";
				break;
			case Four:
				faceName = "4";
				break;
			case Five:
				faceName = "5";
				break;
			case Six:
				faceName = "6";
				break;
			case Seven:
				faceName = "7";
				break;
			case Eight:
				faceName = "8";
				break;
			case Nine:
				faceName = "9";
				break;
			case Ten:
				faceName = "t";
				break;
			case Jack:
				faceName = "j";
				break;
			case Queen:
				faceName = "q";
				break;
			case King:
				faceName = "k";
				break;
			case Ace:
				faceName = "a";
				break;
		}
		
		String fileName = faceName + suitName + ".gif";
		String fullFileName = imagesFolder + fileName;
		
		Image image = null;
		try
		{
			image = ImageIO.read(new File(fullFileName));
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return image;
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
		
		// new
		panel.paintImmediately(0, 0, 720, 620);
		// panel.revalidate();
		// panel.repaint();
		// panel.paintComponents(panel.getGraphics());
	}
	
	public void setButtonImage(int cardIndex, Card card)
	{
		JButton jButton = jButtonSouthList.get(cardIndex);
		try
		{
			Image img = ImageIO.read(new File("5s.gif"));
			Image resizedImage = img.getScaledInstance(60, 80, 0);
			jButton.setIcon(new ImageIcon(resizedImage));
		}
		catch (Exception ex)
		{
		}
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
		
		// panel.revalidate();
		// panel.repaint();
		
		// new
		panel.paintImmediately(0, 0, 720, 620);
		// panel.paintComponents(panel.getGraphics());
	}
}
