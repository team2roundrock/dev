/**
 * 
 */
package edu.txstate.hearts.utils;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import edu.txstate.hearts.model.AgentAggressive;
import edu.txstate.hearts.model.AgentDetermined;
import edu.txstate.hearts.model.Card;
import edu.txstate.hearts.model.Card.Face;
import edu.txstate.hearts.model.Card.Suit;
import edu.txstate.hearts.model.Deck;
import edu.txstate.hearts.model.Player;

/**
 * @author Neil Stickels
 *
 */
public class ProbabilityUtilsTest {

	/**
	 * Test method for {@link edu.txstate.hearts.utils.ProbabilityUtils#getProbabilityEveryoneHasOneOfASuit(edu.txstate.hearts.model.Card.Suit, java.util.List, java.util.List)}.
	 */
	@Test
	public void testGetProbabilityEveryoneHasOneOfASuit() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link edu.txstate.hearts.utils.ProbabilityUtils#getTotalCardCombinationsWithForSuitAtLeastOne(edu.txstate.hearts.model.Card.Suit, java.util.List, java.util.List)}.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testGetTotalCardCombinationsWithForSuitAtLeastOne() {
		Player p = new AgentDetermined("Tester", 0);
		long num;
		p.addCard(new Card(Face.Deuce, Suit.Clubs));
		p.addCard(new Card(Face.Three, Suit.Clubs));
		p.addCard(new Card(Face.Four, Suit.Clubs));
		p.addCard(new Card(Face.Five, Suit.Clubs));
		p.addCard(new Card(Face.Six, Suit.Clubs));
		p.addCard(new Card(Face.Seven, Suit.Clubs));
		p.addCard(new Card(Face.Eight, Suit.Clubs));
		p.addCard(new Card(Face.Nine, Suit.Clubs));
		p.addCard(new Card(Face.Ten, Suit.Clubs));
		p.addCard(new Card(Face.Jack, Suit.Clubs));
		p.addCard(new Card(Face.Queen, Suit.Clubs));
		p.addCard(new Card(Face.King, Suit.Clubs));
		p.addCard(new Card(Face.Ace, Suit.Clubs));
		num = ProbabilityUtils.getTotalCardCombinationsWithForSuitAtLeastOne(Suit.Clubs, p.getHand(), Collections.EMPTY_SET);
		assertEquals("Didn't get the right number", 0, num);
		p.getHand().remove(p.getHand().get(0));
		p.addCard(new Card(Face.Ace, Suit.Diamonds));
		num = ProbabilityUtils.getTotalCardCombinationsWithForSuitAtLeastOne(Suit.Clubs, p.getHand(), Collections.EMPTY_SET);
		assertEquals("Didn't get the right number", 0, num);
		p.getHand().remove(p.getHand().get(0));
		p.addCard(new Card(Face.Ace, Suit.Diamonds));
		num = ProbabilityUtils.getTotalCardCombinationsWithForSuitAtLeastOne(Suit.Clubs, p.getHand(), Collections.EMPTY_SET);
		assertEquals("Didn't get the right number", 0, num);
		p.getHand().remove(p.getHand().get(0));
		p.addCard(new Card(Face.Ace, Suit.Diamonds));
		num = ProbabilityUtils.getTotalCardCombinationsWithForSuitAtLeastOne(Suit.Clubs, p.getHand(), Collections.EMPTY_SET);
		assertEquals("Didn't get the right number", 6, num);
		p.getHand().remove(p.getHand().get(0));
		p.addCard(new Card(Face.Ace, Suit.Diamonds));
		num = ProbabilityUtils.getTotalCardCombinationsWithForSuitAtLeastOne(Suit.Clubs, p.getHand(), Collections.EMPTY_SET);
		assertEquals("Didn't get the right number", 36, num);		
		p.getHand().remove(p.getHand().get(0));
		p.addCard(new Card(Face.Ace, Suit.Diamonds));
		num = ProbabilityUtils.getTotalCardCombinationsWithForSuitAtLeastOne(Suit.Clubs, p.getHand(), Collections.EMPTY_SET);
		assertEquals("Didn't get the right number", 150, num);
		p.getHand().remove(p.getHand().get(0));
		p.addCard(new Card(Face.Ace, Suit.Diamonds));
		num = ProbabilityUtils.getTotalCardCombinationsWithForSuitAtLeastOne(Suit.Clubs, p.getHand(), Collections.EMPTY_SET);
		assertEquals("Didn't get the right number", 540, num);
		p.getHand().remove(p.getHand().get(0));
		p.addCard(new Card(Face.Ace, Suit.Diamonds));
		num = ProbabilityUtils.getTotalCardCombinationsWithForSuitAtLeastOne(Suit.Clubs, p.getHand(), Collections.EMPTY_SET);
		assertEquals("Didn't get the right number", 1806, num);
		p.getHand().remove(p.getHand().get(0));
		p.addCard(new Card(Face.Ace, Suit.Diamonds));
		num = ProbabilityUtils.getTotalCardCombinationsWithForSuitAtLeastOne(Suit.Clubs, p.getHand(), Collections.EMPTY_SET);
		assertEquals("Didn't get the right number", 5796, num);
		p.getHand().remove(p.getHand().get(0));
		p.addCard(new Card(Face.Ace, Suit.Diamonds));
		num = ProbabilityUtils.getTotalCardCombinationsWithForSuitAtLeastOne(Suit.Clubs, p.getHand(), Collections.EMPTY_SET);
		assertEquals("Didn't get the right number", 18150, num);
		p.getHand().remove(p.getHand().get(0));
		p.addCard(new Card(Face.Ace, Suit.Diamonds));
		num = ProbabilityUtils.getTotalCardCombinationsWithForSuitAtLeastOne(Suit.Clubs, p.getHand(), Collections.EMPTY_SET);
		assertEquals("Didn't get the right number", 55980, num);
		p.getHand().remove(p.getHand().get(0));
		p.addCard(new Card(Face.Ace, Suit.Diamonds));
		num = ProbabilityUtils.getTotalCardCombinationsWithForSuitAtLeastOne(Suit.Clubs, p.getHand(), Collections.EMPTY_SET);
		assertEquals("Didn't get the right number", 171006, num);
		p.getHand().remove(p.getHand().get(0));
		p.addCard(new Card(Face.Ace, Suit.Diamonds));
		num = ProbabilityUtils.getTotalCardCombinationsWithForSuitAtLeastOne(Suit.Clubs, p.getHand(), Collections.EMPTY_SET);
		assertEquals("Didn't get the right number", 519156, num);
		p.getHand().remove(p.getHand().get(0));
		p.addCard(new Card(Face.Ace, Suit.Diamonds));
		num = ProbabilityUtils.getTotalCardCombinationsWithForSuitAtLeastOne(Suit.Clubs, p.getHand(), Collections.EMPTY_SET);
		assertEquals("Didn't get the right number", 1569750, num);
	}

	/**
	 * Test method for {@link edu.txstate.hearts.utils.ProbabilityUtils#getTotalCardCombinationsForSuit(edu.txstate.hearts.model.Card.Suit, java.util.List, java.util.List)}.
	 */
	@Test
	public void testGetTotalCardCombinationsForSuit() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link edu.txstate.hearts.utils.ProbabilityUtils#getProbabilityNoneOfSuitAndHasHearts(java.util.List, edu.txstate.hearts.model.Card.Suit, java.util.List)}.
	 */
	@Test
	public void testGetProbabilityNoneOfSuitAndHasHearts() {
		Player p = new AgentAggressive("Tester", 0);
		double num;
		// for the first test, I want to put almost everything in the muck
		// but the aces.  Give me the ace of clubs.  So one player should
		// have the ace of diamonds, one the ace of spades and one the
		// ace of hearts, so the ace of hearts should be playable
		Set<Card> muck = new HashSet<Card>();
		Deck deck = new Deck();
		deck.shuffleCards();
		for(int i = 0; i < 52; i++)
		{
		  Card c = deck.dealCard();
		  if(c.getFace().ordinal() < 12)
				muck.add(c);
		  else if(c.getSuit() == Suit.Clubs)
			  p.addCard(c);
		}
		p.addPlayedCards(muck, false, 0);
		num = ProbabilityUtils.getProbabilityNoneOfSuitAndHasHearts(p.getHand(), Suit.Clubs, p.getPlayedCards(), p.getInPlayCards(), p.getKnownCards(), p.getKnownEmpties(), true);
		assertEquals(1d, num, 0.0001);
		// trying that again, but this time, giving everyone two cards, aces
		// and kings.  my player is going to have the king of hearts, leaving
		// 1 heart in 6.
		muck.clear();
		p.clearPlayedCards();
		p.getHand().clear();
		deck = new Deck();
		deck.shuffleCards();
		for(int i = 0; i < 52; i++)
		{
		  Card c = deck.dealCard();
		  if(c.getFace().ordinal() < 11)
				muck.add(c);
		  else if(c.getFace() == Face.Ace && c.getSuit() == Suit.Clubs)
			  p.addCard(c);
		  else if(c.getFace() == Face.King && c.getSuit() == Suit.Hearts)
			  p.addCard(c);
		}
		p.addPlayedCards(muck, false, 0);
		num = ProbabilityUtils.getProbabilityNoneOfSuitAndHasHearts(p.getHand(), Suit.Clubs, p.getPlayedCards(), p.getInPlayCards(), p.getKnownCards(), p.getKnownEmpties(), true);
		assertEquals(0.5070, num, 0.0001);
		// now let's say that the first person has played the King of Clubs,
		// which takes that card out of the mix
		List<Card> playedCards = new ArrayList<Card>();
		playedCards.add(new Card(Face.King, Suit.Clubs));
		num = ProbabilityUtils.getProbabilityNoneOfSuitAndHasHearts(p.getHand(), Suit.Clubs, p.getPlayedCards(), playedCards, p.getKnownCards(), p.getKnownEmpties(), true);
		assertEquals(0.6897, num, 0.0001);
		// now let's pretend that both the Ace of Spades and the Ace of Diamonds have been played
		playedCards.add(new Card(Face.Ace, Suit.Diamonds));
		num = ProbabilityUtils.getProbabilityNoneOfSuitAndHasHearts(p.getHand(), Suit.Clubs, p.getPlayedCards(), playedCards, p.getKnownCards(), p.getKnownEmpties(), true);
		assertEquals(0.4444, num, 0.0001);
		// if we remove that Ace of Diamonds from the second person, and say
		// it was the Ace of Hearts, then this should return that there is a
		// probability of 1 that someone plays a heart
		playedCards.remove(1);
		playedCards.add(new Card(Face.Ace, Suit.Hearts));
		num = ProbabilityUtils.getProbabilityNoneOfSuitAndHasHearts(p.getHand(), Suit.Clubs, p.getPlayedCards(), playedCards, p.getKnownCards(), p.getKnownEmpties(), true);
		assertEquals(1d, num, 0.0001);
		// have the last person play the King of Diamonds, this should return 1
		playedCards.add(new Card(Face.King, Suit.Diamonds));
		num = ProbabilityUtils.getProbabilityNoneOfSuitAndHasHearts(p.getHand(), Suit.Clubs, p.getPlayedCards(), playedCards, p.getKnownCards(), p.getKnownEmpties(), true);
		assertEquals(1d, num, 0.0001);
		// change the 2nd card back to the Ace of Diamonds, this should return 0 now
		playedCards.remove(1);
		playedCards.add(new Card(Face.Ace, Suit.Diamonds));
		num = ProbabilityUtils.getProbabilityNoneOfSuitAndHasHearts(p.getHand(), Suit.Clubs, p.getPlayedCards(), playedCards, p.getKnownCards(), p.getKnownEmpties(), true);
		assertEquals(0d, num, 0.0001);
		
		
		
	}
	
	@Test
	public void testBadGetProbabilityNoneOfSuitHasHearts()
	{
		//hand - [Deuce of Spades, Six of Spades, Six of Hearts, Seven of Spades, 
		//        Eight of Hearts, Eight of Diamonds, Nine of Diamonds, Nine of Spades, 
		//        Jack of Clubs, Jack of Diamonds, King of Diamonds, King of Clubs, 
		//        Ace of Diamonds] 
		//suit - Clubs 
		//cardsPlayedThisTurn - [Deuce of Clubs]
		//knownCards - [[King of Spades, Queen of Spades, Queen of Diamonds], [], [Deuce of Clubs]] 
		//qosPlayed - false
		//muck - []
		Player p = new AgentAggressive("Test", 0);
		p.addCard(new Card(Face.Deuce, Suit.Spades));
		p.addCard(new Card(Face.Six, Suit.Spades));
		p.addCard(new Card(Face.Six, Suit.Hearts));
		p.addCard(new Card(Face.Seven, Suit.Spades));
		p.addCard(new Card(Face.Eight, Suit.Hearts));
		p.addCard(new Card(Face.Eight, Suit.Diamonds));
		p.addCard(new Card(Face.Nine, Suit.Diamonds));
		p.addCard(new Card(Face.Nine, Suit.Spades));
		p.addCard(new Card(Face.Jack, Suit.Clubs));
		p.addCard(new Card(Face.Jack, Suit.Diamonds));
		p.addCard(new Card(Face.King, Suit.Diamonds));
		p.addCard(new Card(Face.King, Suit.Clubs));
		p.addCard(new Card(Face.Ace, Suit.Diamonds));
		List<Set<Card>> knownCards = new ArrayList<Set<Card>>();
		Set<Card> set1 = new HashSet<Card>();
		set1.add(new Card(Face.King, Suit.Spades));
		set1.add(new Card(Face.Queen, Suit.Spades));
		set1.add(new Card(Face.Queen, Suit.Diamonds));
		knownCards.add(set1);
		knownCards.add(new HashSet<Card>());
		knownCards.add(new HashSet<Card>());
		List<Card> playedCards = new ArrayList<Card>();
		playedCards.add(new Card(Face.Deuce, Suit.Clubs));
		double num = ProbabilityUtils.getProbabilityNoneOfSuitAndHasHearts(p.getHand(), Suit.Clubs, Collections.<Card> emptySet(), playedCards, knownCards, p.getKnownEmpties(), false);
		System.out.println("num is "+num);
	}

	@Test
	public void testBadGetProbabilityNoneOfSuitHasHearts2()
	{
		//hand - [[Deuce of Hearts, Deuce of Diamonds, Three of Diamonds, 
		//         Nine of Diamonds, Nine of Hearts]] 
		//suit - Diamonds 
		//cardsPlayedThisTurn - []
		//knownCards - [[Ten of Clubs, Queen of Clubs], [], []] 
		//qosPlayed - false
		//muck - [Jack of Diamonds, Ten of Diamonds, Queen of Hearts, 
		//        Nine of Spades, Ace of Clubs, Three of Clubs, Three of Spades, 
		//        King of Clubs, Six of Clubs, Eight of Spades, 
		//        Five of Diamonds, Three of Hearts, Ten of Hearts, 
		//        Deuce of Spades, Jack of Hearts, Four of Spades, 
		//        Six of Hearts, Ace of Diamonds, Deuce of Clubs, 
		//        Eight of Clubs, Four of Diamonds, Five of Hearts, 
		//        Eight of Diamonds, Four of Clubs, Nine of Clubs, 
		//        Five of Clubs, King of Diamonds, King of Hearts, 
		//        Six of Diamonds, Six of Spades, Five of Spades, 
		//        Four of Hearts]
		//knownEmpties - [[], [Clubs], []]
		Set<Card> muck = new HashSet<Card>();
		muck.add(new Card(Face.Jack, Suit.Diamonds));
		muck.add(new Card(Face.Ten, Suit.Diamonds));
		muck.add(new Card(Face.Queen, Suit.Hearts));
		muck.add(new Card(Face.Nine, Suit.Spades));
		muck.add(new Card(Face.Ace, Suit.Clubs));
		muck.add(new Card(Face.Three, Suit.Clubs));
		muck.add(new Card(Face.Three, Suit.Spades));
		muck.add(new Card(Face.King, Suit.Clubs));
		muck.add(new Card(Face.Six, Suit.Clubs));
		muck.add(new Card(Face.Eight, Suit.Spades));
		muck.add(new Card(Face.Five, Suit.Diamonds));
		muck.add(new Card(Face.Three, Suit.Hearts));
		muck.add(new Card(Face.Ten, Suit.Hearts));
		muck.add(new Card(Face.Deuce, Suit.Spades));
		muck.add(new Card(Face.Jack, Suit.Hearts));
		muck.add(new Card(Face.Four, Suit.Spades));
		muck.add(new Card(Face.Six, Suit.Hearts));
		muck.add(new Card(Face.Ace, Suit.Diamonds));
		muck.add(new Card(Face.Deuce, Suit.Clubs));
		muck.add(new Card(Face.Eight, Suit.Clubs));
		muck.add(new Card(Face.Four, Suit.Diamonds));
		muck.add(new Card(Face.Five, Suit.Hearts));
		muck.add(new Card(Face.Eight, Suit.Diamonds));
		muck.add(new Card(Face.Four, Suit.Clubs));
		muck.add(new Card(Face.Nine, Suit.Clubs));
		muck.add(new Card(Face.Five, Suit.Clubs));
		muck.add(new Card(Face.King, Suit.Diamonds));
		muck.add(new Card(Face.King, Suit.Hearts));
		muck.add(new Card(Face.Six, Suit.Diamonds));
		muck.add(new Card(Face.Six, Suit.Spades));
		muck.add(new Card(Face.Five, Suit.Spades));
		muck.add(new Card(Face.Four, Suit.Hearts));
		Player p = new AgentAggressive("Test", 0);
		p.addCard(new Card(Face.Deuce, Suit.Hearts));
		p.addCard(new Card(Face.Deuce, Suit.Diamonds));
		p.addCard(new Card(Face.Three, Suit.Diamonds));
		p.addCard(new Card(Face.Nine, Suit.Diamonds));
		p.addCard(new Card(Face.Nine, Suit.Hearts));
		List<Set<Card>> knownCards = new ArrayList<Set<Card>>();
		Set<Card> set1 = new HashSet<Card>();
		set1.add(new Card(Face.Ten, Suit.Clubs));
		set1.add(new Card(Face.Queen, Suit.Clubs));
		knownCards.add(set1);
		knownCards.add(new HashSet<Card>());
		knownCards.add(new HashSet<Card>());
		List<Card> playedCards = new ArrayList<Card>();
		List<Set<Suit>> knownEmpties = new ArrayList<Set<Suit>>();
		Set<Suit> set = new HashSet<Suit>();
		set.add(Suit.Clubs);
		knownEmpties.add(new HashSet<Suit>());
		knownEmpties.add(set);
		knownEmpties.add(new HashSet<Suit>());

		
		double num = ProbabilityUtils.getProbabilityNoneOfSuitAndHasHearts(p.getHand(), Suit.Diamonds, muck, playedCards, knownCards, knownEmpties, false);
		System.out.println("num is "+num);
	}


}
