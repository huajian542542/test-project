package com.jt.sys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.jt.common.vo.PageObject;
import com.jt.common.vo.ServiceException;
import com.jt.sys.dao.SysConfigDao;
import com.jt.sys.entity.SysConfig;
import com.jt.sys.service.SysConfigService;
@Service
public class SysConfigServiceImpl implements SysConfigService{
	@Autowired
	private SysConfigDao sysConfigDao;
	
	/**
	 * 基于删除参数业务进行操作
	 */
	@Override
	public int deleteObjects(Integer...ids) {
		//1.判断参数的合法性
		if(ids==null || ids.length==0) 
			throw new IllegalArgumentException("请选择一个");
		//2.执行删除操作
		int rows;
		try {
		rows=sysConfigDao.deleteObjects(ids);
		}catch(Throwable e) {
			e.printStackTrace();
			throw new ServiceException("系统故障，正在恢复中...");
		}
		if(rows==0) throw new ServiceException("记录不存在!!");
		
		return rows;
	}
	
	/**
	 * 查询参数配置
	 */
	@Override
	public PageObject<SysConfig> findPageObjects(String name, Integer pageCurrent) {
		//1.验证参数合法性
		  //1.1验证pageCurrent的合法性，
		  //不合法抛出IllegalArgumentException异常
		if(pageCurrent==null ||pageCurrent<1) 
			throw new IllegalArgumentException("当前页码不正确!!");
		 //2.基于条件查询总记录数
		  //2.1) 执行查询
		int rowCount = sysConfigDao.getRowCount(name);
		//2.2) 验证查询结果，假如结果为0不再执行如下操作
		if(rowCount==0) throw new ServiceException("系统没有查得到对应的记录");
		//3.基于条件查询当前页记录(pageSize定义为2)
		  //3.1)定义pageSize
		int pageSize=3;
		 //3.2)计算startIndex
		int startIndex=(pageCurrent-1)*pageSize;
		//3.3)执行当前数据的查询操作
		List<SysConfig> records=sysConfigDao.findPageObjects(name, startIndex, pageSize);
		 //4.对分页信息以及当前页记录进行封装
		  //4.1)构建PageObject对象
		PageObject<SysConfig> pageObject=
				  new PageObject<>();
		pageObject.setPageCurrent(pageCurrent);
		pageObject.setPageSize(pageSize);
		pageObject.setRecords(records);
		pageObject.setRowCount(rowCount);
		return pageObject;
	}

	@Override
	public int saveObject(SysConfig entity) {
		//1.验证合法性
		if(entity==null) 
			throw new IllegalArgumentException("保存对象不能为空");
		if(StringUtils.isEmpty(entity.getName()))
			throw new IllegalArgumentException("参数名不能为空");
		if(StringUtils.isEmpty(entity.getValue()))
			throw new IllegalArgumentException("参数值不能为空");
		//2.将数据写入到数据库
		int rows;
		try {
			rows=sysConfigDao.insertObject(entity);
		}catch(Throwable e) {
			e.printStackTrace();
			throw new ServiceException("系统故障，正在恢复");
		}
		if(rows==0) throw new ServiceException("save error");
		return rows;
	}
	/**
	 * 更新数据
	 */
	@Override
	public int updateObject(SysConfig entity) {
		if(entity==null) 
			throw new IllegalArgumentException("对象不能为空");
		if(StringUtils.isEmpty(entity.getName()))
			  throw new IllegalArgumentException("参数名不能为空");
			  if(StringUtils.isEmpty(entity.getValue()))
			  throw new IllegalArgumentException("参数值不能为空");
			  int rows=sysConfigDao.updateObject(entity);
			  if(rows==0) 
				  throw new ServiceException("此记录可能已经不存在");
			  
		return rows;
	}

	



	
}
