package com.sudipta.bookings.code_challenge.model;

public interface Department {

    String doBusiness(Booking booking);
    Boolean sendEmailToCustomerAndDepartment(Booking booking);
    Boolean sendEmailToDepartmentAdmin(Booking booking);

    Boolean addRevenueDetails(Booking booking);

    String getName();

}
