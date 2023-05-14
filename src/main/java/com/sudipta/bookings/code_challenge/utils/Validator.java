package com.sudipta.bookings.code_challenge.utils;

import com.sudipta.bookings.code_challenge.dto.BookingDTO;
import lombok.extern.slf4j.Slf4j;

import java.util.regex.Pattern;

@Slf4j
public class Validator {
    private static final String REGEX_PATTERN = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

    public static void validateBooking(BookingDTO bookingDTO) throws Exception {

        boolean flag = true;
        flag = (bookingDTO.getCurrency() != null) && (bookingDTO.getDepartment() != null) && (bookingDTO.getPrice() != 0.0) && (bookingDTO.getEmail() != null) && (bookingDTO.getSubscription_start_date() != null) && (Pattern.compile(Validator.REGEX_PATTERN).matcher(bookingDTO.getEmail()).matches());
        log.info("Validate flag  " + flag);
        log.info("Validate 2  " + (Pattern.compile(Validator.REGEX_PATTERN).matcher(bookingDTO.getEmail()).matches()));
        log.info("Validate flag 2 " + flag);
        if (flag) {
            bookingDTO.setCurrency(bookingDTO.getCurrency().toUpperCase());
        } else {
            throw new Exception("Booking creation failed! Please validate email and check all the mandatory fields(Email,department,currency,price,Subscription Start Date) are present. Sample json request: {\"description\": \"Cool description!\", \"price\": 50.00, \"currency\": USD, \"subscription_start_date\": 683124845000, \"email\": \"valid@email.ok\", \"department\": \"another_department\" }");
        }
    }
}
