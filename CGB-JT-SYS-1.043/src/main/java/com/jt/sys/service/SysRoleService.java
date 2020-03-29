package com.jt.sys.service;

import java.util.List;
import java.util.Map;

import com.jt.common.vo.CheckBox;
import com.jt.common.vo.PageObject;
import com.jt.sys.entity.SysRole;

public interface SysRoleService {
	 /**
     * 本方法中要分页查询角色信息,并查询角色总记录数据
     * @param pageCurrent 当表要查询的当前页的页码值
     * @return 封装当前实体数据以及分页信息
     */
	 PageObject<SysRole> findPageObjects(
			 String name,Integer pageCurrent);

	 /**
	  * 角色删除模块
	  * @param id
	  * @return
	  */
	 int deleteObject(Integer id);
	 /**
	  * 添加角色操作，保存信息
	  * @param entity
	  * @param menuIds
	  * @return
	  */
	 int saveObject(SysRole entity,Integer[] menuIds);
	 /**
	  * 角色修改页面呈现 操作
	  * 基于角色id查询角色及关联的菜单信息
	  * @param id
	  * @return
	  */
	 Map<String,Object> findObjectById(Integer id) ;
	 /**
	  * 角色修改操作
	  * @param entity
	  * @param menuIds
	  * @return
	  */
	 int updateObject(SysRole entity,Integer[] menuIds);
	 List<CheckBox> findObjects();
}
