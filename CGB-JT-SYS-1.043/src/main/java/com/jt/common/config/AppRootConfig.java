package com.jt.common.config;

import java.io.IOException;


import javax.sql.DataSource;


import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.ControllerAdvice;

import com.alibaba.druid.pool.DruidDataSource;
/**
 * 配置service，dao等相关配置
 * @author 86188
 *
 */
//@Configuration
@ComponentScan(value="com.jt",
excludeFilters= {@Filter(value= {Controller.class,ControllerAdvice.class,Configuration.class})})/*除了这两个，其他的都扫描*/
@PropertySource("classpath:configs.properties")
@MapperScan("com.jt.**.dao")
@EnableAspectJAutoProxy  //此注解用于启用AOP
@EnableTransactionManagement
public class AppRootConfig {

	 /**
	  * 让系统支持多个properties文件应用
	  * @return
	  */
	 @Bean
	 public PropertySourcesPlaceholderConfigurer newPropertyPlaceholderConfigurer(){
		 return new PropertySourcesPlaceholderConfigurer();
	 }
	@Bean(value="dataSource",initMethod="init",destroyMethod="close")
	@Lazy(false)
	public DataSource newDataSource(
			@Value("${jdbcDriver}") String driver,
			@Value("${jdbcUrl}")String url,
			@Value("${jdbcUser}")String username,
			@Value("${jdbcPassword}")String password) {
		DruidDataSource ds = new DruidDataSource();
		ds.setDriverClassName(driver);
		ds.setUrl(url);
		ds.setUsername(username);
		ds.setPassword(password);
		return ds;
	}
	/*
	 * 整合SqlSessionFactoryBean对象
	 * 通过此对象创建SqlSessionFactory
	 */
	@Bean("sqlSessionFactory")
	public SqlSessionFactoryBean newSqlSessionFactoryBean(DataSource dataSource) throws IOException {
		SqlSessionFactoryBean fBean = new SqlSessionFactoryBean();
		fBean.setDataSource(dataSource);
		Resource[] mapperLocations = new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/sys/*Mapper.xml");
		fBean.setMapperLocations(mapperLocations);
		return fBean;
	}
	//此方法等价于   注解   @MapperScan("com.jt.**.dao")
	/*
	@Bean 
	public MapperScannerConfigurer newMapperScannerConfigurer() {
		MapperScannerConfigurer msc = new MapperScannerConfigurer();
		msc.setBasePackage("com.jt.**.dao");
		msc.setSqlSessionFactoryBeanName("sqlSessionFactory");
		return msc;
	}*/
	/**
	 * spring 中的声明式 事务控制
	 */
	@Bean("txManager")
	public DataSourceTransactionManager newDataSourceTransactionManager(DataSource dataSource) {
		DataSourceTransactionManager tm = new DataSourceTransactionManager();
		//注入数据源对象（Druid）
		tm.setDataSource(dataSource);
		return tm;
	}
}
