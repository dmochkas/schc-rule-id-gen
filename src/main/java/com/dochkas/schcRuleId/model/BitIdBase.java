package com.dochkas.schcRuleId.model;

import com.dochkas.schcRuleId.Util;

import java.math.BigInteger;
import java.util.ArrayList;

public class BitIdBase implements Comparable<BitIdBase> {

    private static final int DEFAULT_BIT_SPAN = 1;

    private final byte[] bitData;

    private final int length;

    public BitIdBase() {
        this.bitData = new byte[DEFAULT_BIT_SPAN];
        this.length = 1;
    }

    public static BitIdBase fromInt() {
        return new BitIdBase();
    }

    @Override
    public int compareTo(BitIdBase bitIdBase) {
        if (this.length < bitIdBase.length) {
            return 1;
        }

        if (this.length > bitIdBase.length) {
            return -1;
        }

        for (int i = 0; i < this.bitData.length; i++) {
            if (this.bitData[i] == bitIdBase.bitData[i]) {
                continue;
            }

            return this.bitData[i] >= 0 ? bitIdBase.bitData[i] - this.bitData[i] : this.bitData[i] - bitIdBase.bitData[i];
        }

        return 0;
    }

    @Override
    public String toString() {
        return Util.bytesToBitString(bitData, length);
    }

    public static class BitIdBuilder {

        private final ArrayList<Byte> stagingValues = new ArrayList<>();

        private BitIdBuilder() {}

        public static BitIdBuilder builder() {
            return new BitIdBuilder();
        }

        public BitIdBuilder appendByte(byte fragment) {
            stagingValues.add(fragment);
            return this;
        }

        public BitIdBuilder appendByte(byte fragment, int length) {
            stagingValues.add(fragment);
            return this;
        }

        public BitIdBuilder appendInt(int fragment) {
            stagingValues.add((byte) fragment);
            return this;
        }
    }
}
