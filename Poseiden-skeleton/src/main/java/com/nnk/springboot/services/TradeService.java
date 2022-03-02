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
 * <b>TradeService is an interface that will be implemented by TradeServiceImpl</b>
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


public interface TradeService {


    Logger logger = LogManager.getLogger("TradeService");


    /**
     * Call repository to find the trade list
     * @return call repository to find all trades
     */
    List<Trade> getTradeList();

    /**
     * Call repository to save new trade
     * @param trade trade to save
     * @return call repository to save trade
     */
    Trade saveTrade (Trade trade);

    /**
     * Call repository to find a trade by id
     * @param id id of the trade to find
     * @return call repository to find trade by id
     */
    Optional<Trade> findById(int id);

    /**
     * Call repository to update a trade
     * @param id id of the trade to update
     * @param trade trade to update with changes
     * @return call repository to save trade with changes
     */
    Trade updateTrade(int id, Trade trade);

    /**
     * Call repository to delete a trade
     * @param trade trade to delete
     */
    void delete(Trade trade);
}
