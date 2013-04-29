package edu.txstate.hearts.gui;

import java.awt.Image;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.*;
import java.util.Vector;
import javax.swing.AbstractAction;
import javax.swing.Action;
import edu.txstate.hearts.controller.Hearts;
import edu.txstate.hearts.utils.ReadFiles;

/**
 * This class displays the initial set up of the game. The user will be allow to
 * enter a name, a level of difficulty and set the end score. This user
 * interface is made up of a JFrame containing 3 ComboBoxes that allow for easy
 * access to information.
 * 
 * @author Maria Poole, I Gede Sutapa
 * 
 */
public class ConfigurationUI 
{

	private JFrame frame;

	public JFrame getFrame() 
	{
		return frame;
	}

	private final Action action = new SwingAction();
	private int endScore = 100;
	private String levelOfDifficulty = "Easy";
	private String playerName;
	@SuppressWarnings("rawtypes")
	private JComboBox comboBox;
	private JTextField scoreTextField;
	private Hearts heartsController;
	private boolean userErrorShown = false;

	/**
	 * Constructor
	 * 
	 * @param heartsController	hearts controller
	 */
	public ConfigurationUI(Hearts heartsController) 
	{
		this.heartsController = heartsController;
		initialize();
	}

	/**
	 * @return player name
	 */
	public String getPlayerName() 
	{
		return this.playerName;
	}

	/**
	 * @return selected level of difficulty
	 */
	public String getLevelofDifficulty() 
	{
		return this.levelOfDifficulty;
	}

	/**
	 * @return specified end score
	 */
	public int getEndScore() 
	{
		this.endScore = Integer.parseInt(this.scoreTextField.getText());
		return this.endScore;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initialize() 
	{
		frame = new JFrame();
		frame.setTitle("Hearts - configuration");
		frame.getContentPane().setEnabled(false);
		frame.setBounds(100, 100, 450, 300);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		Image image = ReadFiles.getImage("heart.png");
		frame.setIconImage(image);

		final JButton btnOk = new JButton("OK");
		btnOk.putClientProperty("ButtonType", "ConfigurationOK");
		btnOk.addActionListener(this.heartsController);

		Vector<String> userName = ReadFiles.getRecords();
		comboBox = new JComboBox(userName);
		comboBox.setSelectedIndex(0);
		playerName = (String) comboBox.getSelectedItem();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JComboBox cb = (JComboBox) e.getSource();
				playerName = (String) cb.getSelectedItem();
				if (playerName == null || playerName.trim().equals("")) {
					if (!userErrorShown) {
						displayErrorDialog("Enter a user name");
						userErrorShown = true;
					}
					btnOk.setEnabled(false);
				} else {
					userErrorShown = false;
					btnOk.setEnabled(true);
				}
			}
		});
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 14));
		comboBox.setEditable(true);
		comboBox.setBounds(138, 37, 163, 29);
		frame.getContentPane().add(comboBox);

		JLabel lblUserName = new JLabel("User Name");
		lblUserName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblUserName.setBounds(10, 38, 104, 14);
		frame.getContentPane().add(lblUserName);

		// Create Label and drop down menu for Setting the score
		JLabel lblSetEndScore = new JLabel("Set End Score");
		lblSetEndScore.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSetEndScore.setBounds(10, 95, 93, 14);
		frame.getContentPane().add(lblSetEndScore);
		String[] defaultEndScore = { "100" };
		// JComboBox comboBox_1 = new JComboBox(defaultEndScore);
		this.scoreTextField = new JTextField(defaultEndScore[0]);
		// add an action listener for the score
		this.scoreTextField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// JComboBox cb = (JComboBox)arg0.getSource();
				JTextField cb = (JTextField) arg0.getSource();
				// String newSelection = (String)cb.getSelectedItem();
				String newSelection = cb.getText();
				String message = "Set end score must be a number between 10 and 1000";
				String message2 = "An end score over 500 produces a very long game";
				Integer num;
				try {
					num = Integer.parseInt(newSelection);
					if (num != 100) {
						if (num < 10 || num > 1000) {
							displayErrorDialog(message);
							btnOk.setEnabled(false);
						} else if (num >= 500)
							displayInfoDialog(message2);
					}// end if
					else {
						btnOk.setEnabled(true);
					}
					endScore = num;// set the end score for the game
				}// end of try
				catch (NumberFormatException numberFormatException) {
					displayErrorDialog(message);
					btnOk.setEnabled(false);
				}

			}// end of action listener
		});
		this.scoreTextField.setFont(new Font("Tahoma", Font.PLAIN, 14));
		this.scoreTextField.setEditable(true);
		this.scoreTextField.setBounds(138, 94, 163, 29);
		frame.getContentPane().add(this.scoreTextField);

		// create label and drop down menu for the level of difficulty
		JLabel lblLevelOfDifficulty = new JLabel("Level of Difficulty");
		lblLevelOfDifficulty.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblLevelOfDifficulty.setBounds(10, 152, 118, 19);
		frame.getContentPane().add(lblLevelOfDifficulty);
		// add an action listener when the level is changed
		String[] levels = { "Easy", "Medium", "Hard" };
		JComboBox comboBox_2 = new JComboBox(levels);
		comboBox_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JComboBox cb = (JComboBox) arg0.getSource();
				String newSelection = (String) cb.getSelectedItem();
				levelOfDifficulty = newSelection;
			}
		});
		comboBox_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		comboBox_2.setBounds(138, 151, 163, 29);
		frame.getContentPane().add(comboBox_2);

		// btnOk.addActionListener(new ActionListener() {
		// public void actionPerformed(ActionEvent e)
		// {
		// frmConfigurationWindow.setVisible(false);
		// obj.initialize(playerName, endScore, levelOfDifficulty);
		// }
		// });
		btnOk.setBounds(119, 206, 93, 29);
		frame.getContentPane().add(btnOk);

		// Cancel button includes event handler: closes window when pressed
		JButton btnCancel = new JButton("CANCEL");
		btnCancel.putClientProperty("ButtonType", "ConfigurationCancel");
		btnCancel.addActionListener(this.heartsController);
		// btnCancel.addActionListener(new ActionListener() {
		// public void actionPerformed(ActionEvent arg0) {
		// System.exit(0);
		// }
		// });
		btnCancel.setAction(action);
		btnCancel.setBounds(259, 206, 93, 29);
		frame.getContentPane().add(btnCancel);

	}// end of initialize

	/**
	 * Show UI on center screen
	 */
	public void showDialog() 
	{
		try 
		{
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	public void displayErrorDialog(String message) 
	{
		JOptionPane.showMessageDialog(null, message, "",
				JOptionPane.ERROR_MESSAGE);
	}

	public void displayInfoDialog(String message) 
	{
		JOptionPane.showMessageDialog(null, message, "",
				JOptionPane.INFORMATION_MESSAGE);
	}

	@SuppressWarnings("serial")
	private class SwingAction extends AbstractAction 
	{
		public SwingAction() 
		{
			putValue(NAME, "CANCEL");
			putValue(SHORT_DESCRIPTION, "Don't start game");
		}

		public void actionPerformed(ActionEvent e) {
			// System.exit(0);
		}
	}

	/**
	 * @return the userErrorShown
	 */
	public boolean isUserErrorShown() 
	{
		return userErrorShown;
	}

	/**
	 * @param userErrorShown	the userErrorShown to set
	 */
	public void setUserErrorShown(boolean userErrorShown) 
	{
		this.userErrorShown = userErrorShown;
	}
}