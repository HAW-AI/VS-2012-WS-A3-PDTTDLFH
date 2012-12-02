package branch_access;

import java.net.InetSocketAddress;
import java.util.concurrent.Semaphore;

import mware_lib.CommunicatorCaretaker;
import mware_lib.MessageDB;
import mware_lib.messages.ReplyMessage;
import mware_lib.messages.RequestMessage;

public class ManagerProxy extends Manager{

	private final String name;
	private final InetSocketAddress address;
	
	public ManagerProxy(String name, InetSocketAddress address) {
		this.name = name;
		this.address = address;
	}
	
	@Override
	public String createAccount(String owner) {
		RequestMessage requestMessage = new RequestMessage(name, "createAccount", owner);

		Semaphore messageSemaphore = MessageDB.put(requestMessage);

		CommunicatorCaretaker.getCommunicator(address).send(requestMessage);
		try {
			messageSemaphore.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		ReplyMessage replyMessage = MessageDB.getReplyForRequest(requestMessage);

		// if this is an exception reply message we will just throw the exception here
		if (replyMessage.exception()) {
			replyMessage.throwException();
		}

		// unless replyMessage.throwException throws an Exception we will just
		// return the value of the reply
		return replyMessage.value();
	}

	@Override
	public double getBalance(String accountID) {
		RequestMessage requestMessage = new RequestMessage(name, "getBalance", accountID);
		Semaphore messageSemaphore = MessageDB.put(requestMessage);
		try {
			messageSemaphore.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		ReplyMessage replyMessage = MessageDB.getReplyForRequest(requestMessage);
		
		// if this is an exception reply message we will just throw the exception here
		if (replyMessage.exception()) {
			replyMessage.throwException();
		}
		// unless replyMessage.throwException throws an Exception we will just
		// return the value of the reply
		return Double.valueOf(replyMessage.value());
	}
	
//	public void marshal(){
//		// TODO Auto-generated method stub
//	}
//	
//	public void unmarshal(){
//		// TODO Auto-generated method stub
//	}
}
