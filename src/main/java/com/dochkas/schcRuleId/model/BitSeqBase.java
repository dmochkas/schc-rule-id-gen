package com.dochkas.schcRuleId.model;

import com.dochkas.schcRuleId.Util;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

// TODO: Support adding bits in between the bytes (for now possible only in the end)
public class BitSeqBase implements Comparable<BitSeqBase> {

    private final byte[] bitData;

    private final int length;

    public BitSeqBase(int length, byte... bytes) {
        this.bitData = Arrays.copyOf(bytes, bytes.length);
        this.length = length;
    }

    public boolean getBitAt(int index) {
        if (index < 0 || index >= length) {
            throw new IndexOutOfBoundsException("Can't get bit at pos " + index);
        }

        final int skipBytes = index / Util.BYTE_BITS;
        final int shiftBits =  Util.BYTE_BITS - index % Util.BYTE_BITS - 1;
        final byte targetByte = bitData[skipBytes];

        return ((targetByte >> shiftBits) & 1) != 0;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        BitSeqBase that = (BitSeqBase) o;
        return length == that.length && Objects.deepEquals(bitData, that.bitData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Arrays.hashCode(bitData), length);
    }

    @Override
    public int compareTo(BitSeqBase bitSeqBase) {
        if (this.length < bitSeqBase.length) {
            return -1;
        }

        if (this.length > bitSeqBase.length) {
            return 1;
        }

        for (int i = 0; i < this.bitData.length; i++) {
            if (this.bitData[i] == bitSeqBase.bitData[i]) {
                continue;
            }

            return (this.bitData[i] & 0xFF) - (bitSeqBase.bitData[i] & 0xFF);
        }

        return 0;
    }

    @Override
    public String toString() {
        return Util.bytesToBitString(bitData, length);
    }

    public byte[] getBitData() {
        return bitData;
    }

    public int getLength() {
        return length;
    }

    public static class BitSeqBuilder {

        private final ArrayList<Byte> stagingSequence = new ArrayList<>();

        private int pos = 0;

        private BitSeqBuilder() {}

        public static BitSeqBuilder builder() {
            return new BitSeqBuilder();
        }

        public static BitSeqBuilder fromBitId(BitSeqBase bitId) {
            final BitSeqBuilder builder = new BitSeqBuilder();
            for (int i = 0; i < bitId.bitData.length; i++) {
                builder.stagingSequence.add(bitId.bitData[i]);
            }
            builder.pos = bitId.length;
            return builder;
        }

        public BitSeqBuilder appendBit(int fragment) {
            if (fragment != 0 && fragment != 1) {
                throw new IllegalArgumentException("Bit can be either 0 or 1");
            }

            byte toChange;
            if (pos % Util.BYTE_BITS == 0) {
                toChange = (byte) 0;
                stagingSequence.add(toChange);
            } else {
                toChange = stagingSequence.get(stagingSequence.size() - 1);
            }
            byte assign = (byte) (toChange | (fragment << (Util.BYTE_BITS - pos % Util.BYTE_BITS - 1)));
            stagingSequence.set(stagingSequence.size() - 1, assign);
            pos++;
            return this;
        }

        public BitSeqBuilder appendBit(boolean fragment) {
            return this.appendBit(Boolean.compare(fragment, false));
        }

        public BitSeqBuilder appendByte(byte fragment) {
            stagingSequence.add(fragment);
            pos+=Util.BYTE_BITS;
            return this;
        }

        public BitSeqBuilder appendByte(int fragment) {
            stagingSequence.add((byte) fragment);
            pos+=Util.BYTE_BITS;
            return this;
        }


        public BitSeqBuilder appendInt(int fragment) {
            for (int i = 3; i >= 0; i--) {
                stagingSequence.add((byte) (fragment >> (i * Util.BYTE_BITS)));
            }
            pos += 4*Util.BYTE_BITS;
            return this;
        }

        public BitSeqBase buildAndTruncate(int length) {
            if (pos < length) {
                throw new IllegalArgumentException("Can't truncate to bigger size: " + pos + " < " + length);
            }
            return new BitSeqBase(length, Util.toByteArray(stagingSequence));
        }

        public BitSeqBase build() {
            return new BitSeqBase(pos, Util.toByteArray(stagingSequence));
        }

        public <T extends BitSeqBase> T build(Class<T> childClass) {
            try {
                return childClass.getConstructor(int.class, byte[].class).newInstance(pos, Util.toByteArray(stagingSequence));
            } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                     IllegalAccessException e) {
                throw new RuntimeException("Error creating BitSeqBase instance!", e);
            }
        }
    }
}
