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
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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

	public AgentAggressive(String playerName) {
		super(playerName);
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
				//System.out.println("saving "+thresholds);
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

		// TODO: improve logic to select cards to pass
		// for now, just pick the top 3 highest cards
		List<Card> myHand = this.getHand();
		Collections.sort(myHand, new CardComparator());
		// if(hasTwoOfClubs())
		// {
		// Card c = new Card(Face.Deuce, Suit.Clubs);
		// cardsToPass.add(c);
		// getHand().remove(c);
		// numCardsToPass = 2;
		// }

		for (int i = myHand.size() - 1; i >= 13 - numCardsToPass; i--) {
			cardsToPass.add(myHand.get(i));
			Card card = myHand.get(i);
			if ((card.getSuit() == Card.Suit.Spades)
					&& (card.getFace() == Card.Face.Queen)) {
				hasQueenOfSpades = false;
			}
			getHand().remove(myHand.get(i));
		}
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
		if (veryFirstTurn) {
			cardToPlay = super.playTwoOfClub();
		} else {
			if (cardsPlayed.size() > 0) {
				Suit suitLed = cardsPlayed.get(0).getSuit();
				if (!hasAnyOfSuit(suitLed)) // doesn't have suit played on table
				{
					if (hasQueenOfSpades) {
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
						List<Card> hearts = getAllOfSuit(Suit.Hearts);
						List<Card> spades = getAllOfSuit(Suit.Spades);
						List<Card> diamonds = getAllOfSuit(Suit.Diamonds);
						List<Card> clubs = getAllOfSuit(Suit.Clubs);
						if (hearts.size() > 0) // has hearts in hand
						{
							Collections.sort(hearts, new CardComparator());
							Collections.reverse(hearts); // Every call to
															// reverse ensures
															// '0' is the
															// highest card
							cardToPlay = hearts.get(0);
							lastThreshold = null;
						} else // doesn't have any hearts
						{
							List<Card> fewestCards = getCardsFromSuitWithFewest(
									spades, diamonds, clubs);
							cardToPlay = fewestCards.get(0);
							lastThreshold = null;
						}
					}

				} else // does have suit played on table
				{
					List<Card> playableCards = getAllOfSuit(suitLed);
					Collections.sort(playableCards, new CardComparator());
					Collections.reverse(playableCards);
					cardToPlay = determineBestCard(playableCards, true);
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
					if (fewestCards == spades)
						spades = Collections.EMPTY_LIST;
					if (fewestCards == diamonds)
						diamonds = Collections.EMPTY_LIST;
					if (fewestCards == clubs)
						clubs = Collections.EMPTY_LIST;
					fewestCards = getCardsFromSuitWithFewest(spades, diamonds,
							clubs);
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
							cardToPlay = determineBestCard(hearts, false);
							if (cardToPlay == null) {
								// means we couldn't find a single card under
								// risk threshold, so just play the highest
								// card from the smallest suit
								cardToPlay = first.get(0);
								expectedWins += getProbCardWins(cardToPlay);
							}
						}
					}
				}
			}
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
						getKnownCards(), isQosPlayed());
		while (searching) // search all cards
		{
			Card test = fewestCards.get(counter++);
			double cardWins = getProbCardWins(test);
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
		// if there are already three cards played, the strategy to know if
		// you will win is different
		if (this.getInPlayCards().size() == 3) {
			int biggest = getInPlayCards().get(0).getFace().ordinal();
			if (getInPlayCards().get(1).getSuit() == test.getSuit())
				if (getInPlayCards().get(1).getFace().ordinal() > biggest)
					biggest = getInPlayCards().get(1).getFace().ordinal();
			if (getInPlayCards().get(1).getSuit() == test.getSuit())
				if (getInPlayCards().get(1).getFace().ordinal() > biggest)
					biggest = getInPlayCards().get(1).getFace().ordinal();
			// System.out.println("testing "+test+" I am using special case, biggest is "+biggest+" and my card is "+test.getFace().ordinal());
			if (biggest > test.getFace().ordinal())
				return 0d;
			else
				return 1d;
		}
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
		// System.out.println("there are "+counter+" cards less than yours out of "+availableCards.size());
		return (double) counter / (double) availableCards.size();

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
	public void addPlayedCards(Collection<Card> cards, boolean tookCards) {
		// TODO Auto-generated method stub
		super.addPlayedCards(cards, tookCards);
		int numHearts = 0;
		boolean hasQos;
		Iterator<Card> cardIterator = cards.iterator();
		while (cardIterator.hasNext()) {
			Card c = cardIterator.next();
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
