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
			log("opening new socket "+socketAddress.getAddress()+" "+socketAddress.getPort());
			tmpSocket = new Socket(socketAddress.getAddress(), socketAddress.getPort());
			tmpInput = new BufferedReader(new InputStreamReader(tmpSocket.getInputStream()));
			tmpOutput = new PrintWriter(tmpSocket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.socket = tmpSocket;
		this.input = tmpInput;
		this.output = tmpOutput;
	}

	public Communicator(Socket socket) {
		BufferedReader tmpInput = null;
		PrintWriter tmpOutput = null;

		try {
			tmpInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			tmpOutput = new PrintWriter(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.input = tmpInput;
		this.output = tmpOutput;
		this.socket = socket;
	}

	public void run() {
		String inputLine = null;
		try {
			// while the socket is open and new messages arrive we fectch them
			// and pass them of to the MessageHandler
			log("new communicator is listening on " + socket.getInetAddress().getHostAddress() + ":" + socket.getLocalPort());
			while (!socket.isClosed() && ((inputLine = input.readLine()) != null)) {
				log("received msg: "+inputLine);
				IncomingMessageHandler.handle(inputLine, this);
			}
			socket.close();
			input.close();
			output.close();
		} catch (IOException e) {
			log("Connection closed by other side of the socket");
		}
	}

	public void send(Message message) {
	    log("sending msg from port " + socket.getLocalPort() + " to " + socket.getInetAddress() + ":" + socket.getPort());
		output.println(message.toMessageFormatString());
		output.flush();
	}

	public InetSocketAddress inetSocketAddress() {
		return new InetSocketAddress(socket.getInetAddress(), socket.getPort());
	}

	private void log(String logMessage) {
		Utility.log("Communicator", logMessage);
	}
}
