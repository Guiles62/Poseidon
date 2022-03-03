package com.nnk.springboot.controllerTest;

import com.nnk.springboot.controllers.LoginController;
import com.nnk.springboot.controllers.RatingController;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.RatingService;

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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RatingControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    RatingService ratingService;

    @MockBean
    LoginController loginController;


    RatingController ratingController;


    private User user;
    private Rating rating;
    private Model model;
    private List<Rating> ratingList = new ArrayList<>();
    private BindingResult result;

    @Before
    public void setup() {
        ratingController = new RatingController(ratingService, loginController);
        rating = new Rating();
        rating.setMoodysRating("moodys");
        rating.setFitchRating("fitch");
        rating.setOrderNumber(1);
        ratingList.add(rating);
        when(ratingService.getRatingList()).thenReturn(ratingList);
    }

    @Test
    @WithMockUser(username = "gui")
    public void homeTest() throws Exception {
        mockMvc.perform(get("/rating/list")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "gui")
    public void addRatingFormTest() throws Exception {
        mockMvc.perform(get("/rating/add")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "gui")
    public void validateTest() throws Exception {
        mockMvc.perform(post("/rating/validate")).andExpect(redirectedUrl("/rating/list"));

    }

    @Test
    @WithMockUser(username = "gui", authorities={"ADMIN"})
    public void showUpdateFormTest() throws Exception {
        when(ratingService.findById(1)).thenReturn(Optional.ofNullable(rating));
        mockMvc.perform(get("/rating/update/1")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "gui", authorities={"ADMIN"})
    public void updateRatingTest() throws Exception {
        when(ratingService.findById(1)).thenReturn(Optional.ofNullable(rating));
        mockMvc.perform(post("/rating/update/1")).andExpect(redirectedUrl("/rating/list"));
    }

    @Test
    @WithMockUser(username = "gui", authorities={"ADMIN"})
    public void deleteRatingTest() throws Exception {
        when(ratingService.findById(1)).thenReturn(Optional.ofNullable(rating));
        mockMvc.perform(get("/rating/delete/1")).andExpect(redirectedUrl("/rating/list"));
    }

}
