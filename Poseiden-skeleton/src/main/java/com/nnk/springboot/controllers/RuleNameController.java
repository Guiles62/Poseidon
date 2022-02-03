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

@Controller
public class RuleNameController {
    // TODO: Inject RuleName service
    @Autowired
    RuleNameService ruleNameService;

    private final static Logger logger = LogManager.getLogger("RuleNameController");

    @RequestMapping("/ruleName/list")
    public String home(Model model) {
        // TODO: find all RuleName, add to model
        try {
            logger.info("home");
            model.addAttribute("ruleNames", ruleNameService.getRuleNameList());
        } catch (Exception ex) {
            logger.error("home error");
        }
        return "ruleName/list";
    }

    @GetMapping("/ruleName/add")
    public String addRuleForm(RuleName ruleName) {
        try {
            logger.info("addRuleForm");
        } catch (Exception ex) {
            logger.error("addRuleForm error");
        }
        return "ruleName/add";
    }

    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleName ruleName, BindingResult result, Model model) {
        // TODO: check data valid and save to db, after saving return RuleName list
        try {
            logger.info("validate");
            if (!result.hasErrors()) {
                ruleNameService.saveRuleName(ruleName);
                model.addAttribute("ruleNames", ruleNameService.getRuleNameList());
                return "redirect:/ruleName/list";
            }
        } catch (Exception ex) {
            logger.error("validate error");
        }
        return "ruleName/add";
    }

    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get RuleName by Id and to model then show to the form
        try {
            logger.info("showUpdateForm");
            RuleName rule = ruleNameService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
            model.addAttribute("ruleName", rule);
        } catch (Exception ex) {
            logger.error("showUpdateForm error");
        }
        return "ruleName/update";
    }

    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName,
                             BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update RuleName and return RuleName list
        try {
            logger.info("updateRuleName");
            if (result.hasErrors()) {
                return "ruleName/update";
            }
            ruleNameService.updateRuleName(id, ruleName);
            model.addAttribute("ruleNames", ruleNameService.getRuleNameList());
        } catch (Exception ex) {
            logger.error("updateRuleName error");
        }
        return "redirect:/ruleName/list";
    }

    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
        // TODO: Find RuleName by Id and delete the RuleName, return to Rule list
        try {
            logger.info("deleteRuleName");
            RuleName ruleName = ruleNameService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid ruleName Id:" + id));
            ruleNameService.delete(ruleName);
            model.addAttribute("ruleNames", ruleNameService.getRuleNameList());
        } catch (Exception ex) {
            logger.error("deleteRuleName error");
        }
        return "redirect:/ruleName/list";
    }
}
