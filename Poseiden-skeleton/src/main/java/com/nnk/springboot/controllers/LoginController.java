package com.nnk.springboot.controllers;


import com.nnk.springboot.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.security.RolesAllowed;

@Controller
@RequestMapping("app")
public class LoginController {


    @Autowired
    private UserService userService;

    private final static Logger logger = LogManager.getLogger("LoginController");

    @GetMapping("login")
    public ModelAndView login() {
            logger.info("login");
            ModelAndView mav = new ModelAndView();
            mav.setViewName("login");
            return mav;
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("secure/article-details")
    public ModelAndView getAllUserArticles() {
        logger.info("getAllUserArticles");
        ModelAndView mav = new ModelAndView();
        mav.addObject("users", userService.getUserList());
        mav.setViewName("user/list");
        return mav;
    }

    @GetMapping("error")
    public ModelAndView error() {
        logger.info("error 403 message");
        ModelAndView mav = new ModelAndView();
        String errorMessage= "You are not authorized for the requested data.";
        mav.addObject("errorMsg", errorMessage);
        mav.setViewName("403");
        return mav;
    }
}
