package com.jt.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jt.sys.entity.SysConfig;

public interface SysConfigDao {
	List<SysConfig> findPageObjects(
			@Param("name")String name,
			@Param("startIndex")Integer startIndex,
			@Param("pageSize")Integer pageSize );
	
	int getRowCount(@Param("name")String name);
	//执行删除操作是
	int deleteObjects(@Param("ids") Integer...ids);
	/**
	 * 将配置信息写入数据库
	 * @param entity
	 * @return
	 */
	int insertObject(SysConfig entity);
	/**
	 * 更新配置信息
	 * @param entity 封装配置信息
	 * @return 表示更新的行数
	 */
	int updateObject(SysConfig entity);
	
	
	
}
