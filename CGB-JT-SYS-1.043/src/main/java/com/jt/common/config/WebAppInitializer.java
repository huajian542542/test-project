package com.jt.common.config;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
/**
 * 注解启动类，
 * @author 86188
 *
 */
public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer{
	/**
	 * 负责加载service,dao
	 */
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] {AppRootConfig.class,AppShiroConfig.class};
	}
	/**
	 * 此方法负责加载spring MVC组件
	 */
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] {AppServletConfig.class};
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] {"*.do"};
	}
	/**重写onStartup方法，完成过滤器的注册*/
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		//注册监听器
		registerContextLoaderListener(servletContext);
		//注册shiro过滤器
		registerShiroFilter(servletContext);
		//调用父类方法
		registerDispatcherServlet(servletContext);
		System.out.println("onStartup方法，完成过滤器的注册");
	}
	/**
	 * 注册核心过滤器
	 * @param servletContext
	 */
	private void registerShiroFilter(ServletContext servletContext) {
		//注册Filter对象
				//什么时候需要采用此方式进行注册?
				//项目没有web.xml并且此filter不是自己写的
		FilterRegistration.Dynamic dy = servletContext.addFilter("filterProxy", DelegatingFilterProxy.class);
		//注册过滤器
		dy.setInitParameter("targetBeanName", "shiroFilterFactoryBean");//配置Shiro的ShiroFilterFactoryBean工厂Bean名称相同
		//映射过滤器
		dy.addMappingForUrlPatterns(null,//EnumSet<DispatcherType>
				false,"/*");//url-pattern
		System.out.println("注册核心过滤器");
	}
}
