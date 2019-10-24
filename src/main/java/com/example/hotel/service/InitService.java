package com.example.hotel.service;

import com.example.hotel.model.Booking;
import com.example.hotel.model.Client;
import com.example.hotel.model.Room;
import com.example.hotel.repository.ClientRepository;

import com.example.hotel.repository.BookingRepository;
import com.example.hotel.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

import java.time.LocalDate;
import java.util.Arrays;

@Service
public class InitService {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    BookingRepository bookingRepository;

    @PostConstruct
    public void init(){
        Room r1 = new Room(0L, 2, true, 100);
        Room r2 = new Room(0L, 2, true, 120);
        Room r3 = new Room(0L, 2, false, 200);
        Room r4 = new Room(0L, 2, true, 150);
        Room r5 = new Room(0L, 3, true, 180);
        Room r6 = new Room(0L, 3, true, 260);
        Room r7 = new Room(0L, 3, false, 160);
        Room r8 = new Room(0L, 3, false, 220);

        Client c1 = new Client(0L, "Jan Nowak");
        Client c2 = new Client(0L, "Adam Kowalski");

        r1 = roomRepository.save(r1);
        r2 = roomRepository.save(r2);
        r3 = roomRepository.save(r3);
        r4 = roomRepository.save(r4);
        r5 = roomRepository.save(r5);
        r6 = roomRepository.save(r6);
        r7 = roomRepository.save(r7);
        r8 = roomRepository.save(r8);

        c1 = clientRepository.save(c1);
        c2 = clientRepository.save(c2);

        Booking o1 = new Booking(0L, c1, r1, LocalDate.now().plusDays(4L), LocalDate.now().plusDays(7L));
        Booking o2 = new Booking(0L, c1, r2, LocalDate.now().plusDays(10L), LocalDate.now().plusDays(14L));
        Booking o3 = new Booking(0L, c2, r1, LocalDate.now().plusDays(20L), LocalDate.now().plusDays(22L));
        Booking o4 = new Booking(0L, c2, r2, LocalDate.now().plusDays(25L), LocalDate.now().plusDays(27L));

       bookingRepository.saveAll(Arrays.asList(o1, o2, o3, o4));
    }

}
