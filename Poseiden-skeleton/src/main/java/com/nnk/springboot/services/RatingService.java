package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RatingService {

    @Autowired
    RatingRepository ratingRepository;

    public RatingService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    private final static Logger logger = LogManager.getLogger("RatingService");

    public List<Rating> getRatingList() {
            logger.info("getRatingList");
        return ratingRepository.findAll();
    }

    public Rating saveRating(Rating rating) {
            logger.info("saveRating");
        return ratingRepository.save(rating);
    }

    public Optional<Rating> findById(int id) {
            logger.info("findByID");
        return ratingRepository.findById(id);
    }

    public Rating updateRating(int id, Rating rating) {
        Rating ratingFind = ratingRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid rating Id:" + id));

            logger.info("updateRating");
            ratingFind.setMoodysRating(rating.getMoodysRating());
            ratingFind.setSandPRating(rating.getSandPRating());
            ratingFind.setFitchRating(rating.getFitchRating());
            ratingFind.setOrderNumber(rating.getOrderNumber());
        return ratingRepository.save(ratingFind);
    }

    public void delete(Rating rating) {
            logger.info("delete");
        ratingRepository.delete(rating);
    }
}
