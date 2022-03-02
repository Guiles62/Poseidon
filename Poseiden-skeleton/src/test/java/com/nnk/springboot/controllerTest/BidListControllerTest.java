package com.nnk.springboot.controllerTest;


import com.nnk.springboot.controllers.BidListController;
import com.nnk.springboot.controllers.LoginController;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidListService;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class BidListControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    BidListService bidListService;

    @MockBean
    LoginController loginController;


    BidListController bidListController;

    @InjectMocks
    private BidList bid;
    private List<BidList> bidLists = new ArrayList<>();


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
        bidLists.add(bid);
        when(bidListService.getBidList()).thenReturn(bidLists);
        when(bidListService.findById(1)).thenReturn(Optional.ofNullable(bid));

    }


    @Test
    @WithMockUser("gui")
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
    @WithMockUser(username = "gui", authorities={"ADMIN"})
    public void showUpdateFormTest() throws Exception {
        when(bidListService.findById(1)).thenReturn(Optional.ofNullable(bid));
        mockMvc.perform(get("/bidList/update/1")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "gui", authorities={"ADMIN"})
    public void updateBidTest() throws Exception {
        mockMvc.perform(post("/bidList/update/1")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "gui", authorities={"ADMIN"})
    public void deleteBidTest() throws Exception {
        when(bidListService.findById(1)).thenReturn(Optional.ofNullable(bid));
        mockMvc.perform(get("/bidList/delete/1")).andExpect(redirectedUrl("/bidList/list"));
    }
}
