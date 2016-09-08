package com.longray.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class HomeAction {
	
	@RequestMapping(value = "home", method = RequestMethod.GET)
	public String index(HttpServletRequest request) {

		return "home";
	}
	
	@RequestMapping(value = "/welcome")
	public String welcome(HttpServletRequest request)
	{
//		Locale locale = new Locale("zh", "CN");
//		locale = new Locale("en", "US");
//        request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME,locale);
        //从后台代码获取国际化信息
//        RequestContext requestContext = new RequestContext(request);
//        request.setAttribute("message", requestContext.getMessage("message"));
        
		return "welcome";
	}

}
