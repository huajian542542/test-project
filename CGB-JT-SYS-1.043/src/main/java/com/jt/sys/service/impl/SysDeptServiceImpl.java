package com.jt.sys.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.jt.common.vo.Node;
import com.jt.common.vo.ServiceException;
import com.jt.sys.dao.SysDeptDao;
import com.jt.sys.dao.SysUserDao;
import com.jt.sys.entity.SysDept;
import com.jt.sys.service.SysDeptService;
@Service
public class SysDeptServiceImpl implements SysDeptService {
	@Autowired
	private SysDeptDao sysDeptDao;
	@Autowired
	private SysUserDao sysUserDao;
	@Override
	public List<Map<String, Object>> findObjects() {

		return sysDeptDao.findObjects();
	}
	@Override
	public int deleteObject(Integer id) {
		if(id==null || id<0) 
			throw new ServiceException("数据不合法,id="+id);
		//2.执行删除操作
		//2.1判定此id对应的菜单是否有子元素
		int childCount=sysDeptDao.getChildCount(id);
		if(childCount>0)
			throw new ServiceException("此元素有子元素，不允许删除");
		//2.2判定此部门是否有用户
		int userCount=sysUserDao.getUserCountByDeptId(id);
		if(userCount>0)
			throw new ServiceException("此部门有员工，不允许对部门进行删除");
		//2.2判定此部门是否已经被用户使用,假如有则拒绝删除
		//2.3执行删除操作
		int rows=sysDeptDao.deleteObject(id);
		if(rows==0)
			throw new ServiceException("此信息可能已经不存在");
		return rows;

	}
	@Override
	public List<Node> findZtreeDeptNodes() {

		return sysDeptDao.findZtreeDeptNodes();
	}
	@Override
	public int saveObject(SysDept entity) {
		if(entity==null) 
			throw new ServiceException("保存对象不能为空");
		if(StringUtils.isEmpty(entity.getName())) 
			throw new ServiceException("部门名不能为空");
		int rows;
		try {
			rows=sysDeptDao.insertObject(entity);
		}catch(Exception e) {
			e.printStackTrace();
			throw new ServiceException("保存失败");
		}
		return rows;
	}
	@Override
	public int updateObject(SysDept entity) {
		if(entity==null)
			throw new ServiceException("保存对象不能为空");
		if(StringUtils.isEmpty(entity.getName()))
			throw new ServiceException("部门名不能为空");
		int rows;
		//2.更新数据
		rows=sysDeptDao.updateObject(entity);
		if(rows==0)
			throw new ServiceException("记录可能已经不存在");
		//3.返回数据
		return rows;
	}

}