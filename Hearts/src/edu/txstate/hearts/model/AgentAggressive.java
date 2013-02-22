package edu.txstate.hearts.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import edu.txstate.hearts.controller.Hearts.Passing;

public class AgentAggressive extends Agent{

	public AgentAggressive(String playerName) {
		super(playerName);
		// TODO Auto-generated constructor stub
	}
	
	/* (non-Javadoc)
	 * @see edu.txstate.hearts.model.Agent#getCardsToPass(Passing)
	 */
	@Override
	public List<Card> getCardsToPass(Passing passing) {
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
		Set<Card> p1Set = getKnownCards().get(0);
		Set<Card> p2Set = getKnownCards().get(1);
		Set<Card> p3Set = getKnownCards().get(2);
		
		if(passing == Passing.Left)
			p1Set.addAll(cardsToPass);
		else if(passing == Passing.Front)
			p2Set.addAll(cardsToPass);
		else if(passing == Passing.Right)
			p3Set.addAll(cardsToPass);
		
		return cardsToPass;
	}

	@Override
	public Card playCard(List<Card> cardsPlayed, boolean heartsBroken,
			boolean veryFirstTurn) {
		// TODO We still need to actually implement this method
		return null;
	}

	@Override
	public List<Card> getCardsToPass() {
		// we aren't actually using this, since we actually implemented the real algorithm
		throw new UnsupportedOperationException("This method shouldn't have gotten called");
	}
	
	

}
