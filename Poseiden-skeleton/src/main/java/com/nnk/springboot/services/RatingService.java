package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * <b>RatingService is an interface that will be implemented by RatingServiceImpl</b>
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


public interface RatingService {




    Logger logger = LogManager.getLogger("RatingService");

    /**
     * Call repository to find rating List
     * @return the rating list
     */
    List<Rating> getRatingList();

    /**
     * Call repository to save new Rating
     * @param rating rating to save
     * @return call repository to save new rating
     */
    Rating saveRating(Rating rating);

    /**
     * Call repository to find rating by id
     * @param id id of the rating to find
     * @return call repository to find rating by id
     */
    Optional<Rating> findById(int id);

    /**
     * Call the repository to update rating by id
     * @param id id of the rating to update
     * @param rating rating to update with changes
     * @return call repository to save rating with changes
     */
    Rating updateRating(int id, Rating rating);

    /**
     * Call repository to delete rating
     * @param rating rating to delete
     */
    void delete(Rating rating);
}
