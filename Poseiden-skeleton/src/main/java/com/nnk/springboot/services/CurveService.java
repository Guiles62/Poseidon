package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    public CurveService(CurvePointRepository curvePointRepository) {
        this.curvePointRepository = curvePointRepository;
    }

    private final static Logger logger = LogManager.getLogger("CurveService");

    public List<CurvePoint> getCurvePointList () {
       try {
           logger.info("getCurvePointList");
       } catch (Exception ex) {
           logger.error("getCurvePointList error");
       }
        return curvePointRepository.findAll();
    }

    public CurvePoint saveCurvePoint (CurvePoint curvePoint) {
        try {
            logger.info("saveCurvePoint");
            Timestamp creationDate = new Timestamp(System.currentTimeMillis());
            curvePoint.setCreationDate(creationDate);
        } catch (Exception ex) {
            logger.error("saveCurvePoint error");
        }
        return curvePointRepository.save(curvePoint);
    }

    public Optional<CurvePoint> getCurvePointById(int id) {
        try {
            logger.info("getCurvePointById");
        } catch (Exception ex) {
            logger.error("getCurvePointById error");
        }
        return curvePointRepository.findById(id);
    }

    public CurvePoint updateCurvePoint(int id, CurvePoint curvePoint) {
            CurvePoint curve = curvePointRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid curvePoint Id:" + id));
        try {
            logger.info("updateCurvePoint");
            Timestamp asOfDate = new Timestamp(System.currentTimeMillis());
            curve.setCurveId(curvePoint.getCurveId());
            curve.setAsOfDate(asOfDate);
            curve.setTerm(curvePoint.getTerm());
            curve.setValue(curvePoint.getValue());
            } catch (Exception ex) {
                logger.error("updateCurvePoint error");
            }
            return curvePointRepository.save(curve);
    }

    public void delete(CurvePoint curvePoint) {
        try {
            logger.info("delete");
        } catch (Exception ex) {
            logger.error("delete error");
        }
        curvePointRepository.delete(curvePoint);
    }
}
