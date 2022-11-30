import java.io.Serializable;

public class ChatRoom implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public String roomId;
	public String userList;
	
	public ChatRoom(String roomId, String userList) {
		this.roomId = roomId;
		this.userList = userList;
	}
}
