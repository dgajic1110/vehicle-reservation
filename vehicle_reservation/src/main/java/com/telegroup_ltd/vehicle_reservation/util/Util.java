package com.telegroup_ltd.vehicle_reservation.util;

import org.apache.commons.codec.binary.Hex;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Util {

    private static final String alphaNumericString = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static SecureRandom random = new SecureRandom();

    public static boolean bothNullOrEqual(Object first,Object second){
        if (first==null && second ==null)
            return true;
        if (first!=null && first.equals(second))
            return true;
        return false;
    }

    public static String randomString(int length){
        StringBuilder stringBuilder = new StringBuilder(length);
        for(int i = 0; i < length; i++){
            stringBuilder.append(alphaNumericString.charAt(random.nextInt(alphaNumericString.length())));
        }
        return stringBuilder.toString();
    }

    public static String hashPassword(String plainText)  {
        MessageDigest digest= null;
        try {
            digest = MessageDigest.getInstance("SHA-512");
            return Hex.encodeHexString(digest.digest(plainText.getBytes()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
