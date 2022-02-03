package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RuleNameService {

    @Autowired
    RuleNameRepository ruleNameRepository;

    private final static Logger logger = LogManager.getLogger("RuleNameService");

    public List<RuleName> getRuleNameList() {
        try {
            logger.info("getRuleNameList");
        } catch (Exception ex) {
            logger.error("getRuleNameList error");
        }
        return ruleNameRepository.findAll();
    }

    public RuleName saveRuleName(RuleName ruleName) {
        try {
            logger.info("saveRuleName");
        } catch (Exception ex) {
            logger.error("saveRuleName error");
        }
        return ruleNameRepository.save(ruleName);
    }

    public Optional<RuleName> findById (int id) {
        try {
            logger.info("findById");
        } catch (Exception ex) {
            logger.error("findById error");
        }
        return ruleNameRepository.findById(id);
    }

    public RuleName updateRuleName (int id, RuleName ruleName) {
        RuleName rule = ruleNameRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid ruleName Id:" + id));
        try {
            logger.info("updateRuleName");
            rule.setName(ruleName.getName());
            rule.setDescription(ruleName.getDescription());
            rule.setJson(ruleName.getJson());
            rule.setTemplate(ruleName.getTemplate());
            rule.setSqlStr(ruleName.getSqlStr());
            rule.setSqlPart(ruleName.getSqlPart());
        } catch (Exception ex) {
            logger.error("updateRuleName error");
        }
        return ruleNameRepository.save(rule);
    }

    public void delete(RuleName ruleName) {
        try {
            logger.info("delete");
        } catch (Exception ex) {
            logger.error("delete error");
        }
        ruleNameRepository.delete(ruleName);
    }
}
