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

@Controller
public class RatingController {

    @Autowired
    RatingService ratingService;

    @Autowired
    LoginController loginController;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    private final static Logger logger = LogManager.getLogger("RatingController");


    // call the rating list html page
    @RequestMapping("/rating/list")
    public String home(Principal principal, Model model) {
        // find all Rating and get userInfo, add to model
            logger.info("home");
            model.addAttribute("ratings", ratingService.getRatingList());
            String userInfo = loginController.getUserInfo(principal);
            model.addAttribute("principal", userInfo);
        return "rating/list";
    }

    // call the html page to add a rating
    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating) {
            logger.info("addRatingForm");
        return "rating/add";
    }

    // pass the view information to the controller in order to add a rating
    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model) {
        // check data valid and save to db, after saving return Rating list
            logger.info("validate");
            if (!result.hasErrors()) {
                ratingService.saveRating(rating);
                model.addAttribute("ratings", ratingService.getRatingList());
                return "redirect:/rating/list";
            }
        return "rating/add";
    }

    // call the html page to update a rating by Id
    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // get Rating by Id and to model then show to the form
            logger.info("showUpdateForm");
            Rating rating = ratingService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid rating Id:" + id));
            model.addAttribute("rating", rating);
        return "rating/update";
    }

    // pass the view information to the controller in order to update a rating
    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating,
                             BindingResult result, Model model) {
        // check required fields, if valid call service to update Rating and return Rating list
            logger.info("updateRating");
            if (result.hasErrors()) {
                return "rating/update";
            }
            ratingService.updateRating(id, rating);
            model.addAttribute("ratings", ratingService.getRatingList());
        return "redirect:/rating/list";
    }

    // call the html page to delete rating by Id
    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
        // Find Rating by Id and delete the Rating, return to Rating list
            logger.info("deleteRating");
            Rating rating = ratingService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid rating Id:" + id));
            ratingService.delete(rating);
            model.addAttribute("ratings", ratingService.getRatingList());
        return "redirect:/rating/list";
    }
}
