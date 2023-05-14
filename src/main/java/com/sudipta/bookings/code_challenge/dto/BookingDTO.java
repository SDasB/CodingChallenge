package com.sudipta.bookings.code_challenge.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import java.util.Date;
import java.util.Objects;
import java.util.stream.Stream;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Slf4j
@Getter
@Setter
public class BookingDTO {

    private String description;

    private double price;

    private String currency;

    private Date subscription_start_date;

    private String email;

    private String department;

}
