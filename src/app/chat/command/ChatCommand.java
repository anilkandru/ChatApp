package app.chat.command;

/**
 * Command that encapsulates the message, corresponding processing logic and
 * the set of artifacts needed.
 * 
 * One to one mapping between the message type and the command class. To support
 * additional message types, command classes should be added.
 * 
 * Command can be of any type like 'login', 'logout', 'lookup', 'addContact' etc..
 * @author Anil Kandru
 *
 */
public interface ChatCommand {

	public void execute();
	
}
