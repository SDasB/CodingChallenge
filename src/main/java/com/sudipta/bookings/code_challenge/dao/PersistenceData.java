package com.sudipta.bookings.code_challenge.dao;

import com.sudipta.bookings.code_challenge.model.Booking;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class PersistenceData {

    public static Map<String, Booking> bookingMap = new HashMap<>();

    public static Map<String, Set<Booking>> currencyBookingSetMap = new HashMap<>();

    public static Map<String, Set<Booking>> departmentBookingSetMap = new HashMap<>();

}
