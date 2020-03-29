package com.jt.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jt.common.vo.CheckBox;
import com.jt.sys.entity.SysRole;

public interface SysRoleDao {
	/**
     * 分页查询角色信息
     * @param startIndex 上一页的结束位置
     * @param pageSize 每页要查询的记录数
     * @return
     */
	List<SysRole> findPageObjects(
             @Param("name")String name,
			@Param("startIndex")Integer startIndex,
			@Param("pageSize")Integer pageSize);
	/**
	 * 查询记录总数
	 * @return
	 */
	int getRowCount(@Param("name")String name);
	/**
	 * 角色删除模块
	 * @param id
	 * @return
	 */
	int deleteObject(Integer id);
	/**
	 * 角色添加
	 */
	int insertObject(SysRole entity);
	
	/**
	 * 角色修改页面呈现
	 * @param id
	 * @return
	 */
	SysRole findObjectById(Integer id);
	/**
	 * 角色修改操作实现
	 * @param entity
	 * @return
	 */
	int updateObject(SysRole entity);
	List<CheckBox> findObjects();
}
