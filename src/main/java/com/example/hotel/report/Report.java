package com.example.hotel.report;

import com.example.hotel.model.Booking;
import com.example.hotel.model.BookingReport;
import com.example.hotel.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class Report {

    @Autowired
    BookingRepository bookingRepository;

    public List<BookingReport> getOrderReport(List <Booking> bookings){
        return bookings
                .stream()
                .map(order -> new BookingReport("Room: " + order.getRoom().getId(),
                        order.getFromDate(), order.getToDate()))
                .collect(Collectors.toList());
    }
}
