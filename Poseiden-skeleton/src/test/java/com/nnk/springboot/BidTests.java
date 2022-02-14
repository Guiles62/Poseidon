package com.nnk.springboot;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BidTests {

	@Autowired
	private BidListRepository bidListRepository;


	@Test
	@WithMockUser(username = "gui")
	public void bidListTest() {
		BidList bid = new BidList();
		bid.setAccount("account Test");
		bid.setType("type Test");
		bid.setBidQuantity(10d);
		bid.setBidListId(1);


		// Save
		bid = bidListRepository.save(bid);
		Assert.assertNotNull(bid.getBidListId());
		assertTrue(bid.getBidQuantity() == 10d);


		// Update
		bid.setBidQuantity(20d);
		bid = bidListRepository.save(bid);
		assertTrue(bid.getBidQuantity() == 20d);


		// Find
		List<BidList> listResult = bidListRepository.findAll();
		assertTrue(listResult.size() > 0);

		// Delete
		Integer id = bid.getBidListId();
		bidListRepository.delete(bid);
		Optional<BidList> bidList = bidListRepository.findById(id);
		assertFalse(bidList.isPresent());
	}
}
