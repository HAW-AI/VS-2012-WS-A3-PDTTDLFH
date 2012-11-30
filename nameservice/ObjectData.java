package nameservice;

final public class ObjectData {

	private final String name;
	private final String type;
	private final String host;
	private final int port;
	
	public ObjectData(String name, String type, String host, int port) {
		this.name = name;
		this.type = type;
		this.host = host;
		this.port = port;
	}

	String name() {
		return name;
	}
	
	String type() {
		return type;
	}
	
	String host() {
		return host;
	}
	
	int port() {
		return port;
	}
	
}
