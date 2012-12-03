package mware_lib.messages;

import utillity.Utility;
import static java.util.regex.Pattern.quote;

public class ExceptionMessage extends ReplyMessage {

	private final String exceptionMessageText;
	private final String type;

	public ExceptionMessage(Long messageID, String type, String message) {
		super(messageID);
		this.type = type;
		this.exceptionMessageText = message;
	}
	
	public ExceptionMessage(String message) {
		//quote for escaping delimiter to prevent problem with regex special chars
		super(Long.parseLong(message.split(quote(getMessageDelimeter()))[1]));
		String[] splitRequestMessage = message.split(quote(getMessageDelimeter()));
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
		Object obj=null;
		try {
			final Class<?>[] CONSTRUCTOR_SIGNATURE = {
					Class.forName("java.lang.String") };
			final Object[] CONSTRUCTOR_ARGS = { exceptionMessageText };
				obj = Class.forName(type).getConstructor(CONSTRUCTOR_SIGNATURE).newInstance(CONSTRUCTOR_ARGS);
			throw (RuntimeException) obj;
		} catch (Exception e) {
			System.out.println("An error occured while reflecting an exception. throwing new runtime exception");
			throw new RuntimeException(exceptionMessageText);
		}
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
