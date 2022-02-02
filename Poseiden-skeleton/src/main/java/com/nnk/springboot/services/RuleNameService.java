package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RuleNameService {

    @Autowired
    RuleNameRepository ruleNameRepository;

    public List<RuleName> getRuleNameList() {
        return ruleNameRepository.findAll();
    }

    public RuleName saveRuleName(RuleName ruleName) {
        return ruleNameRepository.save(ruleName);
    }

    public Optional<RuleName> findById (int id) {
        return ruleNameRepository.findById(id);
    }

    public RuleName updateRuleName (int id, RuleName ruleName) {
        RuleName rule = ruleNameRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid ruleName Id:" + id));
        rule.setName(ruleName.getName());
        rule.setDescription(ruleName.getDescription());
        rule.setJson(ruleName.getJson());
        rule.setTemplate(ruleName.getTemplate());
        rule.setSqlStr(ruleName.getSqlStr());
        rule.setSqlPart(ruleName.getSqlPart());
        return ruleNameRepository.save(rule);
    }

    public void delete(RuleName ruleName) {
        ruleNameRepository.delete(ruleName);
    }
}
