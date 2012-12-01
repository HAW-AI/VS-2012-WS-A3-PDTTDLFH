package mware_lib.messages;


public class ResultMessage extends ReplyMessage {

	private final String value;

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
		return null;
	}

}
