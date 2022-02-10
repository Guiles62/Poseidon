package com.nnk.springboot.controllerTest;


import com.nnk.springboot.controllers.CurveController;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.CurveService;
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
public class CurveControllerTest {

    @Mock
    CurveService curveService;

    @Mock
    CurveController curveController;

    @Autowired
    MockMvc mockMvc;

    private Model model;
    private CurvePoint curvePoint;

    @BeforeEach
    void setup() {
        List<CurvePoint> curvePointList = new ArrayList<>();
        curvePoint.setId(1);
        curvePoint.setCurveId(2);
        curvePoint.setTerm(2.0);
        curvePoint.setValue(10.0);
        curvePointList.add(curvePoint);
        when(curveService.getCurvePointList()).thenReturn(curvePointList);
    }

    @Test
    @WithMockUser(username = "gui")
    public void homeTest() throws Exception {
        mockMvc.perform(get("/curvePoint/list")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "gui")
    public void addCurvePointFormTest() throws Exception {
        mockMvc.perform(get("/curvePoint/add")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "gui")
    public void validateTest() throws Exception {
        mockMvc.perform(post("/curvePoint/validate")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "gui")
    public void showUpdateFormTest() throws Exception {
        mockMvc.perform(get("/curvePoint/update/1")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "gui")
    public void updateCurvePointTest() throws Exception {
        mockMvc.perform(post("/curvePoint/update/1")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "gui")
    public void deleteCurvePointTest() throws Exception {
        curveController.deleteCurvePoint(1,model);
        assertEquals(0, curveService.getCurvePointList().size());
    }
}
