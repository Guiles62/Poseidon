package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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
        return bidListRepository.save(bid);
    }

    public Optional<BidList> findById(int id) {
        return bidListRepository.findById(id);
    }

    public BidList updateBid (int id, BidList bidList) {
        BidList bid = bidListRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid bid Id:" + id));
        bid.setAccount(bidList.getAccount());
        bid.setType(bidList.getType());
        bid.setBidQuantity(bidList.getBidQuantity());
        bid.setAskQuantity(bidList.getAskQuantity());
        bid.setBid(bidList.getBid());
        bid.setAsk(bidList.getAsk());
        bid.setBenchmark(bidList.getBenchmark());
        bid.setBidListDate(bidList.getBidListDate());
        bid.setCommentary(bidList.getCommentary());
        bid.setSecurity(bidList.getSecurity());
        bid.setStatus(bidList.getStatus());
        bid.setTrader(bidList.getTrader());
        bid.setBook(bidList.getBook());
        bid.setCreationName(bidList.getCreationName());
        bid.setCreationDate(bidList.getCreationDate());
        bid.setRevisionName(bidList.getRevisionName());
        bid.setRevisionDate(bidList.getRevisionDate());
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
