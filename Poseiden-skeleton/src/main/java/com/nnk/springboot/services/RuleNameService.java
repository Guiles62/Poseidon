package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * <b>RuleNameService is an interface that will be implemented by RuleNameServiceImpl</b>
 * <p>
 *     contain methods
 *     <ul>
 *         <li>getRuleNameList</li>
 *         <li>saveRuleName</li>
 *         <li>findById</li>
 *         <li>updateRuleName</li>
 *         <li>delete</li>
 *     </ul>
 * </p>
 * @author Guillaume C
 */


public interface RuleNameService {


    Logger logger = LogManager.getLogger("RuleNameService");

    /**
     * Call the repository to find the ruleName List
     * @return Call the repository to find the ruleName List
     */
    List<RuleName> getRuleNameList();

    /**
     * Call repository to save nex ruleName
     * @param ruleName ruleName to save
     * @return call repository to save new ruleName
     */
    RuleName saveRuleName(RuleName ruleName);

    /**
     * Call repository to find a ruleName by id
     * @param id id of the ruleName to find
     * @return call repository to find ruleName by id
     */
    Optional<RuleName> findById (int id);

    /**
     * Call repository to update a ruleName
     * @param id id of the ruleName to update
     * @param ruleName ruleName with changes to update
     * @return call repository to save ruleName with changes
     */
    RuleName updateRuleName (int id, RuleName ruleName);

    /**
     * Call repository to delete ruleName
     * @param ruleName ruleName to delete
     */
    void delete(RuleName ruleName);
}
