package com.dochkas.schcRuleId.generators;

import com.dochkas.schcRuleId.Seed;
import com.dochkas.schcRuleId.exceptions.IllegalSeedException;
import com.dochkas.schcRuleId.model.Pool;

import java.util.function.Function;
import java.util.function.Supplier;

public abstract class BasePrefixGenerator<T extends Seed<?>> implements PrefixGenerator {

    protected Function<Integer, T> seedFactory;

    protected final Pool pool;

    public BasePrefixGenerator(Pool pool, Supplier<T> seedSupplier) {
        this.pool = pool;
        this.seedFactory = prefixes -> {
            final T seed = seedSupplier.get();
            this.validateSeed(seed, prefixes);
            return seed;
        };
    }

    public abstract void validateSeed(T seed, int n) throws IllegalSeedException;
}
