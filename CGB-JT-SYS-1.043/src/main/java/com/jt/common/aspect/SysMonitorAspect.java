package com.jt.common.aspect;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Order(1) //多个切面时，定义切面，执行顺序，数字越小优先级越高
@Service
@Aspect
public class SysMonitorAspect {
	@Pointcut("bean(*ServiceImpl)")
	public void monitorPointCut() {}
	
	/**前置通知*/
	@Before("monitorPointCut()")
	public void beforeMethod() {
			System.out.println("beforeMethod");
	}
	/** 返回通知，出异常时，没有返回*/
	@AfterReturning("monitorPointCut()")
	public void returnmethod() {
		System.out.println("returnmethod");
	}
	/**异常通知，出异常时调用*/
	@AfterThrowing("monitorPointCut()")
	public void returnThrowing() {
		System.out.println("returnThrowing");
		/*系统出现异常以后，可以在此位置进行报警，监控*/
	}
	/**后置通知*/
	@After("monitorPointCut()")
	public void afterMethod() {
		System.out.println("afterMethod");
	}
	
	
	
	
	
	
}
