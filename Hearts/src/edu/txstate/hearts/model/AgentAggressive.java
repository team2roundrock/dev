package edu.txstate.hearts.model;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import edu.txstate.hearts.controller.Hearts;
import edu.txstate.hearts.controller.Hearts.Passing;
import edu.txstate.hearts.model.Card.Face;
import edu.txstate.hearts.model.Card.Suit;
import edu.txstate.hearts.utils.ProbabilityUtils;
import edu.txstate.hearts.utils.RiskThresholds;
import edu.txstate.hearts.utils.RiskThresholds.Threshold;

/**
 * Contains rules & strategies for the most difficult AI agent
 * 
 * @author Neil Stickels, Jonathan Shelton
 * 
 */
public class AgentAggressive extends Agent {

	private RiskThresholds thresholds;
	private boolean hasQueenOfSpades = false;
	// private double lastChanceOfWin;
	private double expectedWins;
	private int actualWins;
	private Threshold lastThreshold;
	private final static boolean silent = Hearts.silent;

	public AgentAggressive(String playerName, int num) {
		super(playerName, num);
		try {
			// use buffering
			InputStream file = new FileInputStream("thresholds.bin");
			InputStream buffer = new BufferedInputStream(file);
			ObjectInput input = new ObjectInputStream(buffer);
			try {
				// deserialize the RiskThreshold
				thresholds = (RiskThresholds) input.readObject();

			} finally {
				input.close();
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("problem reading in file thresholds.bin: "
					+ e.getMessage());
			thresholds = new RiskThresholds();
		}
		//System.out.println("thresholds is "+thresholds);
	}

	public void serializeThresholds() {
		try {
			// use buffering
			OutputStream file = new FileOutputStream("thresholds.bin");
			OutputStream buffer = new BufferedOutputStream(file);
			ObjectOutput output = new ObjectOutputStream(buffer);
			try {
				if(silent)
					System.out.println("saving "+thresholds);
				output.writeObject(thresholds);
			} finally {
				output.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.txstate.hearts.model.Agent#getCardsToPass(Passing)
	 */
	@Override
	public List<Card> getCardsToPass(Passing passing) {
		int numCardsToPass = 3;
		List<Card> cardsToPass = new ArrayList<Card>();

		List<Card> myHand = this.getHand();
		Collections.sort(myHand, new CardComparator());
		List<Card> spades = getAllOfSuit(Suit.Spades);
		List<Card> diamonds = getAllOfSuit(Suit.Diamonds);
		List<Card> clubs = getAllOfSuit(Suit.Clubs);
		List<Card> fewest = getCardsFromSuitWithFewest(spades, diamonds, clubs);
		List<Card> oldFewest = null;
		
		while(numCardsToPass > 0)
		{
			if(fewest == null)
			{
				if(spades.size() > 5)
				{
					Collections.sort(spades, new CardComparator());
					Collections.reverse(spades);
					int counter = 0;
					while(numCardsToPass > 0)
					{
						Card c = spades.get(counter++);
						if(c.getFace() == Face.Queen)
							continue;
						cardsToPass.add(c);
						spades.remove(c);
						counter--;
						myHand.remove(c);
						numCardsToPass--;
					}
				} else
				{
					List<Card> hearts = getAllOfSuit(Suit.Hearts);
					Collections.sort(hearts, new CardComparator());
					Collections.reverse(hearts);
					while(numCardsToPass > 0)
					{
						Card c = hearts.get(0);
						cardsToPass.add(c);
						hearts.remove(c);
						numCardsToPass--;
						myHand.remove(c);
					}
				}
			}

			if(numCardsToPass > 0 && fewest.size() > numCardsToPass)
			{
				if(hasTwoOfClubs())
				{
					Card c = new Card(Face.Deuce, Suit.Clubs);
					cardsToPass.add(c);
					myHand.remove(c);
					numCardsToPass--;
					clubs.remove(c);
					fewest = getCardsFromSuitWithFewest(spades, diamonds, clubs);
				}
				if(spades.size() > 4 && fewest.size() > numCardsToPass)
				{
					Set<Card> cardsToRemove = new HashSet<Card>();
					int counter = 0;
					for(Card c: spades)
					{
						if((numCardsToPass-counter) > 0 && (spades.size()-counter) > 4 && c.getFace().ordinal() > 10)
						{
							cardsToPass.add(c);
							myHand.remove(c);
							counter++;
							cardsToRemove.add(c);
							fewest = getCardsFromSuitWithFewest(spades, diamonds, clubs);
						}
					}
					numCardsToPass -= counter;
					spades.removeAll(cardsToRemove);
				}
			} 
		
			if(fewest == clubs)
			{	
				if(clubs.size() <= numCardsToPass)
				{
					cardsToPass.addAll(clubs);
					myHand.removeAll(clubs);
					numCardsToPass -= clubs.size();
					clubs.clear();
				} else
				{
					if(hasTwoOfClubs() && numCardsToPass > 0)
					{
						Card c = new Card(Face.Deuce, Suit.Clubs);
						cardsToPass.add(c);
						myHand.remove(c);
						numCardsToPass--;
						clubs.remove(c);
					}
					Collections.sort(clubs, new CardComparator());
					Collections.reverse(clubs);
					while(numCardsToPass > 0)
					{
						Card c = clubs.remove(0);
						cardsToPass.add(c);
						myHand.remove(c);
						numCardsToPass--;
					}
				}
			} else if(fewest == diamonds)
			{
				if(diamonds.size() <= numCardsToPass)
				{
					cardsToPass.addAll(diamonds);
					myHand.removeAll(diamonds);
					numCardsToPass -= diamonds.size();
					diamonds.clear();
				} else
				{
					if(hasTwoOfClubs() && numCardsToPass > 0)
					{
						Card c = new Card(Face.Deuce, Suit.Clubs);
						cardsToPass.add(c);
						myHand.remove(c);
						numCardsToPass--;
						clubs.remove(c);
					}
					Collections.sort(diamonds, new CardComparator());
					Collections.reverse(diamonds);
					while(numCardsToPass > 0)
					{
						Card c = diamonds.remove(0);
						cardsToPass.add(c);
						myHand.remove(c);
						numCardsToPass--;
					}
				}
			
			} else if(fewest == spades)
			{
				if(hasQueenOfSpades && spades.size() == 1 && numCardsToPass > 0)
				{
					Card c = new Card(Face.Queen, Suit.Spades);
					cardsToPass.add(c);
					myHand.remove(c);
					spades.remove(c);
					numCardsToPass--;
					hasQueenOfSpades = false;
				}
			}
			oldFewest = fewest;
			fewest = getCardsFromSuitWithFewest(spades, diamonds, clubs);
			if(numCardsToPass > 0 && oldFewest == fewest)
			{
				if(fewest == spades)
				{
					fewest = getCardsFromSuitWithFewest(Collections.<Card> emptyList(), diamonds, clubs);
				} else
				{
					System.out.println("I am here");
					try
					{
						Thread.sleep(5000);
					} catch (Exception e)
					{
						
					}
					}
			}
		}
		if(cardsToPass.size() > 3)
		{
			//wtf?
			System.out.println("oh nos");
			System.out.println("passing "+cardsToPass);
			System.out.println("hand is "+myHand);
			try
			{
				Thread.sleep(5000);
			} catch (Exception e)
			{
				
			}
		}

//		for (int i = myHand.size() - 1; i >= 13 - numCardsToPass; i--) {
//			cardsToPass.add(myHand.get(i));
//			Card card = myHand.get(i);
//			if ((card.getSuit() == Card.Suit.Spades)
//					&& (card.getFace() == Card.Face.Queen)) {
//				hasQueenOfSpades = false;
//			}
//			getHand().remove(myHand.get(i));
//		}
		Set<Card> p1Set = getKnownCards().get(0);
		Set<Card> p2Set = getKnownCards().get(1);
		Set<Card> p3Set = getKnownCards().get(2);

		if (passing == Passing.Left)
			p1Set.addAll(cardsToPass);
		else if (passing == Passing.Front)
			p2Set.addAll(cardsToPass);
		else if (passing == Passing.Right)
			p3Set.addAll(cardsToPass);

		return cardsToPass;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.txstate.hearts.model.Agent#playCard(java.util.List, boolean,
	 * boolean)
	 */
	@Override
	public Card playCard(List<Card> cardsPlayed, boolean heartsBroken,
			boolean veryFirstTurn) {
		Card cardToPlay = null;
		String debugStr = "";
		if (veryFirstTurn) {
			cardToPlay = super.playTwoOfClub();
		} else {
			if (cardsPlayed.size() > 0) {
				Suit suitLed = cardsPlayed.get(0).getSuit();
				debugStr+=suitLed+" was led... ";
				if (!hasAnyOfSuit(suitLed)) // doesn't have suit played on table
				{
					debugStr+="I don't have that suit... ";
					if (hasQueenOfSpades) {
						debugStr+="I have the queen of spades... ";
						Iterator<Card> iterator = getHand().iterator();
						while (iterator.hasNext()) {
							Card card = iterator.next();
							if ((card.getSuit() == Card.Suit.Spades)
									&& (card.getFace() == Card.Face.Queen)) {
								cardToPlay = card;
								hasQueenOfSpades = false;
								lastThreshold = null;
							}
						}
					} else // no queen of spades, no suit on table
					{
						debugStr+="No queen of spades... ";
						List<Card> hearts = getAllOfSuit(Suit.Hearts);
						List<Card> spades = getAllOfSuit(Suit.Spades);
						List<Card> diamonds = getAllOfSuit(Suit.Diamonds);
						List<Card> clubs = getAllOfSuit(Suit.Clubs);
						if (hearts.size() > 0) // has hearts in hand
						{
							debugStr+="But I do have hearts... ";
							Collections.sort(hearts, new CardComparator());
							Collections.reverse(hearts); // Every call to
															// reverse ensures
															// '0' is the
															// highest card
							cardToPlay = hearts.get(0);
							debugStr+="I want to play "+cardToPlay;
							lastThreshold = null;
						} else // doesn't have any hearts
						{
							debugStr+="No hearts either... ";
							List<Card> fewestCards = getCardsFromSuitWithFewest(
									spades, diamonds, clubs);
							cardToPlay = fewestCards.get(0);
							debugStr="I guess I will play "+cardToPlay;
							lastThreshold = null;
						}
					}

				} else // does have suit played on table
				{
					debugStr+="I have that suit... ";
					List<Card> playableCards = getAllOfSuit(suitLed);
					Collections.sort(playableCards, new CardComparator());
					Collections.reverse(playableCards);
					cardToPlay = determineBestCard(playableCards, true);
					debugStr+="So I will play "+cardToPlay;
				}
			}
			/*
			 * TODO Ensure agent doesn't always default to playing highest card
			 * when going first. Especially after hearts broken. Particularly in
			 * this case, the highest card in a suit does not HAVE to be played
			 * if there is high risk. Might want to consider evaluating all
			 * suits as well, not necessarily suit with the fewest
			 */
			else // no cards played
			{
				debugStr+="No cards played yet... ";
				List<Card> hearts = getAllOfSuit(Suit.Hearts);
				List<Card> spades = getAllOfSuit(Suit.Spades);
				List<Card> diamonds = getAllOfSuit(Suit.Diamonds);
				List<Card> clubs = getAllOfSuit(Suit.Clubs);

				List<Card> fewestCards = getCardsFromSuitWithFewest(spades,
						diamonds, clubs);

				if (fewestCards == null)
					fewestCards = hearts;

				Collections.sort(fewestCards, new CardComparator());
				Collections.reverse(fewestCards);
				cardToPlay = determineBestCard(fewestCards, false);
				if (cardToPlay == null) {
					//System.out.println("no good card to play from "+ fewestCards);
					List<Card> first = fewestCards;
					List<Card> second = null;
					if (fewestCards == spades)
						spades = Collections.EMPTY_LIST;
					if (fewestCards == diamonds)
						diamonds = Collections.EMPTY_LIST;
					if (fewestCards == clubs)
						clubs = Collections.EMPTY_LIST;
					fewestCards = getCardsFromSuitWithFewest(spades, diamonds,
							clubs);
					second = fewestCards;
					cardToPlay = determineBestCard(fewestCards, false);
					if (cardToPlay == null) {
						if (fewestCards == spades)
							spades = Collections.EMPTY_LIST;
						if (fewestCards == diamonds)
							diamonds = Collections.EMPTY_LIST;
						if (fewestCards == clubs)
							clubs = Collections.EMPTY_LIST;
						fewestCards = getCardsFromSuitWithFewest(spades,
								diamonds, clubs);
						cardToPlay = determineBestCard(fewestCards, false);
						if (cardToPlay == null) {
							if(heartsBroken)
								cardToPlay = determineBestCard(hearts, false);
							if (cardToPlay == null) {
								// means we couldn't find a single card under
								// risk threshold, so just play the highest
								// card from the smallest suit 
								// unless that suit is spades, and it would 
								// force us to play the ace, king or queen of
								// spades and the queen of spades is still out
								// there
								int counter = 0;
								while(first.size() > counter && cardToPlay == null)
								{
									cardToPlay = first.get(counter++);
									if(!isQosPlayed() && cardToPlay.getSuit() == Suit.Spades)
										if(cardToPlay.getFace().ordinal() >= 10)
											cardToPlay = null;
								}
								// means there isn't a spade we can play so get
								// the highest card from the second highest suit
								if(cardToPlay == null)
								{
									if(second == null)
										// means we don't have another suit
										cardToPlay = first.get(0);
									else
										cardToPlay = second.get(0);
								}
								//System.out.println("adding to expectedWins");
								expectedWins += getProbCardWins(cardToPlay);
							}
						}
					}
				}
			}
		}
		if(cardToPlay == null)
		{
			System.out.println("card is null?");
			System.out.println("hand is "+getHand());
			System.out.println("cardsPlayed is "+cardsPlayed);
			System.out.println(debugStr);
			try
			{
				Thread.sleep(5000);
			} catch (Exception e)
			{
				
			}
		}
		if(!silent)
		{
			System.out.println("current hand - "+getHand());
			System.out.println("picked to played - "+cardToPlay);
		}
		
		getHand().remove(cardToPlay);

		if ((cardToPlay.getSuit() == Card.Suit.Spades)
				&& (cardToPlay.getFace() == Card.Face.Queen))
			hasQueenOfSpades = false;
		
		return cardToPlay;
	}

	/**
	 * @param cardToPlay
	 * @param fewestCards
	 * @return
	 */
	private Card determineBestCard(List<Card> fewestCards, boolean force) {
		boolean searching = true;
		int counter = 0;
		Card cardToPlay = null;
		if ((fewestCards == null || fewestCards.size() == 0) && !force)
			return null;
		// System.out.println("fewestCards is "+fewestCards);
		double canPlayHearts = ProbabilityUtils
				.getProbabilityNoneOfSuitAndHasHearts(getHand(), fewestCards
						.get(0).getSuit(), getPlayedCards(), getInPlayCards(),
						getKnownCards(), getKnownEmpties(), isQosPlayed());
		while (searching) // search all cards
		{
			Card test = fewestCards.get(counter++);
			double cardWins = getProbCardWins(test);
			if(!isQosPlayed() && test.getSuit()==Suit.Spades)
			{
				if(hasQueenOfSpades)
				{
					if(test.getFace().equals(Face.Queen))
					{
						if(cardWins != 0d && (!force || (force && fewestCards.size() > 1)))
						{
							canPlayHearts = 1d;
							cardWins = 1d;
						}
					}
				} else
				{
					if(cardWins != 0d && (test.getFace().ordinal() > 10) && (!force || (force && fewestCards.size() > 1)))
					{
						canPlayHearts = 1d;
						cardWins = 1d;
					}
				}
			}
			double risk = canPlayHearts * cardWins;
			double threshold;
			if (getInPlayCards().size() == 3) {
				threshold = 1d;
				lastThreshold = null;
				// System.out.println("using the new stuff");
			} else {
				Threshold t = thresholds.getThreshold(getInPlayCards().size(),
						isQosPlayed());
				threshold = t.getRiskThreshold();
				lastThreshold = t;
			}

			//System.out.println("EVALUATING..." + test + ": cardWins is "
			//		+ cardWins + " canPlayHearts is " + canPlayHearts
			//		+ " total risk is " + risk + " and threshold is "
			//		+ threshold);
			if (risk < threshold) {
				cardToPlay = test;
				searching = false;
				//System.out.println("adding to expectedWins");
				expectedWins += cardWins;
			} else // there is a risk of getting hearts
			{
				if (counter == fewestCards.size()) // all cards of smallest
													// suite have been analyzed
				{
					if (force) {
						cardToPlay = fewestCards.get(0); // just play the
															// highest if at
															// risk
						//System.out.println("adding to expectedWins");
						expectedWins += getProbCardWins(cardToPlay);
					}
					searching = false;
				}
			}
		}
		return cardToPlay;
	}

	/**
	 * This method will find the suit with the lowest number of cards, aligning
	 * with a strategy of removing as many suits as possible.
	 * 
	 * @param spades
	 * @param diamonds
	 * @param clubs
	 * @return fewestCards, which is populated with the suit that has the least
	 *         cards
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
		if (clubs.size() > 0 && clubs.size() < fewest) {
			fewest = clubs.size();
			fewestCards = clubs;
		}
		if (spades.size() > 0 && spades.size() <= fewest) {
			if (fewest == spades.size()) {
				if (spades.get(0).getFace().ordinal() > fewestCards.get(0)
						.getFace().ordinal()) {
					fewest = spades.size();
					fewestCards = spades;
				}
			} else {
				fewest = spades.size();
				fewestCards = spades;
			}
		}
		if (diamonds.size() > 0 && diamonds.size() <= fewest) {
			if (fewest == diamonds.size()) {
				if (diamonds.get(0).getFace().ordinal() > fewestCards.get(0)
						.getFace().ordinal()) {
					fewest = diamonds.size();
					fewestCards = diamonds;
				}
			} else {
				fewest = diamonds.size();
				fewestCards = diamonds;
			}

		}
		return fewestCards;
	}

	/**
	 * Get the probability that a card will win
	 * 
	 * @param test
	 * @return probability that the passed in card will win all cards played on
	 *         the table
	 */
	private double getProbCardWins(Card test) {
		List<Integer> availableCards = new ArrayList<Integer>();
		// if there are already cards played, the strategy to know if
		// you will win is different
		if (this.getInPlayCards().size() > 0) {
			// if test suit doesn't match suit led, then there is no chance of winning
			if(test.getSuit() != getInPlayCards().get(0).getSuit())
				return 0d;
			int biggest = -1;
			for(int i = 0; i < getInPlayCards().size(); i++)
			{
				Card c = getInPlayCards().get(i);
				if(c.getSuit() == test.getSuit())
				{
					if(c.getFace().ordinal() > biggest)
						biggest = c.getFace().ordinal();
				}
			}
			// if the biggest card play is bigger than mine, I can't win
			if (biggest > test.getFace().ordinal())
				return 0d;
			// if all three cards have been played, and my card is bigger
			// than the biggest, I am guaranteed to win
			else if(getInPlayCards().size() == 3)
				return 1d;
		}
		// not everyone has played, so figure out how many cards out there
		// are bigger than mine, and assume that I will win:
		//      #CardsSmallerThanMine / #CardsAvailableCardsOfSuit 
		for (int i = 0; i < 13; i++) {
			availableCards.add(i);
		}
		Iterator<Card> iterator = getHand().iterator();
		while (iterator.hasNext()) {
			Card card = iterator.next();
			if (card.getSuit() == test.getSuit())
				availableCards.set(card.getFace().ordinal(), null);
		}
		Iterator<Card> iterator2 = getPlayedCards().iterator();
		while (iterator2.hasNext()) {
			Card card = iterator2.next();
			if (card.getSuit() == test.getSuit())
				availableCards.set(card.getFace().ordinal(), null);
		}

		List<Integer> newAvailableCards = new ArrayList<Integer>();
		for (int i = 0; i < 13; i++) {
			if (availableCards.get(i) != null) {
				newAvailableCards.add(i);
			}
		}
		availableCards = newAvailableCards;

		if (availableCards.size() == 0)
			return 1;

		int counter = 0;
		for (int i = 0; i < availableCards.size(); i++) {
			if (availableCards.get(i) < test.getFace().ordinal())
				counter++;
		}
		int numToPlay = 3;
		numToPlay -= getInPlayCards().size();
		for(int i = numToPlay-1; i >= 0; i--)
		{
			if(getKnownEmpties().get(i).contains(test.getSuit()))
				numToPlay--;
		}
		int bigger = availableCards.size()-counter;
		if(numToPlay > counter && bigger > 0)
			return 0d;
		//double factor = (double) numToPlay/3d;
		double factor = 1d;
		// System.out.println("there are "+counter+" cards less than yours out of "+availableCards.size());
		double theyWin = (1d-((double) counter / (double) availableCards.size()))*factor;
		return 1d-theyWin;

	}

	/**
	 * Obtain all cards within a suit
	 * 
	 * @param suit
	 * @return cardsOfSuit, a list of cards that contains every card of a suit
	 *         in current hand
	 */
	private List<Card> getAllOfSuit(Suit suit) {

		List<Card> cardsOfSuit = new ArrayList<Card>();
		Iterator<Card> iterator = getHand().iterator();
		while (iterator.hasNext()) {
			Card card = iterator.next();
			if (card.getSuit() == suit)
				cardsOfSuit.add(card);
		}
		return cardsOfSuit;
	}

	/**
	 * Compare current cards against first card played on the table
	 * 
	 * @param suitLed
	 * @return true or false. If true, at least one card in current hand matches
	 *         the first card played
	 */
	private boolean hasAnyOfSuit(Suit suitLed) {
		Iterator<Card> iterator = getHand().iterator();
		while (iterator.hasNext()) {
			Card c = iterator.next();
			if (c.getSuit() == suitLed)
				return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.txstate.hearts.model.Player#addCard(edu.txstate.hearts.model.Card)
	 */
	@Override
	public void addCard(Card card) {
		super.addCard(card);
		if ((card.getSuit() == Card.Suit.Spades)
				&& (card.getFace() == Card.Face.Queen)) {
			hasQueenOfSpades = true;
		}
	}

	@Override
	public List<Card> getCardsToPass() {
		// we aren't actually using this, since we actually implemented the real
		// algorithm
		throw new UnsupportedOperationException(
				"This method shouldn't have gotten called");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.txstate.hearts.model.Player#addPlayedCards(java.util.Collection,
	 * boolean)
	 */
	@Override
	public void addPlayedCards(Collection<Card> cards, boolean tookCards, int num) {
		// TODO Auto-generated method stub
		super.addPlayedCards(cards, tookCards, num);
		int numHearts = 0;
		boolean hasQos;
		Suit suitLed = null;
		Iterator<Card> cardIterator = cards.iterator();
		int counter = 0;
		while (cardIterator.hasNext()) {
			Card c = cardIterator.next();
			if(suitLed == null)
				suitLed = c.getSuit();
			if(c.getSuit() != suitLed)
			{
				int playerNum = num+counter-getMyNum();
				playerNum %= 4;
				playerNum-=1;
				if(playerNum >= 0)
				{
					getKnownEmpties().get(playerNum).add(suitLed);
					//System.out.println("adding "+suitLed+" to "+playerNum+"'s empties");
				}
			}
			if (c.getSuit() == Suit.Hearts)
				numHearts++;
			if (c.getSuit() == Suit.Spades && c.getFace() == Face.Queen)
				// hasQos = true;
				numHearts += 13;
			Set<Card> p1Set = getKnownCards().get(0);
			Set<Card> p2Set = getKnownCards().get(1);
			Set<Card> p3Set = getKnownCards().get(2);
			p1Set.remove(c);
			p2Set.remove(c);
			p3Set.remove(c);
			counter++;
		}
		if (tookCards) {
			actualWins++;
			thresholds.decreaseThreshold(lastThreshold, numHearts);
		} else {
			thresholds.increaseThreshold(lastThreshold);
		}
	}

	/**
	 * @return the expectedWins
	 */
	public double getExpectedWins() {
		return expectedWins;
	}

	/**
	 * @return the actualWins
	 */
	public int getActualWins() {
		return actualWins;
	}

}
