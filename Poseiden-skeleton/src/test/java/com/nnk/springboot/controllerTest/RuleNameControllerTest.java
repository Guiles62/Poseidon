package com.nnk.springboot.controllerTest;

import com.nnk.springboot.controllers.RuleNameController;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.RuleNameService;
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

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RuleNameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    RuleNameService ruleNameService;

    @Mock
    RuleNameController ruleNameController;

    private RuleName ruleName;
    private Model model;



    @BeforeEach
    void setup() {
        List<RuleName> ruleNameList = new ArrayList<>();
        ruleName.setId(1);
        ruleNameList.add(ruleName);
        when(ruleNameService.saveRuleName(ruleName)).thenReturn(ruleName);
        when(ruleNameService.getRuleNameList()).thenReturn(ruleNameList);
    }

    @Test
    @WithMockUser(username = "gui")
    public void homeTest() throws Exception {
        mockMvc.perform(get("/ruleName/list")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "gui")
    public void addRuleFormTest() throws Exception {
        mockMvc.perform(get("/ruleName/add")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "gui")
    public void validateTest() throws Exception {
        mockMvc.perform(post("/ruleName/validate")).andExpect(status().isFound()).andExpect(redirectedUrl("/ruleName/list"));
    }

    @Test
    @WithMockUser(username = "gui")
    public void showUpdateFormTest() throws Exception {
        when(ruleNameService.findById(1)).thenReturn(Optional.ofNullable(ruleName));
        ruleNameController.showUpdateForm(1,model);
        assertEquals(ruleName,ruleNameController.showUpdateForm(1,model));
    }

    @Test
    @WithMockUser(username = "gui", authorities = "ADMIN")
    public void updateRuleNameTest() throws Exception {
        mockMvc.perform(post("/ruleName/update/1")).andExpect(status().isFound()).andExpect(redirectedUrl("/ruleName/list"));
    }

    @Test
    @WithMockUser(username = "gui", authorities = "ADMIN")
    public void deleteRuleNameTest() throws Exception {
        mockMvc.perform(get("/ruleName/delete/1")).andExpect(status().isFound()).andExpect(redirectedUrl("/ruleName/list"));
    }
}
