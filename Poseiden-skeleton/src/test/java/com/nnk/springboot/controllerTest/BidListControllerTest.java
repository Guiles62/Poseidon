package com.nnk.springboot.controllerTest;


import com.nnk.springboot.controllers.BidListController;
import com.nnk.springboot.domain.BidList;

import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.services.BidListService;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;

import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.ui.Model;


import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BidListControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    BidListService bidListService;


    BidListController bidListController;


    private BidList bid;
    private List<BidList> bidLists = new ArrayList<>();
    private Model model;

    @BeforeEach
    public void setup() {
        bidListController = new BidListController(bidListService);
        bid.setBidListId(1);
        bidLists.add(bid);
        when(bidListService.getBidList()).thenReturn(bidLists);

    }


    @Test
    @WithMockUser(username = "gui")
    public void homeTest() throws Exception {
        mockMvc.perform(get("/bidList/list")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "gui")
    public void addBidFormTest() throws Exception {
        mockMvc.perform(get("/bidList/add")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "gui")
    public void validateTest() throws Exception {
        mockMvc.perform(post("/bidList/validate")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "gui")
    public void showUpdateFormTest() throws Exception {
        when(bidListService.getBidList()).thenReturn(bidLists);
        mockMvc.perform(get("/bidList/update/1")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "gui")
    public void updateBidTest() throws Exception {
        mockMvc.perform(post("/bidList/update/1")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "gui")
    public void deleteBidTest() throws Exception {
        bidListController.deleteBid(1,model);
        assertEquals(0, bidListService.getBidList().size());
    }
}
