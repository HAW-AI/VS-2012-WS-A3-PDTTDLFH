package mware_lib;

import mware_lib.messages.ExceptionMessage;
import mware_lib.messages.ReplyMessage;
import mware_lib.messages.ResultMessage;

public class IncomingMessageHandler {
	/*
	 * This class deals with INCOMING messages. Do not be confused by the
	 * fact that it handles request messages as well as reply messages.
	 * Conceptually there is a Communicator on the Clientside and on on the
	 * Server side. The client sends a message which received by the server
	 * and then handled by the server's IncomingMessageHandler which sees a
	 * 'request' message. Once the server sends a reply to the client the
	 * client's IncomingMessageHandler deals with the message and sees a
	 * 'result' message.
	 */

	public static void handle(String message, Communicator communicator) {
		if (message.startsWith("request")) {
			// TODO add constructor which reads an existing message from string
			// and does not create one with a new messageID
			//RequestMessage requestMessage = new RequestMessage(message);
		} else {
			ReplyMessage replyMessage = null;

			if (message.startsWith("result")) {
				replyMessage = new ResultMessage(message);
			} else if (message.startsWith("exception")) {
				replyMessage = new ExceptionMessage(message);
			}

			MessageDB.put(replyMessage);
		}
	}

}
