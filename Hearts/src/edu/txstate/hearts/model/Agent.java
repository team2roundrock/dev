/**
 * 
 */
package edu.txstate.hearts.model;

import java.util.List;

/**
 * @author Neil Stickels, I Gede Sutapa
 *
 */
public class Agent extends Player {

	public Agent(String playerName) {
		super(playerName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Card playCard(List<Card> cardsPlayed, boolean heartsBroken, boolean veryFirstTurn) {
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
		} else
		{
			List<Card> playable = getLegalCards(cardsPlayed, heartsBroken);
			int index = getRand().nextInt(playable.size());
			cardToPlay = playable.get(index);
		}
		getHand().remove(cardToPlay);
		return cardToPlay;
	}

}
