package com.nnk.springboot.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <b>HomeController is the class which call home HTML pages</b>
 * <p>
 *     contain methods
 *     <ul>
 *         <li>home</li>
 *         <li>adminHome</li>
 *     </ul>
 * </p>
 * @author Guillaume C
 */

@Controller
public class HomeController {

	private final static Logger logger = LogManager.getLogger("HomeController");

	/**
	 * call the home page for user as USER and ADMIN authorities
	 * @param model store information to use in the html page with Thymeleaf
	 * @return home HTML page
	 */
	@RequestMapping("/")
	public String home(Model model)
	{
			logger.info("Get Home HTML page");
		return "home";
	}

	/**
	 * call the home page for user as ADMIN authorities and redirect to the bidList/list html page
	 * @param model store information to use in the html page with Thymeleaf
	 * @return /bidList/list HTML page
	 */
	@RequestMapping("/admin/home")
	public String adminHome(Model model) {
			logger.info("Get Admin home HTML page");
		return "redirect:/bidList/list";
	}


}
