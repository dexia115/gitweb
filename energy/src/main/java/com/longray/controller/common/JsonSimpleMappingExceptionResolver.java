package com.longray.controller.common;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import com.google.gson.Gson;
import com.utils.JsonObj;

public class JsonSimpleMappingExceptionResolver extends SimpleMappingExceptionResolver{

	@Override
	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		ModelAndView mv = new ModelAndView();
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Cache-Control", "no-cache, must-revalidate");
		JsonObj jsonObj = new JsonObj();
		jsonObj.setSuccess(false);
		jsonObj.setMessage(ex.getMessage());
		Gson gson = new Gson();
		try {
			response.getWriter().write(gson.toJson(jsonObj));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return mv;
	}

}
