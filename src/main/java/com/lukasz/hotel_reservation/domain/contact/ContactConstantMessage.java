package com.lukasz.hotel_reservation.domain.contact;

public class ContactConstantMessage {
    public static final String PHONE_REGEX = "^\\+\\d{0-2} \\d{9}$";
    public static final String PHONE_REGEX_MESSAGE = "Incorrect contact number";
    public static final String EMAIL_REGEX = "^[A-Za-z0-9]+@[a-z]+ .[a-z]{3}+";
    public static final String EMAIL_REGEX_MESSAGE = "Incorrect email";
}
