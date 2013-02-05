/**
 * 
 */
package edu.txstate.hearts.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import edu.txstate.hearts.model.Card;

/**
 * @author Neil Stickels, I Gede Sutapa
 *
 */
public class Deck {

	private List<Card> cards; //array list of Card objects
	private int currentCard; //index of next Card to be dealt (0-51)
	private static final int NUMBER_OF_CARDS = 52; //constant number of Cards
	
	public Deck()
	{
		Card[] deck = new Card[NUMBER_OF_CARDS];
		int count = 0;
		
		for(Card.Suit suit : Card.Suit.values())
		{
			for(Card.Face face : Card.Face.values())
			{
				deck[count] = new Card(face, suit);
				++count;
			}
		}
		
		cards = Arrays.asList(deck);
		Collections.shuffle(cards);
	}
	
	public Card dealCard()
	{
		//determine whether Cards remain to be dealt
		if(currentCard < cards.size())
			return cards.get(currentCard++); //return current card
		else
			return null; //return null to indicate that all Cards were dealt
	}
	
	public void printCards()
	{
		for(int i = 0; i < cards.size(); i++)
		{
			System.out.printf("%-19s%s", cards.get(i),
					((i+1)%4 == 0) ? "\n" : "");
		}
	}

}
