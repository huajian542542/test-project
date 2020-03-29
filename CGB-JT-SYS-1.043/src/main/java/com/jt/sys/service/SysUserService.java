package com.jt.sys.service;

import java.util.Map;

import com.jt.common.vo.PageObject;
import com.jt.sys.entity.SysUser;
import com.jt.sys.vo.SysUserDeptResult;

public interface SysUserService {
	/**
	 * 分页查询用户以及对应的部门信息
	 * @param username 用户名
	 * @param pageCurrent 当前页码值
	 * @return
	 */
	PageObject<SysUserDeptResult> findPageObjects(
			String username, 
			Integer pageCurrent);
/**
 * 启用和禁用用户的权限
 * @param id
 * @param valid
 * @param modifiedUser
 * @return
 */
	int    validById(Integer id,Integer valid,String modifiedUser);
	/**
	 * 保存用户角色的关系数据
	 * @param entity
	 * @param roleIds
	 * @return
	 */
	int saveObject(SysUser entity,Integer[] roleIds);
	/**
	 * 基于用户id查询用户和角色信息
	 * @param userId
	 * @return
	 */
	Map<String,Object> findObjectById(Integer userId);
	/**
	 * 更新用户信息
	 * @param entity 用户对象
	 * @param roleIds 用户对应的角色信心
	 * @return
	 */
	int updateObject(SysUser entity,Integer[] roleIds);
	
	
}
