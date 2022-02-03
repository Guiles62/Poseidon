package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.apache.tomcat.jni.Time.now;

@Service
public class CurveService {

    @Autowired
    CurvePointRepository curvePointRepository;

    public List<CurvePoint> getCurvePointList () {
       return curvePointRepository.findAll();
    }

    public CurvePoint saveCurvePoint (CurvePoint curvePoint) {
        Timestamp creationDate = new Timestamp(System.currentTimeMillis());
        curvePoint.setCreationDate(creationDate);
        return curvePointRepository.save(curvePoint);
    }

    public Optional<CurvePoint> getCurvePointById(int id) {
        return curvePointRepository.findById(id);
    }

    public CurvePoint updateCurvePoint(int id, CurvePoint curvePoint) {
        CurvePoint curve = curvePointRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid curvePoint Id:" + id));
        Timestamp asOfDate = new Timestamp(System.currentTimeMillis());
        curve.setCurveId(curvePoint.getCurveId());
        curve.setAsOfDate(asOfDate);
        curve.setTerm(curvePoint.getTerm());
        curve.setValue(curvePoint.getValue());
        return curvePointRepository.save(curve);
    }

    public void delete(CurvePoint curvePoint) {
        curvePointRepository.delete(curvePoint);
    }
}
