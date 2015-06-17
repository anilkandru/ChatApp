package app.chat.command;

import app.chat.Message;
import app.chat.util.ClientHandle;
import app.chat.util.ServerRuntime;

/**
 * The message processing command. This command figures out the right receiver 
 * and pushes the message from sender.
 * @author 19809694
 *
 */
public class MessageCommand implements ChatCommand {

	private Message msg;
	private ServerRuntime sRuntime = ServerRuntime.getInstance();

	public MessageCommand(Message msg) {
		this.msg = msg;
	}
	
	@Override
	public void execute() {
		String receiverId = msg.getReceiverId();
		ClientHandle receiverHandle = sRuntime.getClientHandle(receiverId);
		if(receiverHandle == null) {
			//TODO: receiver seems to be offline. Post the message back to the sender
			// that the receiver is offline and write down the messages to a queue, 
			// so that they will be reposted after the receiver logs in.
		}
		try {
//			System.out.println("sending message to receiver: "+ receiverId); //TODO: log
			receiverHandle.writeMessage(msg);
//			System.out.println("sent message to receiver: "+ receiverId); //TODO: log
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
