package mware_lib.messages;

import utillity.Utility;

public class ResultMessage extends ReplyMessage {

	private final String value;

	public ResultMessage(Long messageID, String value) {
		super(messageID);
		this.value = value;
	}
	
	public ResultMessage(String message) {
		// extract the MessageID from the result string
		super(Long.parseLong(message.split(getMessageDelimeter())[1]));
		this.value = message.split(getMessageDelimeter())[2];
	}

	public String value() {
		return value;
	}

	@Override
	public boolean exception() { return false; }

	@Override
	public String toMessageFormatString() {
		return Utility.concatStrWDel(getMessageDelimeter(),	"result", messageID.toString(), value);
	}

	@Override
	public void throwException() {}

}
