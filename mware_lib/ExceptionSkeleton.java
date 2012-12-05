package mware_lib;

import mware_lib.messages.ExceptionMessage;
import mware_lib.messages.RequestMessage;

public class ExceptionSkeleton implements Skeleton{

	@Override
	public String getName() {
		return "ExceptionSkeleton";
	}

	@Override
	public void unmarshal(RequestMessage requestMessage,Communicator communicator) {
		communicator.send(new ExceptionMessage(requestMessage.getMessageID(), "java.lang.RuntimeException", "The requests object does not exist"));
	}

	private void log(String logMessage) {
		Utility.log("ExceptionSkeleton", logMessage);
	}
}
