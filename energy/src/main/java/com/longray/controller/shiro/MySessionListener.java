package com.longray.controller.shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListenerAdapter;

import com.utils.UserVo;

public class MySessionListener extends SessionListenerAdapter{

	@Override
	public void onStart(Session session) {
		System.out.println("create");
		super.onStart(session);
	}

	@Override
	public void onStop(Session session) {
		System.out.println("stop");
		super.onStop(session);
	}

	@Override
	public void onExpiration(Session session) {
		UserVo userVo = (UserVo) session.getAttribute("sessionUser");
		if(userVo != null){
			String userName = userVo.getUserName();
			System.out.println(userName);
		}
		super.onExpiration(session);
	}

}
