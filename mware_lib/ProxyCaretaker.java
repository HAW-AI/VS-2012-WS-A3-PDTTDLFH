package mware_lib;

import java.net.InetSocketAddress;

public class ProxyCaretaker {
	static Object create(String name, String type, String host, int port) {
		System.out.println("Creating proxy for "+name+" "+type+" "+host+" "+port);
		Object result = null;
		try {
			final Class<?>[] CONSTRUCTOR_SIGNATURE = {Class.forName("java.lang.String"), Class.forName("java.net.InetSocketAddress")};
			final Object[] CONSTRUCTOR_ARGS = {name, new InetSocketAddress(host, port)};
			result = Class.forName(type+"Proxy").getConstructor(CONSTRUCTOR_SIGNATURE).newInstance(CONSTRUCTOR_ARGS);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return result;
	}
}
