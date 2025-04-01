package com.dochkas.schcRuleId.generators;

import com.dochkas.schcRuleId.ConfigListSeed;
import com.dochkas.schcRuleId.ShrinkSeed;
import com.dochkas.schcRuleId.exceptions.IllegalSeedException;
import com.dochkas.schcRuleId.model.BitSeqBase;
import com.dochkas.schcRuleId.model.Pool;
import com.dochkas.schcRuleId.model.RulePrefix;

import java.util.List;
import java.util.function.Supplier;

public class ConfigListPriorityPrefixGenerator extends BasePrefixGenerator<ConfigListSeed> {

    private final PrefixGenerator shrinkGenerator;

    // TODO: Validate pool!!!
    public ConfigListPriorityPrefixGenerator(Pool pool, Supplier<ConfigListSeed> seedSupplier) {
        super(pool, seedSupplier);

        this.shrinkGenerator = new ShrinkSeedPriorityPrefixGenerator(pool, () -> configListToShrinkSeed(seedSupplier.get()));
    }

    @Override
    public List<RulePrefix> generatePrefixes(int n) {
        return shrinkGenerator.generatePrefixes(n);
    }

    @Override
    public void validateSeed(ConfigListSeed seed, int n) throws IllegalSeedException {

    }

    private ShrinkSeed configListToShrinkSeed(ConfigListSeed seed) {
        final int[] configList = seed.getValue();
        final BitSeqBase.BitSeqBuilder shrinkBuilder = BitSeqBase.BitSeqBuilder.builder();

        for (int i = 0; i < configList.length; i++) {
            final int constraint = configList[i];
            if (constraint > 0) {
                for (int j = 0; j < constraint - 1; j++) {
                    shrinkBuilder.appendBit(0);
                }
                shrinkBuilder.appendBit(1);
            }
        }

        return new ShrinkSeed(shrinkBuilder.build());
    }
}
