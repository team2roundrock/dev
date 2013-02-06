/**
 * 
 */
package edu.txstate.hearts.controller;

import java.util.ArrayList;
import java.util.List;

import edu.txstate.hearts.model.*;

/**
 * @author Neil Stickels
 *
 */
public class Hearts {

	private static Deck deck;
	private static List<Player> players;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		deck = new Deck();
		deck.shuffleCards();
		deck.printCards();
		System.out.println("");
		
		players = new ArrayList<Player>();
		Player player1 = new User("Gede");
		Player player2 = new User("Neil");
		Player player3 = new User("Jonathan");
		Player player4 = new User("Maria");
		
		players.add(player1);
		players.add(player2);
		players.add(player3);
		players.add(player4);
		
		for(int i = 0; i < 13; i++)
		{
			for(int j = 0; j < players.size(); j++)
			{
				players.get(j).addCard(deck.dealCard());
			}
		}
		
		for(int i = 0; i < players.size(); i++)
		{
			Player player = players.get(i);
			System.out.println(player.getName());
			player.printHand();
			System.out.println("");
			System.out.println("");
		}
		
	}
	
	// we will need to add the stuff for managing the game, determining
	// whose turn it is, what they can play, etc.

}
