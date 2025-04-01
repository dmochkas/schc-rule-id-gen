package com.dochkas.schcRuleId;

import com.dochkas.schcRuleId.generators.BasePrefixGenerator;
import com.dochkas.schcRuleId.model.BitSeqBase;
import com.dochkas.schcRuleId.model.Pool;
import com.dochkas.schcRuleId.model.RulePrefix;
import org.junit.jupiter.api.Test;

public class UtilTest {



    @Test
    void testBitStringConversion() {
        final String result = Util.bytesToBitString(new byte[]{6}, 8);

        System.out.println(result);
        System.out.println(Byte.parseByte(result, 2));
    }

    @Test
    void testIntToByteConversion() {
        final byte result = (byte) 130;

        System.out.println(result);
    }

    @Test
    void testBitIdBuilder() {
        final BitSeqBase bitId = BitSeqBase.BitSeqBuilder
                .builder()
                .appendByte(168)
                .appendByte(255)
                .build();

        final BitSeqBase bitId2 = BitSeqBase.BitSeqBuilder
                .builder()
                .appendByte(168)
                .appendByte(255)
                .buildAndTruncate(5);

        System.out.println(bitId);
        System.out.println(bitId2);
    }

    @Test
    void bitShiftTest() {
        boolean fragment = true;
        int pos = 7;

        byte toChange = 2;
        byte result = (byte) (toChange | (Boolean.compare(fragment, false) << (Util.BYTE_BITS - pos % Util.BYTE_BITS - 1)));

        final BitSeqBase bitId = BitSeqBase.BitSeqBuilder
                .builder()
                .appendByte(result)
                .buildAndTruncate(pos + 1);

        System.out.println(bitId);
    }

    @Test
    void bitAddTest() {
        final BitSeqBase bitId1 = BitSeqBase.BitSeqBuilder
                .builder()
                .appendByte(168)
                .appendBit(true)
                .appendBit(false)
                .appendBit(false)
                .appendBit(true)
                .appendBit(false)
                .appendBit(true)
                .buildAndTruncate(14);

        System.out.println(bitId1);
    }

    @Test
    void compareTest() {
        final BitSeqBase bitId1 = BitSeqBase.BitSeqBuilder
                .builder()
                .appendBit(1)
                .appendBit(1)
                .appendBit(1)
                .build();

        final BitSeqBase bitId2 = BitSeqBase.BitSeqBuilder
                .builder()
                .appendBit(1)
                .appendBit(0)
                .appendBit(0)
                .build();

        System.out.println(bitId1.compareTo(bitId2));
    }

    @Test
    void splitTest() {
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

        System.out.println(pool);

        pool.fullSplit();

        System.out.println(pool);

        pool.fullSplit();

        System.out.println(pool);

        while (pool.size() > 0) {
            System.out.println(pool.pop());
        }

        System.out.println(pool);
    }

    @Test
    void getBitTest() {
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
                .appendBit(1)
                .build();

        System.out.println(Boolean.compare(seed.getBitAt(0), false));
        System.out.println(Boolean.compare(seed.getBitAt(7), false));
        System.out.println(Boolean.compare(seed.getBitAt(8), false));
    }
}
