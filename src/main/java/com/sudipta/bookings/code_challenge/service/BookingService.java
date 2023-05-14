package com.sudipta.bookings.code_challenge.service;

import com.sudipta.bookings.code_challenge.dao.PersistenceData;
import com.sudipta.bookings.code_challenge.dto.BookingDTO;
import com.sudipta.bookings.code_challenge.model.Booking;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;


@Service
@Slf4j
public class BookingService {

    public Booking createBooking(BookingDTO bookingDTO) {
        Booking booking = new Booking(bookingDTO);
        this.addBookingToPersistence(booking);
        //Slog.info("A new Booking with ID: {} was created.", booking.getBookingId());
        return booking;
    }

    public Booking createBooking(String bookingId, BookingDTO bookingDTO) {
        Booking booking = new Booking(bookingId, bookingDTO);
        this.addBookingToPersistence(booking);
        //og.info("A new Booking with ID: {} was created.", booking.getBookingId());
        return booking;
    }

    public Booking updateBooking(String bookingId, BookingDTO bookingDTO) {
        if (!PersistenceData.bookingMap.containsKey(bookingId)) {
            //log.info("Booking with ID: {} doesn't exist.", bookingId);
            return createBooking(bookingId, bookingDTO);
        } else {
            Booking booking = PersistenceData.bookingMap.get(bookingId);
            String existingCurrency = new String(booking.getCurrency());
            String existingDepartmentName = new String(booking.getDepartmentName());
            booking.modifyBooking(bookingDTO);
            if (!existingCurrency.equals(booking.getCurrency())) {
                PersistenceData.currencyBookingSetMap.get(existingCurrency).remove(booking);
                if (PersistenceData.currencyBookingSetMap.containsKey(booking.getCurrency())) {
                    PersistenceData.currencyBookingSetMap.get(booking.getCurrency()).add(booking);
                } else {
                    Set<Booking> bookingSet = new HashSet<>();
                    bookingSet.add(booking);
                    PersistenceData.currencyBookingSetMap.put(booking.getCurrency(), bookingSet);
                }
            }
            String departmentName = booking.getDepartmentName();
            if (!existingDepartmentName.equals(departmentName)) {
                PersistenceData.departmentBookingSetMap.get(existingDepartmentName).remove(booking);
                if (PersistenceData.departmentBookingSetMap.containsKey(departmentName)) {
                    PersistenceData.departmentBookingSetMap.get(departmentName).add(booking);
                } else {
                    Set<Booking> bookingSet = new HashSet<>();
                    bookingSet.add(booking);
                    PersistenceData.departmentBookingSetMap.put(departmentName, bookingSet);
                }
            }
            log.info("Booking with ID: {} was successfully updated.", bookingId);
            return booking;
        }
    }

    public Booking getBookingById(String bookingId) {
        Booking booking = PersistenceData.bookingMap.get(bookingId);
        if (booking == null) {
            log.warn("No booking exists for the Booking ID: {}", bookingId);
        }
        log.info("Booking with ID: {} was successfully found.", bookingId);
        return booking;
    }

    public Set<Booking> getBookingsByDepartment(String departmentName) {
        Set<Booking> bookingSet = PersistenceData.departmentBookingSetMap.getOrDefault(departmentName, new HashSet<>());
        log.info("Found {} bookings with departmentName: {}", bookingSet.size(), departmentName);
        return bookingSet;
    }

    public Set<String> getUsedCurrencyList() {
        Set<String> currencySet = PersistenceData.currencyBookingSetMap.keySet();
        log.info("Found {} currencies.", currencySet.size());
        return currencySet;
    }

    public double getSumOfCurrency(String currency) {
        if (!PersistenceData.currencyBookingSetMap.containsKey(currency)) {
            return 0.0D;
        }
        Set<Booking> bookingSet = PersistenceData.currencyBookingSetMap.get(currency);
        double sum = bookingSet.stream().mapToDouble(Booking::getPrice).sum();
        log.info("Total Sum of Currency: {} was found to be: {}", currency, sum);
        return sum;
    }

    public String doBusinessForBookingById(String bookingId) {
        Booking booking = this.getBookingById(bookingId);
        if (booking == null) {
            log.error("Can't do business for Booking with ID: {}", bookingId);
            return null;
        }
        return booking.getDepartment().doBusiness(booking);
    }

    private void addBookingToPersistence(Booking booking) {
        PersistenceData.bookingMap.put(booking.getBookingId(), booking);
        if (PersistenceData.currencyBookingSetMap.containsKey(booking.getCurrency())) {
            PersistenceData.currencyBookingSetMap.get(booking.getCurrency()).add(booking);
        } else {
            Set<Booking> bookingSet = new HashSet<>();
            bookingSet.add(booking);
            PersistenceData.currencyBookingSetMap.put(booking.getCurrency(), bookingSet);
        }

        String departmentName = booking.getDepartment().getName();
        if (PersistenceData.departmentBookingSetMap.containsKey(departmentName)) {
            PersistenceData.departmentBookingSetMap.get(departmentName).add(booking);
        } else {
            Set<Booking> bookingSet = new HashSet<>();
            bookingSet.add(booking);
            PersistenceData.departmentBookingSetMap.put(departmentName, bookingSet);
        }
    }

    public Set<Booking> getAllBookings() {

        Set<Booking> bookings = new HashSet<Booking> (PersistenceData.bookingMap.values());
        log.info("Found all bookings : "+bookings.size());
        return bookings;
    }

    public void export(HttpServletResponse response, String bookingId) throws Exception {

        Booking booking = PersistenceData.bookingMap.get(bookingId);
        if (booking == null) {
            log.warn("No booking exists for the Booking ID: {}", bookingId);
            throw new Exception("Booking Id Doesn't exist");
        }
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontTitle.setSize(18);

        Paragraph paragraph = new Paragraph("Invoice for booking :"+booking.getBookingId(), fontTitle);
        paragraph.setAlignment(Paragraph.HEADER);

        Font fontParagraph = FontFactory.getFont(FontFactory.HELVETICA);
        fontParagraph.setSize(12);

        Paragraph paragraph2 = new Paragraph("\n The booking execution details are :\n ", fontParagraph);
        paragraph2.setAlignment(Paragraph.ALIGN_LEFT);

        Paragraph paragraph3 = new Paragraph("Department : "+booking.getDepartmentName(), fontParagraph);
        paragraph3.setAlignment(Paragraph.ALIGN_LEFT);
        Paragraph paragraph4 = new Paragraph("Price : "+booking.getPrice(), fontParagraph);
        paragraph4.setAlignment(Paragraph.ALIGN_LEFT);
        Paragraph paragraph5 = new Paragraph("Currency: "+booking.getCurrency(), fontParagraph);
        paragraph5.setAlignment(Paragraph.ALIGN_LEFT);
        Paragraph paragraph6 = new Paragraph("Description: "+booking.getDescription(), fontParagraph);
        paragraph6.setAlignment(Paragraph.ALIGN_LEFT);
        Paragraph paragraph7 = new Paragraph("Start Date: "+booking.getSubscriptionStartDate(), fontParagraph);
        paragraph7.setAlignment(Paragraph.ALIGN_LEFT);
        Paragraph paragraph8 = new Paragraph("Email: "+booking.getSubscriptionStartDate(), fontParagraph);
        paragraph7.setAlignment(Paragraph.ALIGN_LEFT);

        document.add(paragraph);
        document.add(paragraph2);
        document.add(paragraph3);
        document.add(paragraph4);
        document.add(paragraph5);
        document.add(paragraph6);
        document.add(paragraph7);
        document.close();
    }
}
