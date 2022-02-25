package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

/**
 * <b>BidListService is the class that calls the BidListRepository</b>
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

@Service
public class BidListService {

    @Autowired
    BidListRepository bidListRepository;

    public BidListService(BidListRepository bidListRepository) {
        this.bidListRepository = bidListRepository;
    }

    private final static Logger logger = LogManager.getLogger("BidListService");

    /**
     * call repository to find all Bid
     * @return the bid List
     */
    public List<BidList> getBidList () {
        logger.info("Call repository to get BidList");
        return bidListRepository.findAll();
    }

    /**
     * call repository to save a Bid
     * @param bid Bid to save
     * @return call repository to save Bid
     */
    public BidList saveBid(BidList bid) {
        logger.info("call repository to save new Bid");
        Timestamp creationDate = new Timestamp(System.currentTimeMillis());
        Timestamp bidListDate = new Timestamp(bid.getBidListDate().getTime());
        bid.setCreationDate(creationDate);
        bid.setBidListDate(bidListDate);
        return bidListRepository.save(bid);
    }

    /**
     * call repository to find a Bid by Id
     * @param id bid id to find
     * @return bid find with id
     */
    public Optional<BidList> findById(int id) {
        logger.info("Call repository to find Bid by id");
        return bidListRepository.findById(id);
    }

    /**
     * call repository to update a Bid by Id
     * @param id id of the Bid to update
     * @param bidList bidList to update with changes
     * @return call the repository to save changes
     */
    public BidList updateBid (int id, BidList bidList) {

        BidList bid = bidListRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid bid Id:" + id));
        logger.info("Call repository to save Bid with changes");
        Timestamp revisionDate = new Timestamp(System.currentTimeMillis());
        Timestamp bidListDate = new Timestamp(bid.getBidListDate().getTime());
        bid.setAccount(bidList.getAccount());
        bid.setType(bidList.getType());
        bid.setBidQuantity(bidList.getBidQuantity());
        bid.setAskQuantity(bidList.getAskQuantity());
        bid.setBid(bidList.getBid());
        bid.setAsk(bidList.getAsk());
        bid.setBenchmark(bidList.getBenchmark());
        bid.setBidListDate(bidListDate);
        bid.setCommentary(bidList.getCommentary());
        bid.setSecurity(bidList.getSecurity());
        bid.setStatus(bidList.getStatus());
        bid.setTrader(bidList.getTrader());
        bid.setBook(bidList.getBook());
        bid.setCreationName(bidList.getCreationName());
        bid.setRevisionName(bidList.getRevisionName());
        bid.setRevisionDate(revisionDate);
        bid.setDealName(bidList.getDealName());
        bid.setDealType(bidList.getDealType());
        bid.setSourceListId(bidList.getSourceListId());
        bid.setSide(bidList.getSide());
        return bidListRepository.save(bid);
    }

    /**
     * call repository to delete a Bid
     * @param bid bid to delete
     */
    public void deleteBid(BidList bid) {
        logger.info("Call repository to delete Bid");
        bidListRepository.delete(bid);
    }
}
