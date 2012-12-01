package test;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import nameservice.NameService;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class NameServiceTest {

	static int port = 50001;
	static String host = "localhost";
	static BufferedReader in;
	static PrintWriter out;
	static Socket socket;
	static NameService nameservice;
	String msg = "";
	
	@BeforeClass
	public static void startNameservice() throws IOException, InterruptedException {
		nameservice = new NameService(port);
		nameservice.start();
		socket = new Socket(host, port);
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new PrintWriter(socket.getOutputStream(), true);
	}
	
	@AfterClass
	public static void closeSocket() throws IOException, InterruptedException {
		in.close();
		out.close();
		socket.close();
		nameservice.terminate();
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void illegalPort() {
		NameService nameservice = new NameService(-1);
		nameservice.run();
	}
	
	@Test
	public void resolveException() throws IOException, InterruptedException {
		out.println("test");
		msg = in.readLine();
		System.out.println("Test result: "+msg);
		assertTrue(msg.startsWith("exception") && !msg.isEmpty());
	}

	@Test
	public void resolveRebind() throws IOException, InterruptedException {
		out.println("rebind,testname,testtype,testhost,1");
		msg = in.readLine();
		System.out.println("Test result: "+msg);
		assertTrue(msg.startsWith("ok") && !msg.isEmpty());
	}
	
	@Test
	public void resolveNotFound() throws IOException, InterruptedException {
		out.println("resolve,unknown");
		msg = in.readLine();
		System.out.println("Test result: "+msg);
		assertTrue(msg.startsWith("not_found") && !msg.isEmpty());
	}
	
	@Test
	public void resolveFound() throws IOException, InterruptedException {
		out.println("rebind,testname,testtype,testhost,1");
		msg = in.readLine();
		System.out.println("Test result: "+msg);
		out.println("resolve,testname");
		msg = in.readLine();
		System.out.println("Test result: "+msg);
		assertTrue(msg.startsWith("result") && !msg.isEmpty());
	}
}

