package com.github.zhangkaitao.shiro.chapter3.mine.union2_3;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.realm.Realm;
/**
 * 用户名和密码都是guest时验证通过
 * @author admin
 *
 */
public class MyRealm implements Realm{

	@Override
	public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		// TODO Auto-generated method stub
		String username = (String)token.getPrincipal();
		String password = new String((char[]) token.getCredentials());
		if (!username.equals("guest")) {
			
			throw new AuthenticationException("username is not guest!");
		}
		if(!password.equals("guest")){
			
			throw new AuthenticationException("password is not guest!");
		}
			
		return new SimpleAuthenticationInfo(username,password,getName());
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "myRealm";
	}

	@Override
	public boolean supports(AuthenticationToken token) {
		// TODO Auto-generated method stub
		return token instanceof UsernamePasswordToken; //仅支持UsernamePasswordToken类型的Token;
	}
	
}
