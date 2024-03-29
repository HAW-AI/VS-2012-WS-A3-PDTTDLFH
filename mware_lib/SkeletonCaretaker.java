package mware_lib;

import java.util.HashMap;
import java.util.Map;


public class SkeletonCaretaker {
	private static Map<String, Skeleton> skeletons = new HashMap<String, Skeleton>();

	public static void addSkeleton(String name, Object servant) {
		log("creating proxy for "+name);
		Skeleton newSkeleton = null;
		String type = Utility.getOriginType(servant);
		try {
			final Class<?>[] CONSTRUCTOR_SIGNATURE = {Class.forName("java.lang.String"), Class.forName(type)};
			final Object[] CONSTRUCTOR_ARGS = {name, Class.forName(type).cast(servant)};
			newSkeleton = (Skeleton) Class.forName(type+"Skeleton").getConstructor(CONSTRUCTOR_SIGNATURE).newInstance(CONSTRUCTOR_ARGS);
			skeletons.put(name, newSkeleton);
		} catch (Exception e) {
			log("An error occured while reflecting a skeleton: "+e.getMessage());
			throw new RuntimeException("An error occured while reflecting a skeleton: "+e.getMessage());
		}
	}
	
	static Skeleton getSkeleton(String name) {
		Skeleton skeleton = skeletons.get(name);
		if(skeleton == null){
			skeleton = new ExceptionSkeleton();
		}
		return skeletons.get(name);
	}

	private static void log(String logMessage) {
		Utility.log("SkeletonCaretaker", logMessage);
	}
}
