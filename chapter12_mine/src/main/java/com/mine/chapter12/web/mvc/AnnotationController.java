package com.mine.chapter12.web.mvc;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 14-2-12
 * <p>Version: 1.0
 */
@Controller
public class AnnotationController {

    @RequestMapping("/hello1")
    public String hello1() {
    	try {
    		 SecurityUtils.getSubject().checkRole("admin");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
       System.out.println("sdf");
        return "success";
    }

    @RequiresRoles("admin")
    @RequestMapping("/hello2")
    public String hello2() {
        return "success";
    }
}
