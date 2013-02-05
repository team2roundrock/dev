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
	
	public abstract Card playCard();

}
