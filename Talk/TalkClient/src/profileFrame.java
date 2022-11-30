import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class profileFrame extends JPanel {
	private static final long serialVersionUID = 1L;
	public ChatMainFrame mainView;
	
	public JPanel contentPane;
	public JButton UserprofileBtn;
	public JLabel nameLbl, stateMsgLbl;
	public JCheckBox checkboxSelect;
	public ImageIcon UserprofileIcon; //유저 프로필 사진
	
	
	public String Username, UserStatus, UserStatusMsg;
	public Boolean online = false;
	
	private Frame frame;
	private FileDialog fd;
	
	public profileFrame(ChatMainFrame mainView, ImageIcon icon, String Username, String UserStatus, String UserStatusMsg) {
		contentPane = new JPanel();
		contentPane.setBorder(null);		
		contentPane.setLayout(null);
		contentPane.setBackground(Color.PINK);
		contentPane.setVisible(true);
		
		this.Username = Username;
		this.UserStatus = UserStatus;
		this.UserStatusMsg = UserStatusMsg;
		this.UserprofileIcon = icon;
		this.mainView = mainView;
		
		UserprofileBtn = new JButton("");
		UserprofileBtn.setBounds(15, 14, 50, 50);
		UserprofileBtn.setFocusPainted(false);		
		UserprofileBtn.setBorderPainted(false);	//버튼에 이미지 넣고 이미지만 남기기
		UserprofileBtn.setContentAreaFilled(false);
		
		nameLbl = new JLabel(Username);
		nameLbl.setBounds(75, 16, 102, 23);
		
		stateMsgLbl = new JLabel("");
		stateMsgLbl.setBounds(75, 40, 102, 21);

		contentPane.add(UserprofileBtn);
		contentPane.add(nameLbl);
		contentPane.add(stateMsgLbl);
		
		checkboxSelect = new JCheckBox("");
		checkboxSelect.setBackground(Color.WHITE);
		checkboxSelect.setBounds(189, 27, 21, 23);
		contentPane.add(checkboxSelect);
		
	}

	public void SetIcon(ChatMsg cm) {
		UserprofileIcon = cm.getProfile();
		Image img = UserprofileIcon.getImage().getScaledInstance(UserprofileBtn.getWidth(), UserprofileBtn.getHeight(), Image.SCALE_DEFAULT);
		UserprofileBtn.setIcon(new ImageIcon(img));
	}

	public void SetOnline(Boolean online) {
		this.online = online;
	}
	
	public void SetStatusMsg(ChatMsg cm) {
		UserStatusMsg = cm.UserStatusMsg;
		stateMsgLbl.setText(UserStatusMsg);
	}
	
	// 친구 프로필 사진 바뀌었을 때
	public void ChangeFriendProfile(ChatMsg cm) {
		
	}

	/*
	public void SetSelectable(Boolean onoff) {
		if(online) {
			checkboxSelect.setIcon(online_notchecked);
			checkboxSelect.setDisabledIcon(online_notchecked);
			checkboxSelect.setSelectedIcon(online_checked);
			nameLbl.setBounds(65, 10, 95, 50);
			checkboxSelect.setBounds(160, 29, 13, 13);
		}
		checkboxSelect.setEnabled(onoff);
	}
	*/

	
	/*클릭 이벤트 리스너*/
	 class MyActionListener implements ActionListener{
		  
		  @Override
		  public void actionPerformed(ActionEvent e) {
			   if(e.getSource() == contentPane){
				   
			   }
		   }	  
	 }

}

