package com.github.zhangkaitao.shiro.chapter3.mine.union2_3;

import java.util.Collection;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.pam.AbstractAuthenticationStrategy;
import org.apache.shiro.authc.pam.AuthenticationStrategy;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.util.CollectionUtils;
/**
 * 最多允许一个Realm验证成功
 * @author admin
 *
 */
public class MyAuthenticationStrategy extends AbstractAuthenticationStrategy{

	@Override
	public AuthenticationInfo afterAllAttempts(AuthenticationToken token, AuthenticationInfo aggregate)
			throws AuthenticationException {
		if(		aggregate == null 
			||	CollectionUtils.isEmpty(aggregate.getPrincipals()) ){
			throw new AuthenticationException("Authentication token could not be authenticated by any of Realms");
		}
		else if( aggregate.getPrincipals().getRealmNames().size()>=2 ){
			throw new 
			AuthenticationException("Authentication token can't be authenticated correctly by more than 1 Realm");
		}
			
			
		return aggregate;
	}

	@Override
	public AuthenticationInfo afterAttempt(Realm realm, AuthenticationToken token, AuthenticationInfo singleRealmInfo,
			AuthenticationInfo aggregateInfo, Throwable t) throws AuthenticationException {
		// TODO Auto-generated method stub
		AuthenticationInfo info;
		if(singleRealmInfo == null){
			info = aggregateInfo;
		}else{
			if ( aggregateInfo == null) {
				info = singleRealmInfo;
			} else {
				info = merge(singleRealmInfo, aggregateInfo);
			}
		}
		return info;
	}

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

}
