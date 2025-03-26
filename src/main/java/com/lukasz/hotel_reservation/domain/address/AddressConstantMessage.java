package com.lukasz.hotel_reservation.domain.address;

public class AddressConstantMessage {
    public static final String STREET_REGEX = "^[A-Za-z0-9]+$";
    public static final String STREET_REGEX_MESSAGE = "Incorrect street";
    public static final String STREET_NUMBER_REGEX = "^[0-9]+[a-zA-Z]+$";
    public static final String STREET_NUMBER_REGEX_MESSAGE = "Street number should be alphanumeric";
    public static final String POSTAL_CODE_REGEX = "^\\d{2}-\\d{3}$";
    public static final String POSTAL_CODE_REGEX_MESSAGE = "Postal code should be in the format 00-000";
    public static final String CITY_REGEX = "^[A-Za-z]+$";
    public static final String CITY_REGEX_MESSAGE = "Incorrect city";
    public static final String COUNTRY_REGEX = "^[A-Za-z]+$";
    public static final String COUNTRY_REGEX_MESSAGE = "Incorrect country";
}