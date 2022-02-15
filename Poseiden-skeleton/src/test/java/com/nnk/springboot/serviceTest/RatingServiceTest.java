package com.nnk.springboot.serviceTest;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.services.RatingService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RatingServiceTest {

    @Autowired
    RatingService ratingService;

    @Mock
    RatingRepository ratingRepository;

    @InjectMocks
    Rating rating;

    List<Rating>ratingList = new ArrayList<>();

    @Before
    public void setup() {
        rating = new Rating();
        rating.setSandPRating("sand test");
        rating.setFitchRating("fitch test");
        rating.setId(1);
        rating.setMoodysRating("moodys test");
        rating.setOrderNumber(1);
        when(ratingRepository.findAll()).thenReturn(ratingList);
        ratingService = new RatingService(ratingRepository);
    }

    @Test
    @WithMockUser(username = "gui")
    public void getRatingListTest() {
        when(ratingRepository.findAll()).thenReturn(ratingList);
        assertEquals(0,ratingService.getRatingList().size());
    }

    @Test
    @WithMockUser(username = "gui")
    public void saveRatingTest() {
        when(ratingRepository.save(rating)).thenReturn(rating);
        ratingService.saveRating(rating);
        verify(ratingRepository,times(1)).save(rating);
    }

    @Test
    @WithMockUser(username = "gui")
    public void findByIdTest() {
        when(ratingRepository.findById(1)).thenReturn(Optional.ofNullable(rating));
        ratingService.findById(1);
        verify(ratingRepository,times(1)).findById(1);
    }

    @Test
    @WithMockUser(username = "gui")
    public void updateRatingTest() {
        when(ratingRepository.save(rating)).thenReturn(rating);
        when(ratingRepository.findById(1)).thenReturn(Optional.ofNullable(rating));
        rating.setFitchRating("fitch test bis");
        ratingService.updateRating(1,rating);
        verify(ratingRepository,times(1)).save(rating);
    }

    @Test
    @WithMockUser(username = "gui")
    public void deleteTest() {
        ratingService.delete(rating);
        verify(ratingRepository,times(1)).delete(rating);
    }
}
