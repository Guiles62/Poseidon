package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidListService;
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

    // call the BidList html page
    @RequestMapping("/bidList/list")
    public String home(Principal principal,Model model) {
        // call service find all bids to show to the view and get userInfo, add to model
        try {
            logger.info("home");
            model.addAttribute("bidList", bidListService.getBidList());
            String userInfo = loginController.getUserInfo(principal);
            model.addAttribute("principal", userInfo);

        } catch (Exception ex) {
            logger.error("home error");
        }
        return "bidList/list";
    }

    // call the html page to add a bid
    @GetMapping("/bidList/add")
    public String addBidForm(BidList bid) {
        try {
            logger.info("addBidForm");
        } catch (Exception ex) {
            logger.error("addBidForm error");
        }
        return "bidList/add";
    }

    // pass the view information to the controller in order to add a bid
    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bid, BindingResult result, Model model) {
        // check data valid and save to db, after saving return bid list
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

    // call the html page to update a bid by Id
    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // get Bid by Id and to model then show to the form
        try {
            logger.info("showUpdateFrom");
            BidList bid = bidListService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
            model.addAttribute("bid", bid);
        }catch (Exception ex) {
            logger.error("showUpdateForm error");
        }
        return "bidList/update";
    }

    // pass the view information to the controller in order to update a bid
    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList,
                             BindingResult result, Model model) {
        // check required fields, if valid call service to update Bid and return list Bid
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

    // call the html page to delete a bid by Id
    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        // Find Bid by Id and delete the bid, return to Bid list
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
