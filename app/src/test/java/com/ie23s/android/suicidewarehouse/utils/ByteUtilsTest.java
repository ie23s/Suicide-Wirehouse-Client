package com.ie23s.android.suicidewarehouse.utils;

import org.junit.Test;

import java.util.Arrays;

public class ByteUtilsTest {
    private int l1 = 1234;
    private int l2 = 0;
    private int l3 = 739417042;

    private byte[] b1 = {0, 0, 4, -46};
    private byte[] b2 = {0, 0, 0, 0};
    private byte[] b3 = {44, 18, -101, -46};

    @Test
    public void intToBytes() throws Exception {
        if (!Arrays.equals(b1, ByteUtils.intToBytes(l1)))
            throw new Exception("First int");
        if (!Arrays.equals(b2, ByteUtils.intToBytes(l2)))
            throw new Exception("Second int");
        if (!Arrays.equals(b3, ByteUtils.intToBytes(l3)))
            throw new Exception("Third int");

        System.out.println(Arrays.toString(b1));
        System.out.println(Arrays.toString(b2));
        System.out.println(Arrays.toString(b3));
    }

    @Test
    public void bytesToInt() throws Exception {
        if (ByteUtils.bytesToInt(b1) != l1)
            throw new Exception("First array");
        if (ByteUtils.bytesToInt(b2) != l2)
            throw new Exception("Second array");
        if (ByteUtils.bytesToInt(b3) != l3)
            throw new Exception("Third array");
        System.out.println(ByteUtils.bytesToInt(b1));
        System.out.println(ByteUtils.bytesToInt(b2));
        System.out.println(ByteUtils.bytesToInt(b3));
    }
}