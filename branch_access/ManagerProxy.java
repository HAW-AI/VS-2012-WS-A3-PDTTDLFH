package branch_access;

import java.net.InetSocketAddress;

import mware_lib.MethodCaller;

public class ManagerProxy extends Manager{

	private final String name;
	private final InetSocketAddress address;
	
	public ManagerProxy(String name, InetSocketAddress address) {
		this.name = name;
		this.address = address;
	}
	
	@Override
	public String createAccount(String owner) {
		MethodCaller caller = new MethodCaller(address, name);
		String result = caller.call("createAccount", owner);
		if(caller.isExeption()){
				caller.throwException();
		}
		return result;
	}

	@Override
	public double getBalance(String accountID) {
		MethodCaller caller = new MethodCaller(address, name);
		String result = caller.call("getBalance", accountID);
		if(caller.isExeption()){
				caller.throwException();
		}
		return Double.valueOf(result);
	}
}
