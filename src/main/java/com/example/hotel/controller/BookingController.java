package com.example.hotel.controller;


import com.example.hotel.model.BookingReport;
import com.example.hotel.report.Report;
import com.example.hotel.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class BookingController {

    @Autowired
    private BookingRepository bookingRepository;

    @GetMapping(path = "/rooms")
    public List<BookingReport> getRoomsSummary() {
        return new Report().getOrderReport(bookingRepository.findAll());
    }
}
