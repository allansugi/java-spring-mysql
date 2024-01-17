package com.example.demo.verifier;

public class RegisterVerifier {
    /**
     * @param password
     * @return true if password length >= 8, at least 1 uppercase letter, special character and number
     */
    public static boolean validPasswordStrength(String password) {
        boolean containSpecialCharacter = false;
        boolean containNumber = false;
        boolean containUpper = false;
        boolean containLower = false;

        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) {
                containNumber = true;
            } else if (Character.isUpperCase(c)) {
                containUpper = true;
            } else if (Character.isLowerCase(c)) {
                containLower = true;
            } else {
                containSpecialCharacter = true;
            }
        }

        // Ensure that at least one character from each category is present
        return containLower
                && containUpper
                && containNumber
                && containSpecialCharacter
                && password.length() >= 8;
    }

}
