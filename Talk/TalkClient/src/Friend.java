import javax.swing.JPanel;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JCheckBox;

public class Friend extends JPanel{
	private static final long serialVersionUID = 1L;
	
	public ImageIcon UserImg;
	public String UserName;
	public String UserStatusMsg;
	
	public Friend(ChatClientMainView mainView, ImageIcon userimg, String username, String statemsg) {
		UserImg = userimg;
		UserName = username;
		UserStatusMsg = statemsg;
		
		setBounds(0, 0, 312, 60);
		setLayout(null);
		
		UserImg = imageSetSize(UserImg, 55, 55);
		JButton img = new JButton(UserImg);
		img.setBounds(5, 2, 55, 55);
		add(img);
		
		JLabel name = new JLabel(UserName);
		name.setBounds(72, 7, 155, 16);
		add(name);
		
		JLabel stateMsg = new JLabel(UserStatusMsg);
		stateMsg.setBounds(72, 35, 155, 16);
		add(stateMsg);
		
		JCheckBox chckbxNewCheckBox = new JCheckBox("");
		chckbxNewCheckBox.setBounds(280, 18, 26, 23);
		add(chckbxNewCheckBox);
	}
	
	public void SetStatusMsg(ChatMsg cm) {
		
	}
	
	public void SetStatusChangeActive() {
		
	}
	
	public void SetProfileButtonActive() {
		
	}
	
	public ImageIcon imageSetSize(ImageIcon icon, int i, int j) { // image Size Setting
		Image ximg = icon.getImage();  //ImageIcon을 Image로 변환.
		Image yimg = ximg.getScaledInstance(i, j, java.awt.Image.SCALE_SMOOTH);
		ImageIcon xyimg = new ImageIcon(yimg); 
		return xyimg;
	}
}
