package nameservice;

public class Utility {
	public static boolean isInt(String str) {
        boolean result = false;
		try {
            Integer.parseInt(str);
            result = true;
        } catch (NumberFormatException ex) {}
        return result;
    }
	
	public static String concatStrWDel(String delimiter, String... strings) {
	    StringBuilder sb = new StringBuilder();
	    String sep = "";
	    for(String s: strings) {
	        sb.append(sep).append(s);
	        sep = delimiter;
	    }
	    return sb.toString();                           
	}
	
	public static void log(String componentName, String message) {
		System.out.println(componentName + ": " + message);
	}
	
	public static String getOriginType(Object obj) {
		String type = "java.lang.Object";
		Class<?> objClass = obj.getClass();
		while (objClass.getSuperclass() != null) {
				type = objClass.getName();
				objClass = objClass.getSuperclass();
		}
		return type;
	}
}
