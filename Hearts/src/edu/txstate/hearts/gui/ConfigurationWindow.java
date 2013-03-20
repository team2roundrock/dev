package edu.txstate.hearts.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;

import javax.swing.ComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import java.awt.Font;

import java.awt.event.*;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.Action;

import org.apache.commons.math3.util.MultidimensionalCounter.Iterator;

import edu.txstate.hearts.controller.Hearts;
import edu.txstate.hearts.model.User;

public class ConfigurationWindow {

	private JFrame frmConfigurationWindow;
	public JFrame getFrmConfigurationWindow() {
		return frmConfigurationWindow;
	}


	private final Action action = new SwingAction();
	private int endScore = 100;
	private String levelOfDifficulty = "Easy";
	private String playerName;
	private Set theUsers;
	private JComboBox comboBox;
	private Hearts obj;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					ConfigurationWindow window = new ConfigurationWindow();
//					window.frmConfigurationWindow.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the application.
	 * @param set 
	 */
	public ConfigurationWindow(Hearts obj, Set set) {
		theUsers = set;
		this.obj = obj;
		initialize();
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmConfigurationWindow = new JFrame();
		frmConfigurationWindow.setTitle("CONFIGURATION WINDOW");
		frmConfigurationWindow.getContentPane().setEnabled(false);
		frmConfigurationWindow.setBounds(100, 100, 450, 300);
		frmConfigurationWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmConfigurationWindow.getContentPane().setLayout(null);
		
		String [] userName = new String [theUsers.size()];
		java.util.Iterator goThroughUsers = theUsers.iterator();
		int count = 0;
		while(goThroughUsers.hasNext()){
			User user = (User) goThroughUsers.next();
			userName [count++]= user.getName();
		}
		comboBox = new JComboBox(userName);
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JComboBox cb = (JComboBox)e.getSource();
				playerName = (String)cb.getSelectedItem();
				//if(newSelection != (String)cb.getItemAt(0))
				//cb.addItem(newSelection);
			}
		});
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 14));
		comboBox.setEditable(true);
		comboBox.setBounds(138, 37, 163, 29);
		frmConfigurationWindow.getContentPane().add(comboBox);
		
		JLabel lblUserName = new JLabel("User Name");
		lblUserName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblUserName.setBounds(10, 38, 104, 14);
		frmConfigurationWindow.getContentPane().add(lblUserName);
		
		//Create Label and drop down menu for Setting the score
		JLabel lblSetEndScore = new JLabel("Set End Score");
		lblSetEndScore.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSetEndScore.setBounds(10, 95, 93, 14);
		frmConfigurationWindow.getContentPane().add(lblSetEndScore);
		String[] defaultEndScore = {"100"};
		JComboBox comboBox_1 = new JComboBox(defaultEndScore);
		//add an action listener for the score
		comboBox_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JComboBox cb = (JComboBox)arg0.getSource();
				String newSelection = (String)cb.getSelectedItem();
				String message = "Set end score must be a number between 10 and 1000";
				String message2 = "An end score over 500 produces a very long game";
				Integer num;
				try{
				    num = Integer.parseInt(newSelection);
				    if(num != 100){
						if(num < 10 || num > 1000)
							JOptionPane.showMessageDialog(null, message,"", JOptionPane.ERROR_MESSAGE);
						else if(num >= 500)
							JOptionPane.showMessageDialog(null, message2,"", JOptionPane.INFORMATION_MESSAGE);
					}//end if
				    endScore = num;//set the end score for the game
				}//end of try
				catch(NumberFormatException numberFormatException){
					JOptionPane.showMessageDialog(null, message,"", JOptionPane.ERROR_MESSAGE);
				}
				
			}//end of action listener
		});
		comboBox_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		comboBox_1.setEditable(true);
		comboBox_1.setBounds(138, 94, 163, 29);
		frmConfigurationWindow.getContentPane().add(comboBox_1);
		
		//create label and drop down menu for the level of difficulty
		JLabel lblLevelOfDifficulty = new JLabel("Level of Difficulty");
		lblLevelOfDifficulty.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblLevelOfDifficulty.setBounds(10, 152, 118, 19);
		frmConfigurationWindow.getContentPane().add(lblLevelOfDifficulty);
		//add an action listener when the level is changed
		String[] levels = {"Easy", "Medium", "Hard"};
		JComboBox comboBox_2 = new JComboBox(levels);
		comboBox_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JComboBox cb = (JComboBox)arg0.getSource();
				String newSelection = (String)cb.getSelectedItem();
				levelOfDifficulty = newSelection;
			}
		});
		comboBox_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		comboBox_2.setBounds(138, 151, 163, 29);
		frmConfigurationWindow.getContentPane().add(comboBox_2);
		
		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmConfigurationWindow.setVisible(false);
				obj.initialize(playerName, endScore, levelOfDifficulty);
			}
		});
		btnOk.setBounds(119, 206, 93, 29);
		frmConfigurationWindow.getContentPane().add(btnOk);
		
		//Cancel button includes event handler: closes window when pressed
		JButton btnCancel = new JButton("CANCEL");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		btnCancel.setAction(action);
		btnCancel.setBounds(259, 206, 93, 29);
		frmConfigurationWindow.getContentPane().add(btnCancel);
					
	}//end of initialize
	
	
	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "CANCEL");
			putValue(SHORT_DESCRIPTION, "Don't start game");
		}
		public void actionPerformed(ActionEvent e) {
			//System.exit(0);
		}
	}


	public void setUsers(Set setofusers) {
		theUsers = setofusers;
		
	} 
}//end of configuration window class
