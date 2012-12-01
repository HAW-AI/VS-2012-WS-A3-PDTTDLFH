package mware_lib.messages;

public class ExceptionMessage extends ReplyMessage {

	private final String exceptionMessageText;

	public ExceptionMessage(String message) {
		// extract the MessageID from the result string
		super(Long.parseLong(message.split(getMessageDelimeter())[1]));
		String[] separatedMessage = message.split(getMessageDelimeter());

		this.exceptionMessageText = separatedMessage[2];
	}

	@Override
	public String toMessageFormatString() {
		return null;
	}

	@Override
	public boolean exception() { return true; }

}
