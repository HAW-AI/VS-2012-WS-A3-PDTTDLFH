package mware_lib.messages;

import static java.util.regex.Pattern.quote;
import mware_lib.Utility;

public class ResultMessage extends ReplyMessage {

	private final String value;

	public ResultMessage(Long messageID, String value) {
		super(messageID);
		this.value = value;
	}
	
	public ResultMessage(String message) {
		// extract the MessageID from the result string
		//quote for escaping delimiter to prevent problem with regex special chars
		super(Long.parseLong(message.split(quote(getMessageDelimeter()))[1]));
		this.value = message.split(quote(getMessageDelimeter()))[2];
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
