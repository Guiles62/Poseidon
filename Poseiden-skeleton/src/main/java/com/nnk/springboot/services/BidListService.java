package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class BidListService {

    @Autowired
    BidListRepository bidListRepository;

    public List<BidList> getBidList () {
      return bidListRepository.findAll();
    }

    public BidList saveBid(BidList bid) {
        Timestamp creationDate = new Timestamp(System.currentTimeMillis());
        Timestamp bidListDate = new Timestamp(bid.getBidListDate().getTime());
        bid.setCreationDate(creationDate);
        bid.setBidListDate(bidListDate);
        return bidListRepository.save(bid);
    }

    public Optional<BidList> findById(int id) {
        return bidListRepository.findById(id);
    }

    public BidList updateBid (int id, BidList bidList) {
        BidList bid = bidListRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid bid Id:" + id));
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

    public void deleteBid(BidList bid) {
        bidListRepository.delete(bid);
    }
}
