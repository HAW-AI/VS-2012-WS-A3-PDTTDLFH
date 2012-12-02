package cash_access;

import java.net.InetSocketAddress;
import java.util.concurrent.Semaphore;

import mware_lib.CommunicatorStore;
import mware_lib.MessageDB;
import mware_lib.messages.ReplyMessage;
import mware_lib.messages.RequestMessage;

public class AccountProxy extends Account{

	private String name;
	private InetSocketAddress address;
	
	public AccountProxy(String name, InetSocketAddress address) {
		this.name = name;
		this.address = address;
	}
	
	@Override
	public void deposit(double amount) {
		RequestMessage requestMessage = new RequestMessage(name, "deposite", String.valueOf(amount));
		Semaphore messageSemaphore = MessageDB.put(requestMessage);
		CommunicatorStore.getCommunicator(address).send(requestMessage);
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
	}

	@Override
	public void withdraw(double amount) throws OverdraftException {
		RequestMessage requestMessage = new RequestMessage(name, "deposite", String.valueOf(amount));
		Semaphore messageSemaphore = MessageDB.put(requestMessage);
		CommunicatorStore.getCommunicator(address).send(requestMessage);
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
	}

	@Override
	public double getBalance() {
		RequestMessage requestMessage = new RequestMessage(name, "getBalance", "void");
		Semaphore messageSemaphore = MessageDB.put(requestMessage);
		CommunicatorStore.getCommunicator(address).send(requestMessage);
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
