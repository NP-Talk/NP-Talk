import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.util.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class ChatMainFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private String Username;
	
	private Socket socket; // �������
	private InputStream is;
	private OutputStream os;
	private DataInputStream dis;
	private DataOutputStream dos;

	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	boolean check = false;
	
	//
	private ChatMainFrame mainView;
	private ScrollPane scrollPaneMsgList;
	private ScrollPane scrollPaneFriendList;
	
	private JPanel contentPane, btnPanel, friendPanel, msgPanel;
	private JPanel friendHeader, msgHeader;
	
	private JButton frdBtn, msgBtn, moreBtn, frdPlusBtn, msgPlusBtn;
	private JLabel friendLabel, msgLabel;
	private JTextPane textPaneFriendList, textPaneMsgList;
	
	
	private ImageIcon friend_img = new ImageIcon(ChatMainFrame.class.getResource("./img/user.png")); //ģ�� ��ư
	private ImageIcon msg_img = new ImageIcon(ChatMainFrame.class.getResource("./img/msg.png")); //�޼��� ��ư 
	private ImageIcon more_img = new ImageIcon(ChatMainFrame.class.getResource("./img/more.png")); //������ ��ư 
	private ImageIcon frdPlus_img = new ImageIcon(ChatMainFrame.class.getResource("./img/frdPlus.png")); //ģ�� �߰� ��ư
	private ImageIcon msgPlus_img = new ImageIcon(ChatMainFrame.class.getResource("")); //�޼��� �߰� ��ư
	private ImageIcon standardProfile = new ImageIcon(ChatMainFrame.class.getResource("./img/standardProfile.png")); //�⺻ ������ �̹��� 
	public ImageIcon UserIcon;

//	private ArrayList<HashMap<String, ChatMsg>> FriendLabelHash = new ArrayList<HashMap<String, ChatMsg>>();
//	private ArrayList<String> UserList = new ArrayList<>();
//	public ArrayList<profileFrame> FriendLableList = new ArrayList<profileFrame>();
	
	/**/
	public ChatMainFrame(String username, String ip_addr, String port_no) {		
		/**/
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 394, 630);
		setVisible(true);
		mainView = this;
		
		//CreateFriendIconHash();
		
		/*��ü Panel*/
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		/*��ȯ�� ���� ��ư Panel*/
		btnPanel = new JPanel();
		btnPanel.setBorder(null);
		btnPanel.setLayout(null);
		btnPanel.setBounds(0, 0, 76, 600);
		
		// ģ�� ��ư
		// ģ�� ������ ��ȯ
		Image friend_ori_img = friend_img.getImage();
		Image friend_new_img = friend_ori_img.getScaledInstance(20, 20, Image.SCALE_DEFAULT);
		ImageIcon friend_new_icon = new ImageIcon(friend_new_img);

		frdBtn = new JButton(friend_new_icon);
		frdBtn.setEnabled(false);
		frdBtn.addActionListener(new MyActionListener());
		frdBtn.setBorder(null);
		frdBtn.setBounds(19, 15, 36, 36);
		frdBtn.setFocusPainted(false);		
		frdBtn.setBorderPainted(false);	//��ư�� �̹��� �ְ� �̹����� �����
		frdBtn.setContentAreaFilled(false);
		
		// �޼��� ��ư
		// �޼��� ������ ��ȯ
		Image msg_ori_img = msg_img.getImage();
		Image msg_new_img = msg_ori_img.getScaledInstance(18, 20, Image.SCALE_DEFAULT);
		ImageIcon msg_new_icon = new ImageIcon(msg_new_img);

		msgBtn = new JButton(msg_new_icon);
		msgBtn.setEnabled(true);	
		msgBtn.addActionListener(new MyActionListener());
		msgBtn.setBorder(null);
		msgBtn.setBounds(19, 55, 36, 36);		
		msgBtn.setFocusPainted(false);		
		msgBtn.setBorderPainted(false);
		msgBtn.setContentAreaFilled(false);

		// ������ ��ư
		// ������ ������ ��ȯ
		Image more_ori_img = more_img.getImage();
		Image more_new_img = more_ori_img.getScaledInstance(18, 20, Image.SCALE_DEFAULT);
		ImageIcon more_new_icon = new ImageIcon(more_new_img);

		moreBtn = new JButton(more_new_icon);
		moreBtn.setEnabled(true);
		moreBtn.addActionListener(new MyActionListener());
		moreBtn.setBorder(null);
		moreBtn.setBounds(19, 95, 36, 36);
		moreBtn.setFocusPainted(false);		
		moreBtn.setBorderPainted(false);
		moreBtn.setContentAreaFilled(false);
		
		btnPanel.add(frdBtn);
		btnPanel.add(msgBtn);
		btnPanel.add(moreBtn);
		
		
		/*ģ�� List Panel*/
		friendPanel = new JPanel();
		friendPanel.setLayout(null);
		friendPanel.setBounds(75, 0, 315, 600);
		
		
		// ģ�� �� ���
		friendHeader = new JPanel();
		friendHeader.setLayout(null);
		friendHeader.setBounds(0, 0, 315, 68);
		friendHeader.setBackground(Color.WHITE);
		
		friendLabel = new JLabel("ģ��");
		friendLabel.setFont(new Font("����", Font.BOLD, 20));
		friendLabel.setBounds(15, 15, 50, 40);
		
		// ģ�� �߰� ��ư
		// ģ�� �߰� ������ ��ȯ
		Image frdPlus_ori_img = frdPlus_img.getImage();
		Image frdPlus_new_img = frdPlus_ori_img.getScaledInstance(20, 20, Image.SCALE_DEFAULT);
		ImageIcon frdPlus_new_icon = new ImageIcon(frdPlus_new_img);
		
		frdPlusBtn = new JButton(frdPlus_new_icon);
		frdPlusBtn.setEnabled(true);
		frdPlusBtn.addActionListener(new MyActionListener());
		frdPlusBtn.setBounds(245, 17, 35, 35);
		frdPlusBtn.setFocusPainted(false);		
		frdPlusBtn.setBorderPainted(false);
		frdPlusBtn.setContentAreaFilled(false);
		
		// ģ�� ����Ʈ ����
		scrollPaneFriendList = new ScrollPane();
		scrollPaneFriendList.setBounds(0, 69, 315, 531);
		//scrollPaneFriendList.setBorder(null);
		
		textPaneFriendList = new JTextPane();
		textPaneFriendList.setEditable(false); //�Է��� �Ұ��ϸ鼭 �ؽ�Ʈ ������ �ٲٰ� ������
		textPaneFriendList.setBackground(Color.WHITE);
		scrollPaneFriendList.add(textPaneFriendList);
		//scrollPaneFriendList.setViewportView(textPaneFriendList);

		friendHeader.add(friendLabel);
		friendHeader.add(frdPlusBtn);

		friendPanel.add(friendHeader);
		friendPanel.add(scrollPaneFriendList);
		
		
		/*�޼��� List Panel*/
		msgPanel = new JPanel();
		msgPanel.setLayout(null);
		msgPanel.setBounds(75, 0, 315, 600);
		msgPanel.setBackground(Color.BLUE);
	
		/*
		// ģ�� �� ���
		msgHeader = new JPanel();
		msgHeader.setLayout(null);
		msgHeader.setBounds(0, 0, 315, 68);
		msgHeader.setBackground(Color.WHITE);
		
		msgLabel = new JLabel("ä��");
		msgLabel.setFont(new Font("����", Font.BOLD, 20));
		msgLabel.setBounds(15, 15, 50, 40);
		
		// ä�� �߰� ��ư
		// ä�� �߰� ������ ��ȯ
		Image msgPlus_ori_img = msgPlus_img.getImage();
		Image msgPlus_new_img = msgPlus_ori_img.getScaledInstance(20, 20, Image.SCALE_DEFAULT);
		ImageIcon msgPlus_new_icon = new ImageIcon(msgPlus_new_img);
		
		msgPlusBtn = new JButton(msgPlus_new_icon);
		msgPlusBtn.setEnabled(true);
		msgPlusBtn.addActionListener(new MyActionListener());
		msgPlusBtn.setBounds(245, 17, 35, 35);
		msgPlusBtn.setFocusPainted(false);		
		msgPlusBtn.setBorderPainted(false);
		msgPlusBtn.setContentAreaFilled(false);
		
		// ä�� ����Ʈ ����
		scrollPaneMsgList = new JScrollPane();
		scrollPaneMsgList.setBounds(0, 69, 315, 531);
		scrollPaneMsgList.setBorder(null);
		
		textPaneMsgList = new JTextPane();
		textPaneMsgList.setEditable(false); //�Է��� �Ұ��ϸ鼭 �ؽ�Ʈ ������ �ٲٰ� ������
		textPaneMsgList.setBackground(Color.WHITE);
		scrollPaneMsgList.setViewportView(textPaneMsgList);

		msgHeader.add(msgLabel);
		msgHeader.add(msgPlusBtn);

		msgPanel.add(msgHeader);
		msgPanel.add(scrollPaneMsgList);
		*/
		
		// ������ Panel ��ü Panel�� ���̱�
		contentPane.add(btnPanel);
		contentPane.add(friendPanel);
		contentPane.add(msgPanel);
		
		
		/**/
		Username = username;
		UserIcon = standardProfile;
		//User ����
		AttachFriend(standardProfile, Username, "O", "");
		
		/*�α��� ó��*/ 
		ChatMsg obcm = new ChatMsg(Username, "100", "Login");
		obcm.UserStatus = "O";
		obcm.UserStatusMsg = "";
		obcm.profile = standardProfile;
		SendObject(obcm);
		
		ListenNetwork net = new ListenNetwork();
		net.start();
	}
	
	
	/**/
	public Vector<profileFrame> FriendVector = new Vector<profileFrame>(); //ģ�� ������ Vector
	//public Vector<ChatRoom> ChatRoomVector = new Vector<ChatRoom>();
	
	/*AddFriend*/
	public void AttachFriend(ImageIcon icon, String userName, String userStatus, String userStatusMsg) {
		//�� ������ �̵� 
		int len = textPaneFriendList.getDocument().getLength();
		textPaneFriendList.setCaretPosition(len); //place caret at the end (with no selection)
		
		
		profileFrame f = new profileFrame(mainView, icon, userName, userStatus, userStatusMsg);
		textPaneFriendList.insertComponent(f);
		
		
		if(userName.equals(Username)) { //�ڱ� �ڽ� ������ �������� ���� 
			f.setBackground(Color.GRAY);
			//f.setProfileButtonActive(); // �ڽ��� ������ ���� ������ �� �ִ� �̺�Ʈ 
			//f.setStatusChangeActive(); // ���� ���� �޼��� ������ �� �ִ� �̺�Ʈ
		}
		
		//f.setProfileButtonActive(); // ������ ����, ������ Ȯ�� �̺�Ʈ Ȱ��ȭ 
		FriendVector.add(f);
		textPaneFriendList.setCaretPosition(0);
		repaint();
	}
	
	/*ģ��ã��*/
	public profileFrame SearchFriend(String name) {
		for(profileFrame f: FriendVector) {
			if(f.Username.equals(name)) 
				return f;
		}
		return null;
	}
	
	/*���ο� ģ���� ��������*/
	public void LoginNewFriend(ChatMsg cm) {
		for(profileFrame f: FriendVector) {
			if(f.Username.equals(cm.id)) { //�̹� �α��� �Ǿ� �ִ� ���� �����ϱ� ���� 
				if(cm.UserStatus.equals("O")) 
					f.SetOnline(true);
				else 
					f.SetOnline(false);
				
				f.SetStatusMsg(cm);
				return;
			}
		}
		// for�� ������ ������ ��� �̹� �α��� �� ģ���� �ƴ� ���ο� ģ���� ���
		AttachFriend(cm.img, cm.id, cm.UserStatus, cm.UserStatusMsg);
	}
	
	/*ģ���� �α׾ƿ� �������*/ 
	public void LogoutFriend(ChatMsg cm) {
		for(profileFrame f: FriendVector) {
			if(f.Username.equals(cm.id)) 
				f.SetOnline(false);
		}
	}
	
	/*ģ�� ������ ����*/
	public ImageIcon getUserProfielIcon(String Username) {
		for(profileFrame f: FriendVector) {
			if(f.Username.equals(Username)) 
				return f.UserprofileIcon;
		}
		return null;
	}
		
	//������ ���� �ٲ� ��
	public void ChangeFriendProfile(ChatMsg cm) {
		UserIcon = cm.profile;
		
		for(profileFrame f: FriendVector) {
			if(f.Username.equals(cm.id)) f.SetIcon(cm);
		}
		
//		for(ChatRoom r: ChatRoomVector) {
//			if(r.UserList.contains(cm.id)) r.ChangeFriendProfile(cm);
//		}
	}
	
	/*
	//ģ�� ����Ʈ ���Ƿ� ����
	public void CreateFriendIconHash() {
		UserList.add("user1");
		UserList.add("user2");
		
		ChatMsg friend1 = new ChatMsg("user1", "600", "");
		friend1.setProfile(standardProfile);
		friend1.setStateMsg("ù��° ģ��");
		
		ChatMsg friend2 = new ChatMsg("user2", "600", "");
		friend2.setProfile(standardProfile);
		friend2.setStateMsg("�ι�° ģ��");
		
		((Map<String, ChatMsg>) FriendLabelHash).put(Username, friend1);
		((Map<String, ChatMsg>) FriendLabelHash).put(Username, friend2);
		
		

		for(int i=0; i<FriendLabelHash.size(); i++) {
			ImageIcon icon = FriendLabelHash[i].ChatMsg.getProfile();
			profileFrame f = new profileFrame();
			FriendLabelHash.put(,f);
		}
	}	
	public ImageIcon GetUserIcon() {
		return UserIcon;
	}
	
		
	//ģ�� ��� textArea�� ���̱�
	public void AppendList(ChatMsg cm) {
		profileFrame f1 = FriendLabelHash.get(cm.id);
		profileFrame f2 = new profileFrame(f1.UserIcon, f1.UserName);
		FriendLabelList.add(f2);
		
		textPaneFriendList.setCaretPosition(textPaneFriendList.getDocument().getLength());
		textPaneFriendList.insertComponent(f2);
		
		int len = textPaneFriendList.getDocument().getLength();
		textPaneFriendList.setCaretPosition(len);
	}
	*/
		
	//������ �޼����� ������ �޼ҵ�*
	public void SendObject(Object ob) { 
		try {
			oos.writeObject(ob);
		} catch (IOException e) {
			// textArea.append("�޼��� �۽� ����!!\n");
			//AppendText("SendObject Error", check);
		}
	}
	

	//Server Message�� �����ؼ� ȭ�鿡 ǥ��
	class ListenNetwork extends Thread {
		public void run() {
			while (true) {
				try {
					Object obcm = null;
					String msg = null;
					ChatMsg cm;
					
					try {
						obcm = ois.readObject();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						break;
					}
					
					if (obcm == null)
						break;
						
					if (obcm instanceof ChatMsg) {
						cm = (ChatMsg) obcm;
						msg = String.format("[%s]\n%s", cm.id, cm.data);
					} else
						continue;
						
					switch (cm.code) {
					case "100": //New User Login, ���ο� ģ�� ����
						LoginNewFriend(cm);
						break;
					case "200": // chat message
						if (cm.id.equals(Username))
							System.out.println(msg);
							//AppendTextR(msg); // �� �޼����� ������
						else
							System.out.println("msg");
							//AppendText(msg);
						break;
					case "300": // Image ÷��
						if (cm.id.equals(Username))
							System.out.println(cm.id);
							//AppendTextR("[" + cm.id + "]");
						else
							System.out.println(cm.id);
							//AppendText("[" + cm.id + "]");
						//AppendImage(cm.img);
						break;
					case "500": // Mouse Event ����
						//drawing.DoMouseEvent(cm);
						break;
					}
				} catch (IOException e) {
					//AppendText("ois.readObject() error");
					try {
//						dos.close();
//						dis.close();
						ois.close();
						oos.close();
						socket.close();

						break;
					} catch (Exception ee) {
						break;
					} // catch�� ��
				} // �ٱ� catch����

			}
		}
	}


	
	/*Ŭ�� �̺�Ʈ ������*/
	 class MyActionListener implements ActionListener{
		  
		  @Override
		  public void actionPerformed(ActionEvent e) {
		   
			   if(e.getSource() == frdBtn){ // ģ�� ����Ʈ â ��ư
				   frdBtn.setEnabled(false); //ģ�� ��ư ���ϰ�
				   msgBtn.setEnabled(true);
				   moreBtn.setEnabled(true);  
				   
				   friendPanel.setVisible(true); //ģ��â ���̰�
				   msgPanel.setVisible(false);

			   }else if(e.getSource() == msgBtn){ // �޼��� ����Ʈ â ��ư
				   frdBtn.setEnabled(true);
				   msgBtn.setEnabled(false); //�޼��� ��ư ���ϰ�
				   moreBtn.setEnabled(true);
				   
				   friendPanel.setVisible(false);
				   msgPanel.setVisible(true); //�޼���â ���̰�
				   
			   }else if(e.getSource() == moreBtn) { // ������ ��ư
				   frdBtn.setEnabled(true);
				   msgBtn.setEnabled(true); 
				   moreBtn.setEnabled(false); //������ ��ư ���ϰ�
				   
				   friendPanel.setVisible(false);
				   msgPanel.setVisible(true);
				   
			   }else if(e.getSource() == frdPlusBtn) { // ģ�� �߰� ��ư
				   
			   }
		   }
	 }
}
