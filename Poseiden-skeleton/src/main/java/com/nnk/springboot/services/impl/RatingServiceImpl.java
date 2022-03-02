package com.nnk.springboot.services.impl;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.services.RatingService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * <b>RatingServiceImpl is the class which implement RatingService and call RatingRepository</b>
 * <p>
 *     contain methods
 *     <ul>
 *         <li>getRatingList</li>
 *         <li>saveRating</li>
 *         <li>findById</li>
 *         <li>updateRating</li>
 *         <li>delete</li>
 *     </ul>
 * </p>
 * @author Guillaume C
 */

@Service
public class RatingServiceImpl implements RatingService {

    RatingRepository ratingRepository;

    public RatingServiceImpl(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    private final static Logger logger = LogManager.getLogger("RatingService");

    /**
     * Call repository to find rating List
     * @return the rating list
     */
    @Override
    public List<Rating> getRatingList() {
        logger.info("Call repository to find rating List");
        return ratingRepository.findAll();
    }

    /**
     * Call repository to save new Rating
     * @param rating rating to save
     * @return call repository to save new rating
     */
    @Override
    public Rating saveRating(Rating rating) {
        logger.info("Call repository to save new Rating");
        return ratingRepository.save(rating);
    }

    /**
     * Call repository to find rating by id
     * @param id id of the rating to find
     * @return call repository to find rating by id
     */
    @Override
    public Optional<Rating> findById(int id) {
        logger.info("Call repository to find rating by id");
        return ratingRepository.findById(id);
    }

    /**
     * Call the repository to update rating by id
     * @param id id of the rating to update
     * @param rating rating to update with changes
     * @return call repository to save rating with changes
     */
    @Override
    public Rating updateRating(int id, Rating rating) {
        Rating ratingFind = ratingRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid rating Id:" + id));
        logger.info("Call the repository to update rating by id");
        ratingFind.setMoodysRating(rating.getMoodysRating());
        ratingFind.setSandPRating(rating.getSandPRating());
        ratingFind.setFitchRating(rating.getFitchRating());
        ratingFind.setOrderNumber(rating.getOrderNumber());
        return ratingRepository.save(ratingFind);
    }

    /**
     * Call repository to delete rating
     * @param rating rating to delete
     */
    @Override
    public void delete(Rating rating) {
        logger.info("Call repository to delete rating");
        ratingRepository.delete(rating);
    }
}
