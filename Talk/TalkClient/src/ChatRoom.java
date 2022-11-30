import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import javax.swing.UIManager;

public class ChatRoom extends JPanel{
	private static final long serialVersionUID = 1L;
	public ChatClientMainView mainView;
	String UserName;
	ImageIcon img;
	String roomId;
	String userlist;
	//String UserStatusMsg;
	
	
	public ChatRoom(ChatClientMainView mainView, String username, String roomId, String userlist) {
		//setBorder(null);	
		this.mainView = mainView;
		UserName = username;
		this.roomId = roomId;
		this.userlist = userlist;
		
		setBackground(new Color(0, 252, 255));
		setBounds(0, 0, 200, 60);
		setLayout(null);
		
		JButton roomImg = new JButton();
		roomImg.setBounds(5, 4, 50, 50);
		roomImg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("채팅방 클릭 ");
			}
		});
		add(roomImg);
		
		JLabel roomid = new JLabel(roomId);
		roomid.setBackground(new Color(255, 38, 0));
		roomid.setBounds(60, 7, 95, 20);
		roomid.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		add(roomid);
		
		JLabel chatMsg = new JLabel("새로운 채팅");
		chatMsg.setBounds(60, 33, 85, 15);
		add(chatMsg);
		
		JLabel lblNewLabel = new JLabel();
		add(lblNewLabel);
		
		mainView.textPaneChatList.insertComponent(this);
		
	}
}
