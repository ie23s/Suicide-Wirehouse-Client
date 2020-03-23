package com.ie23s.android.suicidewarehouse.utils;

import java.nio.ByteBuffer;

public class ByteUtils {

    public static byte[] intToBytes(int x) {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.putInt(0, x);
        return buffer.array();
    }

    public static int bytesToInt(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.put(bytes, 0, bytes.length);
        buffer.flip();//need flip 
        return buffer.getInt();
    }
}