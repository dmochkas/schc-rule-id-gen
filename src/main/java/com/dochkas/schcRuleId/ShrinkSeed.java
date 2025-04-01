package com.dochkas.schcRuleId;

import com.dochkas.schcRuleId.model.BitSeqBase;

public class ShrinkSeed implements Seed<BitSeqBase> {

    private final BitSeqBase seedValue;

    public ShrinkSeed(BitSeqBase seedValue) {
        this.seedValue = seedValue;
    }

    @Override
    public BitSeqBase getValue() {
        return seedValue;
    }
}
