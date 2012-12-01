package nameservice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import utillity.Utility;

public class NameServiceThread extends Thread{
	
	private final Socket socket;
	private BufferedReader in;
	private PrintWriter out;
	private NameService nameService;

	public NameServiceThread(Socket socket, NameService nameService) {
		this.socket = socket;
		this.nameService = nameService;
		try{
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}

	@Override
	public void run() {
		String msg;
		try {
			while (!socket.isClosed() && ((msg = in.readLine()) != null)) {
				String[] tokens = msg.split(",");
				String action = tokens[0];

				if(action.equals("rebind")){
					if (tokens.length != 5 && !Utility.isInt(tokens[4])) {
						error("rebind: wrong number of params or invalid port");
					} else {
						rebind(tokens[1], tokens[2], tokens[3], Integer.parseInt(tokens[4]));
					}
				} else if (action.equals("resolve")){
					if (tokens.length != 2) {
						error("resolve: wrong number of params");
					} else {
						resolve(tokens[1]);
					}
				} else {
					error("unknown command: " + tokens[0]);
				}
			}
			System.out.println("Request processed. Terminating thread");
			in.close();
			out.close();
			if (!socket.isClosed()) {
				socket.close();
			}
		} catch (IOException e) {
			System.out.println("Connection closed by client");
		}
	}
	
	public void rebind(String name, String type, String host, int port) {
		if (name.isEmpty()) {
			error("rebind: name must not be empty");
		} else if (type.isEmpty()) {
			error("rebind: type must not be empty");
		} else if (host.isEmpty()) {
			error("rebind: host must not be empty");
		} else if (port < 0 || port > 65535) {
			error("rebind: port must be an integer and 0<=port<=65535");
		} else {
			System.out.println("Request: rebind >"+name+" "+type+" "+host+" "+port);
			nameService.addObject(name, new ObjectData(name, type, host, port));
			out.println("ok");
		}
	}

	public void resolve(String name) {
		if (name.isEmpty()) {
			error("resolve: name must not be empty");
		} else {
			ObjectData obj = this.nameService.getObject(name);
			if (obj == null) {
				out.println("not_found");
			} else {
				System.out.println("Request: resolve");
				String msg = "result,"+obj.name()+","+obj.type()+","+obj.host()+","+obj.port();
				out.println(msg);
			}
		}
	}
		
	private void error(String msg) {
		System.out.println("ERROR: " + msg);
		out.println("exception," + msg);
	}
}

