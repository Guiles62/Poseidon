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

@Service
public class BidListService {

    @Autowired
    BidListRepository bidListRepository;

    public BidListService(BidListRepository bidListRepository) {
        this.bidListRepository = bidListRepository;
    }

    private final static Logger logger = LogManager.getLogger("BidListService");

    // call repository to find all Bid
    public List<BidList> getBidList () {
            logger.info("getBidList");
      return bidListRepository.findAll();
    }

    // call repository to save a Bid
    public BidList saveBid(BidList bid) {
            logger.info("saveBid");
            Timestamp creationDate = new Timestamp(System.currentTimeMillis());
            Timestamp bidListDate = new Timestamp(bid.getBidListDate().getTime());
            bid.setCreationDate(creationDate);
            bid.setBidListDate(bidListDate);
        return bidListRepository.save(bid);
    }

    // call repository to find a Bid by Id
    public Optional<BidList> findById(int id) {
            logger.info("findById");
        return bidListRepository.findById(id);
    }

    // call repository to update a Bid by Id
    public BidList updateBid (int id, BidList bidList) {
            BidList bid = bidListRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid bid Id:" + id));
                logger.info("updateBid");
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

    // call repository to delete a Bid
    public void deleteBid(BidList bid) {
            logger.info("deleteBid");
        bidListRepository.delete(bid);
    }
}
