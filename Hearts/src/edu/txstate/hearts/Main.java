package edu.txstate.hearts;

import edu.txstate.hearts.controller.Hearts;
import edu.txstate.hearts.gui.HeartsUI;

public class Main 
{
	private static Hearts heartsController;
	private static HeartsUI heartsUI;

	public static void main(String[] args) 
	{
		heartsUI = new HeartsUI();
		heartsController = new Hearts();
		heartsController.addUI(heartsUI);
		heartsUI.addController(heartsController);
		
		heartsController.run();
	}
}
