package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.RatingService;
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
import java.security.Principal;

/**
 * <b>RatingController is the class which call and receive data for and from rating HTML pages</b>
 * <p>
 *     contain methods
 *     <ul>
 *         <li>home</li>
 *         <li>addRatingForm</li>
 *         <li>validate</li>
 *         <li>showUpdateForm</li>
 *         <li>updateRating</li>
 *         <li>deleteRating</li>
 *     </ul>
 * </p>
 * @author Guillaume C
 */

@Controller
public class RatingController {


    RatingService ratingService;


    LoginController loginController;

    public RatingController(RatingService ratingService, LoginController loginController) {
        this.ratingService = ratingService;
        this.loginController = loginController;
    }

    private final static Logger logger = LogManager.getLogger("RatingController");


    /**
     * call the rating list html page
     * find all Rating and get userInfo, add to model
     * @param principal is the connected user
     * @param model store information to use in the html page with Thymeleaf
     * @return rating/list HTML page
     */
    @RequestMapping("/rating/list")
    public String home(Principal principal, Model model) {
        logger.info("Get rating list to service and add to model");
        model.addAttribute("ratings", ratingService.getRatingList());
        String userInfo = loginController.getUserInfo(principal);
        model.addAttribute("principal", userInfo);
        return "rating/list";
    }

    /**
     * call the html page to add a rating
     * @param rating domain we want to add
     * @return rating/add HTML page
     */
    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating) {
        logger.info("Get Rating form to add a new rating");
        return "rating/add";
    }

    /**
     * pass the view information to the controller in order to add a rating
     * check data valid and save to db, after saving return Rating list
     * @param rating rating to add
     * @param result if Rating pattern has error or not
     * @param model store information to use in the html page with Thymeleaf
     * @return /rating/list if result ok
     * @return rating/add if result ko
     */
    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model) {
        logger.info("Validate Rating send to controller and call service to save it");
        if (!result.hasErrors()) {
            ratingService.saveRating(rating);
            model.addAttribute("ratings", ratingService.getRatingList());
            return "redirect:/rating/list";
        }
        return "rating/add";
    }

    /**
     * call the html page to update a rating by Id
     * get Rating by Id and to model then show to the form
     * @param id id of the Rating we want update
     * @param model store information to use in the html page with Thymeleaf
     * @return rating/update HTML page
     */
    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        logger.info("Get the form to update rating");
        Rating rating = ratingService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid rating Id:" + id));
        model.addAttribute("rating", rating);
        return "rating/update";
    }

    /**
     * pass the view information to the controller in order to update a rating
     * check required fields, if valid call service to update Rating and return Rating list
     * @param id id of the Rating we want update
     * @param rating rating to update with changes
     * @param result if Rating pattern has error or not
     * @param model store information to use in the html page with Thymeleaf
     * @return rating/update if result ko
     * @return /rating/list if result ok
     */
    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating,
                             BindingResult result, Model model) {
        logger.info("validate Rating sent to controller and call service to update it");
        if (result.hasErrors()) {
            return "rating/update";
        }
        ratingService.updateRating(id, rating);
        model.addAttribute("ratings", ratingService.getRatingList());
        return "redirect:/rating/list";
    }

    /**
     * call the html page to delete rating by Id
     * Find Rating by Id and delete the Rating, return to Rating list
     * @param id id of the rating we want to delete
     * @param model store information to use in the html page with Thymeleaf
     * @return /rating/list HTML page
     */
    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
        logger.info("Call service to delete rating");
        Rating rating = ratingService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid rating Id:" + id));
        ratingService.delete(rating);
        model.addAttribute("ratings", ratingService.getRatingList());
        return "redirect:/rating/list";
    }
}
