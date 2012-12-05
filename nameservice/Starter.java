package nameservice;

import mware_lib.Utility;

public class Starter {
	public static void main(String[] args) {
		if(args.length < 1 || !Utility.isInt(args[0])){
			System.out.println("Please enter the welcome socket port number as first argument");
			System.exit(0);
		}
		NameService nameService = new NameService(Integer.parseInt(args[0]));
		nameService.start();
	}
}
