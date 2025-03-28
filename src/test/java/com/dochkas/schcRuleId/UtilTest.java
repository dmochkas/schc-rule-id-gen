package com.dochkas.schcRuleId;

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
}
