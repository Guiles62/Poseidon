package com.nnk.springboot.serviceTest;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.services.BidListService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
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
public class BidListServiceTest {

    @Autowired
    BidListService bidListService;

    @Mock
    BidListRepository bidListRepository;

    @InjectMocks
    BidList bid;

    List<BidList> bidListList = new ArrayList<>();


    @Before
    public void setup() {
        Timestamp creationDate = new Timestamp(System.currentTimeMillis());
        Timestamp bidListDate = new Timestamp(System.currentTimeMillis() - 60);
        bid = new BidList();
        bid.setAccount("account Test");
        bid.setType("type Test");
        bid.setBidQuantity(10d);
        bid.setBidListId(1);
        bid.setBidListDate(creationDate);
        bid.setBidListDate(bidListDate);
        when(bidListRepository.findAll()).thenReturn(bidListList);
        bidListService = new BidListService(bidListRepository);

    }

    @Test
    @WithMockUser(username = "gui")
    public void getBidListTest() {
        when(bidListRepository.findAll()).thenReturn(bidListList);
        assertEquals(0,bidListService.getBidList().size());
    }

    @Test
    @WithMockUser(username = "gui")
    public void saveBidTest() {
        when(bidListRepository.save(bid)).thenReturn(bid);
        bidListService.saveBid(bid);
        verify(bidListRepository,times(1)).save(bid);
    }

    @Test
    @WithMockUser(username = "gui")
    public void findByIdTest() {
        bid.setBidListId(1);
        when(bidListRepository.findById(1)).thenReturn(Optional.ofNullable(bid));
        bidListService.findById(1);
        verify(bidListRepository,times(1)).findById(1);
    }

    @Test
    @WithMockUser(username = "gui")
    public void updateBidTest() {
        when(bidListRepository.save(bid)).thenReturn(bid);
        when(bidListRepository.findById(1)).thenReturn(Optional.ofNullable(bid));
        bid.setType( "type test bis");
        bidListService.updateBid(1,bid);
        verify(bidListRepository,times(1)).save(bid);
    }

    @Test
    @WithMockUser(username = "gui")
    public void deleteBidTest() {
        bidListService.deleteBid(bid);
        verify(bidListRepository,times(1)).delete(bid);
    }

}
