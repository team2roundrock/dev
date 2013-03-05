/**
 * 
 */
package edu.txstate.hearts.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.txstate.hearts.model.*;
import edu.txstate.hearts.model.Card.Face;
import edu.txstate.hearts.model.Card.Suit;

/**
 * @author Neil Stickels, I Gede Sutapa
 *
 */
public class Hearts {

	public enum Passing
	{
		Left,
		Right,
		Front,
		Stay
	}
	private Deck deck;
	private List<Player> players;
	private int turnsPlayed;
	private boolean heartsBroken; //flag when hearts broken to allow a heart as first card played
	private boolean notifyHeartsBroken; //implement notification "Hearts has been broken"
	private Passing passing;
	private int endScore;
	private final static boolean silent = false;
	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{	
		Hearts game = new Hearts();
		game.initialize();
		game.runGame();
	}
	
	private void initialize() 
	{
		passing = Passing.Left; //initial
		endScore = 100; //default
		
		players = new ArrayList<Player>(4);
		Player player1 = new AgentDetermined("Gede");
		Player player2 = new AgentAggressive("Neil");
		Player player3 = new AgentDetermined("Jonathan");
		Player player4 = new AgentDetermined("Maria");
		
		players.add(player1);
		players.add(player2);
		players.add(player3);
		players.add(player4);
	}
	
	private void shuffleCards()
	{
		deck.shuffleCards();
		
		if(!silent)
		{
			deck.printCards();
			System.out.println("");
		}
	}
	
	private void dealCards()
	{
		//deal cards
		for(int i = 0; i < 13; i++)
		{
			for(int j = 0; j < players.size(); j++)
			{
				players.get(j).addCard(deck.dealCard());
			}
		}
		
		//print each player cards
		for(int i = 0; i < players.size(); i++)
		{
			Player player = players.get(i);
			player.sortCards();
			if(!silent)
			{
				System.out.println(player.getName());
				player.printHand();
				System.out.println("");
				System.out.println("");
			}
		}
	}
	
	private void passingCards()
	{
		if(this.passing == Passing.Stay)
			return;
		
		List<Card> p0CardsToPass = this.players.get(0).getCardsToPass(this.passing);
		List<Card> p1CardsToPass = this.players.get(1).getCardsToPass(this.passing);
		List<Card> p2CardsToPass = this.players.get(2).getCardsToPass(this.passing);
		List<Card> p3CardsToPass = this.players.get(3).getCardsToPass(this.passing);
		
		if(this.passing == Passing.Left)
		{
			this.players.get(0).addCards(p1CardsToPass);
			this.players.get(1).addCards(p2CardsToPass);
			this.players.get(2).addCards(p3CardsToPass);
			this.players.get(3).addCards(p0CardsToPass);
		}
		else if(this.passing == Passing.Right)
		{
			this.players.get(0).addCards(p3CardsToPass);
			this.players.get(1).addCards(p0CardsToPass);
			this.players.get(2).addCards(p1CardsToPass);
			this.players.get(3).addCards(p2CardsToPass);
		}
		else if(this.passing == Passing.Front)
		{
			this.players.get(0).addCards(p2CardsToPass);
			this.players.get(1).addCards(p3CardsToPass);
			this.players.get(2).addCards(p0CardsToPass);
			this.players.get(3).addCards(p1CardsToPass);
		}
		if(!silent)
			System.out.println("=====After passing cards to the "  + this.passing.toString() + "=====");
		//print each player cards
		for(int i = 0; i < players.size(); i++)
		{
			Player player = players.get(i);
			player.sortCards();
			if(!silent)
			{
				System.out.println(player.getName());
				player.printHand();
				System.out.println("");
				System.out.println("");
			}
		}
	}
	
	private void runGame() {
		long start = System.nanoTime();
		int myLossCount = 0;
		int myWinCount = 0;
		for (int n = 0; n < 1; n++) {
			// while we still playing the game
			for(int i = 0; i < players.size(); i++)
			{
				Player p = players.get(i);
				int score = (-1)*p.getScore();
				p.addScore(score);
			}
			while (true) {
				for (int i = 0; i < players.size(); i++) {
					Player p = players.get(i);
					// clear each player cards
					p.clearCards();
				}

				// initialize deck for the game
				deck = new Deck();
				shuffleCards();
				dealCards();
				passingCards();

				// initialize game properties
				turnsPlayed = 0;
				heartsBroken = false;
				notifyHeartsBroken = false;

				// new round, find player to start round
				int playerToStartRound = findPlayerToStart();
				if(!silent)
					System.out.println("Player "
						+ players.get(playerToStartRound).getName()
						+ " goes first");

				// while we still have cards
				while (turnsPlayed < 13) {
					// clear player's in play cards
					for (int i = 0; i < players.size(); i++)
						players.get(i).clearInPlayCards();

					playerToStartRound = runTurn(playerToStartRound);
					if(!silent)
						System.out.println("=============== trick "
							+ (turnsPlayed + 1) + " done ================");
					// every 4 cards played is a "trick", a round is when all
					// cards have been exhausted.
					// changed "round <number> done" to "trick <number> done"
					turnsPlayed++;
				}

				// add scores to each player
				assignScoresToPlayers();

				// check for highest point
				Player playerWithHighestTotalPoints = null;
				Player playerWithLowestTotalPoints = null;
				for (int i = 0; i < players.size(); i++) {
					Player p = players.get(i);
					if (playerWithHighestTotalPoints == null)
					{
						playerWithHighestTotalPoints = p;
						playerWithLowestTotalPoints = p;
					}
					else if (playerWithHighestTotalPoints.getScore() < p
							.getScore())
						playerWithHighestTotalPoints = p;
					else if (playerWithLowestTotalPoints.getScore() > p.getScore())
						playerWithLowestTotalPoints = p;
				}

				// determine if the game ends
				if (playerWithHighestTotalPoints.getScore() >= this.endScore) {
					if(playerWithHighestTotalPoints instanceof AgentAggressive)
						myLossCount++;
					if(playerWithLowestTotalPoints instanceof AgentAggressive)
						myWinCount++;
					if(!silent)
					System.out.println("Player "
							+ playerWithHighestTotalPoints.getName()
							+ " loses with total score of "
							+ playerWithHighestTotalPoints.getScore());
					if(!silent)
					System.out.println("Player "
							+ playerWithLowestTotalPoints.getName()
							+ " wins with total score of "
							+ playerWithLowestTotalPoints.getScore());
					break;
				}

				// figure out next passing
				if (this.passing == Passing.Left)
					this.passing = Passing.Right;
				else if (this.passing == Passing.Right)
					this.passing = Passing.Front;
				else if (this.passing == Passing.Front)
					this.passing = Passing.Stay;
				else if (this.passing == Passing.Stay)
					this.passing = Passing.Left;
			}
		}
		long done = System.nanoTime();
		System.out.println("time to run was " + (done - start) / 1000000);
		System.out.println("My loss count was "+myLossCount);
		System.out.println("My win count was "+myWinCount);

		for (int i = 0; i < players.size(); i++) {
			Player p = players.get(i);
			if (p instanceof AgentAggressive) {
				AgentAggressive aa = (AgentAggressive) p;
				//System.out.println("expected wins "+aa.getExpectedWins());
				//System.out.println("actual wins "+aa.getActualWins());
				aa.serializeThresholds();
			}
		}
	}
	
	private int runTurn(int num) 
	{
		List<Card> cardsPlayed = new ArrayList<Card>(4);
		boolean first = true;
		Card firstPlayedCard = null;
		Card cardWithHighestValue = null;
		Player playerWithHighestValue = null;
		
		
		while(cardsPlayed.size() < 4)
		{
		  Player p = players.get(num);
		  Card c = p.playCard(cardsPlayed, heartsBroken, (first && turnsPlayed == 0));
		  cardsPlayed.add(c);
		  
		  if(first)
		  {
			  firstPlayedCard = c;
			  cardWithHighestValue = c;
			  playerWithHighestValue = p;
		  }
		  else
		  {
			  //compare with highest value
			  if(c.getSuit() == firstPlayedCard.getSuit())
			  {
				  if(c.getFace().ordinal() > cardWithHighestValue.getFace().ordinal())
				  {
					  cardWithHighestValue = c;
					  playerWithHighestValue = p;
				  }
			  }
		  }
		  
		  first = false;
		  if(!silent)
			  System.out.println("Player "+p.getName()+" played "+c);
		
		  //notify that hearts has been broken (once per round)
		  notifyHeartsBroken(cardsPlayed, p);
		  
		  //add that card to each player's in play cards
		  for(int i = 0; i < players.size(); i++)
			  players.get(i).addInPlayCards(c);
		  
		  num++;
		  num%=4;
		}
		
		//also add to the played cards
		//add that card to each player's list of played cards
		for(int i = 0; i < players.size(); i++)
		{
			boolean tookCards = players.get(i).equals(playerWithHighestValue);
			players.get(i).addPlayedCards(cardsPlayed, tookCards);
			if(tookCards)
			{
				//add cards to the player with highest value
				for(int j = 0; j < cardsPlayed.size(); j++)
				{
					playerWithHighestValue.addTakenCard(cardsPlayed.get(j), !silent);
					
					//figure out if hearts already broken
					if(!this.heartsBroken && cardsPlayed.get(j).getSuit() == Suit.Hearts)
						this.heartsBroken = true;
						
				}				
			}
		}
		  

		
		return this.players.indexOf(playerWithHighestValue);
	}

	/**
	 * This displays an instant notification when hearts have been broken during a round.
	 * The notification also includes the name of the player who broke hearts.
	 * 
	 * @param cardsPlayed To monitor the cards on the table
	 * @param p Player object. Enables passing in of player name.
	 * @author Jonathan Shelton
	 */
	private void notifyHeartsBroken(List<Card> cardsPlayed, Player p) 
	{
		  if (notifyHeartsBroken == false)
		  {
			  for (int i = 0; i < cardsPlayed.size(); i++)
			  {
				  if (cardsPlayed.get(i).getSuit() == Suit.Hearts)
				  {
					  if(!silent)
						  System.out.println("*****Hearts have been broken by " +p.getName()+"*****");
					  notifyHeartsBroken = true;
				  }
			  }
		  }
	}

	private int findPlayerToStart() 
	{
		for(int i = 0; i < players.size(); i++)
		{
			Player p = players.get(i);
			if(p.hasTwoOfClubs())
				return i;
		}
		throw new RuntimeException("Couldn't find the 2 of clubs");
	}

	private void assignScoresToPlayers()
	{
		//see if a player collects all hearts and queen of spade
		int numHearts;
		int numQueenSpade;
		boolean playerShootingTheMoon = false;
		
		List<Integer> scores = Arrays.asList(0,0,0,0);
		for(int i = 0; i < players.size(); i++)
		{
			numHearts = 0;
			numQueenSpade = 0;
			
			Player player = players.get(i);
			List<Card> takenCards = player.getTakenCards();
			
			for(int j = 0; j < takenCards.size(); j++)
			{
				Card takenCard = takenCards.get(j);
				if(takenCard.getSuit() == Suit.Hearts)
					numHearts++;
				else if(takenCard.getSuit() == Suit.Spades && takenCard.getFace() == Face.Queen)
					numQueenSpade++;
			}
			
			//if this player gets all the hearts & queen of spade
			if(numHearts == 13 && numQueenSpade == 1)
			{
				playerShootingTheMoon = true;
				for(int j = 0; j < players.size(); j++)
				{
					if(i != j)
						scores.set(j, 26);
					else
						scores.set(j, 0);
				}
				break;
			}
			else
			{
				scores.set(i, numHearts + (numQueenSpade * 13));
			}
		}
		
		for(int i = 0; i < players.size(); i++)
		{
			Player player = players.get(i);
			player.addScore(scores.get(i));
			if(!silent)
			{
				System.out.println("This round, " + player.getName() + " collected " + scores.get(i) + " points ");
				System.out.println("Total points for " + player.getName() + " is " + player.getScore() + " points ");
				System.out.println("=======================================");
			}
		}
	}
}
