package test;

import org.junit.After;
import org.junit.Before;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestBase  {
	protected  ClassPathXmlApplicationContext ctx;
	@Before
		public void init() {
		//初始化spring的IOC容器
		System.out.println("init.....");
		ctx=new ClassPathXmlApplicationContext("spring-configs.xml");
		}
	@After
	public void destory() {
		//System.out.println("destory....");
		//ctx.close();
	}
}
