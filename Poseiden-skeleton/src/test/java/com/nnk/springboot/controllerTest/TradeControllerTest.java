package com.nnk.springboot.controllerTest;

import com.nnk.springboot.controllers.TradeController;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.TradeService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;


import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TradeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    TradeService tradeService;

    @Autowired
    TradeController tradeController;

    @InjectMocks
    private Trade trade;
    List<Trade>tradeList = new ArrayList<>();
    private Model model;
    private BindingResult result;

    @BeforeEach
    void setup() {
        trade.setTradeId(1);
        tradeList.add(trade);
        when(tradeService.saveTrade(trade)).thenReturn((trade));
        when(tradeService.getTradeList()).thenReturn(tradeList);
    }

    @Test
    @WithMockUser(username = "gui")
    public void homeTest() throws Exception {
        mockMvc.perform(get("/trade/list")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "gui")
    public void addTradeTest() throws Exception {
        assertEquals("trade/add", tradeController.addTrade(trade));
    }

    @Test
    @WithMockUser(username = "gui")
    public void validateTest() throws Exception {
        assertEquals("trade/add", tradeController.validate(trade,result,model));
    }

    @Test
    @WithMockUser(username = "gui")
    public void showUpdateFormTest() throws Exception {
        assertEquals("trade/update",tradeController.showUpdateForm(1,model));
    }

    @Test
    @WithMockUser(username = "gui",authorities = "ADMIN")
    public void updateTradeTest() throws Exception {
        assertEquals("redirect:/trade/list",tradeController.updateTrade(1,trade,result,model));
    }

    @Test
    @WithMockUser(username = "gui")
    public void deleteTradeTest() throws Exception {
        assertEquals("redirect:/trade/list", tradeController.deleteTrade(1,model));
    }
}
