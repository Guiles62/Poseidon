package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CurveService {

    @Autowired
    CurvePointRepository curvePointRepository;

    public List<CurvePoint> getCurvePointList () {
       return curvePointRepository.findAll();
    }

    public CurvePoint saveCurvePoint (CurvePoint curvePoint) {
        return curvePointRepository.save(curvePoint);
    }

    public Optional<CurvePoint> getCurvePointById(int id) {
        return curvePointRepository.findById(id);
    }

    public CurvePoint updateCurvePoint(int id, CurvePoint curvePoint) {
        curvePoint.setCurveId(id);
        curvePoint.setTerm(curvePoint.getTerm());
        curvePoint.setValue(curvePoint.getValue());
        return curvePointRepository.save(curvePoint);
    }

    public void delete(CurvePoint curvePoint) {
        curvePointRepository.delete(curvePoint);
    }
}
