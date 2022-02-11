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
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
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

    @Mock
    UserController userController;

    private User user;
    private Model model;
    private BindingResult result;
    List<User> userList = new ArrayList<>();


    @BeforeEach
    void setup() {
        user.setId(40);
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
        when(userService.findById(1)).thenReturn(Optional.ofNullable(user));
        userController.showUpdateForm(1,model);
        assertEquals(user,userController.showUpdateForm(1,model));
    }

    @Test
    @WithMockUser(username = "gui")
    public void updateUserTest() throws Exception {
        when(userService.updateUser(1,user)).thenReturn(user);
        userController.updateUser(1,user,result,model);
        assertEquals(user,userController.updateUser(1,user,result,model));
    }

    @Test
    @WithMockUser(username = "gui")
    public void deleteUserTest() throws Exception {
        userController.deleteUser(40,model);
        assertEquals(0,userService.getUserList().size());
    }
}
