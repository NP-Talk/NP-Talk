import java.sql.Date;

import javax.swing.ImageIcon;

public class LoggedUser {
	private static final long serialVersionUID = 1L;
	public String UserName;
	public String code; // 100:로그인, 400:로그아웃, 200:채팅메시지, 300:Image
	public String data; //채팅 메세지 
	public String UserStatus; //유저 online 상태 
	public String UserStatusMsg; //유저 상태메세지 
	public Date date; //마지막으로 보낸 채팅 시간
	
	public ImageIcon img; //주고받는 사진 
	public ImageIcon profile; //프로필사진
	
	LoggedUser(ImageIcon profile, String UserName, String UserStatus, String UserStatusMsg) {
		this.profile = profile;
		this.UserName = UserName;
		this.UserStatus = UserStatus;
		this.UserStatusMsg = UserStatusMsg;
	}
}
