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
    // TODO: Inject Rating service
    @Autowired
    RatingService ratingService;

    @Autowired
    LoginController loginController;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    private final static Logger logger = LogManager.getLogger("RatingController");


    @RequestMapping("/rating/list")
    public String home(Principal principal, Model model) {
        // TODO: find all Rating, add to model
        try {
            logger.info("home");
            model.addAttribute("ratings", ratingService.getRatingList());
            String userInfo = loginController.getUserInfo(principal);
            model.addAttribute("principal", userInfo);
        }catch (Exception ex) {
            logger.error("home error");
        }
        return "rating/list";
    }

    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating) {
        try {
            logger.info("addRatingForm");
        }catch (Exception ex){
            logger.error("addRatingForm error");
        }
        return "rating/add";
    }

    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model) {
        // TODO: check data valid and save to db, after saving return Rating list
        try {
            logger.info("validate");
            if (!result.hasErrors()) {
                ratingService.saveRating(rating);
                model.addAttribute("ratings", ratingService.getRatingList());
                return "redirect:/rating/list";
            }
        } catch (Exception ex) {
            logger.error("validate error");
        }
        return "rating/add";
    }

    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get Rating by Id and to model then show to the form
        try {
            logger.info("showUpdateForm");
            Rating rating = ratingService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid rating Id:" + id));
            model.addAttribute("rating", rating);
        } catch (Exception ex) {
            logger.error("showUpdateForm error");
        }
        return "rating/update";
    }

    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating,
                             BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update Rating and return Rating list
        try {
            logger.info("updateRating");
            if (result.hasErrors()) {
                return "rating/update";
            }
            ratingService.updateRating(id, rating);
            model.addAttribute("ratings", ratingService.getRatingList());
        } catch (Exception ex) {
            logger.error("updateRating error");
        }
        return "redirect:/rating/list";
    }

    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
        // TODO: Find Rating by Id and delete the Rating, return to Rating list
        try {
            logger.info("deleteRating");
            Rating rating = ratingService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid rating Id:" + id));
            ratingService.delete(rating);
            model.addAttribute("ratings", ratingService.getRatingList());
        } catch (Exception ex) {
            logger.error("deleteRating error");
        }
        return "redirect:/rating/list";
    }
}
