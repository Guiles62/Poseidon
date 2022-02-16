package com.nnk.springboot.controllers;


import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.Map;

@Controller
@RequestMapping("app")
public class LoginController {


    @Autowired
    private UserService userService;

    private final OAuth2AuthorizedClientService oAuth2AuthorizedClientService;

    private final static Logger logger = LogManager.getLogger("LoginController");

    public LoginController(OAuth2AuthorizedClientService oAuth2AuthorizedClientService) {
        this.oAuth2AuthorizedClientService = oAuth2AuthorizedClientService;
    }

    @GetMapping("/login")
    public ModelAndView login() {
            logger.info("login");
            ModelAndView mav = new ModelAndView();
            mav.setViewName("login");
            return mav;
    }

    @GetMapping("/secure/article-details")
    public ModelAndView getAllUserArticles() {
        logger.info("getAllUserArticles");
        ModelAndView mav = new ModelAndView();
        mav.addObject("users", userService.getUserList());
        mav.setViewName("user/list");
        return mav;
    }

    @GetMapping("/error")
    public ModelAndView error() {
        logger.info("error 403 message");
        ModelAndView mav = new ModelAndView();
        String errorMessage= "You are not authorized for the requested data.";
        mav.addObject("errorMsg", errorMessage);
        mav.setViewName("error/403");
        return mav;
    }

    @RequestMapping("/*")
    public String getUserInfo(Principal user) {
        StringBuffer userInfo= new StringBuffer();

        if(user instanceof UsernamePasswordAuthenticationToken) {
            userInfo.append(getUsernamePasswordLoginInfo(user));
        } else if (user instanceof OAuth2AuthenticationToken) {
            userInfo.append(getOauth2LoginInfo(user));
        }
        return userInfo.toString();
    }

    private StringBuffer getOauth2LoginInfo(Principal user) {
        StringBuffer protectedInfo = new StringBuffer();
        OAuth2AuthenticationToken authToken = ((OAuth2AuthenticationToken) user);
        OAuth2AuthorizedClient authClient = this.oAuth2AuthorizedClientService.loadAuthorizedClient(authToken.getAuthorizedClientRegistrationId(), authToken.getName());

        Map<String, Object> userDetails = ((DefaultOAuth2User) authToken.getPrincipal()).getAttributes();

        String userToken = authClient.getAccessToken().getTokenValue();
        protectedInfo.append(userDetails.get("name"));

        return protectedInfo;
    }

    private StringBuffer getUsernamePasswordLoginInfo(Principal user) {
        StringBuffer usernameInfo = new StringBuffer();
        UsernamePasswordAuthenticationToken token = ((UsernamePasswordAuthenticationToken) user);

        if(token.isAuthenticated()) {
            User u = (User) token.getPrincipal();
            usernameInfo.append(u.getUsername());
        }
        return usernameInfo;
    }
}
