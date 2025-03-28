package com.dochkas.schcRuleId;

public class Util {

    public static int BYTE_BITS = 8;

    public static String bytesToBitString(byte[] bytes, int length) {
        final StringBuilder bitString = new StringBuilder();
        final int bytesToUse = length / BYTE_BITS;
        final int lengthResidue = length % BYTE_BITS;

        for (int j = bytesToUse - 1; j >= 0; j--) {
            final int toPrint = j == 0 && lengthResidue != 0 ? lengthResidue : BYTE_BITS;
            for (int i = toPrint - 1; i >= 0; i--) {
                bitString.append((bytes[j] >> i) & 1);
            }
        }
        return bitString.toString();
    }
}
