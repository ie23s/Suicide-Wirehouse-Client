package com.ie23s.android.suicidewarehouse.io;

public class UnsignedExeption extends Exception {
    private int code;

    public UnsignedExeption(int code) {
        super("Connection error, code: " + code);

    }

    public int getCode() {
        return code;
    }
}
