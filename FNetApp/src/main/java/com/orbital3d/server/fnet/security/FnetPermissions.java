package com.orbital3d.server.fnet.security;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;

/**
 * FNet permissions.
 * 
 * @author msiren
 *
 */
public final class FnetPermissions {

	public static class Administrator {
		public static class User {
			public static final String LIST = "admin:users:list";
			public static final String CREATE = "admin:user:create";
			public static final String PASSWORD = "admin:user:password";
			public static final String DELETE = "admin:user:delete";
			public static final String PERMISSIONS = "admin:user:permissions";
			public static final String GROUPS = "admin:user:groups";
			public static final String UPDATE_GROUPS = "admin:user:update-groups";
			public static final String UPDATE_PERMISSIONS = "admin:user:update-permissions";
		}

		public static class GroupOperation {
			public static final String LIST = "admin:group:list";
			public static final String CREATE = "admin:group:create";
			public static final String DELETE = "admin:group:delete";
		}

		public static final class Userdata {
			public static final String GET = "admin:userdata:get";
			public static final String UPDATE = "admin:userdata:update";
		}

		public static final class Permission {
			public static final String LIST = "admin:permissions:list";
		}
	}

	public static final class Comment {
		public static final String CREATE = "comment:create";
		public static final String DELETE = "comment:delete";
	}

	public static class File {
		public static final String UPLOAD = "fnet:upload";
		public static final String DOWNLOAD = "fnet:download";
	}

	public static final class Folder {
		public static final String CREATE = "folder:create";
		public static final String DELETE = "folder:delete";
	}

	/**
	 * @return {@link Set} of all permissions
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws SecurityException
	 */
	public static Set<String> allPermissions()
			throws IllegalArgumentException, IllegalAccessException, SecurityException {
		Set<String> permissions = new HashSet<>();
		for (Class<?> clazz : FnetPermissions.class.getClasses()) {
			for (Class<?> innerClazz : clazz.getClasses()) {
				permissions.addAll(getFieldPermissions(innerClazz.getFields()));
			}
			permissions.addAll(getFieldPermissions(clazz.getFields()));
		}
		return permissions;
	}

	/**
	 * @param fields Fields to scan for static {@link String} fields
	 * @return {@link Set} of permission String
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws SecurityException
	 */
	private static Set<String> getFieldPermissions(Field[] fields)
			throws IllegalArgumentException, IllegalAccessException, SecurityException {
		Set<String> permissions = new HashSet<>();
		for (Field field : fields) {
			// Only get the static final fields
			if ((field.getModifiers() & Modifier.STATIC) != 0 && field.getType().equals(String.class)) {
				Object object = field.get(null);
				if (object.getClass().isAssignableFrom(String.class)) {
					permissions.add((String) object);
				}
			}
		}
		return permissions;
	}

}
