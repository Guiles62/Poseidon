package com.nnk.springboot.controllerTest;

import com.nnk.springboot.controllers.RatingController;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.RatingService;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
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

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RatingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    RatingService ratingService;

    @Autowired
    RatingController ratingController;

    @InjectMocks
    private Rating rating;
    private Model model;
    private List<Rating> ratingList = new ArrayList<>();
    private BindingResult result;

    @BeforeEach
    void setup() {
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
        when(ratingService.saveRating(rating)).thenReturn(rating);
        mockMvc.perform(post("/rating/validate")).andExpect(status().isFound());
    }

    @Test
    @WithMockUser(username = "gui")
    public void showUpdateFormTest() throws Exception {
        when(ratingService.findById(1)).thenReturn(Optional.ofNullable(rating));
        ratingController.showUpdateForm(1,model);
        assertEquals(rating,ratingController.showUpdateForm(1,model));
    }

    @Test
    @WithMockUser(username = "gui")
    public void updateRatingTest() throws Exception {
        when(ratingService.updateRating(1,rating)).thenReturn(rating);
        ratingController.updateRating(1,rating,result,model);
        assertEquals(rating, ratingController.updateRating(1,rating,result,model));
    }

    @Test
    @WithMockUser(username = "gui", authorities = "ADMIN")
    public void deleteRatingTest() throws Exception {
        ratingController.deleteRating(1,model);
        assertEquals(0, ratingService.getRatingList().size());
    }

}
