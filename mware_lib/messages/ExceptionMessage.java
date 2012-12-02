package mware_lib.messages;

import utillity.Utility;

public class ExceptionMessage extends ReplyMessage {

	private final String exceptionMessageText;
	private final String type;

	public ExceptionMessage(Long messageID, String type, String message) {
		super(messageID);
		this.type = type;
		this.exceptionMessageText = message;
	}
	
	public ExceptionMessage(String message) {
		super(Long.parseLong(message.split(getMessageDelimeter())[1]));
		String[] splitRequestMessage = message.split(getMessageDelimeter());
		this.type = splitRequestMessage[2];
		this.exceptionMessageText = splitRequestMessage[3];
	}

	@Override
	public String toMessageFormatString() {
		return Utility.concatStrWDel(getMessageDelimeter(), "exception", messageID.toString(), type, exceptionMessageText);
	}

	@Override
	public boolean exception() { return true; }

	public void throwException() {
		// TODO
		//how do we identify an OverdraftException?
		// puff, peng, zuff
	}

	@Override
	public String value() { return null; }

	public String getExceptionMessageText(){
		return this.exceptionMessageText;
	}
	
	public String getType(){
		return this.type;
	}
}
