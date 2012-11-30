package utillity;

public class Utility {
	public static boolean isInt(String str) {
        boolean result = false;
		try {
            Integer.parseInt(str);
            result = true;
        } catch (NumberFormatException ex) {}
        return result;
    }
}
