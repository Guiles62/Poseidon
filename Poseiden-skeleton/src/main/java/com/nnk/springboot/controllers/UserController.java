package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    private final static Logger logger = LogManager.getLogger("UserController");

    @RequestMapping("/user/list")
    public String home(Model model) {
        try {
            logger.info("home");
            model.addAttribute("users", userService.getUserList());
        } catch (Exception ex) {
            logger.error("home error");
        }
        return "user/list";
    }

    @GetMapping("/user/add")
    public String addUser(User bid) {
        try{
            logger.info("addUser");
        } catch (Exception ex) {
            logger.error("addUser error");
        }
        return "user/add";
    }

    @PostMapping("/user/validate")
    public String validate(@Valid User user, BindingResult result, Model model) {
        try {
            logger.info("validate");
            if (!result.hasErrors()) {
                userService.addUser(user);
                model.addAttribute("users", userService.getUserList());
                return "redirect:/user/list";
            }
        }catch (Exception ex) {
            logger.error("validate error");
        }
        return "user/add";
    }

    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        try {
            logger.info("showUpdateForm");
            User user = userService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
            user.setPassword("");
            model.addAttribute("user", user);
        } catch (Exception ex) {
            logger.error("showUpdateForm error");
        }
        return "user/update";
    }

    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid User user,
                             BindingResult result, Model model) {
        try {
            logger.info("updateUser");
            if (result.hasErrors()) {
                return "user/update";
            }
            userService.updateUser(id, user);
            model.addAttribute("users", userService.getUserList());
        } catch (Exception ex) {
            logger.error("updateUser error");
        }
        return "redirect:/user/list";
    }

    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {
        try {
            logger.info("deleteUser");
            User user = userService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
            userService.delete(user);
            model.addAttribute("users", userService.getUserList());
        } catch (Exception ex) {
            logger.error("deleteUser error");
        }
        return "redirect:/user/list";
    }
}
