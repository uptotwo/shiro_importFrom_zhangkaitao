package com.github.zhangkaitao.shiro.chapter6.mine;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

import com.github.zhangkaitao.shiro.chapter6.BaseTest;
import com.github.zhangkaitao.shiro.chapter6.credentials.RetryLimitHashedCredentialsMatcher;
import com.github.zhangkaitao.shiro.chapter6.realm.UserRealm;

public class MyUserRealmTest extends BaseTest {
	
	@Test
	public void doTest1(){
		
		DefaultSecurityManager securityManager = new DefaultSecurityManager();
		
//		创建自定义的密码匹配器，继承HashedCredentialsMatcher
		RetryLimitHashedCredentialsMatcher credentialsMatcher
		= new RetryLimitHashedCredentialsMatcher();
		
		
//		设置密码匹配器的加密方法
		credentialsMatcher.setHashAlgorithmName("md5");
//		设置密码匹配器加密次数
		credentialsMatcher.setHashIterations(2);
//		设置密码匹配器是否采用16进制加密
		credentialsMatcher.setStoredCredentialsHexEncoded(true);
		
		
//		创建自定义的Realm
		UserRealm realm = new UserRealm();
		
//		为Realm设置密码匹配器
		realm.setCredentialsMatcher(credentialsMatcher);
		
		securityManager.setRealm(realm);
		
		UsernamePasswordToken token = new UsernamePasswordToken("zhang","12sdsd3");
		
		SecurityUtils.setSecurityManager(securityManager);
		
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.login(token);
			System.out.println( "success");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("failed");
			e.printStackTrace();
		}
		
	}
	
	
}
