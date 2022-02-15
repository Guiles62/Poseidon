package com.nnk.springboot.serviceTest;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.services.RuleNameService;
import org.junit.Before;
import org.junit.Test;
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
public class RuleNameServiceTest {

    @Autowired
    RuleNameService ruleNameService;

    @Mock
    RuleNameRepository ruleNameRepository;

    @InjectMocks
    RuleName ruleName;

    List<RuleName> ruleNameList = new ArrayList<>();

    @Before
    public void setup() {
        ruleName = new RuleName();
        ruleName.setName("name");
        ruleName.setId(1);
        ruleName.setTemplate("template");
        when(ruleNameRepository.findAll()).thenReturn(ruleNameList);
        ruleNameService = new RuleNameService(ruleNameRepository);
    }

    @Test
    @WithMockUser(username = "gui")
    public void getRuleNameListTest() {
        when(ruleNameRepository.findAll()).thenReturn(ruleNameList);
        assertEquals(0,ruleNameService.getRuleNameList().size());
    }

    @Test
    @WithMockUser(username = "gui")
    public void saveRuleNameTest() {
        when(ruleNameRepository.save(ruleName)).thenReturn(ruleName);
        ruleNameService.saveRuleName(ruleName);
        verify(ruleNameRepository,times(1)).save(ruleName);
    }

    @Test
    @WithMockUser(username = "gui")
    public void findByIdTest() {
        when(ruleNameRepository.findById(1)).thenReturn(Optional.ofNullable(ruleName));
        ruleNameService.findById(1);
        verify(ruleNameRepository,times(1)).findById(1);
    }

    @Test
    @WithMockUser(username = "gui")
    public void updateRuleNameTest() {
        when(ruleNameRepository.findById(1)).thenReturn(Optional.ofNullable(ruleName));
        when(ruleNameRepository.save(ruleName)).thenReturn(ruleName);
        ruleNameService.updateRuleName(1,ruleName);
        verify(ruleNameRepository,times(1)).save(ruleName);
    }

    @Test
    @WithMockUser(username = "gui")
    public void deleteTest() {
        ruleNameService.delete(ruleName);
        verify(ruleNameRepository,times(1)).delete(ruleName);
    }
}
