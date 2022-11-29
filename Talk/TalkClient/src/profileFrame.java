import java.awt.Color;
import java.awt.Image;

import javax.swing.*;

public class profileFrame extends JPanel {
	JButton profileBtn;
	JLabel nameLbl, stateMsgLbl;
	ImageIcon UserIcon; //���� ������ ����
	String Username, stateMsg; // ���� �̸��� ���� �޼���
	
	public profileFrame(String Username, ImageIcon ProfileIcon) {		
		setLayout(null);
		setBackground(Color.PINK);
		
		profileBtn = new JButton("");
		profileBtn.setBounds(15, 14, 50, 50);
		profileBtn.setFocusPainted(false);		
		profileBtn.setBorderPainted(false);	//��ư�� �̹��� �ְ� �̹����� �����
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
	
	// ģ�� ������ ���� �ٲ���� ��
	public void ChangeFriendProfile(ChatMsg cm) {
		
	}
}

