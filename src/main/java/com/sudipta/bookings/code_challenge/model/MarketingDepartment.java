package com.sudipta.bookings.code_challenge.model;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class MarketingDepartment implements Department {

    @Override
    public String doBusiness(Booking booking) {

        sendEmailToCustomerAndDepartment(booking);
        sendEmailToDepartmentAdmin(booking);
        addRevenueDetails(booking);
        log.info("Marketing Department Business executed");
        return "Marketing Department Business executed";
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

    @Override
    public String getName() {


        return "Marketing Department";
    }

}
