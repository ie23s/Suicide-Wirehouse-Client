package com.ie23s.android.suicidewarehouse.utils;

import android.util.Log;

import org.junit.Test;

import javax.crypto.spec.SecretKeySpec;

import static org.junit.Assert.*;

public class AESUtilTest {

    @Test
    public void encrypt() {
    }

    @Test
    public void decrypt() {
    }

    @Test
    public void nextSec() {
    }

    @Test
    public void createSecretKey() {


        SecretKeySpec aesKey = AESUtil.createSecretKey();
        Log.d("AES KEY TEST", aesKey.getEncoded().length + " ");

    }
}