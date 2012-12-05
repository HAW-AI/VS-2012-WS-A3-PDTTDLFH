package mware_lib;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;

import mware_lib.messages.ReplyMessage;
import mware_lib.messages.RequestMessage;

public class MessageDB {
	private static Map<Long, Semaphore> messageSemaphores = new HashMap<Long, Semaphore>();
	private static Map<Long, ReplyMessage> messageDb = new HashMap<Long, ReplyMessage>(); 

	/*
	 * Storing a Message in the MessageDB via 'put' means a reply
	 * has come in, which means a Client Process which has been waiting
	 * for this reply can now be unblocked and lookup the Reply to his
	 * RequestMessage and continue to work with this value or exception.
	 */
	public static void put(ReplyMessage replyMessage) {
		messageDb.put(replyMessage.getMessageID(), replyMessage);
		/*
		 *  when a reply has been stored in the MessageDB and is available for
		 *  the receiver we can release the corresponding semaphore and the
		 *  client can work with the result
		 */
		messageSemaphores.get(replyMessage.getMessageID()).release();
	}

	public static Semaphore put(RequestMessage requestMessage) {
		Semaphore requestMessageSemaphore = new Semaphore(0);
		messageSemaphores.put(requestMessage.getMessageID(), requestMessageSemaphore);
		return requestMessageSemaphore;
	}

	public static ReplyMessage getReplyForRequest(RequestMessage requestMessage) {
		return pop(requestMessage.getMessageID());
	}

	public static ReplyMessage pop(Long messageID) {
		messageSemaphores.remove(messageID);
		return messageDb.remove(messageID);
	}

	private static void log(String logMessage) {
		Utility.log("CommunicatorStore", logMessage);
	}
}
