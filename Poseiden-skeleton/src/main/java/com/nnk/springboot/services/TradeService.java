package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class TradeService {

    @Autowired
    TradeRepository tradeRepository;

    public TradeService(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }

    private final static Logger logger = LogManager.getLogger("TradeService");

    public List<Trade> getTradeList() {
            logger.info("getTradeList");
        return tradeRepository.findAll();
    }

    public Trade saveTrade (Trade trade) {
            logger.info("saveTrade");
            Timestamp creationDate = new Timestamp(System.currentTimeMillis());
            Timestamp tradeDate = new Timestamp(trade.getTradeDate().getTime());
            trade.setCreationDate(creationDate);
            trade.setTradeDate(tradeDate);
        return tradeRepository.save(trade);
    }

    public Optional<Trade> findById(int id) {
            logger.info("finById");
        return tradeRepository.findById(id);
    }

    public Trade updateTrade(int id, Trade trade) {
        Trade tradeFind = tradeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid trade Id:" + id));
            logger.info("updateTrade");
            Timestamp revisionDate = new Timestamp(System.currentTimeMillis());
            Timestamp tradeDate = new Timestamp(trade.getTradeDate().getTime());
            tradeFind.setAccount(trade.getAccount());
            tradeFind.setType(trade.getType());
            tradeFind.setBuyQuantity(trade.getBuyQuantity());
            tradeFind.setSellQuantity(trade.getSellQuantity());
            tradeFind.setBuyPrice(trade.getBuyPrice());
            tradeFind.setSellPrice(trade.getSellPrice());
            tradeFind.setBenchmark(trade.getBenchmark());
            tradeFind.setTradeDate(tradeDate);
            tradeFind.setSecurity(trade.getSecurity());
            tradeFind.setStatus(trade.getStatus());
            tradeFind.setTrader(trade.getTrader());
            tradeFind.setBook(trade.getBook());
            tradeFind.setCreationName(trade.getCreationName());
            tradeFind.setRevisionName(trade.getRevisionName());
            tradeFind.setRevisionDate(revisionDate);
            tradeFind.setDealName(trade.getDealName());
            tradeFind.setDealType(trade.getDealType());
            tradeFind.setSourceListId(trade.getSourceListId());
            tradeFind.setSide(trade.getSide());
        return tradeRepository.save(tradeFind);
    }

    public void delete(Trade trade) {
            logger.info("delete");
        tradeRepository.delete(trade);
    }
}
