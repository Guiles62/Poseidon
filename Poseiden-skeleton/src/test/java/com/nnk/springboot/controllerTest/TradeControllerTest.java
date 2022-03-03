package com.nnk.springboot.controllerTest;

import com.nnk.springboot.controllers.LoginController;
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
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

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

    @MockBean
    TradeService tradeService;

    @MockBean
    LoginController loginController;


    TradeController tradeController;

    @InjectMocks
    private Trade trade;
    List<Trade>tradeList = new ArrayList<>();


    @BeforeEach
    void setup() {
        tradeController = new TradeController(tradeService, loginController);
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
        mockMvc.perform(post("/trade/validate")).andExpect(redirectedUrl("/trade/list"));
    }

    @Test
    @WithMockUser(username = "gui", authorities = {"ADMIN"})
    public void showUpdateFormTest() throws Exception {
        when(tradeService.findById(1)).thenReturn(Optional.of(trade));
        mockMvc.perform(get("/trade/update/1")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "gui", authorities = {"ADMIN"})
    public void updateTradeTest() throws Exception {
        mockMvc.perform(post("/trade/update/1")).andExpect(redirectedUrl("/trade/list"));
    }

    @Test
    @WithMockUser(username = "gui", authorities = {"ADMIN"})
    public void deleteTradeTest() throws Exception {
        when(tradeService.findById(1)).thenReturn(Optional.of(trade));
        mockMvc.perform(get("/trade/delete/1")).andExpect(redirectedUrl("/trade/list"));
    }
}
