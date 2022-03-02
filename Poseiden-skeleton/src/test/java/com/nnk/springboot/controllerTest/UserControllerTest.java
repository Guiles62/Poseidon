package com.nnk.springboot.controllerTest;

import com.nnk.springboot.controllers.UserController;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.UserService;
import com.nnk.springboot.services.impl.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UserServiceImpl userService;

    UserController userController;

    @InjectMocks
    private User user;

    List<User> userList = new ArrayList<>();


    @Before
    public void setup() {
        userController = new UserController(userService);
        user.setId(40);
        userList.add(user);
        when(userService.getUserList()).thenReturn(userList);
    }

    @Test
    @WithMockUser(username = "gui", authorities = {"ADMIN"})
    public void homeTest() throws Exception {
        mockMvc.perform(get("/user/list")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "gui", authorities = {"ADMIN"})
    public void addUserTest() throws Exception {
        mockMvc.perform(get("/user/add")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "gui", authorities = {"ADMIN"})
    public void validateTest() throws Exception {
        mockMvc.perform(post("/user/validate")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "gui", authorities = {"ADMIN"})
    public void showUpdateFormTest() throws Exception {
        when(userService.findById(1)).thenReturn(Optional.of(user));
        mockMvc.perform(get("/user/update/1")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "gui", authorities = {"ADMIN"})
    public void updateUserTest() throws Exception {
        mockMvc.perform(post("/user/update/1")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "gui", authorities = {"ADMIN"})
    public void deleteUserTest() throws Exception {
        when(userService.findById(1)).thenReturn(Optional.of(user));
        mockMvc.perform(get("/user/delete/1")).andExpect(redirectedUrl("/user/list"));
    }
}
