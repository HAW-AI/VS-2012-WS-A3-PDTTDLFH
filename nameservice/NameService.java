package nameservice;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class NameService implements Runnable{
	
	private Map<String, ObjectData> objectMap = new HashMap<String, ObjectData>();
	int port;
	
	public NameService(int port){
		this.port = port;
	}
	
	@Override
	public void run() {
		try {
			ServerSocket welcomeSocket = new ServerSocket(this.port);
			System.out.println("Nameservice is running");
			while (true) {
				Socket socket = welcomeSocket.accept();
				System.out.println("Got request. Starting Thread");
				NameServiceThread nameservice = new NameServiceThread(socket, this);
				nameservice.setDaemon(true);
				nameservice.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	void addObject(String name, ObjectData value){
		objectMap.put(name, value);
	}
	
	ObjectData getObject(String name){
		return objectMap.get(name);
	}
}
