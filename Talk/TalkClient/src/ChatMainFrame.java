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
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 69, 315, 531);
		
		textPaneFriendList = new JTextPane();
		textPaneFriendList.setEditable(false); //�Է��� �Ұ��ϸ鼭 �ؽ�Ʈ ������ �ٲٰ� ������
		textPaneFriendList.setBackground(Color.WHITE);
		scrollPane.setViewportView(textPaneFriendList);

		friendHeader.add(friendLabel);
		friendHeader.add(frdPlusBtn);

		friendPanel.add(friendHeader);
		friendPanel.add(scrollPane);
		
		
		/*�޼��� List Panel*/
		messagePanel = new JPanel();
		messagePanel.setLayout(null);
		messagePanel.setBounds(75, 0, 315, 600);
		messagePanel.setBackground(Color.BLUE);
		
		
		// ������ Panel ��ü Panel�� ���̱�
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
		
	
//	/*ģ���� ������ ���� �ٲ� ��*/
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
	
//	/*ģ�� ����Ʈ ���Ƿ� ����*/
//	public void CreateFriendIconHash() {
//		UserList.add("user1");
//		UserList.add("user2");
//		
//		ChatMsg friend1 = new ChatMsg("user1", "600", "");
//		friend1.setProfile(standardProfile);
//		friend1.setStateMsg("ù��° ģ��");
//		
//		ChatMsg friend2 = new ChatMsg("user2", "600", "");
//		friend2.setProfile(standardProfile);
//		friend2.setStateMsg("�ι�° ģ��");
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
	
		
//	/*ģ�� ��� textArea�� ���̱�*/
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
		
	/*������ �޼����� ������ �޼ҵ�*/
	public void SendObject(Object ob) { 
		try {
			oos.writeObject(ob);
		} catch (IOException e) {
			// textArea.append("�޼��� �۽� ����!!\n");
			//AppendText("SendObject Error", check);
		}
	}

//	/*Server Message�� �����ؼ� ȭ�鿡 ǥ��*/
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
//							//AppendTextR(msg); // �� �޼����� ������
//						else
//							System.out.println("msg");
//							//AppendText(msg);
//						break;
//					case "300": // Image ÷��
//						if (cm.id.equals(Username))
//							System.out.println(cm.id);
//							//AppendTextR("[" + cm.id + "]");
//						else
//							System.out.println(cm.id);
//							//AppendText("[" + cm.id + "]");
//						//AppendImage(cm.img);
//						break;
//					case "500": // Mouse Event ����
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
//					} // catch�� ��
//				} // �ٱ� catch����
//
//			}
//		}
//	}
	
	
	/*Ŭ�� �̺�Ʈ ������*/
	 class MyActionListener implements ActionListener{
		  
		  @Override
		  public void actionPerformed(ActionEvent e) {
		   
			   if(e.getSource() == frdBtn){ // ģ�� ����Ʈ â ��ư
				   frdBtn.setEnabled(false); //ģ�� ��ư ���ϰ�
				   msgBtn.setEnabled(true);
				   moreBtn.setEnabled(true);  
				   
				   friendPanel.setVisible(true); //ģ��â ���̰�
				   messagePanel.setVisible(false);

			   }else if(e.getSource() == msgBtn){ // �޼��� ����Ʈ â ��ư
				   frdBtn.setEnabled(true);
				   msgBtn.setEnabled(false); //�޼��� ��ư ���ϰ�
				   moreBtn.setEnabled(true);
				   
				   friendPanel.setVisible(false);
				   messagePanel.setVisible(true); //�޼���â ���̰�
				   
			   }else if(e.getSource() == moreBtn) { // ������ ��ư
				   frdBtn.setEnabled(true); 
				   msgBtn.setEnabled(false); 
				   moreBtn.setEnabled(false); //������ ��ư ���ϰ�
				   
				   friendPanel.setVisible(false);
				   messagePanel.setVisible(true);
				   
			   }else if(e.getSource() == frdPlusBtn) { // ģ�� �߰� ��ư
				   
			   }
		   }
	 }
}
