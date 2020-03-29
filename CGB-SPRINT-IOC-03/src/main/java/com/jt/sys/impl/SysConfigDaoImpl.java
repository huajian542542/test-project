package com.jt.sys.impl;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.jt.sys.dao.SysConfigDao;
import com.jt.sys.entity.SysConfig;

public class SysConfigDaoImpl implements SysConfigDao {
	private SqlSessionFactory sqlSessionFactory;
	
	
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}


	
	public SysConfig findById(Integer id) {
		//1.创建SqlSession对象
		SqlSession session = sqlSessionFactory.openSession();
		//2.获取dao对象
		//SysConfigDao dao = session.getMapper(SysConfigDao.class);
		String state = "com.jt.sys.dao.SysConfigDao.findById";
		//3.执行查询操作
		SysConfig config = session.selectOne(state,id);
		//4.释放资源
		session.close();
		//返回结果
		return config;
	}

}
