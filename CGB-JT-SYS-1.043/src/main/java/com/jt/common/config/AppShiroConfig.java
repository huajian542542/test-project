package com.jt.common.config;

import java.util.LinkedHashMap;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
/**
 * 定义框架的配置信息
 * @author 86188
 *
 */
@Configuration
public class AppShiroConfig {

	public AppShiroConfig() {
		System.out.print("AppShiroConfig()");
	}
	/** Shiro 基本配置 */
	/**
	 * 配置shiro的SecurityManager对象
	 * @param userRealm
	 * @return
	 */
	@Bean("securityManager")
	public DefaultWebSecurityManager  newDefaultWebSecurityManager(
			AuthorizingRealm userRealm){
		DefaultWebSecurityManager sManager=
				new DefaultWebSecurityManager();
		//此时必须保证realm对象已经存在了
		sManager.setRealm(userRealm);
		return sManager;
	}
	/**配置Shiro的ShiroFilterFactoryBean工厂*/
	@Bean("shiroFilterFactoryBean")
	public ShiroFilterFactoryBean newShiroFilterFactoryBean(SecurityManager securityManager) {
		ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
		bean.setSecurityManager(securityManager);
		//当此用户是一个非认证用户,需要先登陆进行认证

		//System.out.println("---->>>>ShiroFilterFactoryBean(),");
		bean.setSecurityManager(securityManager);
		//当此用户是一个非认证用户,需要先登陆进行认证
		bean.setLoginUrl("/doLoginUI.do");
		LinkedHashMap<String,String> fcMap= new LinkedHashMap<>();
		fcMap.put("/bower_components/**","anon");//anon表示允许匿名访问
		fcMap.put("/build/**", "anon");
		fcMap.put("/dist/**","anon");
		fcMap.put("/plugins/**","anon");
		fcMap.put("/doLogin.do","anon");
		fcMap.put("/doLogout.do ","logout");
		fcMap.put("/**", "authc");//必须授权才能访问
		bean.setFilterChainDefinitionMap(fcMap);
		return bean; 
	}
	/**配置生命周期管理对象*/
	@Bean("lifecycleBeanPostProcessor")
	public LifecycleBeanPostProcessor newLifecycleBeanPostProcessor(){
		return new LifecycleBeanPostProcessor();
	}
	@DependsOn(value="lifecycleBeanPostProcessor")
	@Bean
	public DefaultAdvisorAutoProxyCreator newDefaultAdvisorAutoProxyCreator(){
		return new DefaultAdvisorAutoProxyCreator();
	}
	@Bean
	public AuthorizationAttributeSourceAdvisor newAuthorizationAttributeSourceAdvisor(
			SecurityManager securityManager){
		AuthorizationAttributeSourceAdvisor bean=
				new AuthorizationAttributeSourceAdvisor();
		bean.setSecurityManager(securityManager);
		return bean;
	}



}
