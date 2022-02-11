package com.nnk.springboot.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class HomeController {

	private final static Logger logger = LogManager.getLogger("HomeController");

	@RequestMapping("/")
	public String home(Model model)
	{
		try {
			logger.info("home");
		}catch (Exception ex) {
			logger.error("home error");
		}
		return "home";
	}

	@RequestMapping("/admin/home")
	public String adminHome(Model model) {
		try {
			logger.info("adminHome");
		}catch (Exception ex) {
			logger.error("adminHome error");
		}
		return "redirect:/bidList/list";
	}


}
