package app.chat.util;

import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Server Runtime object to hold the state of the server like the set of 
 * active connections etc..
 * @author 19809694
 *
 */
public class ServerRuntime {

	private ConcurrentHashMap<String, ClientHandle> clientHandles = new ConcurrentHashMap<String, ClientHandle>();
	
	private static ServerRuntime serverRuntime = new ServerRuntime();
	
	private volatile TreeSet<String> users = null;
	
	private ServerRuntime() {
		
	}

	public static ServerRuntime getInstance() {
		return serverRuntime;
	}
	
	// TODO: ideally we should refer to the user info from a directory
	// server. But for the time being we maintain static list of users.
	public Set<String> getUsers() {
		if(users == null) {
			synchronized (ServerRuntime.class) {
				if(users == null) {
					users = new TreeSet<String>();
					for (int i = 1; i <= 100; i++) {
						users.add("" + i);
					}
				}
			}
		}
		return users;
	}

	public ClientHandle getClientHandle(String clientID) {
		return clientHandles.get(clientID);
	}

	public void addClientHandle(String clientID, ClientHandle clientHandle ) {
		clientHandles.put(clientID, clientHandle);
	}
	
}
