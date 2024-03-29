package mware_lib.messages;

public abstract class ReplyMessage extends Message {
	public ReplyMessage(long messageID) {
		super(messageID);
	}

	public abstract boolean exception();
	
	public abstract void throwException();
	
	public abstract String value();
}
