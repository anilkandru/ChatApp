package app.chat;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import app.chat.command.ChatCommand;
import app.chat.util.ChatUtil;
import app.chat.util.Configuration;

/** 
 * Instant Message server to facilitate communication for a set of authorized clients.
 * 
 * Can be started by executing the class directly without any command line arguments.
 * Starts the server on default port 10050
 * @author Anil Kandru
 *
 */
public class ChatServer {
	
	private boolean isStopSignal = false;

	public static void main(String[] args) {
		System.out.println("This is Instant Message server. Runs on port 10050");
		new ChatServer().startup(new Configuration());
	}
	
	/**
	 * Starts the IM Server according to the given configuration.
	 * For the time being config is a simple object with hardcoded values.
	 * Eventually it should come from an XML.
	 * @param config
	 */
	public void startup(Configuration config) {
		
		// TODO: Include the hooks to do actual initialization of all required components.
		
		
		// Initializing the fixed worker thread pool with a size of 10 for the time being, for a user base of size 100.
		// This should be substitued with a custom threadpool that's expandable upto configured max. size.
		ExecutorService workerPool = Executors.newFixedThreadPool(config.getWorkerThreadCount());
		ServerSocket ss = null;
		try {
			ss = new ServerSocket(config.getPort());
			Socket conversationSocket = null;
			while(!isStopSignal) {
				conversationSocket = ss.accept();
				workerPool.execute(new MessageTask(conversationSocket));
			}
			workerPool.shutdown();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(ss != null) {
				try {
					ss.close();
				} catch (IOException e) {
					// ignore
				}
			}
		}
	}
	
	public void shutdown() {
		isStopSignal = true;
	}

	
	/**
	 * Task of a worker thread. A command object is created according to the message type
	 * and plain delegation takes place.
	 * @author Anil kandru
	 *
	 */
	class MessageTask implements Runnable {
		
		Socket socket;
		boolean isStopped = false;
		public MessageTask(Socket socket) {
			this.socket = socket;
		}

		@Override
		public void run() {
			ObjectInputStream messageIn;
			try {
				messageIn = new ObjectInputStream(socket.getInputStream());
				while (!isStopped) {
					Message message = (Message) messageIn.readObject();
					ChatCommand command = ChatUtil.constructChatCommand(message,
							socket, messageIn);
					if (command != null) {
						command.execute();
					} else {
						// TODO: Message type is invalid from the client or an
						// internal error.
					}
					// System.out.println("Message from : "+message.getSenderId() +
					// " and the content is: " +message.getContent()); //TODO: log
				}
			} catch (IOException ioe) {
				ioe.printStackTrace();
			} catch (Exception e) {
				//TODO: Handle the exception and log the error
				e.printStackTrace();
			} finally {
				try {
					socket.close();
				} catch (IOException e) {
					// log
				}
			}
		}
	}
}