package com.sudipta.bookings.code_challenge.controller;


import com.sudipta.bookings.code_challenge.dto.BookingDTO;
import com.sudipta.bookings.code_challenge.model.Booking;
import com.sudipta.bookings.code_challenge.service.BookingService;
import com.sudipta.bookings.code_challenge.utils.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.UUID;
@Slf4j
@RestController
@RequestMapping("/bookingservice/bookings")
public class BookingController {

    @Autowired
    BookingService bookingService;

    @PostMapping("/bookings")
    public ResponseEntity<Object> createBooking(@RequestBody BookingDTO bookingDTO) {
        try {
            Validator.validateBooking(bookingDTO);
            Booking booking = bookingService.createBooking(bookingDTO);
            return new ResponseEntity<>(booking, HttpStatus.OK);
        } catch (Exception exception) {
            log.error(exception.getMessage());
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/bookings")
    public ResponseEntity<Object> getAllBookings() {
        try {
            log.info("/bookings service called");
            return new ResponseEntity<>(bookingService.getAllBookings(), HttpStatus.OK);
        } catch (Exception exception) {
            log.error(exception.getMessage());
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/bookings/{bookingId}")
    public ResponseEntity<Object> updateBookingById(@PathVariable String bookingId, @RequestBody BookingDTO bookingDTO) {
        try {
            Booking booking = bookingService.updateBooking(bookingId, bookingDTO);
            return new ResponseEntity<>(booking, HttpStatus.OK);
        } catch (Exception exception) {
            log.error(exception.getMessage());
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/bookings/{bookingId}")
    public ResponseEntity<Object> getBookingById(@PathVariable String bookingId) {
        try {
            Booking booking = bookingService.getBookingById(bookingId);
            if (booking == null) {
                return new ResponseEntity<>(String.format("Booking with ID: %s wasn't found", bookingId),
                        HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(booking, HttpStatus.OK);
        } catch (Exception exception) {
            log.error(exception.getMessage());
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/bookings/department/{department}")
    public ResponseEntity<Object> getBookingsByDepartment(
            @PathVariable(name = "department") String departmentName) {
        try {
            Set<Booking> bookingSet = bookingService.getBookingsByDepartment(departmentName);
            return new ResponseEntity<>(bookingSet, HttpStatus.OK);
        } catch (Exception exception) {
            log.error(exception.getMessage());
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/bookings/currencies")
    public ResponseEntity<Object> getListOfAllUsedCurrencies() {
        try {
            return new ResponseEntity<>(bookingService.getUsedCurrencyList(), HttpStatus.OK);
        } catch (Exception exception) {
            log.error(exception.getMessage());
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/sum/{currency}")
    public ResponseEntity<Object> getSumOfCurrency(@PathVariable String currency) {
        try {
            return new ResponseEntity<>(bookingService.getSumOfCurrency(currency), HttpStatus.OK);
        } catch (Exception exception) {
            log.error(exception.getMessage());
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/bookings/dobusiness/{bookingId}")
    public ResponseEntity<Object> doBusinessForBooking(@PathVariable String bookingId) {
        try {
            return new ResponseEntity<>(bookingService.doBusinessForBookingById(bookingId), HttpStatus.OK);
        } catch (Exception exception) {
            log.error(exception.getMessage());
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/bookings/dobusinessnew/{bookingId}")
    public ResponseEntity<Object> doBusinessForBooking1(@PathVariable String bookingId, HttpServletResponse response){
        try {
            DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
            String currentDateTime = dateFormatter.format(new Date());

            String headerKey = "Content-Disposition";
            String headerValue = "attachment; filename=pdf_" + currentDateTime + ".pdf";
            response.setHeader(headerKey, headerValue);

            this.bookingService.export(response, bookingId);
            return new ResponseEntity<>(bookingService.doBusinessForBookingById(bookingId), HttpStatus.OK);
        }
        catch(Exception exception){
            log.error(exception.getMessage());
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
