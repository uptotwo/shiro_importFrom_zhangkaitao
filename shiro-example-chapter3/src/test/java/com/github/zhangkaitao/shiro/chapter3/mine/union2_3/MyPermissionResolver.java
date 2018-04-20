package com.github.zhangkaitao.shiro.chapter3.mine.union2_3;


import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.PermissionResolver;
import org.apache.shiro.authz.permission.WildcardPermission;
/**
 * 直接将字符串解释为WildcardPermission()
 * @author admin
 *
 */
public class MyPermissionResolver implements PermissionResolver{

	@Override
	public Permission resolvePermission(String permissionString) {
		// TODO Auto-generated method stub
		if(permissionString == null || permissionString.equals("")){
			
		}
		return new WildcardPermission(permissionString);
	}
	
}
