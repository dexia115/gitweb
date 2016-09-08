package com.utils.spring;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class CustomerContextLoaderListener extends ContextLoaderListener {

	@Override
	public void contextInitialized(ServletContextEvent event) {
		
		ServletContext context = event.getServletContext();
		
		super.contextInitialized(event);
		
		ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(context);
		
		SpringContextHolder contextHolder = new SpringContextHolder();
		
		contextHolder.setApplicationContext(ctx);
		
		/**
		 * 最安全是在此设置安全验证
		 */
		contextHolder.setServletContext(context);
		
	}
}
