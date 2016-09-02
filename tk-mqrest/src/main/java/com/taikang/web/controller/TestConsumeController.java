package com.taikang.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestConsumeController {
	@ResponseBody
	@RequestMapping(value = "/getMessage", method = {RequestMethod.POST,RequestMethod.GET})
	public String getMessage(HttpServletRequest req){
		if(!StringUtils.isEmpty(req.getParameter("message"))){
			
			System.out.println("收到消息：------------>>>"+req.getParameter("message"));
			return "success";
		}
		return "error";
	}

}
