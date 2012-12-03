package mware_lib;

import java.util.HashMap;
import java.util.Map;

import utillity.Utility;

public class SkeletonCaretaker {
	private static Map<String, Skeleton> skeletons = new HashMap<String, Skeleton>();

	static void addSkeleton(Skeleton skeleton) {
		skeletons.put(skeleton.getName(), skeleton);
	}
	
	public static void addSkeleton(String name, Object servant) {
		Skeleton newSkeleton = null;
		String type = Utility.getOriginType(servant);
		try {
			final Class<?>[] CONSTRUCTOR_SIGNATURE = {Class.forName("java.lang.String"), Class.forName(type)};
			final Object[] CONSTRUCTOR_ARGS = {name, Class.forName(type).cast(servant)};
			newSkeleton = (Skeleton) Class.forName(type+"Skeleton").getConstructor(CONSTRUCTOR_SIGNATURE).newInstance(CONSTRUCTOR_ARGS);
			skeletons.put(name, newSkeleton);
		} catch (Exception e) {
			 e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	static Skeleton getSkeleton(String name) {
		//TODO should not return null when the skeleton is missing, its better to throw an exception to handle this case
		return skeletons.get(name);
	}
}
