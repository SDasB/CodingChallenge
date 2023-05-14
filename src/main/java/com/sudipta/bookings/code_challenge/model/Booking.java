package com.sudipta.bookings.code_challenge.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.sudipta.bookings.code_challenge.dao.DepartmentFactory;
import com.sudipta.bookings.code_challenge.dto.BookingDTO;
import com.sudipta.bookings.code_challenge.utils.BookingKeyGenerator;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)

@AllArgsConstructor
//@NoArgsConstructor
@With
@Getter
@Setter
public class Booking {

	@Builder.Default
    private String bookingId;

    private String description;

    private double price;

    private String currency;

    private Date subscriptionStartDate;

    private String email;

    @JsonIgnore
    private Department department;

    @JsonProperty("department")
    public String getDepartmentName() {
        return this.department.getName();
    }

    public Booking(BookingDTO dto) {
;       this.setBookingId(BookingKeyGenerator.getInstance().generateBookingId());
        this.setDepartment(DepartmentFactory.createDepartment(dto.getDepartment()));
        this.setDescription(dto.getDescription());
        this.setPrice(dto.getPrice());
        this.setCurrency(dto.getCurrency());
        this.setSubscriptionStartDate(dto.getSubscription_start_date());
        this.setEmail(dto.getEmail());
    }

    public Booking(String id, BookingDTO dto) {
        this.setBookingId(id);
        this.setDepartment(DepartmentFactory.createDepartment(dto.getDepartment()));
        this.setDescription(dto.getDescription());
        this.setPrice(dto.getPrice());
        this.setCurrency(dto.getCurrency());
        this.setSubscriptionStartDate(dto.getSubscription_start_date());
        this.setEmail(dto.getEmail());
    }

    public void modifyBooking(BookingDTO dto) {
        this.setDepartment(DepartmentFactory.createDepartment(dto.getDepartment()));
        this.setDescription(dto.getDescription());
        this.setPrice(dto.getPrice());
        this.setCurrency(dto.getCurrency());
        this.setSubscriptionStartDate(dto.getSubscription_start_date());
        this.setEmail(dto.getEmail());
    }

}
