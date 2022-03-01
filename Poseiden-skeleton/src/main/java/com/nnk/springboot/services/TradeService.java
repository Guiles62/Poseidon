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

/**
 * <b>TradeService is the class that calls the TradeRepository</b>
 * <p>
 *     contain methods
 *     <ul>
 *         <li>getTradeList</li>
 *         <li>saveTrade</li>
 *         <li>findById</li>
 *         <li>updateTrade</li>
 *         <li>delete</li>
 *     </ul>
 * </p>
 * @author Guillaume C
 */

@Service
public class TradeService {


    TradeRepository tradeRepository;

    public TradeService(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }

    private final static Logger logger = LogManager.getLogger("TradeService");


    /**
     * Call repository to find the trade list
     * @return call repository to find all trades
     */
    public List<Trade> getTradeList() {
        logger.info("Call repository to find the trade list");
        return tradeRepository.findAll();
    }

    /**
     * Call repository to save new trade
     * @param trade trade to save
     * @return call repository to save trade
     */
    public Trade saveTrade (Trade trade) {
        logger.info("Call repository to save new trade");
        Timestamp creationDate = new Timestamp(System.currentTimeMillis());
        Timestamp tradeDate = new Timestamp(trade.getTradeDate().getTime());
        trade.setCreationDate(creationDate);
        trade.setTradeDate(tradeDate);
        return tradeRepository.save(trade);
    }

    /**
     * Call repository to find a trade by id
     * @param id id of the trade to find
     * @return call repository to find trade by id
     */
    public Optional<Trade> findById(int id) {
        logger.info("Call repository to find a trade by id");
        return tradeRepository.findById(id);
    }

    /**
     * Call repository to update a trade
     * @param id id of the trade to update
     * @param trade trade to update with changes
     * @return call repository to save trade with changes
     */
    public Trade updateTrade(int id, Trade trade) {
        Trade tradeFind = tradeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid trade Id:" + id));
        logger.info("Call repository to update a trade");
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

    /**
     * Call repository to delete a trade
     * @param trade trade to delete
     */
    public void delete(Trade trade) {
        logger.info("Call repository to delete a trade");
        tradeRepository.delete(trade);
    }
}
