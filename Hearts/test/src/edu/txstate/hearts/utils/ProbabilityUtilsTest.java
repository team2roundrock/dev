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
import edu.txstate.hearts.model.AgentGoofy;
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
		Player p = new AgentGoofy("tester", 0);
		// first try giving everyone no cards, which should mean there is 0 
		// probability everyone has one of that suit
		Set<Card> muck = new HashSet<Card>();
		Deck d = new Deck();
		d.shuffleCards();
		for(Card c: d.getCards())
		{
			muck.add(c);
		}
		assertEquals(0d, ProbabilityUtils.getProbabilityEveryoneHasOneOfASuit(Suit.Clubs, new ArrayList<Card>(), muck), .001);
		// now give everyone exactly 1 of a suit
		// clear the muck
		muck.clear();
		
		Card c1 = new Card(Face.Deuce, Suit.Clubs);
		p.addCard(c1);
		Card c2 = new Card(Face.Three, Suit.Clubs);
		Card c3 = new Card(Face.Four, Suit.Clubs);
		Card c4 = new Card(Face.Five, Suit.Clubs);
		d.shuffleCards();
		for(Card c: d.getCards())
		{
			if(!c.equals(c1) && !c.equals(c2) && !c.equals(c3) && !c.equals(c4))
				muck.add(c);
		}
		// if everyone has exactly one club, probability should be 1
		assertEquals(1d, ProbabilityUtils.getProbabilityEveryoneHasOneOfASuit(Suit.Clubs, p.getHand(), muck), .001);
		// now give everyone one spade as well
		Card s1 = new Card(Face.Deuce, Suit.Spades);
		p.addCard(s1);
		Card s2 = new Card(Face.Three, Suit.Spades);
		Card s3 = new Card(Face.Four, Suit.Spades);
		Card s4 = new Card(Face.Five, Suit.Spades);	
		d.shuffleCards();
		for(Card c: d.getCards())
		{
			if(!c.equals(c1) && !c.equals(c2) && !c.equals(c3) && !c.equals(c4)
					&& !c.equals(s1) && !c.equals(s2) && !c.equals(s3) && !c.equals(s4))
				muck.add(c);			
		}
		// To figure out total ways:
		// Opp1 could have either 0, 1 or 2 clubs:
		// 	if Opp1 has 0, then:
		// 		Opp2 could have 1 or 2 (he cant have 0):
		//			If Opp2 has 1, Opp3 has 2, ways for that:  3C0*3C1*2C2=3
		//			If Opp2 has 2, Opp3 has 1, ways for that:  3C0*3C2*1C1=3
		//	if Opp1 has 1, then:
		//		Opp2 could have 0, 1 or 2:
		//			If Opp2 has 0, Opp3 has 2, ways for that: 3C1*2C0*2C2=3
		//			If Opp2 has 1, Opp3 has 1, ways for that: 3C1*2C1*1C1=6
		//			If Opp2 has 2, Opp3 has 0, ways for that: 3C1*2C2*0C0=3
		//	if Opp2 has 2, then:
		//		Opp1 could have 0 or 1:
		//			If Opp2 has 0, Opp3 has 1, ways for that: 3C2*1C0*1C1=3
		//			If Opp2 has 1, Opp3 has 0, ways for that: 3C2*1C1*0C0=3
		// adding up all of those ways yields 24 total ways to give the 3 
		// clubs to the other 3 players when there are 6 total cards
		// there are 3C1 (3) * 2C1 (2) * 1C1 (1) or 6 ways for each player
		// to have 1 club, so P(everyone has one) = 6/24 = .25
		assertEquals(0.25, ProbabilityUtils.getProbabilityEveryoneHasOneOfASuit(Suit.Clubs, p.getHand(), muck), 0.001);
		
	}

	/**
	 * Test method for {@link edu.txstate.hearts.utils.ProbabilityUtils#getTotalCardCombinationsWithForSuitAtLeastOne(edu.txstate.hearts.model.Card.Suit, java.util.List, java.util.List)}.
	 */
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
		// make sure knownEmpties is setup right, i know they don't have
		// any clubs but that is all i know
		List<Set<Suit>> knownEmpties = new ArrayList<Set<Suit>>();
		Set<Suit> set1 = new HashSet<Suit>();
		set1.add(Suit.Clubs);
		Set<Suit> set2 = new HashSet<Suit>();
		set2.add(Suit.Clubs);
		Set<Suit> set3 = new HashSet<Suit>();
		set3.add(Suit.Clubs);
		knownEmpties.add(set1);
		knownEmpties.add(set2);
		knownEmpties.add(set3);
		num = ProbabilityUtils.getProbabilityNoneOfSuitAndHasHearts(p.getHand(), Suit.Clubs, p.getPlayedCards(), p.getInPlayCards(), p.getKnownCards(), knownEmpties, true);
		assertEquals(1d, num, 0.0001);
		// trying that again, but this time, giving everyone two cards, aces
		// and kings.  my player is going to have the king of hearts, leaving
		// 1 heart in 6.  In this case, there is 
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
		// now known empties is blank, since there are no known empties
		p.addPlayedCards(muck, false, 0);
		knownEmpties.get(0).clear();
		knownEmpties.get(1).clear();
		knownEmpties.get(2).clear();
		// in this case, there are 112 combinations where someone could have no
		// clubs, but also have a heart
		// there are 142 combinations of ways to deal those cards
		num = ProbabilityUtils.getProbabilityNoneOfSuitAndHasHearts(p.getHand(), Suit.Clubs, p.getPlayedCards(), p.getInPlayCards(), p.getKnownCards(), knownEmpties, true);
		assertEquals(112d/142d, num, 0.0001);
		// now let's say that the first person has played the King of Clubs,
		// which takes that card out of the mix
		List<Card> playedCards = new ArrayList<Card>();
		playedCards.add(new Card(Face.King, Suit.Clubs));
		// now there are only 58 total ways the cards could be distributed
		// and 40 of those where someone doesn't have clubs, but has a heart
		num = ProbabilityUtils.getProbabilityNoneOfSuitAndHasHearts(p.getHand(), Suit.Clubs, p.getPlayedCards(), playedCards, p.getKnownCards(), knownEmpties, true);
		assertEquals(40d/58d, num, 0.0001);
		// now let's pretend that both the King of Clubs and the Ace of Diamonds have been played
		playedCards.add(new Card(Face.Ace, Suit.Diamonds));
		// now there are only 27 total ways the cards could be distributed
		// and 12 of those in which someone doesn't have a club, but has a heart
		num = ProbabilityUtils.getProbabilityNoneOfSuitAndHasHearts(p.getHand(), Suit.Clubs, p.getPlayedCards(), playedCards, p.getKnownCards(), knownEmpties, true);
		assertEquals(12d/27d, num, 0.0001);
		// if we remove that Ace of Diamonds from the second person, and say
		// it was the Ace of Hearts, then this should return that there is a
		// probability of 1 that someone plays a heart
		playedCards.remove(1);
		playedCards.add(new Card(Face.Ace, Suit.Hearts));
		num = ProbabilityUtils.getProbabilityNoneOfSuitAndHasHearts(p.getHand(), Suit.Clubs, p.getPlayedCards(), playedCards, p.getKnownCards(), knownEmpties, true);
		assertEquals(1d, num, 0.0001);
		// have the last person play the King of Diamonds, this should still return 1
		playedCards.add(new Card(Face.King, Suit.Diamonds));
		num = ProbabilityUtils.getProbabilityNoneOfSuitAndHasHearts(p.getHand(), Suit.Clubs, p.getPlayedCards(), playedCards, p.getKnownCards(), knownEmpties, true);
		assertEquals(1d, num, 0.0001);
		// change the 2nd card back to the Ace of Diamonds, this should return 0 now
		playedCards.remove(1);
		playedCards.add(new Card(Face.Ace, Suit.Diamonds));
		num = ProbabilityUtils.getProbabilityNoneOfSuitAndHasHearts(p.getHand(), Suit.Clubs, p.getPlayedCards(), playedCards, p.getKnownCards(), knownEmpties, true);
		assertEquals(0d, num, 0.0001);
		
		
		
	}
	
	// these last two tests were used to debug errors I was getting
	
//	@Test
//	public void testBadGetProbabilityNoneOfSuitHasHearts()
//	{
//		//hand - [Deuce of Spades, Six of Spades, Six of Hearts, Seven of Spades, 
//		//        Eight of Hearts, Eight of Diamonds, Nine of Diamonds, Nine of Spades, 
//		//        Jack of Clubs, Jack of Diamonds, King of Diamonds, King of Clubs, 
//		//        Ace of Diamonds] 
//		//suit - Clubs 
//		//cardsPlayedThisTurn - [Deuce of Clubs]
//		//knownCards - [[King of Spades, Queen of Spades, Queen of Diamonds], [], [Deuce of Clubs]] 
//		//qosPlayed - false
//		//muck - []
//		Player p = new AgentAggressive("Test", 0);
//		p.addCard(new Card(Face.Deuce, Suit.Spades));
//		p.addCard(new Card(Face.Six, Suit.Spades));
//		p.addCard(new Card(Face.Six, Suit.Hearts));
//		p.addCard(new Card(Face.Seven, Suit.Spades));
//		p.addCard(new Card(Face.Eight, Suit.Hearts));
//		p.addCard(new Card(Face.Eight, Suit.Diamonds));
//		p.addCard(new Card(Face.Nine, Suit.Diamonds));
//		p.addCard(new Card(Face.Nine, Suit.Spades));
//		p.addCard(new Card(Face.Jack, Suit.Clubs));
//		p.addCard(new Card(Face.Jack, Suit.Diamonds));
//		p.addCard(new Card(Face.King, Suit.Diamonds));
//		p.addCard(new Card(Face.King, Suit.Clubs));
//		p.addCard(new Card(Face.Ace, Suit.Diamonds));
//		List<Set<Card>> knownCards = new ArrayList<Set<Card>>();
//		Set<Card> set1 = new HashSet<Card>();
//		set1.add(new Card(Face.King, Suit.Spades));
//		set1.add(new Card(Face.Queen, Suit.Spades));
//		set1.add(new Card(Face.Queen, Suit.Diamonds));
//		knownCards.add(set1);
//		knownCards.add(new HashSet<Card>());
//		knownCards.add(new HashSet<Card>());
//		List<Card> playedCards = new ArrayList<Card>();
//		playedCards.add(new Card(Face.Deuce, Suit.Clubs));
//		double num = ProbabilityUtils.getProbabilityNoneOfSuitAndHasHearts(p.getHand(), Suit.Clubs, Collections.<Card> emptySet(), playedCards, knownCards, p.getKnownEmpties(), false);
//		System.out.println("num is "+num);
//	}
//
//	@Test
//	public void testBadGetProbabilityNoneOfSuitHasHearts2()
//	{
//		//hand - [[Deuce of Hearts, Deuce of Diamonds, Three of Diamonds, 
//		//         Nine of Diamonds, Nine of Hearts]] 
//		//suit - Diamonds 
//		//cardsPlayedThisTurn - []
//		//knownCards - [[Ten of Clubs, Queen of Clubs], [], []] 
//		//qosPlayed - false
//		//muck - [Jack of Diamonds, Ten of Diamonds, Queen of Hearts, 
//		//        Nine of Spades, Ace of Clubs, Three of Clubs, Three of Spades, 
//		//        King of Clubs, Six of Clubs, Eight of Spades, 
//		//        Five of Diamonds, Three of Hearts, Ten of Hearts, 
//		//        Deuce of Spades, Jack of Hearts, Four of Spades, 
//		//        Six of Hearts, Ace of Diamonds, Deuce of Clubs, 
//		//        Eight of Clubs, Four of Diamonds, Five of Hearts, 
//		//        Eight of Diamonds, Four of Clubs, Nine of Clubs, 
//		//        Five of Clubs, King of Diamonds, King of Hearts, 
//		//        Six of Diamonds, Six of Spades, Five of Spades, 
//		//        Four of Hearts]
//		//knownEmpties - [[], [Clubs], []]
//		Set<Card> muck = new HashSet<Card>();
//		muck.add(new Card(Face.Jack, Suit.Diamonds));
//		muck.add(new Card(Face.Ten, Suit.Diamonds));
//		muck.add(new Card(Face.Queen, Suit.Hearts));
//		muck.add(new Card(Face.Nine, Suit.Spades));
//		muck.add(new Card(Face.Ace, Suit.Clubs));
//		muck.add(new Card(Face.Three, Suit.Clubs));
//		muck.add(new Card(Face.Three, Suit.Spades));
//		muck.add(new Card(Face.King, Suit.Clubs));
//		muck.add(new Card(Face.Six, Suit.Clubs));
//		muck.add(new Card(Face.Eight, Suit.Spades));
//		muck.add(new Card(Face.Five, Suit.Diamonds));
//		muck.add(new Card(Face.Three, Suit.Hearts));
//		muck.add(new Card(Face.Ten, Suit.Hearts));
//		muck.add(new Card(Face.Deuce, Suit.Spades));
//		muck.add(new Card(Face.Jack, Suit.Hearts));
//		muck.add(new Card(Face.Four, Suit.Spades));
//		muck.add(new Card(Face.Six, Suit.Hearts));
//		muck.add(new Card(Face.Ace, Suit.Diamonds));
//		muck.add(new Card(Face.Deuce, Suit.Clubs));
//		muck.add(new Card(Face.Eight, Suit.Clubs));
//		muck.add(new Card(Face.Four, Suit.Diamonds));
//		muck.add(new Card(Face.Five, Suit.Hearts));
//		muck.add(new Card(Face.Eight, Suit.Diamonds));
//		muck.add(new Card(Face.Four, Suit.Clubs));
//		muck.add(new Card(Face.Nine, Suit.Clubs));
//		muck.add(new Card(Face.Five, Suit.Clubs));
//		muck.add(new Card(Face.King, Suit.Diamonds));
//		muck.add(new Card(Face.King, Suit.Hearts));
//		muck.add(new Card(Face.Six, Suit.Diamonds));
//		muck.add(new Card(Face.Six, Suit.Spades));
//		muck.add(new Card(Face.Five, Suit.Spades));
//		muck.add(new Card(Face.Four, Suit.Hearts));
//		Player p = new AgentAggressive("Test", 0);
//		p.addCard(new Card(Face.Deuce, Suit.Hearts));
//		p.addCard(new Card(Face.Deuce, Suit.Diamonds));
//		p.addCard(new Card(Face.Three, Suit.Diamonds));
//		p.addCard(new Card(Face.Nine, Suit.Diamonds));
//		p.addCard(new Card(Face.Nine, Suit.Hearts));
//		List<Set<Card>> knownCards = new ArrayList<Set<Card>>();
//		Set<Card> set1 = new HashSet<Card>();
//		set1.add(new Card(Face.Ten, Suit.Clubs));
//		set1.add(new Card(Face.Queen, Suit.Clubs));
//		knownCards.add(set1);
//		knownCards.add(new HashSet<Card>());
//		knownCards.add(new HashSet<Card>());
//		List<Card> playedCards = new ArrayList<Card>();
//		List<Set<Suit>> knownEmpties = new ArrayList<Set<Suit>>();
//		Set<Suit> set = new HashSet<Suit>();
//		set.add(Suit.Clubs);
//		knownEmpties.add(new HashSet<Suit>());
//		knownEmpties.add(set);
//		knownEmpties.add(new HashSet<Suit>());
//
//		
//		double num = ProbabilityUtils.getProbabilityNoneOfSuitAndHasHearts(p.getHand(), Suit.Diamonds, muck, playedCards, knownCards, knownEmpties, false);
//		System.out.println("num is "+num);
//	}


}
