package app.chat.util;


/**
 * Config object to store all the details about server configuration.
 * Currently all values are hard coded but those should ideally be picked from XML.
 * @author 19809694
 *
 */
public class Configuration {

	int port;
	
	public int getPort() {
		return 10050;
	}
	
	public int getWorkerThreadCount () {
		return 10;
	}
	
}
