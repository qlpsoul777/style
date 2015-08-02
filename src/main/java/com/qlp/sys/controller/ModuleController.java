package com.qlp.sys.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.qlp.sys.service.ModuleService;

@Controller
@RequestMapping(value = "/module")
public class ModuleController {
	
	@Autowired
	private ModuleService moduleService;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(HttpServletRequest request, Model model){
		String jsonObj = moduleService.findAllModules();
		model.addAttribute("jsonObj", jsonObj);
		return "/style/system/module/list";
	}

}
