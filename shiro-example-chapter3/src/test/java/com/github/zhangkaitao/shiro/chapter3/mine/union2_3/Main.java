package com.github.zhangkaitao.shiro.chapter3.mine.union2_3;

import java.util.Arrays;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.pam.AuthenticationStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.authz.permission.PermissionResolver;
import org.apache.shiro.authz.permission.RolePermissionResolver;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.junit.After;
import org.junit.Test;

public class Main {
	@Test
	public void doAuthorizeAndAnthenticate(){
	
//		创建安全管理器 
		DefaultSecurityManager securityManager = new DefaultSecurityManager();
//		创建权鉴策略 
		AuthenticationStrategy myAuthenticationStrategy = new MyAuthenticationStrategy();
//		创建权鉴 鉴定器 
		ModularRealmAuthenticator authenticator = new ModularRealmAuthenticator();
//		为权鉴鉴定器指定鉴定策略
		authenticator.setAuthenticationStrategy(myAuthenticationStrategy);
//		创建授权器
		ModularRealmAuthorizer authorizer = new ModularRealmAuthorizer();
//		创建权限解析规则
		PermissionResolver permissionResolver = new MyPermissionResolver();
//		为 授权器 指定 权限解析规则
		authorizer.setPermissionResolver(permissionResolver);
//		创建角色权限解析规则
		RolePermissionResolver rolePermissionResolver = new MyRolePermissionResolver();
//		为授权器 指定 角色权限解析规则
		authorizer.setRolePermissionResolver(rolePermissionResolver);
//		创建一个普通的安全数据检验实体
		Realm myrealm = new MyRealm();
//		创建一个在检验通过时会为subject授予角色和权限的安全数据检验实体
		Realm myAuthorizingRealm = new MyAuthorizingRealm();
//		为安全管理器指定权鉴 鉴定器
		securityManager.setAuthenticator(authenticator);
//		为安全管理器指定授权器
		securityManager.setAuthorizer(authorizer);
//		为安全管理器设置安全数据检验实体
		securityManager.setRealms(Arrays.asList(myAuthorizingRealm,myrealm));
		
		SecurityUtils.setSecurityManager(securityManager);
		
		Subject subject = SecurityUtils.getSubject();
		
		UsernamePasswordToken token  = new UsernamePasswordToken();
		token.setUsername("guest");
		token.setPassword("guest".toCharArray());
		
		try {
			subject.login(token);
			System.out.println("login success!");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("login failed!");
			e.printStackTrace();
		}
		
		if(subject.hasRole("admin")){
			System.out.println("has role admin");
		}
		if(subject.isPermitted("image:delete")){
			System.out.println(subject.getPrincipal() +" is permitted  image:delete");
		}
		if(subject.isPermitted("image:insert")){
			System.out.println(subject.getPrincipal() +" is permitted  image:insert");
		}
		
	}
	@After
	public void windUp(){
		ThreadContext.unbindSubject();
	}
}
