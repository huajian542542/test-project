package com.jt.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.vo.JsonResult;
import com.jt.sys.entity.SysMenu;
import com.jt.sys.service.SysMenuService;
/**
 * 完成菜单页面的增删改操作
 * @author 李华健
 */

@Controller
@RequestMapping("/menu/")
public class SysMenuController {
	@Autowired
	private SysMenuService sysMenuService;
	@RequestMapping("doMenuListUI")
	public String doMenuListUI() {
		return "sys/menu_list";
	}
	/**
	 * 处理添加菜单操作
	 * @param entity
	 * @return
	 */
	@RequestMapping("doSaveObject")
	@ResponseBody
	public JsonResult doSaveObject(SysMenu entity){
		sysMenuService.saveObject(entity);
		return new JsonResult("save ok");
	}
	@RequestMapping("doFindZtreeMenuNodes")
	@ResponseBody
	public JsonResult doFindZtreeMenuNodes() {
		return new JsonResult(sysMenuService.findZtreeMenuNodes());
	}
	/**
	 * 删除菜单操作
	 * @param id
	 * @return
	 */
	@RequestMapping(value="doDeleteObject",//method=RequestMethod.POST,
			produces="application/json;charset=utf-8")
	@ResponseBody
	public JsonResult doDeleteObject(Integer id) {
		//System.out.println("id:"+id);
		sysMenuService.deleteObject(id);
		return new JsonResult("delete ok");
	}
	/**
	 * 菜单修改
	 * @return
	 */
	 @RequestMapping("doMenuEditUI")
	 public String doMenuEditUI(){
		 return "sys/menu_edit";
	 }
	@RequestMapping("doFindObjects")
	@ResponseBody
	public JsonResult doFindObjects() {
		return new JsonResult(sysMenuService.findObjects());
	}
	@RequestMapping("doUpdateObject")
	@ResponseBody
	public JsonResult doUpdateObject(SysMenu entity) {
		sysMenuService.updateObject(entity);
		return new JsonResult("update ok");
	}
}
