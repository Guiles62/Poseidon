package com.nnk.springboot.controllerTest;

import com.nnk.springboot.controllers.TradeController;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.TradeService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TradeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    TradeService tradeService;

    @Mock
    TradeController tradeController;

    private Trade trade;
    List<Trade>tradeList = new ArrayList<>();
    private Model model;

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
        mockMvc.perform(get("/trade/add")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "gui")
    public void validateTest() throws Exception {
        mockMvc.perform(post("/trade/validate")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "gui")
    public void showUpdateFormTest() throws Exception {
        when(tradeService.findById(1)).thenReturn(Optional.ofNullable(trade));
        tradeController.showUpdateForm(1,model);
        assertEquals(trade,tradeController.showUpdateForm(1,model));
    }

    @Test
    @WithMockUser(username = "gui",authorities = "ADMIN")
    public void updateTradeTest() throws Exception {
        mockMvc.perform(post("/trade/update/1")).andExpect(status().isFound()).andExpect(redirectedUrl("/trade/list"));
    }

    @Test
    @WithMockUser(username = "gui")
    public void deleteTradeTest() throws Exception {
        tradeController.deleteTrade(1,model);
        assertEquals(0, tradeService.getTradeList().size());
    }
}
