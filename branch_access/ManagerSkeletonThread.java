package branch_access;

import mware_lib.Communicator;
import mware_lib.messages.ExceptionMessage;
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
			communicator.send(new ResultMessage(msg.getMessageID(), result));
		} catch (Exception e){
			communicator.send(new ExceptionMessage(msg.getMessageID(), e.getClass().toString(), e.getMessage()));
		}
	}
}
