package edu.txstate.hearts.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AgentGoofy extends Agent {

	public AgentGoofy(String playerName, int num) {
		super(playerName, num);
		// TODO Auto-generated constructor stub
	}
	
	public Card playCard(List<Card> cardsPlayed, boolean heartsBroken, boolean veryFirstTurn) 
	{
		Card cardToPlay = null;
		if(veryFirstTurn)
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
