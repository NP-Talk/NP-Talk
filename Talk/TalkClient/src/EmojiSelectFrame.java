import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class EmojiSelectFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private static final int BUF_LEN = 128; // Windows 처럼 BUF_LEN 을 정의

	ChatClientChatRoomView chatroom;
	public JPanel contentPane;

	public JButton[] Buttons = new JButton[20];	
	public ImageIcon[] ImageIcons = {
			new ImageIcon("src/emoji/곤란.png"),
			new ImageIcon("src/emoji/궁금.png"),
			new ImageIcon("src/emoji/깜찍.png"),
			new ImageIcon("src/emoji/놀람.png"),
			new ImageIcon("src/emoji/눈물.png"),
			new ImageIcon("src/emoji/당황.png"),
			new ImageIcon("src/emoji/메롱.png"),
			new ImageIcon("src/emoji/미소.png"),
			new ImageIcon("src/emoji/민망.png"),
			new ImageIcon("src/emoji/반함.png"),
			new ImageIcon("src/emoji/버럭.png"),
			new ImageIcon("src/emoji/부끄.png"),
			new ImageIcon("src/emoji/아픔.png"),
			new ImageIcon("src/emoji/안도.png"),
			new ImageIcon("src/emoji/우웩.png"),
			new ImageIcon("src/emoji/윙크.png"),
			new ImageIcon("src/emoji/으으.png"),
			new ImageIcon("src/emoji/잘난척.png"),
			new ImageIcon("src/emoji/잘자.png"),
			new ImageIcon("src/emoji/잠.png"),
	};
	
	EmojiSelectFrame(ChatClientChatRoomView chatroom) {
		this.chatroom = chatroom;
		setBounds(100, 300, 360, 321);
		
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setVisible(true);
			
		int x=0, y=0;

		//버튼 크기 55x55으로 맞추기
		for(int i=0; i<20; i++) {
			ImageIcon icon = chatroom.imageSetSize(ImageIcons[i], 55, 55); //new ImageIcon(new_img);
			
			if(x >= 350) {
				x = 0;
				y += 60;
			}
			Buttons[i] = new JButton(icon);
			Buttons[i].setBounds(x,y,60,60);
			Buttons[i].addActionListener(new EmojiSendAction());
			Buttons[i].setBorderPainted(false);
			Buttons[i].setContentAreaFilled(false);
			Buttons[i].setOpaque(false);

			x+=60;			
			contentPane.add(Buttons[i]);
		}	
	}
	
	
	// 이미지 아이콘 보내기
	class EmojiSendAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// 액션 이벤트가 sendBtn일때 또는 textField 에세 Enter key 치면
			
			for(int i=0; i<Buttons.length; i++) {
				if(e.getSource() == Buttons[i]) {
					ChatMsg obcm = new ChatMsg(chatroom.UserName, "300", "IMG");
					ImageIcon img = ImageIcons[i];
					obcm.img = img;
					chatroom.SendObject(obcm);
					//setVisible(false);
				}
			}
		}
	}

}
