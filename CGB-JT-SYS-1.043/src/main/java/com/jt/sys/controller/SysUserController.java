package com.jt.sys.controller;


import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.vo.JsonResult;
import com.jt.common.vo.PageObject;
import com.jt.sys.entity.SysUser;
import com.jt.sys.service.SysUserService;
import com.jt.sys.vo.SysUserDeptResult;
@Controller
@RequestMapping("/user/")
public class SysUserController {
	@Autowired//缺少@Autowired 会导致controller无法获取数据
	private SysUserService sysUserService;

	@RequestMapping("doValidById")
	@ResponseBody
	public JsonResult doValidById(Integer id,Integer valid) {
		///用户登录成功后自动将信息存放session
		SysUser user =(SysUser)
				SecurityUtils.getSubject().getPrincipal();
		sysUserService.validById(id, valid, user.getUsername());
		return new JsonResult("update ok");
	}
	/**
	 * 用户添加页面
	 * @return
	 */
	@RequestMapping("doUserEditUI")
	public String doUserEditUI(){
		return "sys/user_edit";
	}
	@RequestMapping(value="doFindObjectById",produces="text/html;charset=utf8")
	@ResponseBody
	public JsonResult doFindObjectById(Integer userId) {

		System.out.println("controller:"+userId);
		return new JsonResult(sysUserService.findObjectById(userId));
	}


	@RequestMapping("doSaveObject")
	@ResponseBody
	public JsonResult doSaveObject(SysUser entity,Integer[] roleIds){
		SysUser user =(SysUser) SecurityUtils.getSubject().getPrincipal();
		entity.setModifiedUser(user.getUsername());
		entity.setCreatedUser(user.getUsername());
		sysUserService.saveObject(entity,roleIds);
		return new JsonResult("save ok");
	}
	@RequestMapping("doUpdateObject")
	@ResponseBody
	public JsonResult doUpdateObject(
			SysUser entity,
			Integer[] roleIds){
		sysUserService.saveObject(entity,roleIds);
		return new JsonResult("Update ok");
	}

	/**
	 * 返回用户列表页面
	 * @return
	 */
	@RequestMapping("doUserListUI")
	public String doUserListUI() {
		return "sys/user_list";
	}
	@RequestMapping("doFindPageObjects")
	@ResponseBody
	public JsonResult doFindPageObjects(String username,Integer pageCurrent){
		PageObject<SysUserDeptResult> pageObject=
				sysUserService.findPageObjects(username,pageCurrent);
		System.out.println(pageObject.toString());
		return new JsonResult(pageObject);
	}


}
