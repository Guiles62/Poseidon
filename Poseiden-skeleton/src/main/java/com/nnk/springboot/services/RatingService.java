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
        try {
            logger.info("getRatingList");
        } catch (Exception ex) {
            logger.error("getRatingList error");
        }
        return ratingRepository.findAll();
    }

    public Rating saveRating(Rating rating) {
        try {
            logger.info("saveRating");
        } catch (Exception ex) {
            logger.error("saveRating error");
        }
        return ratingRepository.save(rating);
    }

    public Optional<Rating> findById(int id) {
        try {
            logger.info("findByID");
        } catch (Exception ex) {
            logger.error("findById error");
        }
        return ratingRepository.findById(id);
    }

    public Rating updateRating(int id, Rating rating) {
        Rating ratingFind = ratingRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid rating Id:" + id));
        try {
            logger.info("updateRating");
            ratingFind.setMoodysRating(rating.getMoodysRating());
            ratingFind.setSandPRating(rating.getSandPRating());
            ratingFind.setFitchRating(rating.getFitchRating());
            ratingFind.setOrderNumber(rating.getOrderNumber());
        } catch (Exception ex) {
            logger.error("updateRating error");
        }
        return ratingRepository.save(ratingFind);
    }

    public void delete(Rating rating) {
        try {
            logger.info("delete");
        } catch (Exception ex) {
            logger.error("delete error");
        }
        ratingRepository.delete(rating);
    }
}
