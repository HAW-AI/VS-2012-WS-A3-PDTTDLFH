package nameservice;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class NameService extends Thread{
	
	private Map<String, ObjectData> objectMap = new HashMap<String, ObjectData>();
	private volatile boolean run = true;
	int port;
	
	public NameService(int port){
		this.port = port;
	}
	
	@Override
	public void run() {
		ServerSocket welcomeSocket = null;
		try {
			welcomeSocket = new ServerSocket(this.port);
			System.out.println("Nameservice is running");
			while (run) {
				Socket socket = welcomeSocket.accept();
				System.out.println("Got connection request. Starting Thread");
				NameServiceThread nameservice = new NameServiceThread(socket, this);
				nameservice.setDaemon(true);
				nameservice.start();
			}
			System.out.println("Nameservice is terminating");
			welcomeSocket.close();
		} catch (IOException e) {
			System.out.println("could not listen to port: "+this.port);
			throw new RuntimeException("Could not listen to port: "+this.port);
		}
	}
	
	public void terminate() {
	    run = false;
	}
	
	void addObject(String name, ObjectData value){
		objectMap.put(name, value);
	}
	
	ObjectData getObject(String name){
		return objectMap.get(name);
	}
}
