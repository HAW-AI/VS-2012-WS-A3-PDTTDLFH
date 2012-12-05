package mware_lib;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

public class CommunicatorStore {
	private static Map<InetSocketAddress, Communicator> communicators = new HashMap<InetSocketAddress, Communicator>();

	/*
	 * builds a new or retrieves an existing communicator for a gives SocketAddress
	 */
	public static Communicator getCommunicator(InetSocketAddress address) {
		if (communicators.containsKey(address)) {
			return communicators.get(address);
		} else {
			Communicator communicator = new Communicator(address);
			communicator.setDaemon(true);
			communicator.start();
			putCommunicator(communicator);
			return communicator;
		}
	}
	
	public static void putCommunicator(Communicator communicator) {
		communicators.put(communicator.inetSocketAddress(), communicator);
	}

	private void log(String logMessage) {
		Utility.log("CommunicatorStore", logMessage);
	}
}
