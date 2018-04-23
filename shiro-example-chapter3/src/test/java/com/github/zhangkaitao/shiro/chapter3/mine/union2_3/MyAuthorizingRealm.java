package com.github.zhangkaitao.shiro.chapter3.mine.union2_3;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
/**
 * 如果用户名和密码都是dragon，则验证通过并赋予admin权限
 */
public class MyAuthorizingRealm extends AuthorizingRealm{

	/*
	 * (non-Javadoc)
	 * @see org.apache.shiro.realm.AuthorizingRealm#doGetAuthorizationInfo(org.apache.shiro.subject.PrincipalCollection)
	 * 
	 * 这里的入参 principals我猜是包括 该Realm在内所有验证成功的Realm返回的SimpleAuthenticationInfo中的principal的合集
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SimpleAuthorizationInfo authorizationInfo;
		
		for (Object object : principals.asList()) {
			
			if(((String)object).equals("dragon")){
				
				 authorizationInfo = new SimpleAuthorizationInfo();
					
				authorizationInfo.addRole("admin");
				
				return authorizationInfo;
			}
			if(((String)object).equals("guest")){
				
				 authorizationInfo = new SimpleAuthorizationInfo();
					
				authorizationInfo.addRole("guest");
				
				return authorizationInfo;
			}
			
		}
		
		return new SimpleAuthorizationInfo();
		
	
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		// TODO Auto-generated method stub
		String username = (String)token.getPrincipal();
		String password = new String((char[])token.getCredentials());
		
		if (!username.equals("dragon")) {
			
			throw new AuthenticationException("username is not dragon!");
		}
		if(!password.equals("dragon")){
			
			throw new AuthenticationException("password is not dragon!");
		}
			
		return new SimpleAuthenticationInfo(username,password,getName());
	}
	
}
