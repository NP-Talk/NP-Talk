// ChatMsg.java 채팅 메시지 ObjectStream 용.
import java.io.Serializable;
import java.time.LocalTime;
import java.util.Date;

import javax.swing.ImageIcon;

class ChatMsg implements Serializable {
	private static final long serialVersionUID = 1L;
	public String UserName;
	public String code; // 100:로그인, 400:로그아웃, 200:채팅메시지, 300:Image
	public String data;
	public ImageIcon img;
	public String time;
	public Date date;
	public String roomId = "1234";
	public String userlist = "1234";
	public String UserStatusMsg;
	
	public ChatMsg(String UserName, String code, String msg) {
		this.UserName = UserName;
		this.code = code;
		this.data = msg;
	}
}