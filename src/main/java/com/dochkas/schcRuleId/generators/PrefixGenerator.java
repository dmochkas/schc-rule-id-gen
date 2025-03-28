package com.dochkas.schcRuleId.generators;

import com.dochkas.schcRuleId.model.RulePrefix;

import java.util.ArrayList;

public interface PrefixGenerator {

    ArrayList<RulePrefix> generatePrefixes(int n);
}
