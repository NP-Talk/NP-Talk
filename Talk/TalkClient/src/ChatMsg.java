// ChatMsg.java ä�� �޽��� ObjectStream ��.
import java.io.Serializable;
import java.sql.Date;

import javax.swing.ImageIcon;

class ChatMsg implements Serializable {
	private static final long serialVersionUID = 1L;
	public String id;
	public String code; // 100:�α���, 400:�α׾ƿ�, 200:ä�ø޽���, 300:Image
	public String data; //ä�� �޼��� 
	public String UserStatus; //���� online ���� 
	public String UserStatusMsg; //���� ���¸޼��� 
	public Date date; //���������� ���� ä�� �ð�
	
	public ImageIcon img; //�ְ�޴� ���� 
	public ImageIcon profile; //�����ʻ���
	

	public ChatMsg(String id, String code, String msg) {
		this.id = id;
		this.code = code;
		this.data = msg;
	}

	//�������� code
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

	//�޼���
	public String getData() {
		return data;
	}
	public String getId() {
		return id;
	}

	//���� ID = �̸� 
	public void setId(String id) {
		this.id = id;
	}
	public void setData(String data) {
		this.data = data;
	}

	// ä�� �̹���
	public void setImg(ImageIcon img) {
		this.img = img;
	}
	

	
	// ���� ������ ���� �޼���
	public String getUserStatusMsg() {
		return UserStatusMsg;
	}
	public void setUserStatusMsg(String UserStatusMsg) {
		this.UserStatusMsg = UserStatusMsg;
	}
	
	// ���� ������ �̹���
	public ImageIcon getProfile() {
		return profile;
	}
	public void setProfile(ImageIcon profile) {
		this.profile = profile;
	}
	
	//���� ä�� �޼��� �ð�
	public void setDate(Date date) {
		this.date = date;
	}
	public Date getDate() {
		return date;
	}
	
}