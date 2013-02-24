/**
 * 
 */
package edu.txstate.hearts.model;

import java.util.Comparator;

/**
 * @author Neil Stickels, I Gede Sutapa
 *
 */
public class Card
{
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		Card card = (Card)obj;
		return card.getFace() == getFace() && card.getSuit() == getSuit();
	}

	public static enum Face { Deuce, Three, Four, Five, Six
		, Seven, Eight, Nine, Ten, Jack, Queen, King, Ace };
		
	public static enum Suit { Clubs, Diamonds, Hearts, Spades };
	
	private final Face face;
	private final Suit suit;
	
	public Card(Face cardFace, Suit cardSuit)
	{
		this.face = cardFace;
		this.suit = cardSuit;
	}
	
	public Face getFace()
	{
		return this.face;
	}
	
	public Suit getSuit()
	{
		return this.suit;
	}
	
	public String toString()
	{
		return String.format("%s of %s", face, suit);
	}
}
