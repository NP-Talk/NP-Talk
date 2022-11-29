import java.awt.Color;
import java.awt.Image;

import javax.swing.*;

public class profileFrame extends JPanel {
	JButton profileBtn;
	JLabel nameLbl, stateMsgLbl;
	ImageIcon UserIcon; //유저 프로필 사진
	String Username, stateMsg; // 유저 이름과 상태 메세지
	
	public profileFrame(String Username, ImageIcon ProfileIcon) {		
		setLayout(null);
		setBackground(Color.PINK);
		
		profileBtn = new JButton("");
		profileBtn.setBounds(15, 14, 50, 50);
		profileBtn.setFocusPainted(false);		
		profileBtn.setBorderPainted(false);	//버튼에 이미지 넣고 이미지만 남기기
		profileBtn.setContentAreaFilled(false);

		
		nameLbl = new JLabel("");
		nameLbl.setBounds(75, 16, 102, 23);
		
		stateMsgLbl = new JLabel("");
		stateMsgLbl.setBounds(75, 40, 102, 21);

		add(profileBtn);
		add(nameLbl);
		add(stateMsgLbl);
		
		JCheckBox onlineCheckbox = new JCheckBox("");
		onlineCheckbox.setBounds(189, 27, 21, 23);
		add(onlineCheckbox);
		
	}

	public void SetIcon(ChatMsg cm) {
		UserIcon = cm.getProfile();
		Image img = UserIcon.getImage().getScaledInstance(profileBtn.getWidth(), profileBtn.getHeight(), Image.SCALE_DEFAULT);
		profileBtn.setIcon(new ImageIcon(img));
	}
	
	public void SetNameAndState(ChatMsg cm) {
		
	}
	
	// 친구 프로필 사진 바뀌었을 때
	public void ChangeFriendProfile(ChatMsg cm) {
		
	}
}

