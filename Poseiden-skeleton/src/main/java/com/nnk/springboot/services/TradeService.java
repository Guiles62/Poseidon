package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TradeService {

    @Autowired
    TradeRepository tradeRepository;

    public List<Trade> getTradeList() {
        return tradeRepository.findAll();
    }

    public Trade saveTrade (Trade trade) {
        return tradeRepository.save(trade);
    }

    public Optional<Trade> findById(int id) {
        return tradeRepository.findById(id);
    }

    public Trade updateTrade(int id, Trade trade) {
        // voir ce qu'il y a à mettre réellement à jour
        Trade tradeFind = tradeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid trade Id:" + id));
        tradeFind.setAccount(trade.getAccount());
        tradeFind.setType(trade.getType());
        tradeFind.setBuyQuantity(trade.getBuyQuantity());
        tradeFind.setSellQuantity(trade.getSellQuantity());
        tradeFind.setBuyPrice(trade.getBuyPrice());
        tradeFind.setSellPrice(trade.getSellPrice());
        tradeFind.setBenchmark(trade.getBenchmark());
        tradeFind.setTradeDate(trade.getTradeDate());
        tradeFind.setSecurity(trade.getSecurity());
        tradeFind.setStatus(trade.getStatus());
        tradeFind.setTrader(trade.getTrader());
        tradeFind.setBook(trade.getBook());
        tradeFind.setCreationName(trade.getCreationName());
        tradeFind.setCreationDate(trade.getCreationDate());
        tradeFind.setRevisionName(trade.getRevisionName());
        tradeFind.setRevisionDate(trade.getRevisionDate());
        tradeFind.setDealName(trade.getDealName());
        tradeFind.setDealType(trade.getDealType());
        tradeFind.setSourceListId(trade.getSourceListId());
        tradeFind.setSide(trade.getSide());
        return tradeRepository.save(tradeFind);
    }

    public void delete(Trade trade) {
        tradeRepository.delete(trade);
    }
}
