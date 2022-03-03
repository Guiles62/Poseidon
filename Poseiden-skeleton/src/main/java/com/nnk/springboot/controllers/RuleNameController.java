package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.RuleNameService;
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
 * <b>RuleNameController is the class which call and receive data for and from ruleName HTML pages</b>
 * <p>
 *     contain methods
 *     <ul>
 *         <li>home</li>
 *         <li>addRuleForm</li>
 *         <li>validate</li>
 *         <li>showUpdateForm</li>
 *         <li>updateRuleName</li>
 *         <li>deleteRuleName</li>
 *     </ul>
 * </p>
 * @author Guillaume C
 */

@Controller
public class RuleNameController {


    RuleNameService ruleNameService;


    LoginController loginController;

    public RuleNameController(RuleNameService ruleNameService, LoginController loginController) {
        this.ruleNameService = ruleNameService;
        this.loginController = loginController;
    }

    private final static Logger logger = LogManager.getLogger("RuleNameController");

    /**
     * call ruleName list html page
     * find all RuleName and get userInfo, add to model
     * @param principal is the connected user
     * @param model store information to use in the html page with Thymeleaf
     * @return ruleName/list HTML page
     */
    @RequestMapping("/ruleName/list")
    public String home(Principal principal, Model model) {
        logger.info("Get ruleName List to service and add to model");
        model.addAttribute("ruleNames", ruleNameService.getRuleNameList());
        String userInfo = loginController.getUserInfo(principal);
        model.addAttribute("principal", userInfo);
        return "ruleName/list";
    }

    /**
     * call the html page to add a ruleName
     * @param ruleName domain we want to add
     * @return ruleName/add HTML page
     */
    @GetMapping("/ruleName/add")
    public String addRuleForm(RuleName ruleName) {
        logger.info("Get ruleName Form to add new RuleName");
        return "ruleName/add";
    }

    /**
     * pass the view information to the controller in order to add a ruleName
     * check data valid and save to db, after saving return RuleName list
     * @param ruleName RuleName to add
     * @param result if RuleName pattern has error or not
     * @param model store information to use in the html page with Thymeleaf
     * @return /ruleName/list if result ok
     * @return ruleName/add if result ko
     */
    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleName ruleName, BindingResult result, Model model) {
        logger.info("Validate ruleName send to controller and call service to save it");
        if (!result.hasErrors()) {
            ruleNameService.saveRuleName(ruleName);
            model.addAttribute("ruleNames", ruleNameService.getRuleNameList());
            return "redirect:/ruleName/list";
        }
        return "ruleName/add";
    }

    /**
     * call the html page to update a ruleName by Id
     * get RuleName by Id and to model then show to the form
     * @param id id of the ruleName we want to update
     * @param model store information to use in the html page with Thymeleaf
     * @return ruleName/update HTML page
     */
    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        logger.info("Get the form to update ruleName");
        RuleName rule = ruleNameService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("ruleName", rule);
        return "ruleName/update";
    }

    /**
     * pass the view information to the controller in order to update a ruleName
     * check required fields, if valid call service to update RuleName and return RuleName list
     * @param id id of the ruleName to update
     * @param ruleName ruleName to update with changes
     * @param result if RuleName pattern has error or not
     * @param model store information to use in the html page with Thymeleaf
     * @return ruleName/update if result ko
     * @return /ruleName/list if result ok
     */
    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName,
                             BindingResult result, Model model) {
        logger.info("validate RuleName sent to controller and call service to update it");
        if (result.hasErrors()) {
            return "ruleName/update";
        }
        ruleNameService.updateRuleName(id, ruleName);
        model.addAttribute("ruleNames", ruleNameService.getRuleNameList());
        return "redirect:/ruleName/list";
    }

    /**
     * call the html page to delete ruleName by Id
     * Find RuleName by Id and delete the RuleName, return to Rule list
     * @param id id of the ruleName to delete
     * @param model store information to use in the html page with Thymeleaf
     * @return /ruleName/list HTML page
     */
    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
        logger.info("Call service to delete ruleName");
        RuleName ruleName = ruleNameService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid ruleName Id:" + id));
        ruleNameService.delete(ruleName);
        model.addAttribute("ruleNames", ruleNameService.getRuleNameList());
        return "redirect:/ruleName/list";
    }
}
