/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.security.SecureRandom;
import java.math.BigInteger;
import java.util.Random;

/**
 *
 * @author sushant
 */
public class Generator {

    private SecureRandom random = new SecureRandom();

    public String nextSessionId() {
        return new BigInteger(60, random).toString(32);
    }

    public static String generateSessionKey(int length) {
        String alphabet = new String("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"); //9
        int n = alphabet.length(); //10

        String result = new String();
        Random r = new Random(); //11

        for (int i = 0; i < length; i++) //12
        {
            result = result + alphabet.charAt(r.nextInt(n)); //13
        }
        return result;
    }
}
