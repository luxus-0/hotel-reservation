package com.lukasz.hotel_reservation.domain.customer;

public class CustomerConstantMessage {
    public static final String PHONE_REGEX = "^\\+\\d{0-2} \\d{9}$";
    public static final String PHONE_REGEX_MESSAGE = "Incorrect contact number";
    public static final String EMAIL_REGEX = "^[A-Za-z0-9]+@[a-z]+ .[a-z]{3}+";
    public static final String EMAIL_REGEX_MESSAGE = "Incorrect email";

    public static final String CUSTOMER_NAME_REGEX = "^[A-Z][a-z]*$";
    public static final String CUSTOMER_NAME_REGEX_MESSAGE = "Customer name should begin from big letter";

    public static final String CUSTOMER_SURNAME_REGEX = "^[A-Z][a-z]*$";
    public static final String CUSTOMER_SURNAME_REGEX_MESSAGE = "Customer name should begin from big letter";
}
