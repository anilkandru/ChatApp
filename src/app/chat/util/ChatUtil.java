package app.chat.util;

import java.io.ObjectInputStream;
import java.net.Socket;

import app.chat.Message;
import app.chat.command.ChatCommand;
import app.chat.command.LoginCommand;
import app.chat.command.MessageCommand;

// Utility class
public class ChatUtil {
	
	/**
	 * Factory method to construct the relevant command instance based on the
	 * given message type.
	 * @param msg
	 * @param clientSocket
	 * @param msgIn
	 * @return
	 */
	// TODO: typically this method should be replaced by spring beans DI
	public static ChatCommand constructChatCommand(Message msg,
			Socket clientSocket, ObjectInputStream msgIn) {
		ChatCommand command = null;
		switch (msg.getType()) {
		case Message.MSG_TYPE_LOGIN:
			command = new LoginCommand(msg, clientSocket, msgIn);
			break;
		case Message.MSG_TYPE_CHAT:
			command = new MessageCommand(msg);
			break;
		default:
			// TODO:
			break;
		}
		return command;
	}
}
