package com.example.hotel.report;

import com.example.hotel.model.Client;
import com.example.hotel.model.Booking;
import com.example.hotel.model.BookingReport;
import com.example.hotel.model.Room;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.*;

public class ReportTest {

    @Test
    public void getOrderReport() {
        Room r1 = new Room(1L, 2, true, 100);
        Room r2 = new Room(2L, 2, true, 120);

        Client c1 = new Client(1L, "Jan Nowak");
        Client c2 = new Client(2L, "Adam Kowalski");

        Booking o1 = new Booking(1L, c1, r1, LocalDate.now().plusDays(4L), LocalDate.now().plusDays(7L));
        Booking o2 = new Booking(2L, c1, r2, LocalDate.now().plusDays(10L), LocalDate.now().plusDays(14L));
        Booking o3 = new Booking(3L, c2, r1, LocalDate.now().plusDays(20L), LocalDate.now().plusDays(22L));
        Booking o4 = new Booking(4L, c2, r2, LocalDate.now().plusDays(25L), LocalDate.now().plusDays(27L));

        List<Booking> bookings = new ArrayList<>();
        bookings.add(o1);
        bookings.add(o2);
        bookings.add(o3);
        bookings.add(o4);
        List<BookingReport> given = new Report().getOrderReport(bookings);

        List<BookingReport> expected = new ArrayList<>();
        expected.add(new BookingReport("Room: 1", LocalDate.now().plusDays(4L), LocalDate.now().plusDays(7L)));
        expected.add(new BookingReport("Room: 2", LocalDate.now().plusDays(10L), LocalDate.now().plusDays(14L)));
        expected.add(new BookingReport("Room: 3", LocalDate.now().plusDays(20L), LocalDate.now().plusDays(22L)));
        expected.add(new BookingReport("Room: 4", LocalDate.now().plusDays(25L), LocalDate.now().plusDays(27L)));

        given.sort(Comparator.comparing(BookingReport::getNameRoom));
        expected.sort(Comparator.comparing(BookingReport::getNameRoom));

        assertEquals(given, expected);
    }
}