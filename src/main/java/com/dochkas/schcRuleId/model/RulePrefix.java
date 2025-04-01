package com.dochkas.schcRuleId.model;

public class RulePrefix extends BitSeqBase {

    private boolean assigned = false;

    public RulePrefix(int length, byte... bytes) {
        super(length, bytes);
    }
}
