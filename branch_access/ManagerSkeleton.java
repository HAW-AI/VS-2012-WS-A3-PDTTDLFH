package branch_access;

import mware_lib.Communicator;
import mware_lib.Skeleton;
import mware_lib.messages.RequestMessage;

public class ManagerSkeleton implements Skeleton {
	
	private final String name;
	private final Manager manager;
	
	public ManagerSkeleton(String name, Manager manager) {
		this.name = name;
		this.manager = manager;
	}

	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public void marshal(String msg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unmarshal(RequestMessage requestMessage, Communicator communicator) {
		new ManagerSkeletonThread(manager, requestMessage, communicator).start();
	}
}
