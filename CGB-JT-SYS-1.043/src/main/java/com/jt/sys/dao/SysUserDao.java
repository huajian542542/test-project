package com.jt.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jt.sys.entity.SysUser;
import com.jt.sys.vo.SysUserDeptResult;

public interface SysUserDao {
	/**
	 * 定义基于id查询有多少个员工的方法
	 * @param DeptId
	 * @return
	 */
	int getUserCountByDeptId (Integer DeptId);
	
	List<SysUserDeptResult> findPageObjects(
			@Param("username") String username,
			@Param("startIndex")Integer startIndex,
			@Param("pageSize")Integer pageSize);
	
	int getRowCount(@Param("username") String username);
	/**
	 * 基于用户id禁用或启用
	 * @param id
	 * @param valid （1表示启用，0表示禁用）
	 * @param modifiedUser
	 * @return
	 */
	int validById(
			@Param("id")Integer id,
			@Param("valid")Integer valid,
			@Param("modifiedUser")String modifiedUser);
	/**
	 * 负责将用户信息写入数据库
	 * @param entity
	 * @return
	 */
	int insertObject(SysUser entity);
	
	/**
	 * 基于id查询用户信息
	 * @param id
	 * @return
	 */
	SysUserDeptResult findObjectById(Integer id);
	/**
	 * 用户页面更新
	 * @param user
	 * @return
	 */
	int updateObject(SysUser user);	
	/**
	 * 根据用户名查询用户对象的方法shiro
	 * @param username
	 * @return
	 */
	SysUser findUserByUserName(String username);
}
