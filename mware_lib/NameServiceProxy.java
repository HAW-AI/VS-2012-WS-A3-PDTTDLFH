package mware_lib;

import java.net.Socket;

public class NameServiceProxy extends NameService {
	
	private final Socket socket;

	public NameServiceProxy(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void rebind(Object servant, String name) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object resolve(String name) {
		// TODO Auto-generated method stub
		return null;
	}

}
