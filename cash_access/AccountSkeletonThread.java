package cash_access;

import mware_lib.Communicator;
import mware_lib.messages.ExceptionMessage;
import mware_lib.messages.RequestMessage;
import mware_lib.messages.ResultMessage;

public class AccountSkeletonThread extends Thread{
	private final Account account;
	private RequestMessage msg;
	private Communicator communicator;
	
	public AccountSkeletonThread(Account account, RequestMessage requestMessage,Communicator communicator) {
		this.account = account;
		this.msg = requestMessage;
		this.communicator = communicator;
	}

	@Override
	public void run() {
		try{
			String result = "void";
			if (msg.getMethodName().equals("deposite")) {
				account.deposit(Double.valueOf(msg.getParameters()));
			} else if (msg.getMethodName().equals("withdraw")) {
				account.withdraw(Double.valueOf(msg.getParameters()));
			} else if (msg.getMethodName().equals("getBalance")) {
				result = String.valueOf(account.getBalance());
			} else {
				throw new Exception("Invalid method call");
			}
			communicator.send(new ResultMessage(msg.getMessageID(), result));
		} catch (Exception e){
			communicator.send(new ExceptionMessage(msg.getMessageID(), e.getClass().toString(), e.getMessage()));
		}
	}
}
