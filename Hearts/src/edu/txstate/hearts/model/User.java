/**
 * 
 */
package edu.txstate.hearts.model;

import edu.txstate.hearts.model.Achievements;

/**
 * @author Neil Stickels, I Gede Sutapa
 *
 */
public class User extends Player {

	public User(String playerName) {
		super(playerName);
		// TODO Auto-generated constructor stub
	}

	private Achievements achievements;
	
	public Card playCard()
	{
		// this is where we need to implement how a human player plays a card
		return null;
	}

}
