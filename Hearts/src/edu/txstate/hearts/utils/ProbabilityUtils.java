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
 * might be used by any agent.  In practice, I think only AgentAggressive is
 * using any of this
 * 
 * @author Neil Stickels
 *
 */
public abstract class ProbabilityUtils {
	
	/**
	 * Determine the probability that everyone has at least one card of 
	 * the specified suit
	 * @param suit the suit being queried
	 * @param hand the hand of the user making the request
	 * @param muck all of the cards that have already been played
	 * @return a probability between 0 and 1 that everyone has at least one card
	 * in that suit
	 */
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
	private static long getTotalCardCombinationsForSuit(Suit suit, List<Card> hand, Set<Card> muck)
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
	 * @param cardsPlayedThisTurn a List of Cards that have already been played this turn
	 * @param knownCards a list of three Sets of known cards for opponents
	 * @param knownEmpties a List of three Sets, with each set being known suits that a player does not have any cards in
	 * @param qosPlayed a boolean indicator of whether or not the Queen of Spades
	 * 			has already been played in this game, if not, this algorithm
	 * 			will count the Queen of Spades as a heart
	 * @return probability another player does not have the suit in question and does have hearts
	 */
	public static double getProbabilityNoneOfSuitAndHasHearts(List<Card> hand, 
			Suit suit, Set<Card> muck, List<Card> cardsPlayedThisTurn, 
			List<Set<Card>> knownCards, List<Set<Suit>> knownEmpties, boolean qosPlayed)
	{
		// check if I am playing first
		if(cardsPlayedThisTurn.size() > 0) 
		{
			// I am not playing first, am I playing last?
			if(cardsPlayedThisTurn.size() == 3)
			{
				// yes, I am playing last.  That makes this easy, if someone
				// has already played hearts, then the probability is 1, if
				// no one has, then the probability is 0.
				if(cardsPlayedThisTurn.get(0).getSuit() == Suit.Hearts ||
						cardsPlayedThisTurn.get(1).getSuit() == Suit.Hearts ||
						cardsPlayedThisTurn.get(2).getSuit() == Suit.Hearts)
					return 1d;
				return 0d;
			}
			// am i playing third?
			else if(cardsPlayedThisTurn.size() == 2)
			{
				// if someone has already played hearts, then return 1,
				// otherwise make sure we factor in what was already played
				// into the rest of the calculations
				if(cardsPlayedThisTurn.get(0).getSuit() == Suit.Hearts ||
						cardsPlayedThisTurn.get(1).getSuit() == Suit.Hearts)
					return 1d;
				knownCards.get(1).add(cardsPlayedThisTurn.get(0));
				knownCards.get(2).add(cardsPlayedThisTurn.get(1));
			} 
			// am i playing second?
			else if(cardsPlayedThisTurn.size() == 1)
			{
				// if the first player played hearts, return 1, otherwise,
				// make sure we factor the card into what was already played
				if(cardsPlayedThisTurn.get(0).getSuit() == Suit.Hearts)
					return 1d;
				knownCards.get(2).add(cardsPlayedThisTurn.get(0));
			} else
				// this means I am not playing first, second, third or fourth?
				throw new RuntimeException("how i get here? cards played this turn - "+cardsPlayedThisTurn.size());
		}
		// count how many cards of each suit are still left
		Map<Card.Suit, Integer> suitCounter = new HashMap<Card.Suit, Integer>();
		suitCounter.put(Card.Suit.Clubs, 13);
		suitCounter.put(Card.Suit.Diamonds, 13);
		suitCounter.put(Card.Suit.Hearts, 13);
		suitCounter.put(Card.Suit.Spades, 13);
		Iterator<Card> handIterator = hand.iterator();
		// take all of my cards out of the cards available for each suit
		while(handIterator.hasNext())
		{
			Card.Suit s = handIterator.next().getSuit();
			int prev = suitCounter.get(s);
			suitCounter.put(s, --prev);
		}
		// take all of the cards that have already been played out of the cards
		// available
		Iterator<Card> muckIterator = muck.iterator();
		while(muckIterator.hasNext())
		{
			Card.Suit s = muckIterator.next().getSuit();
			int prev = suitCounter.get(s);
			suitCounter.put(s, --prev);			
		}
		// if there are no hearts remaining, no one could possibly play hearts
		// so return 0
		if(suitCounter.get(Suit.Hearts) == 0)
			return 0d;
		// if no one else has the suit led, and there are hearts out there,
		// save the math and return 1.
//		if(suitCounter.get(suit) == 0 && suitCounter.get(Suit.Hearts) > 0)
//		{
//			//System.out.println("no one else has that suit, and there are hearts, returning 1");
//			return 1d;
//		}
		if(suit == Suit.Hearts)
			if(suitCounter.get(Suit.Hearts) > 0)
				return 1d;
			else
				return 0d;
			
		//System.out.println("suitCounter: "+suitCounter);
		CounterObject co = new CounterObject();
		int[] knownCounterArray = createKnownCounter(knownCards);
		// i know the condition for this is enough to make someone's head hurt
		// because it made mine hurt to come up with it.  So here is how
		// i came up with it...
		// starting from the inside, we want c to go up to the size of the hand
		// or up to however many clubs are available, which ever is less.
		// for determining how many clubs are available for player 1, we 
		// already have the suitCounter map which maps how many cards of each
		// suit are not in the calling players hand or in the muck.
		// but we also want to subtract off any known clubs we know in either
		// player 2's or player 3's hands.  
		// we want the minimum because let's say that the size of the hand is
		// 5, in theory, this player could have up to 5 clubs.  Unless there
		// are fewer than 5 clubs left, in which case this player can't have
		// more than the number of clubs available.  But if there were still
		// 7 clubs available, this player could only have 5, i.e., the size
		// of the hand.
		// in theory, the Math.max part I have there should never be needed,
		// but it is there in case there is a some type of a problem which
		// causes the value returned by that Math.min call to be less than
		// however many clubs this player must have.  in that case, this
		// for loop wouldn't run, which would cause nothing to run.
		// the rest of the nested for loops proceed in much the same way, but
		// in each one, it will also count the number of cards that player 1
		// already has from previous iterations of the for loop, i.e., if c = 2,
		// and the hand size is 5, then player 1 couldn't have more than 3
		// diamonds.

		for(int c = knownCounterArray[0]; c <= Math.max(Math.min(hand.size(), suitCounter.get(Suit.Clubs)-knownCounterArray[4]-knownCounterArray[8]), knownCounterArray[0]); c++)
		{
			if(knownEmpties.get(0).contains(Suit.Clubs) && c > 0)
				continue;
			//System.out.println("max diamonds should be "+(Math.max(Math.min(hand.size(), suitCounter.get(Suit.Diamonds)-c), 0)));
			for(int d = knownCounterArray[1]; d <= Math.max(Math.min(hand.size()-c, suitCounter.get(Suit.Diamonds)-knownCounterArray[5]-knownCounterArray[9]), knownCounterArray[1]); d++)
			{
				if(knownEmpties.get(0).contains(Suit.Diamonds) && d > 0)
					continue;
				//System.out.println("max hearts should be "+(Math.max(Math.min(hand.size(), suitCounter.get(Suit.Hearts)-c-d), 0)));
				for(int h = knownCounterArray[2]; h <= Math.max(Math.min(hand.size()-c-d, suitCounter.get(Suit.Hearts)-knownCounterArray[6]-knownCounterArray[10]), knownCounterArray[2]); h++)
				{
					if(knownEmpties.get(0).contains(Suit.Hearts) && h > 0)
						continue;
					//System.out.println("max spades should be "+(Math.max(Math.min(hand.size(), suitCounter.get(Suit.Spades)-c-d-h), 0)));
					for(int s = knownCounterArray[3]; s <= Math.max(Math.min(hand.size()-c-d-h, suitCounter.get(Suit.Spades)-knownCounterArray[7]-knownCounterArray[11]), knownCounterArray[3]); s++)
					{
						if(knownEmpties.get(0).contains(Suit.Spades) && s > 0)
							continue;
						//System.out.println("Testing P1 has "+c+"C "+d+"D "+s+"S "+h+"H");
						//System.out.println("c+d+h+s is "+(c+d+h+s));
						if((c+d+h+s) == hand.size())
						{
							//System.out.println("Using P1 has "+c+"C "+d+"D "+s+"S "+h+"H");
							long p1Count = ArithmeticUtils.binomialCoefficient(suitCounter.get(Suit.Clubs), c);
							p1Count *= ArithmeticUtils.binomialCoefficient(suitCounter.get(Suit.Diamonds), d);
							p1Count *= ArithmeticUtils.binomialCoefficient(suitCounter.get(Suit.Hearts), h);
							p1Count *= ArithmeticUtils.binomialCoefficient(suitCounter.get(Suit.Spades), s);

							doP2Counts(p1Count, co, c, d, h, s, suitCounter, suit, knownCounterArray, knownEmpties, cardsPlayedThisTurn.size(), qosPlayed, hand, muck);
						}
					}
				}
			}
		}
		//System.out.println("totalCombos - "+co.getTotalCombos());
		double totalCombos = co.getTotalCombos();
		double heartCombos = co.getWithHeartsNoSuitCombos();
		
		if(co.getTotalCombosExceedCounter() > 0)
		{
			totalCombos = (double)(co.getTotalCombosExceedCounter()) + totalCombos/Long.MAX_VALUE;
			heartCombos = (double)(co.getWithHeartsExceedCounter()) + heartCombos/Long.MAX_VALUE;
		}
		//System.out.println("combosWithHeartsNoSuit - "+co.getWithHeartsNoSuitCombos());
		//System.out.println("probability - "+(heartCombos/totalCombos));
		if(heartCombos > totalCombos)
		{
			System.out.println("got more heartCombos than totalCombos?");
			System.out.println("hand - "+hand+" suit - "+suit+" cardsPlayedThisTurn - "+cardsPlayedThisTurn);
			System.out.println("knownCards - "+knownCards+" qosPlayed - "+qosPlayed);
			System.out.println("knownEmpties - "+knownEmpties);
			System.out.println("muck - "+muck);
			
		}
		if(totalCombos == 0)
			return 0d;
		return heartCombos/totalCombos;
	}

	
	private static int[] createKnownCounter(List<Set<Card>> knownCards) 
	{
		int[] knownCounterArray = new int[12];
		for (int i = 0; i < 12; i++)
			knownCounterArray[i] = 0;
		for (int j = 0; j < 3; j++)
		{
			Iterator<Card> kcIterator = knownCards.get(j).iterator();
			int offset = j * 4;
			while (kcIterator.hasNext()) 
			{
				Card c = kcIterator.next();
				if (c.getSuit() == Suit.Clubs) 
					knownCounterArray[offset] = (knownCounterArray[offset] + 1);
				if (c.getSuit() == Suit.Diamonds) 
					knownCounterArray[offset+1] = (knownCounterArray[offset+1] + 1);			
				if (c.getSuit() == Suit.Hearts) 
					knownCounterArray[offset+2] = (knownCounterArray[offset+2] + 1);
				if (c.getSuit() == Suit.Spades) 
					knownCounterArray[offset+3] = (knownCounterArray[offset+3] + 1);
			}
		}
		return knownCounterArray;
	}


	private static void doP2Counts(long p1Count, CounterObject co, int p1c,
			int p1d, int p1h, int p1s, Map<Suit, Integer> suitCounter, Suit suit, 
			int[] knownCounterArray, List<Set<Suit>> knownEmpties, int cardsAlreadyPlayed, boolean qosPlayed,
			List<Card> hand, Set<Card> muck) 
	{
		int handSize = hand.size();
		for(int c = knownCounterArray[4]; c <= Math.max(Math.min(handSize, suitCounter.get(Suit.Clubs)-p1c-knownCounterArray[8]), knownCounterArray[4]); c++)
		{
			if(knownEmpties.get(1).contains(Suit.Clubs) && c > 0)
				continue;
			int p3c = suitCounter.get(Suit.Clubs)-c-p1c;
			if(knownEmpties.get(2).contains(Suit.Clubs) && p3c > 0)
				continue;
			for(int d = knownCounterArray[5]; d <= Math.max(Math.min(handSize-c, suitCounter.get(Suit.Diamonds)-p1d-knownCounterArray[9]), knownCounterArray[5]); d++)
			{
				if(knownEmpties.get(1).contains(Suit.Diamonds) && d > 0)
					continue;
				int p3d = suitCounter.get(Suit.Diamonds)-d-p1d;
				if(knownEmpties.get(2).contains(Suit.Diamonds) && p3d > 0)
					continue;
				for(int h = knownCounterArray[6]; h <= Math.max(Math.min(handSize-c-d, suitCounter.get(Suit.Hearts)-p1h-knownCounterArray[10]), knownCounterArray[6]); h++)
				{
					if(knownEmpties.get(1).contains(Suit.Hearts) && h > 0)
						continue;
					int p3h = suitCounter.get(Suit.Hearts)-h-p1h;
					if(knownEmpties.get(2).contains(Suit.Hearts) && p3h > 0)
						continue;
					for(int s = knownCounterArray[7]; s <= Math.max(Math.min(handSize-c-d-h, suitCounter.get(Suit.Spades)-p1s-knownCounterArray[11]), knownCounterArray[7]); s++)
					{
						if(knownEmpties.get(1).contains(Suit.Spades) && s > 0)
							continue;
						int p3s = suitCounter.get(Suit.Spades)-s-p1s;
						if(knownEmpties.get(2).contains(Suit.Spades) && p3s > 0)
							continue;
						if((c+d+h+s) == handSize)
						{
							//System.out.println("Using P2 has "+c+"C "+d+"D "+s+"S "+h+"H");
							if(suitCounter.get(Suit.Clubs) < c)
							{
								System.out.println("suitCounter for clubs is "+suitCounter.get(Suit.Clubs));
								System.out.println("c is "+c);
								System.out.println("p1c is "+p1c);
								System.out.println("from for loop, checking for c <= "+Math.max(Math.min(handSize, suitCounter.get(Suit.Clubs)-p1c-knownCounterArray[8]), knownCounterArray[4]));
								System.out.println("by breakign this down, it was from Math.max(Math.min("+handSize+", "+suitCounter.get(Suit.Clubs)+"-"+p1c+"-"+knownCounterArray[8]+"), "+knownCounterArray[4]+")");							
							}
							if(suitCounter.get(Suit.Diamonds) < d)
							{
								System.out.println("suitCounter for diamonds is "+suitCounter.get(Suit.Diamonds));
								System.out.println("d is "+d);
								System.out.println("p1d is "+p1d);
								System.out.println("from for loop, checking for d <= "+Math.max(Math.min(handSize-c, suitCounter.get(Suit.Diamonds)-p1d-knownCounterArray[9]), knownCounterArray[5]));
								System.out.println("by breakign this down, it was from Math.max(Math.min("+handSize+"-"+c+", "+suitCounter.get(Suit.Diamonds)+"-"+p1d+"-"+knownCounterArray[9]+"), "+knownCounterArray[5]+")");							
							}
							if(suitCounter.get(Suit.Hearts) < h)
							{
								System.out.println("suitCounter for hearts is "+suitCounter.get(Suit.Hearts));
								System.out.println("h is "+h);
								System.out.println("p1h is "+p1h);
								System.out.println("from for loop, checking for h <= "+Math.max(Math.min(handSize-c-d, suitCounter.get(Suit.Hearts)-p1h-knownCounterArray[10]), knownCounterArray[6]));
								System.out.println("by breakign this down, it was from Math.max(Math.min("+handSize+"-"+c+"-"+d+", "+suitCounter.get(Suit.Hearts)+"-"+p1h+"-"+knownCounterArray[10]+"), "+knownCounterArray[6]+")");							
							}
							if(suitCounter.get(Suit.Spades) < s)
							{
								System.out.println("suitCounter for spades is "+suitCounter.get(Suit.Spades));
								System.out.println("s is "+s);
								System.out.println("p1s is "+p1s);
								System.out.println("from for loop, checking for s <= "+Math.max(Math.min(handSize-c-d-h, suitCounter.get(Suit.Spades)-p1s-knownCounterArray[11]), knownCounterArray[7]));
								System.out.println("by breakign this down, it was from Math.max(Math.min("+handSize+"-"+c+"-"+d+"-"+h+", "+suitCounter.get(Suit.Spades)+"-"+p1s+"-"+knownCounterArray[11]+"), "+knownCounterArray[7]+")");							
							}
							long p2Count = ArithmeticUtils.binomialCoefficient(suitCounter.get(Suit.Clubs), c);
							p2Count *= ArithmeticUtils.binomialCoefficient(suitCounter.get(Suit.Diamonds), d);
							p2Count *= ArithmeticUtils.binomialCoefficient(suitCounter.get(Suit.Hearts), h);
							p2Count *= ArithmeticUtils.binomialCoefficient(suitCounter.get(Suit.Spades), s);
							
							//System.out.println("Using P3 has "+p3c+"C "+p3d+"D "+p3s+"S "+p3h+"H");
							//System.out.println("this adds "+p1Count*p2Count+" combos");

							boolean countHeartsPlayableCombos = false;
							if(h > 0 && cardsAlreadyPlayed < 2)
								countHeartsPlayableCombos = determineHeartsPlayable(suit, c, d, s);
							if(!countHeartsPlayableCombos && p1h > 0)
								countHeartsPlayableCombos = determineHeartsPlayable(suit, p1c, p1d, p1s);
							if(!countHeartsPlayableCombos && p3h > 0 && cardsAlreadyPlayed == 0)
								countHeartsPlayableCombos = determineHeartsPlayable(suit, p3c, p3d, p3s);
							co.addTotalCombos(p1Count*p2Count);
							if(countHeartsPlayableCombos)
								co.addWithHeartsNoSuitCombos(p1Count*p2Count);
							else if(!qosPlayed)
							{
								boolean doQosCount = false;
								if(s > 0 && cardsAlreadyPlayed < 2 && determineQosPlayable(suit, c, d))
									doQosCount = true;
								if(p3s > 0 && cardsAlreadyPlayed == 2 && determineQosPlayable(suit, p3c, p3d))
									doQosCount = true;
								if(p1s > 0 && determineQosPlayable(suit, p1c, p1d))
									doQosCount = true;
								if(doQosCount)
								{
									long qosVariations = 1;
									long totalSpadeVariations = getTotalCardCombinationsForSuit(Suit.Spades, hand, muck);
									if(determineQosPlayable(suit, p1c, p1d))
										qosVariations *= ArithmeticUtils.binomialCoefficient(suitCounter.get(Suit.Spades), p1s);
									if(cardsAlreadyPlayed < 2 && determineQosPlayable(suit, c, d))
										qosVariations *= ArithmeticUtils.binomialCoefficient(suitCounter.get(Suit.Spades), s);
									if(cardsAlreadyPlayed < 3 && determineQosPlayable(suit, p3c, p3d))
										qosVariations *= ArithmeticUtils.binomialCoefficient(suitCounter.get(Suit.Spades), p3s);
									co.addWithHeartsNoSuitCombos(p1Count*p2Count*(qosVariations/totalSpadeVariations));
								}

							}
							//System.out.println("------");
						}
					}
				}
			}
		}	
	}


	private static boolean determineQosPlayable(Suit suit, int c, int d) {
		if((suit == Suit.Clubs) && c == 0)
			return true;
		if((suit == Suit.Diamonds) && d == 0)
			return true;		
		return false;
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
 * Because it is possible for totalCombos to exceed Long.MAX_VALUE in some
 * cases, in those instances, we are actually using this to count the number
 * of times it was exceeded, and then when comparing the values, instead
 * of doing a straight withHeartsNoSuitCombos/totalCombos, you can do 
 * ((withHeartsNoSuitCombo/Long.MAX_VALUE)+withHeartsExceedCounter)/
 * ((totalCombos/Long.MAX_VALUE)+totalCombosExceedCounter), which then
 * compares how many MAX_LONGS each of the values are instead of the actual
 * counts
 * 
 * @author Neil Stickels
 *
 */
class CounterObject 
{
	private long totalCombos = 0;
	private long withHeartsNoSuitCombos = 0;
	
	private int totalCombosExceedCounter = 0;
	private int withHeartsExceedCounter = 0;
	/**
	 * @return the totalCombos
	 */
	public long getTotalCombos() {
		return totalCombos;
	}
	
	/**
	 * Add in another batch of combinations in which a player could have
	 * no cards of the suit and that they do have hearts.  There is some
	 * extra logic here for in case this exceeds MAX_LONG, which shouldn't
	 * happen in this case, but it did for getTotalCombos, so just in case
	 * I put that logic in here as well.  If it does exceed MAX_LONG, then
	 * increment the withHeartsExceedCounter to count the number of times it
	 * exceeded MAX_LONG
	 * @param l the number of combinations to add
	 */
	public void addWithHeartsNoSuitCombos(long l) {
		withHeartsNoSuitCombos+=l;
		if(withHeartsNoSuitCombos < 0)
		{
			withHeartsNoSuitCombos = withHeartsNoSuitCombos + Long.MAX_VALUE + 2;
			withHeartsExceedCounter++;
		}

	}
	
	/**
	 * Add in another batch of combinations for how all of the cards could
	 * be dealt between the players.  There is some extra logic here in case 
	 * this exceeds MAX_LONG.  If it does exceed MAX_LONG, then
	 * increment the totalCombosExceedCounter to count the number of times it
	 * exceeded MAX_LONG
	 * @param l the number of combinations to add
	 */
	public void addTotalCombos(long l) {
		totalCombos+=l;
		if(totalCombos < 0)
		{
			totalCombos = totalCombos + Long.MAX_VALUE + 2;
			totalCombosExceedCounter++;
		}
	}
	
	/**
	 * @return the withHeartsNoSuitCombo
	 */
	public long getWithHeartsNoSuitCombos() {
		return withHeartsNoSuitCombos;
	}

	/**
	 * @return the totalCombosExceedCounter
	 */
	public int getTotalCombosExceedCounter() {
		return totalCombosExceedCounter;
	}

	/**
	 * @return the withHeartsExceedCounter
	 */
	public int getWithHeartsExceedCounter() {
		return withHeartsExceedCounter;
	}
	
}


