package mware_lib;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.regex.Pattern;


public class NameServiceProxy extends NameService {
	
	private final Socket socket;
	private PrintWriter out;
	private BufferedReader in;
	
	public NameServiceProxy(Socket socket) {
		this.socket = socket;
		try{
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
		} catch (IOException e) {
			log("Can not listen to input/output of nameservice");
			throw new RuntimeException("Can not listen to input/output of nameservice");
		}	
	}

	@Override
	public void rebind(Object servant, String name) {
		CommunicatorFactory.init();
		String type = Utility.getOriginType(servant);
		String host = socket.getLocalAddress().getHostAddress();
		String port = String.valueOf(CommunicatorFactory.getDefaultPort());
		String msg =  Utility.concatStrWDel("|", "rebind", name, type, host, port);
		log("sending msg: " + msg);
		out.println(msg);
		out.flush();
		String result = null;
		try {
			result = in.readLine();
			log("receiving msg: " + result);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (result.equals("ok")) {
			SkeletonCaretaker.addSkeleton(name, servant);
		}
	}
	
	@Override
	public Object resolve(String name) {
		Object result = new Object();
		String msg = Utility.concatStrWDel("|", "resolve", name);
		log("sending msg: " + msg);
		out.println(msg);
		out.flush();
		String[] answer = null;
		try {
			answer = in.readLine().split(Pattern.quote("|"));
		} catch (IOException e) {
			log("could not receive an answer from nameservice: "+e.getMessage());
			throw new RuntimeException("Could not receive an answer from nameservice: "+e.getMessage());
		}
		if (answer[0].equals("result")) {
			log("received msg: "+Arrays.toString(answer));
			result = ProxyCaretaker.create(answer[1], answer[2], answer[3], Integer.parseInt(answer[4]));
		} else if (answer[0].equals("not_found")) {
			log("received msg: "+Arrays.toString(answer));
			throw new RuntimeException("Requested object not found");
		} else if(answer[0].equals("exception")){
			log("received msg: "+Arrays.toString(answer));
			throw new RuntimeException("Nameservice exception: "+answer[1]);
		} else {
			log("received unknown msg during rebind: "+Arrays.toString(answer));
			throw new RuntimeException("Received unknown msg during rebind"+Arrays.toString(answer));
		}
		return result;
	}

	private void log(String logMessage) {
		Utility.log("NameServiceProxy", logMessage);
	}
}
