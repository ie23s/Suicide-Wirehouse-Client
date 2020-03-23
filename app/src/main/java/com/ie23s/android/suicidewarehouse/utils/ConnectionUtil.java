package com.ie23s.android.suicidewarehouse.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

@SuppressWarnings("CharsetObjectCanBeUsed")
public class ConnectionUtil {
    private static final String ip = "192.168.1.6";
    private static final int port = 6319;
    private static final String publicKeyString = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCu5z/XcYbtZG/o" +
            "NhJalO0kDYXFxNSZby3HBMfkYQpMlxhnI4caCC2XCI4vq2RKuMWMFOJT0Sv3pY5MWEnMqCWmi75fexLa0KZy" +
            "ojG+bSy9jo16xz29xBLV4eD/C25bAQWFwtZD6ThBAXodihM3Byo1r/ttEkIjE3LubzvQJYc1JQIDAQAB";
    private static byte[] publicKey;

    private SecretKeySpec aesKey;
    private AESUtil aesUtil;
    private byte[] aesSecBin;
    private byte[] aesSecBinserver;

    static  {
        try {
            publicKey = publicKeyString.getBytes("UTF-8");
        } catch (UnsupportedEncodingException ignored) {}
    }

    private SocketUtil socketUtil;
    public int openConnection() {
        System.out.println("fdfsfds" + 0);
        socketUtil = new SocketUtil(ip, port);
        System.out.println("fdfsfds" + 1);
        if (!socketUtil.openConnection())
            return 1;

        System.out.println("fdfsfds" + 2);
        initAES();
        System.out.println("fdfsfds" + 3);


        try {
            sendAESKey();
        } catch (IOException e) {
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 3;
        }

        System.out.println("fdfsfds" + 4);
        try {
            getServerAESSec();
        } catch (IOException e) {
            return 1;
        } catch (UnsignedExeption unsignedExeption) {
            unsignedExeption.printStackTrace();
            return 2;
        } catch (Exception e) {
            e.printStackTrace();
            return 3;
        }

        System.out.println("fdfsfds" + 5);

        try {
            sendData("OK".getBytes("UTF-8"));
            System.out.println(new String(getData(), "UTF-8"));
        } catch (IOException e) {
            return 1;
        } catch (UnsignedExeption unsignedExeption) {
            unsignedExeption.printStackTrace();
            return 2;
        } catch (Exception e) {
            e.printStackTrace();
            return 3;
        }
        System.out.println("Connection success!");

        return 0;
    }

    private void sendAESKey() throws BadPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, NoSuchProviderException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException, IOException {
        String message = RSAUtil.encrypt(publicKey, addPrefix(aesKey.getEncoded()));
        socketUtil.sendLine(message);
    }

    private byte[] addPrefix(byte[] data) {
        aesSecBin = aesUtil.nextSec();
        byte[] outputData = new byte[16 + data.length];

        System.arraycopy(aesSecBin, 0, outputData, 0, 16);
        System.arraycopy(data, 0, outputData, 16, data.length);

        return outputData;
    }

    private void initAES() {
        aesKey = AESUtil.createSecretKey();
        aesUtil = new AESUtil(aesKey);

    }

    private void getServerAESSec() throws IOException, UnsignedExeption {
        String message = socketUtil.readLine();
        System.out.println(message);
        aesSecBinserver = aesUtil.decrypt(message, aesSecBin);
    }

    private void sendData(byte[] data) throws IOException {
        String message = aesUtil.encrypt(addPrefix(data));
        socketUtil.sendLine(message);
    }

    private byte[] getData() throws IOException, UnsignedExeption {
        String message = socketUtil.readLine();
        System.out.println(message);
        byte[] data = aesUtil.decrypt(message, aesSecBinserver);
        System.out.println(data.length);
        byte[] decoded = new byte[data.length - 16];
        System.arraycopy(data, 0, aesSecBinserver, 0, 16);
        System.arraycopy(data, 16, decoded, 0, decoded.length);
        return decoded;
    }
}
