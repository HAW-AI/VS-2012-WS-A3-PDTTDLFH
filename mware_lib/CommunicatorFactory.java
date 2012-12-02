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
			e.printStackTrace();
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
				System.out.println("opening bank socket: "+serverSocket.getLocalPort());
				Socket socket = serverSocket.accept(); // blocks until new connection established.
				System.out.println("received incomming request");
				Communicator communicator = new Communicator(socket);
				communicator.setDaemon(true);
				CommunicatorStore.putCommunicator(communicator);
				communicator.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static int getDefaultPort(){
		return DEFAULT_PORT;
	}
}
