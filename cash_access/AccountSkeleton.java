package cash_access;
import mware_lib.Skeleton;

public class AccountSkeleton  implements Skeleton {
	
	private final String name;
	
	public AccountSkeleton(String name){
		this.name = name;
	}
	
	@Override
	public String name() {
		return name;
	}

}
