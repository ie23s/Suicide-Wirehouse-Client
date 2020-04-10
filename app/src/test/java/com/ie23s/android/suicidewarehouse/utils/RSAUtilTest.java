package com.ie23s.android.suicidewarehouse.utils;

import org.junit.Test;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;


/**
 * Android RSA encryption test
 * <p>
 * Public  key: MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCu5z/XcYbtZG/oNhJalO0kDYXFxNSZby3HBMfkYQpMlxhnI4caCC2XCI4vq2RKuMWMFOJT0Sv3pY5MWEnMqCWmi75fexLa0KZyojG+bSy9jo16xz29xBLV4eD/C25bAQWFwtZD6ThBAXodihM3Byo1r/ttEkIjE3LubzvQJYc1JQIDAQAB
 * Private key: MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAK7nP9dxhu1kb+g2ElqU7SQNhcXE1JlvLccEx+RhCkyXGGcjhxoILZcIji+rZEq4xYwU4lPRK/eljkxYScyoJaaLvl97EtrQpnKiMb5tLL2OjXrHPb3EEtXh4P8LblsBBYXC1kPpOEEBeh2KEzcHKjWv+20SQiMTcu5vO9AlhzUlAgMBAAECgYAOKyOL02GHx5QdLowsFFZljkbg74H9b/k4XxXGVWodLDxI1qLyI+l1i7bg+7RjLTark2GGQrKaHCo72wcXciOiiJBjjmIeSdHrpHCeLxk1rEkxs36db2RmDXSeBEq+c+tG3bc1H6/snxZ8c3YyaiBuPjLc/6G9bzoBo0ns2Wp9PQJBAPYo2bVI8zlWAgjovsBcP1il/dcWyC0bTphwPHlQaO9YDF7+376vuIl3fTWYIq8MhXVHAZIuQRqW196MmuTw/OMCQQC15S0bZRerXRlypfwrLhlbz8OxHQeG90QTQdG3WrcQY4PapuPC5/5qEcojpqDLIM4MEiZs5hIMj3kc1TLfgOxXAkBuXwimoSv1VFwbNIh65aG9lMfJTjy5BNprvT9QQb6bOoZpfaxC6rU6ZeotQqaiiGG6oPjSW4zzaBkofzDgYDFzAkEArKcnRKyVZfxNzmxNSrNMMMCqMLCsV2jXPiwooxDBWRYMrvvgjz3kWMwgAe0FDSpLSlvkC1Pq5+87d6nKyym1qwJAFzxu/oA4lMVtvseZ4PCi1Ibr9e2J/kz3dUvSeGTO4kHw8j+JtBj85OsdbVp+CN00C1LvpIVakNQ6rgPN1oosfw==
 */

public class RSAUtilTest {

    @Test
    public void encrypt() {
        String key = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCu5z/XcYbtZG/oNhJalO0kDYXFxNSZby3HBMfk" +
                "YQpMlxhnI4caCC2XCI4vq2RKuMWMFOJT0Sv3pY5MWEnMqCWmi75fexLa0KZyojG+bSy9jo16xz29xBLV4" +
                "eD/C25bAQWFwtZD6ThBAXodihM3Byo1r/ttEkIjE3LubzvQJYc1JQIDAQAB";
        byte publicKey[] = key.getBytes();
        String encoded = "";
        try {
            encoded = new String(RSAUtil.encrypt(publicKey, new byte[]{2, 3}));
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
        System.out.println(encoded);
    }
}