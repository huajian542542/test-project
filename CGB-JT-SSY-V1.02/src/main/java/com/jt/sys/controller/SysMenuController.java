package com.jt.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.vo.JsonResult;
import com.jt.sys.service.SysMenuService;

@Controller
@RequestMapping("/menu/")
public class SysMenuController {
	@Autowired
	private SysMenuService sysMenuService;
	
	@RequestMapping("doFindObjects")
	 @ResponseBody
	 public JsonResult doFindObjects(){
		 return new JsonResult(sysMenuService.findObjects());
	 }

	
	
	@RequestMapping("doMenuListUI")
	public String doMenuListUI() {
		return "sys/menu_list"; 
	}
	
	
}
