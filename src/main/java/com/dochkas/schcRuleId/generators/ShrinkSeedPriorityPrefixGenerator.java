package com.dochkas.schcRuleId.generators;

import com.dochkas.schcRuleId.ShrinkSeed;
import com.dochkas.schcRuleId.exceptions.IllegalSeedException;
import com.dochkas.schcRuleId.model.BitSeqBase;
import com.dochkas.schcRuleId.model.Pool;
import com.dochkas.schcRuleId.model.RulePrefix;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ShrinkSeedPriorityPrefixGenerator extends BasePrefixGenerator<ShrinkSeed> {

    public ShrinkSeedPriorityPrefixGenerator(Pool pool, Supplier<ShrinkSeed> seedSupplier) {
        super(pool, seedSupplier);
    }

    @Override
    public List<RulePrefix> generatePrefixes(int n) {
        final List<RulePrefix> allocationTable = new ArrayList<>(n);

        final BitSeqBase seed = this.seedFactory.apply(n).getValue();
        boolean allocFlag = true;
        int allocCounter = 0;
        int current = 0;
        while (allocationTable.size() < n) {
            final int residue = n - current;
            if (allocFlag) {
                allocCounter++;
            }
            final boolean shrinkFlag = allocCounter == residue || this.getShrinkFlag(seed, current + allocCounter - 1);


            if (shrinkFlag) {
                final int poolSize = pool.size();
                if (poolSize >= allocCounter + (allocCounter == residue ? 0 : 1)) {
                    this.allocate(allocCounter, allocationTable);
                    current += allocCounter;
                    allocCounter = 0;
                    allocFlag = true;
                } else {
                    pool.split(poolSize * 2 - allocCounter - (allocCounter == residue ? 0 : 1));
                    allocFlag = false;
                }
            }
        }
        return allocationTable;
    }

    @Override
    public void validateSeed(ShrinkSeed seed, int n) throws IllegalSeedException {

    }

    private void allocate(int n, List<RulePrefix> allocationTable) {
        if (n > pool.size()) {
            throw new IllegalArgumentException("Can't allocate " + n + " values from the pool with " + pool.size() + " values");
        }

        for (int i = 0; i < n; i++) {
            final BitSeqBase prefix = pool.pop();
            if (!(prefix instanceof RulePrefix)) {
                throw new IllegalStateException("Pool contains Rule ID before prefixes are initialized!");
            }

            allocationTable.add((RulePrefix) prefix);
        }
    }

    private boolean getShrinkFlag(BitSeqBase seed, int index) {
        return index < seed.getLength() && seed.getBitAt(index);
    }
}
