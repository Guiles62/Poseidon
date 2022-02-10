package com.nnk.springboot.controllerTest;

import com.nnk.springboot.controllers.RatingController;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.RatingService;
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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RatingControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Mock
    RatingService ratingService;

    @Autowired
    RatingController ratingController;

    private Rating rating;
    private List<Rating>ratingList = new ArrayList<>();
    private Model model;

    @BeforeEach
    void setup() {
        rating = new Rating();
        rating.setId(1);
        rating.setFitchRating("fitch");
        rating.setMoodysRating("moodys");
        rating.setOrderNumber(1);
        rating.setSandPRating("sand");
        ratingList.add(rating);
        when(ratingService.getRatingList()).thenReturn(ratingList);
        when(ratingService.updateRating(1,rating)).thenReturn(rating);
        when(ratingService.saveRating(rating)).thenReturn(rating);
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
        mockMvc.perform(post("/rating/validate")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "gui")
    public void showUpdateFormTest() throws Exception {
        mockMvc.perform(get("/rating/update/1")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "gui")
    public void updateRatingTest() throws Exception {
        mockMvc.perform(post("/rating/update/1")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "gui")
    public void deleteRatingTest() throws Exception {
        mockMvc.perform(get("/rating/delete/1")).andExpect(status().isOk());
    }

}
