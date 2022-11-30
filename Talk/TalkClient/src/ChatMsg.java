// ChatMsg.java 채팅 메시지 ObjectStream 용.
import java.io.Serializable;
import java.sql.Date;

import javax.swing.ImageIcon;

class ChatMsg implements Serializable {
	private static final long serialVersionUID = 1L;
	public String id;
	public String code; // 100:로그인, 400:로그아웃, 200:채팅메시지, 300:Image
	public String data; //채팅 메세지 
	public String UserStatus; //유저 online 상태 
	public String UserStatusMsg; //유저 상태메세지 
	public Date date; //마지막으로 보낸 채팅 시간
	
	public ImageIcon img; //주고받는 사진 
	public ImageIcon profile; //프로필사진
	

	public ChatMsg(String id, String code, String msg) {
		this.id = id;
		this.code = code;
		this.data = msg;
	}

	//프로토콜 code
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

	//메세지
	public String getData() {
		return data;
	}
	public String getId() {
		return id;
	}

	//유저 ID = 이름 
	public void setId(String id) {
		this.id = id;
	}
	public void setData(String data) {
		this.data = data;
	}

	// 채팅 이미지
	public void setImg(ImageIcon img) {
		this.img = img;
	}
	

	
	// 유저 프로필 상태 메세지
	public String getUserStatusMsg() {
		return UserStatusMsg;
	}
	public void setUserStatusMsg(String UserStatusMsg) {
		this.UserStatusMsg = UserStatusMsg;
	}
	
	// 유저 프로필 이미지
	public ImageIcon getProfile() {
		return profile;
	}
	public void setProfile(ImageIcon profile) {
		this.profile = profile;
	}
	
	//유저 채팅 메세지 시간
	public void setDate(Date date) {
		this.date = date;
	}
	public Date getDate() {
		return date;
	}
	
}