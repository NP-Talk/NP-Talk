import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.time.LocalTime;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.JSeparator;

public class TalkClientProfile extends JFrame{
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private String UserName;
	private String state;
	
	boolean check = false;
	private JTextField userName;
	private JTextField selfChatText;
	private JTextField editProfileText;
	
	private ImageIcon msg = new ImageIcon(TalkClientProfile.class.getResource("./img/msg.png"));
	private ImageIcon edit = new ImageIcon(TalkClientProfile.class.getResource("./img/profileEdit.png"));
	private JTextField stateMsg;

	private ProfileInfo profileInfo = new ProfileInfo(UserName);
	private ImageIcon profile = profileInfo.getProfileImg();
	private ImageIcon back = profileInfo.getBackgroundImg();
	
	private Socket socket; 
	private InputStream is;
	private OutputStream os;
	private DataInputStream dis;
	private DataOutputStream dos;
	
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	//private ConnectTest ct;
	
	private LocalTime now = LocalTime.now();
	private int hour = now.getHour();
    private int minute = now.getMinute();
    private String ampm = "오전";
    String time = ampm + " " + hour + ":" + minute + " ";
    //TalkClientChatRoomView roomView = new TalkClientChatRoomView();
	
	/**
	 * Create the frame.
	 */
	public TalkClientProfile(String username, String ip_addr, String port_no) {
		
		//System.out.println("ip_addr : " + ip_addr + " port_no : " + port_no);
		
		this.UserName = username;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 394, 630);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		msg = imageSetSize(msg, 35, 35);
		JButton selfChatButton = new JButton(msg);
		selfChatButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				//new ChatClientChatRoomView(username, ip_addr, port_no);
				//new TalkClientSelfRoom(username, socket, ois, oos);
			}
		});
		selfChatButton.setBounds(65, 471, 50, 50);
		selfChatButton.setBorderPainted(false);
		selfChatButton.setContentAreaFilled(false);
		selfChatButton.setOpaque(false);
		contentPane.add(selfChatButton);
		
		edit = imageSetSize(edit, 35, 35);
		JButton editProfileButton = new JButton(edit);
		editProfileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new TalkProfileEditor(UserName);
			}
		});
		editProfileButton.setBounds(264, 471, 50, 50);
		editProfileButton.setBorderPainted(false);
		editProfileButton.setContentAreaFilled(false);
		editProfileButton.setOpaque(false);
		contentPane.add(editProfileButton);
		
		profile = imageSetSize(profile, 90, 90);
		JButton profilePhoto = new JButton(profile);
		profilePhoto.setBounds(153, 295, 90, 90);
		profilePhoto.setBorderPainted(false);
		profilePhoto.setContentAreaFilled(false);
		profilePhoto.setOpaque(false);
		contentPane.add(profilePhoto);
		
		userName = new JTextField();
		userName.setHorizontalAlignment(SwingConstants.CENTER);
		userName.setBackground(new Color(254, 255, 255));
		userName.setText(username);
		userName.setBounds(142, 397, 117, 26);
		userName.setOpaque(false);
		userName.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		contentPane.add(userName);
		userName.setColumns(10);
		
		selfChatText = new JTextField();
		selfChatText.setHorizontalAlignment(SwingConstants.CENTER);
		selfChatText.setText("나와의 채팅");
		selfChatText.setBounds(27, 522, 130, 26);
		selfChatText.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		selfChatText.setOpaque(false);
		contentPane.add(selfChatText);
		selfChatText.setColumns(10);
		
		editProfileText = new JTextField();
		editProfileText.setHorizontalAlignment(SwingConstants.CENTER);
		editProfileText.setText("프로필 편집");
		editProfileText.setBounds(228, 522, 130, 26);
		editProfileText.setOpaque(false);
		editProfileText.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		contentPane.add(editProfileText);
		editProfileText.setColumns(10);
		
		JSeparator line = new JSeparator();
		line.setBounds(41, 458, 296, 12);
		contentPane.add(line);
		
		state = profileInfo.getStateMsg();
		stateMsg = new JTextField(state);
		stateMsg.setHorizontalAlignment(SwingConstants.CENTER);
		stateMsg.setBounds(6, 426, 382, 35);
		stateMsg.setOpaque(false);
		stateMsg.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		contentPane.add(stateMsg);
		stateMsg.setColumns(10);
		
		back = imageSetSize(back, 394, 605);
		JButton background = new JButton(back);
		background.setBackground(new Color(121, 121, 121));
		background.setBounds(0, 0, 394, 605);
		contentPane.add(background);
		setVisible(true);
		
		this.UserName = username;
		
		/*
		try {
			socket = new Socket(ip_addr, Integer.parseInt(port_no));
			//socket = new Socket("127.0.0.1", 3000);

			oos = new ObjectOutputStream(socket.getOutputStream());
			oos.flush();
			ois = new ObjectInputStream(socket.getInputStream());

			//SendMessage("/login " + UserName);
			
			//UserInfo userInfo = new UserInfo(UserName, "100", "Login");
			ChatMsg chatMsg = new ChatMsg(UserName, "100", "Login");
			SendObject(chatMsg);
			
			ListenNetwork net = new ListenNetwork();
			net.start();

		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("connect error");
		}
		*/

	}
	
	// Server Message를 수신해서 화면에 표시
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
				       
				       // 내가 채팅 보낼떄 오른쪽
				       if(cm.UserName.equals(UserName)) {
				    	   check = true;
				       }
				       else {
				           check = false;
				       }
				       
				       msg = String.format("[%s] %s", cm.UserName, cm.data);
					} 
					else
						continue;
				}catch (IOException e) {
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
	
	/*
	// keyboard enter key 치면 서버로 전송
	class TextSendAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// Send button을 누르거나 메시지 입력하고 Enter key 치면
			if (e.getSource() == btnSend || e.getSource() == txtInput) {
				String msg = null;
				// msg = String.format("[%s] %s\n", UserName, txtInput.getText());
				msg = textInput.getText();
				SendMessage(msg);
				txtInput.setText(""); // 메세지를 보내고 나면 메세지 쓰는창을 비운다.
				txtInput.requestFocus(); // 메세지를 보내고 커서를 다시 텍스트 필드로 위치시킨다
				if (msg.contains("/exit")) // 종료 처리
					System.exit(0);
			}
		}
	}
	*/
	
	
	// Server에게 network으로 전송
	public void SendMessage(String msg) {
		try {
			ChatMsg chatMsg = new ChatMsg(UserName, "200", msg);
			oos.writeObject(chatMsg);
		} catch (IOException e) {
			// AppendText("dos.write() error");
			System.out.println("oos.writeObject() error");
			try {
//				dos.close();
//				dis.close();
				ois.close();
				oos.close();
				socket.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				System.exit(0);
			}
		}
	}
	
	
	public void SendObject(Object ob) { // 서버로 메세지를 보내는 메소드
		try {
			oos.writeObject(ob);
		} catch (IOException e) {
			// textArea.append("메세지 송신 에러!!\n");
			System.out.println("SendObject Error" + e);
		}
	}
	
	public ImageIcon imageSetSize(ImageIcon icon, int i, int j) { // image Size Setting
		Image ximg = icon.getImage();  //ImageIcon을 Image로 변환.
		Image yimg = ximg.getScaledInstance(i, j, java.awt.Image.SCALE_SMOOTH);
		ImageIcon xyimg = new ImageIcon(yimg); 
		return xyimg;
	}
}
