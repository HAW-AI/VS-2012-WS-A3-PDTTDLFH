package mware_lib;

import java.net.InetSocketAddress;
import java.util.concurrent.Semaphore;

import mware_lib.messages.ExceptionMessage;
import mware_lib.messages.ReplyMessage;
import mware_lib.messages.RequestMessage;

public class MethodCaller {
	
	private InetSocketAddress address;
	private String obj;
	private ReplyMessage result;

	public MethodCaller(InetSocketAddress remoteObjAddress, String remoteObj){
		this.address = remoteObjAddress;
		this.obj = remoteObj;
	}
	
	public String call(String method, String parameter){
		RequestMessage requestMessage = new RequestMessage(obj, method, parameter);
		Semaphore messageSemaphore = MessageDB.put(requestMessage);
		CommunicatorStore.getCommunicator(address).send(requestMessage);
		try {
			messageSemaphore.acquire();
		} catch (InterruptedException e) {
			System.out.println("Semaphore for a msg got interrupted: "+e.getMessage());
		}
		ReplyMessage replyMessage = MessageDB.getReplyForRequest(requestMessage);
		this.result = replyMessage;
		return replyMessage.value();
	}
	
	public boolean isExeption(){
		return result.exception();
	}
	
	public void throwException(){
		result.throwException();
	}
	
	public boolean isSpecialException(){
		boolean result = false;
		if(this.isExeption()){
			ExceptionMessage exMsg = (ExceptionMessage) this.result;
			if(!exMsg.getType().equals("java.lang.Exception") && !exMsg.getType().equals("java.lang.RuntimeException")){
				result = true;
			}
		}
		return result;
	}
	
	public Exception getException(){
		return ((ExceptionMessage) this.result).getException();
	}
}
