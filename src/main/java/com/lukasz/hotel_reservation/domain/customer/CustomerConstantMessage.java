package com.lukasz.hotel_reservation.domain.customer;

public class CustomerConstantMessage {
    public static final String PHONE_REGEX = "^\\+\\d+ \\d{3} \\d{3} \\d{3}$";
    public static final String PHONE_REGEX_MESSAGE = "Incorrect contact number";

    public static final String EMAIL_REGEX = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    public static final String EMAIL_REGEX_MESSAGE = "Incorrect email";

    public static final String CUSTOMER_NAME_REGEX = "^[A-Z][a-z]*$";
    public static final String CUSTOMER_NAME_REGEX_MESSAGE = "Customer name should begin with a capital letter";

    public static final String CUSTOMER_SURNAME_REGEX = "^[A-Z][a-z]*$";
    public static final String CUSTOMER_SURNAME_REGEX_MESSAGE = "Customer surname should begin with a capital letter";
}
