package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.List;
import java.util.Optional;



/**
 * <b>CurveService is an interface that will be implemented by CurveServiceImpl</b>
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


public interface CurveService {

     Logger logger = LogManager.getLogger("CurveService");

    /**
     * call repository to find all curvePoint
     * @return
     */
    List<CurvePoint> getCurvePointList();

    /**
     * call repository to save a curvePoint
     * @param curvePoint curvePoint to save
     * @return call repository to save curvePoint
     */
    CurvePoint saveCurvePoint (CurvePoint curvePoint);

    /**
     * call repository to find a curvePoint by Id
     * @param id id of the curvePoint to find
     * @return curvePoint find
     */

    Optional<CurvePoint> getCurvePointById(int id);

    /**
     * call repository to update a curvePoint by Id
     * @param id id of the curvePoint to update
     * @param curvePoint curvePoint to update with changes
     * @return call repository to save curvePoint with changes
     */
    CurvePoint updateCurvePoint(int id, CurvePoint curvePoint);

    /**
     * call repository to delete a curvePoint
     * @param curvePoint
     */
    void delete(CurvePoint curvePoint);

}
