/**
 * 
 */
package edu.txstate.hearts.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;


/**
 * Holds implementation for the creation and modification of data belonging to
 * an individual user
 * 
 * @author Jonathan Shelton, Maria Poole,,,
 * 
 */
public class UserData {
	
	private final String userName;
	
	public UserData(String userName)
	{
		this.userName = userName;
	}

	public void createUserDataFile(List<String> achievements) throws IOException {
		if (!ReadFiles.getRecords().contains(userName)) {
			addUserNameToFile();
		}

		File name = new File(userName + ".txt");
		try {
			FileWriter output = new FileWriter(name, true);// open/create file
			for (String achievement : achievements) {
				output.write(achievement + "\n");
			}
			output.close();
		} catch (IOException ioe) {
			System.err.println("IOException: " + ioe.getMessage());
			throw ioe;
		}

	}// end of function

	public void addUserNameToFile() throws IOException {
		try {
			String filename = "Users.txt";
			FileWriter fw = new FileWriter(filename, true); // the true will
															// append the new
															// data
			fw.write(userName + "\n");// appends the string to the file
			fw.close();
			ReadFiles.addUserRecord(userName);
		} catch (IOException ioe) {
			System.err.println("IOException: " + ioe.getMessage());
			throw ioe;
		}

	}

}// end of class
