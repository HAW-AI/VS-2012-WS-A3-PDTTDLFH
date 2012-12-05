package branch_access;

import java.net.InetSocketAddress;
import java.util.concurrent.Semaphore;

import mware_lib.CommunicatorStore;
import mware_lib.MessageDB;
import mware_lib.Utility;
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
		log("creating new account");
		RequestMessage requestMessage = new RequestMessage(name, "createAccount", owner);
		Semaphore messageSemaphore = MessageDB.put(requestMessage);
		log("sending creation msg");
		CommunicatorStore.getCommunicator(address).send(requestMessage);
		log("msg sent");
		try {
			messageSemaphore.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		log("got result");
		ReplyMessage replyMessage = MessageDB.getReplyForRequest(requestMessage);
		log("returning result");
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
	
	private void log(String logMessage) {
		Utility.log("ManagerProxy", logMessage);
	}
}
