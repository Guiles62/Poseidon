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

    public RuleNameService(RuleNameRepository ruleNameRepository) {
        this.ruleNameRepository = ruleNameRepository;
    }

    private final static Logger logger = LogManager.getLogger("RuleNameService");

    public List<RuleName> getRuleNameList() {
            logger.info("getRuleNameList");
        return ruleNameRepository.findAll();
    }

    public RuleName saveRuleName(RuleName ruleName) {
            logger.info("saveRuleName");
        return ruleNameRepository.save(ruleName);
    }

    public Optional<RuleName> findById (int id) {
            logger.info("findById");
        return ruleNameRepository.findById(id);
    }

    public RuleName updateRuleName (int id, RuleName ruleName) {
        RuleName rule = ruleNameRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid ruleName Id:" + id));
            logger.info("updateRuleName");
            rule.setName(ruleName.getName());
            rule.setDescription(ruleName.getDescription());
            rule.setJson(ruleName.getJson());
            rule.setTemplate(ruleName.getTemplate());
            rule.setSqlStr(ruleName.getSqlStr());
            rule.setSqlPart(ruleName.getSqlPart());
        return ruleNameRepository.save(rule);
    }

    public void delete(RuleName ruleName) {
            logger.info("delete");
        ruleNameRepository.delete(ruleName);
    }
}
