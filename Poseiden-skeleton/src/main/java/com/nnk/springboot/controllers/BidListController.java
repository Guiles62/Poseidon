package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.BidListService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
public class BidListController {
    // TODO: Inject Bid service
    @Autowired
    BidListService bidListService;

    @Autowired
    LoginController loginController;

    public BidListController(BidListService bidListService) {
        this.bidListService = bidListService;
    }

    private final static Logger logger = LogManager.getLogger("BidListController");


    @RequestMapping("/bidList/list")
    public String home(Model model) {
        // TODO: call service find all bids to show to the view
        try {
            logger.info("home");
            model.addAttribute("bidList", bidListService.getBidList());
        } catch (Exception ex) {
            logger.error("home error");
        }
        return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidForm(BidList bid) {
        try {
            logger.info("addBidForm");
        } catch (Exception ex) {
            logger.error("addBidForm error");
        }
        return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bid, BindingResult result, Model model) {
        // TODO: check data valid and save to db, after saving return bid list
        try {
            logger.info("validate");
            if (!result.hasErrors()) {
                bidListService.saveBid(bid);
                model.addAttribute("bidList", bidListService.getBidList());
                return "redirect:/bidList/list ";
            }
        }catch (Exception ex) {
            logger.error("validate error");
        }
        return "bidList/add";
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get Bid by Id and to model then show to the form
        try {
            logger.info("showUpdateFrom");
            BidList bid = bidListService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
            model.addAttribute("bid", bid);
        }catch (Exception ex) {
            logger.error("showUpdateForm error");
        }
        return "bidList/update";
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList,
                             BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update Bid and return list Bid
        try {
            logger.info("updateBid");
            if (result.hasErrors()) {
                return "bidList/update";
            }
            bidListService.updateBid(id, bidList);
            model.addAttribute("bidList", bidListService.getBidList());
        }catch (Exception ex) {
            logger.error("updateBid error");
        }
        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        // TODO: Find Bid by Id and delete the bid, return to Bid list
        try {
            logger.info("deleteBid");
            BidList bid = bidListService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid bid Id:" + id));
            bidListService.deleteBid(bid);
            model.addAttribute("bidList", bidListService.getBidList());
        } catch (Exception ex) {
            logger.error("deleteBid error");
        }
        return "redirect:/bidList/list";
    }
}
