package com.jt.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.vo.JsonResult;
import com.jt.common.vo.PageObject;
import com.jt.sys.entity.SysConfig;
import com.jt.sys.service.SysConfigService;

@Controller
@RequestMapping("/config/")
public class SysConfigController {
	@Autowired
	private SysConfigService sysConfigService;

	/**
	 * 处理添加参数操作
	 * @return
	 */
	@RequestMapping("doConfigEditUI")
	public String doConfigEditUI (){
		return "sys/config_edit";
	}
	/**
	 * 添加信息
	 * @param entity
	 * @return
	 */
	@PostMapping(value="doSaveObject", produces="application/json;charset=utf-8")
	@ResponseBody
	public JsonResult doSaveObject(@RequestBody SysConfig entity) {
		sysConfigService.saveObject(entity);
		return new JsonResult("insert OK");
	}
	/**
	 * 处理更新操作
	 * 	@RequestBody注解
	 *  1)处理post请求，并且必须设置post请求的contnetType 
	 *  例如：客户端向服务端传递json格式的字符串，服务端需要将这个
	 *  串转能换为Java对象
	 * @param entity
	 * @return
	 */
	@PostMapping(value="doUpdateObject", produces="application/json;charset=utf-8")
	@ResponseBody
	public JsonResult doUpdateObject(@RequestBody SysConfig entity){
		System.out.println("controllerEntity:"+entity);
		sysConfigService.updateObject(entity);
		return new JsonResult("update ok");
	}
	@RequestMapping(value="doDeleteObjects/{ids}",produces="application/json;charset=utf-8")
	@ResponseBody
	public JsonResult doDeleteObjects(@PathVariable Integer... ids) {//reset 风格url
		sysConfigService.deleteObjects(ids);
		return new JsonResult("delete OK");
	}
	@RequestMapping("doConfigListUI")
	public String doConfigListUI() {
		return "sys/config_list";
	}
	@RequestMapping("doFindPageObjects")
	@ResponseBody
	public JsonResult doFindPageObjects( 
			/*@RequestParam("name")*/ String name,Integer pageCurrent) {
		System.out.println("name"+name);
		PageObject<SysConfig> pageObject=
				sysConfigService.findPageObjects(name,pageCurrent);
		return new JsonResult(pageObject);
	}
}
