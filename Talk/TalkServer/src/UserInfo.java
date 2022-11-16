import java.io.Serializable;

import javax.swing.ImageIcon;

public class UserInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	private String id;
	private String code; // 100:로그인, 400:로그아웃, 200:채팅메시지, 300:Image
	private String data = null;
	private ImageIcon profile = null;
	private String stateMsg = null;
	
	public String getStateMsg() {
		return stateMsg;
	}

	public void setStateMsg(String stateMsg) {
		this.stateMsg = stateMsg;
	}

	public UserInfo(String id, String code, String msg) {
		this.id = id;
		this.code = code;
		this.data = msg;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public ImageIcon getProfile() {
		return profile;
	}
	public void setProfile(ImageIcon profile) {
		this.profile = profile;
	}
	
}
