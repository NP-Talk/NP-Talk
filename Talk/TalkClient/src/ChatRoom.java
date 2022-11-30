//import java.awt.*;
//import java.sql.Date;
//import java.text.SimpleDateFormat;
//import java.util.*;
//
//import javax.swing.*;
//
//public class ChatRoom extends JPanel {
//	private static final long serialVersionUID = 1L;
//	
//	public ImageIcon RoomIcon;
//	public String Username, Room_ID, UserList, UserStatus; //Username = ä�ù� ������ ����
//	public String LastMsg = "���ο� ä��"; //������ message
//	public String LastTime; //������ message ���� �ð�
//	
//
//	public ChatClientChatRoomView roomView; // ���� ä��
//	public ChatMainFrame mainView;
//	public JPanel contentPane;
//	public JLabel ChatRoomIcon, ChatRoomName, LastMsgLbl, LastTimeLbl;
//	
//	public Boolean online = false;
//	public Image tmpImg = null;
//	public Graphics2D tmpGc;
//	
//	
//	public ChatRoom(ChatMainFrame mainview, ImageIcon RoomIcon, String Username, String Room_ID, String UserList) {
//		JPanel contentPane = new JPanel();
//		contentPane.setLayout(null);
//		contentPane.setBorder(null);
//		contentPane.setVisible(true);
//
//		this.mainView = mainview;
//		this.RoomIcon = RoomIcon;
//		this.Username = Username;
//		this.Room_ID = Room_ID;
//		this.UserList = UserList;
//		
//		
//		
//		JLabel ChatRoomIcon = new JLabel("");
//		ChatRoomIcon.setBounds(15, 13, 50, 50);
//		contentPane.add(ChatRoomIcon);
//		
//		
//		JLabel ChatRoomName = new JLabel("<dynamic>");
//		ChatRoomName.setBounds(75, 13, 85, 20);
//		contentPane.add(ChatRoomName);
//		
//		JLabel LastMsgLbl = new JLabel(LastMsg);
//		LastMsgLbl.setBounds(77, 47, 61, 16);
//		contentPane.add(LastMsgLbl);
//		
//		
//		JLabel LastTimeLbl = new JLabel(LastTime);
//		LastTimeLbl.setBounds(200, 13, 50, 20);
//		LastTimeLbl.setHorizontalAlignment(SwingConstants.RIGHT);
//		contentPane.add(LastTimeLbl);
//		
//		roomView = new ChatClientChatRoomView(mainView, Username, Room_ID, UserList);
//		setVisible(true);
//	}
//	
//	public void NewChatClientRoomView() {
//		roomView = new ChatClientChatRoomView(mainView, Username, Room_ID, UserList);
//	}
//	public void paint(Graphics g) {
//		super.paint(g);
//		if(tmpImg == null) {
//			tmpImg = createImage(ChatRoomIcon.getWidth(), ChatRoomIcon.getHeight());
//			tmpGc = (Graphics2D) tmpImg.getGraphics();
//			SetChatRoomIcon();
//		}
//	}
//	/*ä�ù� ������ ���ϱ�*/
//	public void SetChatRoomIcon() {
//		String[] users = UserList.split(" ");
//		Image img = null;
//		
//		if(users.length == 1) { //�Ѹ��� ��� 
//			RoomIcon = mainView.UserIcon;
//			img = RoomIcon.getImage().getScaledInstance(ChatRoomIcon.getWidth(), ChatRoomIcon.getHeight(), Image.SCALE_DEFAULT);
//		} else { //�Ѹ��� �ƴ� ��� 
//			ArrayList<ImageIcon> icons = new ArrayList<ImageIcon>();
//			int k = 0;
//			for(int i=0; i<users.length && k<4; i++) {
//				if(users[i].equals(Username)) continue; //���� ������ ������ ����
//				icons.add(RoomIcon.getUserIcon(users[i]));
//				k++;
//			}
//			
//			if(k == 1) { //2���� ���
//				RoomIcon = icons.get(0);
//				img = RoomIcon.getImage().getScaledInstance(ChatRoomIcon.getWidth(), ChatRoomIcon.getHeight(), Image.SCALE_DEFAULT);
//			} else {
//				//ä�ù� �̹��� ���� ����
//				tmpGc.setColor(Color.WHITE);
//				tmpGc.fillRect(0, 0, ChatRoomIcon.getWidth(), ChatRoomIcon.getHeight());
//				
//				if(k == 2) { //3���� ���
//					Image img0 = icons.get(0).getImage().getScaledInstance(k, k, Image.SCALE_SMOOTH);
//					Image img1 = icons.get(1).getImage().getScaledInstance(k, k, Image.SCALE_SMOOTH);
//					
//					tmpGc.drawImage(img0, 0, 0, ChatRoomIcon);
//					tmpGc.drawImage(img1, ChatRoomIcon.getWidth()*2/5, ChatRoomIcon.getHeight()*2/5, ChatRoomIcon);
//				}
//				if(k == 3) { //4��
//					Image img0 = icons.get(0).getImage().getScaledInstance(ChatRoomIcon.getWidth()/2, ChatRoomIcon.getHeight()/2, Image.SCALE_SMOOTH);
//					Image img1 = icons.get(1).getImage().getScaledInstance(ChatRoomIcon.getWidth()/2, ChatRoomIcon.getHeight()/2, Image.SCALE_SMOOTH);
//					Image img2 = icons.get(2).getImage().getScaledInstance(ChatRoomIcon.getWidth()/2, ChatRoomIcon.getHeight()/2, Image.SCALE_SMOOTH);
//
//					tmpGc.drawImage(img0, 0, 0, ChatRoomIcon);
//					tmpGc.drawImage(img1, 0, ChatRoomIcon.getHeight()/2, ChatRoomIcon);
//					tmpGc.drawImage(img2, ChatRoomIcon.getWidth()/2, ChatRoomIcon.getHeight()*2/5, ChatRoomIcon);
//				}
//				if(k == 4) { //5��
//					Image img0 = icons.get(0).getImage().getScaledInstance(ChatRoomIcon.getWidth()/2, ChatRoomIcon.getHeight()/2, Image.SCALE_SMOOTH);
//					Image img1 = icons.get(1).getImage().getScaledInstance(ChatRoomIcon.getWidth()/2, ChatRoomIcon.getHeight()/2, Image.SCALE_SMOOTH);
//					Image img2 = icons.get(2).getImage().getScaledInstance(ChatRoomIcon.getWidth()/2, ChatRoomIcon.getHeight()/2, Image.SCALE_SMOOTH);
//					Image img3 = icons.get(3).getImage().getScaledInstance(ChatRoomIcon.getWidth()/2, ChatRoomIcon.getHeight()/2, Image.SCALE_SMOOTH);
//					
//					tmpGc.drawImage(img0, 0, 0, ChatRoomIcon);
//					tmpGc.drawImage(img1, ChatRoomIcon.getWidth()/2, 0, ChatRoomIcon);
//					tmpGc.drawImage(img2, 0, ChatRoomIcon.getHeight()/2, ChatRoomIcon);
//					tmpGc.drawImage(img3, ChatRoomIcon.getWidth()/2, ChatRoomIcon.getHeight()/2, ChatRoomIcon);
//				}
//				
//				img = tmpImg;
//			}
//		}
//		ChatRoomIcon.setIcon(new ImageIcon(img));
//		
//	}
//	
//	/**/
//	public void AppendText(ChatMsg cm) {
//		
//	}
//	
//	public void AppendImg(ChatMsg cm) {
//		
//	}
//	
//	public void ChangeFriendProfile(ChatMsg cm) {
//		
//	}
//	
//	public void SetLastMsg(ChatMsg cm) {
//		LastMsg = cm.data;
//		LastMsgLbl.setText(LastMsg);
//		LastTimeLbl.setText(getTime(cm.date));
//	}
//	
//	public String getTime(Date date) {
//		SimpleDateFormat f = new SimpleDateFormat();
//		return f.format(date);
//	}
//}
