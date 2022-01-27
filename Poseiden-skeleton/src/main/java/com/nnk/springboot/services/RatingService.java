package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RatingService {

    @Autowired
    RatingRepository ratingRepository;

    public List<Rating> getRatingList() {
        return ratingRepository.findAll();
    }

    public Rating saveRating(Rating rating) {
        return ratingRepository.save(rating);
    }

    public Optional<Rating> findById(int id) {
        return ratingRepository.findById(id);
    }

    public Rating updateRating(int id, Rating rating) {
        rating.setId(id);
        rating.setFitchRating(rating.getFitchRating());
        rating.setSandPRating(rating.getSandPRating());
        rating.setMoodysRating(rating.getMoodysRating());
        rating.setOrderNumber(rating.getOrderNumber());
        return ratingRepository.save(rating);
    }

    public void delete(Rating rating) {
        ratingRepository.delete(rating);
    }
}
