package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Optional;

@Service
public class BidListService {

    @Autowired
    BidListRepository bidListRepository;

    public List<BidList> getBidList () {
      return bidListRepository.findAll();
    }
    public BidList addBid(BidList bid) {
        return bidListRepository.save(bid);
    }
    public Optional<BidList> getBidById(int id) {
        return bidListRepository.findById(id);
    }
    public BidList addBidById(int id, BidList bidList) {
        return bidListRepository.saveById(id,bidList);
    }
    public BidList deleteById(int id) {
        return bidListRepository.deleteById(id);
    }
}
