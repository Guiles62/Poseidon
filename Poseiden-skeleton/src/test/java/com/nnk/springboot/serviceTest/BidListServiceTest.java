package com.nnk.springboot.serviceTest;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.services.BidListService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BidListServiceTest {

    @Autowired
    private BidListService bidListService;

    @Mock
    private BidListRepository bidListRepository;

    @InjectMocks
    private BidList bid;
    List<BidList> bidListList = new ArrayList<>();

    @BeforeEach
    void setup() {
        bidListService = new BidListService(bidListRepository);
    }

    @Test
    @WithMockUser("gui")
    public void getBidListTest() {
        when(bidListRepository.findAll()).thenReturn(bidListList);
        assertEquals(0,bidListService.getBidList().size());
    }

    @Test
    @WithMockUser(username = "gui")
    public void saveBidTest() {
        bid.setAccount("account Test");
        bid.setType("type Test");
        bid.setBidQuantity(10d);
        bid.setBidListId(1);
        bidListService.saveBid(bid);
        verify(bidListRepository,times(1)).save(any(BidList.class));
    }

    @Test
    @WithMockUser(username = "gui")
    public void findByIdTest() {
        int id = 1;
        when(bidListRepository.findById(id)).thenReturn(Optional.ofNullable(bid));
        bidListService.findById(1);
        verify(bidListRepository,times(1)).findById(id);
    }
}
