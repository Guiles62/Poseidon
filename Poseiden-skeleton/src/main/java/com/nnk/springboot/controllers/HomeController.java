package com.nnk.springboot.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class HomeController {

	private final static Logger logger = LogManager.getLogger("HomeController");

	// call the home page for user as USER and ADMIN authorities
	@RequestMapping("/")
	public String home(Model model)
	{
			logger.info("home");
		return "home";
	}

	// call the home page for user as ADMIN authorities and redirect to the bidList/list html page
	@RequestMapping("/admin/home")
	public String adminHome(Model model) {
			logger.info("adminHome");
		return "redirect:/bidList/list";
	}


}
