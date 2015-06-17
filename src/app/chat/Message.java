package app.chat;

import java.io.Serializable;

/**
 * Standard message that travels between the clients and server.
 * Content may depend on the message type. Eg: 'login' message type requires 
 * the content to have just the 'userID'.
 * 
 * Assumption is that the clients are aware of the message structure.
 * @author Anil Kandru
 *
 */
public class Message implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6275726609518547961L;

	private String senderId;
	private String receiverId;
	private String content;
	private String type; // TODO: Enum of message types - login, logout, message, ack, error

	// from client
	public static final String MSG_TYPE_LOGIN = "login";
	public static final String MSG_TYPE_LOGOUT = "logout";
	public static final String MSG_TYPE_CHAT = "chat";
	
	// from server
	public static final String MSG_TYPE_ACK = "ack";
	public static final String MSG_TYPE_ERROR = "error";
	
	public String getSenderId() {
		return senderId;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	public String getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Message(String type, String content) {
		this.type = type;
		this.content = content;
	}
	
	public Message() {
		
	}
}
