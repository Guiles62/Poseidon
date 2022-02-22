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

/**
 * <b>UserController is the class which call and receive data for and from user HTML pages</b>
 * <p>
 *     contain methods
 *     <ul>
 *         <li>home</li>
 *         <li>addUser</li>
 *         <li>validate</li>
 *         <li>showUpdateForm</li>
 *         <li>updateUser</li>
 *         <li>deleteUser</li>
 *     </ul>
 * </p>
 * @author Guillaume C
 */

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    private final static Logger logger = LogManager.getLogger("UserController");

    /**
     * call user list html page for user as ADMIN authorities
     * find all users, add to model
     * @param model store information to use in the html page with Thymeleaf
     * @return user/list HTML page
     */
    @RequestMapping("/user/list")
    public String home(Model model) {
            logger.info("Get User List to service and add to model");
            model.addAttribute("users", userService.getUserList());
        return "user/list";
    }

    /**
     * call the html page to add a user
     * @param user domain we want to add
     * @return user/add HTML page
     */
    @GetMapping("/user/add")
    public String addUser(User user) {
            logger.info("Get User form to add new User");
        return "user/add";
    }

    /**
     * pass the view information to the controller in order to add a user
     * check data valid and save to db, after saving return User list
     * @param user user to add
     * @param result if User pattern has error or not
     * @param model store information to use in the html page with Thymeleaf
     * @return /user/list if result is ok
     * @return user/add if result is ko
     */
    @PostMapping("/user/validate")
    public String validate(@Valid User user, BindingResult result, Model model) {
            logger.info("Validate User send to controller and call service to save it");
            if (!result.hasErrors()) {
                userService.addUser(user);
                model.addAttribute("users", userService.getUserList());
                return "redirect:/user/list";
            }
        return "user/add";
    }

    /**
     * call the html page to update a user by Id
     * get User by Id and to model then show to the form
     * @param id id of the user we want update
     * @param model store information to use in the html page with Thymeleaf
     * @return user/update HTML page
     */
    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
            logger.info("get the form to update a user");
            User user = userService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
            user.setPassword("");
            model.addAttribute("user", user);
        return "user/update";
    }

    /**
     * pass the view information to the controller in order to update a User
     * check required fields, if valid call service to update User and return User list
     * @param id id of the user we want to update
     * @param user User to update with changes
     * @param result if User pattern has error or not
     * @param model store information to use in the html page with Thymeleaf
     * @return user/update if result is ko
     * @return /user/list if result is ok
     */
    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid User user,
                             BindingResult result, Model model) {
            logger.info("validate User sent to controller and call service to update it");
            if (result.hasErrors()) {
                return "user/update";
            }
            userService.updateUser(id, user);
            model.addAttribute("users", userService.getUserList());
        return "redirect:/user/list";
    }

    /**
     * call the html page to delete a User by Id
     * Find User by Id and call service to delete the User, return to User list
     * @param id if of the user we want to delete
     * @param model store information to use in the html page with Thymeleaf
     * @return /user/list HTML page
     */
    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {
            logger.info("Call service to delete user");
            User user = userService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
            userService.delete(user);
            model.addAttribute("users", userService.getUserList());
        return "redirect:/user/list";
    }
}
