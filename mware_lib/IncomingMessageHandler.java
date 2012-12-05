package mware_lib;

import mware_lib.messages.ExceptionMessage;
import mware_lib.messages.ReplyMessage;
import mware_lib.messages.RequestMessage;
import mware_lib.messages.ResultMessage;

public class IncomingMessageHandler {
	/*
	 * This class deals with INCOMING messages. Do not be confused by the
	 * fact that it handles request messages as well as reply messages.
	 * Conceptually there is a Communicator on the Client side and on on the
	 * Server side. The client sends a message which received by the server
	 * and then handled by the server's IncomingMessageHandler which sees a
	 * 'request' message. Once the server sends a reply to the client the
	 * client's IncomingMessageHandler deals with the message and sees a
	 * 'result' message.
	 */

	public static void handle(String message, Communicator communicator) {
		if (message.startsWith("request")) {
			RequestMessage requestMessage = new RequestMessage(message);
			SkeletonCaretaker.getSkeleton(requestMessage.getRemoteObjectName()).unmarshal(requestMessage, communicator);
		} else {
			ReplyMessage replyMessage = null;

			if (message.startsWith("result")) {
				replyMessage = new ResultMessage(message);
			} else if (message.startsWith("exception")) {
				replyMessage = new ExceptionMessage(message);
			} else {
				log("unknown msg type received: "+message);
				throw new RuntimeException("Unknown msg type received: "+message);
			}

			MessageDB.put(replyMessage);
		}
	}

	private static void log(String logMessage) {
		Utility.log("CommunicatorStore", logMessage);
	}
}