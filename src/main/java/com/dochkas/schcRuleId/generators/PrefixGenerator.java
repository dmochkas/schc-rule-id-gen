package com.dochkas.schcRuleId.generators;

import com.dochkas.schcRuleId.model.RulePrefix;

import java.util.List;

public interface PrefixGenerator {

    List<RulePrefix> generatePrefixes(int n);
}
