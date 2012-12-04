package mware_lib;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ObjectBroker {
	
	private final String serviceHost;
	private final int listenPort;

	private ObjectBroker(String serviceHost, int listenPort) {
		this.serviceHost = serviceHost;
		this.listenPort = listenPort;
	}

	public static ObjectBroker getBroker(String serviceHost, int listenPort) {
		return new ObjectBroker(serviceHost, listenPort);
	}

	public NameService getNameService() {
		Socket socket = null;
		try {
			socket = new Socket(serviceHost, listenPort);
		} catch (UnknownHostException e) {
			System.out.println("IP address of the nameservice could not be determined");
			throw new RuntimeException("IP address of the nameservice could not be determined");
		} catch (IOException e) {
			System.out.println("Could not connect to nameservice");
			throw new RuntimeException("Could not connect to nameservice");
		}
		return new NameServiceProxy(socket);
	}
}
