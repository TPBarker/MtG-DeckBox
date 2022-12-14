package com.example.mtgdeckbox;

/**
 * This class takes input and validates them against a variety of conditions.
 * @author: Tom Barker
 */
public class Validator {

    /**
     * This is the default constructor for this class.
     */
    public Validator() {
    }

    /**
     * This method validates an email address.
     * @param email a String containing the email address to be validated.
     * @return a Boolean describing if the email is valid or not.
     */
    public boolean validateEmail(String email) {
        String validRegex = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(validRegex);
    }

    /**
     * This method validates a password.
     * @param pass a String containing the password to be validated.
     * @return a Boolean describing if the password is valid or not.
     */
    public boolean validatePassword(String pass) {
        boolean valid = false;
        int length = pass.length();
        int charCount = 0;
        int numCount = 0;
        char[] passArray = pass.toCharArray();

        for (char c : passArray) {
            if (Character.isDigit(c)) {
                numCount ++;
            } else {
                c = Character.toUpperCase(c);
                if (c >= 'A' && c <= 'Z') {
                    charCount ++;
                }
            }
        }

        if (length > 5 && numCount > 0 && charCount > 0) {
            // Password is valid
            valid = true;
        }
        return valid;
    }

    /**
     * This method validates a String.
     * @param text a String to be validated.
     * @return a Boolean describing if the String is valid or not.
     */
    public boolean validateString(String text) {
        return !text.isEmpty();
    }
}
