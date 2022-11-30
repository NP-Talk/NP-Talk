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
	
	private Socket socket; // 연결소켓
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
	
	
	private ImageIcon friend_img = new ImageIcon(ChatMainFrame.class.getResource("./img/user.png")); //친구 버튼
	private ImageIcon msg_img = new ImageIcon(ChatMainFrame.class.getResource("./img/msg.png")); //메세지 버튼 
	private ImageIcon more_img = new ImageIcon(ChatMainFrame.class.getResource("./img/more.png")); //더보기 버튼 
	private ImageIcon frdPlus_img = new ImageIcon(ChatMainFrame.class.getResource("./img/frdPlus.png")); //친구 추가 버튼
	private ImageIcon msgPlus_img = new ImageIcon(ChatMainFrame.class.getResource("")); //메세지 추가 버튼
	private ImageIcon standardProfile = new ImageIcon(ChatMainFrame.class.getResource("./img/standardProfile.png")); //기본 프로필 이미지 
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
		
		/*전체 Panel*/
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		/*전환을 위한 버튼 Panel*/
		btnPanel = new JPanel();
		btnPanel.setBorder(null);
		btnPanel.setLayout(null);
		btnPanel.setBounds(0, 0, 76, 600);
		
		// 친구 버튼
		// 친구 아이콘 변환
		Image friend_ori_img = friend_img.getImage();
		Image friend_new_img = friend_ori_img.getScaledInstance(20, 20, Image.SCALE_DEFAULT);
		ImageIcon friend_new_icon = new ImageIcon(friend_new_img);

		frdBtn = new JButton(friend_new_icon);
		frdBtn.setEnabled(false);
		frdBtn.addActionListener(new MyActionListener());
		frdBtn.setBorder(null);
		frdBtn.setBounds(19, 15, 36, 36);
		frdBtn.setFocusPainted(false);		
		frdBtn.setBorderPainted(false);	//버튼에 이미지 넣고 이미지만 남기기
		frdBtn.setContentAreaFilled(false);
		
		// 메세지 버튼
		// 메세지 아이콘 변환
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

		// 더보기 버튼
		// 더보기 아이콘 변환
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
		
		
		/*친구 List Panel*/
		friendPanel = new JPanel();
		friendPanel.setLayout(null);
		friendPanel.setBounds(75, 0, 315, 600);
		
		
		// 친구 라벨 헤더
		friendHeader = new JPanel();
		friendHeader.setLayout(null);
		friendHeader.setBounds(0, 0, 315, 68);
		friendHeader.setBackground(Color.WHITE);
		
		friendLabel = new JLabel("친구");
		friendLabel.setFont(new Font("돋움", Font.BOLD, 20));
		friendLabel.setBounds(15, 15, 50, 40);
		
		// 친구 추가 버튼
		// 친구 추가 아이콘 변환
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
		
		// 친구 리스트 나열
		scrollPaneFriendList = new ScrollPane();
		scrollPaneFriendList.setBounds(0, 69, 315, 531);
		//scrollPaneFriendList.setBorder(null);
		
		textPaneFriendList = new JTextPane();
		textPaneFriendList.setEditable(false); //입력은 불가하면서 텍스트 색상을 바꾸고 싶을때
		textPaneFriendList.setBackground(Color.WHITE);
		scrollPaneFriendList.add(textPaneFriendList);
		//scrollPaneFriendList.setViewportView(textPaneFriendList);

		friendHeader.add(friendLabel);
		friendHeader.add(frdPlusBtn);

		friendPanel.add(friendHeader);
		friendPanel.add(scrollPaneFriendList);
		
		
		/*메세지 List Panel*/
		msgPanel = new JPanel();
		msgPanel.setLayout(null);
		msgPanel.setBounds(75, 0, 315, 600);
		msgPanel.setBackground(Color.BLUE);
	
		/*
		// 친구 라벨 헤더
		msgHeader = new JPanel();
		msgHeader.setLayout(null);
		msgHeader.setBounds(0, 0, 315, 68);
		msgHeader.setBackground(Color.WHITE);
		
		msgLabel = new JLabel("채팅");
		msgLabel.setFont(new Font("돋움", Font.BOLD, 20));
		msgLabel.setBounds(15, 15, 50, 40);
		
		// 채팅 추가 버튼
		// 채팅 추가 아이콘 변환
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
		
		// 채팅 리스트 나열
		scrollPaneMsgList = new JScrollPane();
		scrollPaneMsgList.setBounds(0, 69, 315, 531);
		scrollPaneMsgList.setBorder(null);
		
		textPaneMsgList = new JTextPane();
		textPaneMsgList.setEditable(false); //입력은 불가하면서 텍스트 색상을 바꾸고 싶을때
		textPaneMsgList.setBackground(Color.WHITE);
		scrollPaneMsgList.setViewportView(textPaneMsgList);

		msgHeader.add(msgLabel);
		msgHeader.add(msgPlusBtn);

		msgPanel.add(msgHeader);
		msgPanel.add(scrollPaneMsgList);
		*/
		
		// 각각의 Panel 전체 Panel에 붙이기
		contentPane.add(btnPanel);
		contentPane.add(friendPanel);
		contentPane.add(msgPanel);
		
		
		/**/
		Username = username;
		UserIcon = standardProfile;
		//User 본인
		AttachFriend(standardProfile, Username, "O", "");
		
		/*로그인 처리*/ 
		ChatMsg obcm = new ChatMsg(Username, "100", "Login");
		obcm.UserStatus = "O";
		obcm.UserStatusMsg = "";
		obcm.profile = standardProfile;
		SendObject(obcm);
		
		ListenNetwork net = new ListenNetwork();
		net.start();
	}
	
	
	/**/
	public Vector<profileFrame> FriendVector = new Vector<profileFrame>(); //친구 프로필 Vector
	//public Vector<ChatRoom> ChatRoomVector = new Vector<ChatRoom>();
	
	/*AddFriend*/
	public void AttachFriend(ImageIcon icon, String userName, String userStatus, String userStatusMsg) {
		//맨 끝으로 이동 
		int len = textPaneFriendList.getDocument().getLength();
		textPaneFriendList.setCaretPosition(len); //place caret at the end (with no selection)
		
		
		profileFrame f = new profileFrame(mainView, icon, userName, userStatus, userStatusMsg);
		textPaneFriendList.insertComponent(f);
		
		
		if(userName.equals(Username)) { //자기 자신 프로필 배경색으로 구분 
			f.setBackground(Color.GRAY);
			//f.setProfileButtonActive(); // 자신의 프로필 사진 변경할 수 있는 이벤트 
			//f.setStatusChangeActive(); // 본인 상태 메세지 변경할 수 있는 이벤트
		}
		
		//f.setProfileButtonActive(); // 프로필 변경, 프로필 확인 이벤트 활성화 
		FriendVector.add(f);
		textPaneFriendList.setCaretPosition(0);
		repaint();
	}
	
	/*친구찾기*/
	public profileFrame SearchFriend(String name) {
		for(profileFrame f: FriendVector) {
			if(f.Username.equals(name)) 
				return f;
		}
		return null;
	}
	
	/*새로운 친구가 들어왔을때*/
	public void LoginNewFriend(ChatMsg cm) {
		for(profileFrame f: FriendVector) {
			if(f.Username.equals(cm.id)) { //이미 로그인 되어 있는 유저 구분하기 위해 
				if(cm.UserStatus.equals("O")) 
					f.SetOnline(true);
				else 
					f.SetOnline(false);
				
				f.SetStatusMsg(cm);
				return;
			}
		}
		// for문 끝까지 돌려본 결과 이미 로그인 된 친구가 아닌 새로운 친구일 경우
		AttachFriend(cm.img, cm.id, cm.UserStatus, cm.UserStatusMsg);
	}
	
	/*친구가 로그아웃 했을경우*/ 
	public void LogoutFriend(ChatMsg cm) {
		for(profileFrame f: FriendVector) {
			if(f.Username.equals(cm.id)) 
				f.SetOnline(false);
		}
	}
	
	/*친구 프로필 사진*/
	public ImageIcon getUserProfielIcon(String Username) {
		for(profileFrame f: FriendVector) {
			if(f.Username.equals(Username)) 
				return f.UserprofileIcon;
		}
		return null;
	}
		
	//프로필 사진 바꿀 때
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
	//친구 리스트 임의로 만듦
	public void CreateFriendIconHash() {
		UserList.add("user1");
		UserList.add("user2");
		
		ChatMsg friend1 = new ChatMsg("user1", "600", "");
		friend1.setProfile(standardProfile);
		friend1.setStateMsg("첫번째 친구");
		
		ChatMsg friend2 = new ChatMsg("user2", "600", "");
		friend2.setProfile(standardProfile);
		friend2.setStateMsg("두번째 친구");
		
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
	
		
	//친구 목록 textArea에 붙이기
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
		
	//서버로 메세지를 보내는 메소드*
	public void SendObject(Object ob) { 
		try {
			oos.writeObject(ob);
		} catch (IOException e) {
			// textArea.append("메세지 송신 에러!!\n");
			//AppendText("SendObject Error", check);
		}
	}
	

	//Server Message를 수신해서 화면에 표시
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
					case "100": //New User Login, 새로운 친구 입장
						LoginNewFriend(cm);
						break;
					case "200": // chat message
						if (cm.id.equals(Username))
							System.out.println(msg);
							//AppendTextR(msg); // 내 메세지는 우측에
						else
							System.out.println("msg");
							//AppendText(msg);
						break;
					case "300": // Image 첨부
						if (cm.id.equals(Username))
							System.out.println(cm.id);
							//AppendTextR("[" + cm.id + "]");
						else
							System.out.println(cm.id);
							//AppendText("[" + cm.id + "]");
						//AppendImage(cm.img);
						break;
					case "500": // Mouse Event 수신
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
					} // catch문 끝
				} // 바깥 catch문끝

			}
		}
	}


	
	/*클릭 이벤트 리스너*/
	 class MyActionListener implements ActionListener{
		  
		  @Override
		  public void actionPerformed(ActionEvent e) {
		   
			   if(e.getSource() == frdBtn){ // 친구 리스트 창 버튼
				   frdBtn.setEnabled(false); //친구 버튼 진하게
				   msgBtn.setEnabled(true);
				   moreBtn.setEnabled(true);  
				   
				   friendPanel.setVisible(true); //친구창 보이게
				   msgPanel.setVisible(false);

			   }else if(e.getSource() == msgBtn){ // 메세지 리스트 창 버튼
				   frdBtn.setEnabled(true);
				   msgBtn.setEnabled(false); //메세지 버튼 진하게
				   moreBtn.setEnabled(true);
				   
				   friendPanel.setVisible(false);
				   msgPanel.setVisible(true); //메세지창 보이게
				   
			   }else if(e.getSource() == moreBtn) { // 더보기 버튼
				   frdBtn.setEnabled(true);
				   msgBtn.setEnabled(true); 
				   moreBtn.setEnabled(false); //더보기 버튼 진하게
				   
				   friendPanel.setVisible(false);
				   msgPanel.setVisible(true);
				   
			   }else if(e.getSource() == frdPlusBtn) { // 친구 추가 버튼
				   
			   }
		   }
	 }
}
