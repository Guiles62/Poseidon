package com.nnk.springboot.controllerTest;


import com.nnk.springboot.controllers.HomeController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class HomeControllerTest {

    @Autowired
    HomeController homeController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "gui")
    public void homeTest() throws Exception {
        mockMvc.perform(get("/")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "gui",authorities = "ADMIN")
    public void adminHomeTest() throws Exception {
        mockMvc.perform(get("/admin/home")).andExpect(status().isFound()).andExpect(redirectedUrl("/bidList/list"));
    }
}
