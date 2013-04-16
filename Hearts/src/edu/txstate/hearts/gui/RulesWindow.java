package edu.txstate.hearts.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextPane;
import javax.swing.JTextArea;
import java.awt.Font;
import java.awt.Color;
import javax.swing.UIManager;

import edu.txstate.hearts.utils.ReadFiles;

/**
 * This class displays the rules of the Hearts card game. This window will be accessed from the
 * main Hearts window. This class allows the users to have access to the rules of the game whenever needed.
 * @author Maria Poole
 *
 */
public class RulesWindow extends JFrame 
{
	private JPanel contentPane;

	
	/**
	 * Create the frame.
	 */
	public RulesWindow() 
	{
		setForeground(Color.LIGHT_GRAY);
		setTitle("Hearts - rules");
		setBounds(100, 100, 383, 423);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTextArea txtrRulesOfThe = new JTextArea();
		txtrRulesOfThe.setBackground(UIManager.getColor("Button.background"));
		txtrRulesOfThe.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));
		txtrRulesOfThe.setWrapStyleWord(true);
		txtrRulesOfThe.setLineWrap(true);
		txtrRulesOfThe.setText("                            Rules of the Hearts Game\r\n\r\n* The Hearts game is played with a single deck of 52 cards.\r\n\r\n* Each player receives a hand of 13 cards.\r\n\r\n* On the first hand players will pass three cards of their choosing to their opponent starting    with the player on the left, on the second hand cards will be passed to the player on the      right, on the third hand to the player directly across, and on the fourth hand no cards will    be passed.\r\n\r\n* To start the game, the player holding the two of clubs will make the first move.\r\n\r\n* When a card has been played, the rest of the players must follow with a card of the same     suit. If a player does not have a card of the same suit, he can play a card of any suit except    for the first hand where a heart or the queen of spades cannot be played.\r\n\r\n* After the first hand, players are allowed to play a heart or the queen of spades.\r\n");
		txtrRulesOfThe.setBounds(28, 11, 311, 363);
		contentPane.add(txtrRulesOfThe);
		
		Image image = ReadFiles.getImage("heart.png");
		this.setIconImage(image);
	}
	
	public void showDialog() 
	{
		try 
		{
			this.setLocationRelativeTo(null);
			this.setVisible(true);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}
