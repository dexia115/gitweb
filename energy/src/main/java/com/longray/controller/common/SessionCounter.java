package com.longray.controller.common;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.utils.UserVo;

public class SessionCounter implements HttpSessionListener{

	@Override
	public void sessionCreated(HttpSessionEvent event) {
		ServletContext ctx = event.getSession( ).getServletContext();
		Integer numSessions = (Integer) ctx.getAttribute("numSessions");
		System.out.println(numSessions);
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent hse) {
		HttpSession session = hse.getSession();
		UserVo userVo = (UserVo) session.getAttribute("sessionUser");
		if(userVo != null){
			String userName = userVo.getUserName();
		}
		
	}

}
