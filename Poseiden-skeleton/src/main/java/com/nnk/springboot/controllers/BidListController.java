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

/**
 * <b>BidListController is the class which call and receive data for and from BidList HTML pages</b>
 * <p>
 *     contain methods
 *     <ul>
 *         <li>home</li>
 *         <li>addBidForm</li>
 *         <li>validate</li>
 *         <li>showUpdateForm</li>
 *         <li>updateBid</li>
 *         <li>deleteBid</li>
 *     </ul>
 * </p>
 * @author Guillaume C
 */

@Controller
public class BidListController {

    @Autowired
    BidListService bidListService;

    @Autowired
    LoginController loginController;


    public BidListController(BidListService bidListService) {
        this.bidListService = bidListService;
    }

    private final static Logger logger = LogManager.getLogger("BidListController");

    /**
     * call service to find all bids to show to the view and get userInfo, add to model
     * @param principal is the connected user
     * @param model store information to use in the html page with Thymeleaf
     * @return bidList/list HTML page
     */
    @RequestMapping("/bidList/list")
    public String home(Principal principal,Model model) {
        logger.info("Get Bid List to service and add to model");
        model.addAttribute("bidList", bidListService.getBidList());
        String userInfo = loginController.getUserInfo(principal);
        model.addAttribute("principal", userInfo);
        return "bidList/list";
    }

    /**
     * call the html page to add a bid
     * @param bid domain we want to add
     * @return bidList/add HTML page
     */
    @GetMapping("/bidList/add")
    public String addBidForm(BidList bid) {
        logger.info("Get Bid Form to add new Bid");
        return "bidList/add";
    }

    /**
     * pass the view information to the controller in order to add a bid
     * check data valid and save to db, after saving return bid list
     * @param bid BidList to add
     * @param result if BidList pattern has error or not
     * @param model store information to use in the html page with Thymeleaf
     * @return /bidList/list if result ok
     * @return bidList/add if result ko
     */
    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bid, BindingResult result, Model model) {
        logger.info("Validate Bid send to controller and call service to save it");
        if (!result.hasErrors()) {
            bidListService.saveBid(bid);
            model.addAttribute("bidList", bidListService.getBidList());
            return "redirect:/bidList/list ";
        }
        return "bidList/add";
    }

    /**
     * call the html page to update a bid by Id
     * get Bid by Id and to model then show to the form
     * @param id id of the Bid we want to update
     * @param model store information to use in the html page with Thymeleaf
     * @return bidList/update HTML page
     */
    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        logger.info("Get the form to update Bid");
        BidList bid = bidListService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("bid", bid);
        return "bidList/update";
    }

    /**
     * pass the view information to the controller in order to update a bid
     * check required fields, if valid call service to update Bid and return list Bid
     * @param id id of the Bid we want to update
     * @param bidList BidList to update with changes
     * @param result if BidList pattern has error or not
     * @param model store information to use in the html page with Thymeleaf
     * @return bidList/update if result ko
     * @return /bidList/list if result ok
     */
    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList,
                             BindingResult result, Model model) {
        logger.info("validate Bid sent to controller and call service to update it");
        if (result.hasErrors()) {
            return "bidList/update";
        }
        bidListService.updateBid(id, bidList);
        model.addAttribute("bidList", bidListService.getBidList());
        return "redirect:/bidList/list";
    }

    /**
     * call the html page to delete a bid by Id
     * Find Bid by Id and delete the bid, return to Bid list
     * @param id id of the Bid we want delete
     * @param model store information to use in the html page with Thymeleaf
     * @return /bidList/list HTML page
     */
    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        logger.info("Call service to delete Bid");
        BidList bid = bidListService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid bid Id:" + id));
        bidListService.deleteBid(bid);
        model.addAttribute("bidList", bidListService.getBidList());
        return "redirect:/bidList/list";
    }
}
