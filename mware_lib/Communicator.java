package mware_lib;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

import mware_lib.messages.Message;

public final class Communicator extends Thread {
	private final Socket socket;
	private final BufferedReader input;
	private final PrintWriter output;
	
	// clientside communicator creation happens with the InetSocketAddress
	// of an existing Socket.
	public Communicator(InetSocketAddress socketAddress) {
		Socket tmpSocket = null;
		BufferedReader tmpInput = null;
		PrintWriter tmpOutput = null;

		try {
			tmpSocket = new Socket(socketAddress.getAddress(), socketAddress.getPort());
			tmpInput = new BufferedReader(new InputStreamReader(tmpSocket.getInputStream()));
			tmpOutput = new PrintWriter(tmpSocket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		socket = tmpSocket;
		input = tmpInput;
		output = tmpOutput;
	}

	public void run() {
		String inputLine = null;
		try {
			// while the socket is open and new messages arrive we fectch them
			// and pass them of to the MessageHandler
			while (!socket.isClosed() && ((inputLine = input.readLine()) != null)) {
				MessageHandler.handle(inputLine, this);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void send(Message message) {
		output.println(message.toMessageFormatString());
	}
}
