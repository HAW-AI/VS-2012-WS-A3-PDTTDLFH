package branch_access;

import java.lang.reflect.Method;

import javax.management.RuntimeErrorException;

import mware_lib.Communicator;
import mware_lib.messages.ReplyMessage;
import mware_lib.messages.RequestMessage;
import mware_lib.messages.ResultMessage;

public class ManagerSkeletonThread extends Thread{

	private final Manager manager;
	private RequestMessage msg;
	private Communicator communicator;
	
	public ManagerSkeletonThread(Manager manager, RequestMessage requestMessage,Communicator communicator) {
		this.manager = manager;
		this.msg = requestMessage;
		this.communicator = communicator;
	}

	@Override
	public void run() {
		try{
			String result;
			if (msg.getMethodName().equals("createAccount")) {
				result = manager.createAccount(msg.getParameters());
			} else if (msg.getMethodName().equals("getBalance")) {
				result = String.valueOf(manager.getBalance(msg.getParameters()));
			} else {
				throw new Exception("Invalid method call");
			}
			communicator.send();//TODO result reply
		} catch (Exception e){
			communicator.send();//TODO exception reply
		}
	}
}
