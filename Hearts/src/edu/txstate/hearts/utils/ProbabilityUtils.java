/**
 * 
 */
package edu.txstate.hearts.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.math3.util.ArithmeticUtils;

import edu.txstate.hearts.model.Card;
import edu.txstate.hearts.model.Card.Suit;

/**
 * This class can be used for various probability gathering functions that
 * might be used by any agent.
 * 
 * @author Neil Stickels
 *
 */
public abstract class ProbabilityUtils {
	
	public static double getProbabilityEveryoneHasOneOfASuit(Suit suit, List<Card> hand, Set<Card> muck)
	{
		double countWithOne = getTotalCardCombinationsWithForSuitAtLeastOne(suit, hand, muck);
		double totalCount = getTotalCardCombinationsForSuit(suit, hand, muck);
		return countWithOne/totalCount;
	}
	
	
	/**
	 * Similar to getTotalCombinationsForSuit, but with the caveat that it is
	 * only counting combinations in which each player has at least one card
	 * of the Suit in question.
	 * 
	 * @param suit the Suit you are checking
	 * @param hand your hand
	 * @param muck all of the cards already played
	 * @return the number of ways the cards could legally be distributed in
	 * 			which each player has at least one card of the specified suit
	 */
	public static long getTotalCardCombinationsWithForSuitAtLeastOne(Suit suit, List<Card> hand, Set<Card> muck) 
	{
		int available = removeCardsFromAvailable(suit, hand.iterator(), 13);
		available = removeCardsFromAvailable(suit, muck.iterator(), available);

		//System.out.println("hand is "+hand);
		//System.out.println("available is "+available);
		//System.out.println("hand size is "+hand.size());

		long totalCombos = 0;
		for (int x = 1; x < Math.min(available, hand.size()) + 1; x++) {
			for (int y = 1; y < Math.min(available, hand.size()) + 1; y++) {
				for (int z = 1; z < Math.min(available, hand.size()) + 1; z++) {
					if (x + y + z == available) {
						long xNewCombos, yNewCombos, zNewCombos;
						xNewCombos = ArithmeticUtils.binomialCoefficient(
								available, x);
						//System.out.println("adding "+available+"C"+x+" for player 1 - "+xNewCombos);
						// totalCombos += newCombos;
						yNewCombos = ArithmeticUtils.binomialCoefficient(
								available - x, y);
						//System.out.println("adding "+(available-x)+"C"+y+" for player 2 - "+yNewCombos);
						// totalCombos += newCombos;
						zNewCombos = ArithmeticUtils.binomialCoefficient(
								available - x - y, z);
						//System.out.println("adding "+(available-x-y)+"C"+z+" for player 3 - "+zNewCombos);
						long newCombos = (xNewCombos * yNewCombos * zNewCombos);
						if (newCombos < 1)
							newCombos = 1;
						totalCombos += newCombos;
						//System.out.println("adding "+newCombos);

						//System.out.println("---");

					}
				}
			}
		}
		return totalCombos;

	}

	private static int removeCardsFromAvailable(Suit suit,
			Iterator<Card> iterator, int available) {
		while (iterator.hasNext()) {
			Card c = iterator.next();
			if (c.getSuit() == suit)
				available--;
		}
		return available;
	}


	/**
	 * Determines how many different wants the cards of a given suit could 
	 * <i>legally</i> be distributed among the rest of the players.  Legally
	 * being key here, because of the rules of Hearts, at the start of any
	 * given turn, everyone should have the same number of Cards in their
	 * hand, and it would not be possible to have more cards than that size.
	 * 
	 * The algorithm uses a player's hand and all of the Cards that have
	 * already been played to determine how many cards of that suit are 
	 * available.  The algorithm will then determine all of the legal ways
	 * that those cards could be distributed between the other three players
	 * and return that number of combinations.
	 * 
	 * @param suit the Suit in question
	 * @param hand the hand of the Player calling the function (used to 
	 * 			exclude cards available for other players)
	 * @param muck all of the cards already played
	 * @return the number of ways the cards could legally be distributed 
	 */
	public static long getTotalCardCombinationsForSuit(Suit suit, List<Card> hand, Set<Card> muck)
	{
		int available = removeCardsFromAvailable(suit, hand.iterator(), 13);
		available = removeCardsFromAvailable(suit, muck.iterator(), available);
		
		// unfortunately, this only works if it were possible that one player 
		// could have more cards than the size of his hand, i.e., it allows
		// for the possibility that I could have 6 clubs in my hand when each
		// player's hand size should only be 4.
		//return (long)(Math.pow(3, available));
		
		long totalCombos = 0;
		for(int x = 0; x < Math.min(available, hand.size())+1; x++)
		{
			for(int y = 0; y < Math.min(available, hand.size())+1; y++)
			{
				for(int z= 0; z < Math.min(available, hand.size())+1; z++)
				{
					if(x+y+z == available)
					{
					long xNewCombos, yNewCombos, zNewCombos;
					xNewCombos = ArithmeticUtils.binomialCoefficient(available, x);
					//System.out.println("adding "+available+"C"+x+" for player 1 - "+xNewCombos);
					//totalCombos += newCombos;
					yNewCombos = ArithmeticUtils.binomialCoefficient(available-x, y);
					//System.out.println("adding "+(available-x)+"C"+y+" for player 2 - "+yNewCombos);
					//totalCombos += newCombos;
					zNewCombos = ArithmeticUtils.binomialCoefficient(available-x-y, z);
					//System.out.println("adding "+(available-x-y)+"C"+z+" for player 3 - "+zNewCombos);
					long newCombos = (xNewCombos*yNewCombos*zNewCombos);
					if(newCombos < 1)
						newCombos = 1;
					totalCombos += newCombos;
					//System.out.println("adding "+newCombos);

					//System.out.println("---");

					}
				}
			}
		}
		return totalCombos;
	}

	
	/**
	 * Determines the probability that one of the players does not have any
	 * cards in the suit in question and that that player also has hearts
	 * which could be played.
	 * 
	 * The probability that someone does not have the suit the agent wants to
	 * play and does have hearts can be used to determine if that suit should
	 * be played.
	 * 
	 * The function will determine how many cards of each suit are available
	 * (i.e., not in this player's hand and not already played)
	 * and then determine every combination of ways that these cards could
	 * legally be spread across all of the other players.  Additionally, it
	 * will count the number of those combinations in which one player
	 * has no cards of that suit and has hearts.
	 * 
	 * The probability returned is:
	 *      the number of combinations without cards in the suit and with hearts
	 *      --------------------------------------------------------------------
	 *                    the total number of combinations
	 * 
	 * @param hand the Player's hand
	 * @param suit the Suit the Player is determining the probability for
	 * @param muck the Cards that have already been played in the entire game
	 * @return probability another player does not have the suit in question and does have hearts
	 */
	public static double getProbabilityNoneOfSuitAndHasHearts(List<Card> hand, Suit suit, Set<Card> muck)
	{
		Map<Card.Suit, Integer> suitCounter = new HashMap<Card.Suit, Integer>();
		suitCounter.put(Card.Suit.Clubs, 13);
		suitCounter.put(Card.Suit.Diamonds, 13);
		suitCounter.put(Card.Suit.Hearts, 13);
		suitCounter.put(Card.Suit.Spades, 13);
		Iterator<Card> handIterator = hand.iterator();
		while(handIterator.hasNext())
		{
			Card.Suit s = handIterator.next().getSuit();
			int prev = suitCounter.get(s);
			suitCounter.put(s, --prev);
		}
		Iterator<Card> muckIterator = muck.iterator();
		while(muckIterator.hasNext())
		{
			Card.Suit s = muckIterator.next().getSuit();
			int prev = suitCounter.get(s);
			suitCounter.put(s, --prev);			
		}
		if(suitCounter.get(Suit.Hearts) == 0)
			return 0d;
		if(suitCounter.get(suit) == 0)
			return 1d;
		//System.out.println("suitCounter: "+suitCounter);
		CounterObject co = new CounterObject();
		for(int c = 0; c <= Math.min(hand.size(), suitCounter.get(Suit.Clubs)); c++)
		{
			//System.out.println("max diamonds should be "+(Math.max(Math.min(hand.size(), suitCounter.get(Suit.Diamonds)-c), 0)));
			for(int d = 0; d <= Math.max(Math.min(hand.size(), suitCounter.get(Suit.Diamonds)-c), 0); d++)
			{
				//System.out.println("max hearts should be "+(Math.max(Math.min(hand.size(), suitCounter.get(Suit.Hearts)-c-d), 0)));
				for(int h = 0; h <= Math.max(Math.min(hand.size(), suitCounter.get(Suit.Hearts)-c-d), 0); h++)
				{
					//System.out.println("max spades should be "+(Math.max(Math.min(hand.size(), suitCounter.get(Suit.Spades)-c-d-h), 0)));
					for(int s = 0; s <= Math.max(Math.min(hand.size(), suitCounter.get(Suit.Spades)-c-d-h), 0); s++)
					{
						//System.out.println("Testing P1 has "+c+"C "+d+"D "+s+"S "+h+"H");
						//System.out.println("c+d+h+s is "+(c+d+h+s));
						if((c+d+h+s) == hand.size())
						{
							//System.out.println("Using P1 has "+c+"C "+d+"D "+s+"S "+h+"H");
							long p1Count = ArithmeticUtils.binomialCoefficient(suitCounter.get(Suit.Clubs), c);
							p1Count *= ArithmeticUtils.binomialCoefficient(suitCounter.get(Suit.Diamonds), d);
							p1Count *= ArithmeticUtils.binomialCoefficient(suitCounter.get(Suit.Hearts), h);
							p1Count *= ArithmeticUtils.binomialCoefficient(suitCounter.get(Suit.Spades), s);

							doP2Counts(p1Count, co, c, d, h, s, hand.size(), suitCounter, suit);
						}
					}
				}
			}
		}
		System.out.println("totalCombos - "+co.getTotalCombos());
		double totalCombos = co.getTotalCombos();
		System.out.println("combosWithHeartsNoSuit - "+co.getWithHeartsNoSuitCombos());
		double heartCombos = co.getWithHeartsNoSuitCombos();
		System.out.println("probability - "+(heartCombos/totalCombos));
		return heartCombos/totalCombos;
	}

	
	private static void doP2Counts(long p1Count, CounterObject co, int p1c,
			int p1d, int p1h, int p1s, int handSize, Map<Suit, Integer> suitCounter, Suit suit) {
		for(int c = 0; c <= Math.max(Math.min(handSize, suitCounter.get(Suit.Clubs)-p1c), 0); c++)
		{
			for(int d = 0; d <= Math.max(Math.min(handSize, suitCounter.get(Suit.Diamonds)-c-p1d), 0); d++)
			{
				for(int h = 0; h <= Math.max(Math.min(handSize, suitCounter.get(Suit.Hearts)-c-d-p1h), 0); h++)
				{
					for(int s = 0; s <= Math.max(Math.min(handSize, suitCounter.get(Suit.Spades)-c-d-h-p1s), 0); s++)
					{
						if((c+d+h+s) == handSize)
						{
							//System.out.println("Using P2 has "+c+"C "+d+"D "+s+"S "+h+"H");
							long p2Count = ArithmeticUtils.binomialCoefficient(suitCounter.get(Suit.Clubs), c);
							p2Count *= ArithmeticUtils.binomialCoefficient(suitCounter.get(Suit.Diamonds), d);
							p2Count *= ArithmeticUtils.binomialCoefficient(suitCounter.get(Suit.Hearts), h);
							p2Count *= ArithmeticUtils.binomialCoefficient(suitCounter.get(Suit.Spades), s);
							int p3c = suitCounter.get(Suit.Clubs)-c-p1c;
							int p3d = suitCounter.get(Suit.Diamonds)-d-p1d;
							int p3h = suitCounter.get(Suit.Hearts)-h-p1h;
							int p3s = suitCounter.get(Suit.Spades)-s-p1s;
							//System.out.println("Using P3 has "+p3c+"C "+p3d+"D "+p3s+"S "+p3h+"H");

							boolean countHeartsPlayableCombos = false;
							if(h > 0)
								countHeartsPlayableCombos = determineHeartsPlayable(suit, c, d, s);
							if(!countHeartsPlayableCombos && p1h > 0)
								countHeartsPlayableCombos = determineHeartsPlayable(suit, p1c, p1d, p1s);
							if(!countHeartsPlayableCombos && p3h > 0)
								countHeartsPlayableCombos = determineHeartsPlayable(suit, p3c, p3d, p3s);
							co.addTotalCombos(p1Count*p2Count);
							if(countHeartsPlayableCombos)
								co.addWithHeartsNoSuitCombos(p1Count*p2Count);
						}
					}
				}
			}
		}	
	}


	private static boolean determineHeartsPlayable(Suit suit, int c, int d, int s) {
		if((suit == Suit.Clubs) && c == 0)
			return true;
		if((suit == Suit.Diamonds) && d == 0)
			return true;
		if((suit == Suit.Spades) && s == 0)
			return true;
		return false;
	}
} 	

/**
 * The CounterObject is just a helper class to maintain the counts while
 * performing all of the calculations.
 * 
 * totalCombos is the total combinations of legal hands possible
 * withHeartsNoSuitCombos is the number of combinations which contain hearts
 *   and no cards of the specified suit
 * 
 * @author Neil Stickels
 *
 */
class CounterObject 
{
	private long totalCombos;
	private long withHeartsNoSuitCombos;
	/**
	 * @return the totalCombos
	 */
	public long getTotalCombos() {
		return totalCombos;
	}
	
	public void addWithHeartsNoSuitCombos(long l) {
		withHeartsNoSuitCombos+=l;
		
	}
	
	public void addTotalCombos(long l) {
		totalCombos+=l;	
	}
	/**
	 * @return the withHeartsNoSuitCombo
	 */
	public long getWithHeartsNoSuitCombos() {
		return withHeartsNoSuitCombos;
	}
}
