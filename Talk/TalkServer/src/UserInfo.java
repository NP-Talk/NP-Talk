import java.io.Serializable;

import javax.swing.ImageIcon;

public class UserInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	private String UserName;
	private String code; // 100:로그인, 400:로그아웃, 200:채팅메시지, 300:Image
	private ImageIcon profile = new ImageIcon(UserInfo.class.getResource("./img/StandardProfile.png"));;
	private String stateMsg = "상태 메세지";

	public UserInfo(String UserName, String code) {
		this.UserName = UserName;
		this.code = code;
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		this.UserName = userName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public ImageIcon getProfile() {
		return profile;
	}

	public void setProfile(ImageIcon profile) {
		this.profile = profile;
	}

	public String getStateMsg() {
		return stateMsg;
	}

	public void setStateMsg(String stateMsg) {
		this.stateMsg = stateMsg;
	}
}
