package com.dochkas.schcRuleId;

public class ConfigListSeed implements Seed<int[]> {

    private final int[] configList;

    public ConfigListSeed(int[] configList) {
        this.configList = configList;
    }

    @Override
    public int[] getValue() {
        return configList;
    }
}
