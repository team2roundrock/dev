package edu.txstate.hearts.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Implementation of agent goofy
 * 
 * @author I Gede Sutapa, Maria Poole
 */
public class AgentGoofy extends Agent
{
	/**
	 * Constructor
	 * 
	 * @param name	agent goofy name
	 * @param num	position index
	 */
	public AgentGoofy(String name, int num)
	{
		super(name, num);
	}

	public Card playCard(List<Card> cardsPlayed, boolean heartsBroken, boolean veryFirstTurn)
	{
		Card cardToPlay = null;
		if (veryFirstTurn)
		{
			cardToPlay = super.playTwoOfClub();
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

	/**
	 * @see edu.txstate.hearts.model.Agent#getCardsToPass()
	 */
	public List<Card> getCardsToPass()
	{
		int numCardsToPass = 3;
		List<Card> cardsToPass = new ArrayList<Card>();
		List<Card> myHand = this.getHand();
		Collections.sort(myHand, new CardComparator());

		for (int i = myHand.size() - 1; i >= 13 - numCardsToPass; i--)
		{
			cardsToPass.add(myHand.get(i));
			getHand().remove(myHand.get(i));
		}
		return cardsToPass;
	}
}