/**
 * 
 */
package edu.txstate.hearts.controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.EventQueue;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JButton;

import edu.txstate.hearts.gui.ConfigurationUI;
import edu.txstate.hearts.gui.HeartsUI;
import edu.txstate.hearts.gui.HeartsUI.CardAction;
import edu.txstate.hearts.gui.HeartsUI.Position;
import edu.txstate.hearts.model.*;
import edu.txstate.hearts.model.Card.Face;
import edu.txstate.hearts.model.Card.Suit;
import edu.txstate.hearts.utils.RiskThresholds;

/**
 * @author Neil Stickels, I Gede Sutapa
 *
 */
public class Hearts implements ActionListener
{
	public enum CardAction
	{
		Passing
		, Playing
		, Idle
	}
	
	public enum Passing
	{
		Left,
		Right,
		Front,
		Stay
	}
	
	private int numCardsSelectedToPass;
	private boolean cardsReadyToPass;
	private CardAction cardAction;
	private HeartsUI heartsUI;
	private Deck deck;
	private List<Player> players;
	private List<Card> cardsSelectedToPass;
	private List<JButton> buttonCardsSelectedToPass;
	private Card cardSelectedToPlay;
	private JButton buttonCardSelectedToPlay;
	private boolean cardChosenToPlay;
	private boolean cardReadyToPlay;
	private int turnsPlayed;
	private boolean heartsBroken; //flag when hearts broken to allow a heart as first card played
	private boolean notifyHeartsBroken; //implement notification "Hearts has been broken"
	private Passing passing;
	private int endScore;
	public final static boolean silent = false;
	private final static int GAMES_TO_RUN = 1;
	private Set setofusers;
	private final static boolean runUI = true;
	
	/**
	 * 
	 * @return
	 */
	public Set getSetofusers() {
		return setofusers;
	}

	/**
	 * @param args
	 */
	/*public static void main(String[] args) 
	{	
		final Hearts game = new Hearts();
		
		if(runUI)
		{
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConfigurationUI window = new ConfigurationUI(game);
					window.getFrmConfigurationWindow().setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		} else
		{
			game.oldInitialize();
			game.runGame();
		}
		
		//game.initialize();
		//game.runGame();
	}*/
	
	public Hearts()
	{
			
	}
	
	public void addUI(HeartsUI heartsUI)
	{
		this.heartsUI = heartsUI;
	}
	
	public void run()
	{
		//TODO: use configuration UI
		String playerName = "Mr.Awesome";
		int endScore = 100;
		String levelOfDifficulty = "Hard";
		
		initialize(playerName, endScore, levelOfDifficulty);
		runGame();
	}
	
	public void oldInitialize() 
	{
		passing = Passing.Left; //initial
		endScore = 100; //default
		
		players = new ArrayList<Player>(4);
		Player player1 = new AgentDetermined("Gede", 0);
		//Player player1 = new User(playerName, 0);
		Player player2 = new AgentAggressive("Neil", 1);
		Player player3 = new AgentDetermined("Jonathan", 2);
		Player player4 = new AgentDetermined("Maria", 3);
		
		players.add(player1);
		players.add(player2);
		players.add(player3);
		players.add(player4);

	}
	
	public void initialize(String playerName, int endScore, String levelOfDifficulty) 
	{
		this.passing = Passing.Left; //initial
		this.endScore = endScore; //default
		this.cardsSelectedToPass = new ArrayList<Card>(3);
		this.buttonCardsSelectedToPass = new ArrayList<JButton>(3);
		
		this.players = new ArrayList<Player>(4);
		
		Player player1 = new User(playerName, 0);
		Player player2;
		Player player3;
		Player player4;
		
		if(levelOfDifficulty.equalsIgnoreCase("Easy")){
			 player2 = new AgentGoofy("GoofyNeil", 1);
			 player3 = new AgentGoofy("GoofyJonathan", 2);
			 player4 = new AgentDetermined("DetermineMaria", 3);
			}
			else if(levelOfDifficulty.equalsIgnoreCase("Medium")){
				player2 = new AgentGoofy("Neil", 1);
				player3 = new AgentAggressive("Jonathan", 2);
				player4 = new AgentDetermined("Maria", 3);
				
			}
			else{
				player2 = new AgentAggressive("Neil", 1);
				player3 = new AgentAggressive("Jonathan", 2);
				player4 = new AgentDetermined("Maria", 3);
			}
		
		players.add(player1);
		players.add(player2);
		players.add(player3);
		players.add(player4);
		
		//runGame();
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
		
		List<Card> p0CardsToPass = this.cardsSelectedToPass; 
		for(int i = 0; i < p0CardsToPass.size(); i++)
		{
			this.players.get(0).getHand().remove(p0CardsToPass.get(i));
		}
		
		//List<Card> p0CardsToPass = this.players.get(0).getCardsToPass(this.passing);
		List<Card> p1CardsToPass = this.players.get(1).getCardsToPass(this.passing);
		List<Card> p2CardsToPass = this.players.get(2).getCardsToPass(this.passing);
		List<Card> p3CardsToPass = this.players.get(3).getCardsToPass(this.passing);
		
		if(this.passing == Passing.Left)
		{
			this.players.get(0).addCards(p3CardsToPass);
			this.players.get(1).addCards(p0CardsToPass);
			this.players.get(2).addCards(p1CardsToPass);
			this.players.get(3).addCards(p2CardsToPass);
		}
		else if(this.passing == Passing.Right)
		{
			this.players.get(0).addCards(p1CardsToPass);
			this.players.get(1).addCards(p2CardsToPass);
			this.players.get(2).addCards(p3CardsToPass);
			this.players.get(3).addCards(p0CardsToPass);
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
	
	private void runGame() 
	{
		this.heartsUI.setPlayers(this.players);
		this.heartsUI.setUI();
		this.heartsUI.showDialog();
		
		long start = System.nanoTime();
		int myLossCount = 0;
		int myWinCount = 0;
		for (int n = 0; n < GAMES_TO_RUN; n++) 
		{
			// while we still playing the game
			for(int i = 0; i < players.size(); i++)
			{
				Player p = players.get(i);
				int score = (-1)*p.getScore();
				p.addScore(score);
			}
			while (true) 
			{
				for (int i = 0; i < players.size(); i++)
				{
					Player p = players.get(i);
					// clear each player cards
					p.clearCards();
					this.cardsSelectedToPass.clear();
					this.buttonCardsSelectedToPass.clear();
					
					this.cardsReadyToPass = true;
				}

				// initialize deck for the game
				deck = new Deck();
				shuffleCards();
				dealCards();
				
				this.cardAction = CardAction.Idle;
				this.numCardsSelectedToPass = 0;
				this.heartsUI.displayCards();
				
				if(this.passing != Passing.Stay)
				{
					this.cardAction = CardAction.Passing;
					this.heartsUI.setPassButtonVisible(true);
					this.cardsReadyToPass = false;
				
					while(!cardsReadyToPass)
					{
						
					}
					
					passingCards();
					this.heartsUI.redrawCards();
				
					for(int k = 0; k < this.buttonCardsSelectedToPass.size(); k++)
						this.heartsUI.setCardUnselected(this.buttonCardsSelectedToPass.get(k));
				}
				
				// initialize game properties
				this.cardAction = CardAction.Playing;
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
				while (turnsPlayed < 13) 
				{
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
				System.out.println("expected wins "+aa.getExpectedWins());
				System.out.println("actual wins "+aa.getActualWins());
				aa.serializeThresholds();
			}
		}
	}
	
	private int runTurn(int num) 
	{
		this.cardReadyToPlay = false;
		this.cardChosenToPlay = false;
		this.cardSelectedToPlay = null;
		
		List<Card> cardsPlayed = new ArrayList<Card>(4);
		boolean first = true;
		Card firstPlayedCard = null;
		Card cardWithHighestValue = null;
		Player playerWithHighestValue = null;
		
		Card c = null;
		
		while(cardsPlayed.size() < 4)
		{
		  Player p = players.get(num);
		  Position playerPosition = this.getPlayerPosition(num);
		  
		  //if this is user's turn
		  if(p.getClass().equals(User.class))
		  {
			  User user = (User)p;
			  this.cardAction = CardAction.Playing;
			  
			  while(!this.cardReadyToPlay)
			  {
				  try
				  {
					  while(!this.cardChosenToPlay)
					  {
						  
					  }
					  user.TryPlayCard(this.cardSelectedToPlay, cardsPlayed, heartsBroken, (first && turnsPlayed == 0));
					  this.cardReadyToPlay = true;
					  this.heartsUI.removeButton(this.buttonCardSelectedToPlay, playerPosition);
					  
					  c = this.cardSelectedToPlay;
				  }
				  catch(Exception ex)
				  {
					  this.cardChosenToPlay = false;
					  //TODO show why this is illegal move
				  }
			  }
		  }
		  else
		  {
			  c = p.playCard(cardsPlayed, heartsBroken, (first && turnsPlayed == 0));
			  JButton cardButton = this.heartsUI.findButton(playerPosition, c);
			  this.heartsUI.removeButton(cardButton, playerPosition);
		  }
		  
		  this.heartsUI.showPlayedCardButton(playerPosition, c);
		  
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
		
		//give a little time
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.heartsUI.hideAllPlayedCardButtons();
		
		
		//also add to the played cards
		//add that card to each player's list of played cards
		for(int i = 0; i < players.size(); i++)
		{
			boolean tookCards = players.get(i).equals(playerWithHighestValue);
			players.get(i).addPlayedCards(cardsPlayed, tookCards, num);
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
	
	public Position getPlayerPosition(int num)
	{
		switch(num)
		{
			case 0:
				return Position.South;
			case 1:
				return Position.West;
			case 2:
				return Position.North;
			case 3:
				return Position.East;
			default:
				return Position.South;
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent ae) 
	{
		 Object src = ae.getSource();
		 JButton jButton = (JButton)src;
		 String buttonType = (String)jButton.getClientProperty("ButtonType");
		 
		 if(buttonType.equals("PassButton"))
		 {
			 this.cardsReadyToPass = true;
		 }
		 else if (buttonType.equals("CardButton")) 
		 {
			 Card card = (Card)jButton.getClientProperty("Card");
			 if (this.cardAction == CardAction.Passing) 
			 {
				 boolean isSelected = (boolean) jButton.getClientProperty("Selected");

				 if (isSelected) 
				 {
				   	 this.heartsUI.setCardUnselected(jButton);
				   	 this.buttonCardsSelectedToPass.remove(jButton);
				   	 this.cardsSelectedToPass.remove(card);
					 this.numCardsSelectedToPass--;
				 } 
				 else 
				 {
					 if (this.numCardsSelectedToPass != 3) 
					 {
						 this.heartsUI.setCardSelected(jButton);
						 this.buttonCardsSelectedToPass.add(jButton);
						 this.cardsSelectedToPass.add(card);
						 this.numCardsSelectedToPass++;
					 }
				 }
			 } 
			 else if (this.cardAction == CardAction.Playing) 
			 {
				 this.buttonCardSelectedToPlay = jButton;
				 this.cardSelectedToPlay = card;
				 this.cardChosenToPlay = true;
				 //this.heartsUI.removeButton(jButton, (Position) jButton.getClientProperty("Position"));
			 }
		 }
	}
}