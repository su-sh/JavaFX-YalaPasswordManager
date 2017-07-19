/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 *
 * @author sushant
 */
public class EncryptUtils {

    public static final String DEFAULT_ENCODING = "UTF-8";
    static BASE64Encoder enc = new BASE64Encoder();
    static BASE64Decoder dec = new BASE64Decoder();

    public static String base64encode(String text) {
        try {
            return enc.encode(text.getBytes(DEFAULT_ENCODING));
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    public static String base64decode(String text) {
        try {
            return new String(dec.decodeBuffer(text), DEFAULT_ENCODING);
        } catch (IOException e) {
            return null;
        }
    }

    public static String xorMessage(String message, String key) {
        try {
            if (message == null || key == null) {
                return null;
            }
            char[] keys = key.toCharArray();
            char[] mesg = message.toCharArray();

            int ml = mesg.length;
            int kl = keys.length;
            char[] newmsg = new char[ml];

            for (int i = 0; i < ml; i++) {
                newmsg[i] = (char) (mesg[i] ^ keys[i % kl]);
            }

            return new String(newmsg);
        } catch (Exception e) {
            return null;
        }
    }

    public String enc(String password, String key) {
        String txt = xorMessage(password, key);
        String encoded = base64encode(txt);
        return encoded;
    }

    public String dec(String password, String key) {
        String decoded = base64decode(password);
        String txt = xorMessage(decoded, key);
        return txt;
    }
}
