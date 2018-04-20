package com.github.zhangkaitao.shiro.chapter2.mine;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.pam.AllSuccessfulStrategy;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.authc.pam.FirstSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.authz.Authorizer;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.github.zhangkaitao.shiro.chapter2.authenticator.strategy.AtLeastTwoAuthenticatorStrategy;
import com.github.zhangkaitao.shiro.chapter2.authenticator.strategy.OnlyOneAuthenticatorStrategy;
import com.github.zhangkaitao.shiro.chapter2.realm.MyRealm1;
import com.github.zhangkaitao.shiro.chapter2.realm.MyRealm2;
import com.github.zhangkaitao.shiro.chapter2.realm.MyRealm3;
import com.github.zhangkaitao.shiro.chapter2.realm.MyRealm4;

import junit.framework.Assert;

public class PureJavaAuthenticator {
	
	DefaultSecurityManager securityManager;
	
	ModularRealmAuthenticator authenticator;
	
	@Before
	public void setPreCondition(){
		securityManager = new DefaultSecurityManager();
		authenticator = new ModularRealmAuthenticator();
	}
	
	
	/**
	 * 测试AllSuccessfulStrategy,该策略只有在指定的Realm全部验证通过时，才算验证成功
	 */
//	@Test
	public void testAllSuccessfulStrategyWithSuccess(){
		
		authenticator.setAuthenticationStrategy(new AllSuccessfulStrategy());
		
		securityManager.setAuthenticator(authenticator);
		
		List<Realm> realms = new ArrayList<Realm>();
		realms.add(new MyRealm1());
		realms.add(new MyRealm3());
		
		/*
		 * setAuthenticator 必须在setRealms之前，否则会出现异常，系统将提示No realms have been configed!
		 * 并且在有多个Realm时，须使用setRealms(Collection<Realm>)方法，而若多次调用setRealm(Realm)方法，
		 * 只有最后一次会生效
		 */
		
		securityManager.setRealms(realms);
		
		SecurityUtils.setSecurityManager(securityManager);
		
		AuthenticationToken token = new UsernamePasswordToken("zhang","123");
		
		Subject subject = SecurityUtils.getSubject();
		
		

		try {
			subject.login(token);
			System.out.println("success");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("failed");
			// TODO: handle exception
		}
		
			
			PrincipalCollection principalCollection = subject.getPrincipals();
			
			Assert.assertEquals(2, principalCollection.asList().size());
			
			subject.logout();
	}
	
	
	/**
	 * 测试AtleastOneSuccessfulStrategy,该策略只要有一个Realm验证通过就认为验证成功
	 */
//	@Test
	public void testAtleastOneSuccessfulStrategyWithSuccess(){
		
		authenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());
		securityManager.setAuthenticator(authenticator);
		
		List<Realm> realmList = new ArrayList<Realm>();
		realmList.add(new MyRealm1());
		realmList.add(new MyRealm2());
		realmList.add(new MyRealm3());
		
		securityManager.setRealms(realmList);
		
		SecurityUtils.setSecurityManager(securityManager);
		
		Subject subject = SecurityUtils.getSubject();
		
		UsernamePasswordToken token = new UsernamePasswordToken("zhang","123");
		
		try {
			subject.login(token);
			System.out.println( "success");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("failed");
		}
		
		PrincipalCollection princepalCollection = subject.getPrincipals();
		Assert.assertEquals(2, princepalCollection.asList().size());
		
	}
	
	
	/*
	 * FirstSuccessfulStrategy,该策略只要有一个Realm验证通过就认为验证通过，只在subject.getPrincipals()获得的Collection中存储
	 * 第一个验证通过的Realm
	 */
//	@Test
	public void testFirstSuccessfulStrategyWithSuccess(){
		
		authenticator.setAuthenticationStrategy(new FirstSuccessfulStrategy());
		securityManager.setAuthenticator(authenticator);
		
		List<Realm> realms = new ArrayList<Realm>();
		realms.add(new MyRealm1());
		realms.add(new MyRealm2());
		realms.add(new MyRealm3());
		
		securityManager.setRealms(realms);
		
		SecurityUtils.setSecurityManager(securityManager);
		
		Subject subject = SecurityUtils.getSubject();
		
		UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("zhang", "123");
		
		try {
			subject.login(usernamePasswordToken);
			System.out.println("success");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("failed");
		}
//		此处的PrincipalCollection中只存储第一个验证成功的Realm
		PrincipalCollection principleCollection = subject.getPrincipals();
		System.out.println(principleCollection.asList().get(0));
		
		subject.logout();
	}
	
	
	
	/**
	 * 测试AtLeastTwoAuthenticationStrategy,该策略要求所有Realm至少两个验证成功，
	 * Subject.getPrincipals()获得的Collection包含所有验证通过的Realm
	 */
//	@Test
	public void testAtLeastTwoStrategyWithSuccess(){
		
		authenticator.setAuthenticationStrategy(new AtLeastTwoAuthenticatorStrategy());
		
		securityManager.setAuthenticator(authenticator);
		
		List<Realm> realms = new ArrayList<Realm>();
		realms.add(new MyRealm1());
		realms.add(new MyRealm2());
		realms.add(new MyRealm3());
		realms.add(new MyRealm4());
		
		securityManager.setRealms(realms);
		
		SecurityUtils.setSecurityManager(securityManager);
		
		Subject subject = SecurityUtils.getSubject();
		
		UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("zhang","123");
		
		try {
			subject.login(usernamePasswordToken);
			System.out.println("success");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("failed");
		}
		
		PrincipalCollection principals = subject.getPrincipals();
		
		List<?> principalList = principals.asList();
		
		System.out.println(principalList.size());
		
		for(int i = 0;i<principalList.size();i++){
			System.out.println(principalList.get(i));
		}
	}
	
	
	/**
	 * 测试OnlyOneStrategy,该策略只有在一个Realm验证成功时认为验证通过，
	 * 没有或 多个Realm验证成功均认为验证未通过
	 */
	@Test
	public void testOnlyOneStrategyWithSuccess(){
		
		authenticator.setAuthenticationStrategy(new OnlyOneAuthenticatorStrategy());
		
		securityManager.setAuthenticator(authenticator);
		
		List<Realm> realms = Arrays.asList(new MyRealm1(),new MyRealm2(),new MyRealm3());
		
		securityManager.setRealms(realms);
		
		SecurityUtils.setSecurityManager(securityManager);
		
		Subject subject = SecurityUtils.getSubject();
		
		UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("zhang","123");
		
		try {
			
			subject.login(usernamePasswordToken);
			
			System.out.println("success");
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("failed");
			
		}
		
		PrincipalCollection principals = subject.getPrincipals();
		
		for(int  i = 0 ;i<principals.asList().size();i++){
			System.out.println(principals.asList().get(i));
		}
	}
	
	
	
	
	@After
	public void windUp(){
		ThreadContext.unbindSubject();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
