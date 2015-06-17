package app.chat.client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import app.chat.Message;

/**
 * IM Client program to demonstrate the one to one communication 
 * using the IM Chat server.
 * 
 * Usage: ChatClient senderID receiverID
 * @author Anil Kandru
 *
 */
public class ChatClient {

	public static void main(String[] args) {
		
		if (args.length != 2) {
            System.err.println("Usage: java ChatClient <port number>");
            System.exit(1);
        }
		
		System.out.println("This is IM client: "+args[0]);
		new ChatClient().start(args[0], args[1]);
	}
	
	ObjectOutputStream messageOut = null;
	ObjectInputStream messageIn = null; 
	
	Socket socket = null;
	boolean isStopped = false;
	
	/**
	 * sends a login message first and takes reads from standard input thereafter.
	 * @param clientID
	 * @param receiverID
	 */
	public void start(String clientID, String receiverID) {
		Scanner dataIn = new Scanner(System.in);
		final PrintWriter dataOut = new PrintWriter(System.out);
		try {
			socket = new Socket("localhost", 10050);
			messageOut = new ObjectOutputStream(socket.getOutputStream());
			
			
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
					messageIn = new ObjectInputStream(socket.getInputStream());
					while(!isStopped) {
							Message message = (Message)messageIn.readObject();
							System.out.println("Message arrived: "+message.getContent());
							if(message != null) {
								dataOut.write(message.getContent());
							}
						} 
					}catch(Exception e) {
							// TODO: log the error and exit client.
						}
				}
			}).start();
			
			// first login to the server
			
			Message loginMsg = new Message();
			loginMsg.setType(Message.MSG_TYPE_LOGIN);
			loginMsg.setContent(clientID);
			messageOut.writeObject(loginMsg);
			// send messages forever
			while (true) {
				String line = dataIn.nextLine();
				Message msg = new Message();
				// TODO: Do it cleanly with a builder
				msg.setContent(line);
				msg.setSenderId(clientID);
				msg.setReceiverId(receiverID);
				msg.setType(Message.MSG_TYPE_CHAT);
				messageOut.writeObject(msg);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (dataIn != null) {
					dataIn.close();
				}
				if (messageOut != null) {
					messageOut.close();
				}
				if (socket != null) {
					socket.close();
				}
			} catch (Exception e) {
				// ignore
			}
		}
	}
	
	public void stop() {
		isStopped = true;
	}
}