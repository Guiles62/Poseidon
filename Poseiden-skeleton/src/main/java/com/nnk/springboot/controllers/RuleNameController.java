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

@Controller
public class RuleNameController {

    @Autowired
    RuleNameService ruleNameService;

    @Autowired
    LoginController loginController;

    private final static Logger logger = LogManager.getLogger("RuleNameController");

    // call ruleName list html page
    @RequestMapping("/ruleName/list")
    public String home(Principal principal, Model model) {
        // find all RuleName and get userInfo, add to model
            logger.info("home");
            model.addAttribute("ruleNames", ruleNameService.getRuleNameList());
            String userInfo = loginController.getUserInfo(principal);
            model.addAttribute("principal", userInfo);
        return "ruleName/list";
    }

    // call the html page to add a ruleName
    @GetMapping("/ruleName/add")
    public String addRuleForm(RuleName ruleName) {
            logger.info("addRuleForm");
        return "ruleName/add";
    }

    // pass the view information to the controller in order to add a ruleName
    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleName ruleName, BindingResult result, Model model) {
        // check data valid and save to db, after saving return RuleName list
            logger.info("validate");
            if (!result.hasErrors()) {
                ruleNameService.saveRuleName(ruleName);
                model.addAttribute("ruleNames", ruleNameService.getRuleNameList());
                return "redirect:/ruleName/list";
            }
        return "ruleName/add";
    }

    // call the html page to update a ruleName by Id
    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // get RuleName by Id and to model then show to the form
            logger.info("showUpdateForm");
            RuleName rule = ruleNameService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
            model.addAttribute("ruleName", rule);
        return "ruleName/update";
    }

    // pass the view information to the controller in order to update a ruleName
    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName,
                             BindingResult result, Model model) {
        // check required fields, if valid call service to update RuleName and return RuleName list

            logger.info("updateRuleName");
            if (result.hasErrors()) {
                return "ruleName/update";
            }
            ruleNameService.updateRuleName(id, ruleName);
            model.addAttribute("ruleNames", ruleNameService.getRuleNameList());
        return "redirect:/ruleName/list";
    }

    // call the html page to delete ruleName by Id
    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
        // Find RuleName by Id and delete the RuleName, return to Rule list
            logger.info("deleteRuleName");
            RuleName ruleName = ruleNameService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid ruleName Id:" + id));
            ruleNameService.delete(ruleName);
            model.addAttribute("ruleNames", ruleNameService.getRuleNameList());
        return "redirect:/ruleName/list";
    }
}
