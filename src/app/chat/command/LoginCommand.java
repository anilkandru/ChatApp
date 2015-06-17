package app.chat.command;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import app.chat.Message;
import app.chat.util.ClientHandle;
import app.chat.util.ServerRuntime;

/**
 * Login
 * @author Anil Kandru.
 *
 */
public class LoginCommand implements ChatCommand {

	private Message msg = null;
	private ServerRuntime sRuntime = ServerRuntime.getInstance();;
	private Socket clientSocket = null;

	private ObjectInputStream socketIn = null;
	
	public LoginCommand(Message msg, Socket clientSocket, ObjectInputStream socketIn) {
		this.msg = msg;
		this.clientSocket = clientSocket;
		this.socketIn = socketIn;
	}

	@Override
	public void execute() {
		// TODO: ideally should login by referring to the LDAP server.
		// But right now referring to a fixed set of user IDs for the time being.
		
		// we expect the message contain just the user ID, since the message is
		// of type login.

		String clientID = msg.getContent();

		Message response = new Message();
		String responseLine, msgType;
		try {
			ObjectOutputStream socketOut = new ObjectOutputStream(clientSocket.getOutputStream());
			ClientHandle clientHandle = new ClientHandle(socketIn, socketOut);
			if (!sRuntime.getUsers().contains(clientID)) {
				responseLine = "Invalid user";
				msgType = Message.MSG_TYPE_ERROR;
			} else {
				System.out.println("found the user"+clientID);
				sRuntime.addClientHandle(clientID, clientHandle);
				responseLine = "User " + clientID + " logged in";
				msgType = Message.MSG_TYPE_ERROR;
			}
			response.setContent(responseLine);
			response.setType(msgType);
			clientHandle.writeMessage(response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
