/**
 * 
 */
package edu.txstate.hearts.model;

import java.util.List;

import edu.txstate.hearts.controller.Hearts.Passing;
import edu.txstate.hearts.model.Achievements;

/**
 * @author Neil Stickels, I Gede Sutapa
 *
 */
public class User extends Player 
{
	private Achievements achievements;
	
	public User(String playerName) 
	{
		super(playerName);
		this.achievements = new Achievements();
	}

	@Override
	public Card playCard(List<Card> cardsPlayed, boolean heartsBroken, boolean veryFirstTurn) {
		// TODO Implement human player play the card
		return null;
	}

	@Override
	public List<Card> getCardsToPass(Passing passing) {
		// TODO Implement human player pick cards to pass
		return null;
	}
}
