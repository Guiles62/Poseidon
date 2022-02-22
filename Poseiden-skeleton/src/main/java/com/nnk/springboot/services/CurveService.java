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

/**
 * <b>CurveService is the class that calls the CurvePointRepository</b>
 * <p>
 *     contain methods
 *     <ul>
 *         <li>getCurvePointList</li>
 *         <li>saveCurvePoint</li>
 *         <li>getCurvePointById</li>
 *         <li>updateCurvePoint</li>
 *         <li>delete</li>
 *     </ul>
 * </p>
 * @author Guillaume C
 */

@Service
public class CurveService {

    @Autowired
    CurvePointRepository curvePointRepository;

    public CurveService(CurvePointRepository curvePointRepository) {
        this.curvePointRepository = curvePointRepository;
    }

    private final static Logger logger = LogManager.getLogger("CurveService");

    /**
     * call repository to find all curvePoint
     * @return
     */
    public List<CurvePoint> getCurvePointList () {
           logger.info("Call repository to get CurvePoint List");
        return curvePointRepository.findAll();
    }

    /**
     * call repository to save a curvePoint
     * @param curvePoint curvePoint to save
     * @return call repository to save curvePoint
     */
    public CurvePoint saveCurvePoint (CurvePoint curvePoint) {
            logger.info("Call repository to save new CurvePoint");
            Timestamp creationDate = new Timestamp(System.currentTimeMillis());
            curvePoint.setCreationDate(creationDate);
        return curvePointRepository.save(curvePoint);
    }

    /**
     * call repository to find a curvePoint by Id
     * @param id id of the curvePoint to find
     * @return curvePoint find
     */

    public Optional<CurvePoint> getCurvePointById(int id) {
            logger.info("Call repository to find curvePoint by id");
        return curvePointRepository.findById(id);
    }

    /**
     * call repository to update a curvePoint by Id
     * @param id id of the curvePoint to update
     * @param curvePoint curvePoint to update with changes
     * @return call repository to save curvePoint with changes
     */
    public CurvePoint updateCurvePoint(int id, CurvePoint curvePoint) {
            CurvePoint curve = curvePointRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid curvePoint Id:" + id));
            logger.info("call repository to update curvePoint");
            Timestamp asOfDate = new Timestamp(System.currentTimeMillis());
            curve.setCurveId(curvePoint.getCurveId());
            curve.setAsOfDate(asOfDate);
            curve.setTerm(curvePoint.getTerm());
            curve.setValue(curvePoint.getValue());
            return curvePointRepository.save(curve);
    }

    /**
     * call repository to delete a curvePoint
     * @param curvePoint
     */
    public void delete(CurvePoint curvePoint) {
            logger.info("call repository to delete curvePoint");
        curvePointRepository.delete(curvePoint);
    }
}
