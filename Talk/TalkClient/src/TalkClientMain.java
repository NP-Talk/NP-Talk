// JavaObjClient.java
// ObjecStream 사용하는 채팅 Client

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Font;

public class TalkClientMain extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtUserName;
	private JTextField txtIpAddress;
	private JTextField txtPortNumber;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TalkClientMain frame = new TalkClientMain();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TalkClientMain() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 254, 321);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(254, 251, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("ID");
		lblNewLabel.setBounds(47, 157, 20, 33);
		contentPane.add(lblNewLabel);
		
		txtUserName = new JTextField();
		txtUserName.setHorizontalAlignment(SwingConstants.CENTER);
		txtUserName.setBounds(79, 157, 132, 33);
		contentPane.add(txtUserName);
		txtUserName.setColumns(10);
		
		/*
		JLabel lblIpAddress = new JLabel("IP Address");
		lblIpAddress.setBounds(12, 100, 82, 33);
		contentPane.add(lblIpAddress);
		
		txtIpAddress = new JTextField();
		txtIpAddress.setHorizontalAlignment(SwingConstants.CENTER);
		txtIpAddress.setText("127.0.0.1");
		txtIpAddress.setColumns(10);
		txtIpAddress.setBounds(101, 100, 116, 33);
		contentPane.add(txtIpAddress);
		
		JLabel lblPortNumber = new JLabel("Port Number");
		lblPortNumber.setBounds(12, 163, 82, 33);
		contentPane.add(lblPortNumber);
		
		txtPortNumber = new JTextField();
		txtPortNumber.setText("30000");
		txtPortNumber.setHorizontalAlignment(SwingConstants.CENTER);
		txtPortNumber.setColumns(10);
		txtPortNumber.setBounds(101, 163, 116, 33);
		contentPane.add(txtPortNumber);
		*/
		
		JLabel title = new JLabel("Talk");
		title.setFont(new Font("Arial Black", Font.PLAIN, 32));
		title.setBounds(90, 70, 76, 64);
		contentPane.add(title);
		
		JButton btnConnect = new JButton("LOGIN");
		btnConnect.setBounds(6, 210, 242, 38);
		contentPane.add(btnConnect);
		Myaction action = new Myaction();
		btnConnect.addActionListener(action);
		txtUserName.addActionListener(action);
		//txtIpAddress.addActionListener(action);
		//txtPortNumber.addActionListener(action);
	}
	class Myaction implements ActionListener // 내부클래스로 액션 이벤트 처리 클래스
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			//String username = txtUserName.getText().trim();
			//String ip_addr = txtIpAddress.getText().trim();
			//String port_no = txtPortNumber.getText().trim();
			String username = txtUserName.getText().trim();
			String ip_addr = "127.0.0.1";
			String port_no = "30000";
			//TalkClientList list = new TalkClientList(username, ip_addr, port_no);
			//TalkClientProfile profile = new TalkClientProfile(username, ip_addr, port_no);
			//TalkClientRoom room = new TalkClientRoom(username, ip_addr, port_no);
			ChatMainFrame chatMain = new ChatMainFrame(username, ip_addr, port_no);
			setVisible(false);
		}
	}
}


