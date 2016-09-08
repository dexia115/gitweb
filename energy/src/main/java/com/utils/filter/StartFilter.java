package com.utils.filter;

import java.io.IOException;
import java.io.InputStream;
import java.net.UnknownHostException;
import java.util.Properties;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.java_websocket.WebSocketImpl;

import com.longray.controller.common.ChatServer;
import com.utils.spring.SpringContextHolder;

public class StartFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2) throws IOException, ServletException {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		
		ServletContext servletContext = SpringContextHolder.getServletContext();
		
		try {
			Properties prop = new Properties();
			InputStream in = this.getClass().getResourceAsStream("/application.properties");
			prop.load(in);
			String hashAlgorithmName = prop.getProperty("shiro.hashAlgorithmName").trim();
			String hashIterations = prop.getProperty("shiro.hashIterations").trim();
			String storedCredentialsHexEncoded = prop.getProperty("shiro.storedCredentialsHexEncoded").trim();
			if(servletContext != null){
				servletContext.setAttribute("shiro.hashAlgorithmName", hashAlgorithmName);
				servletContext.setAttribute("shiro.hashIterations", Integer.parseInt(hashIterations));
				servletContext.setAttribute("shiro.storedCredentialsHexEncoded", Boolean.valueOf(storedCredentialsHexEncoded));
			}
			in.close();
			prop.clear();
			
			startWebsocketInstantMsg();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 启动即时聊天服务
	 * @throws UnknownHostException 
	 */
	public void startWebsocketInstantMsg() throws UnknownHostException{
		WebSocketImpl.DEBUG = false;
		ChatServer s  = new ChatServer(8887);
		s.start();
	}

}
