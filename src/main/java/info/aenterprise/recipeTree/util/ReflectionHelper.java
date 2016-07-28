package info.aenterprise.recipeTree.util;

import java.lang.reflect.Field;
import java.util.HashMap;

/**
 * Copyright (c) 2016, AEnterprise
 * http://www.aenterprise.info/
 */
public class ReflectionHelper {
	private static HashMap<String, Field> cache = new HashMap<>();

	public static Object getValue(Object object, String fieldName) {
		String fullname = object.getClass().getCanonicalName() + ":" + fieldName;
		try {
			if (!cache.containsKey(fullname)) {
				Field field = object.getClass().getDeclaredField(fieldName);
				field.setAccessible(true);
				cache.put(fullname, field);
			}
			return cache.get(fullname).get(object);
		} catch (NoSuchFieldException|IllegalAccessException e) {
			Log.error("Unable to gain access to " + fullname);
			Log.error("This means that this version of RecipeTree is not compatible with the installed version of JEI");
			Log.error("Please check if there is a newer version installed, if not please report this and provide this information in your report", e);
		}
		return null;
	}
}
