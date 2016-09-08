package com.longray.controller.shiro;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;

import com.utils.UserVo;

public class DmpSessionListener implements SessionListener{

	@Override
	public void onStart(Session sess) {
		
		System.out.println("session is create");
		
	}

	@Override
	public void onStop(Session session) {
		UserVo userVo = (UserVo) session.getAttribute("sessionUser");
		if(userVo != null){
			String userName = userVo.getUserName();
		}
		
	}

	@Override
	public void onExpiration(Session session) {
		System.out.println("session is expiration");
	}

}
