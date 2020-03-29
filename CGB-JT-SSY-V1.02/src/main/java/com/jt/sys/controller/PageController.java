package com.jt.sys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class PageController {
	@RequestMapping("doIndexUI")
	public String doIndexUI() {
		return "starter";
	}
	/*用于返回分页页面*/
	@RequestMapping("doPageUI")
	public String doPageUI(){
			  return "common/page";
	 }

}
