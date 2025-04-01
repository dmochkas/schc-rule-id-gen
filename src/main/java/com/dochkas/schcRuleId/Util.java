package com.dochkas.schcRuleId;

import java.util.ArrayList;

public class Util {

    public static int BYTE_BITS = 8;

    public static String bytesToBitString(byte[] bytes, int length) {
        final StringBuilder bitString = new StringBuilder();
        final int bytesToUse = length / BYTE_BITS;
        final int lengthResidue = length % BYTE_BITS;

        for (int j = 0 ; j < bytesToUse; j++) {
            for (int i = BYTE_BITS - 1; i >= 0; i--) {
                bitString.append((bytes[j] >> i) & 1);
            }
        }

        for (int i = BYTE_BITS - 1; i > BYTE_BITS - lengthResidue - 1; i--) {
            bitString.append((bytes[bytesToUse] >> i) & 1);
        }
        return bitString.toString();
    }

    public static byte[] toByteArray(ArrayList<Byte> byteList) {
        byte[] byteArray = new byte[byteList.size()];
        for (int i = 0; i < byteList.size(); i++) {
            byteArray[i] = byteList.get(i);
        }
        return byteArray;
    }
}
