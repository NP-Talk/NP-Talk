import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.SpringLayout;
import javax.swing.JTextField;
import java.awt.Color;

public class FriendLabel extends JPanel{
	private final JTextPane textPane = new JTextPane();
	public JButton profile;
	JLabel msg;
	ImageIcon profileImg = null;
	
	public FriendLabel(String username, String chatmsg) {
		setBackground(new Color(147, 208, 250));
		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);
		
		//profileImg = imageSetSize(img, 56, 56);
		
		profile = new JButton(profileImg);
		springLayout.putConstraint(SpringLayout.NORTH, profile, 6, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, profile, 6, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, profile, 56, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.EAST, profile, 56, SpringLayout.WEST, this);
		add(profile);
		
		JLabel name = new JLabel(username);
		springLayout.putConstraint(SpringLayout.NORTH, name, 10, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, name, 6, SpringLayout.EAST, profile);
		add(name);
		
		msg = new JLabel(chatmsg);
		springLayout.putConstraint(SpringLayout.NORTH, msg, 6, SpringLayout.SOUTH, name);
		springLayout.putConstraint(SpringLayout.WEST, msg, 6, SpringLayout.EAST, profile);
		add(msg);
	}
	
	public ImageIcon imageSetSize(ImageIcon icon, int i, int j) { // image Size Setting
		Image ximg = icon.getImage();  //ImageIcon�� Image�� ��ȯ.
		Image yimg = ximg.getScaledInstance(i, j, java.awt.Image.SCALE_SMOOTH);
		ImageIcon xyimg = new ImageIcon(yimg); 
		return xyimg;
	}
}
