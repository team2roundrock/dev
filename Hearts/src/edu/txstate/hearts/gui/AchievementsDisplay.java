package edu.txstate.hearts.gui;


import java.awt.EventQueue;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.util.Arrays;
import edu.txstate.hearts.model.Achievements;
/**
 * This class displays a window that contains the achievements the user had completed. The completion of an
 * achievement is identified by a check mark next to the achievement. And, the uncompleted achievements are also
 * listed.
 * @author Maria Poole
 *
 */

public class AchievementsDisplay extends JFrame {

	private JPanel contentPane;
	private List<String> achievementNames = Arrays.asList("BrokenHeart","ShootingTheMoon","PassingTheBuck",
			"StartTheParty","HatTrick","OvershootingTheMoon1","OvershootingTheMoon2", 
			"OvershootingTheMoon3");
	private Icon checkMark = new ImageIcon("images\\other\\CheckMarkSmall.gif");
	private List<String> userAchievements = Arrays.asList("PassingTheBuck", "OvershootingTheMoon2", "HatTrick");;
	private List<String> unCompletedA = new ArrayList<String>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AchievementsDisplay frame = new AchievementsDisplay();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AchievementsDisplay() {
		setTitle("Achievements List");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 242, 435);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(9,1));
		
		//This field contains the user name
		String userName = Achievements.getUserFileName();
		JLabel lblNewLabel = new JLabel("UserName");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		//lblNewLabel.setBounds(66, 11, 103, 30);
		contentPane.add(lblNewLabel);
		
		
		for(int i = 0; i < achievementNames.size(); i++){
			boolean found = false;
			for(int j = 0; j < userAchievements.size(); j++){
				if(userAchievements.get(j) == achievementNames.get(i)){
					JLabel newLabel = new JLabel(userAchievements.get(j));
					newLabel.setIcon(checkMark);
					newLabel.setHorizontalTextPosition(SwingConstants.LEFT);
					newLabel.setVerticalTextPosition(SwingConstants.BOTTOM);
					newLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
					contentPane.add(newLabel);
					found = true;
					break;
				}
			}
			if(!found){
				unCompletedA.add(achievementNames.get(i));
				
			}//end of not found
			
		}//end of outer for
		for(String achi : unCompletedA){
			JLabel newLabel = new JLabel(achi);
			newLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
			contentPane.add(newLabel);
		}
		
	}//end constructor
}//end class
