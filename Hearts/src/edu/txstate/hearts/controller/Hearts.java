/**
 * 
 */
package edu.txstate.hearts.controller;

import java.util.ArrayList;
import java.util.List;

import edu.txstate.hearts.model.*;

/**
 * @author Neil Stickels
 *
 */
public class Hearts {

	private Deck deck;
	private List<Player> players;
	private int playerToStartRound;
	private int turnsPlayed;
	private boolean heartsBroken;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Hearts game = new Hearts();
		game.initialize();
		game.runGame();
	}
	
	private void initialize() {
		// TODO Auto-generated method stub
		deck = new Deck();
		deck.shuffleCards();
		deck.printCards();
		System.out.println("");
		
		players = new ArrayList<Player>(4);
		Player player1 = new Agent("Gede");
		Player player2 = new Agent("Neil");
		Player player3 = new Agent("Jonathan");
		Player player4 = new Agent("Maria");
		
		players.add(player1);
		players.add(player2);
		players.add(player3);
		players.add(player4);
		
		for(int i = 0; i < 13; i++)
		{
			for(int j = 0; j < players.size(); j++)
			{
				players.get(j).addCard(deck.dealCard());
			}
		}
		
		for(int i = 0; i < players.size(); i++)
		{
			Player player = players.get(i);
			System.out.println(player.getName());
			player.printHand();
			System.out.println("");
			System.out.println("");
		}
		
		playerToStartRound = 0;
		turnsPlayed = 0;
		heartsBroken = false;
		
	}
	
	private void runGame()
	{
		int playerToStartRound = findPlayerToStart();
		System.out.println("Player "+players.get(playerToStartRound).getName()+" goes first");
		while(turnsPlayed < 13)
		{
			playerToStartRound = runTurn(playerToStartRound);
			System.out.println("=============== round "+(turnsPlayed+1)+" done ================");
			turnsPlayed++;
		}
	}
	
	private int runTurn(int num) {
		List<Card> cardsPlayed = new ArrayList<Card>(4);
		boolean first = true;
		while(cardsPlayed.size() < 4)
		{
		  Player p = players.get(num);
		  Card c = p.playCard(cardsPlayed, heartsBroken, (first && turnsPlayed == 0));
		  first = false;
		  System.out.println("Player "+p.getName()+" played "+c);
		  cardsPlayed.add(c);
		  num++;
		  num%=4;
		}
		return (num+2)%4;
	}

	// we will need to add the stuff for managing the game, determining
	// whose turn it is, what they can play, etc.

	private int findPlayerToStart() {
		for(int i = 0; i < players.size(); i++)
		{
			Player p = players.get(i);
			if(p.hasTwoOfClubs())
				return i;
		}
		throw new RuntimeException("Couldn't find the 2 of clubs");
	}

}
