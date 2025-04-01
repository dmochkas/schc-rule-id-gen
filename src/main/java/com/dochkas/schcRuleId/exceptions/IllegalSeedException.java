package com.dochkas.schcRuleId.exceptions;

import com.dochkas.schcRuleId.Seed;

public class IllegalSeedException extends RuntimeException {

    public IllegalSeedException(Seed<?> seed) {
        super(seed.getValue().toString());
    }
}
