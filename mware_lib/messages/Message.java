package mware_lib.messages;

public abstract class Message {
	protected final Long messageID;
	
	public Message(long messageID) {
		this.messageID = messageID;
	}

	public Long getMessageID() {
		return this.messageID;
	}

	public abstract String toMessageFormatString();
	
	protected static String getMessageDelimeter() {
		// this is the delimeter that separates the components of
		// our MessageFormated String
		// e.g. "exception|12345|This is the exception message"
		return "|";
	}
}
