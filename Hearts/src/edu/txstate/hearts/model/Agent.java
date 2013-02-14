/**
 * 
 */
package edu.txstate.hearts.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Neil Stickels, I Gede Sutapa
 *
 */
public class Agent extends Player 
{
	public Agent(String playerName) 
	{
		super(playerName);
	}

	@Override
	public Card playCard(List<Card> cardsPlayed, boolean heartsBroken, boolean veryFirstTurn) 
	{
		return null;
	}

	@Override
	public List<Card> getCardsToPass() 
	{
		return null;
	}

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
