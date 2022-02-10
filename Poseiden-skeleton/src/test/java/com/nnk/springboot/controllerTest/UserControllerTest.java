package com.nnk.springboot.controllerTest;

import com.nnk.springboot.controllers.UserController;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.UserService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    UserService userService;

    @Autowired
    UserController userController;

    private User user;
    private Model model;
    List<User> userList = new ArrayList<>();


    @BeforeEach
    void setup() {
        user.setId(1);
        userList.add(user);
        when(userService.getUserList()).thenReturn(userList);
    }

    @Test
    @WithMockUser(username = "gui")
    public void homeTest() throws Exception {
        mockMvc.perform(get("/user/list")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "gui")
    public void addUserTest() throws Exception {
        mockMvc.perform(get("/user/add")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "gui")
    public void validateTest() throws Exception {
        mockMvc.perform(post("/user/validate")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "gui")
    public void showUpdateFormTest() throws Exception {
        mockMvc.perform(get("/user/update/1")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "gui")
    public void updateUserTest() throws Exception {
        mockMvc.perform(post("/user/update/1")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "gui")
    public void deleteUserTest() throws Exception {
        mockMvc.perform(get("/user/delete/1")).andExpect(status().isOk());
    }
}
