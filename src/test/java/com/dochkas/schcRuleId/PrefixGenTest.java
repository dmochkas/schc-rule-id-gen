package com.dochkas.schcRuleId;

import com.dochkas.schcRuleId.generators.PrefixGenerator;
import com.dochkas.schcRuleId.generators.ShrinkSeedPriorityPrefixGenerator;
import com.dochkas.schcRuleId.model.BitSeqBase;
import com.dochkas.schcRuleId.model.Pool;
import com.dochkas.schcRuleId.model.RulePrefix;
import org.junit.jupiter.api.Test;

import java.util.List;

public class PrefixGenTest {

    @Test
    void generatePrefixTest1() {
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

        // Seed size n prefixes - 1
        final BitSeqBase seed = BitSeqBase.BitSeqBuilder
                .builder()
                .appendBit(1)
                .appendBit(1)
                .appendBit(0)
                .appendBit(0)
                .appendBit(0)
                .appendBit(1)
                .appendBit(0)
                .appendBit(1)
                .build();

        final PrefixGenerator generator = new ShrinkSeedPriorityPrefixGenerator(pool, () -> new ShrinkSeed(seed));

        final int prefixes = 9;

        final List<RulePrefix> rulePrefixes = generator.generatePrefixes(prefixes);

        System.out.println(rulePrefixes);
    }

    @Test
    void generatePrefixTest2() {
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

        final BitSeqBase seed = BitSeqBase.BitSeqBuilder
                .builder()
                .appendBit(0)
                .appendBit(0)
                .appendBit(1)
                .appendBit(0)
//                .appendBit(0)
//                .appendBit(0)
//                .appendBit(0)
//                .appendBit(0)
                .build();

        final PrefixGenerator generator = new ShrinkSeedPriorityPrefixGenerator(pool, () -> new ShrinkSeed(seed));

        final int prefixes = 9;

        final List<RulePrefix> rulePrefixes = generator.generatePrefixes(prefixes);

        System.out.println(rulePrefixes);
    }
}
