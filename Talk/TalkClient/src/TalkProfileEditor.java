import java.awt.Color;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;

public class TalkProfileEditor extends JFrame{
	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private String UserName;
	private String state;
	
	private ProfileInfo profileInfo = new ProfileInfo(UserName);
	private ImageIcon profile = profileInfo.getProfileImg();
	private ImageIcon back = profileInfo.getBackgroundImg();
	private JTextField name;
	private JTextField stateMsg;
	
	public TalkProfileEditor(String username) {
		this.UserName = username;
		state = profileInfo.getStateMsg();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 394, 630);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setVisible(true);
		
		profile = imageSetSize(profile, 90, 90);
		JButton profilePhoto = new JButton(profile);
		profilePhoto.setBounds(159, 366, 90, 90);
		profilePhoto.setBorderPainted(false);
		profilePhoto.setContentAreaFilled(false);
		profilePhoto.setOpaque(false);
		contentPane.add(profilePhoto);
		
		/*
		back = imageSetSize(back, 394, 605);
		JButton background = new JButton(back);
		background.setBackground(new Color(121, 121, 121));
		background.setBounds(0, 0, 394, 605);
		contentPane.add(background);
		*/
		
		name = new JTextField(UserName);
		name.setHorizontalAlignment(SwingConstants.CENTER);
		name.setOpaque(false);
		name.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		name.setBounds(6, 468, 382, 26);
		contentPane.add(name);
		name.setColumns(10);
		
		stateMsg = new JTextField(state);
		stateMsg.setHorizontalAlignment(SwingConstants.CENTER);
		stateMsg.setOpaque(false);
		stateMsg.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		stateMsg.setBounds(6, 511, 388, 26);
		contentPane.add(stateMsg);
		stateMsg.setColumns(10);
		
		JSeparator separator2 = new JSeparator();
		separator2.setForeground(new Color(254, 255, 255));
		separator2.setBounds(390, 549, 388, 12);
		contentPane.add(separator2);
		
		JSeparator separator1 = new JSeparator();
		separator1.setForeground(new Color(254, 255, 255));
		separator1.setBounds(0, 487, 388, 12);
		contentPane.add(separator1);
		setVisible(true);
		
	}
	
	public ImageIcon imageSetSize(ImageIcon icon, int i, int j) { // image Size Setting
		Image ximg = icon.getImage();  //ImageIcon을 Image로 변환.
		Image yimg = ximg.getScaledInstance(i, j, java.awt.Image.SCALE_SMOOTH);
		ImageIcon xyimg = new ImageIcon(yimg); 
		return xyimg;
	}
}
