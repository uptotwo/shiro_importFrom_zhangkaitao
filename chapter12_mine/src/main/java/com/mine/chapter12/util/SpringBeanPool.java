package com.mine.chapter12.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
/**
 * @author Euron
 *	this type is used for fetch beans that managed by Spring
 *	this type must be registered in spring.xml
 */
public final  class SpringBeanPool implements ApplicationContextAware{
	
	private static ApplicationContext applicationContext;  

	 @Override  
	    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {  
	        	SpringBeanPool.applicationContext = applicationContext;  
	    }
	 
		 public static ApplicationContext getApplicationContext() {  
			 
		        return applicationContext;  
		    }  
		  
		 public static Object getBean(String name) {  
		        return applicationContext.getBean(name);  
		 }  
	
		 @SuppressWarnings("unchecked")
		 public static <T>  T getBean(String beanName,T sample){
				return (T) applicationContext.getBean(beanName);
		 }
}
