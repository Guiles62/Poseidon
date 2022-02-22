package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import javax.validation.Valid;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    private final static Logger logger = LogManager.getLogger("UserController");

    // call user list html page for user as ADMIN authorities
    @RequestMapping("/user/list")
    public String home(Model model) {
        // find all users, add to model
            logger.info("home");
            model.addAttribute("users", userService.getUserList());
        return "user/list";
    }

    // call the html page to add a user
    @GetMapping("/user/add")
    public String addUser(User bid) {
            logger.info("addUser");
        return "user/add";
    }

    // pass the view information to the controller in order to add a user
    @PostMapping("/user/validate")
    public String validate(@Valid User user, BindingResult result, Model model) {
        // check data valid and save to db, after saving return User list

            logger.info("validate");
            if (!result.hasErrors()) {
                userService.addUser(user);
                model.addAttribute("users", userService.getUserList());
                return "redirect:/user/list";
            }
        return "user/add";
    }

    // call the html page to update a user by Id
    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // get User by Id and to model then show to the form
            logger.info("showUpdateForm");
            User user = userService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
            user.setPassword("");
            model.addAttribute("user", user);
        return "user/update";
    }

    // pass the view information to the controller in order to update a User
    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid User user,
                             BindingResult result, Model model) {
        // check required fields, if valid call service to update User and return User list
            logger.info("updateUser");
            if (result.hasErrors()) {
                return "user/update";
            }
            userService.updateUser(id, user);
            model.addAttribute("users", userService.getUserList());
        return "redirect:/user/list";
    }

    // call the html page to delete a User by Id
    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {
        // Find User by Id and call service to delete the User, return to User list

            logger.info("deleteUser");
            User user = userService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
            userService.delete(user);
            model.addAttribute("users", userService.getUserList());
        return "redirect:/user/list";
    }
}
