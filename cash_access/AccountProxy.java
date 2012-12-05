package cash_access;

import java.net.InetSocketAddress;

import mware_lib.MethodCaller;

public class AccountProxy extends Account{

	private String name;
	private InetSocketAddress address;
	
	public AccountProxy(String name, InetSocketAddress address) {
		this.name = name;
		this.address = address;
	}
	
	@Override
	public void deposit(double amount) {
		MethodCaller caller = new MethodCaller(address, name);
		caller.call("deposite", String.valueOf(amount));
		if(caller.isExeption()){
				caller.throwException();
		}
	}

	@Override
	public void withdraw(double amount) throws OverdraftException {
		MethodCaller caller = new MethodCaller(address, name);
		caller.call("withdraw", String.valueOf(amount));
		if(caller.isExeption()){
			if(caller.isSpecialException()){
				throw (OverdraftException) caller.getException();
			} else {
				caller.throwException();
			}
		}
	}

	@Override
	public double getBalance() {
		MethodCaller caller = new MethodCaller(address, name);
		String result = caller.call("getBalance", "void");
		if(caller.isExeption()){
				caller.throwException();
		}
		return Double.valueOf(result);
	}
}
