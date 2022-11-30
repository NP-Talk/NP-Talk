import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.Date;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JMenu;
import javax.swing.JLayeredPane;
import javax.swing.JTextPane;

public class ChatClientChatRoomView extends JFrame{
	private static final long serialVersionUID = 1L;
	
	
	private JButton btnSend;
	private static final int BUF_LEN = 128; // Windows 처럼 BUF_LEN 을 정의
	
	private JPanel contentPane;
	private JTextField textInput;
	
	private JButton sendBtn; // 전송버튼 
	private JButton file; // 전송할 사진 
	private JButton emoticon; // 이모티콘 
	private JTextPane roomName; // 채팅방 이름
	
	String UserName;
	String roomId;
	String UserList;
	
	
	//private TalkClientMainView mainView;
	
	private JTextPane textArea;
	
	//private ArrayList<FriendLabel> Friends = new ArrayList<FriendLabel>();
	private Map<String, FriendLabel> Friends = new HashMap<String, FriendLabel>();
	
	

	// 버튼 이미지
	private ImageIcon emoImg = new ImageIcon(ChatClientChatRoomView.class.getResource("./img/emoticon.png"));
	private ImageIcon fileImg = new ImageIcon(ChatClientChatRoomView.class.getResource("./img/file.png"));
	private ImageIcon search = new ImageIcon(ChatClientChatRoomView.class.getResource("./img/search.png"));
	
	private Socket socket; 
	private InputStream is;
	private OutputStream os;
	private DataInputStream dis;
	private DataOutputStream dos;
	
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	
	private Frame frame;
	private FileDialog fd;
	
	boolean check = false;
	
	public ChatClientChatRoomView view;
	public ChatClientMainView mainView;
	
	public ChatClientChatRoomView(ChatClientMainView mainView, String username, String roomId, String userlist, String ip_addr, String port_no) {
		
		this.mainView = mainView;
		UserName = username;
		this.roomId = roomId;
		UserList = userlist;
		
		//CreateFriendIconHash();
		
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 394, 630);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setVisible(true);
		
		// 채팅 내용 
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 63, 394, 419);
		contentPane.add(scrollPane);
		
		textArea = new JTextPane();
		textArea.setBackground(new Color(147, 208, 250));
		textArea.setEditable(true);
		textArea.setFont(new Font("굴림체", Font.PLAIN, 14));
		
		scrollPane.setViewportView(textArea);
		
		// 전송 버튼 
		sendBtn = new JButton("전송");
		sendBtn.setBounds(313, 568, 75, 29);
		contentPane.add(sendBtn);
		
		// 이모티콘 
		emoImg = imageSetSize(emoImg, 28, 28);
		emoticon = new JButton(emoImg);
		emoticon.setBounds(5, 568, 29, 29);
		emoticon.setBorderPainted(false);
		emoticon.setContentAreaFilled(false);
		emoticon.setOpaque(false);
		contentPane.add(emoticon);
		
		// 파일 
		fileImg = imageSetSize(fileImg, 28, 28);
		file = new JButton(fileImg);
		file.setBounds(42, 568, 29, 29);
		file.setBorderPainted(false);
		file.setContentAreaFilled(false);
		file.setOpaque(false);
		contentPane.add(file);
		
		// 채팅 치는 곳 
		textInput = new JTextField();
		textInput.setBackground(new Color(255, 255, 255));
		textInput.setBounds(0, 484, 394, 82);
		contentPane.add(textInput);
		textInput.setColumns(10);
		
		// 채팅방 사진 
		JButton roomProfile = new JButton();
		roomProfile.setBounds(6, 8, 51, 51);
		contentPane.add(roomProfile);
		
		// 채팅방 이름 
		roomName = new JTextPane();
		roomName.setBounds(69, 10, 124, 24);
		roomName.setText(username);
		roomName.setOpaque(false);
		roomName.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		contentPane.add(roomName);
		
		// 채팅 검색 
		search = imageSetSize(search, 29, 29);
		JButton searchMsg = new JButton(search);
		searchMsg.setBounds(292, 8, 29, 29);
		searchMsg.setBorderPainted(false);
		searchMsg.setContentAreaFilled(false);
		searchMsg.setOpaque(false);
		contentPane.add(searchMsg);
		
		exit = new JButton("나가기");
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ChatMsg msg = new ChatMsg(UserName, "400", "Bye");
				SendObject(msg);
				setVisible(false);
			}
		});
		exit.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		exit.setBounds(318, 8, 70, 30);
		contentPane.add(exit);
		
		
		try {
			socket = new Socket(ip_addr, Integer.parseInt(port_no));
			oos = new ObjectOutputStream(socket.getOutputStream());
			oos.flush();
			ois = new ObjectInputStream(socket.getInputStream());
			
			ChatMsg obcm = new ChatMsg(UserName, "100", "Hello");
			SendObject(obcm);
			
			ListenNetwork net = new ListenNetwork();
			net.start();
			TextSendAction action = new TextSendAction();
			sendBtn.addActionListener(action);
			textInput.addActionListener(action);
			textInput.requestFocus();
			ImageSendAction action2 = new ImageSendAction();
			file.addActionListener(action2);

		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			AppendText("connect error");
		}
		
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
		                  //Friends.put(cm.id, new FriendLabel(cm.img, cm.id));
		                  // 내가 채팅 보낼떄 오른쪽
		                  if(cm.UserName.equals(UserName)) {
		                     check = true;
		                  }
		                  else {
		                     check = false;
		                  }
		                  //msg = String.format("[%s] %s", cm.getId(), cm.getData());
		                  msg = String.format("%s", cm.data);
					} else
						continue;
					switch (cm.code) {
					case "200": // chat message
						if(check==true)
							AppendTextR(msg);
						else
							//AppendText(msg);
							AppendTextL(cm);
						break;
					case "300": // Image 첨부
						AppendText("[" + cm.data + "]");
						AppendImage(cm.img);
						break;
					}
				} catch (IOException e) {
					AppendText("ois.readObject() error");
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
	
	public ImageIcon imageSetSize(ImageIcon icon, int i, int j) { // image Size Setting
		Image ximg = icon.getImage();  //ImageIcon을 Image로 변환.
		Image yimg = ximg.getScaledInstance(i, j, java.awt.Image.SCALE_SMOOTH);
		ImageIcon xyimg = new ImageIcon(yimg); 
		return xyimg;
	}
	
	// keyboard enter key 치면 서버로 전송
	class TextSendAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// Send button을 누르거나 메시지 입력하고 Enter key 치면
			if (e.getSource() == btnSend || e.getSource() == textInput) {
				String msg = null;
				msg = String.format("[%s] %s\n", UserName, textInput.getText());
				msg = textInput.getText();
				SendMessage(msg);
				textInput.setText(""); // 메세지를 보내고 나면 메세지 쓰는창을 비운다.
				textInput.requestFocus(); // 메세지를 보내고 커서를 다시 텍스트 필드로 위치시킨다
				if (msg.contains("/exit")) // 종료 처리
					System.exit(0);
			}
		}
	}

	class ImageSendAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// 액션 이벤트가 sendBtn일때 또는 textField 에세 Enter key 치면
			if (e.getSource() == file) {
				frame = new Frame("이미지첨부");
				fd = new FileDialog(frame, "이미지 선택", FileDialog.LOAD);
				// frame.setVisible(true);
				// fd.setDirectory(".\\");
				fd.setVisible(true);
				// System.out.println(fd.getDirectory() + fd.getFile());
				if (fd.getDirectory().length() > 0 && fd.getFile().length() > 0) {
					ChatMsg obcm = new ChatMsg(UserName, "300", "IMG");
					ImageIcon img = new ImageIcon(fd.getDirectory() + fd.getFile());
					obcm.img = img;
					SendObject(obcm);
				}
			}
		}
	}

	ImageIcon icon1 = new ImageIcon("src/icon1.jpg");
	private JButton exit;
	//private JTextArea textArea;

	public void AppendIcon(ImageIcon icon) {
		int len = textArea.getDocument().getLength();
		// 끝으로 이동
		textArea.setCaretPosition(len);
		textArea.insertIcon(icon);
	}
	
	/*
	public void ChangeFirendProfile(ChatMsg cm) {
		FriendLabel f = FriendLabelHash.get(cm.UserName);
		if(f==null)
			return;
		f.SetIcon(cm.img);
		for(FriendLabel fl : FriendLabelList) {
			if(fl.UserName.equals(cm.UserName))
				fl.SetIcon(cm.img);
		}
	}
	*/
	
	public ArrayList<FriendLabel> FriendLabelList = new ArrayList<FriendLabel>();
	
	/*
	public void CreateFriendIconHash() {
		String[] users = UserList.split(" ");
		for(int i = 0; i < users.length; i++) {
			ImageIcon icon = mainView.getUserIcon(users[i]);
			if(icon == null)
				continue;
			FriendLabel f = new FriendLabel(icon, users[i]);
			FriendLabelHash.put(users[i], f);
		}
	}
	
	
	public void getUserIcon(String user) {
		for(int i = 0; i < )
	}
	*/
	// 화면에 출력
	public void AppendTextL(ChatMsg cm) {
		FriendLabel f = Friends.get(cm.UserName);
		FriendLabel f2 = new FriendLabel(f.UserImg, f.UserName);
		FriendLabelList.add(f2);
		
		textArea.setCaretPosition(textArea.getDocument().getLength());
		textArea.insertComponent(f);
		AppendTextL("\n");
		
		int len = textArea.getDocument().getLength();
		textArea.setCaretPosition(len);
		
		JLabel m = new JLabel(" " + cm.data + " ");
		m.setBorder(null);
		m.setOpaque(true);
		m.setBackground(Color.WHITE);
		textArea.insertComponent(m);
		len = textArea.getDocument().getLength();
		textArea.setCaretPosition(len);
		AppendTextL("\n");
	}
	
	
	public synchronized void AppendTextL(String msg) {
		StyledDocument doc = textArea.getStyledDocument();
		SimpleAttributeSet left = new SimpleAttributeSet();
		StyleConstants.setAlignment(left, StyleConstants.ALIGN_LEFT);
		StyleConstants.setForeground(left, Color.BLACK);
		if(msg!=null && !msg.equals("") && !msg.equals("\n"))
			StyleConstants.setForeground(left, Color.WHITE);
		doc.setParagraphAttributes(doc.getLength(), 1, left, true);
		
		try {
			if(msg.equals("\n"))
				doc.insertString(doc.getLength(), msg, left);
			else
				doc.insertString(doc.getLength(), " " + msg + " ", left);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		textArea.setCaretPosition(textArea.getDocument().getLength());
	}
	
	

	// 화면에 출력
	public void AppendText(String msg) {
		//textArea.append(msg + "\n");
		//AppendIcon(icon1);
		msg = msg.trim(); // 앞뒤 blank와 \n을 제거한다.
		int len = textArea.getDocument().getLength();
		textArea.setCaretPosition(len);
		textArea.replaceSelection(msg + "\n");
		
		StyledDocument doc = textArea.getStyledDocument();
		SimpleAttributeSet left = new SimpleAttributeSet();
		StyleConstants.setAlignment(left, StyleConstants.ALIGN_LEFT);
		StyleConstants.setForeground(left, Color.BLACK);
	    doc.setParagraphAttributes(doc.getLength(), 1, left, false);
		try {
			doc.insertString(doc.getLength(), msg+"\n", left );
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}
	// 화면 우측에 출력
	public void AppendTextR(String msg) {
		msg = msg.trim(); // 앞뒤 blank와 \n을 제거한다.	
		
		StyledDocument doc = textArea.getStyledDocument();
		SimpleAttributeSet right = new SimpleAttributeSet();
		StyleConstants.setAlignment(right, StyleConstants.ALIGN_RIGHT);
		StyleConstants.setForeground(right, Color.BLACK);
		StyleConstants.setBackground(right, Color.YELLOW);
		//Style
	    doc.setParagraphAttributes(doc.getLength(), 1, right, false);
	    
	    /*
		try {
			doc.insertString(doc.getLength(),msg+"\n", right );
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		//AppendText("\n");
		int len = textArea.getDocument().getLength();
		textArea.setCaretPosition(len);
		
		JLabel m = new JLabel(msg + " ");
		m.setOpaque(true);
		m.setBackground(Color.YELLOW);
		textArea.insertComponent(m);
		
		JLabel t = new JLabel(getTime());
		t.setLocation(m.getX() - (m.getX()), m.getY());
		textArea.insertComponent(t);

		len = textArea.getDocument().getLength();
		textArea.setCaretPosition(len);
		AppendText("\n");
		//textArea.replaceSelection("\n");
	}
	
	public void AppendImage(ImageIcon ori_icon) {
		int len = textArea.getDocument().getLength();
		textArea.setCaretPosition(len); // place caret at the end (with no selection)
		Image ori_img = ori_icon.getImage();
		Image new_img;
		ImageIcon new_icon;
		int width, height;
		double ratio;
		width = ori_icon.getIconWidth();
		height = ori_icon.getIconHeight();
		// Image가 너무 크면 최대 가로 또는 세로 200 기준으로 축소시킨다.
		if (width > 200 || height > 200) {
			if (width > height) { // 가로 사진
				ratio = (double) height / width;
				width = 200;
				height = (int) (width * ratio);
			} else { // 세로 사진
				ratio = (double) width / height;
				height = 200;
				width = (int) (height * ratio);
			}
			new_img = ori_img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
			new_icon = new ImageIcon(new_img);
			textArea.insertIcon(new_icon);
		} else {
			textArea.insertIcon(ori_icon);
			new_img = ori_img;
		}
		len = textArea.getDocument().getLength();
		textArea.setCaretPosition(len);
		textArea.replaceSelection("\n");
		// ImageViewAction viewaction = new ImageViewAction();
		// new_icon.addActionListener(viewaction); // 내부클래스로 액션 리스너를 상속받은 클래스로
		// panelImage = ori_img.getScaledInstance(panel.getWidth(), panel.getHeight(), Image.SCALE_DEFAULT);

		//gc2.drawImage(ori_img,  0,  0, panel.getWidth(), panel.getHeight(), panel);
		//gc.drawImage(panelImage, 0, 0, panel.getWidth(), panel.getHeight(), panel);
	}
	
	public String getTime() {
		LocalTime now = LocalTime.now();
		int hour = now.getHour();
	    int minute = now.getMinute();
	    String ampm = "오전";
	    if(hour > 12) {
			ampm = "오후";
		}
	    
	    String time = ampm + " " + hour + ":" + minute + " ";
		return time;
	}

	// Windows 처럼 message 제외한 나머지 부분은 NULL 로 만들기 위한 함수
	public byte[] MakePacket(String msg) {
		byte[] packet = new byte[BUF_LEN];
		byte[] bb = null;
		int i;
		for (i = 0; i < BUF_LEN; i++)
			packet[i] = 0;
		try {
			bb = msg.getBytes("euc-kr");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
		}
		for (i = 0; i < bb.length; i++)
			packet[i] = bb[i];
		return packet;
	}

	// Server에게 network으로 전송
	public void SendMessage(String msg) {
		ChatMsg cm = new ChatMsg(UserName, "200", msg);
		cm.date = new Date();
		cm.roomId = roomId; // 고유한 방 번호를 사용해야함 
		mainView.SendObject(cm); // mainView : 소켓을 최초로 생성한 클래스 
	}

	public void SendObject(Object ob) { // 서버로 메세지를 보내는 메소드
		try {
			oos.writeObject(ob);
		} catch (IOException e) {
			// textArea.append("메세지 송신 에러!!\n");
			AppendText("SendObject Error");
		}
	}
}
