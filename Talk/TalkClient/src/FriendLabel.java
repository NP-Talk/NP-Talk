import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.SpringLayout;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Font;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class FriendLabel extends JPanel{
	private static final long serialVersionUID = 1L;
	private final JTextPane textPane = new JTextPane();
	public JButton profile;
	public JLabel chatmsg;
	public String UserName = "test";
	public ImageIcon UserImg = new ImageIcon(FriendLabel.class.getResource("./img/standardProfile.png"));
	
	public FriendLabel(ImageIcon userImg, String username) {
		UserImg = userImg;
		UserName = username;
		
		setBackground(new Color(147, 208, 250));
		setLayout(null);
		
		UserImg = imageSetSize(UserImg, 56, 56);
		profile = new JButton(UserImg);
		profile.setBounds(1, 5, 40, 40);
		add(profile);
		
		JLabel name = new JLabel(UserName);
		name.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		name.setBounds(45, 5, 40, 40);
		name.setSize(120, 20);
		add(name);
		
		chatmsg = new JLabel("");
		chatmsg.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		chatmsg.setBounds(45, 25, 40, 40);
		chatmsg.setSize(120, 20);
		add(chatmsg);
	}
	
	public ImageIcon imageSetSize(ImageIcon icon, int i, int j) { // image Size Setting
		Image ximg = icon.getImage();  //ImageIcon을 Image로 변환.
		Image yimg = ximg.getScaledInstance(i, j, java.awt.Image.SCALE_SMOOTH);
		ImageIcon xyimg = new ImageIcon(yimg); 
		return xyimg;
	}
}
