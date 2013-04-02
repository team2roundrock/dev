/**
 * 
 */
package edu.txstate.hearts.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Path;

/**
 * Holds implementation for the creation and modification
 * of data belonging to an individual user
 * @author Jonathan Shelton
 *
 */
public class UserData 
{
	private Player player;
	public UserData(Player player)
	{
		this.player = player;
	}
	
	public void CreateUserData()
	{
		File file = new File(/*"C\" + */player.getName() + ".txt");
		if (file.exists())
		{
			
		}
		
		try 
		{			
			
			//PrintWriter output = new PrintWriter (player.getName() + ".txt");
		} 
		catch (FileNotFoundException ex) 
		{
			ex.printStackTrace();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
	}

}
