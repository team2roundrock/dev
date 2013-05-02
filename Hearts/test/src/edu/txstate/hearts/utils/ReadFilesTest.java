package edu.txstate.hearts.utils;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.txstate.hearts.model.User;

public class ReadFilesTest {
	private List<String> records;
	@SuppressWarnings("unused")
	private List<String> achievement;
	private File file;
	private File userFile;
	private Scanner input = null;
	private User user;
	@SuppressWarnings("unused")
	private String playerName;
	
	@Before
	public void setUp() throws Exception {
		records = new ArrayList<String>();
		achievement = new ArrayList<String>();
		file = new File("Users.txt");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testReadUserRecords() {
		try {
			input = new Scanner(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while(input.hasNext()){
			String name = input.nextLine();
			records.add(name);
		}
		assertTrue(!records.isEmpty());
	}

	@Test
	public void testReadAchievements() {
		String name = null;
		String achiname = null;
		
		user = new User(name, 0);
		user.getAchievements().HatTrick();
		userFile = new File(name + ".txt");
		
		try {
		input = new Scanner(userFile);
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	while(input.hasNext()){
		achiname = input.nextLine();
	}
		
	assertTrue(achiname.equals("HatTrick"));	
	}

}
