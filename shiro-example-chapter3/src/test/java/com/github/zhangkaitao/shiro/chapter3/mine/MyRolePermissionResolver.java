package com.github.zhangkaitao.shiro.chapter3.mine;

import java.util.Arrays;
import java.util.Collection;

import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.RolePermissionResolver;
import org.apache.shiro.authz.permission.WildcardPermission;

public class MyRolePermissionResolver implements RolePermissionResolver{

	@Override
	public Collection<Permission> resolvePermissionsInRole(String roleString) {
		// TODO Auto-generated method stub
		if (roleString.trim() == "dragon") {
			
			return Arrays.asList(new WildcardPermission("dragon:*"));
			
		} else if (roleString.trim() == "someone"){
			
			return Arrays.asList(new WildcardPermission("something:delete,update:athing"));
		}
		return null;
	}

	
}
