package com.nnk.springboot.controllerTest;


import com.nnk.springboot.controllers.BidListController;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.BidListService;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



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


    BidListController bidListController;

    @InjectMocks
    private BidList bid;
    private List<BidList> bidLists = new ArrayList<>();
    private Model model;
    private BindingResult result;

    @Before
    public void setup() {
        bidListController = new BidListController(bidListService);
        Timestamp creationDate = new Timestamp(System.currentTimeMillis());
        Timestamp bidListDate = new Timestamp(System.currentTimeMillis() - 60);
        bid = new BidList();
        bid.setAccount("account Test");
        bid.setType("type Test");
        bid.setBidQuantity(10d);
        bid.setBidListId(1);
        bid.setBidListDate(creationDate);
        bid.setBidListDate(bidListDate);
        bid.setBid(10.0);
        bid.setAsk(10.0);
        bidListController = new BidListController(bidListService);
        bidLists.add(bid);
        when(bidListService.getBidList()).thenReturn(bidLists);
        when(bidListService.findById(1)).thenReturn(Optional.ofNullable(bid));

    }


    @Test
    @WithUserDetails("gui")
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
        mockMvc.perform(get("/bidList/update/1")).andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser("gui")
    public void updateBidTest() throws Exception {
        mockMvc.perform(post("/bidList/update/1")).andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser("gui")
    public void deleteBidTest() throws Exception {
        mockMvc.perform(get("/bidList/delete/1")).andExpect(status().isForbidden());
    }
}
