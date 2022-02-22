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

/**
 * <b>CurveController is the class which call and receive data for and from curvePoint HTML pages</b>
 * <p>
 *     contain methods
 *     <ul>
 *         <li>home</li>
 *         <li>addCurvePointForm</li>
 *         <li>validate</li>
 *         <li>showUpdateForm</li>
 *         <li>updateCurvePoint</li>
 *         <li>deleteCurvePoint</li>
 *     </ul>
 * </p>
 * @author Guillaume C
 */

@Controller
public class CurveController {

    @Autowired
    CurveService curveService;

    @Autowired
    LoginController loginController;

    private final static Logger logger = LogManager.getLogger("CurveController");

    /**
     * call the curvePoint list html page
     * find all Curve Point and userInfo, add to model
     * @param principal is the connected user
     * @param model store information to use in the html page with Thymeleaf
     * @return curvePoint/list HTML page
     */
    @RequestMapping("/curvePoint/list")
    public String home(Principal principal, Model model) {
            logger.info("get curvePoint list to service and add to model");
            model.addAttribute("curvePoints", curveService.getCurvePointList());
            String userInfo = loginController.getUserInfo(principal);
            model.addAttribute("principal", userInfo);
        return "curvePoint/list";
    }

    /**
     * call the html page to add a curvePoint
     * @param curvePoint domain we want to add
     * @return curvePoint/add HTML page
     */
    @GetMapping("/curvePoint/add")
    public String addCurvePointForm(CurvePoint curvePoint) {
            logger.info("addCurvePointForm");
        return "curvePoint/add";
    }

    /**
     * pass the view information to the controller in order to add a curvePoint
     * check data valid and save to db, after saving return Curve list
     * @param curvePoint curvePoint to add
     * @param result if curvePoint pattern has error or not
     * @param model store information to use in the html page with Thymeleaf
     * @return /curvePoint/list HTML page if result ok
     * @return curvePoint/add HTML page if result ko
     */
    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
            logger.info("validate curvePoint sent to controller and call service to save it");
            if (!result.hasErrors()) {
                curveService.saveCurvePoint(curvePoint);
                model.addAttribute("curvePoints", curveService.getCurvePointList());
                return "redirect:/curvePoint/list";
            }
        return "curvePoint/add";
    }

    /**
     * call the html page to update a curvePoint by Id
     * get CurvePoint by Id and to model then show to the form
     * @param id id of the curvePoint we want update
     * @param model store information to use in the html page with Thymeleaf
     * @return curvePoint/update
     */
    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
            logger.info("Get the form to update curvePoint");
            CurvePoint curvePoint = curveService.getCurvePointById(id).orElseThrow(() -> new IllegalArgumentException("Invalid curvePoint Id:" + id));
            model.addAttribute("curvePoint", curvePoint);
        return "curvePoint/update";
    }

    /**
     * pass the view information to the controller in order to update a curvePoint
     * check required fields, if valid call service to update Curve and return Curve list
     * @param id id of the curvePoint to update
     * @param curvePoint curvePoint to update
     * @param result if curvePoint pattern has error or not
     * @param model store information to use in the html page with Thymeleaf
     * @return curvePoint/update HTML page if result ko
     * @return /curvePoint/list HTML page if result ok
     */
    @PostMapping("/curvePoint/update/{id}")
    public String updateCurvePoint(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint,
                             BindingResult result, Model model) {
            logger.info("validate curvePoint sent to controller and call service to update it");
            if (result.hasErrors()) {
                return "curvePoint/update";
            }
            curveService.updateCurvePoint(id, curvePoint);
            model.addAttribute("curvePoints", curveService.getCurvePointList());
        return "redirect:/curvePoint/list";
    }

    /**
     * call the html page to delete curvePoint by Id
     * Find Curve by Id and delete the Curve, return to Curve list
     * @param id id of curvePoint to delete
     * @param model store information to use in the html page with Thymeleaf
     * @return /curvePoint/list HTML page
     */
    @GetMapping("/curvePoint/delete/{id}")
    public String deleteCurvePoint(@PathVariable("id") Integer id, Model model) {
            logger.info("Call service to delete curvePoint");
            CurvePoint curvePoint1 = curveService.getCurvePointById(id).orElseThrow(() -> new IllegalArgumentException("Invalid curvePoint Id:" + id));
            curveService.delete(curvePoint1);
            model.addAttribute("curvePoints", curveService.getCurvePointList());
        return "redirect:/curvePoint/list";
    }
}
