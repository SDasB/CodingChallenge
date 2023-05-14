package com.sudipta.bookings.code_challenge.model;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SoftwareDepartment implements Department {

    @Override
    public String doBusiness(Booking booking) {

        sendEmailToCustomerAndDepartment(booking);
        sendEmailToDepartmentAdmin(booking);
        addRevenueDetails(booking);
        log.info("Software Department Business executed");
        return "Software Department Business executed";
    }

    @Override
    public String getName() {


        return "Software Department";
    }

    @Override
    public Boolean sendEmailToCustomerAndDepartment(Booking booking) {
        return null;
    }

    @Override
    public Boolean sendEmailToDepartmentAdmin(Booking booking) {
        return null;
    }

    @Override
    public Boolean addRevenueDetails(Booking booking) {
        return null;
    }
}
