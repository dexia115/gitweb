package com.longray.controller.shiro;

import java.security.Principal;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import com.utils.UserVo;

public class AnyRolesFilter extends AccessControlFilter{
	
	private String loginUrl = "/login.jsp?params=failure";

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
//        HttpServletResponse httpResponse = (HttpServletResponse) response;
		Principal principal = httpRequest.getUserPrincipal();
		Subject subjects = SecurityUtils.getSubject();
		Session session = subjects.getSession();
		UserVo userVo = (UserVo) session.getAttribute("sessionUser");
		if(userVo != null){
			String userName = userVo.getUserName();
//			System.out.println(userName);
		}
//		System.out.println(subjects.isAuthenticated());
//		System.out.println(subjects.isRemembered());
		if (principal != null) {
			return true;
		}
		String url = httpRequest.getServletPath().toString();
		if(url.contains(".jsp") || url.contains("system/test")){
			return true;
		}
		url = url.substring(1);
//		String queryString = httpRequest.getQueryString();
//		String requestURI=httpRequest.getRequestURI();
		httpRequest.getSession().setAttribute("lastUrl",url);
		return false;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		WebUtils.issueRedirect(request, response, loginUrl);
		return false;
	}

}
