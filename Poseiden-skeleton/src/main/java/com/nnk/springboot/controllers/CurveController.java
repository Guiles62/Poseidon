package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.CurveService;
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
public class CurveController {
    // TODO: Inject Curve Point service
    @Autowired
    CurveService curveService;

    @Autowired
    LoginController loginController;

    private final static Logger logger = LogManager.getLogger("CurveController");

    // call the curvePoint list html page
    @RequestMapping("/curvePoint/list")
    public String home(Principal principal, Model model) {
        // find all Curve Point and userInfo, add to model
        try {
            logger.info("home");
            model.addAttribute("curvePoints", curveService.getCurvePointList());
            String userInfo = loginController.getUserInfo(principal);
            model.addAttribute("principal", userInfo);
        }catch (Exception ex) {
            logger.error("home error");
        }
        return "curvePoint/list";
    }

    // call the html page to add a curvePoint
    @GetMapping("/curvePoint/add")
    public String addCurvePointForm(CurvePoint curvePoint) {
        try {
            logger.info("addCurvePointForm");
        } catch (Exception ex) {
            logger.error("addCurvePointForm error");
        }
        return "curvePoint/add";
    }

    // pass the view information to the controller in order to add a curvePoint
    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
        // check data valid and save to db, after saving return Curve list
        try {
            logger.info("validate");
            if (!result.hasErrors()) {
                curveService.saveCurvePoint(curvePoint);
                model.addAttribute("curvePoints", curveService.getCurvePointList());
                return "redirect:/curvePoint/list";
            }
        }catch (Exception ex) {
            logger.error("validate error");
        }
        return "curvePoint/add";
    }

    // call the html page to update a curvePoint by Id
    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // get CurvePoint by Id and to model then show to the form
        try {
            logger.info("showUpdateForm");
            CurvePoint curvePoint = curveService.getCurvePointById(id).orElseThrow(() -> new IllegalArgumentException("Invalid curvePoint Id:" + id));
            model.addAttribute("curvePoint", curvePoint);
        }catch (Exception ex) {
            logger.error("showUpdateForm error");
        }
        return "curvePoint/update";
    }

    // pass the view information to the controller in order to update a curvePoint
    @PostMapping("/curvePoint/update/{id}")
    public String updateCurvePoint(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint,
                             BindingResult result, Model model) {
        // check required fields, if valid call service to update Curve and return Curve list
        try {
            logger.info("updateCurvePoint");
            if (result.hasErrors()) {
                return "curvePoint/update";
            }
            curveService.updateCurvePoint(id, curvePoint);
            model.addAttribute("curvePoints", curveService.getCurvePointList());
        }catch (Exception ex) {
            logger.error("updateCurvePoint error");
        }
        return "redirect:/curvePoint/list";
    }

    // call the html page to delete curvePoint by Id
    @GetMapping("/curvePoint/delete/{id}")
    public String deleteCurvePoint(@PathVariable("id") Integer id, Model model) {
        // Find Curve by Id and delete the Curve, return to Curve list
        try {
            logger.info("deleteCurvePoint");
            CurvePoint curvePoint1 = curveService.getCurvePointById(id).orElseThrow(() -> new IllegalArgumentException("Invalid curvePoint Id:" + id));
            curveService.delete(curvePoint1);
            model.addAttribute("curvePoints", curveService.getCurvePointList());
        }catch (Exception ex) {
            logger.error("deleteCurvePoint error");
        }
        return "redirect:/curvePoint/list";
    }
}
