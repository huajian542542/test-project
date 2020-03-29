package com.jt.common.aspect;

import java.lang.reflect.Method;

import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.jt.common.annotation.RequestLog;
import com.jt.common.util.IPUtils;
import com.jt.sys.dao.SysLogDao;
import com.jt.sys.entity.SysLog;
import com.jt.sys.entity.SysUser;

/**
 * @Aspect 这个注解描述的类为一个切面对象
 * 此对象中通常要定义：
 * 1.切入点（织入扩展业务的那些点，
 * 2.通知（扩展业务）等
 * 
 * @author 86188
 *
 */
@Order(1)
@Service
@Aspect
public class SysLogAspect {
	@Autowired
	private SysLogDao sysLogDao;
	/**
	 * @Pointcut 注解用于定义切入点表达式
	 * 切入点：切入扩展功能的多个连接点的集合
	 * com.jt.common.annotation
	 * @annotation(com.jt.common.anntation.RequestLog)
	 */
	@Pointcut("bean(*ServiceImpl)")
	public void sysPointCut(){}
	/**
	 * @Around 注解修饰的方法为一个环绕对象
	 * @param point 连接点，通常指核心业务方法
	 * @return
	 */
	@Around("sysPointCut()")
		public Object around(ProceedingJoinPoint joinPoint)throws Throwable  {
		//执行目标方法
		long startTime=System.nanoTime();
				Object result = joinPoint.proceed();
				long endTime=System.nanoTime();
				/*获取用户的操作日志，并将日志信息写入到数据库 2020-03-26
				 * */
				//1.获取执行时长 totalTime
				long totalTime = endTime-startTime;
				saveSysLog(joinPoint, totalTime);
			return result;
		}
	private void saveSysLog(ProceedingJoinPoint joinPoint,long totalTime) throws Throwable, NoSuchMethodException {
		
		
		
		//2.获取当前操作用户
		SysUser user=(SysUser)SecurityUtils.getSubject().getPrincipal();
		String username = user.getUsername();
		//3.获取操作方法（哪个类的哪个方法）
		//3.1获取方法签名
		MethodSignature ms =(MethodSignature)joinPoint.getSignature();//方法的签名
		Class<?> targetCls = joinPoint.getTarget().getClass();
		System.out.println("targetCls.getName()="+targetCls.getName());//targetCls.getName()=com.jt.sys.service.impl.SysConfigServiceImpl
		Class<?>[] parameterType = ms.getParameterTypes();
		String methodName = ms.getName();
		String clsMethod = targetCls.getName()+"."+methodName;
		System.out.println(clsMethod);//com.jt.sys.service.impl.SysConfigServiceImpl.findPageObjects
		
		/**/
		//4.获取当前操作方法是传入的参数
		String args =JSON.toJSONString(joinPoint.getArgs());
		System.out.println("SysLogAspect-args="+args);
		//5.获取当前操作的说明，是什么操作
		Method metnod = targetCls.getDeclaredMethod(methodName, parameterType);
		System.out.println("m.getName()="+metnod.getName());
		RequestLog requestLog = metnod.getDeclaredAnnotation(RequestLog.class);
		String operation = "";
		if(requestLog!=null) {
			operation=requestLog.value();
		}
		//6.获取当前用户的ip地址
		String ip = IPUtils.getIpAddr();
		System.out.println("ip="+ip);
		SysLog log = new SysLog();
		//.7封装信息
		log.setIp(ip);
		log.setUsername(username);
		log.setOperation(operation);
		log.setMethod(clsMethod);
		log.setParams(args);
		log.setTime(totalTime);
		//8.将日志对象存储到数据库
		sysLogDao.insertObject(log);
				
	}
}
