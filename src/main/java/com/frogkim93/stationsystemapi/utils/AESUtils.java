package com.frogkim93.stationsystemapi.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AESUtils {
    private static final String SECRET_KEY = "StationSimulatorKeyABCDE";

    public static String encrypt(int value) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            SecretKeySpec key = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] bytes = Long.toString(value).getBytes();
            byte[] encryptedBytes = cipher.doFinal(bytes);
            return Base64.getUrlEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return String.valueOf(value);
    }

    public static int decrypt(String encryptedValue) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            SecretKeySpec key = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] encryptedBytes = Base64.getUrlDecoder().decode(encryptedValue);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            return Integer.parseInt(new String(decryptedBytes));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }
}
