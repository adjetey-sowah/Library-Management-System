package com.juls.firstapp.librarymanagementsystem.model.lending;

import com.juls.firstapp.librarymanagementsystem.model.enums.ReservationStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

class ReservationsTest {

    private Reservations reservation;

    @BeforeEach
    void setUp() {
        reservation = new Reservations();
    }

    @Test
    void testDefaultConstructor() {
        assertNotNull(reservation);
        assertEquals(ReservationStatus.PENDING, reservation.getStatus());
        assertNotNull(reservation.getReservationDate());
    }

    @Test
    void testParameterizedConstructor() {
        Long userId = 1L;
        Long resourceId = 2L;
        reservation = new Reservations(userId, resourceId);

        assertEquals(userId, reservation.getUserId());
        assertEquals(resourceId, reservation.getResourceId());
        assertEquals(ReservationStatus.PENDING, reservation.getStatus());
        assertNotNull(reservation.getReservationDate());
    }

    @Test
    void testSetAndGetReservationId() {
        Long reservationId = 100L;
        reservation.setReservationId(reservationId);
        assertEquals(reservationId, reservation.getReservationId());
    }

    @Test
    void testSetAndGetUserId() {
        Long userId = 1L;
        reservation.setUserId(userId);
        assertEquals(userId, reservation.getUserId());
    }

    @Test
    void testSetAndGetResourceId() {
        Long resourceId = 2L;
        reservation.setResourceId(resourceId);
        assertEquals(resourceId, reservation.getResourceId());
    }

    @Test
    void testSetAndGetStatus() {
        reservation.setStatus(ReservationStatus.COMPLETED);
        assertEquals(ReservationStatus.COMPLETED, reservation.getStatus());
    }

    @Test
    void testSetAndGetReservationDate() {
        LocalDateTime newDate = LocalDateTime.of(2024, 12, 28, 14, 0);
        reservation.setReservationDate(newDate);
        assertEquals(newDate, reservation.getReservationDate());
    }

    @Test
    void testToString() {
        reservation.setReservationId(1L);
        reservation.setUserId(2L);
        reservation.setResourceId(3L);
        reservation.setStatus(ReservationStatus.PENDING);

        String expected = "Reservations{" +
                "reservationId=1, userId=2, resourceId='3', status=PENDING, reservationDate=" +
                reservation.getReservationDate() + '}';
        assertTrue(reservation.toString().contains("reservationId=1"));
    }
}
