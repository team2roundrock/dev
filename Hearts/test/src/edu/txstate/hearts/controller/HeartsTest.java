package edu.txstate.hearts.controller;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.txstate.hearts.model.AgentAggressive;
import edu.txstate.hearts.model.AgentDetermined;
import edu.txstate.hearts.model.AgentGoofy;
import edu.txstate.hearts.model.Player;
import edu.txstate.hearts.model.User;
import edu.txstate.hearts.model.Agent;

/**
 * The Only part of this class that can be tested is
 * game initialization, which ensures that values
 * entered into the GUI will start the game correctly.
 * 
 * End score should be what user typed, the correct
 * combination of agent types should be picked based
 * on the level of difficulty chosen, and player 1 
 * should be set up as a human user.
 * 
 * @author Jonathan
 *
 */
public class HeartsTest {
	
	Player p;
	User u;
	File file;
	Hearts hearts = new Hearts();
	Scanner scan;
	String playerName;
	

	@Before
	public void setUp() throws Exception {
		playerName = "TestPlayer";
		p = new User(playerName, 0);
		u = (User) p; //Cast player as a human user
		file = new File("TestPlayer.txt");
		scan = new Scanner(file);
		hearts = new Hearts();
	}

	@After
	public void tearDown() throws Exception {
		file.delete();
	}

	@Test //String playerName, int endScore, String levelOfDifficulty
	public void initialize() {
		
		int endScore = 75; //set score that the game ends at
		String levelOfDifficulty = "Easy"; //set difficulty
		
		hearts.initialize(playerName, endScore, levelOfDifficulty);
		assertEquals("Player 1 not set as user",true,file.exists());
		assertEquals("Player 1 not set as user",playerName,p.getName());
		assertEquals("Score not passed properly",75,hearts.getEndScore());
		
		//Ensure proper agent setup for easy difficulty
		Agent a = (Agent) hearts.getPlayers().get(1);
		Agent b = (Agent) hearts.getPlayers().get(2);
		Agent c = (Agent) hearts.getPlayers().get(3);
		assertTrue ("Easy difficulty not set correct, ", a instanceof AgentGoofy);
		assertTrue ("Easy difficulty not set correct, ", b instanceof AgentGoofy);
		assertTrue ("Easy difficulty not set correct, ", c instanceof AgentDetermined);
		
        levelOfDifficulty = "Medium"; //set difficulty
		
		hearts.initialize(playerName, endScore, levelOfDifficulty);
		
		// Ensure proper agent setup for easy difficulty
		a = (Agent) hearts.getPlayers().get(1);
		b = (Agent) hearts.getPlayers().get(2);
		c = (Agent) hearts.getPlayers().get(3);
		assertTrue("Medium  difficulty not set correct, ", a instanceof AgentGoofy);
		assertTrue("Medium difficulty not set correct, ", b instanceof AgentAggressive);
		assertTrue("Medium difficulty not set correct, ",c instanceof AgentDetermined);
		
		levelOfDifficulty = "Hard"; //set difficulty
		
		hearts.initialize(playerName, endScore, levelOfDifficulty);
		
		// Ensure proper agent setup for easy difficulty
		a = (Agent) hearts.getPlayers().get(1);
		b = (Agent) hearts.getPlayers().get(2);
		c = (Agent) hearts.getPlayers().get(3);
		assertTrue("Hard  difficulty not set correct, ", a instanceof AgentAggressive);
		assertTrue("Hard difficulty not set correct, ", b instanceof AgentAggressive);
		assertTrue("Hard difficulty not set correct, ",c instanceof AgentDetermined);
	}

}
