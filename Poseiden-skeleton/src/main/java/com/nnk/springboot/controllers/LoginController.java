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

/**
 * <b>LoginController is the class which call and receive data for and from user,login and error HTML pages</b>
 * <p>
 *     contain methods
 *     <ul>
 *         <li>login</li>
 *         <li>getAllUserArticles</li>
 *         <li>error</li>
 *         <li>getUserInfo</li>
 *         <li>getOauth2LoginInfo</li>
 *         <li>getUsernamePasswordLoginInfo</li>
 *     </ul>
 * </p>
 * @author Guillaume C
 */

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

    /**
     * call the login html
     * @return login HTML page
     */
    @GetMapping("/login")
    public ModelAndView login() {
        logger.info("Get the Login HTML page");
        ModelAndView mav = new ModelAndView();
        mav.setViewName("login");
        return mav;
    }

    /**
     * call the userList html
     * @return user/list HTML page
     */
    @GetMapping("/secure/article-details")
    public ModelAndView getAllUserArticles() {
        logger.info("Get userList HTML page");
        ModelAndView mav = new ModelAndView();
        mav.addObject("users", userService.getUserList());
        mav.setViewName("user/list");
        return mav;
    }

    /**
     * call error 403 html
     * @param principal is the connected user
     * @param model store information to use in the html page with Thymeleaf
     * @return /error/403 HTML page
     */
    @GetMapping("/*")
    public String error (Principal principal, Model model) {
        logger.info("Get error 403 message");
        String errorMessage= "You are not authorized for the requested data.";
        model.addAttribute("errorMsg", errorMessage);
        String userInfo = getUserInfo(principal);
        model.addAttribute("principal", userInfo);

        return "/error/403";
    }

    /**
     * retrieves logged in user data as text
     * @param user is the connected user
     * @return user information token to String
     */
    public String getUserInfo(Principal user) {
        StringBuffer userInfo= new StringBuffer();

        if(user instanceof UsernamePasswordAuthenticationToken) {
            userInfo.append(getUsernamePasswordLoginInfo(user));
        } else if (user instanceof OAuth2AuthenticationToken) {
            userInfo.append(getOauth2LoginInfo(user));
        }
        return userInfo.toString();
    }

    /**
     * retrieves the logged in user's login from the Github token
     * @param user is the connected user
     * @return user login token to string
     */
    public StringBuffer getOauth2LoginInfo(Principal user) {
        StringBuffer protectedInfo = new StringBuffer();

        OAuth2AuthenticationToken authToken = ((OAuth2AuthenticationToken) user);
        OAuth2AuthorizedClient authClient = this.oAuth2AuthorizedClientService.loadAuthorizedClient(authToken.getAuthorizedClientRegistrationId(), authToken.getName());

        Map<String, Object> userDetails = ((DefaultOAuth2User) authToken.getPrincipal()).getAttributes();

        String userToken = authClient.getAccessToken().getTokenValue();
        protectedInfo.append(userDetails.get("login"));

        return protectedInfo;
    }

    /**
     * retrieve logged in user data from application
     * @param user is the connected user
     * @return user userName token to string
     */
    public StringBuffer getUsernamePasswordLoginInfo(Principal user) {
        StringBuffer usernameInfo = new StringBuffer();
        UsernamePasswordAuthenticationToken token = ((UsernamePasswordAuthenticationToken) user);

        if(token.isAuthenticated()) {
            User u = (User) token.getPrincipal();
            usernameInfo.append(u.getUsername());
        }
        return usernameInfo;
    }
}
