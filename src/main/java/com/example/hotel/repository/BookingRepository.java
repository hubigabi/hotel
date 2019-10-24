package com.example.hotel.repository;

import com.example.hotel.model.Booking;
import com.example.hotel.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByRoom(Room room);
}
