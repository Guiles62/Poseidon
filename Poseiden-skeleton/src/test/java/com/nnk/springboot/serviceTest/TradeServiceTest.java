package com.nnk.springboot.serviceTest;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.services.TradeService;
import com.nnk.springboot.services.impl.TradeServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TradeServiceTest {

    @Autowired
    TradeServiceImpl tradeServiceImpl;

    @Mock
    TradeRepository tradeRepository;

    @InjectMocks
    Trade trade;

    List<Trade> tradeList = new ArrayList<>();

    @Before
    public void setup() {
        Timestamp revisionDate = new Timestamp(System.currentTimeMillis());
        Timestamp tradeDate = new Timestamp(System.currentTimeMillis() - 60);
        trade = new Trade();
        trade.setTradeId(1);
        trade.setTrader("trader");
        trade.setSide("side");
        trade.setTradeDate(tradeDate);
        trade.setRevisionDate(revisionDate);
        when(tradeRepository.findAll()).thenReturn(tradeList);
        tradeServiceImpl = new TradeServiceImpl(tradeRepository);
    }

    @Test
    @WithMockUser(username = "gui")
    public void getTradeListTest() {
        when(tradeRepository.findAll()).thenReturn(tradeList);
        assertEquals(0, tradeServiceImpl.getTradeList().size());
    }

    @Test
    @WithMockUser(username = "gui")
    public void saveTradeTest() {
        when(tradeRepository.save(trade)).thenReturn(trade);
        tradeServiceImpl.saveTrade(trade);
        verify(tradeRepository,times(1)).save(trade);
    }

    @Test
    @WithMockUser(username = "gui")
    public void findByIdTest() {
        when(tradeRepository.findById(1)).thenReturn(Optional.ofNullable(trade));
        tradeServiceImpl.findById(1);
        verify(tradeRepository,times(1)).findById(1);
    }

    @Test
    @WithMockUser(username = "gui")
    public void updateTradeTest() {
        when(tradeRepository.findById(1)).thenReturn(Optional.ofNullable(trade));
        when(tradeRepository.save(trade)).thenReturn(trade);
        tradeServiceImpl.updateTrade(1, trade);
        verify(tradeRepository,times(1)).save(trade);
    }

    @Test
    @WithMockUser(username = "gui")
    public void deleteTest() {
        tradeServiceImpl.delete(trade);
        verify(tradeRepository,times(1)).delete(trade);
    }
}
