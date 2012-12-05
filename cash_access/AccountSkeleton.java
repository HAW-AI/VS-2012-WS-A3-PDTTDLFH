package cash_access;
import mware_lib.Communicator;
import mware_lib.Skeleton;
import mware_lib.Utility;
import mware_lib.messages.RequestMessage;

public class AccountSkeleton  implements Skeleton {
	
	private final String name;
	private final Account account;
	
	public AccountSkeleton(String name, Account account){
		this.name = name;
		this.account = account;
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public void unmarshal(RequestMessage requestMessage, Communicator communicator) {
		new AccountSkeletonThread(account, requestMessage, communicator).start();
	}

	private void log(String logMessage) {
		Utility.log("AccountSkeleton", logMessage);
	}

}
