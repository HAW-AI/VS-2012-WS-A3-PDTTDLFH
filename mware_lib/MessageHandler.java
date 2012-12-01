package mware_lib;

import mware_lib.messages.ExceptionMessage;
import mware_lib.messages.ReplyMessage;
import mware_lib.messages.RequestMessage;
import mware_lib.messages.ResultMessage;

public class MessageHandler {

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