import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import javax.swing.JTextPane;
import java.awt.Panel;
import java.awt.ScrollPane;

import javax.swing.JScrollPane;
import javax.swing.DropMode;
import javax.swing.ImageIcon;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JLabel;

public class ChatClientMainView extends JFrame{
	//ChatClientMainView mainView;
	private static final long serialVersionUID = 1L;
	public List<Map<String, UserInfo>> FriendsList = new ArrayList<>();
	public Vector<ChatRoom> ChatRoomVector = new Vector();
	public Vector<UserInfo> Users = new Vector<UserInfo>();
	public UserInfo userInfo;
	public String UserName;
	public ChatClientMainView mainView;
	public ImageIcon UserIcon;
	public ImageIcon profile = new ImageIcon(ChatClientMainView.class.getResource("./img/standardProfile.png"));
	
	UserInfo u1 = new UserInfo("user1", "50");
	UserInfo u2 = new UserInfo("user2", "50");
	UserInfo u3 = new UserInfo("user3", "50");
	UserInfo u4 = new UserInfo("user4", "50");
	UserInfo u5 = new UserInfo("user5", "50");
	
	public JPanel contentPane;
	public JPanel panel;
	public JButton friendListBtn;
	public JButton chatListBtn;
	public JPanel friendPanel;
	public JPanel chatPanel;
	public JScrollPane scrollPaneFriendList;
	public JScrollPane scrollPaneChatList;
	//public NewScrollPane scrollPaneFriend;
	//public JScrollPane scrollPaneChat;
	public JTextPane textPaneFriendList;
	public JTextPane textPaneChatList;
	public JButton addChatRoomBtn;
	public JPanel chatPanelHeader;
	public JPanel friendHeader;
	public JLabel friendLabel;
	
	private Socket socket;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private Panel chatPanel2;
	private JLabel chatLabel;
	
	
	
	public ChatClientMainView(String username, String ip_addr, String port_no) {
		this.mainView = this;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 394, 630);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		panel = new JPanel();
		panel.setBounds(0, 0, 82, 630);
		panel.setBorder(null);
		panel.setLayout(null);
		contentPane.add(panel);
		
		ImageIcon img = new ImageIcon(ChatClientMainView.class.getResource("./img/msg.png"));
		img = imageSetSize(img, 50, 50);
		friendListBtn = new JButton("");
		friendListBtn.setEnabled(true);
		
		friendListBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				chatListBtn.setEnabled(true);
				friendListBtn.setEnabled(false);
				chatPanel.setVisible(false);
				friendPanel.setVisible(true);
			}
		});
		friendListBtn.setBorder(null);
		friendListBtn.setBounds(15, 21, 50, 50);
		friendListBtn.setIcon(img);
		friendListBtn.setBorderPainted(false);
		friendListBtn.setContentAreaFilled(false);
		friendListBtn.setOpaque(false);
		friendListBtn.setFocusPainted(false);
		panel.add(friendListBtn);
		
		ImageIcon img2 = new ImageIcon(ChatClientMainView.class.getResource("./img/msg.png"));
		img2 = imageSetSize(img2, 50, 50);
		chatListBtn = new JButton(img2);
		chatListBtn.setEnabled(true);
		chatListBtn.setBounds(15, 83, 50, 50);
		//chatList.setBorder(null);
		chatListBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				friendListBtn.setEnabled(true);
				chatListBtn.setEnabled(false);
				friendPanel.setVisible(false);
				chatPanel.setVisible(true);
			}
			
		});
		chatListBtn.setBorder(null);
		chatListBtn.setIcon(img);
		chatListBtn.setBorderPainted(false);
		chatListBtn.setContentAreaFilled(false);
		chatListBtn.setOpaque(false);
		chatListBtn.setFocusPainted(false);
		panel.add(chatListBtn);
		
		friendPanel = new JPanel();
		friendPanel.setBounds(82, 0, 312, 602);
		friendPanel.setLayout(null);
		contentPane.add(friendPanel);
		
		scrollPaneFriendList = new JScrollPane();
		scrollPaneFriendList.setBounds(0, 63, 312, 539);
		scrollPaneFriendList.setLayout(null);
		friendPanel.add(scrollPaneFriendList);
		
		textPaneFriendList = new JTextPane();
		textPaneFriendList.setEditable(false);
		//scrollPaneFriendList.setViewportView(textPaneFriendList);
		textPaneFriendList.setBounds(0, 0, 312, 602);
		//scrollPaneFriendList.add(textPaneFriendList);
		
		friendHeader = new JPanel();
		friendHeader.setLayout(null);
		friendHeader.setBackground(new Color(255, 255, 255));
		friendHeader.setBounds(0, 0, 312, 62);
		friendPanel.add(friendHeader);
		
		friendLabel = new JLabel("친구");
		friendLabel.setBounds(10, 15, 54, 30);
		friendLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 28));
		friendHeader.add(friendLabel);
		
		chatPanel = new JPanel();
		chatPanel.setVisible(false);
		chatPanel.setBounds(82, 0, 312, 602);
		chatPanel.setLayout(null);
		contentPane.add(chatPanel);
		
		scrollPaneChatList = new JScrollPane();
		scrollPaneChatList.setLayout(null);
		//scrollPaneChatList.setViewportBorder(null);
		scrollPaneChatList.setBounds(0, 62, 312, 541);
		chatPanel.add(scrollPaneChatList);
		
		textPaneChatList = new JTextPane();
		textPaneChatList.setEditable(false);
		scrollPaneChatList.setViewportView(textPaneChatList);
		textPaneChatList.setBounds(0, 0, 312, 602);
		//scrollPaneChatList.add(textPaneChatList);
		
		chatPanelHeader = new JPanel();
		chatPanelHeader.setBackground(new Color(255, 255, 255));
		chatPanelHeader.setLayout(null);
		chatPanelHeader.setBounds(0, 0, 312, 61);
		chatPanel.add(chatPanelHeader);
		
		ImageIcon img3 = new ImageIcon(ChatClientMainView.class.getResource("./img/chatPlus.png"));
		img3 = imageSetSize(img3, 50, 50);
		addChatRoomBtn = new JButton(img3);
		addChatRoomBtn.setBounds(250, 5, 50, 50);
		addChatRoomBtn.setBorderPainted(false);
		addChatRoomBtn.setContentAreaFilled(false);
		addChatRoomBtn.setOpaque(false);
		addChatRoomBtn.setFocusPainted(false);
		chatPanelHeader.add(addChatRoomBtn);
		
		chatLabel = new JLabel("채팅");
		chatLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 28));
		chatLabel.setBounds(10, 15, 100, 30);
		chatPanelHeader.add(chatLabel);
		
		
		setVisible(true);
		
		
		try {
			socket = new Socket(ip_addr, Integer.parseInt(port_no));
			oos = new ObjectOutputStream(socket.getOutputStream());
			oos.flush();
			ois = new ObjectInputStream(socket.getInputStream());
			
			chatPanel.setVisible(false);
			friendPanel.setVisible(true);
			
			AddFriend(profile, username, "한성대학교 컴퓨터공학부");
			ChatMsg obcm = new ChatMsg(username, "100", "Hello");
			AddChatRoom(obcm);
			
			ChatMsg obcm2 = new ChatMsg(username, "100", "Hello");
			AddChatRoom(obcm2);
			//AddFriend(profile, username, "한성대학교 컴퓨터공학부");
			
			//ChatMsg obcm2 = new ChatMsg(username, "100", "Hello");
			//AddChatRoom(obcm2);
			//AddChatList(obcm);
			SendObject(obcm);
			//SendObject(obcm2);
			
			ListenNetwork net = new ListenNetwork();
			net.start();
		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("connect error");
		}
	}
	
	class ListenNetwork extends Thread {
		public void run() {
			while (true) {
				try {
					Object ob = null;
					String msg = null;
					UserInfo ui;
					try {
						ob = ois.readObject();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						break;
					}
					if (ob == null)
						break;
					
					if (ob instanceof UserInfo) {
						System.out.println("Received User List");
					} else
						continue;
				} catch (IOException e) {
					System.out.println(e + "	ois.readObject() error");
					try {
//							dos.close();
//							dis.close();
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
	
	public String ShowDialog() {
		
		return "";
	}
	
	public void LoginNewFriend(ChatMsg cm) {
		for(Friend f : FriendVector) {
			if(f.UserName.equals(cm.UserName)) {
				/*
				if(f.UserName.equals(cm.UserName)) 
					//f.SetOnine(true);
				
				else
					//f.SetOnline(false);
				*/
				f.SetStatusMsg(cm);
				return;
			
			}
		}
		AddFriend(cm.img, cm.UserName, cm.UserStatusMsg);
	}
	
	public void LogoutFriend(ChatMsg cm) {
		for(Friend f : FriendVector) {
			if(f.UserName.equals(cm.UserName)) {
				//f.SetOnline(false);
			}
		}
	}
	
	public void ChangeFriendProfile(ChatMsg cm) {
		UserIcon = cm.img;
		for(Friend f : FriendVector) {
			if(f.UserName.equals(cm.UserName)) {
				//f.SetIcon(cm);
			}
			//
		}
	}
	
	public Vector<Friend> FriendVector = new Vector<Friend>();
	
	public void AddFriend(ImageIcon icon, String username, String statusmsg) { // on off 표시시 String userstatus 추가 
		int len = textPaneFriendList.getDocument().getLength();
		textPaneFriendList.setCaretPosition(len);
		Friend f = new Friend(mainView, icon, username, statusmsg);
		f.UserStatusMsg = statusmsg;
		scrollPaneFriendList.add(f);
		//textPaneFriendList.insertComponent(f);
		if(username.equals(UserName)) {
			f.setBackground(new Color(240, 240, 240));
			// f.SetProfileButtonActive(); // 본인의 Profile 사진은 변경할 수 있다.
			f.SetStatusChangeActive();
		}
		f.SetProfileButtonActive(); // 프사 변경, 프사 확인 버튼 활성화 
		//f.SetSelectable(false); // check box는 diable 시켜서 상태 확인용으로만 사용된다.
		FriendVector.add(f);
		//textPaneFriendList.setCaretPosition(0);
		//repaint();
	}
	
	
	public void NewChatClientRoomView() {
		//roomView = new ChatClientMainView(mainview, UserName, roomId, UserList);
	}
	
	public void AddChatRoom(ChatMsg cm) {
		System.out.println("Add Chat Room");
		int len = textPaneChatList.getDocument().getLength();
		System.out.println("Len " + len);
		textPaneChatList.setCaretPosition(len);
		ChatRoom r = new ChatRoom(mainView, UserName, cm.roomId, cm.userlist);
		//r.setAlignmentY(len);
		System.out.println(r.userlist);
		scrollPaneChatList.add(r);
		//textPaneChatList.insertComponent(r);
		//textPaneChatList.
		ChatRoomVector.add(r);
		textPaneChatList.setCaretPosition(0);
		repaint();
	}
	/*
	public void AddChatList(ChatMsg cm) {
		int len = friendListPanel.getDocument().getLength();
		friendListPanel.setCaretPosition(len);
		ChatRoom r = new ChatRoom(mainView, UserName, cm.roomId, cm.userlist);
		//r.setOpaque(true);
		//friendListPanel.insertComponent(r);
		ChatRoomVector.add(r);
		friendListPanel.setCaretPosition(len);
	}
	*/
	public ChatRoom SearchChatRoom(String roomId) {
		for(ChatRoom r : ChatRoomVector) {
			if(r.roomId.equals(roomId))
				return r;
		}
		return null;
	}
	
	public void SendObject(Object ob) { // 서버로 메세지를 보내는 메소드
		try {
			oos.writeObject(ob);
		} catch (IOException e) {
			// textArea.append("메세지 송신 에러!!\n");
			System.out.println(e + "	SendObject Error");
		}
	}
	
	public ImageIcon imageSetSize(ImageIcon icon, int i, int j) { // image Size Setting
		Image ximg = icon.getImage();  //ImageIcon을 Image로 변환.
		Image yimg = ximg.getScaledInstance(i, j, java.awt.Image.SCALE_SMOOTH);
		ImageIcon xyimg = new ImageIcon(yimg); 
		return xyimg;
	}
}
