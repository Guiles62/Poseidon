package com.nnk.springboot.controllerTest;


import com.nnk.springboot.controllers.LoginController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    LoginController loginController;

    @Test
    @WithMockUser(username = "gui")
    public void loginTest() throws Exception {
        mockMvc.perform(get("/app/login")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "gui")
    public void getAllUserArticlesTest() throws Exception {
        mockMvc.perform(get("/app/secure/article-details")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "gui")
    public void errorTest() throws Exception {
        mockMvc.perform(get("/app/error")).andExpect(status().isOk());
    }
}
