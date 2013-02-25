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

/**
 * Contains rules & strategies for the most difficult AI agent
 * 
 * @author Neil Stickels, Jonathan Shelton
 *
 */
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

	/* (non-Javadoc)
	 * @see edu.txstate.hearts.model.Agent#playCard(java.util.List, boolean, boolean)
	 */
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
				if (!hasAnyOfSuit(suitLed)) // doesn't have suit played on table
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
					else //no queen of spades, no suit on table
					{
						List<Card> hearts = getAllOfSuit(Suit.Hearts);
						List<Card> spades = getAllOfSuit(Suit.Spades);
						List<Card> diamonds = getAllOfSuit(Suit.Diamonds);
						List<Card> clubs = getAllOfSuit(Suit.Clubs);
						if (hearts.size() > 0) //has hearts in hand
						{
							Collections.sort(hearts, new CardComparator());
							Collections.reverse(hearts); //Every call to reverse ensures '0' is the highest card
							cardToPlay = hearts.get(0);
						}
						else //doesn't have any hearts
						{
							List<Card> fewestCards = getCardsFromSuitWithFewest(
									spades, diamonds, clubs);
							cardToPlay = fewestCards.get(0);
						}
					}
						
				}
				else //does have suit played on table
				{
					List<Card> playableCards = getAllOfSuit(suitLed);
					Collections.sort(playableCards, new CardComparator());
					Collections.reverse(playableCards);
					boolean searching = true;
					int counter = 0;
					double canPlayHearts = ProbabilityUtils.getProbabilityNoneOfSuitAndHasHearts(getHand(), suitLed, getPlayedCards(), getInPlayCards(), getKnownCards(), isQosPlayed());
					while (searching) //search all cards
					{
						Card test = playableCards.get(counter++);
						double cardWins = getProbCardWins(test);
						double risk = canPlayHearts*cardWins;
						System.out.println("EVALUATING..." + test + ": risk is " + risk + " and threshold is " + thresholds.getThresholds());
						if (risk < thresholds.getThresholds())
						{
							searching = false;
							cardToPlay = test;
						}
						else //there is a risk of getting hearts
						/* TODO: We may want to re-look at this. What about choosing the lowest risk that is above the threshold, after hearts is broken?
								Scenario: 1st player plays 3 of clubs. Aggressive has 4, 8, jack of clubs. If aggressive plays jack, it is taking on
										  substantially more risk than if it played the 4. Unless it is able to determine that at least one remaining player
										  has no remaining clubs. */
						{
							if (counter == playableCards.size()) //all cards of smallest suite have been analyzed
							{
								searching = false;
								cardToPlay = playableCards.get(0); //just play the highest if at risk
							}
						}
							
					}
				}
			}
			/* TODO Ensure agent doesn't always default to playing highest card when going first. Especially after hearts broken.
			        Particularly in this case, the highest card in a suit does not HAVE to be played if there is high risk.
			        Might want to consider evaluating all suits as well, not necessarily suit with the fewest */
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
				Collections.reverse(fewestCards);
				boolean searching = true;
				int counter = 0;
				double canPlayHearts = ProbabilityUtils.getProbabilityNoneOfSuitAndHasHearts(getHand(), fewestCards.get(0).getSuit(), getPlayedCards(), getInPlayCards(), getKnownCards(), isQosPlayed());
				while (searching) //search all cards
				{
					Card test = fewestCards.get(counter++);
					double cardWins = getProbCardWins(test);
					double risk = canPlayHearts*cardWins;
					System.out.println("EVALUATING..." + test + ": risk is " + risk + " and threshold is " + thresholds.getThresholds());
					if (risk < thresholds.getThresholds())
					{
						cardToPlay = test;
						searching = false;
					}
					else //there is a risk of getting hearts
					{
						if (counter == fewestCards.size()) //all cards of smallest suite have been analyzed
						{
							cardToPlay = fewestCards.get(0); //just play the highest if at risk
							searching = false;
						}
					}		
				}		
			}
		}
		getHand().remove(cardToPlay);
		
		if ((cardToPlay.getSuit() == Card.Suit.Spades) && (cardToPlay.getFace() == Card.Face.Queen))
			hasQueenOfSpades = false;
		
		return cardToPlay;
	}

	/**
	 * This method will find the suit with the lowest number of cards,
	 * aligning with a strategy of removing as many suits as possible.
	 * 
	 * @param spades
	 * @param diamonds
	 * @param clubs
	 * @return fewestCards, which is populated with the suit that has the
	 * least cards
	 */
	private List<Card> getCardsFromSuitWithFewest(List<Card> spades,
			List<Card> diamonds, List<Card> clubs) {
		List<Card> fewestCards = null;
		int fewest = 14;
		Collections.sort(clubs, new CardComparator());
		Collections.reverse(clubs);
		Collections.sort(diamonds, new CardComparator());
		Collections.reverse(diamonds);
		Collections.sort(spades, new CardComparator());
		Collections.reverse(spades);
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

	/**
	 * Get the probability that a card will win
	 * @param test
	 * @return probability that the passed in card will win all
	 * cards played on the table
	 */
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

	/**
	 * Obtain all cards within a suit
	 * @param suit
	 * @return cardsOfSuit, a list of cards that contains every
	 * card of a suit in current hand
	 */
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

	/**
	 * Compare current cards against first card played on the table
	 * @param suitLed
	 * @return true or false. If true, at least one card in current hand
	 * matches the first card played
	 */
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
