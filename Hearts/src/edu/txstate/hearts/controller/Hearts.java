/**
 * 
 */
package edu.txstate.hearts.controller;

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
		deck.printCards();
	}
	
	// we will need to add the stuff for managing the game, determining
	// whose turn it is, what they can play, etc.

}
