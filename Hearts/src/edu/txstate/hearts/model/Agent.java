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
		// TODO this is where the AI for a computer player will go
		Card cardToPlay = null;
		if(veryFirstTurn)
		{
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
		} 
		else
		{
			List<Card> playable = getLegalCards(cardsPlayed, heartsBroken);
			int index = getRand().nextInt(playable.size());
			cardToPlay = playable.get(index);
		}
		getHand().remove(cardToPlay);
		return cardToPlay;
	}

	@Override
	public List<Card> getCardsToPass() 
	{
		int numCardsToPass = 3;
		List<Card> cardsToPass = new ArrayList<Card>();
		
		//TODO: improve logic to select cards to pass
		//for now, just pick the top 3 highest cards
		List<Card> myHand = this.getHand();
		Collections.sort(myHand, new CardComparator());
		
		for(int i = myHand.size() - 1; i >= 13 - numCardsToPass; i--)
		{
			cardsToPass.add(myHand.get(i));
			getHand().remove(myHand.get(i));
		}
		return cardsToPass;
	}
}
