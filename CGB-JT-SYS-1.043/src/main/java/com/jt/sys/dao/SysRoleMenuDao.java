package com.jt.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface SysRoleMenuDao {
int deleteObjectsByMenuId(Integer menuId);
	
		
	/**
	 * 角色删除，修改魔块 
	 * int deleteObjectsByRoleId(Integer roleId);
	 */
	 int deleteObjectsByRoleId(Integer roleId);
	 /**
	  * 在SysRoleMenuDao中基于用户id查找菜单id信息
	  * @param roleIds
	  * @return
	  */
	 List<Integer> findMenuIdsByRoleId(@Param("roleIds") Integer...roleIds);
	/**
	 * 角色添加页面操作 保存
	 * @param roleId
	 * @param menuIds
	 * @return
	 */
	 int insertObject(
			@Param("roleId")Integer roleId,
			@Param("menuIds")Integer[] menuIds);
	 /**
	  * 角色修改页面 呈现 操作
	  * @param roleId
	  * @return
	  */
	 List<Integer> findMenuIdsByRoleIds(Integer roleId);
}
