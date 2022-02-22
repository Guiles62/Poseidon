package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.TradeService;
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
public class TradeController {
    // TODO: Inject Trade service
    @Autowired
    TradeService tradeService;

    @Autowired
    LoginController loginController;

    private final static Logger logger = LogManager.getLogger("TradeController");

    // call trade list html page
    @RequestMapping("/trade/list")
    public String home(Principal principal, Model model) {
        // find all Trade and get userInfo, add to model
        try {
            logger.info("home");
            model.addAttribute("trades", tradeService.getTradeList());
            String userInfo = loginController.getUserInfo(principal);
            model.addAttribute("principal", userInfo);
        } catch (Exception ex) {
            logger.error("home error");
        }
        return "trade/list";
    }

    // call the html page to add a trade
    @GetMapping("/trade/add")
    public String addTrade(Trade trade) {
        try {
            logger.info("addTrade");
        } catch (Exception ex) {
            logger.error("addTrade error");
        }
        return "trade/add";
    }

    // pass the view information to the controller in order to add a trade
    @PostMapping("/trade/validate")
    public String validate(@Valid Trade trade, BindingResult result, Model model) {
        // check data valid and save to db, after saving return Trade list
        try {
            logger.info("validate");
            if (!result.hasErrors()) {
                tradeService.saveTrade(trade);
                model.addAttribute("trades", tradeService.getTradeList());
                return "redirect:/trade/list";
            }
        } catch (Exception ex) {
            logger.error("validate error");
        }
        return "trade/add";
    }

    // call the html page to update a trade by Id
    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // get Trade by Id and to model then show to the form
        try {
            logger.info("showUpdateForm");
            Trade trade = tradeService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
            model.addAttribute("trade", trade);
        } catch (Exception ex) {
            logger.error("showUpdateForm error");
        }
        return "trade/update";
    }

    // pass the view information to the controller in order to update a trade
    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade,
                             BindingResult result, Model model) {
        // check required fields, if valid call service to update Trade and return Trade list
        try {
            logger.info("updateTrade");
            if (result.hasErrors()) {
                return "trade/update";
            }
            tradeService.updateTrade(id, trade);
            model.addAttribute("trades", tradeService.getTradeList());
        } catch (Exception ex) {
            logger.error("updateTrade error");
        }
        return "redirect:/trade/list";
    }

    // call the html page to delete trade by Id
    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
        // Find Trade by Id and call service to delete the Trade, return to Trade list
        try {
            logger.info("deleteTrade");
            Trade trade = tradeService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid trade Id:" + id));
            tradeService.delete(trade);
            model.addAttribute("trades", tradeService.getTradeList());
        } catch (Exception ex) {
            logger.error("deleteTrade error");
        }
        return "redirect:/trade/list";
    }
}
