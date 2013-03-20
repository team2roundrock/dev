/**
 * 
 */
package edu.txstate.hearts.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.txstate.hearts.controller.Hearts.Passing;

/**
 * @author Neil Stickels, I Gede Sutapa
 *
 */
public abstract class Agent extends Player 
{
	protected Agent(String playerName, int num) 
	{
		super(playerName, num);
	}

	@Override
	public abstract Card playCard(List<Card> cardsPlayed, boolean heartsBroken, boolean veryFirstTurn);

	@Override
	public List<Card> getCardsToPass(Passing passing)
	{
		return getCardsToPass();
	}
	
	public abstract List<Card> getCardsToPass();

	public Card playTwoOfClub()
	{
		Card cardToPlay = null;
		List<Card> hand = getHand();
		for(int i = 0; i < hand.size(); i++)
		{
			Card c = hand.get(i);
			if ((c.getSuit() == Card.Suit.Clubs)
					&& (c.getFace() == Card.Face.Deuce))
			{
				cardToPlay = c;
			}
		}
		
		return cardToPlay;
	}
}
