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


@Controller
public class CurveController {
    // TODO: Inject Curve Point service
    @Autowired
    CurveService curveService;

    private final static Logger logger = LogManager.getLogger("CurveController");

    @RequestMapping("/curvePoint/list")
    public String home(Model model) {
        // TODO: find all Curve Point, add to model
        try {
            logger.info("home");
            model.addAttribute("curvePoints", curveService.getCurvePointList());
        }catch (Exception ex) {
            logger.error("home error");
        }
        return "curvePoint/list";
    }

    @GetMapping("/curvePoint/add")
    public String addCurvePointForm(CurvePoint curvePoint) {
        try {
            logger.info("addCurvePointForm");
        } catch (Exception ex) {
            logger.error("addCurvePointForm error");
        }
        return "curvePoint/add";
    }

    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
        // TODO: check data valid and save to db, after saving return Curve list
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

    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get CurvePoint by Id and to model then show to the form
        try {
            logger.info("showUpdateForm");
            CurvePoint curvePoint = curveService.getCurvePointById(id).orElseThrow(() -> new IllegalArgumentException("Invalid curvePoint Id:" + id));
            model.addAttribute("curvePoint", curvePoint);
        }catch (Exception ex) {
            logger.error("showUpdateForm error");
        }
        return "curvePoint/update";
    }

    @PostMapping("/curvePoint/update/{id}")
    public String updateCurvePoint(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint,
                             BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update Curve and return Curve list
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

    @GetMapping("/curvePoint/delete/{id}")
    public String deleteCurvePoint(@PathVariable("id") Integer id, Model model) {
        // TODO: Find Curve by Id and delete the Curve, return to Curve list
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
