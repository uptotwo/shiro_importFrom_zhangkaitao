package com.github.zhangkaitao.shiro.chapter2.mine;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Assert;
import org.junit.Test;

public class ShiroTest {
	
	public void testHelloWorld(){
//		获取SecurityManager工厂，此处使用ini配置文件初始化SecurityManager
		Factory<org.apache.shiro.mgt.SecurityManager>  factory = new IniSecurityManagerFactory("classpath:shiro.ini");
//	获得SecurityManager并绑定给SecurityUtils 
		org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();
		SecurityUtils.setSecurityManager(securityManager);
//		得到subject及创建用户名，密码，身份验证token
		Subject subject  = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken("li","235");
		try {
			subject.login(token);
			System.out.println("loginSuccess");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("loginFailed");
		}
		Assert.assertEquals(true, subject.isAuthenticated());
		subject.logout();
	}
}
