package edu.txstate.hearts.utils;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.txstate.hearts.model.User;

public class UserDataTest {
	private String filename = "Users.txt";
	private File fw;
	private File fw2;
	private String newUser;
	private User user;
	private List<String> records;

	@Before
	public void setUp() throws Exception {
		newUser = "claudia";
		fw = new File(newUser + ".txt");
		fw2 = new File(filename);
		records = new ArrayList<String>();
	}

	@After
	public void tearDown() throws Exception {
		fw.delete();
		//fw2.delete();
	}

	@Test
	public void testCreateUserDataFile() {
		user = new User(newUser, 0);
		
		assertEquals("File not created, ", true, fw.exists());
	}

	@Test
	public void testAddUserNameToFile() {
		user = new User(newUser, 0);
		Scanner input = null;
		try {
			input = new Scanner(fw2);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		while(input.hasNext()){
			String aUser = input.nextLine();
			records.add(aUser);
		}
		
		assertTrue(records.contains(newUser));
	}

}
