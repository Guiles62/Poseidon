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

/**
 * <b>TradeController is the class which call and receive data for and from trade HTML pages</b>
 * <p>
 *     contain methods
 *     <ul>
 *         <li>home</li>
 *         <li>addTrade</li>
 *         <li>validate</li>
 *         <li>showUpdateForm</li>
 *         <li>updateTrade</li>
 *         <li>deleteTrade</li>
 *     </ul>
 * </p>
 * @author Guillaume C
 */

@Controller
public class TradeController {

    TradeService tradeService;

    @Autowired
    LoginController loginController;

    public TradeController(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    private final static Logger logger = LogManager.getLogger("TradeController");

    /**
     * call trade list html page
     * find all Trade and get userInfo, add to model
     * @param principal is the connected user
     * @param model store information to use in the html page with Thymeleaf
     * @return trade/list HTML page
     */
    @RequestMapping("/trade/list")
    public String home(Principal principal, Model model) {
        logger.info("Get the Trade List to service and add to model");
        model.addAttribute("trades", tradeService.getTradeList());
        String userInfo = loginController.getUserInfo(principal);
        model.addAttribute("principal", userInfo);
        return "trade/list";
    }

    /**
     * call the html page to add a trade
     * @param trade domain we want to add
     * @return trade/add HTML page
     */
    @GetMapping("/trade/add")
    public String addTrade(Trade trade) {
        logger.info("Get Trade form to add new Trade");
        return "trade/add";
    }

    /**
     * pass the view information to the controller in order to add a trade
     * check data valid and save to db, after saving return Trade list
     * @param trade trade to add
     * @param result if Trade pattern has error or not
     * @param model store information to use in the html page with Thymeleaf
     * @return /trade/list if result is ok
     * @return trade/add if result is ko
     */
    @PostMapping("/trade/validate")
    public String validate(@Valid Trade trade, BindingResult result, Model model) {
        logger.info("Validate Trade send to controller and call service to save it");
        if (!result.hasErrors()) {
            tradeService.saveTrade(trade);
            model.addAttribute("trades", tradeService.getTradeList());
            return "redirect:/trade/list";
        }
        return "trade/add";
    }

    /**
     * call the html page to update a trade by Id
     * get Trade by Id and to model then show to the form
     * @param id id of the Trade we want update
     * @param model store information to use in the html page with Thymeleaf
     * @return trade/update HTML page
     */
    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        logger.info("Get the form to update a trade");
        Trade trade = tradeService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("trade", trade);
        return "trade/update";
    }

    /**
     * pass the view information to the controller in order to update a trade
     * check required fields, if valid call service to update Trade and return Trade list
     * @param id id of the trade we want to update
     * @param trade trade to update with changes
     * @param result if Trade pattern has error or not
     * @param model store information to use in the html page with Thymeleaf
     * @return trade/update if result is ko
     * @return /trade/list if result is ok
     */
    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade,
                             BindingResult result, Model model) {
        logger.info("validate Trade sent to controller and call service to update it");
        if (result.hasErrors()) {
            return "trade/update";
        }
        tradeService.updateTrade(id, trade);
        model.addAttribute("trades", tradeService.getTradeList());
        return "redirect:/trade/list";
    }

    /**
     * call the html page to delete trade by Id
     * Find Trade by Id and call service to delete the Trade, return to Trade list
     * @param id id of the trade we want to delete
     * @param model store information to use in the html page with Thymeleaf
     * @return /trade/list HTML page
     */
    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
        logger.info("call service to delete Trade");
        Trade trade = tradeService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid trade Id:" + id));
        tradeService.delete(trade);
        model.addAttribute("trades", tradeService.getTradeList());
        return "redirect:/trade/list";
    }
}
