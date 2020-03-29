package com.jt.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface SysUserRoleDao {
	/**
	 * 角色删除模块
	 * @param roleId
	 * @return
	 */
	int deleteObjectsByRoleId(Integer roleId);
	/**
	 * 负责将用户与角色的关系数据写入到数据库
	 * @param userId 用户id
	 * @param roleIds 多个角色id
	 * @return
	 */

	int insertObject(
			@Param("userId")Integer userId,
			@Param("roleIds")Integer[] roleIds);
	/**
	 * 查询用户对应角色id
	 * @param id 用户id
	 * @return
	 */
	List<Integer> findRoleIdsByUserId(Integer id);
	/**
	 * 先删除关系数据再添加
	 * @param userId
	 * @return
	 */
	int deleteObjectsByUserId(Integer userId);
	
}
