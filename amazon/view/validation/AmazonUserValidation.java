package com.amazon.view.validation;

/**
 * <p>
 * Validates the email, password, username, address, phone number
 * </p>
 *
 * @author Roshan
 * @version 1.0
 */
public class AmazonUserValidation extends AmazonValidation {

    private static AmazonUserValidation amazonUserValidation  = null;

    private AmazonUserValidation() {}

    /**
     * <p>
     *     Represents the object of AmazonUserValidation class can be created for only one time
     * </p>
     * @return Represents {@link AmazonUserValidation}
     */
    public static AmazonUserValidation getAmazonUserValidation() {
        if(amazonUserValidation == null) {
            return new AmazonUserValidation();
        } else {
            return amazonUserValidation;
        }
    }

    /**
     * <p>
     * Validates the email format enter by the user
     * </p>
     *
     * @param email Email that is entered by the user
     * @return true if the email is matches pattern otherwise return false
     */
    public boolean validateEmail(final String email) {
        return email.matches("^[a-zA-z]+[a-zA-Z0-9_.\\S]+@[a-z]{3,}\\.[a-z\\S]{2,3}$");
    }

    /**
     * <p>
     * Validates the password format enter by the user
     * </p>
     *
     * @param password Password that is entered by the user
     * @return True if the password matches the pattern otherwise return false
     */
    public boolean validatePassword(final String password) {
        return password.matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).\\S{7,20}$");
    }

    /**
     * <p>
     * Validates username format enter by the user
     * </p>
     *
     * @param userName users name that is entered by user
     * @return True the users name matches the pattern
     */
    public boolean validateUserName(final String userName) {
        return userName.matches("^[a-zA-Z][a-zA-Z0-9]{3,16}");
    }

    /**
     * <p>
     * Validates user address format enter by the user
     * </p>
     *
     * @param address user address
     * @return True if the address is valid
     */
    public boolean validateAddress(final String address) {
        return address.matches("^[0-9]+/[0-9]{1,3}[\\w*\\s*[0-9]*]{2,}");
    }

    /**
     * <p>
     * Validates the phone no format enter by the user
     * </p>
     *
     * @param phoneNo Phone number entered by the user
     * @return True if the Phone number matches the pattern otherwise returns false
     */
    public boolean validatePhone(final String phoneNo) {
        return phoneNo.matches("^[6-9][0-9]{9}+$");
    }

    /**
     * <p>
     * Check's whether the user value to determine the user wants to return back to menu
     * </p>
     *
     * @param value value for validate
     * @return True if the value is matches the pattern otherwise return false
     */
    public boolean isReturnToMenu(final String value) {
        return ('#' == value.charAt(0));
    }

    /**
     * <p>
     * Check's whether the user wants to exit or not
     * </p>
     *
     * @param userChoice value for validate
     * @return True if the value is matches the pattern otherwise return false
     */
    public boolean toContinueValidation(final String userChoice) {
        return ('y' == userChoice.charAt(0) || 'Y' == userChoice.charAt(0));
    }
}
