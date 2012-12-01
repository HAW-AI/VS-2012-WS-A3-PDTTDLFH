package branch_access;

import java.net.InetSocketAddress;

public class ManagerProxy extends Manager{

	private final String name;
	private final InetSocketAddress address;
	
	public ManagerProxy(String name, InetSocketAddress address) {
		this.name = name;
		this.address = address;
	}
	
	@Override
	public String createAccount(String owner) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getBalance(String accountID) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public void marshal(){
		// TODO Auto-generated method stub
	}
}
