package com.longray.controller.common;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.utils.UserVo;

public class MemberInterceptor implements HandlerInterceptor{

	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		//请求的路径
        String contextPath = request.getContextPath();
        String url = request.getServletPath().toString();
		String queryString = request.getQueryString();

        HttpSession session = request.getSession();
        request.setAttribute("url",url);
        UserVo userVo = (UserVo) session.getAttribute("sessionUser");
        if(userVo == null)
        {
        	/*if(url.contains("/my")){
        		String originalUrl = url;
        		if(queryString != null){
        			originalUrl = url+"?"+queryString;
        		}
        		Cookie cookie = new Cookie("lastRequestUrl",originalUrl);
        		cookie.setMaxAge(3600);
        		cookie.setPath("/");
        		response.addCookie(cookie);
        		response.sendRedirect(contextPath+"/app/loginPage?params=failure");
            	return false;
        	}*/
        	response.sendRedirect(contextPath+"/login.jsp?params=failure");
        	return false;
        }

        return true;
	}

	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}

}
