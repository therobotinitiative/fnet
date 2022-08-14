package com.orbital3d.server.fnet.security;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;

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
		}

		public static final class Userdata {
			public static final String GET = "admin:userdata:get";
			public static final String UPDATE = "admin:userdata:update";
		}

		public static final class Permission {
			public static final String LIST = "admin:permissions:list";
		}
	}

	public static Set<String> allPermissions()
			throws IllegalArgumentException, IllegalAccessException, SecurityException {
		Set<String> permissions = new HashSet<>();
		for (Class<?> c : FnetPermissions.class.getClasses()) {
			for (Class<?> c1 : c.getClasses()) {
				for (Field field : c1.getFields()) {
					// Only get the static final fields
					if ((field.getModifiers() & Modifier.STATIC) != 0 && (field.getModifiers() & Modifier.FINAL) != 0
							&& field.getType().equals(String.class)) {
						Object object = field.get(null);
						if (object.getClass().isAssignableFrom(String.class)) {
							permissions.add((String) object);
						}
					}
				}
			}
		}
		return permissions;
	}

}
