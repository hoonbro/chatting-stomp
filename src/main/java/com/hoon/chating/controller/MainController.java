package com.hoon.chating.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class MainController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@RequestMapping("/chat")
	public ModelAndView chat() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("chat");
		return mv;
	}
}
