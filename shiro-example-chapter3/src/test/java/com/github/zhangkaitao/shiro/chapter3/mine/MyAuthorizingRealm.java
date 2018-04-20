package com.github.zhangkaitao.shiro.chapter3.mine;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

public class MyAuthorizingRealm  extends AuthorizingRealm{

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// TODO Auto-generated method stub
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		
		authorizationInfo.addRole("dragon");
		
		authorizationInfo.addStringPermission("not_dragon_but_I_authorized:dol");
		
		return authorizationInfo;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		// TODO Auto-generated method stub
		String username = (String) token.getPrincipal();
		String psd = new String( (char[])token.getCredentials() );
		
		if (username .equals("dragon" ) && psd.equals("dragonpsd") ) {
			return new SimpleAuthenticationInfo(username,psd,getName());
		}else{
			throw new AuthenticationException("username or psd is not correct!");
		}
	}

}
