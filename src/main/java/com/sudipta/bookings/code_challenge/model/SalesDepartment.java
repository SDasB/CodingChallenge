package com.sudipta.bookings.code_challenge.model;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SalesDepartment implements Department{

    @Override
    public String doBusiness(Booking booking) {
        sendEmailToCustomerAndDepartment(booking);
        sendEmailToDepartmentAdmin(booking);
        addRevenueDetails(booking);
        log.info("Sales Department Business executed");
        return "Sales Department Business executed";
    }

    @Override
    public String getName() {
        return null;
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
