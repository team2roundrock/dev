package edu.txstate.hearts.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import java.awt.Font;

public class ConfigurationWindow {

	private JFrame frmConfigurationWindow;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConfigurationWindow window = new ConfigurationWindow();
					window.frmConfigurationWindow.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ConfigurationWindow() {
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
		
		JComboBox comboBox = new JComboBox();
		comboBox.setEditable(true);
		comboBox.setBounds(138, 37, 139, 23);
		frmConfigurationWindow.getContentPane().add(comboBox);
		
		JLabel lblUserName = new JLabel("User Name");
		lblUserName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblUserName.setBounds(10, 38, 104, 14);
		frmConfigurationWindow.getContentPane().add(lblUserName);
		
		JLabel lblSetEndScore = new JLabel("Set End Score");
		lblSetEndScore.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSetEndScore.setBounds(10, 95, 93, 14);
		frmConfigurationWindow.getContentPane().add(lblSetEndScore);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setEditable(true);
		comboBox_1.setBounds(138, 94, 139, 23);
		frmConfigurationWindow.getContentPane().add(comboBox_1);
		
		JLabel lblLevelOfDifficulty = new JLabel("Level of Difficulty");
		lblLevelOfDifficulty.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblLevelOfDifficulty.setBounds(10, 152, 118, 19);
		frmConfigurationWindow.getContentPane().add(lblLevelOfDifficulty);
		
		String[] levels = {"Easy", "Medium", "Hard"};
		JComboBox comboBox_2 = new JComboBox(levels);
		comboBox_2.setBounds(138, 151, 139, 23);
		frmConfigurationWindow.getContentPane().add(comboBox_2);
		
		JButton btnOk = new JButton("OK");
		btnOk.setBounds(138, 209, 89, 23);
		frmConfigurationWindow.getContentPane().add(btnOk);
		
		JButton btnCancel = new JButton("CANCEL");
		btnCancel.setBounds(276, 209, 89, 23);
		frmConfigurationWindow.getContentPane().add(btnCancel);
	}
}
