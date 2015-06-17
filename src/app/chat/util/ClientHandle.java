package app.chat.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import app.chat.Message;

/**
 * Handle to a particular client's input/output streams to read and push
 * the messages.
 * @author 19809694
 *
 */
public class ClientHandle {

	private Socket clientSocket;
	
	private ObjectInputStream socketIn;
	private ObjectOutputStream socketOut;

	public ClientHandle(Socket clientSocket) throws IOException {
		this.clientSocket = clientSocket;
		initialize();
	}
	
	public ClientHandle(ObjectInputStream socketIn, ObjectOutputStream socketOut) {
		this.socketIn = socketIn;
		this.socketOut = socketOut;
	}

	private void initialize() throws IOException {
		
		socketIn = new ObjectInputStream(clientSocket.getInputStream());
		socketOut = new ObjectOutputStream(clientSocket.getOutputStream());
	}
	
	public void writeMessage(Message msg) throws Exception {
		if(socketOut != null) {
			socketOut.writeObject(msg);
			socketOut.flush();
		}
	}
	
	public Message readMessage() throws Exception {
		if(socketIn != null) {
			return (Message)socketIn.readObject();
		}
		return null;
	}
}
