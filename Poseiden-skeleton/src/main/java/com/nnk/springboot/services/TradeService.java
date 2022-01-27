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
        trade.setTradeId(id);
        trade.setAccount(trade.getAccount());
        trade.setType(trade.getType());
        trade.setBuyQuantity(trade.getBuyQuantity());
        trade.setSellQuantity(trade.getSellQuantity());
        trade.setBuyPrice(trade.getBuyPrice());
        trade.setSellPrice(trade.getSellPrice());
        trade.setBenchmark(trade.getBenchmark());
        trade.setTradeDate(trade.getTradeDate());
        trade.setSecurity(trade.getSecurity());
        trade.setStatus(trade.getStatus());
        trade.setTrader(trade.getTrader());
        trade.setBook(trade.getBook());
        trade.setCreationName(trade.getCreationName());
        trade.setCreationDate(trade.getCreationDate());
        trade.setRevisionName(trade.getRevisionName());
        trade.setRevisionDate(trade.getRevisionDate());
        trade.setDealName(trade.getDealName());
        trade.setDealType(trade.getDealType());
        trade.setSourceListId(trade.getSourceListId());
        trade.setSide(trade.getSide());
        return tradeRepository.save(trade);
    }

    public void delete(Trade trade) {
        tradeRepository.delete(trade);
    }
}
