/**
 * 
 */
package edu.txstate.hearts.model;

import java.util.ArrayList;
import java.util.List;

import edu.txstate.hearts.controller.Hearts;

/**
 * @author Neil Stickels, I Gede Sutapa
 *
 */
public abstract class Player {
	
	private String name;
	private int score;
	private List<Card> hand;
	
	public Player(String playerName)
	{
		hand = new ArrayList<Card>();
		this.name = playerName;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public int getScore()
	{
		return this.score;
	}
	
	public void addScore(int point)
	{
		this.score += point;
	}
	
	public void addCard(Card card)
	{
		this.hand.add(card);
	}
	
	public void printHand()
	{
		for(int i = 0; i < hand.size(); i++)
		{
			System.out.printf("%-19s%s", hand.get(i),
					((i+1)%4 == 0) ? "\n" : "");
		}
	}
	
	public abstract Card playCard();

}
