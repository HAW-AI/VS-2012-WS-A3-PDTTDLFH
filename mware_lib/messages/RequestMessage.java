package mware_lib.messages;

import mware_lib.MessageID;

public class RequestMessage extends Message {

	private final String proxyName;
	private final String methodName;
	private final String parameters;

	public RequestMessage(String proxyName, String methodName, String variableLengthParameters) {
		super(MessageID.getNextMessageID());
		this.proxyName = proxyName;
		this.methodName = methodName;
		this.parameters = variableLengthParameters;
	}

	@Override
	public String toMessageFormatString() {
		return null;
	}
}
