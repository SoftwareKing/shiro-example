package org.xujin.shiro.permission;

import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.PermissionResolver;
import org.apache.shiro.authz.permission.WildcardPermission;

public class MyPermissionResolver implements PermissionResolver {

	public Permission resolvePermission(String permissionString) {
		if(permissionString.startsWith("+")) {
			return new MyPermission(permissionString);
		}
		return new WildcardPermission(permissionString);
	}

}
