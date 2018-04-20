package com.github.zhangkaitao.shiro.chapter3.mine;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.authz.permission.RolePermissionResolver;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

public class Main {
	@Test
	public void doAuthorizeWhenLoginSuccess(){
		
		DefaultSecurityManager securityManager = new DefaultSecurityManager();
		
		ModularRealmAuthorizer authorizer = new ModularRealmAuthorizer();
		
		
		RolePermissionResolver rolePermissionResolver = new MyRolePermissionResolver();
		
		authorizer.setRolePermissionResolver(rolePermissionResolver);
		
		securityManager.setAuthorizer(authorizer);
		
		securityManager.setRealm(new MyAuthorizingRealm());
		
		UsernamePasswordToken token = new UsernamePasswordToken("dragon", "dragonpsd".toCharArray());
		
		SecurityUtils.setSecurityManager(securityManager);
		
		Subject subject = SecurityUtils.getSubject();
		
		try {
			
			subject.login(token);
			
		} catch (Exception e) {
			
			// TODO: handle exception
			e.printStackTrace();
		}
		
		System.out.println("if it was done "+subject.hasRole("dragon"));
		
		System.out.println("if it was permitted dragon:90  "+subject.isPermitted("dragon:90"));
		
		System.out.println("if it was permitted something:delete  "+subject.isPermitted("something:delete"));
		
		System.out.println("if it was permitted not_dragon_but_I_authorized:dol "+subject.isPermitted("not_dragon_but_I_authorized:dol"));
		
	}
}
