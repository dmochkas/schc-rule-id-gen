package com.dochkas.schcRuleId;

import com.dochkas.schcRuleId.generators.ConfigListPriorityPrefixGenerator;
import com.dochkas.schcRuleId.generators.PrefixGenerator;
import com.dochkas.schcRuleId.model.BitSeqBase;
import com.dochkas.schcRuleId.model.Pool;
import com.dochkas.schcRuleId.model.RulePrefix;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ConfigListGenTest {

    @Test
    void baseTest() {
        final Pool pool = new Pool();

        final BitSeqBase bitId1 = BitSeqBase.BitSeqBuilder
                .builder()
                .appendBit(1)
                .build(RulePrefix.class);

        final BitSeqBase bitId2 = BitSeqBase.BitSeqBuilder
                .builder()
                .appendBit(0)
                .build(RulePrefix.class);

        pool.add(bitId1);
        pool.add(bitId2);

        final int[] seed = new int[] { 1, 1, 0, 4 };

        final PrefixGenerator generator = new ConfigListPriorityPrefixGenerator(pool, () -> new ConfigListSeed(seed));

        final int prefixes = 6;

        final List<RulePrefix> rulePrefixes = generator.generatePrefixes(prefixes);

        System.out.println(rulePrefixes);
    }
}
