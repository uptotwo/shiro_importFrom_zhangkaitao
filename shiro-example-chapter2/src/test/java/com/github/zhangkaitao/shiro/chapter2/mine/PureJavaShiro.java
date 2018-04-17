package com.github.zhangkaitao.shiro.chapter2.mine;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.junit.Test;

import com.alibaba.druid.pool.DruidDataSource;

import junit.framework.Assert;

public class PureJavaShiro {
	@Test
	public void pureTest1() {
		
		DefaultSecurityManager securityManager = new DefaultSecurityManager();
		
		/*
		 * JdbcRealm会默认的寻找所配置的库中的users表中的username和password
		 */
		JdbcRealm jdbcRealm = new JdbcRealm();
		
		DruidDataSource druidDatasource = new DruidDataSource();
		
		druidDatasource.setDriverClassName("com.mysql.jdbc.Driver");
		druidDatasource.setUrl("jdbc:mysql://localhost:3306/shiro");
		druidDatasource.setUsername("root");
		druidDatasource.setPassword("123456");
		
		jdbcRealm.setDataSource(druidDatasource);
		securityManager.setRealm(jdbcRealm);
		
		SecurityUtils.setSecurityManager(securityManager);
		Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123");

        try {
            //4、登录，即身份验证
            subject.login(token);
            System.out.println("success");
        } catch (AuthenticationException e) {
            //5、身份验证失败
        	System.out.println("failed");
            e.printStackTrace();
            
        }

        Assert.assertEquals(true, subject.isAuthenticated()); //断言用户已经登录

        //6、退出
        subject.logout();
//        ThreadContext.unbindSubject();//退出时请解除绑定Subject到线程 否则对下次测试造成影响
	}
}
