package com.utils.spring;

import javax.servlet.ServletContext;
import javax.sql.DataSource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringContextHolder implements ApplicationContextAware {
	
	private static ApplicationContext applicationContext = null;
	
	//专为软件使用期限设置的变量 
	private static ServletContext servletContext = null;
	
	private static DataSource dataSource = null;

	public static ServletContext getServletContext() {
		return servletContext;
	}

	public void setServletContext(ServletContext servletContext) {
		SpringContextHolder.servletContext = servletContext;
	}

	public static DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		SpringContextHolder.dataSource = dataSource;
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public void setApplicationContext(ApplicationContext applicationContext) {
		SpringContextHolder.applicationContext = applicationContext;
	}


}
