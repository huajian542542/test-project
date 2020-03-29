package com.jt.sys.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jt.common.vo.Node;
import com.jt.sys.entity.SysMenu;

public interface SysMenuDao {
	/**
	 * 显示菜单
	 * @return
	 */
	List<Map<String,Object>> findObjects();
	 /**
	  * 根据菜单id统计子菜单的个数
	  * @param id
	  * @return
	  */
	 int getChildCount(Integer id);
	 /**
	  * 根据id 删除菜单
	  * @param id
	  * @return
	  */
	 int deleteObject(Integer id);

	 List<Node> findZtreeMenuNodes();
	 
	 int insertObject(SysMenu entity);
	 
	 int updateObject(SysMenu entity);
	 /**
	  * 在SysMenuDao中基于菜单id查找权限标识信息
	  * @param menuIds
	  * @return
	  */
	 List<String> findPermissions(@Param("menuIds") Integer...menuIds);
}
