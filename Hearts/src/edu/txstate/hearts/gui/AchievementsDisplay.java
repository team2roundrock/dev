package edu.txstate.hearts.gui;

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Image;
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
import java.io.FileNotFoundException;
import java.util.Arrays;
import edu.txstate.hearts.model.User;
import edu.txstate.hearts.utils.ReadFiles;
import javax.swing.ToolTipManager;

/**
 * This class displays a window that contains the achievements the user had
 * completed. The completion of an achievement is identified by a check mark
 * next to the achievement. And, the uncompleted achievements are also listed.
 * 
 * @author Maria Poole, I Gede Sutapa
 * 
 */

@SuppressWarnings({ "serial" })
public class AchievementsDisplay extends JFrame
{
	private JPanel contentPane;
	private List<String> achievementNames = Arrays.asList("BrokenHeart", "ShootingTheMoon", "PassingTheBuck", "StartTheParty", "HatTrick",
			"OvershootingTheMoon1", "OvershootingTheMoon2", "OvershootingTheMoon");
	private Icon checkMark = new ImageIcon("images\\other\\CheckMarkSmall.gif");
	private List<String> userAchievements;
	private List<String> unCompletedA = new ArrayList<String>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					User user = new User(ReadFiles.readUserRecords().get(0), 0);
					AchievementsDisplay frame = new AchievementsDisplay(user);
					frame.setVisible(true);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AchievementsDisplay(User user)
	{
		setTitle("Hearts - achievements");
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 300, 435);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(9, 1));

		// This field contains the user name
		String userName = user.getName();
		JLabel lblNewLabel = new JLabel(userName);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		// lblNewLabel.setBounds(66, 11, 103, 30);
		contentPane.add(lblNewLabel);
		ToolTipManager.sharedInstance().setInitialDelay(100);
		ToolTipManager.sharedInstance().setDismissDelay(25000);

		// UIManager.put("ToolTip.background", Color.GREEN);

		try
		{
			userAchievements = ReadFiles.readAchievements(userName);
			System.out.println(userAchievements);
		}
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// userAchievements = user.getAchievements().getListOfAchievements();
		boolean shotOnce = false, shotTwice = false;
		for (int i = 0; i < achievementNames.size(); i++)
		{
			boolean found = userAchievements.contains(achievementNames.get(i));
			{
				if (found)
				{
					String name = achievementNames.get(i);
					System.out.println(name);
					if (name.equals("OvershootingTheMoon1"))
					{

						shotOnce = true;

					}
					else if (name.equals("OvershootingTheMoon2"))
					{

						shotOnce = false;
						shotTwice = true;

					}
					else if (name.equals("OvershootingTheMoon"))
					{

						shotOnce = false;
						shotTwice = false;

						JLabel newLabel = new JLabel("Overshooting The Moon");
						newLabel.setIcon(checkMark);
						newLabel.setHorizontalTextPosition(SwingConstants.LEFT);
						newLabel.setVerticalTextPosition(SwingConstants.BOTTOM);
						newLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
						newLabel.setToolTipText("<html>" + "You collected all hearts and the Queen of Spades not once," + "<br>"
								+ "not twice, but three times! Amazing! You've probably sunk" + "<br>"
								+ "way too much time in this game by now, but hey... at least you" + "<br>"
								+ "got this achievement as a record of your accomplishment. Now go" + "<br>" + "outside and get some sun!"
								+ "</html>");
						contentPane.add(newLabel);

					}
					else if (name.equals("BrokenHeart"))
					{
						JLabel newLabel = new JLabel("Broken Heart");
						newLabel.setIcon(checkMark);
						newLabel.setHorizontalTextPosition(SwingConstants.LEFT);
						newLabel.setVerticalTextPosition(SwingConstants.BOTTOM);
						newLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
						newLabel.setToolTipText("<html>" + "So you won all the hearts in one round but the Queen" + "<br>"
								+ "(of Spades) got away. Sorry about that. Cheer up though, " + "<br>"
								+ "there are plenty of other cards in the pile. You'll love again." + "</html>");
						contentPane.add(newLabel);
					}
					else if (name.equals("ShootingTheMoon"))
					{
						JLabel newLabel = new JLabel("Shooting The Moon");
						newLabel.setIcon(checkMark);
						newLabel.setHorizontalTextPosition(SwingConstants.LEFT);
						newLabel.setVerticalTextPosition(SwingConstants.BOTTOM);
						newLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
						newLabel.setToolTipText("<html>" + "So you won all the hearts and the Queen (of Spades) in" + "<br>"
								+ "one round. Congratulations. You showed those opponents" + "<br>"
								+ "who's boss and collected zero points as your reward." + "</html>");
						contentPane.add(newLabel);
					}
					else if (name.equals("PassingTheBuck"))
					{
						JLabel newLabel = new JLabel("Passing The Buck");
						newLabel.setIcon(checkMark);
						newLabel.setHorizontalTextPosition(SwingConstants.LEFT);
						newLabel.setVerticalTextPosition(SwingConstants.BOTTOM);
						newLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
						newLabel.setToolTipText("<html>" + "You passed the Queen of Spades to another player..." + "<br>"
								+ "Smart move. She didn't want to be with you anyway.");
						contentPane.add(newLabel);
					}
					else if (name.equals("StartTheParty"))
					{
						JLabel newLabel = new JLabel("Start The Party");
						newLabel.setIcon(checkMark);
						newLabel.setHorizontalTextPosition(SwingConstants.LEFT);
						newLabel.setVerticalTextPosition(SwingConstants.BOTTOM);
						newLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
						newLabel.setToolTipText("<html>" + "You started a round by playing the Deuce of Clubs." + "<br>"
								+ "If you could have started with anything else, you" + "<br>" + "must have reprogrammed the game...");
						contentPane.add(newLabel);
					}
					else if (name.equals("HatTrick"))
					{
						JLabel newLabel = new JLabel("Hat Trick");
						newLabel.setIcon(checkMark);
						newLabel.setHorizontalTextPosition(SwingConstants.LEFT);
						newLabel.setVerticalTextPosition(SwingConstants.BOTTOM);
						newLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
						newLabel.setToolTipText("<html>" + "You collected all four cards on the table by playing" + "<br>"
								+ "the highest one. You seriously would have shown more skill" + "<br>"
								+ "by not having this achievement. Good job, slick.");
						contentPane.add(newLabel);
					}
					found = true;
				}
			}
			if (!found)
			{
				unCompletedA.add(achievementNames.get(i));

			}// end of not found

		}// end of outer for

		Boolean shot1 = false, shot2 = false;
		for (String achi : unCompletedA)
		{
			// JLabel achiDesc = new JLabel();
			// achiDesc.setFont(new Font("Tahoma", Font.PLAIN, 14));

			if (achi.equals("BrokenHeart"))
			{
				JLabel achiDesc = new JLabel("Broken Heart");
				achiDesc.setFont(new Font("Tahoma", Font.PLAIN, 14));
				achiDesc.setToolTipText("<html>" + "Get all the hearts but miss the Queen of Spades" + "<br>"
						+ "Otherwise known as really bad luck." + "<br>" + "<b>(One card short of Shooting The Moon)</b>" + "</html>");
				contentPane.add(achiDesc);
			}
			if (achi.equals("ShootingTheMoon"))
			{
				JLabel achiDesc = new JLabel("Shooting The Moon");
				achiDesc.setFont(new Font("Tahoma", Font.PLAIN, 14));
				achiDesc.setToolTipText("<html>" + "Get the Queen of Spades and every heart" + "<br>"
						+ "suited card in a round. Do this and you'll" + "<br>" + "be gifting these points to everyone but yourself."
						+ "</html>");
				contentPane.add(achiDesc);
			}
			if (achi.equals("PassingTheBuck"))
			{
				JLabel achiDesc = new JLabel("Passing The Buck");
				achiDesc.setFont(new Font("Tahoma", Font.PLAIN, 14));
				achiDesc.setToolTipText("<html>" + "Pass the Queen of Spades when it's time to" + "<br>"
						+ "pass cards. Now she's somebody else's problem." + "<br>" + "Stay sharp!" + "</html>");
				contentPane.add(achiDesc);
			}
			if (achi.equals("StartTheParty"))
			{
				JLabel achiDesc = new JLabel("Start The Party");
				achiDesc.setFont(new Font("Tahoma", Font.PLAIN, 14));
				achiDesc.setToolTipText("<html>" + "Get the Deuce of Clubs and play it to start a round." + "<br>"
						+ "Not that you have the option of starting with anything else." + "</html>");
				contentPane.add(achiDesc);
			}
			if (achi.equals("HatTrick"))
			{
				JLabel achiDesc = new JLabel("Hat Trick");
				achiDesc.setFont(new Font("Tahoma", Font.PLAIN, 14));
				achiDesc.setToolTipText("<html>" + "Collect a trick (all four cards on the table) by playing a" + "<br>"
						+ "higher card than the first suit played. Super easy stuff here." + "</html>");
				contentPane.add(achiDesc);
			}
			if (achi.equals("OvershootingTheMoon1"))
			{
				shot1 = true;

			}
			if (achi.equals("OvershootingTheMoon2") && shot1 == true)
			{
				shot2 = true;
			}

			if (achi.equals("OvershootingTheMoon") && shot2 == true) // None
															// earned
			{
				JLabel achiDesc = new JLabel("Overshooting The Moon");
				achiDesc.setFont(new Font("Tahoma", Font.PLAIN, 14));
				achiDesc.setToolTipText("<html>" + "Collect all hearts and the Queen of Spades to Shoot The Moon" + "<br>"
						+ "once. Then do it again. You're still not done. Shoot the Moon " + "<br>"
						+ "for a third time and show it who's boss!" + "</html>");
				contentPane.add(achiDesc);
			}
			if (shotOnce == true) // OvershootingTheMoon1 earned
			{
				JLabel achiDesc = new JLabel("Overshooting The Moon (33% complete)");
				achiDesc.setFont(new Font("Tahoma", Font.PLAIN, 14));
				achiDesc.setToolTipText("<html>" + "Collect all hearts and the Queen of Spades to Shoot The Moon" + "<br>"
						+ "once. Then do it again. You're still not done. Shoot the Moon " + "<br>"
						+ "for a third time and show it who's boss!" + "<br>" + "<b>(You've Shot the Moon one time. Just two more to go!)</b>"
						+ "</html>");
				contentPane.add(achiDesc);
				shotOnce = false;
			}
			if (shotTwice == true) // OvershootingTheMoon2 earned
			{
				JLabel achiDesc = new JLabel("Overshooting The Moon (66% complete)");
				achiDesc.setFont(new Font("Tahoma", Font.PLAIN, 14));
				achiDesc.setToolTipText("<html>" + "Collect all hearts and the Queen of Spades to Shoot The Moon" + "<br>"
						+ "once. Then do it again. You're still not done. Shoot the Moon " + "<br>"
						+ "for a third time and show it who's boss!" + "<br>"
						+ "<b>(You've Shot the Moon twice now. Just one more time left!)</b>" + "</html>");
				contentPane.add(achiDesc);
				shotTwice = false;
			}
			// contentPane.add(achiDesc);
			// contentPane.add(newLabel);
		}

		Image image = ReadFiles.getImage("heart.png");
		this.setIconImage(image);
	}// end constructor

	/**
	 * Show UI on center screen
	 */
	public void showDialog()
	{
		try
		{
			this.setLocationRelativeTo(null);
			this.setVisible(true);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}// end class