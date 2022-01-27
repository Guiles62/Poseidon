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
        ruleName.setId(id);
        ruleName.setName(ruleName.getName());
        ruleName.setSqlPart(ruleName.getSqlPart());
        ruleName.setSqlStr(ruleName.getSqlStr());
        ruleName.setJson(ruleName.getJson());
        ruleName.setTemplate(ruleName.getTemplate());
        ruleName.setDescription(ruleName.getDescription());
        return ruleNameRepository.save(ruleName);
    }

    public void delete(RuleName ruleName) {
        ruleNameRepository.delete(ruleName);
    }
}
