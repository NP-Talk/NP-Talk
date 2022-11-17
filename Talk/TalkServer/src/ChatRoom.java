import java.io.Serializable;

public class ChatRoom implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String userList;
	private String code;
	
	public ChatRoom(String userList, String code) {
		this.userList = userList;
		this.code = code;
		
		
	}
}
