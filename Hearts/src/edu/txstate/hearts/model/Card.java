/**
 * 
 */
package edu.txstate.hearts.model;

/**
 * @author Neil Stickels, I Gede Sutapa
 *
 */
public class Card {

	public static enum Face { Ace, Deuce, Three, Four, Five, Six
		, Seven, Eight, Nine, Ten, Jack, Queen, King};
		
	public static enum Suit { Clubs, Diamonds, Hearts, Spades};
	
	private final Face face;
	private final Suit suit;
	private int pointValue;
	
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
	
	public int getPointValue()
	{
		return this.pointValue;
	}
	
	public String toString()
	{
		return String.format("%s of %s", face, suit);
	}
}
