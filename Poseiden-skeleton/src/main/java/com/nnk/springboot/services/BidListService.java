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
        bidList.setBidListId(id);
        bidList.setType(bidList.getType());
        bidList.setBidQuantity(bidList.getBidQuantity());
        return bidListRepository.save(bidList);
    }

    public void deleteBid(BidList bid) {
        bidListRepository.delete(bid);
    }
}
