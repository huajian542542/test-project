package com.jt.sys.service;



import com.jt.common.vo.PageObject;
import com.jt.sys.entity.SysConfig;

public interface SysConfigService {
	/**
     * 通过此方法实现分页查询操作
     * @param name 基于条件查询时的参数名
     * @param pageCurrent 当前的页码值
     * @return 当前页记录+分页信息
     */

	PageObject<SysConfig> findPageObjects(String name,Integer pageCurrent);
	
	//执行删除操作是
	int deleteObjects(Integer...ids);
	/**
	 * 将配置信息写入数据库
	 * @param entity
	 * @return
	 */
	int saveObject(SysConfig entity);
	int updateObject(SysConfig entity);
}
