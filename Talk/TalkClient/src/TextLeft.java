import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.SpringLayout;

public class TextLeft extends JPanel{
	private final JTextPane textPane = new JTextPane();
	public JButton profile;
	public JLabel time;
	public ImageIcon profileImg = null;
	
	public TextLeft(String username, String chattime) {
		setBackground(new Color(147, 208, 250));
		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);
		
		//profileImg = imageSetSize(img, 56, 56);
		
		profile = new JButton(profileImg);
		springLayout.putConstraint(SpringLayout.NORTH, profile, 6, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, profile, 6, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, profile, 51, SpringLayout.NORTH, this);
		add(profile);
		
		JLabel name = new JLabel(username);
		springLayout.putConstraint(SpringLayout.WEST, name, 62, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, profile, -6, SpringLayout.WEST, name);
		name.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		springLayout.putConstraint(SpringLayout.NORTH, name, 10, SpringLayout.NORTH, this);
		add(name);
		
		time = new JLabel(chattime);
		springLayout.putConstraint(SpringLayout.NORTH, time, 6, SpringLayout.SOUTH, name);
		springLayout.putConstraint(SpringLayout.WEST, time, 6, SpringLayout.EAST, profile);
		add(time);
	}
	
	public ImageIcon imageSetSize(ImageIcon icon, int i, int j) { // image Size Setting
		Image ximg = icon.getImage();  //ImageIcon을 Image로 변환.
		Image yimg = ximg.getScaledInstance(i, j, java.awt.Image.SCALE_SMOOTH);
		ImageIcon xyimg = new ImageIcon(yimg); 
		return xyimg;
	}
}
