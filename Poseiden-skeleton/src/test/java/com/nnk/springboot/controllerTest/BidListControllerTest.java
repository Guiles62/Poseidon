package com.nnk.springboot.controllerTest;


import com.nnk.springboot.controllers.BidListController;
import com.nnk.springboot.domain.BidList;
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
import org.springframework.validation.BindingResult;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
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

    @Mock
    BidListController bidListController;

    private BidList bid;
    private List<BidList> bidLists = new ArrayList<>();
    private Model model;
    private BindingResult result;

    @BeforeEach
    public void setup() {
        bid = new BidList();
        bid.setBidListId(1);
        bidListController = new BidListController(bidListService);
        bidLists.add(bid);
        when(bidListService.getBidList()).thenReturn(bidLists);

    }


    @Test
    @WithMockUser(username = "gui", authorities = "ADMIN")
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
        when(bidListService.saveBid(bid)).thenReturn(bid);
        mockMvc.perform(post("/bidList/validate")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "gui")
        public void showUpdateFormTest() throws Exception {
        when(bidListService.findById(1)).thenReturn(Optional.ofNullable(bid));
        bidListController.showUpdateForm(1,model);
        assertEquals(bid,bidListController.showUpdateForm(1,model));
    }

    @Test
    @WithMockUser(username = "gui")
    public void updateBidTest() throws Exception {
        when(bidListService.updateBid(1,bid)).thenReturn(bid);
        bidListController.updateBid(1,bid,result,model);
        assertEquals(bid,bidListController.updateBid(1,bid,result,model));
    }

    @Test
    @WithMockUser(username = "gui")
    public void deleteBidTest() throws Exception {
        bidListController.deleteBid(1,model);
        assertEquals(0, bidListService.getBidList().size());
    }
}
