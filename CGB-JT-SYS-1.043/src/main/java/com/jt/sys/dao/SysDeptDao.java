package com.jt.sys.dao;

import java.util.List;
import java.util.Map;

import com.jt.common.vo.Node;
import com.jt.sys.entity.SysDept;

public interface SysDeptDao {
	List<Map<String,Object>> findObjects();
	 /**
	  * 根据部门id统计子部门的个数
	  * @param id
	  * @return
	  */
	 int getChildCount(Integer id);
	 /**
	  * 根据id 删除部门
	  * @param id
	  * @return
	  */
	 int deleteObject(Integer id);
/**
 * 部门呈现
 * @return
 */
	 List<Node> findZtreeDeptNodes();
	 /**
	  * 添加部门
	  * 
	  */
	 int insertObject(SysDept entity);
	 /**
	  * 修改部门信息
	  * @param entity
	  * @return
	  */
	 int updateObject(SysDept entity);
	 
	// int findById(SysUser entity);
}
