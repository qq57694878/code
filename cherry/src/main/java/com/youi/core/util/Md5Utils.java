package com.youi.core.util;

import java.io.InputStream;
import java.security.MessageDigest;

public class Md5Utils {
    private final static char hexDigits[] = { '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
    public static String getMd5(String text) {
        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            md.update(text.getBytes("UTF-8"));

            byte[] bytes = md.digest();
            int j = bytes.length;
            char[] c = new char[j * 2];
            int k = 0;

            for (int i = 0; i < j; i++) {
                byte b = bytes[i];
                c[k++] = hexDigits[b >>> 4 & 0xf];
                c[k++] = hexDigits[b & 0xf];
            }

            return new String(c);
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
    }


    public final static String MD5(InputStream is) {
        InputStream in = is;
        byte buffer[] = new byte[1024];
        int len;
        try {
            MessageDigest digest = MessageDigest.getInstance("md5");
            while ((len = in.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, len);
            }
            //in.close();
            byte[] b = digest.digest();
            return byteToHexString(b);

        } catch (Exception e) {
            return null;
        }
    }


    private static String byteToHexString(byte[] tmp) {
        String s;
        char str[] = new char[16 * 2];
        int k = 0;
        for (int i = 0; i < 16; i++) {
            byte byte0 = tmp[i];
            str[k++] = hexDigits[byte0 >>> 4 & 0xf];

            str[k++] = hexDigits[byte0 & 0xf];
        }
        s = new String(str);
        return s;
    }
}
