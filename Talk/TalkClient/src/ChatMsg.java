// ChatMsg.java 채팅 메시지 ObjectStream 용.
import java.io.Serializable;
import javax.swing.ImageIcon;

class ChatMsg implements Serializable {
	private static final long serialVersionUID = 1L;
	public String id;
	public String code; // 100:로그인, 400:로그아웃, 200:채팅메시지, 300:Image
	public String data;
	public ImageIcon img;
	
	//private String username;
	private String stateMsg; //상태메세지
	private ImageIcon profile; //프로필사진

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

	//유저 ID
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
	
	/*
	// 유저 이름
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	*/
	
	// 유저 프로필 상태 메세지
	public String getStateMsg() {
		return stateMsg;
	}
	public void setStateMsg(String stateMsg) {
		this.stateMsg = stateMsg;
	}
	
	// 유저 프로필 이미지
	public ImageIcon getProfile() {
		return profile;
	}
	public void setProfile(ImageIcon profile) {
		this.profile = profile;
	}
}