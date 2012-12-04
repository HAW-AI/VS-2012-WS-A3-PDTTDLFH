package mware_lib;

import mware_lib.messages.RequestMessage;

public interface Skeleton {
	public String getName();
	public void unmarshal(RequestMessage requestMessage, Communicator communicator);
}
