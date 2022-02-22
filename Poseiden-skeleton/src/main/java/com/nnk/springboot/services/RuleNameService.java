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
 * <b>RuleNameService is the class that calls the RuleNameRepository</b>
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

@Service
public class RuleNameService {

    @Autowired
    RuleNameRepository ruleNameRepository;

    public RuleNameService(RuleNameRepository ruleNameRepository) {
        this.ruleNameRepository = ruleNameRepository;
    }

    private final static Logger logger = LogManager.getLogger("RuleNameService");

    /**
     * Call the repository to find the ruleName List
     * @return Call the repository to find the ruleName List
     */
    public List<RuleName> getRuleNameList() {
            logger.info("Call the repository to find the ruleName List");
        return ruleNameRepository.findAll();
    }

    /**
     * Call repository to save nex ruleName
     * @param ruleName ruleName to save
     * @return call repository to save new ruleName
     */
    public RuleName saveRuleName(RuleName ruleName) {
            logger.info("Call repository to save nex ruleName");
        return ruleNameRepository.save(ruleName);
    }

    /**
     * Call repository to find a ruleName by id
     * @param id id of the ruleName to find
     * @return call repository to find ruleName by id
     */
    public Optional<RuleName> findById (int id) {
            logger.info("Call repository to find a ruleName by id");
        return ruleNameRepository.findById(id);
    }

    /**
     * Call repository to update a ruleName
     * @param id id of the ruleName to update
     * @param ruleName ruleName with changes to update
     * @return call repository to save ruleName with changes
     */
    public RuleName updateRuleName (int id, RuleName ruleName) {
        RuleName rule = ruleNameRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid ruleName Id:" + id));
            logger.info("Call repository to update a ruleName");
            rule.setName(ruleName.getName());
            rule.setDescription(ruleName.getDescription());
            rule.setJson(ruleName.getJson());
            rule.setTemplate(ruleName.getTemplate());
            rule.setSqlStr(ruleName.getSqlStr());
            rule.setSqlPart(ruleName.getSqlPart());
        return ruleNameRepository.save(rule);
    }

    /**
     * Call repository to delete ruleName
     * @param ruleName ruleName to delete
     */
    public void delete(RuleName ruleName) {
            logger.info("Call repository to delete ruleName");
        ruleNameRepository.delete(ruleName);
    }
}
