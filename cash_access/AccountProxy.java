package cash_access;

import java.net.InetSocketAddress;

public class AccountProxy extends Account{

	private String name;
	private InetSocketAddress address;
	
	public AccountProxy(String name, InetSocketAddress address) {
		this.name = name;
		this.address = address;
	}
	
	@Override
	public void deposit(double amount) {
		// TODO Auto-generated method stub
	}

	@Override
	public void withdraw(double amount) throws OverdraftException {
		// TODO Auto-generated method stub
	}

	@Override
	public double getBalance() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public void marshal(){
		// TODO Auto-generated method stub
	}
}
