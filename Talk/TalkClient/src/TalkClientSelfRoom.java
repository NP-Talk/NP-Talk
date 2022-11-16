import javax.swing.JFrame;
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
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JMenu;
import javax.swing.JLayeredPane;
import javax.swing.JTextPane;

public class TalkClientSelfRoom extends JFrame{
	private static final long serialVersionUID = 1L;
	
	private String UserName;
	private JButton btnSend;
	private static final int BUF_LEN = 128; // Windows ó�� BUF_LEN �� ����
	
	private JPanel contentPane;
	private JTextField txtUserName;
	private JTextField txtIpAddress;
	private JTextField txtPortNumber;
	private JTextField textInput;
	
	private JButton sendBtn; // ���۹�ư 
	private JButton file; // ������ ���� 
	private JButton emoticon; // �̸�Ƽ�� 
	private JTextPane roomName; // ä�ù� �̸� 
	
	private JTextPane textArea;

	// ��ư �̹���
	private ImageIcon emoImg = new ImageIcon(TalkClientRoom.class.getResource("./img/emoticon.png"));
	private ImageIcon fileImg = new ImageIcon(TalkClientRoom.class.getResource("./img/file.png"));
	private ImageIcon search = new ImageIcon(TalkClientRoom.class.getResource("./img/search.png"));
	
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
	
	public TalkClientSelfRoom(String username, Socket socket, ObjectInputStream ois, ObjectOutputStream oos) {
		this.UserName = username;
		this.socket = socket;
		//this.ois = ois;
		//this.oos = oos;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 394, 630);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setVisible(true);
		
		// ä�� ���� 
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 63, 394, 419);
		contentPane.add(scrollPane);
		
		textArea = new JTextPane();
		textArea.setEditable(true);
		textArea.setFont(new Font("����ü", Font.PLAIN, 14));
		
		scrollPane.setViewportView(textArea);
		
		// ���� ��ư 
		sendBtn = new JButton("����");
		sendBtn.setBounds(313, 568, 75, 29);
		contentPane.add(sendBtn);
		
		// �̸�Ƽ�� 
		emoImg = imageSetSize(emoImg, 28, 28);
		emoticon = new JButton(emoImg);
		emoticon.setBounds(5, 568, 29, 29);
		emoticon.setBorderPainted(false);
		emoticon.setContentAreaFilled(false);
		emoticon.setOpaque(false);
		contentPane.add(emoticon);
		
		// ���� 
		fileImg = imageSetSize(fileImg, 28, 28);
		file = new JButton(fileImg);
		file.setBounds(42, 568, 29, 29);
		file.setBorderPainted(false);
		file.setContentAreaFilled(false);
		file.setOpaque(false);
		contentPane.add(file);
		
		// ä�� ġ�� �� 
		textInput = new JTextField();
		textInput.setBackground(new Color(255, 255, 255));
		textInput.setBounds(0, 484, 394, 82);
		contentPane.add(textInput);
		textInput.setColumns(10);
		
		// ä�ù� ���� 
		JButton roomProfile = new JButton();
		roomProfile.setBounds(6, 8, 51, 51);
		contentPane.add(roomProfile);
		
		// ä�ù� �̸� 
		roomName = new JTextPane();
		roomName.setBounds(69, 10, 207, 24);
		roomName.setText(username);
		roomName.setOpaque(false);
		roomName.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		contentPane.add(roomName);
		
		// ä�� �˻� 
		search = imageSetSize(search, 29, 29);
		JButton searchMsg = new JButton(search);
		searchMsg.setBounds(344, 6, 29, 29);
		searchMsg.setBorderPainted(false);
		searchMsg.setContentAreaFilled(false);
		searchMsg.setOpaque(false);
		contentPane.add(searchMsg);
		
		try {
			oos = new ObjectOutputStream(socket.getOutputStream());
			oos.flush();
			ois = new ObjectInputStream(socket.getInputStream());

			//SendMessage("/login " + UserName);
			
			//UserInfo obui = new UserInfo(UserName, "100", "Hello");
			//SendObject(obui);
			
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
	
	// Server Message�� �����ؼ� ȭ�鿡 ǥ��
		class ListenNetwork extends Thread {
			public void run() {
				while (true) {
					try {
						// String msg = dis.readUTF();
//						byte[] b = new byte[BUF_LEN];
//						int ret;
//						ret = dis.read(b);
//						if (ret < 0) {
//							AppendText("dis.read() < 0 error");
//							try {
//								dos.close();
//								dis.close();
//								socket.close();
//								break;
//							} catch (Exception ee) {
//								break;
//							}// catch�� ��
//						}
//						String	msg = new String(b, "euc-kr");
//						msg = msg.trim(); // �յ� blank NULL, \n ��� ����

						Object obui = null;
						String msg = null;
						UserInfo ui;
						try {
							obui = ois.readObject();
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							break;
						}
						if (obui == null)
							break;
						
						if (obui instanceof UserInfo) {
			                  ui = (UserInfo) obui;
			                  
			                  // ���� ä�� ������ ������
			                  if(ui.getId().equals(UserName)) {
			                     check = true;
			                  }
			                  else {
			                     check = false;
			                  }
							msg = String.format("[%s] %s", ui.getId(), ui.getData());
						} else
							continue;
						switch (ui.getCode()) {
						case "200": // chat message
							AppendText(msg);
							break;
						case "300": // Image ÷��
							AppendText("[" + ui.getId() + "]");
							AppendImage(ui.profile);
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
						} // catch�� ��
					} // �ٱ� catch����

				}
			}
		}
	
	public ImageIcon imageSetSize(ImageIcon icon, int i, int j) { // image Size Setting
		Image ximg = icon.getImage();  //ImageIcon�� Image�� ��ȯ.
		Image yimg = ximg.getScaledInstance(i, j, java.awt.Image.SCALE_SMOOTH);
		ImageIcon xyimg = new ImageIcon(yimg); 
		return xyimg;
	}
	
	// keyboard enter key ġ�� ������ ����
		class TextSendAction implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Send button�� �����ų� �޽��� �Է��ϰ� Enter key ġ��
				if (e.getSource() == btnSend || e.getSource() == textInput) {
					String msg = null;
					msg = String.format("[%s] %s\n", UserName, textInput.getText());
					msg = textInput.getText();
					SendMessage(msg);
					textInput.setText(""); // �޼����� ������ ���� �޼��� ����â�� ����.
					textInput.requestFocus(); // �޼����� ������ Ŀ���� �ٽ� �ؽ�Ʈ �ʵ�� ��ġ��Ų��
					if (msg.contains("/exit")) // ���� ó��
						System.exit(0);
				}
			}
		}

		class ImageSendAction implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
				// �׼� �̺�Ʈ�� sendBtn�϶� �Ǵ� textField ���� Enter key ġ��
				if (e.getSource() == file) {
					frame = new Frame("�̹���÷��");
					fd = new FileDialog(frame, "�̹��� ����", FileDialog.LOAD);
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
		//private JTextArea textArea;

		public void AppendIcon(ImageIcon icon) {
			int len = textArea.getDocument().getLength();
			// ������ �̵�
			textArea.setCaretPosition(len);
			textArea.insertIcon(icon);
		}

		// ȭ�鿡 ���
		public void AppendText(String msg) {
			//textArea.append(msg + "\n");
			//AppendIcon(icon1);
			msg = msg.trim(); // �յ� blank�� \n�� �����Ѵ�.
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
		// ȭ�� ������ ���
		public void AppendTextR(String msg) {
			msg = msg.trim(); // �յ� blank�� \n�� �����Ѵ�.	
			StyledDocument doc = textArea.getStyledDocument();
			SimpleAttributeSet right = new SimpleAttributeSet();
			StyleConstants.setAlignment(right, StyleConstants.ALIGN_RIGHT);
			StyleConstants.setForeground(right, Color.BLUE);	
		    doc.setParagraphAttributes(doc.getLength(), 1, right, false);
			try {
				doc.insertString(doc.getLength(),msg+"\n", right );
			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int len = textArea.getDocument().getLength();
			textArea.setCaretPosition(len);
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
			// Image�� �ʹ� ũ�� �ִ� ���� �Ǵ� ���� 200 �������� ��ҽ�Ų��.
			if (width > 200 || height > 200) {
				if (width > height) { // ���� ����
					ratio = (double) height / width;
					width = 200;
					height = (int) (width * ratio);
				} else { // ���� ����
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
			// new_icon.addActionListener(viewaction); // ����Ŭ������ �׼� �����ʸ� ��ӹ��� Ŭ������
			// panelImage = ori_img.getScaledInstance(panel.getWidth(), panel.getHeight(), Image.SCALE_DEFAULT);

			//gc2.drawImage(ori_img,  0,  0, panel.getWidth(), panel.getHeight(), panel);
			//gc.drawImage(panelImage, 0, 0, panel.getWidth(), panel.getHeight(), panel);
		}

		// Windows ó�� message ������ ������ �κ��� NULL �� ����� ���� �Լ�
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

		// Server���� network���� ����
		public void SendMessage(String msg) {
			try {
				// dos.writeUTF(msg);
//				byte[] bb;
//				bb = MakePacket(msg);
//				dos.write(bb, 0, bb.length);
				UserInfo obui = new UserInfo(UserName, msg);
				oos.writeObject(obui);
			} catch (IOException e) {
				// AppendText("dos.write() error");
				AppendText("oos.writeObject() error");
				try {
//					dos.close();
//					dis.close();
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

		public void SendObject(Object ob) { // ������ �޼����� ������ �޼ҵ�
			try {
				oos.writeObject(ob);
			} catch (IOException e) {
				// textArea.append("�޼��� �۽� ����!!\n");
				AppendText("SendObject Error");
			}
		}
}
