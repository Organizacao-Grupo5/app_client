package util.security;

import java.util.Scanner;

public class Criptografia {
    public static String encrypt(String message, int shift) {

        String encrypted = "";
        for (char c : message.toCharArray()) {
            if (Character.isLetter(c)) {
                int offset =message.charAt(0);
                char encryptedChar = (char) (((c - offset + shift) % 26) + offset);
                encrypted+=encryptedChar;
            } else {
                encrypted+=c;
            }
        }
        return encrypted;
    }
}