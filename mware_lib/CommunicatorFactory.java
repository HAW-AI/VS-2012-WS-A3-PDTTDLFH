package mware_lib;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class CommunicatorFactory extends Thread {
	/*
	 * This class waits for new incoming connections. Once a client has connected
	 * to the new server socket, a new Communicator is created with this socket and
	 * added to the CommunicatorStore.
	 */

	private static final int DEFAULT_PORT = 6667;
	private static CommunicatorFactory communicatorFactory;
	private ServerSocket serverSocket = null;

	public CommunicatorFactory() {
		try {
			this.serverSocket = new ServerSocket(DEFAULT_PORT);
		} catch (IOException e) {
			log("could not connect "+e.getMessage());
			throw new RuntimeException("Could not receive an answer from nameservice: "+e.getMessage());
		}
	}

	/*
	 * Singleton starter. Build a CommunicatorFactory and start if none
	 * exists otherwise do nothing.
	 */
	public static void init() {
		if (communicatorFactory == null) {
			communicatorFactory = new CommunicatorFactory();
			communicatorFactory.setDaemon(true);
			communicatorFactory.start();
		}
	}

	public void run() {
		while(true) {
			try {
				Socket socket = serverSocket.accept();
				Communicator communicator = new Communicator(socket);
				communicator.setDaemon(true);
				CommunicatorStore.putCommunicator(communicator);
				communicator.start();
			} catch (IOException e) {
				log("an error occured while waiting for a new connection to be established");
				throw new RuntimeException("An error occured while waiting for a new connection to be established");
			}
		}
	}
	
	public static int getDefaultPort(){
		return DEFAULT_PORT;
	}

	private void log(String logMessage) {
		Utility.log("CommunicatorFactory", logMessage);
	}
}
