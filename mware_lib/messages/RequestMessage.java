package mware_lib.messages;

import mware_lib.MessageID;
import mware_lib.Utility;
import static java.util.regex.Pattern.quote;

public class RequestMessage extends Message {

	private final String remoteObjectName;
	private final String methodName;
	private final String parameters;

	public RequestMessage(String proxyName, String methodName, String variableLengthParameters) {
		super(MessageID.getNextMessageID());
		this.remoteObjectName = proxyName;
		this.methodName = methodName;
		this.parameters = variableLengthParameters;
	}

	/*
	 * This constructor is necessary because a request message will be received as
	 * a string by the IcomingMessageHandler which then has to build a RequestMessage
	 * from the string
	 * Also important because building a Request from its string representation does
	 * not give-out a new MessageID!!!
	 */
	public RequestMessage(String requestMessage) {
		//quote for escaping delimiter to prevent problem with regex special chars
		super(Long.parseLong(requestMessage.split(quote(getMessageDelimeter()))[1]));
		String[] splitRequestMessage = requestMessage.split(quote(getMessageDelimeter()));
		this.remoteObjectName = splitRequestMessage[2];
		this.methodName = splitRequestMessage[3];
		this.parameters = splitRequestMessage[4];
	}

	@Override
	public String toMessageFormatString() {
		return Utility.concatStrWDel(getMessageDelimeter(),
				"request", messageID.toString(), remoteObjectName, methodName, parameters);
	}

	public String getRemoteObjectName() {
		return remoteObjectName;
	}
	
	public String getMethodName() {
		return this.methodName;
	}
	
	public String getParameters() {
		return this.parameters;
	}

	private void log(String logMessage) {
		Utility.log("RequestMessage", logMessage);
	}
}
