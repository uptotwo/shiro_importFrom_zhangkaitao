package com.github.zhangkaitao.shiro.chapter2.mine;

import java.util.Collection;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.pam.AbstractAuthenticationStrategy;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.util.CollectionUtils;
/**
 * 该策略需要至少两个Realm验证成功
 */
public class MyAuthenticatorStrategy extends AbstractAuthenticationStrategy{
	
	@Override
	public AuthenticationInfo beforeAllAttempts(Collection<? extends Realm> realms, AuthenticationToken token)
			throws AuthenticationException {
		// TODO Auto-generated method stub
		
		return new SimpleAuthenticationInfo();
	}
	
	@Override
	public AuthenticationInfo beforeAttempt(Realm realm, AuthenticationToken token, AuthenticationInfo aggregate)
			throws AuthenticationException {
		// TODO Auto-generated method stub
		return aggregate;
	}
	
	@Override
	public AuthenticationInfo afterAttempt(Realm realm, AuthenticationToken token, AuthenticationInfo singleRealmInfo,
			AuthenticationInfo aggregateInfo, Throwable t) throws AuthenticationException {
		// TODO Auto-generated method stub
		AuthenticationInfo info;
		
		if (singleRealmInfo == null) {
			info = aggregateInfo;
		} else {
			if (aggregateInfo == null) {
				info = singleRealmInfo;
			} else {
				info = merge(singleRealmInfo,aggregateInfo);
			}
		}
		return info;
	} 
	
	@Override
	public AuthenticationInfo afterAllAttempts(AuthenticationToken token, AuthenticationInfo aggregate)
			throws AuthenticationException {
		// TODO Auto-generated method stub
		if (aggregate == null 
				|| CollectionUtils.isEmpty( aggregate.getPrincipals() )
				||aggregate.getPrincipals().getRealmNames().size()<2 ) {
			throw new AuthenticationException("Authentication token of type ["
				+token.getClass()+"]"+"could not be authenticated by any configed realms"
				+"Please ensure that at least two realms can authenticate these tokens");
		}
		return aggregate;
	}
	
	
}
