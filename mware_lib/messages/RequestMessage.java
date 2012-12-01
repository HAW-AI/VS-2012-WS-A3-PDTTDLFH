package mware_lib.messages;

import mware_lib.MessageID;

public class RequestMessage extends Message {

	public RequestMessage(String message) {
		super(MessageID.getNextMessageID());
	}

	@Override
	public String toMessageFormatString() {
		return null;
	}
}
