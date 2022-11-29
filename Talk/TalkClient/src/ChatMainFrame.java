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
	private JPanel contentPane, btnPanel, friendPanel, messagePanel, friendHeader;
	private JButton frdBtn, msgBtn, moreBtn, frdPlusBtn;
	private JLabel friendLabel;
	private JTextPane textPaneFriendList;
	
	private ImageIcon friend_img = new ImageIcon(ChatMainFrame.class.getResource("./img/user.png"));
	private ImageIcon msg_img = new ImageIcon(ChatMainFrame.class.getResource("./img/msg.png"));
	private ImageIcon more_img = new ImageIcon(ChatMainFrame.class.getResource("./img/more.png"));
	private ImageIcon frdPlus_img = new ImageIcon(ChatMainFrame.class.getResource("./img/frdPlus.png"));	
	private ImageIcon standardProfile = new ImageIcon(ChatMainFrame.class.getResource("./img/standardProfile.png"));
	private ImageIcon UserIcon;

	private ArrayList<HashMap<String, ChatMsg>> FriendLabelHash = new ArrayList<HashMap<String, ChatMsg>>();
	private ArrayList<String> UserList = new ArrayList<>();
	public ArrayList<profileFrame> FriendLableList = new ArrayList<profileFrame>();
	
	/**/
	public ChatMainFrame(String username, String ip_addr, String port_no) {		
		/**/
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 394, 630);
		setVisible(true);
		
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
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 69, 315, 531);
		
		textPaneFriendList = new JTextPane();
		textPaneFriendList.setEditable(false); //입력은 불가하면서 텍스트 색상을 바꾸고 싶을때
		textPaneFriendList.setBackground(Color.WHITE);
		scrollPane.setViewportView(textPaneFriendList);

		friendHeader.add(friendLabel);
		friendHeader.add(frdPlusBtn);

		friendPanel.add(friendHeader);
		friendPanel.add(scrollPane);
		
		
		/*메세지 List Panel*/
		messagePanel = new JPanel();
		messagePanel.setLayout(null);
		messagePanel.setBounds(75, 0, 315, 600);
		messagePanel.setBackground(Color.BLUE);
		
		
		// 각각의 Panel 전체 Panel에 붙이기
		contentPane.add(btnPanel);
		contentPane.add(friendPanel);
		contentPane.add(messagePanel);
	}
		//Username = username;
		
//		try {
//			socket = new Socket(ip_addr, Integer.parseInt(port_no));
////			is = socket.getInputStream();
////			dis = new DataInputStream(is);
////			os = socket.getOutputStream();
////			dos = new DataOutputStream(os);
//			
//			oos = new ObjectOutputStream(socket.getOutputStream());
//			oos.flush();
//			ois = new ObjectInputStream(socket.getInputStream());
//			
//			ChatMsg obcm = new ChatMsg(Username, "100", "Hello");
//			obcm.setProfile(standardProfile);
//			SendObject(obcm);
//
//			
//		} catch(NumberFormatException | IOException e){
//			
//		}
//	}
		
	
//	/*친구들 프로필 사진 바꿀 때*/
//	public void ChangeFriendProfile(ChatMsg cm) {
//		profileFrame f = FriendLabelHash.get(cm.id);
//		if(f == null) return;
//
//		f.SetIcon(cm);
//		for(profileFrame f1: FriendLableList) {
//			if(f1.name.equals(cm.id))
//				f1.SetIcon(cm);
//		}
//	}
	
//	/*친구 리스트 임의로 만듦*/
//	public void CreateFriendIconHash() {
//		UserList.add("user1");
//		UserList.add("user2");
//		
//		ChatMsg friend1 = new ChatMsg("user1", "600", "");
//		friend1.setProfile(standardProfile);
//		friend1.setStateMsg("첫번째 친구");
//		
//		ChatMsg friend2 = new ChatMsg("user2", "600", "");
//		friend2.setProfile(standardProfile);
//		friend2.setStateMsg("두번째 친구");
//		
//		((Map<String, ChatMsg>) FriendLabelHash).put(Username, friend1);
//		((Map<String, ChatMsg>) FriendLabelHash).put(Username, friend2);
//		
//		
//
//		for(int i=0; i<FriendLabelHash.size(); i++) {
//			ImageIcon icon = FriendLabelHash[i].ChatMsg.getProfile();
//			profileFrame f = new profileFrame();
//			FriendLabelHash.put(,f);
//		}
//	}	
//	public ImageIcon GetUserIcon() {
//		return UserIcon;
//	}
	
		
//	/*친구 목록 textArea에 붙이기*/
//	public void AppendList(ChatMsg cm) {
//		profileFrame f1 = FriendLabelHash.get(cm.id);
//		profileFrame f2 = new profileFrame(f1.UserIcon, f1.UserName);
//		FriendLabelList.add(f2);
//		
//		textPaneFriendList.setCaretPosition(textPaneFriendList.getDocument().getLength());
//		textPaneFriendList.insertComponent(f2);
//		
//		int len = textPaneFriendList.getDocument().getLength();
//		textPaneFriendList.setCaretPosition(len);
//	}
//	
		
	/*서버로 메세지를 보내는 메소드*/
	public void SendObject(Object ob) { 
		try {
			oos.writeObject(ob);
		} catch (IOException e) {
			// textArea.append("메세지 송신 에러!!\n");
			//AppendText("SendObject Error", check);
		}
	}

//	/*Server Message를 수신해서 화면에 표시*/
//	class ListenNetwork extends Thread {
//		public void run() {
//			while (true) {
//				try {
//
//					Object obcm = null;
//					String msg = null;
//					ChatMsg cm;
//					try {
//						obcm = ois.readObject();
//					} catch (ClassNotFoundException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//						break;
//					}
//					if (obcm == null)
//						break;
//					if (obcm instanceof ChatMsg) {
//						cm = (ChatMsg) obcm;
//						msg = String.format("[%s]\n%s", cm.id, cm.data);
//					} else
//						continue;
//					switch (cm.code) {
//					case "200": // chat message
//						if (cm.id.equals(Username))
//							System.out.println(msg);
//							//AppendTextR(msg); // 내 메세지는 우측에
//						else
//							System.out.println("msg");
//							//AppendText(msg);
//						break;
//					case "300": // Image 첨부
//						if (cm.id.equals(Username))
//							System.out.println(cm.id);
//							//AppendTextR("[" + cm.id + "]");
//						else
//							System.out.println(cm.id);
//							//AppendText("[" + cm.id + "]");
//						//AppendImage(cm.img);
//						break;
//					case "500": // Mouse Event 수신
//						//drawing.DoMouseEvent(cm);
//						break;
//					}
//				} catch (IOException e) {
//					//AppendText("ois.readObject() error");
//					try {
////						dos.close();
////						dis.close();
//						ois.close();
//						oos.close();
//						socket.close();
//
//						break;
//					} catch (Exception ee) {
//						break;
//					} // catch문 끝
//				} // 바깥 catch문끝
//
//			}
//		}
//	}
	
	
	/*클릭 이벤트 리스너*/
	 class MyActionListener implements ActionListener{
		  
		  @Override
		  public void actionPerformed(ActionEvent e) {
		   
			   if(e.getSource() == frdBtn){ // 친구 리스트 창 버튼
				   frdBtn.setEnabled(false); //친구 버튼 진하게
				   msgBtn.setEnabled(true);
				   moreBtn.setEnabled(true);  
				   
				   friendPanel.setVisible(true); //친구창 보이게
				   messagePanel.setVisible(false);

			   }else if(e.getSource() == msgBtn){ // 메세지 리스트 창 버튼
				   frdBtn.setEnabled(true);
				   msgBtn.setEnabled(false); //메세지 버튼 진하게
				   moreBtn.setEnabled(true);
				   
				   friendPanel.setVisible(false);
				   messagePanel.setVisible(true); //메세지창 보이게
				   
			   }else if(e.getSource() == moreBtn) { // 더보기 버튼
				   frdBtn.setEnabled(true); 
				   msgBtn.setEnabled(false); 
				   moreBtn.setEnabled(false); //더보기 버튼 진하게
				   
				   friendPanel.setVisible(false);
				   messagePanel.setVisible(true);
				   
			   }else if(e.getSource() == frdPlusBtn) { // 친구 추가 버튼
				   
			   }
		   }
	 }
}
