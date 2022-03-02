package com.nnk.springboot.serviceTest;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.services.CurveService;
import com.nnk.springboot.services.impl.CurveServiceImpl;
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
public class CurveServiceTest {

    @Autowired
    CurveServiceImpl curveServiceImpl;

    @Mock
    CurvePointRepository curvePointRepository;

    @InjectMocks
    CurvePoint curvePoint;

    List<CurvePoint> curveList = new ArrayList<>();

    @Before
    public void setup() {
        curvePoint = new CurvePoint();
        curvePoint.setCurveId(1);
        curvePoint.setValue(1.0);
        curvePoint.setTerm(1.0);
        curvePoint.setId(1);
        when(curvePointRepository.findAll()).thenReturn(curveList);
        curveServiceImpl = new CurveServiceImpl(curvePointRepository);
    }

    @Test
    @WithMockUser(username = "gui")
    public void getCurvePointListTest() {
        when(curvePointRepository.findAll()).thenReturn(curveList);
        assertEquals(0,curveServiceImpl.getCurvePointList().size());
    }

    @Test
    @WithMockUser(username = "gui")
    public void saveCurvePointTest() {
        when(curvePointRepository.save(curvePoint)).thenReturn(curvePoint);
        curveServiceImpl.saveCurvePoint(curvePoint);
        verify(curvePointRepository,times(1)).save(curvePoint);
    }

    @Test
    @WithMockUser(username = "gui")
    public void getCurvePointByIdTest() {
        when(curvePointRepository.findById(1)).thenReturn(Optional.ofNullable(curvePoint));
        curveServiceImpl.getCurvePointById(1);
        verify(curvePointRepository,times(1)).findById(1);
    }

    @Test
    @WithMockUser(username = "gui")
    public void updateCurvePointTest() {
        when(curvePointRepository.findById(1)).thenReturn(Optional.ofNullable(curvePoint));
        when(curvePointRepository.save(curvePoint)).thenReturn(curvePoint);
        curvePoint.setValue(2.0);
        curveServiceImpl.updateCurvePoint(1,curvePoint);
        verify(curvePointRepository,times(1)).save(curvePoint);
    }

    @Test
    @WithMockUser(username = "gui")
    public void deleteTest() {
        curveServiceImpl.delete(curvePoint);
        verify(curvePointRepository,times(1)).delete(curvePoint);
    }
}
