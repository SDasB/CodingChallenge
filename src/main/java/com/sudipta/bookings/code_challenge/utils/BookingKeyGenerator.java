package com.sudipta.bookings.code_challenge.utils;

import java.util.UUID;
public class BookingKeyGenerator{

    private String uniqueID;
    private static BookingKeyGenerator instance = null;

    private BookingKeyGenerator()
    {
        uniqueID=null;
    }
    public static synchronized  BookingKeyGenerator getInstance() {
        if (instance == null)
            instance = new BookingKeyGenerator();

        return instance;
    }

    public String generateBookingId(){
        uniqueID=UUID.randomUUID().toString();
        return uniqueID;
    }
}
