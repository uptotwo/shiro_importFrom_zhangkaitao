package com.github.zhangkaitao.shiro.chapter3.mine.union2_3;

import java.util.Arrays;
import java.util.Collection;

import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.RolePermissionResolver;
import org.apache.shiro.authz.permission.WildcardPermission;
/**
 * admin 拥有所有权限
 * guest 拥有  *:insert,view 权限
 * @author admin
 *
 */
public class MyRolePermissionResolver implements  RolePermissionResolver{

	@Override
	public Collection<Permission> resolvePermissionsInRole(String roleString) {
		// TODO Auto-generated method stub
		if(roleString!= null && roleString.equals("admin")){
			return Arrays.asList(new WildcardPermission("*"));
		}else if(roleString.equals("guest")){
			return Arrays.asList(new WildcardPermission("*:insert,view"));
		}
		return null;
	}

}
