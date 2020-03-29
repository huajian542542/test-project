package com.jt.sys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class PageController {
	@RequestMapping("doIndexUI")
	public String doIndexUI() {
		System.out.println("启动");
		return "starter";
	}
	/*用于返回分页页面*/
	@RequestMapping("doPageUI")
	public String doPageUI(){
			  return "common/page";
	 }
	 @RequestMapping("doLoginUI")
	   public String doLoginUI(){
		 System.out.println("登录页面");
		   return "login";
	   }


}
