package com.longray.controller.equipment;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("equipment")
public class ReportAction {
	
	@RequestMapping(value = "report", method = RequestMethod.GET)
	public String index(HttpServletRequest request) {

		return "equipment/report";
	}
	
	@RequestMapping(value = "metronic", method = RequestMethod.GET)
	public String metronic(){
		return "equipment/metronic";
	}

}
