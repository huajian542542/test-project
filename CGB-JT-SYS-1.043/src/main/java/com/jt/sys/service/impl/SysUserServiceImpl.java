package com.jt.sys.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.util.StringUtils;
import com.jt.common.vo.PageObject;
import com.jt.common.vo.ServiceException;
import com.jt.sys.dao.SysUserDao;
import com.jt.sys.dao.SysUserRoleDao;
import com.jt.sys.entity.SysUser;
import com.jt.sys.service.SysUserService;
import com.jt.sys.vo.SysUserDeptResult;
@Service
public class SysUserServiceImpl implements SysUserService{
	@Autowired
	private SysUserDao sysUserDao;
	@Autowired
	private SysUserRoleDao sysUserRoleDao;
	@RequiresPermissions("sys:user:update")
	@Override
	public int updateObject(SysUser entity,  Integer[] roleIds) {
		//1.合法验证
		if(entity==null)
			throw new ServiceException("用户信息不能为空");
		if(StringUtils.isEmpty(entity.getUsername()))
			throw new ServiceException("用户名不能为空");
		//用户名已经存在的验证,尝试自己实现.
		if(StringUtils.isEmpty(roleIds)) 
			throw new ServiceException("用户必须选一个角色");

		if(!StringUtils.isEmpty(entity.getPassword())){
			//对密码加密
			String salt=UUID.randomUUID().toString();
			SimpleHash hash=//shiro
					new SimpleHash(
							"MD5",
							entity.getPassword(),
							salt,1);
			entity.setSalt(salt);
			entity.setPassword(hash.toHex());
		}
		//2.更新数据
		int rows=0;
		try{
			rows=sysUserDao.updateObject(entity);
			sysUserRoleDao.deleteObjectsByUserId(entity.getId());
			sysUserRoleDao.insertObject(
					entity.getId(),roleIds);
		}catch(Throwable e){
			e.printStackTrace();
			//发起报警信息
			throw new ServiceException("服务端现在异常,请稍后访问");
		}
		//3.返回结果
		return rows;

	}


	public PageObject<SysUserDeptResult> findPageObjects(String username,Integer pageCurrent) {
		//1.合法验证
		if(pageCurrent==null || pageCurrent<1)
			throw new IllegalArgumentException("当前页码值无效");
		//2.查询总记录数
		int rowCount=sysUserDao.getRowCount(username);
		//3.对记录数进行验证
		if(rowCount==0)
			throw new ServiceException("没有记录");
		//4.查询当前页数据
		int pageSize=5;
		int startIndex=(pageCurrent-1)*pageSize;
		List<SysUserDeptResult> records=
				sysUserDao.findPageObjects(username,
						startIndex, pageSize);
		//5.对数据进行封装
		PageObject<SysUserDeptResult> po=new PageObject<>();
		po.setRowCount(rowCount);
		po.setRecords(records);
		po.setPageSize(pageSize);
		po.setPageCurrent(pageCurrent);
		po.setPageCount((rowCount-1)/pageSize+1);
		//6.返回结果
		return po;

	}
	@RequiresPermissions("sys:user:valid")
	@Override
	public int validById(Integer id, Integer valid, String modifiedUser) {
		//1.合法性验证
		if(id==null||id<=0)
			throw new ServiceException("参数不合法,id="+id);
		if(valid!=1&&valid!=0&&valid!=1)
			throw new ServiceException("参数不合法,valie="+valid);
		if(StringUtils.isEmpty(modifiedUser))
			throw new ServiceException("修改用户不能为空");
		//2.执行禁用或启用操作
		int rows=0;
		try{
			rows=sysUserDao.validById(id, valid, modifiedUser);
		}catch(Throwable e){
			e.printStackTrace();
			//报警,给维护人员发短信
			throw new ServiceException("底层正在维护");
		}
		if(rows==0)
			throw new ServiceException("此记录可能已经不存在");
		return rows;
	}
	
	@Override
	public int saveObject(SysUser entity, Integer[] roleIds) {
		//1.验证数据合法性
		if(entity==null)
			throw new IllegalArgumentException("保存对象不能为空");
		if(StringUtils.isEmpty(entity.getUsername()))
			throw new IllegalArgumentException("用户名不能为空");
		if(StringUtils.isEmpty(entity.getPassword()))
			throw new IllegalArgumentException("密码不能为空");
		if(roleIds==null || roleIds.length==0)
			throw new ServiceException("至少要为用户分配角色");
		//2.将数据写入数据库
		String salt=UUID.randomUUID().toString();
		entity.setSalt(salt);
		//加密(先了解,讲shiro时再说)
		SimpleHash sHash=
				new SimpleHash("MD5",//algorithmName加密算法
						entity.getPassword(),//source被加密的对象
						salt,//salt盐值，使用产生的随机字符
						//1024
						1);//加密次数
		entity.setSalt(salt);
		entity.setPassword(sHash.toHex());
		int rows=sysUserDao.insertObject(entity);
		sysUserRoleDao.insertObject(
				entity.getId(),
				roleIds);//"1,2,3,4";
		//3.返回结果
		return rows;
	}
	@Override
	public Map<String, Object> findObjectById(Integer userId) {
		//1.合法性验证
		System.out.println("userid:"+userId);
		if(userId == null || userId<1)
			throw new IllegalArgumentException(
					"参数数据不合法,userId="+userId);
		//2.业务查询
		SysUserDeptResult result= sysUserDao.findObjectById(userId);
		
		if(result==null)
			throw new ServiceException("此用户已经不存在");
		System.out.println("user:"+result.toString());
		List<Integer> roleIds=
				sysUserRoleDao.findRoleIdsByUserId(userId);
		System.out.println("roleIds:"+roleIds);
		//3.数据封装
		Map<String,Object> map=new HashMap<>();
		
		map.put("roleIds", roleIds);
		map.put("user", result);
		return map;
	}
}
