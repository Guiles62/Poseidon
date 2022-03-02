package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

/**
 * <b>BidListService is an interface that will be implemented by BidListServiceImpl</b>
 * <p>
 *     contain methods
 *     <ul>
 *         <li>getBidList</li>
 *         <li>saveBid</li>
 *         <li>findById</li>
 *         <li>updateBid</li>
 *         <li>deleteBid</li>
 *     </ul>
 * </p>
 * @author Guillaume C
 */

public interface BidListService {


     Logger logger = LogManager.getLogger("BidListService");

    /**
     * call repository to find all Bid
     * @return the bid List
     */
    List<BidList> getBidList ();

    /**
     * call repository to save a Bid
     * @param bid Bid to save
     * @return call repository to save Bid
     */
    BidList saveBid(BidList bid);

    /**
     * call repository to find a Bid by Id
     * @param id bid id to find
     * @return bid find with id
     */
    Optional<BidList> findById(int id);

    /**
     * call repository to update a Bid by Id
     * @param id id of the Bid to update
     * @param bidList bidList to update with changes
     * @return call the repository to save changes
     */
    BidList updateBid (int id, BidList bidList);

    /**
     * call repository to delete a Bid
     * @param bid bid to delete
     */
    void deleteBid(BidList bid);
}
