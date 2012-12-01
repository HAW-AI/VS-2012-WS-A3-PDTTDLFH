package mware_lib;

public class MessageID {
	private static long currentMessageID = 0;

	public static synchronized long getNextMessageID() {
		return currentMessageID++;
	}
}
