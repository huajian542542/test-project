package com.jt.common.config;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
@Configuration
@ComponentScan(value="com.jt",
	includeFilters= {//通过过滤指定 只加载  Controller类
		@Filter(value= {Controller.class,ControllerAdvice.class},
				type=FilterType.ANNOTATION)},
	useDefaultFilters=false)/*默认加载指定包下的所有文件*/
@EnableWebMvc
public class AppServletConfig extends WebMvcConfigurerAdapter{

	//配置视图解析器
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		registry.jsp("/WEB-INF/pages/", ".html");
	}
	//整合fstJson
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		FastJsonHttpMessageConverter msConverters = new FastJsonHttpMessageConverter();
		List<MediaType> list = new ArrayList<MediaType>();
		list.add(new MediaType("text", "html", Charset.forName("utf-8")));
		list.add(new MediaType("application", "json", Charset.forName("utf-8")));
		msConverters.setSupportedMediaTypes(list);
		converters.add(msConverters);
	}
	


}
