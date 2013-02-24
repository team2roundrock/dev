package edu.txstate.hearts.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import edu.txstate.hearts.controller.Hearts.Passing;
import edu.txstate.hearts.model.Card.Suit;
import edu.txstate.hearts.utils.ProbabilityUtils;
import edu.txstate.hearts.utils.RiskThresholds;

public class AgentAggressive extends Agent{
	
	RiskThresholds thresholds = new RiskThresholds();
	boolean hasQueenOfSpades = false;

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
			Card card = myHand.get(i);
			if ((card.getSuit() == Card.Suit.Spades)
					&& (card.getFace() == Card.Face.Queen))
			{
				hasQueenOfSpades = false;
			}
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
		Card cardToPlay = null;
		if(veryFirstTurn)
		{
			cardToPlay = super.playTwoOfClub();
		} 
		else
		{
			if (cardsPlayed.size() > 0)
			{
				Suit suitLed = cardsPlayed.get(0).getSuit();
				if (!hasAnyOfSuit(suitLed))
				{
					if (hasQueenOfSpades)
					{
						Iterator<Card> iterator = getHand().iterator();
						while (iterator.hasNext())
						{
							Card card = iterator.next();
							if ((card.getSuit() == Card.Suit.Spades)
									&& (card.getFace() == Card.Face.Queen))
							{
								cardToPlay = card;
								hasQueenOfSpades = false;
							}
						}
					} 
					else
					{
						List<Card> hearts = getAllOfSuit(Suit.Hearts);
						List<Card> spades = getAllOfSuit(Suit.Spades);
						List<Card> diamonds = getAllOfSuit(Suit.Diamonds);
						List<Card> clubs = getAllOfSuit(Suit.Clubs);
						if (hearts.size() > 0)
						{
							Collections.sort(hearts, new CardComparator());
							cardToPlay = hearts.get(0);
						}
						else
						{
							List<Card> fewestCards = getCardsFromSuitWithFewest(
									spades, diamonds, clubs);
							cardToPlay = fewestCards.get(0);
							
							
						}
					}
						
				}
				else //do have card of suit on table
				{
					List<Card> playableCards = getAllOfSuit(suitLed);
					Collections.sort(playableCards, new CardComparator());
					boolean searching = true;
					int counter = 0;
					double canPlayHearts = ProbabilityUtils.getProbabilityNoneOfSuitAndHasHearts(getHand(), suitLed, getPlayedCards(), getInPlayCards(), getKnownCards(), isQosPlayed());
					while (searching)
					{
						Card test = playableCards.get(counter++);
						double cardWins = getProbCardWins(test);
						double risk = canPlayHearts*cardWins;
						System.out.println(test + "risk is " + risk + " and threshold is " + thresholds.getThreshold());
						if (risk < thresholds.getThreshold())
						{
							searching = false;
							cardToPlay = test;
						}
						else
						{
							if (counter == playableCards.size())
							{
								cardToPlay = playableCards.get(0);
								searching = false;
							}
						}
							
					}
					//playableCards.get(0);
					
				}
			}
			else //no cards played
			{
				List<Card> hearts = getAllOfSuit(Suit.Hearts);
				List<Card> spades = getAllOfSuit(Suit.Spades);
				List<Card> diamonds = getAllOfSuit(Suit.Diamonds);
				List<Card> clubs = getAllOfSuit(Suit.Clubs);
				
				List<Card> fewestCards = getCardsFromSuitWithFewest(
						spades, diamonds, clubs);
				
				if (fewestCards == null)
					fewestCards = hearts;
				
				Collections.sort(fewestCards, new CardComparator());
				boolean searching = true;
				int counter = 0;
				double canPlayHearts = ProbabilityUtils.getProbabilityNoneOfSuitAndHasHearts(getHand(), fewestCards.get(0).getSuit(), getPlayedCards(), getInPlayCards(), getKnownCards(), isQosPlayed());
				while (searching)
				{
					Card test = fewestCards.get(counter++);
					double cardWins = getProbCardWins(test);
					double risk = canPlayHearts*cardWins;
					System.out.println(test + "risk is " + risk + " and threshold is " + thresholds.getThreshold());
					if (risk < thresholds.getThreshold())
					{
						searching = false;
						cardToPlay = test;
					}
					else
					{
						if (counter == fewestCards.size())
						{
							cardToPlay = fewestCards.get(0);
							searching = false;
						}
					}
						
				}		
				
				
			}
		}
		getHand().remove(cardToPlay);
		
		if ((cardToPlay.getSuit() == Card.Suit.Spades)
				&& (cardToPlay.getFace() == Card.Face.Queen))
		{
			hasQueenOfSpades = false;
		}
		
		return cardToPlay;
	}

	/**
	 * @param spades
	 * @param diamonds
	 * @param clubs
	 * @return
	 */
	private List<Card> getCardsFromSuitWithFewest(List<Card> spades,
			List<Card> diamonds, List<Card> clubs) {
		List<Card> fewestCards = null;
		int fewest = 14;
		Collections.sort(clubs, new CardComparator());
		Collections.sort(diamonds, new CardComparator());
		Collections.sort(spades, new CardComparator());
		if (clubs.size() > 0 && clubs.size() < fewest)
		{
			fewest = clubs.size();
			fewestCards = clubs;
		}
		if (spades.size() > 0 && spades.size() <= fewest)
		{
			if (fewest == spades.size())
			{
				if(spades.get(0).getFace().ordinal() > fewestCards.get(0).getFace().ordinal())
				{
					fewest = spades.size();
					fewestCards = spades;
				}
			}
			else
			{
				fewest = spades.size();
				fewestCards = spades;
			}
			//fewestCards = spades;
		}
		if (diamonds.size() > 0 && diamonds.size() <= fewest)
		{
			if (fewest == diamonds.size())
			{
				if(diamonds.get(0).getFace().ordinal() > fewestCards.get(0).getFace().ordinal())
				{
					fewest = diamonds.size();
					fewestCards = diamonds;
				}
			}
			else
			{
				fewest = diamonds.size();
				fewestCards = diamonds;
			}
			
		}
		return fewestCards;
	}

	private double getProbCardWins(Card test) {
		List<Integer> availableCards = new ArrayList<Integer>();
		for (int i = 0; i < 13; i++)
		{
			availableCards.add(i);
		}
		Iterator<Card> iterator = getHand().iterator();
		while (iterator.hasNext())
		{
			Card card = iterator.next();
			if (card.getSuit() == test.getSuit())
					availableCards.set(card.getFace().ordinal(),null);
		}
		Iterator<Card> iterator2 = getPlayedCards().iterator();
		while (iterator2.hasNext())
		{
			Card card = iterator2.next();
			if (card.getSuit() == test.getSuit())
					availableCards.set(card.getFace().ordinal(),null);
		}
		
		List<Integer> newAvailableCards = new ArrayList<Integer>();
		for (int i = 0; i < 13; i++)
		{
			if (availableCards.get(i) != null)
			{
				newAvailableCards.add(i);
			}
		}
		availableCards = newAvailableCards;	
		
		if (availableCards.size() == 0)
			return 1;
		
		int counter = 0;
		for (int i = 0; i < availableCards.size(); i++)
		{
			if (availableCards.get(i) < test.getFace().ordinal())
				counter++;
		}
		return 1-(counter/availableCards.size());
	}

	private List<Card> getAllOfSuit(Suit suit) {
		
		List<Card> cardsOfSuit = new ArrayList<Card>();
		Iterator<Card> iterator = getHand().iterator();
		while (iterator.hasNext())
		{
			Card card = iterator.next();
			if (card.getSuit() == suit)
					cardsOfSuit.add(card);
		}
		return cardsOfSuit;
	}

	private boolean hasAnyOfSuit(Suit suitLed) {
		Iterator<Card> iterator = getHand().iterator();
		while (iterator.hasNext())
		{
			Card c = iterator.next();
			if (c.getSuit() == suitLed)
				return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see edu.txstate.hearts.model.Player#addCard(edu.txstate.hearts.model.Card)
	 */
	@Override
	public void addCard(Card card) {
		// TODO Auto-generated method stub
		super.addCard(card);
		if ((card.getSuit() == Card.Suit.Spades)
				&& (card.getFace() == Card.Face.Queen))
		{
			hasQueenOfSpades = true;
		}
	}

	@Override
	public List<Card> getCardsToPass() {
		// we aren't actually using this, since we actually implemented the real algorithm
		throw new UnsupportedOperationException("This method shouldn't have gotten called");
	}
	
	

}
