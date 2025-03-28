package com.dochkas.schcRuleId.generators;

import com.dochkas.schcRuleId.Seed;
import com.dochkas.schcRuleId.model.RulePrefix;

import java.util.ArrayList;
import java.util.function.Supplier;

public abstract class PriorityPrefixGenerator extends BasePrefixGenerator {

    private Supplier<Seed> seedSupplier;

    @Override
    public ArrayList<RulePrefix> generatePrefixes(int n) {
        return null;
    }
}
